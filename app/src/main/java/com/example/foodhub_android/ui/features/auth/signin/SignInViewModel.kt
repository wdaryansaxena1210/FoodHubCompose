package com.example.foodhub_android.ui.features.auth.signin

import android.content.Context
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodhub_android.data.FoodApi
import com.example.foodhub_android.data.auth.GoogleAuthUiProvider
import com.example.foodhub_android.data.models.OAuthRequest
import com.example.foodhub_android.data.models.SignInRequest
import com.example.foodhub_android.data.models.SignUpRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SignInViewModel @Inject constructor(val foodApi : FoodApi) : ViewModel(){

    val googleAuthUiProvider = GoogleAuthUiProvider()

    private val _uiState = MutableStateFlow<SignInEvent>(SignInEvent.Nothing)
    val uiState = _uiState.asStateFlow()

    private val _navigationEvent = MutableSharedFlow<SignInNavigationEvent>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()


    fun onEmailChange(email : String){
        _email.value= email
    }
    fun onPasswordChange(password : String){
        _password.value= password
    }

    //trigger SignUpEvent(s)
    fun onSignInClick(){

        viewModelScope.launch {
            _uiState.value = SignInEvent.Loading

            try{
                val response = foodApi.signIn(
                    SignInRequest(
                        email = _email.value,
                        password = _password.value,
                    )
                )

                if (response.token.isNotEmpty()){
                    _uiState.value = SignInEvent.Success
                    _navigationEvent.emit(SignInNavigationEvent.NavigateToHome)
//                    println("changed to error cuz token not empty")
                }
            }catch (e : Exception){
                e.printStackTrace()
                _uiState.value = SignInEvent.Error
//                println("changed to error in exception")
            }
//            _uiState.value = SignUpEvent.Success
//            _navigationEvent.emit(SignUpNavigationEvent.NavigateToHome)
        }
    }

    fun onLoginClick() {
        viewModelScope.launch {
            _navigationEvent.emit(SignInNavigationEvent.NavigateToSignUp)
        }
    }

    fun onGoogleSignInClick(context :Context) {
        _uiState.value = SignInEvent.Loading
        viewModelScope.launch {
            val response = googleAuthUiProvider.signIn(context, CredentialManager.create(context))

            //googleAuthUiProvider.signIn technically NEVER returns null but for future changes
            if(response!=null){

                val request = OAuthRequest(
                    token = response.token,
                    provider = "google"
                )
                val res = foodApi.oAuth(request)

                Log.d("SignInViewModel", "Sign in token after hitting /auth/oauth backend: ${res.token}")

                if(res.token.isNotEmpty()){
                    _uiState.value = SignInEvent.Success
                    _navigationEvent.emit(SignInNavigationEvent.NavigateToHome)
                }
                _uiState.value = SignInEvent.Success
                _navigationEvent.emit(SignInNavigationEvent.NavigateToHome)
            }
            else{
                _uiState.value = SignInEvent.Error
            }
        }

    }


    sealed class SignInNavigationEvent{
        object NavigateToSignUp : SignInNavigationEvent()
        object NavigateToHome : SignInNavigationEvent()
    }

    sealed class SignInEvent(){
        object Success: SignInEvent()
        object Error : SignInEvent()
        object Loading : SignInEvent()
        object Nothing : SignInEvent()
    }


}
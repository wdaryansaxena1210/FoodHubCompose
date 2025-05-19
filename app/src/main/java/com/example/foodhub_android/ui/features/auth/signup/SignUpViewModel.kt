package com.example.foodhub_android.ui.features.auth.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodhub_android.data.FoodApi
import com.example.foodhub_android.data.models.SignUpRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/*
NOTE: this viewModel does not CAUSE navigation (as it does not have a navController).
Here is the workflow of navigation:
1. we have a FLOW called navigationEvent
2. this vm asks the Composables to collect/listen to the navigationEvent FLOW. When a
navigationEvent is collected inside a composable, the composable can do NavController.navigate(sumn)
(as the composable has access to the navController)
 */
@HiltViewModel
class SignUpViewModel @Inject constructor(val foodApi : FoodApi) : ViewModel(){

    private val _uiState = MutableStateFlow<SignUpEvent>(SignUpEvent.Nothing)
    val uiState = _uiState.asStateFlow()

    private val _navigationEvent = MutableSharedFlow<SignUpNavigationEvent>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private val _name = MutableStateFlow("")
    val name = _name.asStateFlow()

    fun onEmailChange(email : String){
        _email.value= email
    }
    fun onPasswordChange(password : String){
        _password.value= password
    }
    fun onNameChange(name : String) {
        _name.value = name
    }

    //trigger SignUpEvent(s)
    fun onSignUpClick(){

        viewModelScope.launch {
            _uiState.value = SignUpEvent.Loading

            try{
                val response = foodApi.signUp(
                    SignUpRequest(
                        email = _email.value,
                        password = _password.value,
                        name = _name.value
                    )
                )

                if (response.token.isNotEmpty()){
                    _uiState.value = SignUpEvent.Success
                    _navigationEvent.emit(SignUpNavigationEvent.NavigateToHome)
//                    println("changed to error cuz token not empty")
                }
            }catch (e : Exception){
                e.printStackTrace()
                _uiState.value = SignUpEvent.Error
//                println("changed to error in exception")
            }
//            _uiState.value = SignUpEvent.Success
//            _navigationEvent.emit(SignUpNavigationEvent.NavigateToHome)
        }
    }

    fun onLoginClick() {
        viewModelScope.launch {
            _navigationEvent.emit(SignUpNavigationEvent.NavigateToLogin)
        }
    }


    sealed class SignUpNavigationEvent{
        object NavigateToLogin : SignUpNavigationEvent()
        object NavigateToHome : SignUpNavigationEvent()
    }

    sealed class SignUpEvent(){
        object Success: SignUpEvent()
        object Error : SignUpEvent()
        object Loading : SignUpEvent()
        object Nothing : SignUpEvent()
    }
}
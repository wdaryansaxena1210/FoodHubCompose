package com.example.foodhub_android.ui.features.auth.signup

import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.foodhub_android.R
import com.example.foodhub_android.ui.FoodHubTextField
import com.example.foodhub_android.ui.GroupSocialButtons
import com.example.foodhub_android.ui.theme.Orange
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import okio.blackholeSink

@Composable
fun SignUpScreen(modifier: Modifier = Modifier, viewModel: SignUpViewModel = hiltViewModel()) {
    val name: State<String> = viewModel.name.collectAsStateWithLifecycle()
    val email = viewModel.email.collectAsStateWithLifecycle()
    val password = viewModel.password.collectAsStateWithLifecycle()

    val uiState = viewModel.uiState.collectAsState()
    val errorMessage = remember { mutableStateOf<String?>(null) }
    val isLoading = remember { mutableStateOf(false) }


    when (uiState.value) {
        is SignUpViewModel.SignUpEvent.Error -> {
            isLoading.value = false
            errorMessage.value = "Something went wrong"
        }

        is SignUpViewModel.SignUpEvent.Loading -> {
            isLoading.value = true
            errorMessage.value = null
        }

        else -> {
            //only thing left is 'is Success' :
            isLoading.value = false
            errorMessage.value = null
        }
    }

    val context = LocalContext.current
    LaunchedEffect(true) {
        viewModel.navigationEvent.collectLatest {
            when (it) {
                is SignUpViewModel.SignUpNavigationEvent.NavigateToHome -> {
                    Toast.makeText(
                        context,
                        "Sign Up Successful",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is SignUpViewModel.SignUpNavigationEvent.NavigateToLogin -> {
                    //navigate to login
                }
            }
        }
    }


    Box(Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.ic_auth_bg),
            contentDescription = null,
            modifier = modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {


        Box(
            modifier = Modifier
                .weight(0.5f)
                .fillMaxWidth(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Text(
                text = "Sign Up",
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold
            )
        }

        //above this is the SIGN-UP text

        Box(modifier = Modifier.weight(0.5f))


        //Full Name and everything below it

        FoodHubTextField(
            value = name.value,
            onValueChange = { viewModel.onNameChange(it) },
            label = {
                Text(
                    text = stringResource(R.string.full_Name),
                    fontSize = 16.sp,
                    color = Color.Gray,
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
        )

        FoodHubTextField(
            value = email.value,
            onValueChange = { viewModel.onEmailChange(it) },
            label = {
                Text(
                    text = stringResource(R.string.email),
                    fontSize = 16.sp,
                    color = Color.Gray,
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
        )

        FoodHubTextField(
            value = password.value,
            onValueChange = { viewModel.onPasswordChange(it) },
            label = {
                Text(
                    text = stringResource(R.string.password),
                    fontSize = 16.sp,
                    color = Color.Gray,
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.size(16.dp))

        errorMessage.value?.let{
            Text(
                text = errorMessage.value ?: "", color = Color.Red,
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }

        Button(
            onClick = viewModel::onSignUpClick,
            colors = ButtonDefaults.buttonColors(containerColor = Orange),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .height(48.dp),
            elevation = ButtonDefaults.buttonElevation(8.dp)
        ) {

            Box() {
                AnimatedContent(targetState = isLoading.value,
                    transitionSpec = {
                        fadeIn(animationSpec = tween(300)) + scaleIn(initialScale = 0.8f) togetherWith
                                fadeOut(animationSpec = tween(300)) + scaleOut(targetScale = 0.8f)

                    }
                ) { targetState ->
                    if (targetState) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(24.dp)
                                .align(Alignment.Center),
                            color = Color.White,
                        )
                    } else {
                        Text(
                            text = stringResource(R.string.sign_up),
                            modifier = Modifier.padding(horizontal = 32.dp)
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            text = stringResource(R.string.aleady_have_account),
            style = TextStyle(letterSpacing = 1.sp),
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(Alignment.CenterHorizontally)
                .clickable { },
        )
        Spacer(
            Modifier
                .padding(16.dp)
                .background(Color.Black)
        )
        GroupSocialButtons(onFacebookClick = { }, onGoogleClick = { }, color = Color.Black)
    }
}

//@Preview(showBackground = true)
//@Composable
//fun SignUpScreenPreview(modifier: Modifier = Modifier) {
//    SignUpScreen()
//}
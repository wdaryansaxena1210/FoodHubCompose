package com.example.foodhub_android.ui.features.auth.signup

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
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foodhub_android.R
import com.example.foodhub_android.ui.FoodHubTextField
import com.example.foodhub_android.ui.GroupSocialButtons
import com.example.foodhub_android.ui.theme.Orange
import dagger.hilt.android.lifecycle.HiltViewModel
import okio.blackholeSink

@Composable
fun SignUpScreen(modifier: Modifier = Modifier, viewModel: SignUpViewModel ) {
    var name by remember { mutableStateOf("Aryan")}
    var email by remember { mutableStateOf("email@test.com")}
    var password by remember { mutableStateOf("password")}


    Box(Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.ic_auth_bg),
            contentDescription = null,
            modifier = modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
    }
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {


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
            value = name,
            onValueChange = {name=it},
            label = { Text(text = stringResource(R.string.full_Name), fontSize = 16.sp, color = Color.Gray,) },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
        )

        FoodHubTextField(
            value = email,
            onValueChange = {email=it},
            label = { Text(text = stringResource(R.string.email), fontSize = 16.sp, color = Color.Gray,) },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
        )

        FoodHubTextField(
            value = password,
            onValueChange = {password=it},
            label = { Text(text = stringResource(R.string.password), fontSize = 16.sp, color = Color.Gray,) },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.size(16.dp))

        Button(onClick = {},
            colors = ButtonDefaults.buttonColors(containerColor = Orange),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .height(48.dp),
            elevation = ButtonDefaults.buttonElevation(8.dp)
        ) {
            Text(text = stringResource(R.string.sign_up), modifier = Modifier.padding(horizontal = 32.dp))
        }
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            text = stringResource(R.string.aleady_have_account),
            style = TextStyle(letterSpacing = 1.sp) ,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(Alignment.CenterHorizontally)
                .clickable { },
        )
        Spacer(
            Modifier
                .padding(16.dp)
                .background(Color.Black))
        GroupSocialButtons(onFacebookClick = { }, onGoogleClick = { }, color = Color.Black)
    }
}

//@Preview(showBackground = true)
//@Composable
//fun SignUpScreenPreview(modifier: Modifier = Modifier) {
//    SignUpScreen()
//}
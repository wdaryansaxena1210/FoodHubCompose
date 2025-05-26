package com.codewithfk.foodhub.ui


import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.foodhub_android.data.FoodApi
import com.example.foodhub_android.data.FoodHubSession
import com.example.foodhub_android.ui.FoodHubNavHost
import com.example.foodhub_android.ui.features.auth.AuthScreen
import com.example.foodhub_android.ui.features.auth.signin.SignInScreen
import com.example.foodhub_android.ui.features.auth.signup.SignUpScreen
import com.example.foodhub_android.ui.navigation.AuthScreen
import com.example.foodhub_android.ui.navigation.Home
import com.example.foodhub_android.ui.navigation.Login
import com.example.foodhub_android.ui.navigation.SignUp
import com.example.foodhub_android.ui.theme.FoodHubAndroidTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var foodApi: FoodApi

    @Inject
    lateinit var foodHubSession: FoodHubSession

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FoodHubAndroidTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    //FIXME : vulnerability. you should check in your backend if the non-null token is not a random text but a valid token
                    val startPoint = if (foodHubSession.getToken() != null) Home::class else AuthScreen::class
                    Log.d("MainActivity", "startPoint: ${foodHubSession.getToken()}")

                    val navController = rememberNavController()
                    FoodHubNavHost(
                        navController = navController,
                        startDestination = startPoint,
                        modifier = Modifier.padding(innerPadding),

                    ) {
                        composable<SignUp>() {
                            SignUpScreen(navController = navController)
                        }
                        composable<AuthScreen>() {
                            AuthScreen(navController = navController)
                        }
                        composable<Home>() {
                            RestaurantHomeScreen(navController = navController)
                        }
                        composable<Login>() {
                            SignInScreen(navController = navController)
                        }
                        composable("test") {
                        }

                    }
                }
            }
        }

        if (::foodApi.isInitialized) {
            Log.d("MainActivity", "FoodApi initialized")
        }
    }

    @Composable
    private fun RestaurantHomeScreen(navController: NavHostController) {
        Text("Restaurant HOME Screen")
    }
}

package com.codewithfk.foodhub.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.foodhub_android.data.FoodApi
import com.example.foodhub_android.data.FoodHubSession
import com.example.foodhub_android.data.models.FoodItem
import com.example.foodhub_android.ui.features.auth.AuthScreen
import com.example.foodhub_android.ui.features.auth.signin.SignInScreen
import com.example.foodhub_android.ui.features.auth.signup.SignUpScreen
import com.codewithfk.foodhub.ui.feature.cart.CartScreen
import com.codewithfk.foodhub.ui.feature.food_item_details.FoodDetailsScreen
import com.codewithfk.foodhub.ui.feature.home.HomeScreen
import com.codewithfk.foodhub.ui.feature.restaurant_details.RestaurantDetailsScreen
import com.example.foodhub_android.ui.navigation.AuthScreen
import com.example.foodhub_android.ui.navigation.Cart
import com.example.foodhub_android.ui.navigation.FoodDetails
import com.example.foodhub_android.ui.navigation.Home
import com.example.foodhub_android.ui.navigation.Login
import com.example.foodhub_android.ui.navigation.RestaurantDetails
import com.example.foodhub_android.ui.navigation.SignUp
import com.example.foodhub_android.ui.navigation.foodItemNavType
import com.example.foodhub_android.ui.theme.FoodHubAndroidTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.reflect.typeOf

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
                    val startPoint = if (foodHubSession.getToken() != null) Home else AuthScreen
                    Log.d("MainActivity", "startPoint: ${foodHubSession.getToken()}")

                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = startPoint,
                        modifier = Modifier.padding(innerPadding),
                        enterTransition = {
                            slideIntoContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                                animationSpec = tween(300)
                            ) + fadeIn(animationSpec = tween(300))
                        },
                        exitTransition = {
                            slideOutOfContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                                animationSpec = tween(300)
                            ) + fadeOut(animationSpec = tween(300))
                        },
                        popEnterTransition = {
                            slideIntoContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                                animationSpec = tween(300)
                            ) + fadeIn(animationSpec = tween(300))
                        },
                        popExitTransition = {
                            slideOutOfContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                                animationSpec = tween(300)
                            ) + fadeOut(animationSpec = tween(300))
                        }
                    ) {
                        composable<SignUp>() {
                            SignUpScreen(navController = navController)
                        }
                        composable<AuthScreen>() {
                            AuthScreen(navController = navController)
                        }
                        composable<Home>() {
                            HomeScreen(navController = navController)
                        }
                        composable<Login>() {
                            SignInScreen(navController = navController)
                        }
                        composable<RestaurantDetails>() {
                            val route: RestaurantDetails = it.toRoute<RestaurantDetails>()
                            RestaurantDetailsScreen(
                                navController = navController,
                                name = route.name,
                                imageUrl = route.restaurantImageUrl,
                                restaurantId = route.restaurantId
                            )
                        }

                        composable<FoodDetails>(
                            typeMap = mapOf(typeOf<FoodItem>() to foodItemNavType)
                        ) {
                            val route = it.toRoute<FoodDetails>()
                            FoodDetailsScreen(
                                navController = navController,
                                foodItem = route.foodItem,
                                onItemAddedToCart = {
                                    Toast.makeText(
                                        this@MainActivity,
                                        "Item added to cart",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            )
                        }

                        composable("test") {
                            CartScreen(navController=navController)
                        }
                        composable<Cart>() {
//                            shouldShowBottomNav.value = true
                            CartScreen(navController)
                        }

                    }
                }
            }
        }

        if (::foodApi.isInitialized) {
            Log.d("MainActivity", "FoodApi initialized")
        }
    }
}




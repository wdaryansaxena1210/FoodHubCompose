package com.example.foodhub_android.ui

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import com.example.foodhub_android.R
import com.example.foodhub_android.ui.theme.Orange
import kotlin.reflect.KClass
import kotlin.reflect.KType

@Composable
fun GroupSocialButtons(
    modifier: Modifier = Modifier,
    color: Color = Color.White,
    onFacebookClick: () -> Unit,
    onGoogleClick: () -> Unit,
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .then(modifier),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            HorizontalDivider(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp),
                thickness = 1.dp,
                color = color
            )
            Text(
                text = stringResource(R.string.sign_in_with),
                modifier = Modifier.padding(4.dp),
                color = color
            )
            HorizontalDivider(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                thickness = 1.dp,
                color = color
            )
        }

        Row {
            SocialButton(
                icon = R.drawable.ic_facebook,
                title = R.string.sign_with_facebook,
                onClick = onFacebookClick,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.padding(4.dp))

            SocialButton(
                icon = R.drawable.ic_google,
                title = R.string.sign_with_google,
                onClick = onGoogleClick
            )
        }
    }
}


@Composable
fun SocialButton(
    modifier: Modifier = Modifier,
    icon: Int,
    title: Int,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
        modifier = Modifier.then(modifier),
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,

            ) {
            Image(
                painter = painterResource(id = R.drawable.ic_google),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.padding(4.dp))
            Text(
                text = stringResource(id = title),
                color = Color.Black,
                fontSize = 20.sp
            )
        }
    }
}

@Composable
fun FoodHubTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = RoundedCornerShape(10.dp),
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors().copy(
        focusedContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent,
        disabledContainerColor = Color.LightGray.copy(alpha = 0.4f),
    )
) {
    Column(

    ) {
        label?.let{
            Spacer(modifier = Modifier.size(8.dp))
            it()
        }
        OutlinedTextField(
            value = value,
            onValueChange,
            modifier,
            enabled,
            readOnly,
            textStyle.copy(fontWeight = FontWeight.SemiBold),
            null,
            placeholder,
            leadingIcon,
            trailingIcon,
            prefix,
            suffix,
            supportingText,
            isError,
            visualTransformation,
            keyboardOptions,
            keyboardActions,
            singleLine,
            maxLines,
            minLines,
            interactionSource,
            shape,
            colors
        )
    }
}


//common widget as the "animation" is same for NavHost in any flavor (Rider, Customer or Restaurant flavour)
@Composable
fun FoodHubNavHost(
    navController: NavHostController,
    startDestination: Any,
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.TopStart,
    route: KClass<*>? = null,
    typeMap: Map<KType, @JvmSuppressWildcards NavType<*>> = emptyMap(),
    enterTransition:
    (@JvmSuppressWildcards
    AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition) =
        {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(300)
            ) + fadeIn(animationSpec = tween(300))
        },

    exitTransition:
    (@JvmSuppressWildcards
    AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition) =
        {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(300)
            ) + fadeOut(animationSpec = tween(300))
        },

    popEnterTransition:
    (@JvmSuppressWildcards
    AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition) =
        {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(300)
            ) + fadeIn(animationSpec = tween(300))
        },

    popExitTransition:
    (@JvmSuppressWildcards
    AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition) =
        {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(300)
            ) + fadeOut(animationSpec = tween(300))
        },

    sizeTransform:
    (@JvmSuppressWildcards
    AnimatedContentTransitionScope<NavBackStackEntry>.() -> SizeTransform?)? =
        null,
    builder: NavGraphBuilder.() -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
        contentAlignment = contentAlignment,
        enterTransition = enterTransition,
        exitTransition = exitTransition,
        popEnterTransition = popEnterTransition,
        popExitTransition = popExitTransition,
        route = route,
        typeMap = typeMap,
        builder = builder
    )
}
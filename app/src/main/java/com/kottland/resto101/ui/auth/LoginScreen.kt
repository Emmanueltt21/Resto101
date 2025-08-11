package com.kottland.resto101.ui.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kottland.resto101.R
import com.kottland.resto101.ui.components.CustomTextField
import com.kottland.resto101.ui.components.PasswordTextField
import com.kottland.resto101.ui.components.PrimaryButton
import com.kottland.resto101.ui.components.TextButton
import com.kottland.resto101.ui.theme.Dimens
import com.kottland.resto101.ui.theme.Resto101Theme
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onNavigateToSignUp: () -> Unit,
    onNavigateToForgotPassword: () -> Unit,
    modifier: Modifier = Modifier
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    
    val focusManager = LocalFocusManager.current
    
    fun validateEmail(email: String): String? {
        return when {
            email.isBlank() -> "Email is required"
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "Invalid email format"
            else -> null
        }
    }
    
    fun validatePassword(password: String): String? {
        return when {
            password.isBlank() -> "Password is required"
            password.length < 6 -> "Password must be at least 6 characters"
            else -> null
        }
    }
    val coroutineScope = rememberCoroutineScope()
    fun handleLogin() {
        emailError = validateEmail(email)
        passwordError = validatePassword(password)
        if (emailError == null && passwordError == null) {
            isLoading = true
            // Simulate login process with delay
            coroutineScope.launch {
                kotlinx.coroutines.delay(1000)
                isLoading = false
                onLoginSuccess()
            }
        }
    }
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(Dimens.ScreenPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(Dimens.SpaceHuge))
        
        // App Logo
        Text(
            text = "🍽️",
            style = MaterialTheme.typography.displayLarge
        )
        
        Spacer(modifier = Modifier.height(Dimens.SpaceMedium))
        
        Text(
            text = "Welcome Back!",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        
        Spacer(modifier = Modifier.height(Dimens.SpaceSmall))
        
        Text(
            text = "Sign in to continue to Resto101",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(Dimens.SpaceExtraLarge))
        
        // Email Field
        CustomTextField(
            value = email,
            onValueChange = { 
                email = it
                emailError = null
            },
            label = "Email",
            placeholder = "Enter your email",
            leadingIcon = Icons.Default.Email,
            isError = emailError != null,
            errorMessage = emailError,
            imeAction = ImeAction.Next,
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            )
        )
        
        Spacer(modifier = Modifier.height(Dimens.SpaceMedium))
        
        // Password Field
        PasswordTextField(
            value = password,
            onValueChange = { 
                password = it
                passwordError = null
            },
            label = "Password",
            placeholder = "Enter your password",
            isError = passwordError != null,
            errorMessage = passwordError,
            imeAction = ImeAction.Done,
            keyboardActions = KeyboardActions(
                onDone = { 
                    focusManager.clearFocus()
                    handleLogin()
                }
            )
        )
        
        Spacer(modifier = Modifier.height(Dimens.SpaceSmall))
        
        // Forgot Password
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(
                text = "Forgot Password?",
                onClick = onNavigateToForgotPassword
            )
        }
        
        Spacer(modifier = Modifier.height(Dimens.SpaceLarge))
        
        // Login Button
        PrimaryButton(
            text = "Sign In",
            onClick = { handleLogin() },
            isLoading = isLoading,
            enabled = !isLoading
        )
        
        Spacer(modifier = Modifier.height(Dimens.SpaceLarge))
        
        // Sign Up Link
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Don't have an account? ",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            TextButton(
                text = "Sign Up",
                onClick = onNavigateToSignUp
            )
        }
        
        Spacer(modifier = Modifier.height(Dimens.SpaceLarge))
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    Resto101Theme {
        LoginScreen(
            onLoginSuccess = { },
            onNavigateToSignUp = { },
            onNavigateToForgotPassword = { }
        )
    }
}
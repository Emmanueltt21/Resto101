package com.kottland.resto101.ui.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kottland.resto101.ui.components.CustomTextField
import com.kottland.resto101.ui.components.PrimaryButton
import com.kottland.resto101.ui.components.SecondaryButton
import com.kottland.resto101.ui.theme.Dimens
import com.kottland.resto101.ui.theme.Resto101Theme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotPasswordScreen(
    onBackToLogin: () -> Unit,
    onResetSent: () -> Unit,
    modifier: Modifier = Modifier
) {
    var email by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    var isResetSent by remember { mutableStateOf(false) }
    
    val focusManager = LocalFocusManager.current
    
    fun validateEmail(email: String): String? {
        return when {
            email.isBlank() -> "Email is required"
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "Invalid email format"
            else -> null
        }
    }
    
    fun handleResetPassword() {
        emailError = validateEmail(email)
        
        if (emailError == null) {
            isLoading = true
            // Simulate reset password process
            kotlinx.coroutines.GlobalScope.launch {
                kotlinx.coroutines.delay(2000)
                isLoading = false
                isResetSent = true
            }
        }
    }
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // Top App Bar
        TopAppBar(
            title = { },
            navigationIcon = {
                IconButton(onClick = onBackToLogin) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        )
        
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.ScreenPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(Dimens.SpaceHuge))
            
            if (!isResetSent) {
                // Reset Password Form
                Text(
                    text = "🔒",
                    style = MaterialTheme.typography.displayLarge
                )
                
                Spacer(modifier = Modifier.height(Dimens.SpaceLarge))
                
                Text(
                    text = "Forgot Password?",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                
                Spacer(modifier = Modifier.height(Dimens.SpaceMedium))
                
                Text(
                    text = "Don't worry! Enter your email address and we'll send you a link to reset your password.",
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
                    label = "Email Address",
                    placeholder = "Enter your email",
                    leadingIcon = Icons.Default.Email,
                    isError = emailError != null,
                    errorMessage = emailError,
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Done,
                    keyboardActions = KeyboardActions(
                        onDone = { 
                            focusManager.clearFocus()
                            handleResetPassword()
                        }
                    )
                )
                
                Spacer(modifier = Modifier.height(Dimens.SpaceExtraLarge))
                
                // Reset Button
                PrimaryButton(
                    text = "Send Reset Link",
                    onClick = { handleResetPassword() },
                    isLoading = isLoading,
                    enabled = !isLoading
                )
                
                Spacer(modifier = Modifier.height(Dimens.SpaceMedium))
                
                // Back to Login
                SecondaryButton(
                    text = "Back to Login",
                    onClick = onBackToLogin
                )
            } else {
                // Success State
                Text(
                    text = "📧",
                    style = MaterialTheme.typography.displayLarge
                )
                
                Spacer(modifier = Modifier.height(Dimens.SpaceLarge))
                
                Text(
                    text = "Check Your Email",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                
                Spacer(modifier = Modifier.height(Dimens.SpaceMedium))
                
                Text(
                    text = "We've sent a password reset link to\n$email",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(Dimens.SpaceSmall))
                
                Text(
                    text = "Please check your email and follow the instructions to reset your password.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(Dimens.SpaceExtraLarge))
                
                // Resend Button
                SecondaryButton(
                    text = "Resend Email",
                    onClick = { 
                        isResetSent = false
                        email = ""
                    }
                )
                
                Spacer(modifier = Modifier.height(Dimens.SpaceMedium))
                
                // Back to Login
                PrimaryButton(
                    text = "Back to Login",
                    onClick = onBackToLogin
                )
            }
            
            Spacer(modifier = Modifier.height(Dimens.SpaceExtraLarge))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ForgotPasswordScreenPreview() {
    Resto101Theme {
        ForgotPasswordScreen(
            onBackToLogin = { },
            onResetSent = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ForgotPasswordScreenSuccessPreview() {
    Resto101Theme {
        var isResetSent by remember { mutableStateOf(true) }
        ForgotPasswordScreen(
            onBackToLogin = { },
            onResetSent = { }
        )
    }
}
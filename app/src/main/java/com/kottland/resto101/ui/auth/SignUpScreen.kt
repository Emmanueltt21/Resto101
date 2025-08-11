package com.kottland.resto101.ui.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kottland.resto101.ui.components.CustomTextField
import com.kottland.resto101.ui.components.PasswordTextField
import com.kottland.resto101.ui.components.PrimaryButton
import com.kottland.resto101.ui.components.TextButton
import com.kottland.resto101.ui.theme.Dimens
import com.kottland.resto101.ui.theme.Resto101Theme

@Composable
fun SignUpScreen(
    onSignUpSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit,
    modifier: Modifier = Modifier
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    
    var nameError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var phoneError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var confirmPasswordError by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    
    val focusManager = LocalFocusManager.current
    
    fun validateName(name: String): String? {
        return when {
            name.isBlank() -> "Name is required"
            name.length < 2 -> "Name must be at least 2 characters"
            else -> null
        }
    }
    
    fun validateEmail(email: String): String? {
        return when {
            email.isBlank() -> "Email is required"
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "Invalid email format"
            else -> null
        }
    }
    
    fun validatePhone(phone: String): String? {
        return when {
            phone.isBlank() -> "Phone number is required"
            phone.length < 10 -> "Phone number must be at least 10 digits"
            else -> null
        }
    }
    
    fun validatePassword(password: String): String? {
        return when {
            password.isBlank() -> "Password is required"
            password.length < 6 -> "Password must be at least 6 characters"
            !password.any { it.isDigit() } -> "Password must contain at least one number"
            !password.any { it.isLetter() } -> "Password must contain at least one letter"
            else -> null
        }
    }
    
    fun validateConfirmPassword(password: String, confirmPassword: String): String? {
        return when {
            confirmPassword.isBlank() -> "Please confirm your password"
            password != confirmPassword -> "Passwords do not match"
            else -> null
        }
    }
    
    fun handleSignUp() {
        nameError = validateName(name)
        emailError = validateEmail(email)
        phoneError = validatePhone(phone)
        passwordError = validatePassword(password)
        confirmPasswordError = validateConfirmPassword(password, confirmPassword)
        
        if (nameError == null && emailError == null && phoneError == null && 
            passwordError == null && confirmPasswordError == null) {
            isLoading = true
            // Simulate sign up process
            onSignUpSuccess()
        }
    }
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(Dimens.ScreenPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(Dimens.SpaceLarge))
        
        // App Logo
        Text(
            text = "🍽️",
            style = MaterialTheme.typography.displayLarge
        )
        
        Spacer(modifier = Modifier.height(Dimens.SpaceMedium))
        
        Text(
            text = "Create Account",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        
        Spacer(modifier = Modifier.height(Dimens.SpaceSmall))
        
        Text(
            text = "Join Resto101 and start ordering delicious food",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(Dimens.SpaceExtraLarge))
        
        // Name Field
        CustomTextField(
            value = name,
            onValueChange = { 
                name = it
                nameError = null
            },
            label = "Full Name",
            placeholder = "Enter your full name",
            leadingIcon = Icons.Default.Person,
            isError = nameError != null,
            errorMessage = nameError,
            imeAction = ImeAction.Next,
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            )
        )
        
        Spacer(modifier = Modifier.height(Dimens.SpaceMedium))
        
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
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next,
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            )
        )
        
        Spacer(modifier = Modifier.height(Dimens.SpaceMedium))
        
        // Phone Field
        CustomTextField(
            value = phone,
            onValueChange = { 
                phone = it
                phoneError = null
            },
            label = "Phone Number",
            placeholder = "Enter your phone number",
            leadingIcon = Icons.Default.Phone,
            isError = phoneError != null,
            errorMessage = phoneError,
            keyboardType = KeyboardType.Phone,
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
            placeholder = "Create a password",
            isError = passwordError != null,
            errorMessage = passwordError,
            imeAction = ImeAction.Next,
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            )
        )
        
        Spacer(modifier = Modifier.height(Dimens.SpaceSmall))
        
        // Password Requirements
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            )
        ) {
            Column(
                modifier = Modifier.padding(Dimens.SpaceMedium)
            ) {
                Text(
                    text = "Password Requirements:",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(Dimens.SpaceSmall))
                Text(
                    text = "• At least 6 characters\n• Contains at least one letter\n• Contains at least one number",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        
        Spacer(modifier = Modifier.height(Dimens.SpaceMedium))
        
        // Confirm Password Field
        PasswordTextField(
            value = confirmPassword,
            onValueChange = { 
                confirmPassword = it
                confirmPasswordError = null
            },
            label = "Confirm Password",
            placeholder = "Confirm your password",
            isError = confirmPasswordError != null,
            errorMessage = confirmPasswordError,
            imeAction = ImeAction.Done,
            keyboardActions = KeyboardActions(
                onDone = { 
                    focusManager.clearFocus()
                    handleSignUp()
                }
            )
        )
        
        Spacer(modifier = Modifier.height(Dimens.SpaceExtraLarge))
        
        // Sign Up Button
        PrimaryButton(
            text = "Create Account",
            onClick = { handleSignUp() },
            isLoading = isLoading,
            enabled = !isLoading
        )
        
        Spacer(modifier = Modifier.height(Dimens.SpaceLarge))
        
        // Login Link
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Already have an account? ",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            TextButton(
                text = "Sign In",
                onClick = onNavigateToLogin
            )
        }
        
        Spacer(modifier = Modifier.height(Dimens.SpaceLarge))
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    Resto101Theme {
        SignUpScreen(
            onSignUpSuccess = { },
            onNavigateToLogin = { }
        )
    }
}
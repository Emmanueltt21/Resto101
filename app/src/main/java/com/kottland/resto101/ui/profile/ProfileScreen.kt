package com.kottland.resto101.ui.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kottland.resto101.data.mock.StaticDataProvider
import com.kottland.resto101.ui.components.SecondaryButton
import com.kottland.resto101.ui.theme.Dimens
import com.kottland.resto101.ui.theme.Resto101Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onNavigateToOrderHistory: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier
) {
    val user = StaticDataProvider.currentUser
    var showLanguageDialog by remember { mutableStateOf(false) }
    var showLogoutDialog by remember { mutableStateOf(false) }
    var selectedLanguage by remember { mutableStateOf("English") }
    var isDarkTheme by remember { mutableStateOf(false) }
    
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(Dimens.ScreenPadding),
        verticalArrangement = Arrangement.spacedBy(Dimens.SpaceLarge)
    ) {
        // Profile Header
        item {
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dimens.SpaceLarge),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Profile Picture Placeholder
                    Surface(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape),
                        color = MaterialTheme.colorScheme.primaryContainer
                    ) {
                        Box(
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Profile Picture",
                                modifier = Modifier.size(40.dp),
                                tint = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(Dimens.SpaceMedium))
                    
                    Text(
                        text = user.name,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Text(
                        text = user.email,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    if (user.phone.isNotBlank()) {
                        Text(
                            text = user.phone,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
        
        // Account Section
        item {
            ProfileSection(
                title = "Account",
                items = listOf(
                    ProfileMenuItem(
                        icon = Icons.Default.Person,
                        title = "Edit Profile",
                        subtitle = "Update your personal information",
                        onClick = { /* TODO: Navigate to edit profile */ }
                    ),
                    ProfileMenuItem(
                        icon = Icons.Default.ShoppingCart,
                        title = "Order History",
                        subtitle = "View your past orders",
                        onClick = onNavigateToOrderHistory
                    ),
                    ProfileMenuItem(
                        icon = Icons.Default.LocationOn,
                        title = "Delivery Addresses",
                        subtitle = "Manage your saved addresses",
                        onClick = { /* TODO: Navigate to addresses */ }
                    ),
                    ProfileMenuItem(
                        icon = Icons.Default.AccountBox,
                        title = "Payment Methods",
                        subtitle = "Manage your payment options",
                        onClick = { /* TODO: Navigate to payment methods */ }
                    )
                )
            )
        }
        
        // Preferences Section
        item {
            ProfileSection(
                title = "Preferences",
                items = listOf(
                    ProfileMenuItem(
                        icon = Icons.Default.Settings,
                        title = "Language",
                        subtitle = selectedLanguage,
                        onClick = { showLanguageDialog = true }
                    ),
                    ProfileMenuItem(
                        icon = Icons.Default.Settings,
                        title = "Theme",
                        subtitle = if (isDarkTheme) "Dark" else "Light",
                        onClick = { isDarkTheme = !isDarkTheme },
                        hasSwitch = true,
                        switchChecked = isDarkTheme,
                        onSwitchChanged = { isDarkTheme = it }
                    ),
                    ProfileMenuItem(
                        icon = Icons.Default.Notifications,
                        title = "Notifications",
                        subtitle = "Manage notification preferences",
                        onClick = { /* TODO: Navigate to notifications */ }
                    )
                )
            )
        }
        
        // Support Section
        item {
            ProfileSection(
                title = "Support",
                items = listOf(
                    ProfileMenuItem(
                        icon = Icons.Default.Info,
                        title = "Help Center",
                        subtitle = "Get help and support",
                        onClick = { /* TODO: Navigate to help */ }
                    ),
                    ProfileMenuItem(
                        icon = Icons.Default.Send,
                        title = "Send Feedback",
                        subtitle = "Share your thoughts with us",
                        onClick = { /* TODO: Navigate to feedback */ }
                    ),
                    ProfileMenuItem(
                        icon = Icons.Default.Info,
                        title = "About",
                        subtitle = "App version and information",
                        onClick = { /* TODO: Navigate to about */ }
                    )
                )
            )
        }
        
        // Logout Button
        item {
            SecondaryButton(
                text = "Logout",
                onClick = { showLogoutDialog = true },
                modifier = Modifier.fillMaxWidth()
            )
        }
        
        // Bottom spacing
        item {
            Spacer(modifier = Modifier.height(Dimens.SpaceLarge))
        }
    }
    
    // Language Selection Dialog
    if (showLanguageDialog) {
        AlertDialog(
            onDismissRequest = { showLanguageDialog = false },
            title = {
                Text("Select Language")
            },
            text = {
                Column {
                    val languages = listOf("English", "Français")
                    languages.forEach { language ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = selectedLanguage == language,
                                onClick = { selectedLanguage = language }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = language,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showLanguageDialog = false
                        // TODO: Apply language change
                    }
                ) {
                    Text("Apply")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showLanguageDialog = false }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
    
    // Logout Confirmation Dialog
    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = {
                Text("Logout")
            },
            text = {
                Text("Are you sure you want to logout?")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showLogoutDialog = false
                        onLogout()
                    }
                ) {
                    Text("Logout")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showLogoutDialog = false }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun ProfileSection(
    title: String,
    items: List<ProfileMenuItem>,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(Dimens.SpaceLarge)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(Dimens.SpaceMedium))
            
            items.forEachIndexed { index, item ->
                ProfileMenuItemRow(
                    item = item
                )
                
                if (index < items.size - 1) {
                    Spacer(modifier = Modifier.height(Dimens.SpaceSmall))
                    Divider()
                    Spacer(modifier = Modifier.height(Dimens.SpaceSmall))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileMenuItemRow(
    item: ProfileMenuItem,
    modifier: Modifier = Modifier
) {
    Surface(
        onClick = item.onClick,
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = Dimens.SpaceSmall),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.width(Dimens.SpaceMedium))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )
                
                if (item.subtitle.isNotBlank()) {
                    Text(
                        text = item.subtitle,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            
            if (item.hasSwitch) {
                Switch(
                    checked = item.switchChecked,
                    onCheckedChange = item.onSwitchChanged
                )
            } else {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

data class ProfileMenuItem(
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val title: String,
    val subtitle: String = "",
    val onClick: () -> Unit,
    val hasSwitch: Boolean = false,
    val switchChecked: Boolean = false,
    val onSwitchChanged: (Boolean) -> Unit = {}
)

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    Resto101Theme {
        ProfileScreen(
            onNavigateToOrderHistory = { },
            onNavigateToSettings = { },
            onLogout = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileSectionPreview() {
    Resto101Theme {
        ProfileSection(
            title = "Account",
            items = listOf(
                ProfileMenuItem(
                    icon = Icons.Default.Person,
                    title = "Edit Profile",
                    subtitle = "Update your personal information",
                    onClick = { }
                ),
                ProfileMenuItem(
                    icon = Icons.Default.ShoppingCart,
                    title = "Order History",
                    subtitle = "View your past orders",
                    onClick = { }
                )
            ),
            modifier = Modifier.padding(16.dp)
        )
    }
}
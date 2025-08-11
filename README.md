# Resto101 - Restaurant Food Ordering App

A modern, full-featured Android restaurant food ordering app built with Jetpack Compose and Material 3 design system.

## Features

### 🔐 Authentication
- User login and registration
- Password reset functionality
- Form validation with real-time feedback

### 🏠 Core App Features
- **Splash Screen**: Animated app introduction
- **Home Screen**: Featured meals, categories, and popular items
- **Menu**: Browse meals by category with filtering and sorting
- **Meal Details**: Comprehensive meal information with nutrition facts
- **Shopping Cart**: Add/remove items with quantity management
- **Checkout**: Complete order flow with delivery and payment options
- **Order Management**: Order confirmation and history tracking
- **User Profile**: Account settings and preferences

### 🎨 Design & UX
- Material 3 design system
- Light and dark theme support
- Responsive UI with consistent spacing
- Smooth animations and transitions
- Modern, clean interface

### 🌍 Internationalization
- Multi-language support (English & French)
- Localized strings and content

## Tech Stack

- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM (Model-View-ViewModel)
- **Navigation**: Navigation Compose
- **Design System**: Material 3
- **Language**: Kotlin
- **Build System**: Gradle with Kotlin DSL

## Project Structure

```
app/src/main/java/com/kottland/resto101/
├── data/
│   ├── model/          # Data models (User, Meal, Order, etc.)
│   └── mock/           # Static mock data provider
├── navigation/         # Navigation setup and destinations
├── ui/
│   ├── auth/          # Authentication screens
│   ├── cart/          # Shopping cart functionality
│   ├── checkout/      # Checkout process
│   ├── components/    # Reusable UI components
│   ├── home/          # Home screen
│   ├── meal/          # Meal detail screen
│   ├── menu/          # Menu browsing
│   ├── order/         # Order management
│   ├── profile/       # User profile
│   ├── splash/        # Splash screen
│   └── theme/         # Theme, colors, typography
└── MainActivity.kt    # Main activity with navigation setup
```

## Getting Started

### Prerequisites
- Android Studio Hedgehog | 2023.1.1 or later
- JDK 8 or later
- Android SDK API level 24 or later

### Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd Resto101
   ```

2. **Open in Android Studio**
   - Launch Android Studio
   - Select "Open an existing project"
   - Navigate to the cloned directory and select it

3. **Build the project**
   ```bash
   ./gradlew build
   ```

4. **Run the app**
   - Connect an Android device or start an emulator
   - Click the "Run" button in Android Studio or use:
   ```bash
   ./gradlew installDebug
   ```

## App Flow

1. **Splash Screen** → **Login/Register**
2. **Home Screen** with categories and featured meals
3. **Browse Menu** by category with filters
4. **View Meal Details** and add to cart
5. **Review Cart** and proceed to checkout
6. **Complete Order** with delivery and payment info
7. **Order Confirmation** and tracking
8. **Order History** and profile management

## Key Components

### Data Models
- `User`: User account information
- `Meal`: Food item with details, nutrition, and pricing
- `Category`: Meal categorization
- `Cart` & `CartItem`: Shopping cart management
- `Order`: Order tracking and history
- `PaymentMethod`: Payment options

### UI Components
- `CustomTextField`: Styled input fields
- `PasswordTextField`: Secure password input
- `PrimaryButton` & `SecondaryButton`: Consistent button styles
- `MealCard`: Meal display component
- `CategoryCard`: Category navigation

### Navigation
- Centralized navigation with `AppNavigation`
- Bottom navigation for main screens
- Deep linking support for meal details and orders

## Mock Data

The app uses `StaticDataProvider` for demonstration purposes, including:
- Sample meals with detailed information
- User profiles and order history
- Categories and featured content
- Payment methods and addresses

## Customization

### Theming
Modify colors, typography, and shapes in:
- `ui/theme/Color.kt`
- `ui/theme/Type.kt`
- `ui/theme/Theme.kt`

### Localization
Add new languages by creating `values-{language}/strings.xml` files following the existing English and French examples.

### Mock Data
Update `StaticDataProvider.kt` to customize:
- Meal categories and items
- User information
- Order history
- Payment methods

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Acknowledgments

- Material 3 design guidelines
- Jetpack Compose documentation
- Android development best practices
- Unsplash for placeholder images

---

**Note**: This is a template/demo application with mock data. For production use, integrate with real backend services, implement proper authentication, and add payment processing.
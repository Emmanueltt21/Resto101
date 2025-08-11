package com.kottland.resto101.data.mock

import com.kottland.resto101.data.model.*
import java.util.Date
import java.util.Calendar

object StaticDataProvider {
    
    val categories = listOf(
        Category(
            id = "1",
            name = "Appetizers",
            description = "Start your meal with our delicious appetizers",
            imageUrl = "https://images.unsplash.com/photo-1541014741259-de529411b96a?w=400",
            isPopular = true
        ),
        Category(
            id = "2",
            name = "Main Courses",
            description = "Hearty and satisfying main dishes",
            imageUrl = "https://images.unsplash.com/photo-1546833999-b9f581a1996d?w=400",
            isPopular = true
        ),
        Category(
            id = "3",
            name = "Desserts",
            description = "Sweet treats to end your meal perfectly",
            imageUrl = "https://images.unsplash.com/photo-1551024506-0bccd828d307?w=400"
        ),
        Category(
            id = "4",
            name = "Beverages",
            description = "Refreshing drinks and hot beverages",
            imageUrl = "https://images.unsplash.com/photo-1544145945-f90425340c7e?w=400"
        ),
        Category(
            id = "5",
            name = "Salads",
            description = "Fresh and healthy salad options",
            imageUrl = "https://images.unsplash.com/photo-1512621776951-a57141f2eefd?w=400"
        ),
        Category(
            id = "6",
            name = "Pizza",
            description = "Wood-fired pizzas with fresh toppings",
            imageUrl = "https://images.unsplash.com/photo-1513104890138-7c749659a591?w=400",
            isPopular = true
        )
    )
    
    val meals = listOf(
        // Appetizers
        Meal(
            id = "1",
            name = "Bruschetta Trio",
            description = "Three varieties of our signature bruschetta with fresh tomatoes, basil, and mozzarella",
            price = 12.99,
            imageUrl = "https://images.unsplash.com/photo-1572695157366-5e585ab2b69f?w=400",
            categoryId = "1",
            ingredients = listOf("Bread", "Tomatoes", "Basil", "Mozzarella", "Olive Oil", "Garlic"),
            preparationTime = 10,
            isVegetarian = true,
            rating = 4.5f,
            reviewCount = 127,
            isPopular = true,
            nutritionInfo = NutritionInfo(calories = 280, protein = 8.5f, carbs = 35.2f, fat = 12.1f)
        ),
        Meal(
            id = "2",
            name = "Buffalo Wings",
            description = "Crispy chicken wings tossed in our signature buffalo sauce, served with celery and blue cheese",
            price = 14.99,
            imageUrl = "https://images.unsplash.com/photo-1608039755401-742074f0548d?w=400",
            categoryId = "1",
            ingredients = listOf("Chicken Wings", "Buffalo Sauce", "Celery", "Blue Cheese", "Carrots"),
            preparationTime = 15,
            isSpicy = true,
            rating = 4.7f,
            reviewCount = 203,
            nutritionInfo = NutritionInfo(calories = 420, protein = 28.3f, carbs = 8.1f, fat = 32.5f)
        ),
        
        // Main Courses
        Meal(
            id = "3",
            name = "Grilled Salmon",
            description = "Fresh Atlantic salmon grilled to perfection, served with roasted vegetables and lemon butter sauce",
            price = 26.99,
            imageUrl = "https://images.unsplash.com/photo-1467003909585-2f8a72700288?w=400",
            categoryId = "2",
            ingredients = listOf("Atlantic Salmon", "Asparagus", "Bell Peppers", "Lemon", "Butter", "Herbs"),
            preparationTime = 20,
            isGlutenFree = true,
            rating = 4.8f,
            reviewCount = 156,
            isFeatured = true,
            nutritionInfo = NutritionInfo(calories = 485, protein = 42.1f, carbs = 12.3f, fat = 28.7f)
        ),
        Meal(
            id = "4",
            name = "Ribeye Steak",
            description = "12oz prime ribeye steak cooked to your preference, served with garlic mashed potatoes",
            price = 34.99,
            imageUrl = "https://images.unsplash.com/photo-1546833999-b9f581a1996d?w=400",
            categoryId = "2",
            ingredients = listOf("Ribeye Steak", "Potatoes", "Garlic", "Butter", "Rosemary", "Salt", "Pepper"),
            preparationTime = 25,
            isGlutenFree = true,
            rating = 4.9f,
            reviewCount = 89,
            isFeatured = true,
            nutritionInfo = NutritionInfo(calories = 720, protein = 58.2f, carbs = 28.1f, fat = 42.8f)
        ),
        Meal(
            id = "5",
            name = "Chicken Parmesan",
            description = "Breaded chicken breast topped with marinara sauce and melted mozzarella, served over pasta",
            price = 22.99,
            imageUrl = "https://images.unsplash.com/photo-1632778149955-e80f8ceca2e8?w=400",
            categoryId = "2",
            ingredients = listOf("Chicken Breast", "Breadcrumbs", "Marinara Sauce", "Mozzarella", "Pasta", "Parmesan"),
            preparationTime = 18,
            rating = 4.6f,
            reviewCount = 234,
            isPopular = true,
            nutritionInfo = NutritionInfo(calories = 650, protein = 45.3f, carbs = 52.1f, fat = 28.9f)
        ),
        
        // Pizza
        Meal(
            id = "6",
            name = "Margherita Pizza",
            description = "Classic pizza with fresh mozzarella, tomato sauce, and basil on our wood-fired crust",
            price = 18.99,
            imageUrl = "https://images.unsplash.com/photo-1513104890138-7c749659a591?w=400",
            categoryId = "6",
            ingredients = listOf("Pizza Dough", "Tomato Sauce", "Fresh Mozzarella", "Basil", "Olive Oil"),
            preparationTime = 12,
            isVegetarian = true,
            rating = 4.4f,
            reviewCount = 312,
            isPopular = true,
            nutritionInfo = NutritionInfo(calories = 520, protein = 22.1f, carbs = 65.3f, fat = 18.7f)
        ),
        Meal(
            id = "7",
            name = "Pepperoni Supreme",
            description = "Loaded with pepperoni, Italian sausage, bell peppers, onions, and extra cheese",
            price = 24.99,
            imageUrl = "https://images.unsplash.com/photo-1565299624946-b28f40a0ca4b?w=400",
            categoryId = "6",
            ingredients = listOf("Pizza Dough", "Pepperoni", "Italian Sausage", "Bell Peppers", "Onions", "Mozzarella"),
            preparationTime = 15,
            rating = 4.7f,
            reviewCount = 187,
            nutritionInfo = NutritionInfo(calories = 680, protein = 28.5f, carbs = 58.2f, fat = 35.1f)
        ),
        
        // Salads
        Meal(
            id = "8",
            name = "Caesar Salad",
            description = "Crisp romaine lettuce with parmesan cheese, croutons, and our house-made Caesar dressing",
            price = 13.99,
            imageUrl = "https://images.unsplash.com/photo-1512621776951-a57141f2eefd?w=400",
            categoryId = "5",
            ingredients = listOf("Romaine Lettuce", "Parmesan Cheese", "Croutons", "Caesar Dressing", "Anchovies"),
            preparationTime = 8,
            isVegetarian = true,
            rating = 4.3f,
            reviewCount = 145,
            nutritionInfo = NutritionInfo(calories = 320, protein = 12.8f, carbs = 18.5f, fat = 22.3f)
        ),
        
        // Desserts
        Meal(
            id = "9",
            name = "Chocolate Lava Cake",
            description = "Warm chocolate cake with a molten center, served with vanilla ice cream",
            price = 8.99,
            imageUrl = "https://images.unsplash.com/photo-1551024506-0bccd828d307?w=400",
            categoryId = "3",
            ingredients = listOf("Dark Chocolate", "Butter", "Eggs", "Sugar", "Flour", "Vanilla Ice Cream"),
            preparationTime = 12,
            isVegetarian = true,
            rating = 4.8f,
            reviewCount = 267,
            isPopular = true,
            nutritionInfo = NutritionInfo(calories = 450, protein = 8.2f, carbs = 52.1f, fat = 24.8f)
        ),
        
        // Beverages
        Meal(
            id = "10",
            name = "Fresh Lemonade",
            description = "House-made lemonade with fresh lemons and mint",
            price = 4.99,
            imageUrl = "https://images.unsplash.com/photo-1544145945-f90425340c7e?w=400",
            categoryId = "4",
            ingredients = listOf("Fresh Lemons", "Sugar", "Water", "Mint", "Ice"),
            preparationTime = 3,
            isVegan = true,
            isVegetarian = true,
            isGlutenFree = true,
            rating = 4.2f,
            reviewCount = 98,
            nutritionInfo = NutritionInfo(calories = 120, protein = 0.2f, carbs = 32.1f, fat = 0.1f)
        )
    )
    
    val featuredMeals: List<Meal>
        get() = meals.filter { it.isFeatured }
    
    val popularMeals: List<Meal>
        get() = meals.filter { it.isPopular }
    
    fun getMealsByCategory(categoryId: String): List<Meal> {
        return meals.filter { it.categoryId == categoryId }
    }
    
    fun getMealById(mealId: String): Meal? {
        return meals.find { it.id == mealId }
    }
    
    fun getCategoryById(categoryId: String): Category? {
        return categories.find { it.id == categoryId }
    }
    
    val currentUser = User(
        id = "user_1",
        name = "John Doe",
        email = "john.doe@example.com",
        phone = "+1 (555) 123-4567",
        profileImageUrl = "https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?w=400",
        address = Address(
            street = "123 Main Street",
            city = "New York",
            state = "NY",
            zipCode = "10001",
            country = "USA"
        )
    )
    
    // Legacy property for backward compatibility
    val mockUser: User
        get() = currentUser
    
    val paymentMethods = listOf(
        PaymentMethod(
            id = "pm_1",
            type = PaymentType.CREDIT_CARD,
            name = "Visa Card",
            details = "**** **** **** 4242"
        ),
        PaymentMethod(
            id = "pm_2",
            type = PaymentType.DEBIT_CARD,
            name = "Debit Card",
            details = "**** **** **** 1234"
        ),
        PaymentMethod(
            id = "pm_3",
            type = PaymentType.PAYPAL,
            name = "PayPal",
            details = "john.doe@example.com"
        ),
        PaymentMethod(
            id = "pm_4",
            type = PaymentType.CASH,
            name = "Cash on Delivery",
            details = "Pay with cash upon delivery"
        )
    )
    
    val orderHistory: List<Order>
        get() {
            val calendar = Calendar.getInstance()
            return listOf(
                Order(
                    id = "ORD-2024-001",
                    items = listOf(
                        CartItem(
                            id = "cart_item_1",
                            meal = getMealById("3")!!,
                            quantity = 1,
                            specialInstructions = "Medium rare please"
                        ),
                        CartItem(
                            id = "cart_item_2",
                            meal = getMealById("9")!!,
                            quantity = 2,
                            specialInstructions = ""
                        )
                    ),
                    subtotal = 44.97,
                    deliveryFee = 2.99,
                    tax = 3.60,
                    total = 51.56,
                    status = OrderStatus.DELIVERED,
                    orderDate = calendar.apply { 
                        time = Date()
                        add(Calendar.DAY_OF_MONTH, -2) 
                    }.time,
                    estimatedDeliveryTime = "Delivered",
                    deliveryAddress = "123 Main Street, New York, NY 10001",
                    paymentMethod = paymentMethods[0],
                    customerName = currentUser.name,
                    customerPhone = currentUser.phone
                ),
                Order(
                    id = "ORD-2024-002",
                    items = listOf(
                        CartItem(
                            id = "cart_item_3",
                            meal = getMealById("6")!!,
                            quantity = 1,
                            specialInstructions = "Extra basil"
                        ),
                        CartItem(
                            id = "cart_item_4",
                            meal = getMealById("10")!!,
                            quantity = 2,
                            specialInstructions = ""
                        )
                    ),
                    subtotal = 28.97,
                    deliveryFee = 2.99,
                    tax = 2.32,
                    total = 34.28,
                    status = OrderStatus.OUT_FOR_DELIVERY,
                    orderDate = calendar.apply { 
                        time = Date()
                        add(Calendar.HOUR, -1) 
                    }.time,
                    estimatedDeliveryTime = "15-20 minutes",
                    deliveryAddress = "123 Main Street, New York, NY 10001",
                    paymentMethod = paymentMethods[1],
                    customerName = currentUser.name,
                    customerPhone = currentUser.phone
                ),
                Order(
                    id = "ORD-2024-003",
                    items = listOf(
                        CartItem(
                            id = "cart_item_5",
                            meal = getMealById("4")!!,
                            quantity = 1,
                            specialInstructions = "Well done"
                        ),
                        CartItem(
                            id = "cart_item_6",
                            meal = getMealById("8")!!,
                            quantity = 1,
                            specialInstructions = "Dressing on the side"
                        )
                    ),
                    subtotal = 48.98,
                    deliveryFee = 2.99,
                    tax = 3.92,
                    total = 55.89,
                    status = OrderStatus.DELIVERED,
                    orderDate = calendar.apply { 
                        time = Date()
                        add(Calendar.DAY_OF_MONTH, -5) 
                    }.time,
                    estimatedDeliveryTime = "Delivered",
                    deliveryAddress = "123 Main Street, New York, NY 10001",
                    paymentMethod = paymentMethods[2],
                    customerName = currentUser.name,
                    customerPhone = currentUser.phone
                )
            )
        }
    
    val mockOrders: List<Order>
        get() = orderHistory
}
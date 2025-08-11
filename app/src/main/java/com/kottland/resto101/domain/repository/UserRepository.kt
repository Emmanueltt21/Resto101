package com.kottland.resto101.domain.repository

import com.kottland.resto101.data.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getCurrentUser(): Flow<User?>
    suspend fun login(email: String, password: String): Result<User>
    suspend fun register(name: String, email: String, phone: String, password: String): Result<User>
    suspend fun logout()
    suspend fun updateProfile(user: User): Result<User>
    suspend fun resetPassword(email: String): Result<Boolean>
    fun isLoggedIn(): Flow<Boolean>
}
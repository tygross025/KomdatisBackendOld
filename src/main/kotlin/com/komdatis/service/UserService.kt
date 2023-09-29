package com.komdatis.service

import com.komdatis.model.User
import com.komdatis.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService(@Autowired private val userRepository: UserRepository) {

    fun getAllUsers(): List<User> = userRepository.findAll().toList()

    fun createUser(user: User): User = userRepository.save(user)

    fun getUserById(userId: Int): User? = userRepository.findById(userId).orElse(null)

    fun updateUserById(userId: Int, user: User): User? {
        val existingUser = userRepository.findById(userId).orElse(null) ?: return null

        val updatedUser = existingUser.copy(name = user.name, email = user.email)
        return userRepository.save(updatedUser)
    }

    fun deleteUserById(userId: Int): Boolean {
        return if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId)
            true
        } else {
            false
        }
    }
}

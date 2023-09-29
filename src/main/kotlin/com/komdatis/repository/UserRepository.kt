package com.komdatis.repository

import com.komdatis.model.User
import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<User, Int>
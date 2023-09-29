package com.komdatis.repository

import com.komdatis.model.Building
import org.springframework.data.repository.CrudRepository

interface BuildingRepository : CrudRepository<Building, Int>
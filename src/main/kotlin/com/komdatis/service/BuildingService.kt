package com.komdatis.service

import com.komdatis.model.Building
import com.komdatis.repository.BuildingRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class BuildingService(@Autowired private val buildingRepository: BuildingRepository) {

    fun getAllBuildings(): List<Building> = buildingRepository.findAll().toList()

    fun createBuilding(building: Building): Building = buildingRepository.save(building)

    fun getBuildingById(buildingId: Int): Building? = buildingRepository.findById(buildingId).orElse(null)

    fun updateBuildingById(buildingId: Int, building: Building): Building? {
        val existingBuilding = buildingRepository.findById(buildingId).orElse(null) ?: return null

        val updatedBuilding = existingBuilding.copy(
            firstName = building.firstName,
            lastName = building.lastName,
            address = building.address,
            livingSpace = building.livingSpace,
            warmth = building.warmth,
            warmWater = building.warmWater,
            heatedBasement = building.heatedBasement,
            apartments = building.apartments
        )
        return buildingRepository.save(updatedBuilding)
    }

    fun deleteBuildingById(buildingId: Int): Boolean {
        return if (buildingRepository.existsById(buildingId)) {
            buildingRepository.deleteById(buildingId)
            true
        } else {
            false
        }
    }
}

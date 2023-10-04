package com.komdatis.service

import com.komdatis.dto.BuildingDto
import com.komdatis.repository.BuildingRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class BuildingService(
    @Autowired private val buildingRepository: BuildingRepository,
    @Autowired private val buildingConverterService: BuildingConverterService
) {

    fun getAllBuildings(): List<BuildingDto> =
        buildingRepository.findAll().map { buildingConverterService.toDto(it) }

    fun createBuilding(buildingDto: BuildingDto): BuildingDto {
        val building = buildingConverterService.fromDto(buildingDto)
        building.warmWater.forEach { it.building = building }
        building.warmth.forEach { it.building = building }
        val savedBuilding = buildingRepository.save(building)
        return buildingConverterService.toDto(savedBuilding)
    }

    fun getBuildingById(buildingId: Int): BuildingDto? {
        val building = buildingRepository.findById(buildingId).orElse(null) ?: return null
        return buildingConverterService.toDto(building)
    }

    fun updateBuildingById(buildingId: Int, buildingDto: BuildingDto): BuildingDto? {
        val existingBuilding = buildingRepository.findById(buildingId).orElse(null) ?: return null
        existingBuilding.apply {
            this.firstName = buildingDto.firstName
            this.lastName = buildingDto.lastName
            this.address = buildingDto.address
            this.livingSpace = buildingDto.livingSpace
            this.warmth = buildingDto.warmth.map { buildingConverterService.fromDto(it, existingBuilding) }
            this.warmWater = buildingDto.warmWater.map { buildingConverterService.fromDto(it, existingBuilding) }
            this.heatedBasement = buildingDto.heatedBasement
            this.apartments = buildingDto.apartments
        }
        val savedBuilding = buildingRepository.save(existingBuilding)
        return buildingConverterService.toDto(savedBuilding)
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

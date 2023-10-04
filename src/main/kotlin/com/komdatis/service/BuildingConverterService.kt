package com.komdatis.service

import com.komdatis.dto.BuildingDto
import com.komdatis.dto.BuildingWarmWaterDto
import com.komdatis.dto.BuildingWarmthDto
import com.komdatis.model.Building
import com.komdatis.model.BuildingWarmWater
import com.komdatis.model.BuildingWarmth
import org.springframework.stereotype.Service

@Service
class BuildingConverterService {

    fun toDto(building: Building): BuildingDto {
        return BuildingDto(
            id = building.id,
            firstName = building.firstName,
            lastName = building.lastName,
            address = building.address,
            livingSpace = building.livingSpace,
            warmth = building.warmth.map { BuildingWarmthDto(it.id, it.value) },
            warmWater = building.warmWater.map { BuildingWarmWaterDto(it.id, it.value) },
            heatedBasement = building.heatedBasement,
            apartments = building.apartments
        )
    }

    fun fromDto(buildingDto: BuildingDto): Building {
        val building = Building(
            id = buildingDto.id,
            firstName = buildingDto.firstName,
            lastName = buildingDto.lastName,
            address = buildingDto.address,
            livingSpace = buildingDto.livingSpace,
            heatedBasement = buildingDto.heatedBasement,
            apartments = buildingDto.apartments,

        )
        // Handle the nested DTOs
        building.warmth = buildingDto.warmth.map { fromDto(it, building) }
        building.warmWater = buildingDto.warmWater.map { fromDto(it, building) }
        return building
    }

    fun toDto(warmth: BuildingWarmth): BuildingWarmthDto {
        return BuildingWarmthDto(
            id = warmth.id, value = warmth.value
        )
    }

    fun toDto(warmWater: BuildingWarmWater): BuildingWarmWaterDto {
        return BuildingWarmWaterDto(
            id = warmWater.id, value = warmWater.value
        )
    }

    fun fromDto(warmthDto: BuildingWarmthDto, building: Building): BuildingWarmth {
        return BuildingWarmth(
            id = warmthDto.id, building = building, value = warmthDto.value
        )
    }

    fun fromDto(warmWaterDto: BuildingWarmWaterDto, building: Building): BuildingWarmWater {
        return BuildingWarmWater(
            id = warmWaterDto.id, building = building, value = warmWaterDto.value
        )
    }
}

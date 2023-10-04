package com.komdatis.dto

data class BuildingDto(
    val id: Long? = null,
    val firstName: String,
    val lastName: String,
    val address: String,
    val livingSpace: Float,
    val warmth: List<BuildingWarmthDto> = emptyList(),
    val warmWater: List<BuildingWarmWaterDto> = emptyList(),
    val heatedBasement: Boolean,
    val apartments: Short
)
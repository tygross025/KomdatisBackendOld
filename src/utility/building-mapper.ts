import { BuildingDto } from "../dto/building-dto";
import { BuildingWarmWaterDto } from "../dto/building-warm-water-dto";
import { BuildingWarmthDto } from "../dto/building-warmth-dto";
import { Building } from "../model/building";
import { BuildingWarmWater } from "../model/building-warm-water";
import { BuildingWarmth } from "../model/building-warmth";

  
export const buildingFromDto = (buildingDto: BuildingDto): Building => {
    const building = {
        firstName: buildingDto.firstName,
        lastName: buildingDto.lastName,
        address: buildingDto.address,
        livingSpace: buildingDto.livingSpace,
        warmth: buildingDto.warmth.map(buildingWarmthFromDto),
        warmWater: buildingDto.warmWater.map(buildingWarmWaterFromDto),
        heatedBasement: buildingDto.heatedBasement,
        apartments: buildingDto.apartments,
    }
    return building
}

export const buildingToDto = (building: Building): BuildingDto => {
    const buildingDto = {
        firstName: building.firstName,
        lastName: building.lastName,
        address: building.address,
        livingSpace: building.livingSpace,
        warmth: building.warmth.map(buildingWarmthToDto),
        warmWater: building.warmWater.map(buildingWarmWaterToDto),
        heatedBasement: building.heatedBasement,
        apartments: building.apartments,
    }
    return buildingDto
}

export const buildingWarmthFromDto = (buildingWarmthDto: BuildingWarmthDto): BuildingWarmth => {
    const buildingWarmth = {
        buildingId: buildingWarmthDto.buildingId,
        value: buildingWarmthDto.value
    }
    return buildingWarmth
}

export const buildingWarmthToDto = (buildingWarmth: BuildingWarmth): BuildingWarmthDto => {
    const buildingWarmthDto = {
        buildingId: buildingWarmth.buildingId,
        value: buildingWarmth.value
    }
    return buildingWarmthDto
}

export const buildingWarmWaterFromDto = (buildingWarmWaterDto: BuildingWarmWaterDto): BuildingWarmWater => {
    const buildingWarmWater = {
        buildingId: buildingWarmWaterDto.buildingId,
        value: buildingWarmWaterDto.value
    }
    return buildingWarmWater
}

export const buildingWarmWaterToDto = (buildingWarmWater: BuildingWarmWater): BuildingWarmWaterDto => {
    const buildingWarmWaterDto = {
        buildingId: buildingWarmWater.buildingId,
        value: buildingWarmWater.value
    }
    return buildingWarmWaterDto
}
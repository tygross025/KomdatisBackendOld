package com.komdatis.controller

import com.komdatis.model.Building
import com.komdatis.service.BuildingService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/buildings")
class BuildingController(@Autowired private val buildingService: BuildingService) {

    @GetMapping("")
    fun getAllBuildings(): List<Building> = buildingService.getAllBuildings()

    @GetMapping("/{id}")
    fun getBuildingById(@PathVariable("id") buildingId: Int): ResponseEntity<Building> {
        val building = buildingService.getBuildingById(buildingId)
        return if (building != null) {
            ResponseEntity(building, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @PostMapping("")
    fun createBuilding(@RequestBody building: Building): ResponseEntity<Building> {
        val savedBuilding = buildingService.createBuilding(building)

        return if (savedBuilding != null) {
            ResponseEntity(savedBuilding, HttpStatus.CREATED)
        } else {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @PutMapping("/{id}")
    fun updateBuildingById(
        @PathVariable("id") buildingId: Int,
        @RequestBody building: Building
    ): ResponseEntity<Building> {
        val updatedBuilding = buildingService.updateBuildingById(buildingId, building)

        return if (updatedBuilding != null) {
            ResponseEntity(updatedBuilding, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @DeleteMapping("/{id}")
    fun deleteBuildingById(@PathVariable("id") buildingId: Int): ResponseEntity<Building> {
        val isDeleted = buildingService.deleteBuildingById(buildingId)
        return if (isDeleted) {
            ResponseEntity(HttpStatus.NO_CONTENT)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }
}

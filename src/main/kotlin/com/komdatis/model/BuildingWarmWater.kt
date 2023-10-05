package com.komdatis.model

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull

@Entity
@Table(name = "building_warm_water")
data class BuildingWarmWater(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "building_id")
    var building: Building,

    @NotNull
    @Column(name = "warm_water_value")
    var value: Float
)

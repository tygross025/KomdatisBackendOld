package com.komdatis.model

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull

@Entity
@Table(name = "building_warmth")
data class BuildingWarmth(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "building_id")
    var building: Building,

    @NotNull
    @Column(name = "warmth_value")
    var value: Float
)

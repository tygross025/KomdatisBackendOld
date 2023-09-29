package com.komdatis.model

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull

@Entity
@Table(name = "building_warmth")
data class BuildingWarmth(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @NotNull
    @ManyToOne
    @JoinColumn(name = "building_id")
    val building: Building,

    @NotNull
    @Column(name = "warmth_value")
    val value: Float
)

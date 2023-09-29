package com.komdatis.model

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull

@Entity
@Table(name = "buildings")
data class Building(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @NotNull
    @Column(name = "first_name")
    val firstName: String,

    @NotNull
    @Column(name = "last_name")
    val lastName: String,

    @NotNull
    @Column(name = "address")
    val address: String,

    @NotNull
    @Column(name = "living_space")
    val livingSpace: Float,

    @OneToMany(mappedBy = "building", cascade = [CascadeType.ALL], orphanRemoval = true)
    val warmth: List<BuildingWarmth> = emptyList(),

    @OneToMany(mappedBy = "building", cascade = [CascadeType.ALL], orphanRemoval = true)
    val warmWater: List<BuildingWarmWater> = emptyList(),

    @NotNull
    @Column(name = "heated_basement")
    val heatedBasement: Boolean,

    @NotNull
    @Column(name = "apartments")
    val apartments: Short
)

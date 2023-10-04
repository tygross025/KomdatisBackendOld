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
    var firstName: String,

    @NotNull
    @Column(name = "last_name")
    var lastName: String,

    @NotNull
    @Column(name = "address")
    var address: String,

    @NotNull
    @Column(name = "living_space")
    var livingSpace: Float,

    @OneToMany(mappedBy = "building", cascade = [CascadeType.ALL], orphanRemoval = true)
    var warmth: List<BuildingWarmth> = emptyList(),

    @OneToMany(mappedBy = "building", cascade = [CascadeType.ALL], orphanRemoval = true)
    var warmWater: List<BuildingWarmWater> = emptyList(),

    @NotNull
    @Column(name = "heated_basement")
    var heatedBasement: Boolean,

    @NotNull
    @Column(name = "apartments")
    var apartments: Short
)

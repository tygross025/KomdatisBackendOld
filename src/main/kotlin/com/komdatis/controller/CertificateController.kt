package com.komdatis.controller

import com.komdatis.service.CertificateService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/certificates")
class CertificateController(@Autowired private val certificateService: CertificateService) {

    @GetMapping("/building/{id}")
    fun generateEnergyCertificate(@PathVariable("id") buildingId: Int): ResponseEntity<String> {
        val energyCertificate = certificateService.generateEnergyCertificate(buildingId)
        return if (!energyCertificate.isNullOrEmpty()) {
            ResponseEntity(energyCertificate, HttpStatus.CREATED)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }
}

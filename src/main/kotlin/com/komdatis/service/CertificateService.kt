package com.komdatis.service

import com.komdatis.model.Building
import com.komdatis.repository.BuildingRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.io.File
import java.time.LocalDate

@Service
class CertificateService(@Autowired private val buildingRepository: BuildingRepository) {

    val filePath: String = System.getenv("ENERGY_CERTIFICATES_FILE_PATH") ?: "/default/path/certificates/"

    fun generateEnergyCertificate(buildingId: Int): String? {
        val existingBuilding = buildingRepository.findById(buildingId).orElse(null) ?: return null
        val endUsage = generateEnergyCertificate(existingBuilding)
        val builder = StringBuilder()

        builder.appendLine("Eigentümer: ${existingBuilding.firstName} ${existingBuilding.lastName}")
        builder.appendLine("Adresse: ${existingBuilding.address}")
        builder.appendLine("Wohnfläche: ${existingBuilding.livingSpace}")
        builder.appendLine("Keller beheizbar? ${if (existingBuilding.heatedBasement) "Ja" else "Nein"}")
        builder.appendLine("| Heizwärmeverbrauch (kWh) | Warmwasserverbrauch (kWh) |")
        builder.appendLine("|--------------------------|---------------------------|")
        for ((warmthVal, warmWaterVal) in existingBuilding.warmth.zip(existingBuilding.warmWater)) {
            builder.appendLine(
                "|                 ${
                    String.format(
                        "%.2f",
                        warmthVal
                    )
                } |                   ${String.format("%.2f", warmWaterVal)} |"
            )
        }
        builder.appendLine("|--------------------------|---------------------------|")
        builder.appendLine("Verbrauch pro m²: ${String.format("%.2f", endUsage)}")
        builder.appendLine("|----------------------------------------------------|")
        builder.appendLine("|   25    50    75   100   125   150   175   200 225 |")
        builder.appendLine("| A+    A     B     C     D     E     F     G     H  |")
        builder.appendLine("|----------------------------------------------------|")

        var i = 0.0f
        while (i <= endUsage) {
            builder.append(" ")
            i += 5.0f
        }
        builder.appendLine("^")
        builder.appendLine("Annahmen: Klimafaktor = 1 und die Immobilie hatte keinen Leerstand über den Messzeitraum.")

        val energyCertificate = builder.toString()
        if(energyCertificate.isNotEmpty()) {
            saveEnergyCertificateToFile(energyCertificate, existingBuilding.address)
        }

        return energyCertificate
    }

    private fun generateEnergyCertificate(building: Building): Float {
        val space = calculateUsageSpace(building)
        val energy = calculateEnergyUsage(building)
        val numberYears = building.warmth.size
        if (building.warmth.size != building.warmWater.size) {
            throw IllegalArgumentException("Unequal Number of Measurements")
        }
        return (energy / space) * (1.0f / numberYears)
    }

    private fun calculateUsageSpace(building: Building): Float {
        return if (building.apartments <= 2 && building.heatedBasement) {
            building.livingSpace * 1.35f
        } else {
            building.livingSpace * 1.2f
        }
    }

    private fun calculateEnergyUsage(building: Building): Float {
        val warmthSum = building.warmth.sumOf { it.value.toDouble() }.toFloat()
        val warmWaterSum = building.warmWater.sumOf { it.value.toDouble() }.toFloat()
        return warmthSum + warmWaterSum
    }

    private fun saveEnergyCertificateToFile(energyCertificate: String, address: String) {
        // File Name Otto-Hahn-Str_44227Dortmund_2023-09-20
        val file = File(
            filePath
                .plus(address.replace(", ", "_"))
                .plus("_")
                .plus(LocalDate.now())
                .trim()
        )
        file.writeText(energyCertificate)
    }
}

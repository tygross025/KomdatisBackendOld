package com.komdatis.service

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.komdatis.model.Building
import com.komdatis.dto.BuildingDto
import com.komdatis.repository.BuildingRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate

@Service
class BuildingService(
    @Autowired private val buildingRepository: BuildingRepository,
    @Autowired private val buildingConverterService: BuildingConverterService
) {

    @Value("\${connector.url}")
    private lateinit var connectorUrl: String
    @Value("\${connector.auth}")
    private lateinit var connectorAuth: String

    fun getAllBuildings(): List<BuildingDto> =
        buildingRepository.findAll().map { buildingConverterService.toDto(it) }

    fun createBuilding(buildingDto: BuildingDto): BuildingDto? {
        val building = buildingConverterService.fromDto(buildingDto)
        building.warmWater.forEach { it.building = building }
        building.warmth.forEach { it.building = building }
        
        val restTemplate = RestTemplate()

        val assetRequest = generateAssetRequest(building)
        val contractDefinitionRequest = generateContractDefinitionRequest(building)

        try {
            restTemplate.postForObject("$connectorUrl/assets", assetRequest, String::class.java)
            restTemplate.postForObject("$connectorUrl/contractdefinitions", contractDefinitionRequest, String::class.java)
        } catch (exception: RestClientException) {
            return null
        }
        val savedBuilding = buildingRepository.save(building)
        return buildingConverterService.toDto(savedBuilding)
    }

    fun getBuildingById(buildingId: Int): BuildingDto? {
        val building = buildingRepository.findById(buildingId).orElse(null) ?: return null
        return buildingConverterService.toDto(building)
    }

    fun updateBuildingById(buildingId: Int, buildingDto: BuildingDto): BuildingDto? {
        val existingBuilding = buildingRepository.findById(buildingId).orElse(null) ?: return null
        existingBuilding.apply {
            this.firstName = buildingDto.firstName
            this.lastName = buildingDto.lastName
            this.address = buildingDto.address
            this.livingSpace = buildingDto.livingSpace
            this.warmth = buildingDto.warmth.map { buildingConverterService.fromDto(it, existingBuilding) }
            this.warmWater = buildingDto.warmWater.map { buildingConverterService.fromDto(it, existingBuilding) }
            this.heatedBasement = buildingDto.heatedBasement
            this.apartments = buildingDto.apartments
        }
        val savedBuilding = buildingRepository.save(existingBuilding)
        return buildingConverterService.toDto(savedBuilding)
    }

    fun deleteBuildingById(buildingId: Int): Boolean {
        return if (buildingRepository.existsById(buildingId)) {
            buildingRepository.deleteById(buildingId)
            true
        } else {
            false
        }
    }

    /**
     * generates the request needed to create an asset on the connector.
     */
    private fun generateAssetRequest(building: Building): HttpEntity<JsonNode> {
        val headers = HttpHeaders()
        headers.add("x-api-key", connectorAuth)

        val jsonString = "{\n" +
                "            \"@context\": {\n" +
                "            \"edc\": \"https://w3id.org/edc/v0.0.1/ns/\"\n" +
                "        },\n" +
                "            \"edc:asset\": {\n" +
                "            \"@id\": \"" + building.address + "\",\n" +
                "            \"edc:properties\": {\n" +
                "            \"edc:name\": \"" + building.address + "\",\n" +
                "            \"edc:contenttype\": \"appliaction/json\",\n" +
                "            \"edc:version\": \"1.0\",\n" +
                "            \"edc:type\": \"HttpData\"\n" +
                "        }\n" +
                "        },\n" +
                "            \"edc:dataAddress\": {\n" +
                "            \"edc:type\": \"HttpData\",\n" +
                "            \"edc:properties\": {\n" +
                "            \"edc:name\": \"hi\",\n" +
                "            \"baseUrl\": \"https://baseurl\",\n" + //todo missing baseurl
                "            \"edc:type\": \"HttpData\"\n" +
                "        }\n" +
                "        }\n" +
                "        }"

        val mapper = ObjectMapper()
        val node = mapper.readTree(jsonString)
        return HttpEntity(node, headers)
    }

    /**
     * generates the request needed to create a contract definition on the connector.
     */
    private fun generateContractDefinitionRequest(building: Building): HttpEntity<JsonNode> {
        val headers = HttpHeaders()
        headers.add("x-api-key", connectorAuth)

        //todo change policy used
        val jsonString = "{\n" +
                "    \"@context\": {\n" +
                "      \"edc\": \"https://w3id.org/edc/v0.0.1/ns/\"\n" +
                "    },\n" +
                "    \"@id\": \"" + building.address + "\",\n" +
                "    \"accessPolicyId\": \"eu-restricted-policy\",\n" +
                "    \"contractPolicyId\": \"eu-restricted-policy\",\n" +
                "    \"assetsSelector\": [\n" +
                "      {\n" +
                "        \"operandLeft\": \"https://w3id.org/edc/v0.0.1/ns/id\",\n" +
                "        \"operator\": \"=\",\n" +
                "        \"operandRight\": \"" + building.address + "\"\n" +
                "      }\n" +
                "    ]\n" +
                "}"

        val mapper = ObjectMapper()
        val node = mapper.readTree(jsonString)
        return HttpEntity(node, headers)
    }
}

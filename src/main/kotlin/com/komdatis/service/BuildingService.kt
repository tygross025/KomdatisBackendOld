package com.komdatis.service

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.komdatis.model.Building
import com.komdatis.repository.BuildingRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate

@Service
class BuildingService(@Autowired private val buildingRepository: BuildingRepository) {

    @Value("\${connector.url}")
    private lateinit var connectorUrl: String
    @Value("\${connector.auth}")
    private lateinit var connectorAuth: String


    fun getAllBuildings(): List<Building> = buildingRepository.findAll().toList()

    fun createBuilding(building: Building): Building? {
        val restTemplate = RestTemplate()

        val assetRequest = generateAssetRequest(building)
        val contractDefinitionRequest = generateContractDefinitionRequest(building)

        try {
            restTemplate.postForObject("$connectorUrl/assets", assetRequest, String::class.java)
            restTemplate.postForObject("$connectorUrl/contractdefinitions", contractDefinitionRequest, String::class.java)
        } catch (exception: RestClientException) {
            return null
        }
        return buildingRepository.save(building)
    }

    fun getBuildingById(buildingId: Int): Building? = buildingRepository.findById(buildingId).orElse(null)

    fun updateBuildingById(buildingId: Int, building: Building): Building? {
        val existingBuilding = buildingRepository.findById(buildingId).orElse(null) ?: return null

        val updatedBuilding = existingBuilding.copy(
            firstName = building.firstName,
            lastName = building.lastName,
            address = building.address,
            livingSpace = building.livingSpace,
            warmth = building.warmth,
            warmWater = building.warmWater,
            heatedBasement = building.heatedBasement,
            apartments = building.apartments
        )
        return buildingRepository.save(updatedBuilding)
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

package com.example.amphibians

interface AmphibianRepository {
    suspend fun getAmphibians(): List<Amphibian>
}

class NetworkAmphibianRepository(
    private val servicio: AmphibianApiService
) : AmphibianRepository {
    override suspend fun getAmphibians(): List<Amphibian> = servicio.getAmphibians()
}
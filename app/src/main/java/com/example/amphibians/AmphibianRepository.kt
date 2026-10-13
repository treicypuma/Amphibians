package com.example.amphibians

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AmphibianRepository {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://android-kotlin-fun-mars-server.appspot.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val servicio = retrofit.create(AmphibianApiService::class.java)

    suspend fun getAmphibians(): List<Amphibian> = servicio.getAmphibians()
}
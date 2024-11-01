package com.example.catfacts.data.network.mappers

import com.example.catfacts.data.network.entities.CatFactApi
import com.example.catfacts.data.storage.entities.FactEntity
import javax.inject.Inject

class FactApiToEntityMapper @Inject constructor() : (CatFactApi) -> (FactEntity) {
    override fun invoke(factApi: CatFactApi): FactEntity {
        return FactEntity(
            text = factApi.text
        )
    }
}
package com.example.catfacts.data.network.mappers

import com.example.catfacts.data.network.entities.CatFact
import com.example.catfacts.data.storage.entities.FactEntity
import javax.inject.Inject

class FactApiToEntityMapper @Inject constructor() : (CatFact) -> (FactEntity) {
    override fun invoke(factApi: CatFact): FactEntity {
        return FactEntity(
            text = factApi.text
        )
    }
}
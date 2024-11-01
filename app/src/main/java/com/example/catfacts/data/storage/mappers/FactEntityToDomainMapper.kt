package com.example.catfacts.data.storage.mappers

import com.example.catfacts.data.storage.entities.FactEntity
import com.example.catfacts.domain.entities.Fact
import javax.inject.Inject

class FactEntityToDomainMapper @Inject constructor() : (FactEntity) -> (Fact){
    override fun invoke(factEntity: FactEntity): Fact {
        return Fact(
            text = factEntity.text
        )
    }
}
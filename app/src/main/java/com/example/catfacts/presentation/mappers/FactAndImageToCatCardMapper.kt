package com.example.catfacts.presentation.mappers

import com.example.catfacts.domain.models.entities.Fact
import com.example.catfacts.domain.models.entities.Image
import com.example.catfacts.presentation.model.CatCard
import javax.inject.Inject

class FactAndImageToCatCardMapper @Inject constructor() : (Int, Fact, Image) -> (CatCard){
    override fun invoke(index:Int,fact: Fact, image: Image): CatCard {
        return CatCard(
            index = index,
            text = fact.text,
            imageUrl = image.imageUrl
        )
    }
}
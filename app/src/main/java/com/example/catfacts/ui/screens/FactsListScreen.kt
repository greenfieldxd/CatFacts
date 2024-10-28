package com.example.catfacts.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.catfacts.ui.components.FactCard

@Composable
fun FactsListScreen(modifier: Modifier = Modifier, mainViewModel: MainViewModel) {

    val isLoading by remember { mainViewModel.isLoading }

    if (isLoading){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.onPrimary,
                strokeWidth = 4.dp,
                modifier = Modifier.align(Alignment.Center))
        }
    }
    else {
        val factsList = mainViewModel.facts

        LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
            items(items = factsList){ fact ->
                FactCard(name = "${fact.id + 1}",
                    text = fact.text,
                    imageUrl = fact.imageUrl,
                    expanded = fact.expanded.value,
                    onClick = { mainViewModel.expandFact(fact.id) },
                    modifier = modifier)
            }
        }
    }
}
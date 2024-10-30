package com.example.catfacts.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.catfacts.R
import com.example.catfacts.ui.components.FactCard
import com.example.catfacts.viewmodel.MainViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun FactsScreen(modifier: Modifier = Modifier, mainViewModel: MainViewModel) {

    val facts by mainViewModel.facts.collectAsState()
    val isLoadError by remember { mainViewModel.isLoadError }
    val isLoading by remember { mainViewModel.isLoading }
    val isRefreshing by remember { mainViewModel.isRefreshing }
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isRefreshing)

    if (isLoadError) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(stringResource(R.string.load_error))
        }
    } else {
        SwipeRefresh(state = swipeRefreshState, onRefresh = { mainViewModel.refreshFacts() }) {
            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.onPrimary,
                        strokeWidth = 4.dp,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            } else {
                LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
                    items(items = facts) { fact ->
                        FactCard(
                            name = "${fact.id + 1}",
                            text = fact.text,
                            imageUrl = fact.imageUrl,
                            modifier = modifier
                        )
                    }
                }
            }
        }
    }
}
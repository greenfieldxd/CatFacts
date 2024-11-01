package com.example.catfacts.presentation.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.catfacts.R
import com.example.catfacts.presentation.main.MainViewModel
import com.example.catfacts.presentation.state.ProgressState
import com.example.catfacts.presentation.state.ProgressState.Loading
import com.example.catfacts.presentation.ui.components.FactCard
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun FactsScreen(modifier: Modifier = Modifier, viewModel: MainViewModel) {

    val facts by viewModel.facts.collectAsState()
    val progressState by viewModel.progressState.collectAsState()
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = progressState is ProgressState.Refreshing)

    SwipeRefresh(state = swipeRefreshState,
        onRefresh = { viewModel.refreshFacts() }
    ) {
        when (progressState) {
            is Loading -> {
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

            }

            is ProgressState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(stringResource(R.string.load_error))
                    Button(
                        modifier = Modifier.padding(top = 10.dp),
                        onClick = { viewModel.refreshFacts() }
                    ) {
                        Text(stringResource(R.string.try_again_text), fontSize = 20.sp)
                    }
                }
            }

            is ProgressState.Success, is ProgressState.Refreshing -> {
                LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
                    items(items = facts) { fact ->
                        FactCard(
                            name = "${fact.index + 1}",
                            text = fact.text,
                            imageUrl = fact.imageUrl
                        )
                    }
                }

            }
        }
    }
}
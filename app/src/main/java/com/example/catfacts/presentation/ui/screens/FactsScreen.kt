package com.example.catfacts.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.catfacts.R
import com.example.catfacts.presentation.main.MainViewModel
import com.example.catfacts.presentation.state.ProgressState
import com.example.catfacts.presentation.ui.components.FactCard
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun FactsScreen(modifier: Modifier = Modifier, viewModel: MainViewModel) {

    val facts by viewModel.facts.collectAsState()
    val progressState by viewModel.progressState.collectAsState()
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = false)

    SwipeRefresh(state = swipeRefreshState,
        onRefresh = { viewModel.clearCards() }
    ) {
        when (progressState) {
            is ProgressState.Loading, is ProgressState.IsEmpty -> {
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
                Column (
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val message = (progressState as ProgressState.Error).message
                    Text(text = "${stringResource(R.string.load_error_text)} $message.",
                        textAlign = TextAlign.Center,
                        fontSize = 18.sp,
                        modifier = Modifier
                            .padding(20.dp)
                    )
                    Button(
                        modifier = Modifier.padding(top = 10.dp),
                        onClick = { viewModel.loadNewFacts() }
                    ) {
                        Text(stringResource(R.string.try_again_text), fontSize = 20.sp)
                    }
                }
            }

            is ProgressState.Success -> {
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
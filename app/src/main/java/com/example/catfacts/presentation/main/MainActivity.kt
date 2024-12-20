package com.example.catfacts.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.catfacts.presentation.ui.screens.FactsScreen
import com.example.catfacts.presentation.ui.screens.WelcomeScreen
import com.example.catfacts.presentation.ui.theme.CatFactsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        setContent {
            CatFactsTheme {
                Scaffold { innerPadding ->
                    MyApp(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun MyApp(modifier: Modifier = Modifier, viewModel: MainViewModel = hiltViewModel()) {
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }
    Surface (modifier =
        modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        val tweenDuration = 500
        Box(modifier = Modifier.fillMaxSize()) {
            AnimatedVisibility(
                visible = shouldShowOnboarding,
                enter = slideInHorizontally(initialOffsetX =  { fullWidth -> - fullWidth}, animationSpec = tween(durationMillis = tweenDuration)),
                exit = slideOutHorizontally(targetOffsetX =  { fullWidth -> - fullWidth}, animationSpec = tween(durationMillis = tweenDuration)),
            ) {
                WelcomeScreen(onContinueClicked = {
                    shouldShowOnboarding = false
                })
            }
            AnimatedVisibility (
                visible = !shouldShowOnboarding,
                enter = slideInHorizontally(initialOffsetX =  { fullWidth -> fullWidth}, animationSpec = tween(durationMillis = tweenDuration)),
                exit = slideOutHorizontally(targetOffsetX =  { fullWidth -> fullWidth}, animationSpec = tween(durationMillis = tweenDuration)),
            ) {
                FactsScreen(
                    modifier = modifier,
                    viewModel = viewModel
                )
            }
        }
    }
}
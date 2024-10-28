package com.example.catfacts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.catfacts.ui.screens.FactsListScreen
import com.example.catfacts.ui.screens.OnboardingScreen
import com.example.catfacts.ui.theme.CatFactsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CatFactsTheme {
                Scaffold { innerPadding ->
                    MyApp(modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun MyApp(modifier: Modifier = Modifier, mainViewModel: MainViewModel = viewModel()) {
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }

    Surface(modifier, color = MaterialTheme.colorScheme.background) {
        if (shouldShowOnboarding)
            OnboardingScreen(onContinueClicked = { shouldShowOnboarding = false })
        else FactsListScreen(mainViewModel = mainViewModel)
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 600)
@Composable()
private fun WelcomePreview() {
    CatFactsTheme {
        OnboardingScreen(onContinueClicked = { })
    }
}
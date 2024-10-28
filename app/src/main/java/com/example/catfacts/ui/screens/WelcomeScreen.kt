package com.example.catfacts.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.catfacts.R
import com.example.catfacts.ui.theme.CatFactsTheme

@Composable
fun WelcomeScreen(onContinueClicked: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(stringResource(R.string.welcome_text), fontSize = 18.sp, modifier = Modifier.padding(40.dp))
        Button(
            modifier = Modifier.padding(top = 10.dp),
            onClick = onContinueClicked
        ) {
            Text("Continue", fontSize = 20.sp)
        }
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 600)
@Composable()
private fun WelcomePreview() {
    CatFactsTheme {
        WelcomeScreen(onContinueClicked = { })
    }
}
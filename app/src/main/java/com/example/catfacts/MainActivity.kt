package com.example.catfacts

import android.os.Bundle
import android.view.RoundedCorner
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.catfacts.ui.theme.CatFactsTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import coil.size.Size

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
fun MyApp(modifier: Modifier = Modifier) {
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }

    Surface(modifier, color = MaterialTheme.colorScheme.background) {
        if (shouldShowOnboarding)
            OnboardingScreen(onContinueClicked = { shouldShowOnboarding = false })
        else CardsList()
    }
}

@Composable
fun OnboardingScreen(onContinueClicked: () -> Unit, modifier: Modifier = Modifier) {
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

@Composable
fun CardsList(modifier: Modifier = Modifier, mainViewModel: MainViewModel = viewModel()) {

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

@Composable
fun FactCard(name: String, text: String, expanded: Boolean, imageUrl: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    val cardColor by animateColorAsState(
        targetValue = if (expanded) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.primary,
        label = "cardColor"
    )
    val contentColor = MaterialTheme.colorScheme.onPrimary;

    Card(
        colors = CardDefaults.cardColors(containerColor = cardColor, contentColor = contentColor),
        modifier = modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        CardContent(
            name = name,
            text = text,
            imageUrl = imageUrl,
            expanded = expanded,
            onClick = onClick,
            modifier = modifier
        )
    }
}

@Composable
fun CardContent(
    name: String,
    text: String,
    imageUrl: String,
    expanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier
) {
    Column(
        modifier
            .fillMaxWidth()
            .padding(12.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Row {
            Row(
                modifier = modifier
                    .weight(1f)
                    .padding(12.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(imageUrl)
                        .size(Size(128, 128))
                        .scale(Scale.FILL)
                        .placeholder(R.drawable.picture)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .size(128.dp)
                        .padding(4.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop)
                Text(text = "Fact $name", modifier.padding(8.dp), fontSize = 36.sp, style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold))
            }

            IconButton(
                onClick = onClick
            ) {
                val icon = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore
                val contentDescription =
                    if (expanded) stringResource(R.string.show_less) else stringResource(R.string.show_more)
                Icon(imageVector = icon, contentDescription = contentDescription)
            }
        }

        if (expanded) Text(modifier = modifier.padding(12.dp), text = text, fontSize = 24.sp)
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 600)
@Composable()
private fun WelcomePreview() {
    CatFactsTheme {
        OnboardingScreen(onContinueClicked = { })
    }
}
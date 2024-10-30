package com.example.catfacts.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.catfacts.R
import com.example.catfacts.ui.theme.CatFactsTheme

@Composable
fun FactCard(
    name: String,
    text: String,
    imageUrl: String,
    modifier: Modifier = Modifier
) {
    var expanded = rememberSaveable { mutableStateOf(false) }
    val cardColor by animateColorAsState(
        targetValue = if (expanded.value) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.primary,
        label = "cardColor"
    )
    val contentColor = MaterialTheme.colorScheme.onPrimary;
    Card(
        colors = CardDefaults.cardColors(containerColor = cardColor, contentColor = contentColor),
        shape = RoundedCornerShape(40.dp),
        modifier = modifier.padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        CardContent(
            name = name,
            text = text,
            imageUrl = imageUrl,
            expanded = expanded.value,
            onClick = { expanded.value = !expanded.value },
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
            .padding(8.dp)
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
                    .padding(5.dp)
                    .clip(CircleShape),
                verticalAlignment = Alignment.Bottom
            ) {
                LoadableImage(
                    imageUrl = imageUrl,
                    modifier = Modifier
                        .size(128.dp)
                        .padding(8.dp)
                        .clip(CircleShape)
                )
                Text(
                    text = "Fact $name",
                    modifier = modifier.padding(8.dp),
                    fontSize = 26.sp,
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold)
                )
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
        if (expanded) {
            Text(
                modifier = modifier.padding(20.dp),
                text = text,
                fontSize = 20.sp
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 500, heightDp = 400)
@Composable
private fun FactCardPreview() {
    CatFactsTheme {
        FactCard(
            name = "Name",
            text = "Some text",
            imageUrl = "",
        )
    }
}
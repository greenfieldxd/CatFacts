package com.example.catfacts.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.catfacts.R

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
                LoadableImage(
                    imageUrl = imageUrl,
                    modifier = Modifier
                        .size(128.dp)
                        .padding(8.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
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
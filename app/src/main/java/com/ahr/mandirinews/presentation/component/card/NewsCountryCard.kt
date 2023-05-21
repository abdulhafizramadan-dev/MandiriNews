package com.ahr.mandirinews.presentation.component.card

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ahr.mandirinews.ui.theme.MandiriNewsTheme
import com.blongho.country_data.World

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsCountryCard(
    modifier: Modifier = Modifier,
    @DrawableRes flag: Int,
    title: String,
    languages: List<String> = emptyList(),
    onCountryClicked: () -> Unit = {},
    isSelected: Boolean
) {
    val countryLanguages = languages.joinToString(separator = ", ")
    val containerCardColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant
    val countryCardColor = CardDefaults.cardColors(
        containerColor = containerCardColor
    )
    val countryCardBorder = if (isSelected) BorderStroke(4.dp, MaterialTheme.colorScheme.primary) else null
    Card(
        modifier = modifier,
        onClick = onCountryClicked,
        colors = countryCardColor,
        border = countryCardBorder
    ) {
        Column {
            Image(
                painter = painterResource(id = flag),
                contentDescription = null,
                modifier = Modifier
                    .height(110.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(bottomEnd = 16.dp, bottomStart = 16.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .padding(horizontal = 8.dp),
                fontWeight = FontWeight.Bold
            )
            Text(
                text = countryLanguages,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .padding(horizontal = 8.dp),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNewsCountryCard() {
    MandiriNewsTheme {
        val country = World.getCountryFrom("au")
        Row(modifier = Modifier
            .padding(all = 16.dp)
            .fillMaxWidth()
        ) {
            NewsCountryCard(
                modifier = Modifier.weight(1f),
                flag = country.flagResource,
                title = country.name,
                languages = country.languages,
                isSelected = false,
            )
            Spacer(modifier = Modifier.width(16.dp))
            NewsCountryCard(
                modifier = Modifier.weight(1f),
                flag = country.flagResource,
                title = country.name,
                languages = country.languages,
                isSelected = true,
            )
        }
    }
}

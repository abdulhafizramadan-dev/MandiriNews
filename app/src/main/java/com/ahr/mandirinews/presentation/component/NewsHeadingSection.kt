package com.ahr.mandirinews.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ahr.mandirinews.R
import com.ahr.mandirinews.ui.theme.MandiriNewsTheme

@Composable
fun NewsHeadingSection(
    modifier: Modifier = Modifier,
    title: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(vertical = 8.dp)
            .then(modifier),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold
            ),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNewsHeadingSection() {
    MandiriNewsTheme {
        NewsHeadingSection(
            title = stringResource(R.string.label_headline_news),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

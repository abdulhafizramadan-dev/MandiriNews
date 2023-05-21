package com.ahr.mandirinews.presentation.component.topappbar

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Public
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ahr.mandirinews.R
import com.ahr.mandirinews.ui.theme.MandiriNewsTheme
import com.blongho.country_data.World

enum class NewsTopAppBarAction {
    Search, Locale, Notification
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsTopAppBar(
    modifier: Modifier = Modifier,
    title: String = stringResource(id = R.string.app_name),
    @DrawableRes navIcon: Int = R.drawable.logo_mandiri,
    @DrawableRes countryIcon: Int = World.getFlagOf("id"),
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
    onActionButtonClicked: (NewsTopAppBarAction) -> Unit = {}
) {
    TopAppBar(
        navigationIcon = {
            Row {
                Spacer(modifier = Modifier.width(8.dp))
                Image(
                    painter = painterResource(id = navIcon),
                    contentDescription = stringResource(R.string.desc_logo_bank_mandiri),
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
        },
        title = { Text(text = title) },
        actions = {
            NewsTopBarAction(
                onActionClicked = onActionButtonClicked,
                countryIcon = countryIcon
            )
        },
        scrollBehavior = scrollBehavior,
        modifier = modifier,
    )
}

@Composable
fun NewsTopBarAction(
    onActionClicked: (NewsTopAppBarAction) -> Unit = {},
    @DrawableRes countryIcon: Int = R.drawable.logo_mandiri

) {
    IconButton(onClick = { onActionClicked(NewsTopAppBarAction.Locale) }) {
        Box(modifier = Modifier.size(26.dp)) {
            Icon(
                imageVector = Icons.Outlined.Public,
                contentDescription = stringResource(R.string.desc_locale_icon),
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.Center)
            )
            Image(
                painter = painterResource(id = countryIcon),
                contentDescription = stringResource(R.string.desc_country_icon),
                modifier = Modifier
                    .clip(CircleShape)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                        shape = CircleShape
                    )
                    .size(16.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(all = 4.dp)
                    .align(Alignment.BottomEnd)
            )
        }
    }
    IconButton(onClick = { onActionClicked(NewsTopAppBarAction.Search) }) {
        Icon(
            imageVector = Icons.Outlined.Search,
            contentDescription = stringResource(R.string.desc_search_icon)
        )
    }
    IconButton(onClick = { onActionClicked(NewsTopAppBarAction.Notification) }) {
        Icon(
            imageVector = Icons.Outlined.Notifications,
            contentDescription = stringResource(R.string.desc_notification_icon)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun PreviewNewsTopAppBar() {
    MandiriNewsTheme {
        val indonesiaCountryFlag = World.getFlagOf("id")
        NewsTopAppBar(
            countryIcon = indonesiaCountryFlag
        )
    }
}

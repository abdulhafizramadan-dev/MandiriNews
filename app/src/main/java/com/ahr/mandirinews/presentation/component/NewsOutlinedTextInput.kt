package com.ahr.mandirinews.presentation.component

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ahr.mandirinews.R
import com.ahr.mandirinews.ui.theme.MandiriNewsTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsOutlinedTextInput(
    modifier: Modifier = Modifier,
    text: String,
    onTextChanged: (String) -> Unit,
    placeholder: String,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    maxLines: Int = Integer.MAX_VALUE,
    singleLine: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    focusRequester: FocusRequester = FocusRequester.Default
) {
    OutlinedTextField(
        value = text,
        onValueChange = onTextChanged,
        textStyle = MaterialTheme.typography.labelLarge,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,
            disabledBorderColor = Color.Transparent,
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        placeholder = {
            Text(
                text = placeholder,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
            )
        },
        shape = MaterialTheme.shapes.large,
        modifier = modifier
            .height(50.dp)
            .focusRequester(focusRequester),
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        maxLines = maxLines,
        singleLine = singleLine,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewNewOutlinedTextInput() {
    MandiriNewsTheme {
        var text by remember {
            mutableStateOf("")
        }
        NewsOutlinedTextInput(
            text = text,
            onTextChanged = { text = it },
            placeholder = stringResource(id = R.string.placeholder_search_news)
        )
    }
}
package com.ahr.mandirinews.presentation.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import com.ahr.mandirinews.ui.theme.MandiriNewsTheme

@Composable
fun NewsAlertDialog(
    modifier: Modifier = Modifier,
    title: String,
    message: String,
    confirmText: String,
    cancelText: String,
    onConfirm: () -> Unit = {},
    onCancel: () -> Unit = {}
) {
    Dialog(onDismissRequest = onCancel) {
        AlertDialog(
            onDismissRequest = onCancel,
            title = {
                Text(text = title)
            },
            text = {
                Text(text = message)
            },
            dismissButton = {
                TextButton(onClick = onCancel) {
                    Text(text = cancelText)
                }
            },
            confirmButton = {
                TextButton(onClick = onConfirm) {
                    Text(text = confirmText)
                }
            },
            modifier = modifier
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNewsAlertDialog() {
    MandiriNewsTheme {
        NewsAlertDialog(
            title = "Apakah kamu yakin ingin mengganti lokasi ke Indonesia?",
            message = "Semua berikan akan menyesuaikan dengan lokasi yang dipilih!",
            confirmText = "Confirm",
            cancelText = "Cancel"
        )
    }
}
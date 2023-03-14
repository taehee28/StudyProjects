package com.thk.firebaselogindemo.ui.components

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun LoadingDialog(
    onDismissRequest: () -> Unit,
    properties: DialogProperties,
) = Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties
    ) {
        CircularProgressIndicator()
    }
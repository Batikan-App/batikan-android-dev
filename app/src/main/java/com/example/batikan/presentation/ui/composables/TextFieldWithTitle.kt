package com.example.batikan.presentation.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.batikan.presentation.ui.theme.TextSecondary
import com.example.batikan.presentation.ui.theme.TextSmallMedium

@Composable
fun TextFieldWithTitle(
    title: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
    singleLine: Boolean = true,
    maxLines: Int = 1,
) {
    Column(modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = 30.dp)
    ) {
        Text(
            text = title,
            style = TextSmallMedium,
            color = TextSecondary,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
            modifier = Modifier.fillMaxWidth(),
            singleLine = singleLine,
            maxLines = maxLines
        )
    }
}
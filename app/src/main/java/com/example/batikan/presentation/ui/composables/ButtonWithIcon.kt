package com.example.batikan.presentation.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.batikan.presentation.ui.theme.TextPrimary

@Composable
fun ButtonWithIcon (
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    leadingIcon: ImageVector,
    trailingIocn: ImageVector,
    text: String
) {
    Button (
        onClick = onClick,
        shape = RectangleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = Color.Transparent
        ),
        contentPadding = PaddingValues(0.dp)
    ) {
        Row (
            modifier = modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row (horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = TextPrimary
                )
                Text(
                    text = text,
                    color = TextPrimary
                )
            }
            Icon(
                imageVector = trailingIocn,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = TextPrimary
            )

        }
    }
}
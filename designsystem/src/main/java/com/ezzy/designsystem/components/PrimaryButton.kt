package com.ezzy.designsystem.components

import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.ezzy.designsystem.utils.DpDimensions

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    label: String,
    onClick: () -> Unit = {},
    isEnabled: Boolean = true,
    disabledColor: Color,
    height: Dp = DpDimensions.Dp50
) {

    Button(
        onClick = { onClick() },
        modifier = modifier.height(height),
        enabled = isEnabled,
        colors = ButtonDefaults.buttonColors(
            disabledContainerColor = disabledColor,
            containerColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Text(
            text = label,
            color = Color.White,
            style = MaterialTheme.typography.titleMedium
        )
    }

}
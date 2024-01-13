package com.ezzy.designsystem.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ezzy.designsystem.utils.DpDimensions

@Composable
fun SocialButton(
    modifier: Modifier = Modifier,
    label: String,
    onClick: () -> Unit = {},
    @DrawableRes socialIcon: Int,
    height: Dp = DpDimensions.Dp50,
    cornerRadius: Dp = DpDimensions.Dp50,
    isEnabled: Boolean = true
) {
    Surface(
        modifier = modifier.height(height),
        onClick = { onClick() },
        shape = RoundedCornerShape(cornerRadius),
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outline),
        enabled = isEnabled,
        color = MaterialTheme.colorScheme.background
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = socialIcon), contentDescription = label + " icon",
                modifier = Modifier.size(DpDimensions.Dp20)
            )

            Spacer(modifier = Modifier.width(DpDimensions.Small))

            Text(
                text = label,
                color = MaterialTheme.colorScheme.inversePrimary,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}
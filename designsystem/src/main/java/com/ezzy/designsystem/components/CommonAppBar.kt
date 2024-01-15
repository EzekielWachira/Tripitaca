package com.ezzy.designsystem.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ezzy.designsystem.utils.DpDimensions

@Composable
fun CommonAppBar(
    modifier: Modifier = Modifier, onBackClicked: () -> Unit = {},
    backIcon: ImageVector = Icons.Outlined.ArrowBack
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(
                all = 16.dp
            )
    ) {

        IconButton(onClick = { onBackClicked() }) {
            Icon(
                imageVector = backIcon,
                contentDescription = "Back arrow",
                tint = MaterialTheme.colorScheme.inversePrimary
            )
        }

    }
}

@Composable
fun CommonAppBar(
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit = {},
    @DrawableRes backIcon: Int,
    iconTint: Color = MaterialTheme.colorScheme.inversePrimary
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(
                all = DpDimensions.Smallest
            )
    ) {

        IconButton(onClick = { onBackClicked() }) {
            Icon(
                painter = painterResource(id = backIcon),
                contentDescription = "Back arrow",
                tint = iconTint
            )
        }

    }
}
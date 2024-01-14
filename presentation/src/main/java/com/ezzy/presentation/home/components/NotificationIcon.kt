package com.ezzy.presentation.home.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ezzy.designsystem.utils.DpDimensions
import com.ezzy.presentation.R

@Composable
fun NotificationIcon(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {

    Box(
        modifier = modifier
            .clip(CircleShape)
            .border(width = 1.dp, color = MaterialTheme.colorScheme.outline, CircleShape)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Box(
//            modifier = Modifier.padding(DpDimensions.Small),
            contentAlignment = Alignment.Center
        ) {
            IconButton(onClick = onClick,
                modifier = Modifier.testTag("notification")) {
                Icon(
                    painter = painterResource(id = R.drawable.notification),
                    contentDescription = "Notification icon",
                    tint = MaterialTheme.colorScheme.inversePrimary
                )
            }
        }
    }

}
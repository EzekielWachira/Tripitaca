package com.ezzy.presentation.common

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.ezzy.presentation.R

@Composable
fun CategoryHeader(
    modifier: Modifier = Modifier,
    categoryTitle: String,
    onClick: () -> Unit = {}
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {

        Text(
            text = categoryTitle,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.inversePrimary,
            modifier = Modifier.weight(1f)
        )

        TextButton(onClick = onClick) {
            Text(
                text = stringResource(R.string.view_all),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimary,

                )
        }

//        IconButton(onClick = onClick) {
//            Icon(
//                painter = painterResource(id = R.drawable.right_arrow),
//                contentDescription = "Right arrow",
//                tint = MaterialTheme.colorScheme.onPrimary,
//                modifier = Modifier.size(DpDimensions.Dp24)
//
//            )
//        }

    }

}

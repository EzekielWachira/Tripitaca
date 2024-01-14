package com.ezzy.presentation.home.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.ezzy.designsystem.theme.TripitacaTheme
import com.ezzy.presentation.R
import com.ezzy.presentation.home.model.Filter
import com.ezzy.presentation.home.model.filters

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterItem(
    modifier: Modifier = Modifier,
    filter: Filter,
    onCheck: (isChecked: Boolean, filter: Filter) -> Unit
) {
    FilterChip(
        selected = filter.isSelected,
        onClick = {
            onCheck(!filter.isSelected, filter)
        },
        label = {
            Text(
                text = filter.title,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.inversePrimary
            )
        },
        leadingIcon = {
            if (filter.isSelected) {
                Icon(
                    painter = painterResource(id = R.drawable.check),
                    contentDescription = "Checked Icon",
                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                )
            } else {
                null
            }
        },
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.3f),
        ),
        shape = CircleShape
    )
}

@Preview
@Composable
fun FilterItemPreview() {
    TripitacaTheme {
        FilterItem(filter = filters[0]) { isChecked, filter ->

        }
    }
}
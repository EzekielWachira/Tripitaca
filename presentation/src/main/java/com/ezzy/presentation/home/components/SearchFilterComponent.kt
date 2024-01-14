package com.ezzy.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.ezzy.designsystem.utils.DpDimensions
import com.ezzy.presentation.R
import com.ezzy.presentation.common.SearchBar
import com.ezzy.quizzo.ui.common.state.SearchState

@Composable
fun SearchFilterComponent(
    modifier: Modifier = Modifier,
    searchState: SearchState,
    onValueChange: (query: String) -> Unit = {},
    onFilterClick: () -> Unit = {}
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(DpDimensions.Normal)
    ) {

        SearchBar(
            placeholder = stringResource(R.string.search),
            state = searchState,
            modifier = Modifier.weight(1f),
            onSearch = { query -> onValueChange(query) }
        )

        FilterButton(
            onClick = onFilterClick,

        )

    }

}


@Composable
fun FilterButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {

    Surface(
        modifier = modifier,
        onClick = onClick,
        color = MaterialTheme.colorScheme.onPrimary,
        shape = RoundedCornerShape(DpDimensions.Small)
    ) {

        Box(modifier = Modifier.padding(DpDimensions.Normal), contentAlignment = Alignment.Center) {
            Icon(
                painter = painterResource(id = R.drawable.filter),
                contentDescription = "Filter Icon",
                modifier = Modifier.size(DpDimensions.Dp20),
                tint = Color.White
            )
        }

    }

}
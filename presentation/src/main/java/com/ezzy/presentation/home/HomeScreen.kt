package com.ezzy.presentation.home

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ezzy.designsystem.components.CustomPadding
import com.ezzy.designsystem.theme.DarkBlue
import com.ezzy.designsystem.utils.DpDimensions
import com.ezzy.presentation.common.CategoryHeader
import com.ezzy.presentation.home.components.FeaturedItem
import com.ezzy.presentation.home.components.FilterBottomSheet
import com.ezzy.presentation.home.components.HomeTopBar
import com.ezzy.presentation.home.components.ListingItem
import com.ezzy.presentation.home.components.SearchFilterComponent
import com.ezzy.presentation.home.model.filters
import com.ezzy.presentation.home.model.listings
import com.ezzy.quizzo.ui.common.state.SearchState
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, isSystemInDarkMode: Boolean = isSystemInDarkTheme()) {

    val systemUiController = rememberSystemUiController()
    val useDarkIcons = !isSystemInDarkMode

    var searchState by remember {
        mutableStateOf(SearchState())
    }

    val bottomSheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()

    var isSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }

    var filters by remember {
        mutableStateOf(filters)
    }


    SideEffect {
        systemUiController.setSystemBarsColor(
            color = if (useDarkIcons)
                Color.White else DarkBlue,
            darkIcons = useDarkIcons
        )
    }

    Scaffold(
        topBar = {
            HomeTopBar(
                modifier = Modifier.fillMaxWidth(),
                onClick = { }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {

            CustomPadding(
                verticalPadding = DpDimensions.Small,
                horizontalPadding = DpDimensions.Normal
            ) {

                SearchFilterComponent(searchState = searchState,
                    modifier = Modifier.fillMaxWidth(),
                    onFilterClick = {
                        isSheetOpen = true
                    },
                    onValueChange = { query ->
                        searchState = searchState.copy(query = query)
                    })

            }

            LazyColumn(
                modifier = Modifier.weight(1f)){
                item {
                    Column {
                        CustomPadding(
                            verticalPadding = DpDimensions.Small,
                            horizontalPadding = DpDimensions.Normal
                        ) {
                            CategoryHeader(modifier = Modifier.fillMaxWidth(),
                                categoryTitle = "Featured",
                                onClick = {})
                        }

                        LazyRow(
                            contentPadding = PaddingValues(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(DpDimensions.Small)
                        ) {
                            items(listings) {
                                FeaturedItem(listing = it)
                            }
                        }


                        CustomPadding(
                            verticalPadding = DpDimensions.Small,
                            horizontalPadding = DpDimensions.Normal
                        ) {
                            CategoryHeader(modifier = Modifier.fillMaxWidth(),
                                categoryTitle = "Our Recommendations",
                                onClick = {})
                        }

                    }
                }


                items(listings) {
                    ListingItem(listing = it,
                        modifier = Modifier.padding(DpDimensions.Normal))
                }
            }



            if (isSheetOpen) {
                FilterBottomSheet(bottomSheetState = bottomSheetState,
                    onDismiss = { isSheetOpen = false },
                    filters = filters,
                    onFilterCheck = { isChecked, filter ->
                        filters[filters.indexOf(filter)].isSelected = isChecked
                    })
            }

        }
    }

}
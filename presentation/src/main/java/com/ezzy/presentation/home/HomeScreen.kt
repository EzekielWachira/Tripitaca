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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.ezzy.data.domain.model.Property
import com.ezzy.data.utils.StateWrapper
import com.ezzy.designsystem.components.CustomPadding
import com.ezzy.designsystem.theme.DarkBlue
import com.ezzy.designsystem.utils.DpDimensions
import com.ezzy.presentation.common.CategoryHeader
import com.ezzy.presentation.home.components.FeaturedItem
import com.ezzy.presentation.home.components.FilterBottomSheet
import com.ezzy.presentation.home.components.HomeTopBar
import com.ezzy.presentation.home.components.ListingItem
import com.ezzy.presentation.home.components.SearchFilterComponent
import com.ezzy.presentation.home.viewmodel.HomeViewModel
import com.ezzy.presentation.listing_detail.viewmodel.DetailViewModel
import com.ezzy.quizzo.ui.common.state.SearchState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch

private const val TAG = "HomeScreen"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController, isSystemInDarkMode: Boolean = isSystemInDarkTheme(),
    detailViewModel: DetailViewModel
) {

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


    val viewModel: HomeViewModel = hiltViewModel()
    val listingState by viewModel.listingState.collectAsStateWithLifecycle()
    val filters by viewModel.filters.collectAsStateWithLifecycle(initialValue = emptyList())


    SideEffect {
        systemUiController.setSystemBarsColor(
            color = if (useDarkIcons)
                Color.White else DarkBlue,
            darkIcons = useDarkIcons
        )
    }

    LaunchedEffect(key1 = listingState.state) {
        viewModel.getListings()
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
                modifier = Modifier.weight(1f)
            ) {
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

                        when (listingState.state) {
                            is StateWrapper.Loading -> {
                                CircularProgressIndicator()
                            }

                            is StateWrapper.Success -> {
                                LazyRow(
                                    contentPadding = PaddingValues(horizontal = 16.dp),
                                    horizontalArrangement = Arrangement.spacedBy(DpDimensions.Small)
                                ) {
                                    items(
                                        (listingState.state as StateWrapper.Success<List<Property>>)
                                            .data.take(5)
                                    ) {
                                        FeaturedItem(listing = it,
                                            onClick = { _ ->
                                                detailViewModel.setProperty(it)
                                                navController.navigate("details/{${it.id}}")
                                            })
                                    }
                                }
                            }

                            is StateWrapper.Failure -> {
                                Text(text = (listingState.state as StateWrapper.Failure).errorMessage)
                            }

                            is StateWrapper.Empty -> {}
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


                when (listingState.state) {
                    is StateWrapper.Loading -> {
                        item {
                            CircularProgressIndicator()
                        }
                    }

                    is StateWrapper.Success -> {
                        items(
                            (listingState.state as StateWrapper.Success<List<Property>>).data
                        ) {
                            ListingItem(
                                listing = it,
                                modifier = Modifier.padding(DpDimensions.Normal),
                                onClick = { _ ->
                                    detailViewModel.setProperty(it)
                                    navController.navigate("details/{${it.id}}")
                                }
                            )
                        }
                    }

                    is StateWrapper.Failure -> {
                        item {
                            Text(text = (listingState.state as StateWrapper.Failure).errorMessage)
                        }
                    }

                    is StateWrapper.Empty -> {}
                }


            }



            if (isSheetOpen) {
                FilterBottomSheet(bottomSheetState = bottomSheetState,
                    onDismiss = { isSheetOpen = false },
                    filterss = filters,
                    onFilterCheck = { isChecked, filter ->
                        coroutineScope.launch {
                            viewModel.applyFilter(
                                filter = filter.apply {
                                    isSelected = isChecked
                                },
                                filters
                            )
                        }
                    })
            }

        }
    }

}
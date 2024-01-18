package com.ezzy.presentation.view_all

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.ezzy.data.domain.model.Property
import com.ezzy.data.utils.StateWrapper
import com.ezzy.designsystem.components.CommonAppBarWithTitle
import com.ezzy.designsystem.theme.DarkBlue
import com.ezzy.designsystem.utils.DpDimensions
import com.ezzy.presentation.R
import com.ezzy.presentation.home.components.ListingItem
import com.ezzy.presentation.home.viewmodel.HomeViewModel
import com.ezzy.presentation.listing_detail.ErrorComponent
import com.ezzy.presentation.listing_detail.viewmodel.DetailViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun ViewAllScreen(
    navController: NavController,
    isSystemInDarkMode: Boolean = isSystemInDarkTheme(),
    detailViewModel: DetailViewModel
) {

    val systemUiController = rememberSystemUiController()
    val useDarkIcons = !isSystemInDarkMode

    val viewModel: HomeViewModel = hiltViewModel()
    val listingState by viewModel.listingState.collectAsStateWithLifecycle()

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
            CommonAppBarWithTitle(
                title = stringResource(R.string.all_listings),
                backIcon = R.drawable.back,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            when (listingState.state) {
                is StateWrapper.Loading -> {
                    CircularProgressIndicator()
                }

                is StateWrapper.Success -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
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
                }

                is StateWrapper.Failure -> {
                    ErrorComponent(errorMessage = (listingState.state as StateWrapper.Failure).errorMessage)
                }

                is StateWrapper.Empty -> {}
            }
        }
    }
}
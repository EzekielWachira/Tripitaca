package com.ezzy.presentation.listing_detail

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ehsanmsz.mszprogressindicator.progressindicator.BallSpinFadeLoaderProgressIndicator
import com.ezzy.data.domain.model.Property
import com.ezzy.data.utils.StateWrapper
import com.ezzy.designsystem.components.CommonAppBar
import com.ezzy.designsystem.components.CustomPadding
import com.ezzy.designsystem.components.PrimaryButton
import com.ezzy.designsystem.theme.DarkBlue
import com.ezzy.designsystem.theme.Grey74
import com.ezzy.designsystem.utils.DpDimensions
import com.ezzy.presentation.R
import com.ezzy.presentation.listing_detail.components.PagerIndicator
import com.ezzy.presentation.listing_detail.viewmodel.DetailViewModel
import com.ezzy.presentation.utils.formatTimeToSmallDate
import com.ezzy.presentation.utils.smartTruncate
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DetailScreen(
    navController: NavController,
    propertyId: String?,
    isSystemInDarkMode: Boolean = isSystemInDarkTheme(),
    viewModel: DetailViewModel
) {

    val systemUiController = rememberSystemUiController()
    val useDarkIcons = !isSystemInDarkMode


//    val viewModel: DetailViewModel = hiltViewModel()
    val state by viewModel.singleListingState.collectAsStateWithLifecycle()
    val property by viewModel.property.collectAsStateWithLifecycle()

    SideEffect {
        systemUiController.setSystemBarsColor(
            color = if (useDarkIcons)
                Color.White else DarkBlue,
            darkIcons = useDarkIcons
        )
    }

    LaunchedEffect(key1 = state.state) {
        viewModel.getListing(propertyId!!)
    }


    Scaffold(
        topBar = {
            CommonAppBar(backIcon = R.drawable.back,
                modifier = Modifier.fillMaxWidth(),
                onBackClicked = {
                    navController.popBackStack()
                })
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()


        ) {

            if (property != null) {
                property?.let { property ->
                    Column(modifier = Modifier.fillMaxSize()) {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .verticalScroll(rememberScrollState())
                        ) {


                            Spacer(modifier = Modifier.height(DpDimensions.Small))

                            ImagesPager(
                                images = property.photos,
                                pagerState = rememberPagerState {
                                    property.photos.size
                                })

                            Spacer(modifier = Modifier.height(DpDimensions.Dp20))

                            Column(
                                modifier = Modifier
                                    .padding(DpDimensions.Normal)
                                    .fillMaxWidth()
                            ) {

                                InfoSection(property = property)

                                Spacer(modifier = Modifier.height(DpDimensions.Dp30))

                                Divider(color = MaterialTheme.colorScheme.outline)

                                Spacer(modifier = Modifier.height(DpDimensions.Dp20))
                                OwnerSection(
                                    property = property,
                                    modifier = Modifier.fillMaxWidth()
                                )
                                Spacer(modifier = Modifier.height(DpDimensions.Dp20))
                                DescriptionSection(
                                    property = property,
                                    modifier = Modifier.fillMaxWidth()
                                )
                                Spacer(modifier = Modifier.height(DpDimensions.Dp20))
                                FacilitiesSection(
                                    property = property,
                                    modifier = Modifier.fillMaxWidth()
                                )
                                Spacer(modifier = Modifier.height(DpDimensions.Dp20))
                                LocationSection(
                                    modifier = Modifier.fillMaxWidth(),
                                    property = property
                                )

                                Spacer(modifier = Modifier.height(DpDimensions.Dp30))
                                AvailabilitySection(
                                    modifier = Modifier.fillMaxWidth(),
                                    property = property
                                )


                            }
                        }

                        BottomSection(property = property, onClick = {
                            navController.navigate("booking/${property.id}")
                        })
                    }
                }
            } else {
                ErrorComponent(errorMessage = "Error Loading Listing")
            }

//            when (state.state) {
//                is StateWrapper.Loading -> {
//                    LoadingComponent()
//                }
//
//                is StateWrapper.Success -> {
//
//
//                    if ((state.state as StateWrapper.Success<Property?>).data != null) {
//                        (state.state as StateWrapper.Success<Property?>).data?.let { property ->
//                            Column(modifier = Modifier.fillMaxSize()) {
//                                Column(
//                                    modifier = Modifier
//                                        .weight(1f)
//                                        .verticalScroll(rememberScrollState())
//                                ) {
//
//
//                                    Spacer(modifier = Modifier.height(DpDimensions.Small))
//
//                                    ImagesPager(
//                                        images = property.photos,
//                                        pagerState = rememberPagerState {
//                                            property.photos.size
//                                        })
//
//                                    Spacer(modifier = Modifier.height(DpDimensions.Dp20))
//
//                                    Column(
//                                        modifier = Modifier
//                                            .padding(DpDimensions.Normal)
//                                            .fillMaxWidth()
//                                    ) {
//
//                                        InfoSection(property = property)
//
//                                        Spacer(modifier = Modifier.height(DpDimensions.Dp30))
//
//                                        Divider(color = MaterialTheme.colorScheme.outline)
//
//                                        Spacer(modifier = Modifier.height(DpDimensions.Dp20))
//                                        OwnerSection(
//                                            property = property,
//                                            modifier = Modifier.fillMaxWidth()
//                                        )
//                                        Spacer(modifier = Modifier.height(DpDimensions.Dp20))
//                                        DescriptionSection(
//                                            property = property,
//                                            modifier = Modifier.fillMaxWidth()
//                                        )
//                                        Spacer(modifier = Modifier.height(DpDimensions.Dp20))
//                                        FacilitiesSection(
//                                            property = property,
//                                            modifier = Modifier.fillMaxWidth()
//                                        )
//                                        Spacer(modifier = Modifier.height(DpDimensions.Dp20))
//                                        LocationSection(
//                                            modifier = Modifier.fillMaxWidth(),
//                                            property = property
//                                        )
//                                    }
//                                }
//
//                                BottomSection(property = property, onClick = {
//
//                                })
//                            }
//                        }
//                    } else {
//                        ErrorComponent(errorMessage = "Error Loading Listing")
//                    }
//                }
//
//                is StateWrapper.Failure -> {
//                    ErrorComponent(errorMessage = (state.state as StateWrapper.Failure).errorMessage)
//                }
//
//                is StateWrapper.Empty -> {}
//            }


        }

    }


}


@Composable
fun LoadingComponent() {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        BallSpinFadeLoaderProgressIndicator(
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.size(50.dp)
        )

    }

}


@Composable
fun ErrorComponent(errorMessage: String) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(DpDimensions.Dp20),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Surface(
            modifier = Modifier.fillMaxWidth(),
            border = BorderStroke(width = 1.dp, color = Color.Red),
            shape = RoundedCornerShape(DpDimensions.Small)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(DpDimensions.Dp20)
            ) {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.inversePrimary,
                    style = MaterialTheme.typography.bodyMedium
                )

            }
        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AvailabilitySection(
    modifier: Modifier = Modifier,
    property: Property
) {

    val state = rememberDatePickerState(
        initialSelectedDateMillis = null,
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return !property.booked_dates.contains(utcTimeMillis.formatTimeToSmallDate()) &&
                        utcTimeMillis > System.currentTimeMillis()
            }
        }
    )

    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.availability),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.inversePrimary
        )

        Spacer(modifier = Modifier.height(DpDimensions.Normal))

        DatePicker(
            title = null,
            headline = null,
            showModeToggle = false,
            state = state,
            colors = DatePickerDefaults.colors(
                containerColor = MaterialTheme.colorScheme.background,
                weekdayContentColor = MaterialTheme.colorScheme.onPrimary,
                dayContentColor = MaterialTheme.colorScheme.inversePrimary,
                selectedDayContainerColor = MaterialTheme.colorScheme.onPrimary,
                todayContentColor = MaterialTheme.colorScheme.onPrimary,
                selectedDayContentColor = Color.White,
                todayDateBorderColor = MaterialTheme.colorScheme.onPrimary
            )
        )

    }
}

@Composable
fun LocationSection(
    modifier: Modifier = Modifier,
    property: Property
) {

    val latLng = LatLng(property.geolocation.lat, property.geolocation.lon)
    val cameraPosition = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(latLng, 10f)
    }

    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.location),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.inversePrimary
        )

        Spacer(modifier = Modifier.height(DpDimensions.Normal))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(DpDimensions.Small))
        ) {

            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPosition
            ) {

                Marker(
                    state = MarkerState(position = latLng),
                    title = property.name,
                    snippet = "Property Location"
                )

            }

        }


    }
}


@Composable
fun BottomSection(
    property: Property,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {

    Surface(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.background,
        shadowElevation = DpDimensions.Small
    ) {
        Column {
            Divider(color = MaterialTheme.colorScheme.outline)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(DpDimensions.Normal),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(DpDimensions.Smallest)) {
                    Text(
                        text = "Price",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.inversePrimary
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = stringResource(id = R.string.currency, property.price.toFloat()),
                            style = MaterialTheme.typography.headlineLarge,
                            color = MaterialTheme.colorScheme.inversePrimary
                        )

                        Text(
                            text = "/ night",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.inversePrimary
                        )
                    }
                }

                Spacer(modifier = Modifier.width(DpDimensions.Dp100))


                PrimaryButton(
                    label = "Book Now",
                    disabledColor = Grey74,
                    modifier = Modifier.weight(1f),
                    onClick = onClick
                )


            }

        }

    }

}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FacilitiesSection(
    property: Property,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.amenities),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.inversePrimary
        )

        Spacer(modifier = Modifier.height(DpDimensions.Normal))

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(DpDimensions.Dp20),
            verticalArrangement = Arrangement.spacedBy(DpDimensions.Normal)
        ) {
            property.amenities?.filter {
                !it.contains("missing", ignoreCase = true)
            }?.forEach { amenity ->
                FacilityItem(icon = amenity.getIcon(), title = amenity)
            }
        }


    }
}

fun String.getIcon(): Int {
    return when {
        this.contains("parking", ignoreCase = true) -> R.drawable.parking
        this.contains("internet", ignoreCase = true) -> R.drawable.wifi
        this.contains("buzzer", ignoreCase = true) -> R.drawable.buzzer
        this.contains("elevator", ignoreCase = true) -> R.drawable.elevator
        this.contains("hanger", ignoreCase = true) -> R.drawable.hanger
        this.contains("heating", ignoreCase = true) -> R.drawable.heating
        this.contains("iron", ignoreCase = true) -> R.drawable.iron
        this.contains("kitchen", ignoreCase = true) -> R.drawable.kitchen
        this.contains("tv", ignoreCase = true) -> R.drawable.tv
        this.contains("air condition", ignoreCase = true) -> R.drawable.airconditioning
        this.contains("dryer", ignoreCase = true) -> R.drawable.hair_dryer
        else -> R.drawable.placeholder_ic
    }
}


@Composable
fun FacilityItem(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    title: String,
    iconSize: Dp = DpDimensions.Dp20
) {

    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .clip(CircleShape)
                .background(
                    MaterialTheme.colorScheme.onPrimary.copy(alpha = .3f),
                    CircleShape
                )
        ) {
            Box(
                modifier = Modifier.padding(DpDimensions.Normal),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = icon), contentDescription = null,
                    modifier = Modifier.size(iconSize),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

        Spacer(modifier = Modifier.height(DpDimensions.Small))

        Text(
            text = title.smartTruncate(10),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.inversePrimary
        )
    }

}


@Composable
fun DescriptionSection(
    property: Property,
    modifier: Modifier = Modifier,
) {

    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.overview),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.inversePrimary
        )

        Spacer(modifier = Modifier.height(DpDimensions.Normal))

        ConditionalText(text = property.description, modifier = Modifier.fillMaxWidth())
    }

}


@Composable
fun ConditionalText(
    modifier: Modifier = Modifier,
    text: String
) {

    val minimumLineLength = 5
    var expandedState by remember {
        mutableStateOf(false)
    }
    var showReadMoreButtonState by remember { mutableStateOf(false) }
    val maxLines = if (expandedState) 200 else minimumLineLength

    Column(
        modifier = modifier
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.inversePrimary,
            overflow = TextOverflow.Ellipsis,
            maxLines = maxLines,
            onTextLayout = { textLayoutResult ->
                if (textLayoutResult.lineCount > minimumLineLength - 1) {
                    if (textLayoutResult.isLineEllipsized(minimumLineLength - 1)) {
                        showReadMoreButtonState = true
                    }
                }

            }
        )

        if (showReadMoreButtonState) {
            Spacer(modifier = Modifier.height(DpDimensions.Small))
            Text(
                text = if (expandedState) stringResource(R.string.read_less)
                else stringResource(R.string.read_more),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.clickable {
                    expandedState = !expandedState
                }
            )
        }
    }

}


@Composable
fun OwnerSection(
    property: Property,
    modifier: Modifier = Modifier,
    onCallClick: () -> Unit = {},
    onMessageClick: () -> Unit = {}
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(DpDimensions.Normal)
    ) {

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(property.host_picture_url)
                .placeholder(R.drawable.placeholder)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(CircleShape)
                .height(DpDimensions.Dp50)
                .width(DpDimensions.Dp50)
        )

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = property.host_name,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.inversePrimary
            )

            Text(
                text = "Owner",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.inversePrimary
            )

        }


        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { onMessageClick() }) {
                Icon(
                    painter = painterResource(id = R.drawable.message),
                    contentDescription = "Message icon",
                )
            }

            IconButton(onClick = { onCallClick() }) {
                Icon(
                    painter = painterResource(id = R.drawable.call),
                    contentDescription = "Call icon",
                )
            }
        }

    }

}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun InfoSection(
    property: Property,
    modifier: Modifier = Modifier
) {

    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(DpDimensions.Normal)) {
        Text(
            text = property.name,
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.inversePrimary
        )


        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Surface(
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = .3f),
                shape = RoundedCornerShape(DpDimensions.Small)
            ) {
                CustomPadding(
                    verticalPadding = DpDimensions.Smallest,
                    horizontalPadding = DpDimensions.Small
                ) {
                    Text(
                        text = property.property_type,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))


            RatingComponent(property)
        }

        Spacer(modifier = Modifier.height(DpDimensions.Smallest))

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(DpDimensions.Small),
            verticalArrangement = Arrangement.spacedBy(DpDimensions.Small)
        ) {
            AmenityComponent(icon = R.drawable.bed, title = " ${property.beds} Beds")
            AmenityComponent(
                icon = R.drawable.bathroom,
                title = " ${property.bathrooms} Bathrooms"
            )
            AmenityComponent(
                icon = R.drawable.bedroom,
                title = " ${property.bedrooms} Bedrooms"
            )
            AmenityComponent(
                icon = R.drawable.group,
                title = " ${property.accommodates} Guests"
            )
        }


    }

}


@Composable
fun AmenityComponent(
    @DrawableRes icon: Int,
    title: String,
    modifier: Modifier = Modifier,
    iconSize: Dp = DpDimensions.Normal
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(DpDimensions.Smallest)
    ) {


        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .clip(CircleShape)
                .background(
                    MaterialTheme.colorScheme.onPrimary.copy(alpha = .3f),
                    CircleShape
                )
        ) {
            Box(
                modifier = Modifier.padding(DpDimensions.Small),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = icon), contentDescription = null,
                    modifier = Modifier.size(iconSize),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.inversePrimary
        )

    }

}


@Composable
fun RatingComponent(
    property: Property,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(DpDimensions.Small)
    ) {

        Image(
            painter = painterResource(id = R.drawable.rating),
            contentDescription = "Star Icon",
            modifier = Modifier.size(DpDimensions.Dp20)
        )


        Text(
            text = stringResource(
                id = R.string.reviews,
                property.reviews_per_month?.toFloat() ?: 0f,
                property.number_of_reviews.toFloat()
            ),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.inversePrimary
        )
    }

}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImagesPager(
    modifier: Modifier = Modifier,
    images: List<String>,
    pagerState: PagerState
) {

    Box(modifier = modifier.fillMaxWidth()) {

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth(),
            pageSpacing = DpDimensions.Normal,
            contentPadding = PaddingValues(horizontal = 16.dp),
        ) { page ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(images[page])
                        .placeholder(R.drawable.placeholder)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(RoundedCornerShape(DpDimensions.Small))
                        .fillMaxHeight()
                        .fillMaxWidth()
                )

            }
        }


        CustomPadding(
            verticalPadding = DpDimensions.Normal,
            horizontalPadding = DpDimensions.Small,
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            PagerIndicator(
                currentPage = pagerState.currentPage, items = images,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }


    }

}


val photos = listOf(
    "https://storage.googleapis.com/leizen-frontend.appspot.com/hotel/c1f629edf1df2ffa4b0a26735b78c612?GoogleAccessId=firebase-adminsdk-pe7p9%40leizen-frontend.iam.gserviceaccount.com&Expires=16749763200&Signature=Jtya8nI3hEAchQkHm8NHEg7eQ085MViFqhZhmf15gcoE2lr%2FtYUsmlDyjAjtrcSh8arhsp6gJaXdnis%2BOESJ8fcTNJsXk9YsYw8343vyrFj9Vs0hFxWbi0ACUHXI3w5KqbHlToCrCRa%2FJdqHfv%2Fx8yF1SpuTjjFkd5zbDIftPJns1zSjQPi3g187p%2FrDvdYEjrXzs6wv6sMWXBPhvo21RYo0uWKQJXXE2ax194Tn5%2B4GLofG5USNiO1ZUuEZgooZ5wahF7l8Edot%2B2CEmVUnxRsr%2FyrtqMrBVa9%2Fa70SG3AgdOH8n9eqLTYt3HROA1JfsIyejIz1NgtbJ%2BSgb1hCkg%3D%3D",
    "https://storage.googleapis.com/leizen-frontend.appspot.com/hotel/0f5781cb491326050ef6618ded1774ff?GoogleAccessId=firebase-adminsdk-pe7p9%40leizen-frontend.iam.gserviceaccount.com&Expires=16749763200&Signature=X2H25oTRlLItQiLICdzKNyaEEjr9cWTLpBdvaZZHJhqaC1Fv9oseyh6zZsRB0B%2FPBv%2Bvqhxao6VSKXzDzRc5X9C5rOQ5A86b4qLMnkGoSXaZA3zpuOov5x9ivTXlgikNVAHYNPjq0JX8uC62cKHtX87jt2yzRKsu4tJFbZWsLU1%2FdPsOXrLCCAH6GbV%2F%2FM70hr8Oe94Soyz67dU6RAH4LizNFBOPpU9bVlSry5XfwvHzxJ4bUIT%2FbrbAJAmCR344D9MA%2F8pzESEPnwgZUMymE5ZIyt9QBAxUXfxyx527lqNEsbBuPe0qxUGDcrw1jQr3rpDb%2FXTXbo6Rn11BA8Rlbw%3D%3D",
    "https://storage.googleapis.com/leizen-frontend.appspot.com/hotel/8b77763f029295641f3b319a136d699d?GoogleAccessId=firebase-adminsdk-pe7p9%40leizen-frontend.iam.gserviceaccount.com&Expires=16749763200&Signature=H53jTNDOgSsjuL1ULIATogu4lREKFE8U3EMXsm72%2FTy2DuIxRMs6IMkuTQjh2oAWLpCNGkPCA5pNK%2BVcX9bZCye4aZt%2BCPsevbWBOpXn5ydmbWQ%2Fbdy9M9sKpf6RKrOlu8s%2Bnu3%2FWSNrxtWm9w0BPG%2BdJeWKQyKj4TqbmJdojENS4LOLudgVI2up0aJcczp7d85vqgnZ4ucFn9zRDJC762uVEFdQFXcciH123rXpVbMk6LkLmHW03%2BmjvWbVrY5ONzWbUkfzHJrEg8NR5YwcrEtHl2Il3r0JUrfBtmvs7OeqXY%2FS%2BWUTRK3Gn%2BfuMkuXgWVK5BmlAYHUDOn8qlG3bQ%3D%3D",
    "https://storage.googleapis.com/leizen-frontend.appspot.com/hotel/216ce94d6a3e99b4bec1bc85ae665ed8?GoogleAccessId=firebase-adminsdk-pe7p9%40leizen-frontend.iam.gserviceaccount.com&Expires=16749763200&Signature=i9Q0DN18LUxHTcbRhCLSYIXVY9ErVBO%2BpKOkL%2F1G1fzyawic%2FpdN9uELYe6SrAqw4pWLEswJ1ws6W%2FmDoV6Q%2BOjBdqHboJpMyf%2F4iGxm3MV%2B56zjnonO0JGSS%2F%2BQRnfquTHXR%2FFbZegHYCbBaIpKWA2VSozQDc%2Br2RbEF3a3u65aayeUhTzrg%2BQ8UBnmH0KCK5Rqv7%2BgIvVW4Q2bcVB1cg6HjlUGJGY3grUv1O5c1eg8h%2Bo4lUyIdRVBAauBtCwdtCyCGoP%2FtLnxeGjedJ%2BWO0jkCEa3xWY%2B3J7XWRwNpPpCbofO4TSZkYk4Be%2FaMQk93YFPzWsL094Nv0nte3HwwQ%3D%3D",
    "https://storage.googleapis.com/leizen-frontend.appspot.com/hotel/9822dc8273f95ca3c6c7dd3c7eb40dd0?GoogleAccessId=firebase-adminsdk-pe7p9%40leizen-frontend.iam.gserviceaccount.com&Expires=16749763200&Signature=QL0CCO2pgMi0svqF3fZ5uS3SUqlYNfnwv3x3XZS0BNt67pEUgwBWmrNcaRzEgSE2SbhFJJC4qq96TZktFHwR%2Bjcw7eddEPV5DT3d7pd6Qe9jnEfr26SuvojgPIorTExbmKDaiP4ZgaQ8YualeE9PQezRCeHwF9o%2FdzOUcbVK7XwMUqbRNwgkPxfOfiBJIUVsAcPjMQgn9dQuSXBoNtQ0umGj6%2B%2BOEOJJnmjy22agA%2FHvGOI75b6Sd%2BzdEpj2pwyF28jZuBIoBCWotVV1M2A7LiXh2eXPgG%2BwlBibyCjmnnyeMybHI5ypiCbwEv8naxvbe2H%2Btixw7QERDL3w1mKz%2Fg%3D%3D",
    "https://storage.googleapis.com/leizen-frontend.appspot.com/hotel/6beaf0972671a22ab1e1de475f0a085b?GoogleAccessId=firebase-adminsdk-pe7p9%40leizen-frontend.iam.gserviceaccount.com&Expires=16749763200&Signature=rI%2FjiMPINDSkBc%2BRdfMmSIfKwcqvEQzc%2FjQxAbB8AR6Zh7OBfcNfb4WPn0TIKorMIdJDkyp%2B0PkMf%2FjiONJT1GkVPtayKzwiS0n4k%2FIfE0wudyRtgtnOEo7XG5meODscnM0dp6bWuDTJw8Y7H3WCYKeJ1hdkC7HnESL%2BFwYLB6Hge%2FXqwSsDG14GBB%2BbEP96wpekSmKjPUa%2FghQs%2B7IPGN0JcRLg9dQWalEn6DskA4sLWr%2FmuXvRd6IwGhqHVLu%2Bkka%2FD%2B6VLG%2FHLUvZmyllx1sCQiMPY3BS4IoukNpKa33LnP7ZzsUQ7hRC4A5BiXkF0ny9DEahPwYmFBcHaBE%2BSw%3D%3D",
    "https://storage.googleapis.com/leizen-frontend.appspot.com/hotel/3283b9de00ac994497bfd034ec41fa16?GoogleAccessId=firebase-adminsdk-pe7p9%40leizen-frontend.iam.gserviceaccount.com&Expires=16749763200&Signature=MN349GpHKfP08P0yWPdG%2B9fpdDj1HXjiVqF1oAfxqBec8h2bNcne5QK1zJgQTUG6slux0vZ7zlGpP0lNgMb7eih6qexXtKxl5znu4%2BxpdDfZ6aI%2FTxKM66V2YFGBR%2BhKPr4Pjloycxud6hwufgA03HRfaBXFKSqmfeiVNZ5E%2FCGkz5yEz6nWIzJHc%2BM9U4xxhJTv9%2Bo6XM%2FRBUH2GjqqYLwj8wTG1Y1pZ1nayPpPUHUBiq%2Fs2ffMKPTL7tOPWbTXw%2BkbBfa5ydJjMLKvhnW0efVCOcBxNRT6r3V3toxoip5Bdc8nREnlRHuNCUYpqQkAz5o7ys2gS2Laze4bTzIaLQ%3D%3D"
)
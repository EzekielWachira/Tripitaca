package com.ezzy.presentation.booking

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DateRangePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.ezzy.data.domain.model.Property
import com.ezzy.designsystem.components.CommonAppBarWithTitle
import com.ezzy.designsystem.components.CustomPadding
import com.ezzy.designsystem.components.PrimaryButton
import com.ezzy.designsystem.theme.DarkBlue
import com.ezzy.designsystem.theme.DisabledColor
import com.ezzy.designsystem.utils.DpDimensions
import com.ezzy.presentation.R
import com.ezzy.presentation.booking.state.GuestState
import com.ezzy.presentation.booking.viewmodel.BookingViewModel
import com.ezzy.presentation.booking.viewmodel.Event
import com.ezzy.presentation.booking.viewmodel.GuestType
import com.ezzy.presentation.home.components.ListingItem
import com.ezzy.presentation.listing_detail.viewmodel.DetailViewModel
import com.ezzy.presentation.utils.formatTimeToSmallDate
import com.ezzy.presentation.utils.getTotalDays
import com.ezzy.presentation.utils.smartTruncate
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingScreen(
    navController: NavController,
    isSystemInDarkMode: Boolean = isSystemInDarkTheme(),
    detailViewModel: DetailViewModel,
) {


    val systemUiController = rememberSystemUiController()
    val useDarkIcons = !isSystemInDarkMode

    val property by detailViewModel.property.collectAsStateWithLifecycle()
    val bookingViewModel: BookingViewModel = hiltViewModel()
    val guestState by bookingViewModel.guestState.collectAsStateWithLifecycle()


    val bottomSheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()

    var isSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }

    var dateType by rememberSaveable {
        mutableStateOf(DateType.CHECK_IN)
    }

    var checkInDate by rememberSaveable {
        mutableLongStateOf(0L)
    }

    var checkOutDate by rememberSaveable {
        mutableLongStateOf(0L)
    }

    val totalDays by rememberSaveable {
        mutableLongStateOf(Pair(checkOutDate, checkInDate).getTotalDays())
    }

    val scrollState = rememberScrollState()
    val density = LocalDensity.current

    SideEffect {
        systemUiController.setSystemBarsColor(
            color = if (useDarkIcons)
                Color.White else DarkBlue,
            darkIcons = useDarkIcons
        )
    }





    Scaffold(
        topBar = {
            CommonAppBarWithTitle(
                title = property?.name?.smartTruncate(20).toString(),
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
                .fillMaxSize()
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(scrollState)
                    .padding(DpDimensions.Normal)
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(DpDimensions.Normal)
            ) {

                ListingItem(listing = property!!, modifier = Modifier.fillMaxWidth())

                Spacer(modifier = Modifier.height(DpDimensions.Small))


                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(DpDimensions.Small)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(DpDimensions.Normal)
                    ) {

                        DateSelector(
                            title = stringResource(R.string.check_in),
                            placeholder = if (checkInDate != 0L)
                                checkInDate.formatTimeToSmallDate()
                            else stringResource(R.string.check_in),
                            modifier = Modifier.weight(1f),
                            onClick = {
                                isSheetOpen = true
                                dateType = DateType.CHECK_IN
                            }
                        )


                        DateSelector(
                            title = stringResource(R.string.check_out),
                            placeholder = if (checkOutDate != 0L)
                                checkOutDate.formatTimeToSmallDate()
                            else stringResource(R.string.check_out),
                            modifier = Modifier.weight(1f),
                            onClick = {
                                isSheetOpen = true
                                dateType = DateType.CHECK_OUT
                            }
                        )

                    }

//                    Text(text = "(${totalDays} days)",
//                        style = MaterialTheme.typography.bodyMedium,
//                        color = MaterialTheme.colorScheme.inversePrimary,
//                        modifier = Modifier.align(Alignment.End))
                }


                GuestSection(
                    guestState = guestState,
                    property = property!!,
                    modifier = Modifier.fillMaxWidth(),
                    onAdd = { guestType, value ->
                        bookingViewModel.setGuest(
                            numberOfGuest = value,
                            guestType = guestType,
                            state = guestState,
                            event = Event.ADD
                        )
                    },
                    onMinus = { guestType, value ->
                        bookingViewModel.setGuest(
                            numberOfGuest = value,
                            guestType = guestType, state = guestState,
                            event = Event.SUBTRACT
                        )
                    }
                )

                Spacer(modifier = Modifier.height(DpDimensions.Small))



                AnimatedVisibility(visible = (checkInDate != 0L && checkOutDate != 0L),
                    enter = slideInVertically {
                        with(density) { -40.dp.roundToPx() }
                    } + expandVertically(expandFrom = Alignment.Top)
                            + fadeIn(initialAlpha = .3f),
                    exit = slideOutVertically() + shrinkVertically() + fadeOut()
                ) {
                    TotalSection(
                        guestState = guestState,
                        checkInDate = checkInDate,
                        checkOutDate = checkOutDate,
                        property = property!!,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Surface(
                modifier = Modifier.fillMaxWidth(), color = MaterialTheme.colorScheme.background,
                shadowElevation = DpDimensions.Small
            ) {
                CustomPadding(
                    verticalPadding = DpDimensions.Dp30,
                    horizontalPadding = DpDimensions.Dp20
                ) {
                    PrimaryButton(
                        label = "Continue", disabledColor = DisabledColor,
                        onClick = {},
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            if (isSheetOpen) {
                DatePickerBottomSheet(
                    bottomSheetState = bottomSheetState,
                    property = property!!,
                    onDismiss = {
                        isSheetOpen = false
                    },
                    onSelectDates = { dateIn, dateOut ->
                        checkInDate = dateIn
                        checkOutDate = dateOut
                        isSheetOpen = false
                    })
            }
        }
    }
}


@Composable
fun TotalSection(
    modifier: Modifier = Modifier,
    guestState: GuestState,
    checkInDate: Long,
    checkOutDate: Long,
    property: Property
) {

    Column(modifier = modifier) {

        Text(
            text = stringResource(R.string.price_details),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.inversePrimary
        )

        Spacer(modifier = Modifier.height(DpDimensions.Normal))

        Column(
            horizontalAlignment = Alignment.End, modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(DpDimensions.Small)
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = "Ksh ${property.price} x ${
                        Pair(
                            checkOutDate,
                            checkInDate
                        ).getTotalDays()
                    }",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.inversePrimary
                )

                Text(
                    modifier = Modifier.weight(1f),
                    text = "Ksh ${property.price * Pair(checkOutDate, checkInDate).getTotalDays()}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.inversePrimary,
                    textAlign = TextAlign.End
                )
            }

            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = "Service fee",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.inversePrimary
                )

                Text(
                    modifier = Modifier.weight(1f),
                    text = "Ksh ${property.cleaning_fee ?: 0}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.inversePrimary,
                    textAlign = TextAlign.End
                )
            }
            Spacer(modifier = Modifier.height(DpDimensions.Smallest))

            HorizontalDivider()

            Spacer(modifier = Modifier.height(DpDimensions.Smallest))

            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = "Total",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.inversePrimary
                )

                Text(
                    modifier = Modifier.weight(1f),
                    text = "Ksh ${
                        (property.cleaning_fee ?: 0) + (property.price * Pair(
                            checkOutDate,
                            checkInDate
                        ).getTotalDays())
                    }",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.inversePrimary,
                    textAlign = TextAlign.End
                )
            }
        }


    }


}


@Composable
fun GuestSection(
    modifier: Modifier = Modifier,
    guestState: GuestState,
    property: Property,
    onAdd: (guestType: GuestType, value: Int) -> Unit = { _, _ -> },
    onMinus: (guestType: GuestType, value: Int) -> Unit = { _, _ -> },
) {

    var isInEditMode by rememberSaveable {
        mutableStateOf(false)
    }

    val density = LocalDensity.current

    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(DpDimensions.Normal)) {

        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Quests",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.inversePrimary
                )
                Text(
                    text = "Adults ${guestState.adults}, Children ${guestState.children}, Infants ${guestState.infant}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.inversePrimary
                )
            }

            TextButton(
                onClick = {
                    isInEditMode = !isInEditMode
                },
                shape = CircleShape,
                border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.onPrimary)
            ) {
                CustomPadding(verticalPadding = 0.dp, horizontalPadding = DpDimensions.Normal) {
                    Text(
                        text = if (isInEditMode) stringResource(R.string.done) else stringResource(R.string.edit),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }

            }
        }


        AnimatedVisibility(visible = isInEditMode,
            enter = slideInVertically {
                with(density) { -40.dp.roundToPx() }
            } + expandVertically(expandFrom = Alignment.Top)
                    + fadeIn(initialAlpha = .3f),
            exit = slideOutVertically() + shrinkVertically() + fadeOut()
        ) {

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(DpDimensions.Small)
            ) {
                GuestCounter(
                    guestType = GuestType.ADULT,
                    guestState = guestState,
                    property = property,
                    onAdd = { guestType, value ->
                        onAdd(guestType, value)
                    },
                    onMinus = { guestType, value ->
                        onMinus(guestType, value)
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                GuestCounter(
                    guestType = GuestType.CHILDREN,
                    guestState = guestState,
                    property = property,
                    onAdd = { guestType, value ->
                        onAdd(guestType, value)
                    },
                    onMinus = { guestType, value ->
                        onMinus(guestType, value)
                    },
                    modifier = Modifier.fillMaxWidth()
                )


                GuestCounter(guestType = GuestType.INFANT,
                    guestState = guestState,
                    property = property,
                    onAdd = { guestType, value ->
                        onAdd(guestType, value)
                    },
                    onMinus = { guestType, value ->
                        onMinus(guestType, value)
                    }
                )


            }

        }


    }

}


@Composable
fun GuestCounter(
    modifier: Modifier = Modifier,
    guestType: GuestType,
    guestState: GuestState,
    onAdd: (guestType: GuestType, value: Int) -> Unit = { _, _ -> },
    onMinus: (guestType: GuestType, value: Int) -> Unit = { _, _ -> },
    property: Property
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = when (guestType) {
                    GuestType.ADULT -> stringResource(R.string.adults)
                    GuestType.CHILDREN -> stringResource(R.string.children)
                    GuestType.INFANT -> stringResource(R.string.infant)
                },
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.inversePrimary
            )
            Text(
                text = when (guestType) {
                    GuestType.ADULT -> stringResource(R.string.age_13)
                    GuestType.CHILDREN -> stringResource(R.string.ages_2_12)
                    GuestType.INFANT -> stringResource(R.string.under_2)
                },
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.inversePrimary
            )
        }


        AddMinusButtons(
            guestState = guestState, guestType = guestType,
            onAddClick = { value, guestType ->
                onAdd(guestType, value)
            },
            onMinusClick = { value, guestType ->
                onMinus(guestType, value)
            },
            property = property
        )


    }

}

@Composable
fun AddMinusButtons(
    modifier: Modifier = Modifier,
    onAddClick: (value: Int, guestType: GuestType) -> Unit = { _, _ -> },
    onMinusClick: (value: Int, guestType: GuestType) -> Unit = { _, _ -> },
    guestState: GuestState,
    guestType: GuestType,
    property: Property
) {

    Row(
        modifier = modifier, verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(DpDimensions.Small)
    ) {
        IconButton(
            onClick = {
                onMinusClick(
                    1, guestType
                )
            },
            enabled = when (guestType) {
                GuestType.ADULT -> {
                    guestState.adults > 0
                }

                GuestType.CHILDREN -> {
                    guestState.children > 0
                }

                GuestType.INFANT -> {
                    guestState.infant > 0
                }
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.minus), contentDescription = "Minus Icon",
                modifier = Modifier.size(DpDimensions.Dp20)
            )
        }

        Text(
            text = (when (guestType) {
                GuestType.ADULT -> guestState.adults
                GuestType.CHILDREN -> guestState.children
                GuestType.INFANT -> guestState.infant
            }).toString(),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.inversePrimary
        )

        IconButton(
            onClick = {
                onAddClick(
                    1, guestType
                )
            },
            enabled = property.accommodates > (guestState.adults + guestState.children + guestState.infant)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.add), contentDescription = "Add Icon",
                modifier = Modifier.size(DpDimensions.Dp20)
            )
        }
    }

}


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerBottomSheet(
    modifier: Modifier = Modifier,
    onSelectDates: (checkInDate: Long, checkOutDate: Long) -> Unit = { _, _ -> },
    cornerRadius: Dp = DpDimensions.Dp20,
    bottomSheetState: SheetState,
    property: Property,
    onDismiss: () -> Unit,
) {


    val dateRangerState = rememberDateRangePickerState(
        initialSelectedStartDateMillis = null,
        initialSelectedEndDateMillis = null,
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return !property.booked_dates.contains(utcTimeMillis.formatTimeToSmallDate()) &&
                        utcTimeMillis > System.currentTimeMillis()
            }
        }
    )


    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        shape = RoundedCornerShape(topStart = cornerRadius, topEnd = cornerRadius),
        sheetState = bottomSheetState,
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.background
    ) {

        Column(
            modifier = Modifier
                .padding(start = DpDimensions.Normal, end = DpDimensions.Normal)
                .fillMaxWidth(),
        ) {

            DateRangePicker(
                state = dateRangerState,
                modifier = Modifier.weight(1f),
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

            CustomPadding(verticalPadding = 0.dp, horizontalPadding = DpDimensions.Normal) {
                PrimaryButton(
                    label = "Confirm Selection", disabledColor = DisabledColor,
                    isEnabled = dateRangerState.selectedStartDateMillis != null &&
                            dateRangerState.selectedEndDateMillis != null,
                    onClick = {
                        onSelectDates(
                            dateRangerState.selectedStartDateMillis!!,
                            dateRangerState.selectedEndDateMillis!!
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(DpDimensions.Dp30))
            }


        }
    }

}


@Composable
fun DateSelector(
    modifier: Modifier = Modifier,
    title: String,
    placeholder: String,
    onClick: () -> Unit = {}
) {

    Column(modifier = modifier) {

        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.inversePrimary
        )

        Spacer(modifier = Modifier.height(DpDimensions.Normal))

        DateSelector(
            placeholder = placeholder,
            modifier = Modifier.fillMaxWidth(),
            onClick = onClick
        )

    }
}


@Composable
fun DateSelector(
    modifier: Modifier = Modifier,
    placeholder: String,
    onClick: () -> Unit = {}
) {

    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.surface,
        onClick = onClick,
        shape = RoundedCornerShape(DpDimensions.Small)
    ) {

        Row(modifier = Modifier.padding(DpDimensions.Normal)) {
            Text(
                text = placeholder,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.inversePrimary,
                modifier = Modifier.weight(1f)
            )

            Icon(
                painter = painterResource(id = R.drawable.calendar),
                contentDescription = null,
                modifier = Modifier.size(DpDimensions.Dp20),
                tint = MaterialTheme.colorScheme.inversePrimary
            )
        }

    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarSection(
    modifier: Modifier = Modifier,
    property: Property,
    onDatesSelected: (Pair<Long, Long>) -> Unit = {},
    datePickerState: DateRangePickerState
) {

    Column(
        modifier = modifier
            .padding(DpDimensions.Normal)
            .fillMaxWidth()
    ) {

        Text(
            text = stringResource(R.string.location),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.inversePrimary
        )

        Spacer(modifier = Modifier.height(DpDimensions.Normal))


    }

}


enum class DateType {
    CHECK_IN,
    CHECK_OUT
}
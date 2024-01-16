package com.ezzy.presentation.booking

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DateRangePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
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
import com.ezzy.presentation.listing_detail.viewmodel.DetailViewModel
import com.ezzy.presentation.utils.formatTimeToSmallDate
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

            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(DpDimensions.Normal)
                .weight(1f)) {
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
            }

            Surface(modifier = Modifier.fillMaxWidth(), color = MaterialTheme.colorScheme.background,
                shadowElevation = DpDimensions.Small) {
                CustomPadding(verticalPadding = DpDimensions.Dp30, horizontalPadding = DpDimensions.Dp20) {
                    PrimaryButton(label = "Continue", disabledColor = DisabledColor,
                        onClick = {},
                        modifier = Modifier.fillMaxWidth())
                }
            }

            if (isSheetOpen) {
                DatePickerBottomSheet(title = when (dateType) {
                    DateType.CHECK_IN -> stringResource(id = R.string.check_in)
                    DateType.CHECK_OUT -> stringResource(id = R.string.check_out)
                },
                    bottomSheetState = bottomSheetState,
                    property = property!!,
                    onDismiss = {
                        isSheetOpen = false
                    },
                    onSelectDate = { date ->
                        when (dateType) {
                            DateType.CHECK_IN -> {
                                checkInDate = date
                            }

                            DateType.CHECK_OUT -> {
                                checkOutDate = date
                            }
                        }
                        isSheetOpen = false
                    })
            }
        }
    }
}


@Composable
fun GuestSection(
    modifier: Modifier = Modifier,

) {

}


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerBottomSheet(
    modifier: Modifier = Modifier,
    title: String,
    onSelectDate: (date: Long) -> Unit = {},
    cornerRadius: Dp = DpDimensions.Dp20,
    bottomSheetState: SheetState,
    property: Property,
    onDismiss: () -> Unit,
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
            DatePicker(
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

            CustomPadding(verticalPadding = 0.dp, horizontalPadding = DpDimensions.Normal) {
                PrimaryButton(
                    label = "Confirm Selection", disabledColor = DisabledColor,
                    isEnabled = state.selectedDateMillis != null,
                    onClick = { onSelectDate(state.selectedDateMillis!!) },
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
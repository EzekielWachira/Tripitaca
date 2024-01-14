package com.ezzy.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import com.ezzy.designsystem.utils.DpDimensions
import com.ezzy.presentation.R
import com.ezzy.presentation.home.model.Filter

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun FilterBottomSheet(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = DpDimensions.Dp20,
    bottomSheetState: SheetState,
    onDismiss: () -> Unit,
    filters: List<Filter>,
    onFilterCheck: (isChecked: Boolean, filter: Filter) -> Unit
) {



    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        shape = RoundedCornerShape(topStart = cornerRadius, topEnd = cornerRadius),
        sheetState = bottomSheetState,
        modifier = modifier
    ) {

        Column(
            modifier = Modifier
                .padding(start = DpDimensions.Normal, end = DpDimensions.Normal)
                .fillMaxWidth(),
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()

            ) {

                Text(
                    text = stringResource(R.string.filter), modifier = Modifier.weight(1f),
                    color = MaterialTheme.colorScheme.inversePrimary,
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center
                )

//                IconButton(
//                    onClick = onDismiss,
//                    modifier = Modifier
//                        .testTag("notification")
//                        .border(
//                            width = 1.dp,
//                            color = MaterialTheme.colorScheme.outline,
//                            CircleShape
//                        )
//                ) {
//                    Icon(
//                        painter = painterResource(id = R.drawable.close),
//                        contentDescription = "Notification icon",
//                        tint = MaterialTheme.colorScheme.inversePrimary
//                    )
//                }

            }

            Spacer(modifier = Modifier.height(DpDimensions.Normal))

            Divider(color = MaterialTheme.colorScheme.outline)

            Spacer(modifier = Modifier.height(DpDimensions.Normal))

            Text(
                text = stringResource(R.string.facilities),
                color = MaterialTheme.colorScheme.inversePrimary,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(DpDimensions.Normal))

            FlowRow(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(DpDimensions.Small)) {
                filters.forEach { filter ->
                    FilterItem(filter = filter, onCheck = { isChecked, fl ->
                        onFilterCheck(isChecked, fl)
                    })
                }
            }

            Spacer(modifier = Modifier.height(DpDimensions.Normal))


        }

    }


}
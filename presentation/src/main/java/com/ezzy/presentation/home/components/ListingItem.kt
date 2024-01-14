package com.ezzy.presentation.home.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ezzy.designsystem.components.CustomPadding
import com.ezzy.designsystem.utils.DpDimensions
import com.ezzy.presentation.R
import com.ezzy.presentation.home.model.Listing

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListingItem(
    modifier: Modifier = Modifier,
    listing: Listing,
    onClick: (Listing) -> Unit = {}
) {

    Surface(
        modifier = modifier,
        onClick = { onClick(listing) },
        color = MaterialTheme.colorScheme.onSurface,
        shape = RoundedCornerShape(DpDimensions.Small),
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.surface)
    ) {

        Row(
            modifier = Modifier
                .padding(DpDimensions.Small)
                .fillMaxWidth()
        ) {

            Box{
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(listing.image)
                        .placeholder(R.drawable.placeholder)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(RoundedCornerShape(DpDimensions.Small))
                        .height(DpDimensions.Dp130)
                        .width(DpDimensions.Dp130)
                )



            }

            Spacer(modifier = Modifier.height(DpDimensions.Normal))

            CustomPadding(
                verticalPadding = DpDimensions.Normal,
                horizontalPadding = DpDimensions.Small,
                modifier = Modifier.weight(1f)
            ) {


                Text(
                    text = listing.name,
                    color = MaterialTheme.colorScheme.inversePrimary,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.basicMarquee(),
                    maxLines = 1
                )

                Spacer(modifier = Modifier.height(DpDimensions.Small))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(DpDimensions.Small)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.pin), contentDescription = null,
                        modifier = Modifier.size(DpDimensions.Dp20),
                        tint = MaterialTheme.colorScheme.inversePrimary
                    )

                    Text(
                        text = listing.location,
                        color = MaterialTheme.colorScheme.inversePrimary,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(DpDimensions.Small))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {

                    Text(
                        text = listing.amount,
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = "/month",
                        color = MaterialTheme.colorScheme.inversePrimary,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )

                }
            }

        }

    }

}
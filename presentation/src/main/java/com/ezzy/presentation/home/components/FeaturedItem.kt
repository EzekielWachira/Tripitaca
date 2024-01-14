package com.ezzy.presentation.home.components

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ezzy.designsystem.components.CustomPadding
import com.ezzy.designsystem.theme.TripitacaTheme
import com.ezzy.designsystem.utils.DpDimensions
import com.ezzy.presentation.R
import com.ezzy.presentation.home.model.Listing
import com.ezzy.presentation.home.model.listings

@Composable
fun FeaturedItem(
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

        Column(
            modifier = Modifier
                .padding(DpDimensions.Small)
        ) {
            Box(
                modifier = Modifier.width(250.dp)
            ) {
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
                        .height(DpDimensions.Dp150)
                        .fillMaxWidth()
                )

                CustomChip(
                    label = "Apartment", modifier = Modifier
                        .align(Alignment.BottomEnd)
                )

            }

            Spacer(modifier = Modifier.height(DpDimensions.Small))

            CustomPadding(
                verticalPadding = DpDimensions.Normal,
                horizontalPadding = DpDimensions.Small
            ) {


                Text(
                    text = listing.name,
                    color = MaterialTheme.colorScheme.inversePrimary,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center
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

@Composable
fun CustomChip(
    modifier: Modifier = Modifier,
    label: String,
) {
    CustomPadding(verticalPadding = DpDimensions.Small, horizontalPadding = DpDimensions.Small,
        modifier = modifier) {
        Surface(
            shape = CircleShape,
            color = MaterialTheme.colorScheme.background.copy(alpha = 0.7f),
            border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outline)
        ) {
            Row(modifier = Modifier.padding(DpDimensions.Small)) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onPrimary,
                )
            }
        }
    }
}

@Preview
@Composable
fun FeaturedItemPreview() {
    TripitacaTheme {
        FeaturedItem(listing = listings[0])
    }
}
package com.route.firstapp.ui

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.route.domain.data.Product
import com.route.firstapp.R
import com.route.firstapp.ui.theme.MyPreview


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ProductCard(
    product: Product,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .widthIn(max = 190.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(size = 16.dp),
            )
            .border(
                width = 1.dp, color = Color(0xFF0041824D),
                shape = RoundedCornerShape(size = 16.dp)
            )
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            GlideImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding()
                    .height(128.dp),
                model = product.imageUrl,
                contentScale = ContentScale.Crop,
                contentDescription = "product_image"
            )
            Text(
                product.name ?: "",
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Text(
                    "EGP 1,100",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    "EGP 1,100",
                    style = MaterialTheme.typography.bodySmall,
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Reviews (25)",
                    style = MaterialTheme.typography.bodySmall
                )
                Icon(
                    painter = painterResource(R.drawable.ic_star),
                    contentDescription = "reviews count",
                )
                Icon(
                    modifier = Modifier
                        .background(color = Color(0xFF004182), shape = CircleShape)
                        .padding(8.dp),
                    imageVector = Icons.Default.Add,
                    contentDescription = "ic_add",
                    tint = Color.White
                )
            }

        }
        Icon(
            modifier = Modifier
                .align(alignment = Alignment.TopEnd)
                .padding(12.dp)
                .background(
                    color = Color.White,
                    shape = CircleShape
                )
                .shadow(elevation = 12.dp),
            painter = painterResource(R.drawable.ic_fav),
            tint = Color(0xFF004182),
            contentDescription = "fav icon"
        )
    }
}

@Composable
@Preview(uiMode = UI_MODE_NIGHT_NO, device = Devices.PIXEL)
private fun productPreview() {
    MyPreview {
        ProductCard(
            product = Product(
                "123",
                name = "Pepsi",
                imageUrl = "234"
            )
        )
    }
}

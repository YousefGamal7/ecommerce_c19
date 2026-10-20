package com.route.firstapp.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.route.domain.data.ProductsSort
import com.route.firstapp.R
import com.route.firstapp.ui.home.categoriesSection.HomeCategoriesSection
import com.route.firstapp.ui.home.productsSection.HorizontalProductCardRow
import com.route.firstapp.ui.theme.BrandColor
import com.route.firstapp.ui.theme.MyPreview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onSearchBarClick: () -> Unit = {}
) {
    Scaffold(
        modifier = modifier,
        contentColor = Color.White,
        topBar = {
            TopAppBar(
                title = {
                    Image(
                        modifier = Modifier.height(24.dp),
                        painter = painterResource(R.drawable.route_logo),
                        contentDescription = "logo",
                        contentScale = ContentScale.FillHeight
                    )
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(state = rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            HomeSearchBar(
                onSearchBarClick = onSearchBarClick
            )
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.FillBounds,
                painter = painterResource(R.drawable.home_banner),
                contentDescription = "home banner"
            )

            HomeCategoriesSection()

            HorizontalProductCardRow(
                sort = ProductsSort.MOST_SELLING
            )

            HorizontalProductCardRow(
                sort = ProductsSort.LOWEST_PRICE
            )

            HorizontalProductCardRow(
                sort = ProductsSort.LATEST_ADDED
            )
        }
    }
}

@Composable
fun HomeSearchBar(
    modifier: Modifier = Modifier,
    onSearchBarClick: () -> Unit = {}
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        Box(
            modifier = Modifier
                .weight(1f, fill = true)
                .clickable { onSearchBarClick() }
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White),
                enabled = false,
                readOnly = true,
                textStyle = MaterialTheme.typography.bodyMedium.copy(
                    color = BrandColor,
                ),
                prefix = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "search",
                        tint = BrandColor
                    )
                },
                placeholder = {
                    Text(
                        text = "Search Here",
                        style = MaterialTheme.typography.bodyMedium,
                        color = BrandColor
                    )
                },
                shape = RoundedCornerShape(size = 50.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    disabledBorderColor = Color(0x4D004182),
                    disabledTextColor = BrandColor,
                    disabledPrefixColor = BrandColor,
                    disabledPlaceholderColor = BrandColor.copy(alpha = 0.6f),
                    disabledContainerColor = Color.White
                ),
                value = "",
                onValueChange = {}
            )
        }
        Icon(
            modifier = Modifier
                .width(24.dp)
                .clickable { },
            imageVector = Icons.Default.ShoppingCart,
            tint = BrandColor,
            contentDescription = "Cart"
        )
    }
}

@Composable
@Preview
private fun HomeScreenPreview() {
    MyPreview {
        HomeScreen()
    }
}

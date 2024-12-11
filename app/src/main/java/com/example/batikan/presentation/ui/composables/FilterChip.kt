package com.example.batikan.presentation.ui.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.batikan.presentation.ui.theme.BorderSecondary
import com.example.batikan.presentation.ui.theme.Primary50
import com.example.batikan.presentation.ui.theme.Primary600
import com.example.batikan.presentation.ui.theme.TextSecondary
import com.example.batikan.presentation.ui.theme.White


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterChipGroup(
    modifier: Modifier = Modifier,
    items: List<String>,
    defaultSelectedItemIndex:Int = 0,
    onSelectedChanged : (String) -> Unit = {}
){
    var selectedItemIndex by remember { mutableIntStateOf(defaultSelectedItemIndex) }

    LaunchedEffect(defaultSelectedItemIndex) {
        if (items.isNotEmpty()) {
            onSelectedChanged(items[defaultSelectedItemIndex])
        }
    }


    LazyRow(
        modifier = modifier
            .fillMaxWidth(),
        userScrollEnabled = true
    ) {
        items(items.size) { index: Int ->
            FilterChip(
                modifier = Modifier
                    .padding(end = 6.dp),
                selected = items[selectedItemIndex] == items[index],
                onClick = {
                    selectedItemIndex = index
                    onSelectedChanged(items[index])
                },
                enabled = true,
                label = { Text(items[index]) },
                colors = FilterChipDefaults.filterChipColors(
                    containerColor = White,
                    labelColor = if (selectedItemIndex == index) Primary600 else TextSecondary,
                    selectedContainerColor = Primary50
                ),
                border = FilterChipDefaults.filterChipBorder(
                    enabled = true,
                    selected = selectedItemIndex == index,
                    borderColor = BorderSecondary,
                    selectedBorderColor = Primary600,
                    borderWidth = 2.dp
                )
            )
        }
    }
}

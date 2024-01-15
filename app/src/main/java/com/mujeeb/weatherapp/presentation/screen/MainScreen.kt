package com.mujeeb.weatherapp.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mujeeb.weatherapp.presentation.viewmodel.MainViewModel
import com.mujeeb.weatherapp.presentation.viewstate.CityListResponseViewState
import com.mujeeb.weatherapp.presentation.viewstate.WeatherResultViewState
import com.mujeeb.weatherapp.utils.DataHandler


@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel(),
    onShowDetailsScreen: (Int) -> Unit,
    modifier: Modifier
) {
    val handler by viewModel.result.collectAsStateWithLifecycle(
        initialValue = DataHandler.SUCCESS(
            CityListResponseViewState()
        )
    )


    var searchQuery by remember {
        mutableStateOf("")
    }

    var selectedLocationId by remember { mutableIntStateOf(0) }

    LaunchedEffect(key1 = Unit) {
        viewModel.searchCity(searchQuery)
    }

    when (handler) {
        is DataHandler.LOADING -> {}
        is DataHandler.SUCCESS -> {
            SearchView(
                searchQuery = searchQuery,
                searchResults = handler.data?.list ?: listOf(),
                onSearchQueryChange = {
                    searchQuery = it
                    viewModel.searchCity(it)
                },
                onItemClick = { id: Int ->
                    selectedLocationId = id
                    onShowDetailsScreen(selectedLocationId)

                }
            )
        }

        is DataHandler.ERROR -> {}

    }


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchView(
    searchQuery: String,
    searchResults: List<WeatherResultViewState>,
    onSearchQueryChange: (String) -> Unit,
    onItemClick: (Int) -> Unit,
) {

    SearchBar(
        query = searchQuery,
        onQueryChange = onSearchQueryChange,
        onSearch = {},
        placeholder = {
            Text(text = "Search location")
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                tint = MaterialTheme.colorScheme.onSurface,
                contentDescription = null
            )
        },
        trailingIcon = {},
        content = {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(32.dp),
                contentPadding = PaddingValues(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(
                    count = searchResults.size,
                    key = { index -> searchResults[index].id!! },
                    itemContent = { index ->
                        val result = searchResults[index]
                        ItemView(
                            onClick = onItemClick,
                            result = result
                        )
                    }
                )
            }
        },
        active = true,
        onActiveChange = {},
        tonalElevation = 0.dp
    )

}

@Composable
fun ItemView(
    result: WeatherResultViewState,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                result.id?.let { onClick.invoke(it) }
            }
    ) {
        result.name?.let { Text(text = it) }

    }
}
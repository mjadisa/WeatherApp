package com.mujeeb.weatherapp.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mujeeb.weatherapp.presentation.viewmodel.CityForecastViewModel
import com.mujeeb.weatherapp.presentation.viewstate.ForecastResultViewState
import com.mujeeb.weatherapp.presentation.viewstate.ForecastViewState
import com.mujeeb.weatherapp.utils.DataHandler
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.mujeeb.weatherapp.R
import com.mujeeb.weatherapp.utils.EMPTY_STRING
import com.mujeeb.weatherapp.utils.toEstimatedDirection
import com.mujeeb.weatherapp.utils.toDirectionImageResource
import com.mujeeb.weatherapp.utils.toTimeStampFromDate
import kotlinx.coroutines.launch

@Composable
fun DetailsScreen(
    modifier: Modifier,
    id: String,
    viewModel: CityForecastViewModel = hiltViewModel()
) {
    var openSnackbar by remember { mutableStateOf(false) }
    var snackBarMessage by remember { mutableStateOf("") }

    val handler by viewModel.result.collectAsStateWithLifecycle(
        initialValue = DataHandler.SUCCESS(
            ForecastViewState()
        )
    )

    LaunchedEffect(key1 = Unit) {
        viewModel.getWeatherData(id.toInt())
    }

    when (handler) {
        is DataHandler.LOADING -> {
            ShowProgressBar()
        }

        is DataHandler.SUCCESS -> {
            DetailsContent(results = handler.data?.list ?: listOf())
        }

        is DataHandler.ERROR -> {
            openSnackbar = true
            snackBarMessage = "Error loading data...!"
            DisplayMessage(snackBarMessage, openSnackbar) { openSnackbar = it }

        }

    }

}

@Composable
fun DetailsContent(results: List<ForecastResultViewState>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(vertical = 5.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Weather forecast",
                    style = MaterialTheme.typography.headlineMedium
                )
            }
        }
        items(results) { forecast ->
            ForecastCard(forecast)
        }
    }
}

@Composable
fun ForecastCard(forecast: ForecastResultViewState) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {



            val imageResource = forecast.wind?.deg?.toEstimatedDirection()?.toDirectionImageResource()
                ?: R.drawable.icons8_west_direction_48


            Column(Modifier.padding(8.dp)) {
                Text(
                    text = forecast.dt?.toTimeStampFromDate() ?: EMPTY_STRING,
                    style = MaterialTheme.typography.bodyMedium,
                )
                Image(
                    painter = painterResource(id = imageResource),
                    contentDescription = null,
                    modifier = Modifier.size(130.dp)
                        .padding(8.dp),
                    contentScale = ContentScale.Fit,
                )

            }
            Column(Modifier.padding(8.dp)) {
                Text(
                    text = forecast.main?.temp.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = forecast.wind?.speed.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = forecast.weather?.get(0)?.description ?: EMPTY_STRING,
                    style = MaterialTheme.typography.bodyMedium,
                )

            }
        }

    }

}

@Composable
fun ShowProgressBar() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(64.dp),
            strokeWidth = 5.dp,
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant
        )
    }
}


@Composable
fun DisplayMessage(
    message: String,
    showSb: Boolean,
    openSnackbar: (Boolean) -> Unit
) {

    val snackState = remember { SnackbarHostState() }
    val snackScope = rememberCoroutineScope()

    SnackbarHost(
        modifier = Modifier,
        hostState = snackState
    ) {
        Snackbar(
            snackbarData = it,
            containerColor = Color.Black,
            contentColor = Color.White
        )
    }
    if (showSb) {
        LaunchedEffect(Unit) {
            snackScope.launch { snackState.showSnackbar(message) }
            openSnackbar(false)
        }

    }
}


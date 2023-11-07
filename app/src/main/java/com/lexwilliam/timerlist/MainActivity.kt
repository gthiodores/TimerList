package com.lexwilliam.timerlist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lexwilliam.timerlist.ui.theme.TimerListTheme
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    @OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TimerListTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val timerList by viewModel.timerList.collectAsState()
                    val isFilterShown by viewModel.isFilterShown.collectAsState()

                    if (timerList.isNotEmpty()) {
                        LaunchedEffect(Unit) {
                            while (true) {
                                delay(1.seconds)
                                viewModel.updateTimer()
                            }
                        }
                    }

                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        topBar = {
                            Row(
                                modifier = Modifier
                                    .padding(top = 16.dp)
                                    .padding(horizontal = 32.dp)
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Button(onClick = { viewModel.onTimerAdded() }) {
                                    Text(text = "Add Timer")
                                }
                                Button(
                                    onClick = { viewModel.onChangeColumnClicked() }
                                ) {
                                    Text(text = "Show Filter")
                                }
                                Button(onClick = { viewModel.popTimer() }) {
                                    Text(text = "Pop Timer")
                                }
                            }
                        }
                    ) { innerPadding ->
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 16.dp)
                                .padding(horizontal = 60.dp)
                                .padding(innerPadding),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            LazyVerticalGrid(
                                modifier = Modifier
                                    .animateContentSize()
                                    .weight(if (isFilterShown) 1f else 2f),
                                verticalArrangement = Arrangement.spacedBy(16.dp),
                                horizontalArrangement = Arrangement.spacedBy(16.dp),
                                columns = GridCells.Fixed(if (isFilterShown) 1 else 2)
                            ) {
                                items(
                                    items = timerList,
                                    key = { item -> item.id }
                                ) { timer ->
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(310.dp)
                                            .background(
                                                MaterialTheme.colorScheme.primary,
                                                RoundedCornerShape(16.dp)
                                            )
                                            .animateItemPlacement(),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        Text(text = timer.id.toString().split("-")[0])
                                        Text(text = timer.displayTime.toString(), fontSize = 50.sp)
                                    }
                                }
                            }
                            LazyColumn(
                                modifier = Modifier
                                    .animateContentSize()
                                    .weight(1f),
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                items(items = timerList, key = { item -> item.id }) { timer ->
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(340.dp)
                                            .background(
                                                MaterialTheme.colorScheme.secondary,
                                                RoundedCornerShape(16.dp)
                                            )
                                            .animateItemPlacement(),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        Text(text = timer.id.toString().split("-")[0])
                                        Text(text = timer.displayTime.toString(), fontSize = 50.sp)
                                    }
                                }
                            }
                            AnimatedVisibility(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(start = 24.dp),
                                visible = isFilterShown,
                                enter = slideInHorizontally(
                                    animationSpec = tween(durationMillis = 500),
                                    initialOffsetX = { w -> w }
                                ),
                                exit = slideOutHorizontally(
                                    animationSpec = tween(durationMillis = 500),
                                    targetOffsetX = { w -> w }
                                )
                            ) {
                                Column(
                                    modifier = Modifier,
                                ) {
                                    repeat(10) {
                                        Text(text = "Filter")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

//data class TestItem(
//    val
//)
//
//val testItem =

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TimerListTheme {
        Greeting("Android")
    }
}
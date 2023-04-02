package com.simon.harrypotter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.simon.harrypotter.core.viewModels.AppViewModel
import com.simon.harrypotter.core.viewModels.Events
import com.simon.harrypotter.ui.navGraph.SetUpNavGraph
import com.simon.harrypotter.ui.theme.HarryPotterTheme
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalAnimationApi::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            HarryPotterTheme {

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .navigationBarsPadding(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberAnimatedNavController()
                    val appViewModel:AppViewModel = koinViewModel()
                    SetUpNavGraph(navController =navController,appViewModel)

                    val events = appViewModel.uiEvents.collectAsState(initial =Events.Idle)
                    LaunchedEffect(events.value ){
                        when(events.value){
                            is Events.NavigateToScreen -> {
                                val eventData = events.value as Events.NavigateToScreen
                                val screen = eventData.screen
                                val args  = eventData.args

                                navController.navigate("${screen.route}/${args}")
                            }
                            is Events.NavigateUp -> {
                                navController.navigateUp()
                            }
                            else ->{

                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    HarryPotterTheme {
        Greeting("Android")
    }
}
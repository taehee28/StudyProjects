@file:OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class,
    ExperimentalAnimationApi::class
)

package com.thk.fullscreen_bottomsheet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.thk.fullscreen_bottomsheet.ui.theme.FullscreenbottomsheetTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FullscreenbottomsheetTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
//    FullScreenBottomSheet()
//    FullScreenBottomDrawer()
//    DialogScreen()
    AnimationNavHost()
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FullscreenbottomsheetTheme {
        Greeting("Android")
    }
}

@Composable
fun FullScreenBottomSheet() {
    /**
     * ????????? ????????? ?????? ?????? ??????, halfExpanded ?????? ??????
     * ???????????? ????????? ?????? ?????????
     */

    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )
    val scope = rememberCoroutineScope()

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            Box(modifier = Modifier.fillMaxSize())
        }
    ) {
        Button(
            onClick = {
                scope.launch { sheetState.animateTo(ModalBottomSheetValue.Expanded) }
            }
        ) {
            Text(text = "bottom sheet")
        }
    }
}

@Composable
fun FullScreenBottomDrawer() {
    /**
     * ????????? ????????? ?????? ??????, halfExpanded ?????? ???????????? ??????
     * ???????????? ????????? ?????? ?????????
     */

    val drawerState = rememberBottomDrawerState(initialValue = BottomDrawerValue.Closed)
    val scope = rememberCoroutineScope()

    BottomDrawer(
        drawerState = drawerState,
        drawerContent = {
            Box(modifier = Modifier.fillMaxSize())
        },
        gesturesEnabled = false
    ) {
        Button(
            onClick = {
                scope.launch { drawerState.animateTo(BottomDrawerValue.Expanded) }
            }
        ) {
            Text(text = "bottom drawer")
        }
    }
}

@Composable
fun DialogScreen() {
    val isDialogShow = remember {
        mutableStateOf(false)
    }

    Box {
        Button(onClick = { isDialogShow.value = true }) {
            Text(text = "dialog")
        }


        AnimatedVisibility(visible = isDialogShow.value, enter = slideInVertically()) {
            FullScreenDialog { isDialogShow.value = false }
        }

    }

}

@Composable
fun FullScreenDialog(onDismissRequest: () -> Unit) {
    /**
     * ????????? ??? animation ???????????? ?????? ????????? ???
     * ???????????? ??????????????? ?????? ?????? ??????(????????? ??????) dim ???????????????
     * ???????????? ????????? ??????????????? ??????
     */

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = false, dismissOnClickOutside = false)
    ) {
        Surface(modifier = Modifier.fillMaxSize()) {

        }
    }
}

@Composable
fun AnimationNavHost() {
    /**
     * Navigation Animation ????????? ?????? ??????
     */

    val navController = rememberAnimatedNavController()
    
    AnimatedNavHost(navController = navController, startDestination = "Main") {
        composable(
            "Main",
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }
        ) {
            MainScreen { navController.navigate("Sub") }
        }

        composable(
            "Sub",
            enterTransition = {
                when (initialState.destination.route) {
                    "Main" -> slideIntoContainer(AnimatedContentScope.SlideDirection.Up, animationSpec = tween(150))
                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    "Main" -> slideOutOfContainer(AnimatedContentScope.SlideDirection.Down, animationSpec = tween(150))
                    else -> null
                }
            }
        ) {
            SubScreen()
        }
    }
}

@Composable
fun MainScreen(onButtonClick: () -> Unit) {
    Column {
        Text(text = "Main")
        Button(onClick = onButtonClick) {
            Text(text = "move to Sub")
        }

    }
}

@Composable
fun SubScreen() {
    Column(Modifier.background(Color.LightGray)) {
        Text(text = "Sub")
    }
}
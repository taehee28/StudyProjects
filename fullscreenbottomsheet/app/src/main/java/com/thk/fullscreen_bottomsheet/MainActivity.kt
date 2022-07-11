@file:OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class,
    ExperimentalAnimationApi::class
)

package com.thk.fullscreen_bottomsheet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
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
     * 사용자 제스처 막기 지원 안함, halfExpanded 스킵 가능
     * 뒤로가기 누르면 앱이 종료됨
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
     * 사용자 제스처 막기 가능, halfExpanded 스킵 지원하지 않음
     * 뒤로가기 누르면 앱이 종료됨
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
     * 나타날 때 animation 설정하는 방법 찾아야 됨
     * 나타나면 다이얼로그 밑에 있는 화면(상태바 포함) dim 처리해버림
     * 뒤로가기 누르면 다이얼로그 종료
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
    val navController = rememberAnimatedNavController()
    
    AnimatedNavHost(navController = navController, startDestination = "Main") {
        composable(
            "Main"
        ) {
            MainScreen({ /*TODO*/ })
        }

        composable("Sub") {
            SubScreen()
        }
    }
}

@Composable
fun MainScreen(onButtonClick: () -> Unit) {
    Text(text = "Main")
    Button(onClick = { /*TODO*/ }) {
        Text(text = "move to Sub")
    }
}

@Composable
fun SubScreen() {
    Text(text = "Sub")
}
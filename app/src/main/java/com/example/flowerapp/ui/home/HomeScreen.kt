package com.example.flowerapp.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.flowerapp.ui.theme.CustomScaffold
import com.example.flowerapp.ui.theme.MenuCard
import com.example.flowerapp.R
import com.example.flowerapp.ui.theme.ButtonWithIcon

@Composable
fun HomeScreen(
    openCameraScreen: () -> Unit,
    openView3DModelScreen: () -> Unit,
    onLogOut: () -> Unit
) {
    CustomScaffold(bottomBarText = "Home") {
        innerPadding -> HomeComponent(innerPadding, openCameraScreen, openView3DModelScreen, onLogOut)
    }
}

@Composable
fun HomeComponent(
    innerPadding: PaddingValues,
    openCameraScreen: () -> Unit,
    openView3DModelScreen: () -> Unit,
    onLogOut: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
        ,
        contentAlignment = Alignment.Center
    ) {
        ButtonWithIcon(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(20.dp)
            ,
            onClick = onLogOut,
            iconId = R.drawable.logout,
            textId = R.string.logout
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            MenuCard(
                imageId = R.drawable.view_3d_model,
                text = stringResource(id = R.string.model_3D),
                onClick = {
                    openView3DModelScreen()
                }
            )
            MenuCard(
                imageId = R.drawable.capture_picture,
                text = stringResource(id = R.string.capture_picture),
                onClick = {
                    openCameraScreen()
                }
            )
        }
    }
}
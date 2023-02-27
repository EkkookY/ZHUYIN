package com.example.testzhuyin.ui.page

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FabPosition
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import com.example.testzhuyin.ui.theme.black
import com.example.testzhuyin.ui.theme.green1
import com.example.testzhuyin.ui.theme.white
import com.example.testzhuyin.viewmodel.MineViewModel

@Composable
@OptIn(ExperimentalCoilApi::class, ExperimentalMaterialApi::class)
fun MinePage(modifier: Modifier=Modifier, navController: NavController, viewModel: MineViewModel =MineViewModel()) {

    val scope = rememberCoroutineScope ()
    val scaffoldState = rememberBottomSheetScaffoldState()
    BottomSheetScaffold(
        sheetContent = {
            Column(modifier = Modifier
                .padding(top = 0.dp)
                .fillMaxWidth()
                .weight(1f)
                .clip(shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
                .background(
                    color = green1,
                    shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                )
            ){}
        },
        scaffoldState = scaffoldState,

        floatingActionButtonPosition = FabPosition.End,
        sheetPeekHeight = 128.dp,
        sheetBackgroundColor = white,
        drawerBackgroundColor = black,


    ) { innerPadding ->
        LazyColumn(contentPadding = innerPadding) {
            items(100) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .background(white)
                )
            }
        }
    }
}



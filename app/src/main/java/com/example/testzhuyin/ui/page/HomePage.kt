package com.example.testzhuyin.ui.page


import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testzhuyin.R

import com.example.testzhuyin.ui.components.SearchBar
import com.example.testzhuyin.ui.theme.black
import com.example.testzhuyin.ui.theme.blue
import com.example.testzhuyin.ui.theme.green1
import com.example.testzhuyin.ui.theme.green2
import com.example.testzhuyin.ui.theme.green3
import com.example.testzhuyin.ui.theme.grey1
import com.example.testzhuyin.ui.theme.red2
import com.example.testzhuyin.ui.theme.white
import com.example.testzhuyin.ui.theme.white1
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.testzhuyin.data.DataProvide.ContentData
import com.example.testzhuyin.viewmodel.ArticleViewModel
import com.melody.map.myapplication.ui.HomeMapScreen


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomePage(modifier: Modifier = Modifier, navController: NavController, viewModel: ArticleViewModel = ArticleViewModel()) {

    val scaffoldState = rememberBottomSheetScaffoldState()
    val voicestate =remember{ mutableStateOf(false) }



    BottomSheetScaffold(


        sheetContent = {
            NameBar()
            Column(modifier = Modifier
                .padding(top = 0.dp)
                .fillMaxWidth()
                .weight(1f)
                .clip(shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
                .background(
                    color = green1,
                    shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                )
            ){
                /**
                 *网格图随标签栏变更，待完善
                 */
                GridContent()
            }


        },
        scaffoldState = scaffoldState,

        sheetPeekHeight = 260.dp,
        sheetBackgroundColor = white,
    ) { innerPadding ->
        Column {
            Column(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(white)
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(18.dp, 28.dp, 0.dp, 0.dp)
                ) {
                    Column(
                        Modifier
                            .padding(start = 12.dp)
                            .weight(1f)
                    ) {
                        Text(
                            text = "民族百科地图",
                            fontSize = 20.sp,
                            color = green2,
                            fontWeight = FontWeight.Bold
                        )

                    }
//                        var iconRes = painterResource(id = R.drawable.icon_voice_on)
//                        if( == ){
//                            iconRes = painterResource(id = R.drawable.icon_voice_off)
//                        }// 音量开关
                    Image(
                        painter = painterResource(id = R.drawable.icon_voice_on),
                        contentDescription = "音量",
                        Modifier
                            .padding(0.dp, 0.dp, 20.dp, 0.dp)
                            .size(30.dp)
                    )
                }
                SearchBar()
                Column(modifier=Modifier.height(400.dp)) {
                    HomeMapScreen { latLng, targetAssets ->
                        navController.navigate(
                            route = "detail/ethnicity/${latLng.latitude}/${latLng.longitude}/${targetAssets}",
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun NameBar(){
    val names= listOf("推荐","景点","美食","服饰","节日")
    var selected by remember {
        mutableStateOf(0)
    }
    LazyRow(
        Modifier
            .padding(0.dp, 8.dp)
            .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)), contentPadding = PaddingValues(12.dp,0.dp)) {
        itemsIndexed(names){index , name ->
            Column(
                Modifier
                    .padding(24.dp, 4.dp)
                    .width(IntrinsicSize.Max)
                    .clickable { selected = index }) {
                Text(text = name, fontSize = 15.sp, color = if (index == selected) black else grey1)
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 2.dp)
                    .height(4.dp)
                    .clip(
                        RoundedCornerShape(2.dp)
                    )
                    .background(if (index == selected) green2 else Color.Transparent))

            }
        }

    }
}
@Composable
fun GridContent(){
    LazyVerticalGrid(columns = GridCells.Fixed(2), contentPadding = PaddingValues(vertical = 16.dp) ){
        items(ContentData){item ->
            FavoriteCollectionCard(drawable = item.drawable, text = item.text, onButtonClick = {/*todo*/}, modifier = Modifier.height(56.dp))

        }
    }
    Box(modifier = Modifier.height(20.dp))
}
@Composable
fun FavoriteCollectionCard(
    @DrawableRes drawable: Int,
    @StringRes text: Int,
    onButtonClick:()->Unit,
    modifier: Modifier = Modifier
) {
    Card(modifier = Modifier
        .padding(16.dp, 16.dp)
        .fillMaxWidth(),
        elevation = 2.dp,
        backgroundColor = white1,
        shape = RoundedCornerShape(corner = CornerSize(16.dp)
        )
    ){
        Column(modifier = Modifier
            .clickable { onButtonClick.invoke() }

            .fillMaxWidth()

        ) {
            Image(painter = painterResource(id = (drawable)), contentDescription =null,contentScale = ContentScale.Crop, modifier = Modifier.size(180.dp,140.dp) )
            Text(text = stringResource(id = text), fontSize = 20.sp, modifier = Modifier.padding(4.dp,2.dp,0.dp,0.dp))
            Text(text = stringResource(id = text), fontSize = 12.sp, modifier = Modifier.padding(4.dp,4.dp,0.dp,8.dp))
//          RouteNavigationUtil.navigationTo(navHostController, destinationName = RouteKey.ARTICLE.toString())
        }

    }
}
@Composable
fun HomeUIbutton(
    modifier: Modifier
){
    Column(

        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(26.dp, 200.dp, 18.dp, 0.dp)
                .weight(1f)
        ) {
            Column(
                Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(color = green2)
            ) {
                Row() {
                    androidx.compose.foundation.Image(
                        painter = painterResource(id = R.drawable.image_nation_achang),
                        contentDescription = "每日民族",
                        Modifier
                            .padding(10.dp)
                            .size(60.dp)
                    )
                    Column(modifier = Modifier.padding(10.dp, 10.dp, 18.dp, 30.dp)) {
                        Text(text = "每日民族", color = green3, fontSize = 10.sp)
                        Box(modifier = Modifier.padding(0.dp,3.dp))
                        Text(text = "阿昌族", color = red2, fontSize = 18.sp)

                    }
                }
            }
            Box(modifier = Modifier.padding(14.dp))
            Column(
                Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(color = blue)
            ) {
                Column(modifier = Modifier.padding(18.dp, 10.dp, 18.dp, 30.dp)) {
                    Text(text = "趣味答题", color = white, fontSize = 18.sp,)
                    Box(modifier = Modifier.padding(0.dp,3.dp))
                    Text(
                        text = "快来测测你对民族有多了解吧",
                        color = white1,
                        fontSize = 10.sp
                    )
                }

            }
        }
    }
}
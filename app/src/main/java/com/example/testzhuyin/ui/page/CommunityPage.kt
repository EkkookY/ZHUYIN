package com.example.testzhuyin.ui.page

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.testzhuyin.ui.components.SearchBar
import com.example.testzhuyin.viewmodel.CommunityViewModel
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import com.example.testzhuyin.data.DataProvide
import com.example.testzhuyin.model.UserContent
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.example.testzhuyin.R
import com.example.testzhuyin.ui.theme.*


@Composable
fun CommunityPage(modifier: Modifier = Modifier, navController: NavController, viewModel: CommunityViewModel = CommunityViewModel()){
    Column(modifier = Modifier) {
        Row(
            Modifier
                .padding(start = 12.dp)
                .weight(1f)
                .height(20.dp)
        ){}
        Text(text = "社区",
            fontSize = 20.sp,
            color = black,
            fontWeight = FontWeight.Bold)

        SearchBar()
        CommunityNameBar()
        CommunityContent(modifier = Modifier)
    }

}
@Composable
fun CommunityContent(modifier: Modifier){
    val comcontents= remember { DataProvide.comcontentlist}
    LazyColumn(contentPadding = PaddingValues(16.dp,8.dp)){
        items(
            items = comcontents,
            itemContent = { ContentListItem( comcontent= it ) {}
            }
        )
    }
}

@Composable
fun ContentListItem(comcontent: UserContent, onButtonClick:()->Unit){
    Row(Modifier.clickable { onButtonClick.invoke() }) {
        Column(modifier = Modifier
            .padding(16.dp)
            .align(Alignment.CenterVertically)
            .clip(RoundedCornerShape(10.dp))
        ) {
            Image(painter = painterResource(id = comcontent.user), contentDescription ="user", contentScale = ContentScale.Crop,modifier = Modifier.size(30.dp,30.dp))
        }
        Column(modifier = Modifier
            .padding(16.dp)
            .align(Alignment.CenterVertically)
        ) {
            Text(text = comcontent.name, fontSize = 20.sp)
            Text(text = comcontent.time, fontSize = 8.sp)
        }
        Column(modifier = Modifier
            .padding(end = 4.dp)
            .align(Alignment.CenterVertically)
            .clip(RoundedCornerShape(8.dp))
            .background(color = green1)
        ) {
            Row() {
                Image(painter = painterResource(id = R.drawable.icon_location), contentDescription ="location", contentScale = ContentScale.Crop)
                Text(text = comcontent.Slocation, fontSize = 12.sp, color = green2)
                Text(text = comcontent.Nationality, fontSize = 12.sp, color = green2,modifier = Modifier.padding(start =2.dp ))
            }
        }
    }
    Row(Modifier.clickable { onButtonClick.invoke() }) {

        Text(text = comcontent.text, fontSize = 16.sp)
        Column(modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .align(Alignment.CenterVertically)
        ) {
            Image(painter = painterResource(id = comcontent.drawable), contentDescription ="user", contentScale = ContentScale.Crop)
//                 RouteNavigationUtil.navigationTo(navHostController, destinationName = RouteKey.ARTICLE.toString())
        }
    }

}


@Composable
fun CommunityContentCard(
    @DrawableRes drawable: Int,
    @StringRes text: Int,

    onButtonClick:()->Unit,
    modifier: Modifier = Modifier
) {
    Text(text = stringResource(id = text), fontSize = 20.sp, modifier = Modifier.padding(4.dp,4.dp,4.dp,4.dp))
    Card(modifier = Modifier
        .padding(16.dp, 16.dp)
        .fillMaxWidth(1f),
        border = BorderStroke(1.dp, green2),
        elevation = 2.dp,
        backgroundColor = white1,
        shape = RoundedCornerShape(corner = CornerSize(16.dp))
    ){
        Row(modifier = Modifier
            .fillMaxWidth(1f)
            .clickable { onButtonClick.invoke()},
            horizontalArrangement = Arrangement.Center

        ) {
            Image(painter = painterResource(id = (drawable)), contentDescription =null,contentScale = ContentScale.Crop, modifier = Modifier.size(180.dp,140.dp) )

        }
    }
}
@Composable
fun CommunityNameBar(){
    val names= listOf("推荐","附近")
    var selected by remember {
        mutableStateOf(0)
    }
    LazyRow(
        Modifier
            .padding(0.dp, 8.dp)
            .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)), contentPadding = PaddingValues(12.dp,0.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
        itemsIndexed(names){index , name ->
            Column(
                Modifier
                    .padding(70.dp, 4.dp)
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
package com.example.testzhuyin.ui.page

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import com.example.testzhuyin.R
import com.example.testzhuyin.data.DataProvide
import com.example.testzhuyin.data.Note
import com.example.testzhuyin.model.NoticeContent
import com.example.testzhuyin.ui.components.SearchBar
import com.example.testzhuyin.ui.theme.*


@Composable
fun TestPage(modifier: Modifier =Modifier) {

    androidx.compose.foundation.Image(
        painter = painterResource(id = R.drawable.background),
        contentDescription = "msg_read",
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxWidth()
    )
    Column(modifier = Modifier) {
        Row( verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()) {

            Text(text = "消息",fontSize = 18.sp, textAlign = TextAlign.Center,modifier= Modifier
                .align(Alignment.CenterVertically)
                .padding(bottom = 10.dp, top = 10.dp, start = 150.dp), color = themegreen)
            androidx.compose.foundation.Image(
                painter = painterResource(id =R.drawable.msg_read),
                contentDescription = "msg_read",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(top = 2.dp, start = 80.dp)
                    .size(62.dp, 22.dp)

            )

        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()


        ) {
            Column(modifier = Modifier.padding( start = 30.dp)

            ) {
                androidx.compose.foundation.Image(
                    painter = painterResource(id =R.drawable.msg1),
                    contentDescription = "msg_read",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(90.dp, 90.dp)

                )
                Text(text = "点赞",fontSize = 14.sp,color = themegreen,modifier = Modifier.padding(start = 30.dp))
            }

            Column(
                modifier = Modifier.padding(start = 10.dp)
            ) {
                androidx.compose.foundation.Image(
                    painter = painterResource(id =R.drawable.msg2),
                    contentDescription = "msg_read",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(90.dp, 90.dp)
                )
                Text(text = "评论",fontSize = 14.sp, color = themegreen,modifier = Modifier.padding(start = 30.dp))
            }

            Column(
                modifier = Modifier.padding(start = 10.dp)
            ) {
                androidx.compose.foundation.Image(
                    painter = painterResource(id =R.drawable.msg3),
                    contentDescription = "msg_read",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(90.dp, 90.dp)
                )
                Text(modifier = Modifier.padding(start = 30.dp), text = "通知",fontSize = 14.sp,color = themegreen)
            }
        }
        Row(
            Modifier
                .padding(10.dp)
        ){}

        TestContent(modifier = Modifier)
    }
}

@Composable
fun TestContent( modifier: Modifier = Modifier
    .fillMaxWidth()){
    val NoticeContents=remember{ DataProvide.noticelist}
    LazyColumn(contentPadding = PaddingValues(25.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
        items(
            items = NoticeContents,
            itemContent = { NoticeListItem( noticecontent=it) {}
            }
        )
    }
}

@Composable
fun NoticeListItem(noticecontent:NoticeContent, onButtonClick:()->Unit){

    Row(
        Modifier
            .clickable { onButtonClick.invoke() }

            .fillMaxWidth()) {
        Row(modifier = Modifier
            .width(270.dp)

        ) {
            Column(modifier = Modifier
                .padding(bottom = 15.dp,top =15.dp)
                .align(Alignment.Top)
                .clip(RoundedCornerShape(10.dp))
            ) {
                androidx.compose.foundation.Image(
                    painter = painterResource(id =noticecontent.drawable),
                    contentDescription = "user",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(45.dp, 45.dp)
                )
            }
            Column(modifier = Modifier
                .padding(start = 10.dp, bottom = 5.dp, top =15.dp)
                .align(Alignment.Top)
            ) {
                Text(text = noticecontent.id, fontSize = 16.sp, modifier = Modifier.padding(bottom = 3.dp))
                Text(text = noticecontent.text, fontSize = 14.sp,color= grey5)
            }
        }


        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(top =15.dp)
                .align(Alignment.Top)

        ) {
            Text(text = noticecontent.time, fontSize =12.sp, color = Color.Gray)
        }

    }
}
package com.example.testzhuyin.ui.page

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testzhuyin.R
import com.example.testzhuyin.data.DataProvide
import com.example.testzhuyin.model.GroupContent
import com.example.testzhuyin.model.NoticeContent
import com.example.testzhuyin.ui.theme.grey5
import com.example.testzhuyin.ui.theme.themegreen

@Preview
@Composable
fun GroupPage(modifier: Modifier = Modifier){
    Column() {
        Row(
            Modifier
                .padding(100.dp)
        ){}
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier =
            Modifier
                .padding(10.dp)

        ){
            Text(text = "热门圈子",modifier = Modifier.padding(start = 10.dp),fontSize = 16.sp)
            Text(text = "附近圈子",modifier = Modifier.padding(start = 10.dp),fontSize = 14.sp)
            Text(text = "我创建的",modifier = Modifier.padding(start = 10.dp),fontSize = 14.sp)
        }
        GroupContent(modifier = Modifier)
    }

}

@Composable
fun GroupContent( modifier: Modifier = Modifier
    .fillMaxWidth()){
    val GroupContents= remember{ DataProvide.grouplist}
    LazyColumn(contentPadding = PaddingValues(25.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        items(
            items = GroupContents,
            itemContent = { GroupListItem( groupcontent=it) {}
            }
        )
    }
}

@Composable
fun GroupListItem(groupcontent: GroupContent, onButtonClick:()->Unit){
    Column(){
        Row(
            Modifier
                .clickable { onButtonClick.invoke() }

                .fillMaxWidth()) {
            Row(modifier = Modifier
                .width(270.dp)

            ) {
                Column(modifier = Modifier
                    .padding(bottom = 15.dp, top = 15.dp)
                    .align(Alignment.Top)
                    .clip(RoundedCornerShape(10.dp))
                ) {
                    androidx.compose.foundation.Image(
                        painter = painterResource(id =groupcontent.drawable),
                        contentDescription = "user",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(45.dp, 45.dp)
                    )
                }
                Column(modifier = Modifier
                    .padding(start = 10.dp, bottom = 5.dp, top = 15.dp)
                    .align(Alignment.Top)
                ) {
                    Text(text = groupcontent.id, fontSize = 16.sp, modifier = Modifier.padding(bottom = 3.dp))
                    Text(text = groupcontent.num.toString()+"人", fontSize = 12.sp,color= grey5)
                }
            }


            Row(
                modifier = Modifier
                    .padding(top = 15.dp)
                    .align(Alignment.Top)

            ) {
                androidx.compose.foundation.Image(
                    painter = painterResource(id = R.drawable.group_join),
                    contentDescription = "user",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(70.dp, 28.dp)
                )
            }

        }
        Box(
            modifier = Modifier
                .padding(bottom = 10.dp)
        ) {
            androidx.compose.foundation.Image(
                painter = painterResource(id = R.drawable.group_back),
                contentDescription = "user",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(328.dp, 28.dp)
            )
            Row(

                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(start = 5.dp, top = 5.dp, bottom = 5.dp)

            ) {
                androidx.compose.foundation.Image(
                    painter = painterResource(id = R.drawable.pic_group_msg),
                    contentDescription = "user",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(16.dp, 16.dp)
                )
                Text(text = groupcontent.text, fontSize =12.sp, color = Color.Gray, modifier = Modifier.padding(start = 5.dp))
            }
        }

    }


}
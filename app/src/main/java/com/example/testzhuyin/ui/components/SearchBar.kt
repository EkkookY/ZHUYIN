package com.example.testzhuyin.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testzhuyin.R
import com.example.testzhuyin.ui.theme.green1
import com.example.testzhuyin.ui.theme.grey1

@Composable
fun SearchBar(){
    Row(
        modifier = Modifier
            .padding(24.dp, 6.dp, 24.dp, 2.dp)
            .fillMaxWidth()
            .height(40.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(color = green1),
        verticalAlignment = Alignment.CenterVertically) {
        var searchText by remember {
            mutableStateOf("")
        }
        BasicTextField(searchText, { searchText = it },
            Modifier
                .padding(start = 24.dp)
                .weight(1f),
            textStyle = TextStyle(fontSize = 15.sp)
        ){if (searchText.isEmpty()){
            Text(text = "搜索一下吧", color = grey1, fontSize = 15.sp)
        }
            it()
        }
        Box(
            Modifier
                .padding(4.dp)
                .fillMaxHeight()
                .aspectRatio(1f)){
            Icon(painterResource(id = R.drawable.icon_search) , contentDescription ="搜索" ,
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.Center)
            )
        }
        
    }
}
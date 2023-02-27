package com.example.testzhuyin.ui.page

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testzhuyin.R
import com.example.testzhuyin.data.DataProvide
import com.example.testzhuyin.data.Note
import com.example.testzhuyin.ui.theme.green4
import com.example.testzhuyin.ui.theme.grey5
import com.example.testzhuyin.ui.theme.white


@Preview
@Composable
fun TemptPage(modifier: Modifier =Modifier){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        androidx.compose.foundation.Image(
            painter = painterResource(id =R.drawable.background),
            contentDescription = "msg_read",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(top = 2.dp)
                .fillMaxWidth()

        )
        androidx.compose.foundation.Image(
            painter = painterResource(id =R.drawable.start1),
            contentDescription = "msg_read",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(top = 2.dp)
                .fillMaxWidth()

        )

    }



}





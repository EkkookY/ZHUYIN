package com.example.testzhuyin.ui.page

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import com.example.testzhuyin.R
import com.example.testzhuyin.data.Note
import com.example.testzhuyin.data.DataProvide
import com.example.testzhuyin.model.RouteKey
import com.example.testzhuyin.ui.components.SearchBar
import com.example.testzhuyin.ui.theme.*
import com.example.testzhuyin.viewmodel.JourneyViewModel




fun Image(painter: Painter, contentDescription: Any?, padding: Modifier) {

}

@Composable
@OptIn(ExperimentalCoilApi::class, ExperimentalMaterialApi::class)
fun JourneyPage(modifier: Modifier = Modifier, navController: NavController, viewModel: JourneyViewModel = JourneyViewModel()) {


    val scaffoldState = rememberBottomSheetScaffoldState()




    BottomSheetScaffold(
        sheetContent = {
//            FloatingActionButton(onClick = { /*TODO*/ }) {
//                navController.navigate( route = RouteKey.NOTE.toString())
//            }

            Column(
                modifier = Modifier
                    .padding(top = 0.dp)
                    .fillMaxWidth()
//                    .weight(1f)
                    .clip(shape = RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp))
                    .background(
                        color = white,
                        shape = RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp)
                    )
            )
            {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(18.dp, 28.dp, 18.dp, 0.dp)) {
                    Column(
                        modifier = Modifier
                            .padding(top = 10.dp)
                            ){
                        Text(text ="旅途", fontSize = 20.sp, color = black , fontWeight = FontWeight.Bold)
                        Text(text ="     条笔记", fontSize = 8.sp, color = green2 )
                    }
                    SearchBar()
                }



                val notes= remember { DataProvide.notelist}
                LazyColumn(contentPadding = PaddingValues(16.dp,8.dp)){
                    items(
                        items = notes,
                        itemContent = { NoteListItem( note =it ) {

                        }
                        }
                    )
                }

            }
        },
        scaffoldState = scaffoldState,

        sheetPeekHeight = 260.dp,
        sheetBackgroundColor = white,
    ) { innerPadding ->
        Column{
            Column(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(white)) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(18.dp, 28.dp, 0.dp, 0.dp)){
                    Column (
                        Modifier
                            .padding(start = 12.dp)
                            .weight(1f)){
                        Text(text ="旅途", fontSize = 20.sp, color = black ,fontWeight = FontWeight.Bold)
                        Row {
                            Image(
                                painter = painterResource(id = R.drawable.icon_location),
                                contentDescription = "位置",
                                Modifier
                                    .size(15.dp)
                                    )
                            Text(text = "云南大理白族自治区", fontSize = 12.sp, color = green2)
                        }
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

            }

        }
    }
}



@Composable
fun CommonButtonWidget(
    modifier: Modifier = Modifier
        .padding(horizontal = 16.dp)
        .fillMaxWidth()
        .height(80.dp),
    buttonTitle:String,
    onButtonClick:()->Unit){

    OutlinedButton(
        onClick = { onButtonClick.invoke() },
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(contentColor = MaterialTheme.colors.primary.copy(
            alpha = 1f
        ), backgroundColor = MaterialTheme.colors.primary.copy(
            alpha = 1f
        ))
    ) {
        Text(text = buttonTitle, fontSize = 16.sp, color = Color.Black)
    }

}
@Composable
fun NoteListItem(note: Note, onButtonClick:()->Unit){
    Card(modifier = Modifier
        .padding(8.dp, 10.dp)
        .fillMaxWidth(),
        elevation = 2.dp,
        backgroundColor = green4,
        shape = RoundedCornerShape(corner = CornerSize(16.dp)
        )

    ){
        Box() {

            Image(
                painter = painterResource(id = R.drawable.note_pic1),
                contentDescription = "note_pic",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(150.dp, 40.dp)
                    .align(Alignment.BottomEnd)
            )

            Image(
                painter = painterResource(id = R.drawable.note_pic),
                contentDescription = "note_pic1",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(42.dp, 40.dp)
                    .align(Alignment.TopEnd)
            )

            Row(Modifier.clickable { onButtonClick.invoke() }) {
                Column(modifier = Modifier
                    .padding(22.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)
                ) {
                    Text(text = note.title, fontSize = 16.sp)
                    Text(text = note.time, fontSize = 8.sp, color = grey5, modifier = Modifier.padding(start = 12.dp, top = 6.dp))
//                 RouteNavigationUtil.navigationTo(navHostController, destinationName = RouteKey.ARTICLE.toString())
                }

            }
        }


    }
}




//        topBar = {
//            TopAppBar(
//                title = { Text("旅途") },
//                navigationIcon = {
//                    IconButton(onClick = { scope.launch { scaffoldState.drawerState.open() } }) {
//                        Icon(Icons.Default.Menu, contentDescription = "Localized description")
//                    }
//                }
//            )
//        },
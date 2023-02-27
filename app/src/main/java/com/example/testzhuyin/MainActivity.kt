package com.example.testzhuyin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.testzhuyin.ui.page.GroupPage
import com.example.testzhuyin.ui.page.JourneyPage
import com.example.testzhuyin.ui.page.TestPage
import com.example.testzhuyin.ui.theme.TestZHUYINTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestZHUYINTheme {

               MainScaffoldConfig()
 //               GroupPage()

                }
            }
        }
    }


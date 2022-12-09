package com.example.vkcup

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.vkcup.ui.theme.VKCupTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

var categoryList = listOf<Category>(
    Category("News",mutableStateOf(false)),
    Category("Politics",mutableStateOf(false)),
    Category("Work",mutableStateOf(false)),
    Category("Humor",mutableStateOf(false)),

    )

fun onClickCategoryChoiceCard(index:Int){
    categoryList[index].isSubscribe.value =
        sel(categoryList[index].isSubscribe.value != true, true, false)
}
fun<T> sel(condition:Boolean, trueValue:T, falseValue:T):T =
    if(condition) trueValue else falseValue


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VKCupTheme {
                MainView(categoryList)
            }
        }
        delay()
    }

}

fun delay(){

}
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    VKCupTheme {
        MainView(categoryList)
    }
}
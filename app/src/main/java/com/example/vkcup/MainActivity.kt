package com.example.vkcup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.tooling.preview.Preview
import com.example.vkcup.ui.theme.VKCupTheme

var categoryList = listOf(
    Category("News",mutableStateOf(false)),
    Category("Politics",mutableStateOf(false)),
    Category("Work",mutableStateOf(false)),
    Category("Humor",mutableStateOf(false)),

    )

data class Category(val name:String, var isSubscribe: MutableState<Boolean>)

fun onClickCategoryChoiceCard(stateButton:MutableState<AnimationStateButton>, counts:MutableState<Int>){
    counts.value++
    if (stateButton.value == AnimationStateButton.UNCLICKED ||
        stateButton.value == AnimationStateButton.FIRSTTIME
    ) {
        stateButton.value = AnimationStateButton.CLICKED
    } else stateButton.value = AnimationStateButton.UNCLICKED
}

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
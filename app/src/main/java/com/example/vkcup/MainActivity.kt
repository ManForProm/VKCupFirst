package com.example.vkcup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.tooling.preview.Preview
import com.example.vkcup.ui.theme.VKCupTheme

class InitialTopicSelection(){
    val categoryList = mutableListOf(
        Category("News", mutableStateOf(false)),
        Category("Politics", mutableStateOf(false)),
        Category("Work", mutableStateOf(false)),
        Category("Humor", mutableStateOf(false)),
        Category("Serials", mutableStateOf(false)),
        Category("Cars", mutableStateOf(false)),
        Category("Relaxing", mutableStateOf(false)),
        Category("Very long topic for test", mutableStateOf(false)),
        Category("Music", mutableStateOf(false)),
        Category("Films", mutableStateOf(false)),
        Category("TV", mutableStateOf(false)),
        Category("Nature", mutableStateOf(false)),
        Category("Transport", mutableStateOf(false)),
        Category("Education", mutableStateOf(false)),
        Category("Taxi", mutableStateOf(false)),
        Category("Cooking", mutableStateOf(false)),
        Category("DIY", mutableStateOf(false)),
        Category("Sports", mutableStateOf(false)),
        Category("Travel", mutableStateOf(false)),
        Category("Food", mutableStateOf(false)),
        Category("Urban", mutableStateOf(false)),
        Category("Cars", mutableStateOf(false)),
    )

    fun showButton():Boolean{
        return true
    }
    fun onClickCategoryChoiceCard(stateButton:MutableState<AnimationStateButton>, counts:MutableState<Int>){
        counts.value++
        if (stateButton.value == AnimationStateButton.UNCLICKED ||
            stateButton.value == AnimationStateButton.FIRSTTIME
        ) {
            stateButton.value = AnimationStateButton.CLICKED
        } else stateButton.value = AnimationStateButton.UNCLICKED
    }
    fun onClickLater(stateButton:MutableState<AnimationStateButton>, counts:MutableState<Int>){
        onClickCategoryChoiceCard(stateButton,counts)
        TODO() //go to another tab
    }
    fun onClickNext(){
        TODO()
    }

}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val initialTopicSelection = InitialTopicSelection()
        setContent {
            VKCupTheme {
                MainView(initialTopicSelection.categoryList,
                    initialTopicSelection::onClickCategoryChoiceCard,
                    initialTopicSelection::onClickLater,
                    initialTopicSelection::onClickNext,
                    initialTopicSelection.showButton()
                )
            }
        }
    }

}
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val initialTopicSelection = InitialTopicSelection()
    VKCupTheme {
        MainView(initialTopicSelection.categoryList,
                initialTopicSelection::onClickCategoryChoiceCard,
            initialTopicSelection::onClickLater,
            initialTopicSelection::onClickNext,
            initialTopicSelection.showButton())
    }
}
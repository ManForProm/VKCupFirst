package com.example.vkcup

import androidx.compose.animation.*
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.*
import com.example.vkcup.ui.theme.inter

@Composable
fun MainView(categoryList: List<Category>){
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ){
        ListCategory(listCategory = categoryList)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListCategory(listCategory: List<Category>){
//    LazyColumn{
//        LazyRow{
//
//        }
//        items(listCategory){
//            CategoryCard(name = it.name, isSubscribe = it.isSubscribe)
//        }
//    }
    var size = 140.dp
    LazyVerticalGrid(cells = GridCells.Adaptive(size), contentPadding = PaddingValues(
        start = 12.dp,
        top = 16.dp,
        end = 12.dp,
        bottom = 16.dp
    )){
        items(listCategory.size) { index ->
            CategoryCard(name = listCategory[index].name,
                isSubscribe = listCategory[index].isSubscribe,
                index = index)
        }
    }

}

@Composable
fun CategoryCard(name:String,isSubscribe: MutableState<Boolean>,index:Int){
    val paddingModifier = Modifier.padding(12.dp)
    val stateButton =remember{ mutableStateOf(animationStateButton.UNCLICKED) }
    val counts = remember { mutableStateOf(0) }
    val interactionSource = remember { MutableInteractionSource() }
    var tapped by remember { mutableStateOf(false) }
    Card(
        shape = RoundedCornerShape(10.dp),
        elevation = 10.dp,
        modifier = paddingModifier
            .height(IntrinsicSize.Min)
            .fillMaxWidth(),
        backgroundColor = clickableColor(isSubscribe.value),
    ) {
        Row(
            Modifier
                .height(IntrinsicSize.Min) //intrinsic measurements
                .fillMaxWidth()
                .pointerInput(Unit){
                    detectTapGestures(onPress = { offset ->
                        tapped = true

                        val press = PressInteraction.Press(offset)
                        interactionSource.emit(press)

                        tryAwaitRelease()

                        interactionSource.emit(PressInteraction.Release(press))

                        tapped = false
                    })
                }
                .clickable(onClick =  {
                        onClickCategoryChoiceCard(index)
                        counts.value++
                        if (stateButton.value == animationStateButton.UNCLICKED ||
                            stateButton.value == animationStateButton.FIRSTTIME
                        ) {
                            stateButton.value = animationStateButton.CLICKED
                        } else stateButton.value = animationStateButton.UNCLICKED
                    }),
        Arrangement.Center) {
            Text(text = name,
                modifier = paddingModifier,
                fontFamily = inter,
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp,
            )
            AnimatedVisibaleDivider(visibility = !isSubscribe.value,
                modifier = paddingModifier)
            addAnimation(
                modifier = Modifier
                    .padding(18.dp)
                    .height(20.dp)
                    .scale(10f),
                stateButton = stateButton,
                counts = counts
            )
        }
    }
}
@Composable
fun AnimatedVisibaleDivider(visibility:Boolean,
                            modifier:Modifier = Modifier){
    AnimatedVisibility(visible = visibility,
        enter = expandVertically() + fadeIn(),
        exit = shrinkVertically() + fadeOut()
    ) {
        Divider(
            color = Color(com.airbnb.lottie.R.color.dim_foreground_disabled_material_dark),
            modifier = modifier
                .fillMaxHeight()  //fill the max height
                .width(1.dp)
        )
    }
}
enum class animationStateButton{
    CLICKED,UNCLICKED,FIRSTTIME
}

@Composable
fun calculateAnimation(stateButton: MutableState<animationStateButton>,
                       counts:MutableState<Int>,
                       composition: LottieComposition?
):LottieAnimationState{
    if(counts.value == 0){
        stateButton.value = animationStateButton.FIRSTTIME
    }
    return  when(stateButton.value){
        animationStateButton.CLICKED -> animateLottieCompositionAsState(composition = composition,
            isPlaying = stateButton.value == animationStateButton.CLICKED ,
            clipSpec = LottieClipSpec.Progress(0.0f, 0.5f),
        )
        animationStateButton.UNCLICKED -> animateLottieCompositionAsState(composition = composition,
            isPlaying = stateButton.value == animationStateButton.UNCLICKED ,
            clipSpec = LottieClipSpec.Progress(0.5f, 1f),
        )
        animationStateButton.FIRSTTIME -> animateLottieCompositionAsState(composition = composition,
            isPlaying = false ,
            clipSpec = LottieClipSpec.Progress(0.0f, 0.0f),
        )
    }
}
@Composable
fun addAnimation(modifier:Modifier = Modifier,
                 stateButton: MutableState<animationStateButton>,
                 counts:MutableState<Int>,
){
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.add_category_animation))
    val progress = calculateAnimation(stateButton = stateButton,
        counts = counts,
        composition = composition)
    LottieAnimation(composition = composition,
        progress = { progress.value },
        modifier = modifier)
}

@Composable
fun clickableColor(isSubscribe: Boolean):Color{
    val color = animateColorAsState(
        if (isSubscribe) Color.Yellow else Color.Gray,
    )
    
    return color.value
}
data class Category(val name:String, var isSubscribe: MutableState<Boolean>)
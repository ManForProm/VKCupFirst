package com.example.vkcup

import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.*
import com.example.vkcup.ui.theme.inter
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment
import com.google.accompanist.flowlayout.SizeMode

@Composable
fun MainView(
    categoryList: List<Category>,
    onClickCategory: (
        stateButton: MutableState<AnimationStateButton>,
        counts: MutableState<Int>
    ) -> Unit,
    onClickLater: (
        stateButton: MutableState<AnimationStateButton>,
        counts: MutableState<Int>
    ) -> Unit,
    onClickNext: () -> Unit,
    showButton: Boolean
) {
    Scaffold(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        backgroundColor = Color.Black,
        topBar = {
            TopOfListCategory(
                modifier = Modifier.padding(12.dp),
                text = "Mark what you are interested in to set up zen",
                buttonText = "Later", onClick = onClickLater
            )
        },
        bottomBar = {
            Row(modifier = Modifier.fillMaxWidth(), Arrangement.Center){
                PoopingUpButton(modifier = Modifier,
                    // visibility = showButton,
                    text = "Next",
                    onClick = onClickNext)
            }
        }
    ) {
            Column(modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally) {
                ListCategory(listCategory = categoryList, onClickCategory = onClickCategory,)
        }
    }
}

@Composable
fun TopOfListCategory(
    modifier: Modifier, text: String, buttonText: String,
    onClick: (
        stateButton: MutableState<AnimationStateButton>,
        counts: MutableState<Int>
    ) -> Unit
) {
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Box(Modifier.weight(2f)) {
            VisibleText(
                text = text,
                visibility = true,
                modifier = Modifier.padding(12.dp),
                color = Color.Gray,
                fontFamily = inter,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp
            )
        }
        Box(Modifier.weight(1f)){
            CategoryCard(
                name = buttonText, devider = false, plusIcon = false,
                modifier = Modifier
                    .padding(top = 12.dp)
                    .height(IntrinsicSize.Min)
                    .width(100.dp)
                    .border(
                        width = 1.dp,
                        color = Color.LightGray,
                        shape = RoundedCornerShape(10.dp)
                    ),
                onClickCategory = onClick
            )
        }
    }
}

@Composable
fun PoopingUpButton(modifier: Modifier, visibility: Boolean = true,
                    text: String = "Next",
                    onClick: () -> Unit) {
    val animationExpandShrinkSpec = tween<IntSize>(durationMillis = 200)
    AnimatedVisibility(
        visible = visibility,
        enter = expandVertically(
            animationSpec = animationExpandShrinkSpec,
            expandFrom = Alignment.Top
        ) + fadeIn(),
        exit = shrinkVertically(
            animationSpec = animationExpandShrinkSpec,
            shrinkTowards = Alignment.Top
        ) + fadeOut()
    ) {
        Button(modifier = modifier,
            onClick = { onClick() },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
            shape = RoundedCornerShape(12.dp)
        ) {
            VisibleText(
                text = text,
                visibility = true,
                modifier = Modifier.padding(top = 7.dp, bottom = 7.dp,start = 20.dp, end = 20.dp),
                color = Color.Gray,
                fontFamily = inter,
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp
            )
        }
    }

}

@Composable
fun ListCategory(
    listCategory: List<Category>,
    onClickCategory: (
        stateButton: MutableState<AnimationStateButton>,
        counts: MutableState<Int>
    ) -> Unit
) {
    FlowRow(
        modifier = Modifier.padding(8.dp),
        mainAxisAlignment = MainAxisAlignment.Start,
        mainAxisSize = SizeMode.Wrap,
        crossAxisSpacing = 12.dp,
        mainAxisSpacing = 8.dp
    ) {
        listCategory.forEachIndexed { index, category ->
            CategoryCard(
                name = listCategory[index].name, modifier = Modifier
                    .height(IntrinsicSize.Min)
                    .width(IntrinsicSize.Max),
                onClickCategory = onClickCategory
            )
        }
    }
}

@Composable
fun CategoryCard(
    name: String, shape: RoundedCornerShape = RoundedCornerShape(10.dp),
    modifier: Modifier,
    devider: Boolean = true,
    text: Boolean = true,
    plusIcon: Boolean = true,
    onClickCategory: (
        stateButton: MutableState<AnimationStateButton>,
        counts: MutableState<Int>
    ) -> Unit
) {
    val paddingModifier = Modifier.padding(top = 12.dp, bottom = 12.dp)
    val stateButton = remember { mutableStateOf(AnimationStateButton.UNCLICKED) }
    val counts = remember { mutableStateOf(0) }
    val offsetTransfer = remember {
        mutableStateOf(
            Offset(
                (0..500).random().toFloat(),
                (0..100).random().toFloat()
            )
        )
    }
    Card(
        modifier = modifier,
        backgroundColor = Color.DarkGray,
        shape = shape,
        elevation = 10.dp

    ) {
        BackgroundColorCircle(
            visibility = stateButton.value.boolean,
            offsetTransfer, color = Color(0xE0FF5317)
        )
        Row(
            Modifier
                .pointerInput(Unit) {
                    detectTapGestures(onTap = { offset ->
                        offsetTransfer.value = offset
                    })
                }
                .clickable(onClick = { onClickCategory(stateButton, counts) }),
            Arrangement.Center
        ) {
            VisibleText(
                text = name,
                visibility = text,
                modifier = paddingModifier.padding(end = 12.dp, start = 10.dp),
                color = Color.White,
                fontFamily = inter,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp
            )
            AnimatedVisibaleDivider(
                visibility = !stateButton.value.boolean && devider,
                modifier = paddingModifier.padding(end = 12.dp)
            )
            AddIconAnimation(
                modifier = paddingModifier
                    .padding(top = 2.dp, end = 12.dp)
                    .height(20.dp)
                    .width(20.dp)
                    .scale(6f),
                visibility = plusIcon,
                stateButton = stateButton,
                counts = counts
            )
        }
    }
}

@Composable
fun BackgroundColorCircle(
    visibility: Boolean,
    offset: MutableState<Offset>,
    color: Color,
    sizeCircle: Float = 1000f
) {
    val animationTargetState = remember { mutableStateOf(0f) }

    if (visibility) animationTargetState.value = sizeCircle else animationTargetState.value = 0f
    val animatedFloatState = animateFloatAsState(
        targetValue = animationTargetState.value,
        animationSpec = tween(durationMillis = 500)
    )
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer(alpha = 0.99f)
    ) {
        drawCircle(
            color = color,
            radius = animatedFloatState.value,
            center = offset.value,
            blendMode = BlendMode.Xor
        )
    }
}

@Composable
fun VisibleText(
    text: String, visibility: Boolean, modifier: Modifier, color: Color,
    fontFamily: FontFamily, fontWeight: FontWeight, fontSize: TextUnit
) {
    AnimatedVisibility(
        visible = visibility,
    ) {
        Text(
            text = text,
            modifier = modifier,
            color = color,
            fontFamily = fontFamily,
            fontWeight = fontWeight,
            fontSize = fontSize,
        )
    }
}

@Composable
fun AnimatedVisibaleDivider(
    visibility: Boolean,
    modifier: Modifier = Modifier
) {
    val animationExpandShrinkSpec = tween<IntSize>(durationMillis = 100)
    AnimatedVisibility(
        visible = visibility,
        enter = expandVertically(animationSpec = animationExpandShrinkSpec) + fadeIn(),
        exit = shrinkVertically(animationSpec = animationExpandShrinkSpec) + fadeOut()
    ) {
        Divider(
            color = Color.White,
            modifier = modifier
                .fillMaxHeight()
                .width(1.dp)
        )
    }
}

enum class AnimationStateButton(val boolean: Boolean) {
    CLICKED(true), UNCLICKED(false), FIRSTTIME(false)
}

@Composable
fun calculateAnimation(
    stateButton: MutableState<AnimationStateButton>,
    counts: MutableState<Int>,
    composition: LottieComposition?
): LottieAnimationState {
    if (counts.value == 0) {
        stateButton.value = AnimationStateButton.FIRSTTIME
    }
    return when (stateButton.value) {
        AnimationStateButton.CLICKED -> animateLottieCompositionAsStateAddSettings(
            composition = composition,
            clipSpec = LottieClipSpec.Progress(0.0f, 0.5f)
        )
        AnimationStateButton.UNCLICKED -> animateLottieCompositionAsStateAddSettings(
            composition = composition,
            clipSpec = LottieClipSpec.Progress(0.5f, 1f)
        )
        AnimationStateButton.FIRSTTIME -> animateLottieCompositionAsStateAddSettings(
            composition = composition,
            isPlaying = false,
            clipSpec = LottieClipSpec.Progress(0.0f, 0.0f)
        )
    }
}

@Composable
fun animateLottieCompositionAsStateAddSettings(
    composition: LottieComposition?, isPlaying: Boolean = true,
    clipSpec: LottieClipSpec?
): LottieAnimationState =
    animateLottieCompositionAsState(
        composition = composition,
        isPlaying = isPlaying,
        clipSpec = clipSpec,
        speed = 1.5f
    )

@Composable
fun AddIconAnimation(
    modifier: Modifier = Modifier,
    visibility: Boolean,
    stateButton: MutableState<AnimationStateButton>,
    counts: MutableState<Int>,
) {
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.add_category_animation)
    )
    val progress = calculateAnimation(
        stateButton = stateButton,
        counts = counts,
        composition = composition
    )
    AnimatedVisibility(
        visible = visibility,
    ) {
        LottieAnimation(
            composition = composition,
            progress = { progress.value },
            modifier = modifier
        )
    }

}

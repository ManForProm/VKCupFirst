package com.example.vkcup

import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.*
import com.example.vkcup.ui.theme.inter

@Composable
fun MainView(categoryList: List<Category>) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        ListCategory(listCategory = categoryList)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListCategory(listCategory: List<Category>) {
    val size = 160.dp
    LazyVerticalGrid(
        cells = GridCells.Adaptive(size), contentPadding = PaddingValues(
            start = 12.dp,
            top = 16.dp,
            end = 12.dp,
            bottom = 16.dp
        )
    ) {
        items(listCategory.size) { index ->
            CategoryCard(
                name = listCategory[index].name,
            )
        }
    }

}

@Composable
fun CategoryCard(name: String,) {
    val paddingModifier = Modifier.padding(12.dp)
    val stateButton = remember { mutableStateOf(AnimationStateButton.UNCLICKED) }
    val counts = remember { mutableStateOf(0) }
    val offsetTransfer = remember { mutableStateOf(Offset((0 .. 500).random().toFloat(),
        (0 .. 100).random().toFloat())) }
    Card(
        shape = RoundedCornerShape(10.dp),
        elevation = 10.dp,
        modifier = paddingModifier
            .height(IntrinsicSize.Min)
            .fillMaxWidth(),
        backgroundColor = Color.Gray
    ) {
        BackgroundColorCircle(visibility = stateButton.value.boolean, offsetTransfer)
        Row(
            Modifier
                .height(IntrinsicSize.Min)
                .fillMaxWidth()
                .pointerInput(Unit) {
                    detectTapGestures(onTap = { offset ->
                            offsetTransfer.value = offset
                    })
                }
                .clickable(onClick = { onClickCategoryChoiceCard(stateButton, counts) }),
            Arrangement.Center
        ) {
            Text(
                text = name,
                modifier = paddingModifier,
                fontFamily = inter,
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp,
            )
            AnimatedVisibaleDivider(
                visibility = !stateButton.value.boolean,
                modifier = paddingModifier
            )
            AddIconAnimation(
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
fun BackgroundColorCircle(visibility: Boolean, offset: MutableState<Offset>,) {
    val animationTargetState = remember { mutableStateOf(0f) }

    if(visibility) animationTargetState.value = 500f else animationTargetState.value = 0f
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
            color = Color.Yellow,
            radius = animatedFloatState.value,
            center = offset.value,
            blendMode = BlendMode.Xor
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
            color = Color(com.airbnb.lottie.R.color.dim_foreground_disabled_material_dark),
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
    LottieAnimation(
        composition = composition,
        progress = { progress.value },
        modifier = modifier
    )
}

package com.example.vkcup.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.vkcup.R

// Set of Material typography styles to start with
val inter = FontFamily(
                Font(R.font.inter_black, FontWeight.Black),
                Font(R.font.inter_bold, FontWeight.Bold),
                Font(R.font.inter_extrabold, FontWeight.ExtraBold),
                Font(R.font.inter_extralight, FontWeight.ExtraLight),
                Font(R.font.inter_light, FontWeight.Light),
                Font(R.font.inter_medium, FontWeight.Medium),
                Font(R.font.inter_semibold, FontWeight.SemiBold),
                Font(R.font.inter_thin, FontWeight.Thin),
)
val Typography = Typography(
        body1 = TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
        )
        /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)
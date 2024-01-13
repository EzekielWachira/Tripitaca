package com.ezzy.designsystem.theme


import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.ezzy.designsystem.R
import com.ezzy.designsystem.utils.SpDimensions


val fontFamily = FontFamily(
    Font(R.font.urbanist_bold, FontWeight.Bold),
    Font(R.font.urbanist_semibold, FontWeight.SemiBold),
    Font(R.font.urbanist_extrabold, FontWeight.ExtraBold),
    Font(R.font.urbanist_light, FontWeight.Light),
    Font(R.font.urbanist_medium, FontWeight.Medium),
    Font(R.font.urbanist_regular, FontWeight.Normal),
    Font(R.font.urbanist_thin, FontWeight.Thin),
)

val Typography = Typography(
    headlineLarge = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = SpDimensions.HeadlineLarge,
        lineHeight = 28.sp,
        letterSpacing = 0.5.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = SpDimensions.BodyLarge,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = SpDimensions.BodyMedium,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    bodySmall = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = SpDimensions.BodySmall,
        lineHeight = 20.sp,
        letterSpacing = 0.5.sp
    ),
    titleMedium = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = SpDimensions.TitleMedium,
        lineHeight = 22.sp,
        letterSpacing = 0.5.sp
    ),
    titleLarge = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = SpDimensions.TitleLarge,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = SpDimensions.HeadlineMedium,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    titleSmall = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = SpDimensions.TitleSmall,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    headlineSmall =TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = SpDimensions.HeadlineSmall,
        lineHeight = 22.sp,
        letterSpacing = 0.5.sp
    )
)
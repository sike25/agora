package hu.ait.agora.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import hu.ait.agora.R

val interFamily = FontFamily (
    Font(R.font.inter_black),
    Font(R.font.inter_bold),
    Font(R.font.inter_extrabold),
    Font(R.font.inter_extralight),
    Font(R.font.inter_light),
    Font(R.font.inter_medium),
    Font(R.font.inter_regular),
    Font(R.font.inter_semibold),
    Font(R.font.inter_thin),
)

val interFamilyRegular = FontFamily (Font(R.font.inter_regular))
val interFamilyLight = FontFamily (Font(R.font.inter_light))
val interFamilyBold = FontFamily (Font(R.font.inter_semibold))

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )

)
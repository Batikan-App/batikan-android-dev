package com.example.batikan.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.example.batikan.R

val fontProvider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

// Fungsi untuk membuat FontFamily dari Google Fonts
fun googleFont(familyName: String, weight: FontWeight) = FontFamily(
    Font(
        googleFont = GoogleFont(familyName),
        fontProvider = fontProvider,
        weight = weight
    )
)


// Display 2xl
val Display2xlRegular = TextStyle(
    fontFamily = googleFont("Inter", FontWeight.W400),
    fontWeight = FontWeight.W400,
    fontSize = 72.sp,
    letterSpacing = (-0.02).em
)

val Display2xlMedium = TextStyle(
    fontFamily = googleFont("Inter", FontWeight.W500),
    fontWeight = FontWeight.W500,
    fontSize = 72.sp,
    letterSpacing = (-0.02).em
)

val Display2xlSemiBold = TextStyle(
    fontFamily = googleFont("Inter", FontWeight.W600),
    fontWeight = FontWeight.W600,
    fontSize = 72.sp,
    letterSpacing = (-0.02).em
)

val Display2xlBold = TextStyle(
    fontFamily = googleFont("Inter", FontWeight.W700),
    fontWeight = FontWeight.W700,
    fontSize = 72.sp,
    letterSpacing = (-0.02).em
)

// Display xl
val DisplayXlRegular = TextStyle(
    fontFamily = googleFont("Inter", FontWeight.W400),
    fontWeight = FontWeight.W400,
    fontSize = 60.sp,
    letterSpacing = (-0.02).em
)

val DisplayXlMedium = TextStyle(
    fontFamily = googleFont("Inter", FontWeight.W500),
    fontWeight = FontWeight.W500,
    fontSize = 60.sp,
    letterSpacing = (-0.02).em
)

val DisplayXlSemiBold = TextStyle(
    fontFamily = googleFont("Inter", FontWeight.W600),
    fontWeight = FontWeight.W600,
    fontSize = 60.sp,
    letterSpacing = (-0.02).em
)

val DisplayXlBold = TextStyle(
    fontFamily = googleFont("Inter", FontWeight.W700),
    fontWeight = FontWeight.W700,
    fontSize = 60.sp,
    letterSpacing = (-0.02).em
)

// Display lg
val DisplayLgRegular = TextStyle(
    fontFamily = googleFont("Inter", FontWeight.W400),
    fontWeight = FontWeight.W400,
    fontSize = 48.sp,
    letterSpacing = (-0.02).em
)

val DisplayLgMedium = TextStyle(
    fontFamily = googleFont("Inter", FontWeight.W500),
    fontWeight = FontWeight.W500,
    fontSize = 48.sp,
    letterSpacing = (-0.02).em
)

val DisplayLgSemiBold = TextStyle(
    fontFamily = googleFont("Inter", FontWeight.W600),
    fontWeight = FontWeight.W600,
    fontSize = 48.sp,
    letterSpacing = (-0.02).em
)

val DisplayLgBold = TextStyle(
    fontFamily = googleFont("Inter", FontWeight.W700),
    fontWeight = FontWeight.W700,
    fontSize = 48.sp,
    letterSpacing = (-0.02).em
)

// Display md
val DisplayMdRegular = TextStyle(
    fontFamily = googleFont("Inter", FontWeight.W400),
    fontWeight = FontWeight.W400,
    fontSize = 36.sp,
    letterSpacing = (-0.02).em
)

val DisplayMdMedium = TextStyle(
    fontFamily = googleFont("Inter", FontWeight.W500),
    fontWeight = FontWeight.W500,
    fontSize = 36.sp,
    letterSpacing = (-0.02).em
)

val DisplayMdSemiBold = TextStyle(
    fontFamily = googleFont("Inter", FontWeight.W600),
    fontWeight = FontWeight.W600,
    fontSize = 36.sp,
    letterSpacing = (-0.02).em
)

val DisplayMdBold = TextStyle(
    fontFamily = googleFont("Inter", FontWeight.W700),
    fontWeight = FontWeight.W700,
    fontSize = 36.sp,
    letterSpacing = (-0.02).em
)

// Display sm
val DisplaySmRegular = TextStyle(
    fontFamily = googleFont("Inter", FontWeight.W400),
    fontWeight = FontWeight.W400,
    fontSize = 30.sp,
    letterSpacing = 0.sp
)

val DisplaySmMedium = TextStyle(
    fontFamily = googleFont("Inter", FontWeight.W500),
    fontWeight = FontWeight.W500,
    fontSize = 30.sp,
    letterSpacing = 0.sp
)

val DisplaySmSemiBold = TextStyle(
    fontFamily = googleFont("Inter", FontWeight.W600),
    fontWeight = FontWeight.W600,
    fontSize = 30.sp,
    letterSpacing = 0.sp
)

val DisplaySmBold = TextStyle(
    fontFamily = googleFont("Inter", FontWeight.W700),
    fontWeight = FontWeight.W700,
    fontSize = 30.sp,
    letterSpacing = 0.sp
)

// Display xs
val DisplayXsRegular = TextStyle(
    fontFamily = googleFont("Inter", FontWeight.W400),
    fontWeight = FontWeight.W400,
    fontSize = 24.sp,
    letterSpacing = 0.sp
)

val DisplayXsMedium = TextStyle(
    fontFamily = googleFont("Inter", FontWeight.W500),
    fontWeight = FontWeight.W500,
    fontSize = 24.sp,
    letterSpacing = 0.sp
)

val DisplayXsSemiBold = TextStyle(
    fontFamily = googleFont("Inter", FontWeight.W600),
    fontWeight = FontWeight.W600,
    fontSize = 24.sp,
    letterSpacing = 0.sp
)

val DisplayXsBold = TextStyle(
    fontFamily = googleFont("Inter", FontWeight.W700),
    fontWeight = FontWeight.W700,
    fontSize = 24.sp,
    letterSpacing = 0.sp
)

// Text xl
val TextXlRegular = TextStyle(
    fontFamily = googleFont("Inter", FontWeight.W400),
    fontWeight = FontWeight.W400,
    fontSize = 20.sp,
    letterSpacing = 0.sp
)

val TextXlMedium = TextStyle(
    fontFamily = googleFont("Inter", FontWeight.W500),
    fontWeight = FontWeight.W500,
    fontSize = 20.sp,
    letterSpacing = 0.sp
)

val TextXlSemiBold = TextStyle(
    fontFamily = googleFont("Inter", FontWeight.W600),
    fontWeight = FontWeight.W600,
    fontSize = 20.sp,
    letterSpacing = 0.sp
)

val TextXlBold = TextStyle(
    fontFamily = googleFont("Inter", FontWeight.W700),
    fontWeight = FontWeight.W700,
    fontSize = 20.sp,
    letterSpacing = 0.sp
)

// Text lg
val TextLgRegular = TextStyle(
    fontFamily = googleFont("Inter", FontWeight.W400),
    fontWeight = FontWeight.W400,
    fontSize = 18.sp,
    letterSpacing = 0.sp
)

val TextLgMedium = TextStyle(
    fontFamily = googleFont("Inter", FontWeight.W500),
    fontWeight = FontWeight.W500,
    fontSize = 18.sp,
    letterSpacing = 0.sp
)

val TextLgSemiBold = TextStyle(
    fontFamily = googleFont("Inter", FontWeight.W600),
    fontWeight = FontWeight.W600,
    fontSize = 18.sp,
    letterSpacing = 0.sp
)

val TextLgBold = TextStyle(
    fontFamily = googleFont("Inter", FontWeight.W700),
    fontWeight = FontWeight.W700,
    fontSize = 18.sp,
    letterSpacing = 0.sp
)

// Text md
val TextMdRegular = TextStyle(
    fontFamily = googleFont("Inter", FontWeight.W400),
    fontWeight = FontWeight.W400,
    fontSize = 16.sp,
    letterSpacing = 0.sp
)

val TextMdMedium = TextStyle(
    fontFamily = googleFont("Inter", FontWeight.W500),
    fontWeight = FontWeight.W500,
    fontSize = 16.sp,
    letterSpacing = 0.sp
)

val TextMdSemiBold = TextStyle(
    fontFamily = googleFont("Inter", FontWeight.W600),
    fontWeight = FontWeight.W600,
    fontSize = 16.sp,
    letterSpacing = 0.sp
)

val TextMdBold = TextStyle(
    fontFamily = googleFont("Inter", FontWeight.W700),
    fontWeight = FontWeight.W700,
    fontSize = 16.sp,
    letterSpacing = 0.sp
)

// Text small
val TextSmallRegular = TextStyle(
    fontFamily = googleFont("Inter", FontWeight.W400),
    fontWeight = FontWeight.W400,
    fontSize = 14.sp,
    letterSpacing = 0.sp
)

val TextSmallMedium = TextStyle(
    fontFamily = googleFont("Inter", FontWeight.W500),
    fontWeight = FontWeight.W500,
    fontSize = 14.sp,
    letterSpacing = 0.sp
)

val TextSmallSemiBold = TextStyle(
    fontFamily = googleFont("Inter", FontWeight.W600),
    fontWeight = FontWeight.W600,
    fontSize = 14.sp,
    letterSpacing = 0.sp
)

val TextSmallBold = TextStyle(
    fontFamily = googleFont("Inter", FontWeight.W700),
    fontWeight = FontWeight.W700,
    fontSize = 14.sp,
    letterSpacing = 0.sp
)

// Text xs
val TextXsRegular = TextStyle(
    fontFamily = googleFont("Inter", FontWeight.W400),
    fontWeight = FontWeight.W400,
    fontSize = 12.sp,
    letterSpacing = 0.sp
)

val TextXsMedium = TextStyle(
    fontFamily = googleFont("Inter", FontWeight.W500),
    fontWeight = FontWeight.W500,
    fontSize = 12.sp,
    letterSpacing = 0.sp
)

val TextXsSemiBold = TextStyle(
    fontFamily = googleFont("Inter", FontWeight.W600),
    fontWeight = FontWeight.W600,
    fontSize = 12.sp,
    letterSpacing = 0.sp
)

val TextXsBold = TextStyle(
    fontFamily = googleFont("Inter", FontWeight.W700),
    fontWeight = FontWeight.W700,
    fontSize = 12.sp,
    letterSpacing = 0.sp
)


// Set of Material typography styles to start with
val Typography = Typography(
//    bodyLarge = Display2xlRegular,
//    Display2xlMedium = Display2xlMedium,
//    Display2xlSemiBold = Display2xlSemiBold,
//    Display2xlBold = Display2xlBold,


)
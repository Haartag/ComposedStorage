package com.valerytimofeev.composedstorage.about

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.valerytimofeev.composedstorage.R
import com.valerytimofeev.composedstorage.common.TopBar

@Composable
fun AboutScreen(
    navController: NavController
) {
    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            TopBar(
                title = stringResource(R.string.about_title),
                buttonIcon = Icons.Filled.ArrowBack,
                onButtonClicked = { navController.popBackStack() },
            )

            Text(
                modifier = Modifier.padding(16.dp),
                text = "What's new in this version: \n Nothing."
            )
            
            Spacer(modifier = Modifier.height(20.dp))

            val gitHubUrl = "https://github.com/Haartag/ComposedStorage"
            val pixabayUrl = "https://pixabay.com/"
            val uriHandler = LocalUriHandler.current

            val aboutMainText = buildAnnotatedString {
                append("The photos in this app are received from ")

                pushStringAnnotation(tag = "pixabay", annotation = pixabayUrl)
                withStyle(
                    style = SpanStyle(
                        color = Color(0xff64B5F6),
                        fontSize = 16.sp,
                        textDecoration = TextDecoration.Underline
                    )
                ) {
                    append("Pixabay")
                }

                append("\n\n\n\n\n")

                pushStringAnnotation(tag = "GitHub", annotation = gitHubUrl)
                withStyle(
                    style = SpanStyle(
                        color = Color(0xff64B5F6),
                        fontSize = 16.sp,
                        textDecoration = TextDecoration.Underline
                    )
                ) {
                    append("App`s GitHub repository")
                }
            }
            ClickableText(
                modifier = Modifier.padding(16.dp),
                text = aboutMainText,
                onClick = {
                    aboutMainText.getStringAnnotations(tag = "pixabay", start = it, end = it)
                        .firstOrNull()?.let { url ->
                            uriHandler.openUri(url.item)
                        }
                    aboutMainText.getStringAnnotations(tag = "GitHub", start = it, end = it)
                        .firstOrNull()?.let { url ->
                            uriHandler.openUri(url.item)
                        }
                })
            
            Spacer(modifier = Modifier.height(20.dp))
            
            Text(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                text = "Application version: 0.1",
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                color = Color.Gray
            )
        }
    }
}
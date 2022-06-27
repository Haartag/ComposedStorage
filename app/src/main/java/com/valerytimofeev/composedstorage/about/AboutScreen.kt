package com.valerytimofeev.composedstorage.about

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.valerytimofeev.composedstorage.BuildConfig
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
                .fillMaxSize(),
        ) {
            TopBar(
                title = stringResource(R.string.about_title),
                buttonIcon = Icons.Filled.ArrowBack,
                onButtonClicked = { navController.popBackStack() },
            )
            Spacer(modifier = Modifier.height(24.dp))

            //app logo
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    modifier = Modifier.size(width = 64.dp, height = 108.dp),
                    painter = painterResource(id = R.drawable.ic__3),
                    contentDescription = "App icon",
                    tint = Color.Red,
                )
                Text(
                    modifier = Modifier.offset(x = (-12).dp),
                    text = "akroma",
                    style = MaterialTheme.typography.h4,
                    fontWeight = FontWeight.Light
                )
            }
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                //What`s new
                Column {
                    Text(
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.body1,
                        text = "What's new in this version:"
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.body2,
                        text = "– \n"
                                //"– fixed tab color`s bug; \n" +
                                //"– some UI improvement; \n" +
                                //"– reduce app size."
                    )
                }

                //links in bottom
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    val gitHubUrl = "https://github.com/Haartag/ComposedStorage"
                    val pixabayUrl = "https://pixabay.com/"
                    val uriHandler = LocalUriHandler.current

                    val pixabayText = buildAnnotatedString {
                        append("The photos in this app are received from ")

                        pushStringAnnotation(tag = "pixabay", annotation = pixabayUrl)
                        withStyle(
                            style = SpanStyle(
                                color = Color(0xff64B5F6),
                                //fontSize = 16.sp,
                                textDecoration = TextDecoration.Underline
                            )
                        ) {
                            append("Pixabay")
                        }
                    }
                    ClickableText(
                        modifier = Modifier.padding(16.dp),
                        text = pixabayText,
                        style = MaterialTheme.typography.body2,
                        onClick = {
                            pixabayText.getStringAnnotations(
                                tag = "pixabay",
                                start = it,
                                end = it
                            )
                                .firstOrNull()?.let { url ->
                                    uriHandler.openUri(url.item)
                                }
                        })
                    Spacer(modifier = Modifier.height(8.dp))
                    ClickableText(
                        text = AnnotatedString("App`s GitHub repository"),
                        style = MaterialTheme.typography.body2.merge(
                            TextStyle(
                                color = Color(0xff64B5F6),
                                textDecoration = TextDecoration.Underline
                            )
                        ),
                        onClick = {
                            uriHandler.openUri(gitHubUrl)
                        })
                    Spacer(modifier = Modifier.height(20.dp))
                    ClickableText(
                        text = AnnotatedString("Open source libraries used in this app."),
                        style = MaterialTheme.typography.caption.merge(
                            TextStyle(
                                textAlign = TextAlign.Center,
                                textDecoration = TextDecoration.Underline,
                                color = Color.DarkGray
                            )
                        ),
                        onClick = {
                            navController.navigate("about_licenses")
                        })
                    Text(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        text = "Application version: ${BuildConfig.VERSION_NAME}",
                        style = MaterialTheme.typography.caption,
                        textAlign = TextAlign.Center,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        }
    }
}
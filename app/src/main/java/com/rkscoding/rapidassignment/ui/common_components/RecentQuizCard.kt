package com.rkscoding.rapidassignment.ui.common_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rkscoding.rapidassignment.R
import com.rkscoding.rapidassignment.ui.theme.darkRed
import com.rkscoding.rapidassignment.ui.theme.indigo
import com.rkscoding.rapidassignment.ui.theme.rose
import com.rkscoding.rapidassignment.ui.theme.roseSoft

@Composable
fun RecentQuizCard(modifier: Modifier = Modifier, onClick:()-> Unit) {
    val percentage = rememberSaveable { mutableStateOf(50) }
    val score = rememberSaveable { mutableStateOf(20) }
    val totalMarks = rememberSaveable { mutableStateOf(100) }
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(176.dp)
            .clip(RoundedCornerShape(24.dp))
            .clickable {
                onClick.invoke()
            }
            .background(roseSoft)
    ) {
        Row(
            modifier = modifier.padding(16.dp)
        ) {
            Column(
                modifier = modifier.width(192.dp)
            ) {
                Box(
                    modifier = modifier
                        .wrapContentSize()
                        .clip(RoundedCornerShape(12.dp))
                        .background(rose)
                ) {
                    Text(
                        text = "RECENT QUIZ",
                        style = TextStyle(
                            fontSize = MaterialTheme.typography.titleSmall.fontSize,
                            fontWeight = FontWeight.Medium,
                            color = Color.White
                        ),
                        modifier = modifier.padding(8.dp)
                    )
                }
                Spacer(modifier = modifier.weight(1f))
                // Subject
                Text(
                    text = "Science ",
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.labelLarge.fontSize,
                        fontWeight = FontWeight.Bold,
                        color = darkRed
                    )
                )
                // Topic
                Spacer(modifier = modifier.size(8.dp))
                Text(
                    text = "Science Technology",
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.labelMedium.fontSize,
                        fontWeight = FontWeight.Bold,

                        color = darkRed
                    )
                )

            }
            Spacer(modifier = modifier.weight(1f))
            Column {
                // Percentage
                Box(
                    modifier = modifier
                        .width(136.dp)
                        .weight(1f)
                        .border(width = 2.dp, shape = RoundedCornerShape(12.dp), color = indigo)
                        .clip(RoundedCornerShape(12.dp))
                        .background(indigo),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.explode_bg_indigo),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        alpha = .6f
                    )
                    Text(
                        text = "${percentage.value}", style = TextStyle(
                            fontSize = MaterialTheme.typography.displayMedium.fontSize,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 4.sp,
                            color = Color.White
                        )
                    )
                    Text(
                        text = "%", style = TextStyle(
                            fontSize = MaterialTheme.typography.titleLarge.fontSize,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White
                        ),
                        modifier = modifier
                            .align(Alignment.TopEnd)
                            .padding(top = 4.dp, end = 8.dp)
                    )
                }
                Spacer(modifier = modifier.size(16.dp))
                // mark scored
                Box(
                    modifier = modifier
                        .width(136.dp)
                        .weight(1f)
                        .border(width = 2.dp, shape = RoundedCornerShape(12.dp), color = indigo)
                        .clip(RoundedCornerShape(12.dp))
                        .background(indigo),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.explode_bg_indigo),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        alpha = .6f
                    )
                    Text(
                        text = "${score.value}", style = TextStyle(
                            fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        ),
                        modifier = modifier
                            .align(Alignment.TopStart)
                            .padding(start = 8.dp, top = 4.dp)
                    )
                    Text(
                        text = " /", style = TextStyle(
                            fontSize = MaterialTheme.typography.displayMedium.fontSize,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White
                        ),
                        modifier = modifier
                            .align(Alignment.Center)
                            .padding(end = 8.dp, bottom = 4.dp)
                    )
                    Text(
                        text = "${totalMarks.value}", style = TextStyle(
                            fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        ),
                        modifier = modifier
                            .align(Alignment.BottomEnd)
                            .padding(end = 8.dp, bottom = 4.dp)
                    )
                }
            }
        }
    }
}
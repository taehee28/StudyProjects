package com.thk.compose_parallelloading.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thk.compose_parallelloading.model.AppItem
import com.thk.compose_parallelloading.model.TaskStatus

@Composable
fun ListItem(
    item: AppItem,
    background: Color,
    onItemClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(background)
            .clickable { onItemClick() },
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = item.text,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp),
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
        )

        Box(
            modifier = Modifier.size(60.dp),
            contentAlignment = Alignment.Center
        ) {
            // 상태가 IN_PROGRESS라면 인디케이터를 표시
            if (item.taskStatus == TaskStatus.IN_PROGRESS) {
                CircularProgressIndicator(
                    modifier = Modifier.size(30.dp),
                    strokeWidth = 3.dp,
                    color = Color.Magenta
                )
            } else {
                val icon: ImageVector
                val tintColor: Color

                // task를 실행한 적 없으면 play 아이콘을,
                // 아니면 완료 아이콘을 표시
                if (item.taskStatus == TaskStatus.NOT_STARTED) {
                    icon = Icons.Default.PlayArrow
                    tintColor = Color.Gray
                } else {
                    icon = Icons.Default.Check
                    tintColor = Color.Blue
                }

                Icon(
                    icon,
                    "action icon",
                    tint = tintColor
                )
            }
        }
    }
}

@Preview
@Composable
fun ListItemPreview() {
    MaterialTheme {
        ListItem(item = AppItem(id = 1, text = "item"), background = Color.White, onItemClick = {})
    }
}
package com.thk.pagingdemo.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.thk.datda.model.Post
import com.thk.pagingdemo.PostViewModel
import com.thk.pagingdemo.ui.theme.PagingDemoTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun PostList(postsFlow: Flow<PagingData<Post>>) {
    val posts = postsFlow.collectAsLazyPagingItems()

    LazyColumn(contentPadding = PaddingValues(horizontal = 8.dp)) {
        items(posts, key = { it.id }) { post ->
            post?.also { PostItem(userId = it.userId, id = it.id, content = it.body) }
        }
    }
}

@Preview
@Composable
fun PostListPreview() {
    PagingDemoTheme {
        PostList(emptyFlow())
    }
}

@Composable
fun PostItem(
    userId: Int,
    id: Int,
    content: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Row {
                Box(modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray))
                
                Spacer(modifier = Modifier.width(8.dp))
                
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "user$userId",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 18.sp
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        Text(
                            text = "@user$userId",
                            modifier = Modifier.alpha(0.7f)
                        )

                        Spacer(modifier = Modifier
                            .height(0.dp)
                            .weight(1f))

                        IconButton(onClick = { /*TODO*/ }, modifier = Modifier
                            .size(24.dp)
                            .alpha(0.7f)) {
                            Icon(Icons.Default.MoreVert, contentDescription = "menu")
                        }
                    }

                    // 내용
                    Text(
                        text = content,
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                    )
                }
            }

        }
    }
}

@Preview
@Composable
fun PostItemPreview() {
    PagingDemoTheme {
        PostItem(1, 1, "hi")
    }
}
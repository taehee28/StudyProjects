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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.skydoves.landscapist.glide.GlideImage
import com.thk.data.model.Photo
import com.thk.data.model.Post
import com.thk.pagingdemo.ui.theme.PagingDemoTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun PhotoList(photoFlow: Flow<PagingData<Photo>>) {
    val photos = photoFlow.collectAsLazyPagingItems()

    LazyColumn(contentPadding = PaddingValues(horizontal = 8.dp)) {
        items(photos, key = { it.id }) { photo ->
            photo?.also {
                PhotoItem(
                    albumId = it.albumId,
                    id = it.id,
                    title = it.title,
                    url = it.url
                )
            }
        }

        photos.apply {
            when {
                loadState.mediator?.refresh is LoadState.Loading -> {
                    item { LoadingView(modifier = Modifier.fillParentMaxSize()) }
                }
                loadState.mediator?.append is LoadState.Loading -> {
                    item { LoadingView(modifier = Modifier.wrapContentHeight()) }
                }
                loadState.mediator?.refresh is LoadState.Error -> {
                    val error = loadState.refresh as LoadState.Error
                    item { ErrorView(error.error.localizedMessage!!, modifier = Modifier.fillParentMaxSize()) { retry() } }
                }
                loadState.mediator?.append is LoadState.Error -> {
                    val error = loadState.append as LoadState.Error
                    item { ErrorView(error.error.localizedMessage!!, modifier = Modifier.wrapContentHeight()) { retry() } }
                }
            }
        }
    }
}

@Composable
fun PostList(postsFlow: Flow<PagingData<Post>>) {
    val posts = postsFlow.collectAsLazyPagingItems()

    LazyColumn(contentPadding = PaddingValues(horizontal = 8.dp)) {
        items(posts, key = { it.id }) { post ->
            post?.also { PostItem(userId = it.userId, id = it.id, content = it.body) }
        }

        posts.apply {
            when {
                loadState.mediator?.refresh is LoadState.Loading -> {
                    item { LoadingView(modifier = Modifier.fillParentMaxSize()) }
                }
                loadState.mediator?.append is LoadState.Loading -> {
                    item { LoadingView(modifier = Modifier.wrapContentHeight()) }
                }
                loadState.mediator?.refresh is LoadState.Error -> {
                    val error = loadState.refresh as LoadState.Error
                    item { ErrorView(error.error.localizedMessage!!, modifier = Modifier.fillParentMaxSize()) { retry() } }
                }
                loadState.mediator?.append is LoadState.Error -> {
                    val error = loadState.append as LoadState.Error
                    item { ErrorView(error.error.localizedMessage!!, modifier = Modifier.wrapContentHeight()) { retry() } }
                }
            }
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
fun PhotoItem(
    albumId: Int,
    id: Int,
    title: String,
    url: String
) {
    ListItem {
        Text(text = "albumId = $albumId, id = $id")
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.body1,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        )
        Spacer(modifier = Modifier.height(8.dp))
        com.skydoves.landscapist.glide.GlideImage(
            imageModel = url,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            contentScale = ContentScale.Crop,
            loading = {
                Text(text = "loading...")
            },
            failure = {
                Text(text = "failed")
            }
        )
    }
}

@Composable
fun PostItem(
    userId: Int,
    id: Int,
    content: String
) {
    ListItem {
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

@Preview
@Composable
fun PostItemPreview() {
    PagingDemoTheme {
        PostItem(1, 1, "hi")
    }
}

@Composable
fun ListItem(
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            content = content
        )
    }
}

@Composable
fun LoadingView(modifier: Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(50.dp),
            strokeWidth = 5.dp
        )
    }
}

@Preview
@Composable
fun LoadingViewPreview() {
    PagingDemoTheme {
        LoadingView(modifier = Modifier.wrapContentHeight())
    }
}

@Composable
fun ErrorView(message: String, modifier: Modifier, onClick: () -> Unit) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        Text(text = message)
        Button(onClick = onClick) {
            Text(text = "retry")
        }
    }
}

@Preview
@Composable
fun ErrorViewPreview() {
    PagingDemoTheme {
        ErrorView("", modifier = Modifier.wrapContentHeight(), {})
    }
}
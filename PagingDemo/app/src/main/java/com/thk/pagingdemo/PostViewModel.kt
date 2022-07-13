package com.thk.pagingdemo

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.thk.datda.model.Post
import com.thk.datda.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val postRepository: PostRepository
) : ViewModel() {
    private val TAG = PostViewModel::class.simpleName

    val postFlow: Flow<PagingData<Post>> = postRepository.getUserPosts().cachedIn(viewModelScope)

    suspend fun getPosts() = viewModelScope.launch {
        Log.d(TAG, "api result: ${postRepository.getPosts()}")
    }
}
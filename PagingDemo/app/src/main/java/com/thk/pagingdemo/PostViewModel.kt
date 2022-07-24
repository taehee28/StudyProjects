package com.thk.pagingdemo

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.thk.data.model.Photo
import com.thk.data.model.Post
import com.thk.data.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.retryWhen
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val postRepository: PostRepository
) : ViewModel() {
    val postFlow: Flow<PagingData<Post>> = postRepository.getUserPosts().cachedIn(viewModelScope)
    val photoFlow: Flow<PagingData<Photo>> = postRepository.getAlbumPhotos().cachedIn(viewModelScope)
}
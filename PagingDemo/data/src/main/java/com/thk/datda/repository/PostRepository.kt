package com.thk.datda.repository

import com.thk.datda.datasource.RemoteDataSource
import javax.inject.Inject

interface PostRepository {
}

class PostRepositoryImpl @Inject constructor(dataSource: RemoteDataSource): PostRepository {
}
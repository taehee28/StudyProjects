package com.thk.datda.datasource

import com.thk.datda.network.ApiInterface
import javax.inject.Inject

interface RemoteDataSource {
}

class RemoteDataSourceImpl @Inject constructor(api: ApiInterface): RemoteDataSource {
}
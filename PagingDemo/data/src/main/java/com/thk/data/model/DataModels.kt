package com.thk.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.thk.data.database.PhotoTableInfo
import com.thk.data.database.PostTableInfo

@Entity(tableName = PostTableInfo.TABLE_NAME)
data class Post(
    val userId: Int,
    @PrimaryKey
    @ColumnInfo(name = PostTableInfo.COLUMN_NAME_ID)
    val id: Int,
    val title: String,
    val body: String
)

@Entity(tableName = PhotoTableInfo.TABLE_NAME)
data class Photo(
    val albumId: Int,
    @PrimaryKey
    @ColumnInfo(name = PhotoTableInfo.COLUMN_NAME_ID)
    val id: Int,
    val title: String,
    val url: String,
    val thumbnailUrl: String
)
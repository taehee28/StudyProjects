package com.thk.datda.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.thk.datda.database.TableInfo

@Entity(tableName = TableInfo.TABLE_NAME)
data class Post(
    val userId: Int,
    @PrimaryKey
    @ColumnInfo(name = TableInfo.COLUMN_NAME_ID)
    val id: Int,
    val title: String,
    val body: String
)

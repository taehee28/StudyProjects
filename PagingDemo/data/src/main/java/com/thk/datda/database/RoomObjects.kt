package com.thk.datda.database

import android.content.Context
import androidx.paging.PagingSource
import androidx.room.*
import com.thk.datda.model.Post

object DatabaseInfo {
    const val DATABASE_VERSION = 1
    const val DATABASE_NAME = "postdb.db"
}

object TableInfo {
    const val TABLE_NAME = "posts"
    const val COLUMN_NAME_USER_ID = "userId"
    const val COLUMN_NAME_ID = "postId"
    const val COLUMN_NAME_TITLE = "title"
    const val COLUMN_NAME_BODY = "body"
}

@Dao
interface PostDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(posts: List<Post>)

    @Query("SELECT * FROM ${TableInfo.TABLE_NAME} ORDER BY ${TableInfo.COLUMN_NAME_USER_ID}, ${TableInfo.COLUMN_NAME_ID}")
    fun getPosts(): PagingSource<Int, Post>

    @Query("DELETE FROM ${TableInfo.TABLE_NAME}")
    suspend fun clearAll()
}

@Database(entities = [Post::class], version = DatabaseInfo.DATABASE_VERSION)
abstract class PostDatabase : RoomDatabase() {
    abstract fun postsDao(): PostDao

//    companion object {
//        @Volatile
//        private var INSTANCE: PostDatabase? = null
//
//        fun getInstance(context: Context): PostDatabase =
//            INSTANCE ?: synchronized(this) {
//                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
//            }
//
//        private fun buildDatabase(context: Context) =
//            Room.databaseBuilder(
//                context.applicationContext,
//                PostDatabase::class.java,
//                DatabaseInfo.DATABASE_NAME
//            ).build()
//    }
}
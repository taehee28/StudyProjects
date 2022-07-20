package com.thk.data.database

import androidx.paging.PagingSource
import androidx.room.*
import com.thk.data.model.Photo
import com.thk.data.model.Post

object DatabaseInfo {
    const val DATABASE_VERSION = 1
    const val DATABASE_NAME = "postdb.db"
}

object PostTableInfo {
    const val TABLE_NAME = "posts"
    const val COLUMN_NAME_USER_ID = "userId"
    const val COLUMN_NAME_ID = "postId"
    const val COLUMN_NAME_TITLE = "title"
    const val COLUMN_NAME_BODY = "body"
}

object PhotoTableInfo {
    const val TABLE_NAME = "photos"
    const val COLUMN_NAME_ALBUM_ID = "albumId"
    const val COLUMN_NAME_ID = "photoId"
    const val COLUMN_NAME_TITLE = "title"
    const val COLUMN_NAME_URL = "url"
    const val COLUMN_NAME_THUMBNAIL_URL = "thumbnailUrl"
}

@Dao
interface PostDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(posts: List<Post>)

    @Query("SELECT * FROM ${PostTableInfo.TABLE_NAME} ORDER BY ${PostTableInfo.COLUMN_NAME_USER_ID}, ${PostTableInfo.COLUMN_NAME_ID}")
    fun getPosts(): PagingSource<Int, Post>

    @Query("DELETE FROM ${PostTableInfo.TABLE_NAME}")
    suspend fun clearAll()
}

@Dao
interface PhotoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(photos: List<Photo>)

    @Query("SELECT * FROM ${PhotoTableInfo.TABLE_NAME} ORDER BY ${PhotoTableInfo.COLUMN_NAME_ID}")
    fun getAlbumPhotos(): PagingSource<Int, Photo>

    @Query("DELETE FROM ${PhotoTableInfo.TABLE_NAME}")
    suspend fun clearAll()
}

@Database(
    version = DatabaseInfo.DATABASE_VERSION,
    entities = [Post::class, Photo::class]
)
abstract class PostDatabase : RoomDatabase() {
    abstract fun postsDao(): PostDao
    abstract fun photoDao(): PhotoDao

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
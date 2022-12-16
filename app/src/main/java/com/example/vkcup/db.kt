package com.example.vkcup

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.chrono.HijrahChronology.INSTANCE


@Entity
data class Category(
    val name:String,
    @ColumnInfo(name = "is_subscribe") var isSubscribe: MutableState<Boolean>
){

    @PrimaryKey(autoGenerate = true)
    var catrgoryId:Int = 0
}

@Database(entities = [Category::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): CategoryDao
    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context,scope:CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).addCallback(CategoryDBCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

}

@Dao
interface CategoryDao{
    @Query("SELECT * FROM category")
    fun getAll():List<Category>

    @Insert
    fun insertAll(category:List<Category>)
}
private class CategoryDBCallback(
    private val scope: CoroutineScope
) : RoomDatabase.Callback() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        INSTANCE?.let { database ->
            scope.launch {

            }
        }
    }

    suspend fun populateDatabase(categoryDao: CategoryDao) {

        val categoryList = mutableListOf(
            Category("News", mutableStateOf(false)),
            Category("Politics", mutableStateOf(false)),
            Category("Work", mutableStateOf(false)),
            Category("Humor", mutableStateOf(false)),
            Category("Serials", mutableStateOf(false)),
            Category("Cars", mutableStateOf(false)),
            Category("Relaxing", mutableStateOf(false)),
            Category("Very long topic for test", mutableStateOf(false)),
            Category("Music", mutableStateOf(false)),
            Category("Films", mutableStateOf(false)),
            Category("TV", mutableStateOf(false)),
            Category("Nature", mutableStateOf(false)),
            Category("Transport", mutableStateOf(false)),
            Category("Education", mutableStateOf(false)),
            Category("Taxi", mutableStateOf(false)),
            Category("Cooking", mutableStateOf(false)),
            Category("DIY", mutableStateOf(false)),
            Category("Sports", mutableStateOf(false)),
            Category("Travel", mutableStateOf(false)),
            Category("Food", mutableStateOf(false)),
            Category("Urban", mutableStateOf(false)),
            Category("Cars", mutableStateOf(false)),
        )

        categoryDao.insertAll(categoryList)

    }
}

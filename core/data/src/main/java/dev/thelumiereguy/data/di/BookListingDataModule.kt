package dev.thelumiereguy.data.di

import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import dev.thelumiereguy.data.local.AudioBookDatabase
import dev.thelumiereguy.data.local.dao.AudioBookDao
import dev.thelumiereguy.data.network.GetBooksApi
import dev.thelumiereguy.data.network.GetBooksApiImpl
import dev.thelumiereguy.data.repo.BookListingRepo
import dev.thelumiereguy.data.repo.BookListingRepoImpl

@Module
@InstallIn(ViewModelComponent::class)
abstract class BookListingDataModule {

    @Binds
    @ViewModelScoped
    abstract fun bindBooksRepo(
        bookListingRepo: BookListingRepoImpl
    ): BookListingRepo

    @Binds
    abstract fun bindBooksApi(
        booksApi: GetBooksApiImpl
    ): GetBooksApi
}

@Module
@InstallIn(SingletonComponent::class)
class BookListDataModule {

    @Provides
    fun provideAudioBookDatabase(@ApplicationContext context: Context): AudioBookDatabase {
        return Room.databaseBuilder(
            context,
            AudioBookDatabase::class.java,
            "audio_books_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideAudioBooksDao(db: AudioBookDatabase): AudioBookDao {
        return db.audioBookDao()
    }
}

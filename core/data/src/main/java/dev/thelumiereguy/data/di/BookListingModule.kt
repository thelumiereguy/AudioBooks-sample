package dev.thelumiereguy.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dev.thelumiereguy.data.BookListingRepo
import dev.thelumiereguy.data.BookListingRepoImpl
import dev.thelumiereguy.data.network.GetBooksApi
import dev.thelumiereguy.data.network.GetBooksApiImpl

@Module
@InstallIn(ViewModelComponent::class)
abstract class BookListingModule {

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
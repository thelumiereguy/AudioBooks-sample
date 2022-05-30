package dev.thelumiereguy.data.repo.fake

import android.annotation.SuppressLint
import dev.thelumiereguy.data.models.Book
import dev.thelumiereguy.data.repo.BookListingRepo

class FakeBookListingRepoImpl : BookListingRepo {

    @SuppressLint("ResourceType")
    val fakeData = listOf(
        Book(
            1,
            "The Black Witch",
            "Laurie Forest",
            1,
            "",
        ),
        Book(
            2,
            "A Promised Land",
            "Barrack Obama",
            2,
            "",
        ),
        Book(
            3,
            "Harry Potter and the Prisoner of Azkaban",
            "J.K. Rowling",
            3,
            "",
        ),
        Book(
            4,
            "The Kidnapper's Accomplice",
            "C.J. Archer",
            4,
            "",
        ),
        Book(
            5,
            "Light Mage",
            "Laurie Forest",
            5,
            "",
        ),
        Book(
            6,
            "Sherlock Holmes",
            "Sir Arthur Conan Doyle",
            6,
            "",
        ),
        Book(
            7,
            "Wuthering Heights",
            "Emily Brontë",
            7,
            "",
        ),
        Book(
            8,
            "Clean Code",
            "Robert C. Martin",
            8,
            "",
        ),
        Book(
            9,
            "The Magician's Diary",
            "C.J. Archer",
            9,
            "",
        ),
        Book(
            10,
            "The Secret",
            "Rhonda Byrne",
            10,
            "",
        ),
    )

    override suspend fun fetchBooks(): List<Book> {
        return fakeData
    }
}
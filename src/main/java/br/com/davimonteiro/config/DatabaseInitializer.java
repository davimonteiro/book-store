package br.com.davimonteiro.config;


import br.com.davimonteiro.domain.Book;
import br.com.davimonteiro.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static br.com.davimonteiro.domain.Book.BookBuilder.aBook;
import static com.google.common.collect.Lists.newArrayList;
import static java.time.Month.*;

@Service
public class DatabaseInitializer {

    @Autowired
    private BookRepository bookRepository;

    @Transactional
    public void init() throws Exception {

        List<Book> books = newArrayList(

                aBook()
                        .id(1L)
                        .title("A Game of Thrones")
                        .isbn("0-553-10354-7")
                        .publication(LocalDate.of(1996, AUGUST, 1))
                        .build(),

                aBook()
                        .id(2L)
                        .title("A Clash of Kings")
                        .isbn("0-553-10803-4")
                        .publication(LocalDate.of(1998, NOVEMBER, 16))
                        .build(),

                aBook()
                        .id(3L)
                        .title("A Storm of Swords")
                        .isbn("0-553-10663-5")
                        .publication(LocalDate.of(2000, AUGUST, 8))
                        .build(),

                aBook()
                        .id(4L)
                        .title("A Feast for Crows")
                        .isbn("0-553-10354-7")
                        .publication(LocalDate.of(2005, OCTOBER, 17))
                        .build(),


                aBook()
                        .id(5L)
                        .title("A Dance with Dragons")
                        .isbn("978-0553801477")
                        .publication(LocalDate.of(2011, JULY, 12))
                        .build()

        );

        bookRepository.save(books);
    }

}

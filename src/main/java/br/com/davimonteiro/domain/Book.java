package br.com.davimonteiro.domain;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@Entity
public class Book {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull(message = "Title is required")
    @Size(min=1, message = "Title is required")
    private String title;

    @NotNull(message = "ISBN is required")
    @Size(min=1, message = "ISBN is required")
    private String isbn;

    @NotNull(message = "Publication date is required")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate publication;

    public static final class BookBuilder {
        private Long id;
        private String title;
        private String isbn;
        private LocalDate publication;

        private BookBuilder() {
        }

        public static BookBuilder aBook() {
            return new BookBuilder();
        }

        public BookBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public BookBuilder title(String title) {
            this.title = title;
            return this;
        }

        public BookBuilder isbn(String isbn) {
            this.isbn = isbn;
            return this;
        }

        public BookBuilder publication(LocalDate publication) {
            this.publication = publication;
            return this;
        }

        public Book build() {
            Book book = new Book();
            book.setId(id);
            book.setTitle(title);
            book.setIsbn(isbn);
            book.setPublication(publication);
            return book;
        }
    }

}

package br.com.davimonteiro.repository;

import br.com.davimonteiro.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

}

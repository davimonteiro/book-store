package br.com.davimonteiro.web;

import br.com.davimonteiro.domain.Book;
import br.com.davimonteiro.repository.BookRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.validation.Valid;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.ok;

@Controller
@RequestMapping("/books")
public class BookController {

    @Inject
    private BookRepository bookRepository;

    @GetMapping
    public ModelAndView findAll() {
        ModelAndView modelAndView = new ModelAndView("books/books");
        List<Book> books = bookRepository.findAll();
        modelAndView.addObject("books", books);
        return modelAndView;
    }

    @GetMapping("/add")
    public ModelAndView add(Book book) {
        ModelAndView modelAndView = new ModelAndView("books/bookForm");
        modelAndView.addObject("book", book);
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") Long id) {
        return add(bookRepository.findOne(id));
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable("id") Long id) {
        bookRepository.delete(id);
        return findAll();
    }

    @PostMapping
    public ModelAndView createBook(@Valid Book book, BindingResult result) {
        if (result.hasErrors()) {
            return add(book);
        }
        bookRepository.save(book);
        return findAll();
    }

}

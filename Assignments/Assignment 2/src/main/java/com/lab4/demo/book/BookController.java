package com.lab4.demo.book;

import com.lab4.demo.book.model.dto.BookDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.lab4.demo.UrlMapping.*;

@RestController
@RequestMapping(BOOKS)
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping
    public List<BookDTO> findBooks(@RequestBody  BookDTO book){ return bookService.findBooks(book);}

    @PostMapping
    public BookDTO create(@RequestBody BookDTO bookDTO) {
        return bookService.create(bookDTO);
    }

    @PutMapping
    public BookDTO edit(@PathVariable Long id, @RequestBody BookDTO bookDTO) {
        return bookService.edit(id, bookDTO);
    }

    @DeleteMapping
    public void delete(@PathVariable Long id) {
        bookService.delete(id);
    }





}



package com.lab4.demo.book;

import com.lab4.demo.book.model.Book;
import com.lab4.demo.book.model.dto.BookDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static com.lab4.demo.TestCreationFactory.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class BookServiceIntegrationTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookMapper bookMapper;

    @Test
    void findAllBooks() {
        int nrOfBooks = 10;
        List<Book> books = new ArrayList<>();
        for (int i = 0; i < nrOfBooks; i++) {
            Book book = Book.builder()
                    .title("Title " + i)
                    .genre("Genre " + i)
                    .author("Author " + i)
                    .quantity(i + 1)
                    .price((float) (i / 2))
                    .build();
            books.add(book);
            bookRepository.save(book);
        }
        BookDTO bookDTO = BookDTO.builder().build();

        List<BookDTO> dtos = bookService.findAllBooks();

        for (int i = 0; i < nrOfBooks; i++) {
            assertEquals(books.get(i).getTitle(), dtos.get(i).getTitle());
            assertEquals(books.get(i).getQuantity(), dtos.get(i).getQuantity());
        }
    }

    @Test
    void findSpecificBooks() {
        int nrOfBooks = 10;
        String genre = "novel";
        String author = "Paulo Coelho";
        List<Book> books = new ArrayList<>();
        for (int i = 0; i < nrOfBooks; i++) {
            Book book = Book.builder()
                    .title("Title " + i)
                    .genre("Genre" + i)
                    .author("Author " + i)
                    .quantity(i + 1)
                    .price((float) (i / 2))
                    .build();
            if(i % 2 == 0)
                book.setGenre(genre);
            if(i % 4 == 0)
                book.setAuthor(author);
            books.add(book);
            bookRepository.save(book);
        }

        BookDTO bookDTO = new BookDTO();
        bookDTO.setGenre(genre);
        bookDTO.setAuthor(author);
        List<BookDTO> dtos = bookService.findAllBooks();

        int j = 0;
        for (int i = 0; i < nrOfBooks; i++) {
            if( i % 4 == 0) {
                assertEquals(books.get(i).getId(), dtos.get(j).getId());
                assertEquals(books.get(i).getTitle(), dtos.get(j).getTitle());
                assertEquals(books.get(i).getQuantity(), dtos.get(j).getQuantity());
                j ++;
            }
        }
    }

    @Test
    void create(){
        int nrOfBooks = 10;
        List<BookDTO> DTOs = new ArrayList<>();
        for (int i = 0; i < nrOfBooks; i++) {
            Book book = Book.builder()
                    .title("Title " + i)
                    .genre("Genre " + i)
                    .author("Author " + i)
                    .quantity(i + 1)
                    .price((float) (i / 2))
                    .build();
            DTOs.add(bookMapper.toDto(book));
            bookService.create(bookMapper.toDto(book));
        }
        BookDTO bookDTO = BookDTO.builder().build();
        List<BookDTO> allTheBooks = bookService.findAllBooks();
        assertEquals(nrOfBooks, allTheBooks.size());



    }

    @Test
    void edit(){
        Book book = Book.builder()
                .title(randomString())
                .genre(randomString())
                .author(randomString())
                .quantity(31)
                .price(randomFloat())
                .build();
        BookDTO bookInserted = bookService.create(bookMapper.toDto(book));
        book.setQuantity(15);

        BookDTO modifiedBook = bookService.edit(bookInserted.getId(), bookMapper.toDto(book));
        assertEquals(modifiedBook.getQuantity(), (Integer) 16);
    }

    @Test
    void delete(){
        Book book = Book.builder()
                .title("Title")
                .genre("Genre")
                .author("Author ")
                .quantity(31)
                .price(30f)
                .build();
        BookDTO bookInserted = bookService.create(bookMapper.toDto(book));
        bookService.delete(bookInserted.getId());
        assertFalse(bookRepository.findById(bookInserted.getId()).isPresent());

    }



}

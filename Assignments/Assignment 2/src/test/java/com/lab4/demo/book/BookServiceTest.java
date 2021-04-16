package com.lab4.demo.book;

import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.book.model.Book;
import com.lab4.demo.book.model.dto.BookDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static com.lab4.demo.TestCreationFactory.randomLong;
import static org.mockito.Mockito.*;

class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bookService = new BookService(bookRepository, bookMapper);
    }

    @Test
    void findBooks() {
        List<Book> books = TestCreationFactory.listOf(Book.class);
        BookSpecificationBuilder builder = new BookSpecificationBuilder();

        when(bookRepository.findAll(builder.build())).thenReturn(books);

        List<BookDTO> all = bookService.findBooks(null);

        Assertions.assertEquals(books.size(), all.size());
    }

    @Test
    void create() {
        long id = randomLong();
        BookDTO bookDTO = BookDTO.builder()
                .id(id)
                .title("the alchemist")
                .author("Paulo Coelho")
                .genre("Story")
                .price(16.5F)
                .quantity(15)
                .build();

        when(bookMapper.toDto(bookRepository.save(
                bookMapper.fromDto(bookDTO)))).thenReturn(bookDTO);

    }

    @Test
    void edit() {
        long id = randomLong();
        BookDTO bookDTO = BookDTO.builder()
                .id(id)
                .title("the alchemist")
                .author("Paulo Coelho")
                .genre("Story")
                .price(16.5F)
                .quantity(15)
                .build();

        Book book = bookMapper.fromDto(bookDTO);
        when(bookMapper.toDto(bookRepository.save(book))).thenReturn(bookDTO);
    }


}
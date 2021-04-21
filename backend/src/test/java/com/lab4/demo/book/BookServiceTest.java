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

import static com.lab4.demo.TestCreationFactory.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

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
        BookDTO bookDTO = BookDTO.builder().build();

        List<BookDTO> all = bookService.findAllBooks();

        Assertions.assertEquals(books.size(), all.size());
    }

    @Test
    void create() {
        long id = randomLong();
        BookDTO reqItem = BookDTO.builder()
                .id(id)
                .title(randomString())
                .author(randomString())
                .genre(randomString())
                .price(randomFloat())
                .quantity(randomBoundedInt(40))
                .build();

        Book book = Book.builder()
                .id(id)
                .title(randomString())
                .author(randomString())
                .quantity(randomBoundedInt(200))
                .price(randomFloat())
                .id(randomLong())
                .build();



        when(bookMapper.fromDto(reqItem)).thenReturn(book);
        when(bookMapper.toDto(book)).thenReturn(reqItem);
        when(bookRepository.save(book)).thenReturn(book);

        Assertions.assertEquals(reqItem.getId(), bookService.create(reqItem).getId());

    }


}
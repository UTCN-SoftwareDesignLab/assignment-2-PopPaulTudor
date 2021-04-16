package com.lab4.demo.book;

import com.lab4.demo.BaseControllerTest;
import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.book.model.Book;
import com.lab4.demo.book.model.dto.BookDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.lab4.demo.TestCreationFactory.randomLong;
import static com.lab4.demo.UrlMapping.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BookControllerTest extends BaseControllerTest {

    @InjectMocks
    private BookController controller;

    @Mock
    private BookService bookService;


    @BeforeEach
    protected void setUp() {
        super.setUp();
        controller = new BookController(bookService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void findBooks() throws Exception {


        List<BookDTO> books = TestCreationFactory.listOf(Book.class);
        when(bookService.findBooks(null)).thenReturn(books);

        ResultActions response = mockMvc.perform(get(BOOKS));

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(books));

    }

    @Test
    void create() throws Exception {
        BookDTO reqItem = BookDTO.builder()
                .title("the alchemist")
                .author("Paulo Coelho")
                .genre("Story")
                .price(16.5F)
                .quantity(15)
                .build();

        when(bookService.create(reqItem)).thenReturn(reqItem);

        ResultActions result = performPostWithRequestBody(BOOKS, reqItem);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqItem));
    }

    @Test
    void edit() throws Exception{
        long id = randomLong();
        BookDTO bookDTO = BookDTO.builder()
                .id(id)
                .title("the alchemist")
                .author("Paulo Coelho")
                .genre("Story")
                .price(16.5F)
                .quantity(15)
                .build();

        when(bookService.edit(id, bookDTO)).thenReturn(bookDTO);

        ResultActions result = performPutWithRequestBodyAndPathVariable(BOOKS, bookDTO, id);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(bookDTO));
    }

    @Test
    void delete() throws Exception{

        long id = randomLong();
        doNothing().when(bookService).delete(id);

        ResultActions result = performDeleteWIthPathVariable(BOOKS, id);
        verify(bookService, times(1)).delete(id);

        result.andExpect(status().isOk());
    }
}
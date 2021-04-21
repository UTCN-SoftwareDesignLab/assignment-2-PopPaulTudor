package com.lab4.demo.book;

import com.lab4.demo.BaseControllerTest;
import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.book.model.dto.BookDTO;
import com.lab4.demo.report.ReportServiceFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.lab4.demo.TestCreationFactory.*;
import static com.lab4.demo.TestCreationFactory.randomBoundedInt;
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

    @Mock
    private ReportServiceFactory reportServiceFactory;


    @BeforeEach
    protected void setUp() {
        super.setUp();
        controller = new BookController(bookService, reportServiceFactory);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void findBooks() throws Exception {

        List<BookDTO> books = TestCreationFactory.listOf(BookDTO.class);
        BookDTO bookDTO = new BookDTO();
        when(bookService.findAllBooks()).thenReturn(books);

        ResultActions response = mockMvc.perform(get(BOOKS));

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(books));

    }

    @Test
    void create() throws Exception {
        BookDTO reqItem = BookDTO.builder()
                .title(randomString())
                .author(randomString())
                .genre(randomString())
                .price(randomFloat())
                .quantity(randomBoundedInt(40))
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
                .title(randomString())
                .author(randomString())
                .genre(randomString())
                .price(randomFloat())
                .quantity(randomBoundedInt(40))
                .build();
        when(bookService.edit(id, bookDTO)).thenReturn(bookDTO);

        ResultActions result = performPutWithRequestBodyAndPathVariable(BOOKS + ENTITY, bookDTO, id);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(bookDTO));
    }

    @Test
    void delete() throws Exception{

        long id = randomLong();
        doNothing().when(bookService).delete(id);

        ResultActions result = performDeleteWIthPathVariable(BOOKS + ENTITY, id);
        verify(bookService, times(1)).delete(id);

        result.andExpect(status().isOk());
    }
}
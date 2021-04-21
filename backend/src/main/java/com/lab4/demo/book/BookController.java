package com.lab4.demo.book;

import com.lab4.demo.book.model.dto.BookDTO;
import com.lab4.demo.report.ReportServiceFactory;
import com.lab4.demo.report.ReportType;
import javassist.bytecode.ByteArray;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


import javax.validation.constraints.NotNull;
import java.io.ByteArrayOutputStream;
import java.util.List;

import static com.lab4.demo.UrlMapping.*;

@RestController
@RequestMapping(BOOKS)
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final ReportServiceFactory reportServiceFactory;

    @GetMapping
    public List<BookDTO> findAllBooks(){
        return bookService.findAllBooks();}

//    @GetMapping(FILTER)
//    public List<BookDTO> findAllBooks( @RequestBody @NonNull BookDTO bookDTO){
//        return bookService.findBooks(bookDTO);}


    @PostMapping
    public BookDTO create(@RequestBody BookDTO bookDTO) {
        return bookService.create(bookDTO);
    }

    @PatchMapping(ENTITY)
    public BookDTO edit(@PathVariable Long id, @RequestBody BookDTO bookDTO) {
        return bookService.edit(id, bookDTO);
    }

    @DeleteMapping(ENTITY)
    public void delete(@PathVariable Long id) {
        bookService.delete(id);
    }

    @GetMapping(EXPORT_REPORT)
    public byte[] generateReport(@PathVariable String type){
        BookDTO bookDTO = BookDTO.builder().quantity(0).build();
        ByteArrayOutputStream byteArrayOutputStream = reportServiceFactory.getReportService(ReportType.valueOf(type))
                .printReport(bookService.findBooks(bookDTO));
        return byteArrayOutputStream.toByteArray();

    }





}



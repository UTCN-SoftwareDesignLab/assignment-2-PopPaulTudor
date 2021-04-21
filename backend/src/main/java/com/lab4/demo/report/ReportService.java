package com.lab4.demo.report;

import com.lab4.demo.book.model.dto.BookDTO;

import java.io.ByteArrayOutputStream;
import java.util.List;

public interface ReportService {
    String export();

    ReportType getType();

    ByteArrayOutputStream printReport(List<BookDTO> bookDTOList);
}

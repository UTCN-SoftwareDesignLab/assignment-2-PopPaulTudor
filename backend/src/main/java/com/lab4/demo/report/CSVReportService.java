package com.lab4.demo.report;

import com.lab4.demo.book.model.Book;
import com.lab4.demo.book.model.dto.BookDTO;
import com.opencsv.CSVWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.lab4.demo.report.ReportType.CSV;

@Service
public class CSVReportService implements ReportService {


    @Override
    public String export() {
        return "I am a CSV reporter.";
    }

    @Override
    public ReportType getType() {
        return CSV;
    }

    @Override
    public ByteArrayOutputStream printReport(List<BookDTO> bookDTOList) {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        OutputStreamWriter streamWriter = new OutputStreamWriter(out);
        try {
            CSVWriter writer = new CSVWriter(streamWriter);

            for(BookDTO bookDTO: bookDTOList){
                writer.writeNext(new String[]{String.valueOf(bookDTO.getId())});
                writer.writeNext(new String[]{String.valueOf(bookDTO.getTitle())});
                writer.writeNext(new String[]{String.valueOf(bookDTO.getAuthor())});
            }

            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return out;

    }


}

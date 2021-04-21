package com.lab4.demo.report;

import com.lab4.demo.book.model.dto.BookDTO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import static com.lab4.demo.report.ReportType.PDF;

@Service
public class PdfReportService implements ReportService {
    @Override
    public String export() {
        return "I am a PDF reporter.";
    }


    @Override
    public ReportType getType() {
        return PDF;
    }

    @Override
    public ByteArrayOutputStream  printReport(List<BookDTO> bookDTOList) {

        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);
        ByteArrayOutputStream output =new ByteArrayOutputStream();

        StringBuilder content = new StringBuilder();
        content.append("The following books are out of stock:\n");
        PDPageContentStream pageContentStream = null;
        try {
            pageContentStream = new PDPageContentStream(document, page);
            pageContentStream.beginText();
            pageContentStream.setFont(PDType1Font.HELVETICA, 12);
            pageContentStream.newLineAtOffset(100, 700);

            for (BookDTO bookDTO : bookDTOList)
                content.append(bookDTO.getTitle())
                        .append(" by ")
                        .append(bookDTO.getAuthor())
                        .append(" with id: ")
                        .append(bookDTO.getId());

            String stringContent = content.toString().replace("\n", "").replace("\r", "");

            pageContentStream.showText(stringContent);
            pageContentStream.endText();
            pageContentStream.close();

            document.save(output);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return output;


    }
}

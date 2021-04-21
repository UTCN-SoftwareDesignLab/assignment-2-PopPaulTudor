package com.lab4.demo.book;

import com.lab4.demo.book.model.Book;
import com.lab4.demo.book.model.dto.BookDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    private Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found: " + id));
    }

    public List<BookDTO> findBooks(BookDTO bookDTO) {
        BookSpecificationBuilder builder = new BookSpecificationBuilder();

            if (!bookDTO.getTitle().equals(""))
                builder.with("title", ":", bookDTO.getTitle());

            if (!bookDTO.getAuthor().equals(""))
                builder.with("author", ":", bookDTO.getAuthor());

            if (!bookDTO.getGenre().equals(""))
                builder.with("genre", ":", bookDTO.getGenre());

        if (!bookDTO.getQuantity().equals(-1))
            builder.with("quantity", ":", bookDTO.getQuantity());

        List<Book> all = bookRepository.findAll(builder.build());
        return all.stream().map(bookMapper::toDto)
                .collect(toList());
    }

    public List<BookDTO> findAllBooks() {

        List<Book> all = bookRepository.findAll();
        return all.stream().map(bookMapper::toDto)
                .collect(toList());
    }

    public BookDTO create(BookDTO bookDTO) {
        return bookMapper.toDto(bookRepository.save(
                bookMapper.fromDto(bookDTO)
        ));
    }

    public BookDTO edit(Long id, BookDTO bookDTO) {
        Book book = findById(id);

        if (!bookDTO.getAuthor().equals(""))
            book.setAuthor(bookDTO.getAuthor());

        if (!bookDTO.getTitle().equals(""))
            book.setTitle(bookDTO.getTitle());

        if (!bookDTO.getAuthor().equals(""))
            book.setGenre(bookDTO.getGenre());

        if (bookDTO.getPrice() != -1)
            book.setPrice(bookDTO.getPrice());

        if (bookDTO.getQuantity() != -1 || book.getQuantity() - bookDTO.getQuantity() >= 0) {
            if (bookDTO.getQuantity().equals(book.getQuantity())) {
                book.setQuantity(bookDTO.getQuantity() - 1);
            } else
                book.setQuantity(bookDTO.getQuantity());

        }
        return bookMapper.toDto(bookRepository.save(book));
    }

    public void delete(Long id) {
        bookRepository.deleteById((id));
    }

}

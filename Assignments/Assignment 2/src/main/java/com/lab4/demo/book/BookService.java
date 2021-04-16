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

    private Book findById(Long id){
        return bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found: " + id));
    }


    public List<BookDTO> findBooks(BookDTO bookDTO) {
        BookSpecificationBuilder builder = new BookSpecificationBuilder();

        if(bookDTO != null) {
            if (bookDTO.getTitle() != null)
                builder.with("title", ":", bookDTO.getTitle());

            if (bookDTO.getAuthor() != null)
                builder.with("author", ":", bookDTO.getAuthor());

            if (bookDTO.getGenre() != null)
                builder.with("genre", ":", bookDTO.getGenre());

            if (bookDTO.getPrice() != null)
                builder.with("price", ":", bookDTO.getPrice());
        }

        List<Book> all = bookRepository.findAll(builder.build());
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

        if(bookDTO.getAuthor() != null)
            book.setAuthor(bookDTO.getAuthor());

        if(bookDTO.getTitle() != null)
            book.setTitle(bookDTO.getTitle());

        if(bookDTO.getAuthor() != null)
            book.setGenre(bookDTO.getGenre());

        if(bookDTO.getPrice() != null )
            book.setPrice(bookDTO.getPrice());

        if(bookDTO.getQuantity() != null)
            book.setQuantity(book.getQuantity() - bookDTO.getQuantity());

        return bookMapper.toDto(bookRepository.save(book));
    }

    public void delete(Long id) {
        bookRepository.deleteById((id));
    }

}

package org.efire.net.service;

import lombok.RequiredArgsConstructor;
import org.efire.net.domain.Book;
import org.efire.net.dto.BookDto;
import org.efire.net.exception.BookNotFoundException;
import org.efire.net.mapper.LibraryMapper;
import org.efire.net.repository.AuthorRepository;
import org.efire.net.repository.BookRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final LibraryMapper libraryMapper;


    public Book retrieveBook(Long id) {
        var book = bookRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Can't find any book under given ID"));
        return book;
    }

    public List<Book> retrieveBookByISBN(String isbn) {
        var book = bookRepository.findByIsbn(isbn).orElseThrow(() -> new BookNotFoundException("No book under ISBN: "+ isbn));
        var books = new ArrayList<Book>();
        books.add(book);
        return books;
    }

    public Book createBook(BookDto bookDto) {
        var author = authorRepository.findById(bookDto.getAuthorId())
                .orElseThrow(() -> new EntityNotFoundException("Author not found"));
        var book = libraryMapper.dtoToBook(bookDto);
        book.setAuthor(author);
        return bookRepository.save(book);
    }

    public List<Book> retrieveAllBook() {
        return bookRepository.findAll();
    }
}

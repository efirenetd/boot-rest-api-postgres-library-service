package org.efire.net.service;

import lombok.RequiredArgsConstructor;
import org.efire.net.domain.Book;
import org.efire.net.dto.BookDto;
import org.efire.net.exception.BookNotFoundException;
import org.efire.net.mapper.LibraryMapper;
import org.efire.net.repository.AuthorRepository;
import org.efire.net.repository.BookRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final LibraryMapper libraryMapper;

    @Cacheable(value = "bookCache")
    public Book retrieveBook(Long id) {
        return bookRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Can't find any book under given ID"));
    }

    @Cacheable(value = "bookCache")
    public List<Book> retrieveBookByISBN(String isbn) {
        return bookRepository.findByIsbn(isbn)
                .map(Arrays::asList).orElseThrow(() -> new BookNotFoundException("No book under ISBN: "+ isbn));
    }

    @CacheEvict(cacheNames = "bookCache", allEntries = true)
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

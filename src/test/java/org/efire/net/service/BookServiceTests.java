package org.efire.net.service;

import org.efire.net.common.RedisCacheConfig;
import org.efire.net.domain.Author;
import org.efire.net.domain.Book;
import org.efire.net.mapper.LibraryMapper;
import org.efire.net.repository.AuthorRepository;
import org.efire.net.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 *  To execute this tests, you need to have Redis running on your local (localhost:6379)
 *
 *  If you have Docker, execute docker run
      e.g.
      docker run --name library-redis -p 6379:6379 -e ALLOW_EMPTY_PASSWORD=yes -e REDIS_DISABLE_COMMANDS=FLUSHDB,FLUSHALL -d redis
 */
@ExtendWith(SpringExtension.class)
@Import({RedisCacheConfig.class, BookService.class})
@ImportAutoConfiguration( classes = {
        CacheAutoConfiguration.class,
        RedisAutoConfiguration.class
})
public class BookServiceTests {

    @MockBean
    private BookRepository bookRepository;
    @MockBean
    private AuthorRepository authorRepository;
    @MockBean
    private LibraryMapper libraryMapper;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private BookService bookService;


    @BeforeEach
    void setUp() {
        cacheManager.getCache("bookCache").clear();
    }

    @Disabled
    @Test
    void givenRedisCaching_whenFindById_thenBookReturnedFromCache() {
        var BOOK_ID = Long.valueOf("1");
        var aBook = new Book();
        var anAuthor = new Author();

        anAuthor.setId(2L);
        anAuthor.setLastName("Tenerife");
        anAuthor.setFirstName("Delfin");

        aBook.setId(BOOK_ID);
        aBook.setIsbn("111");
        aBook.setName("The Book");
        aBook.setAuthor(anAuthor);

        given(bookRepository.findById(BOOK_ID)).willReturn(Optional.of(aBook));

        var bookCacheMiss = bookService.retrieveBook(BOOK_ID);
        var bookCacheHit = bookService.retrieveBook(BOOK_ID);

        assertThat(bookCacheMiss).isEqualTo(aBook);
        assertThat(bookCacheHit).isEqualTo(aBook);

        verify(bookRepository, times(1)).findById(BOOK_ID);
        var bookCache = cacheManager.getCache("bookCache").get(BOOK_ID).get();
        assertThat(bookCache).isEqualTo(aBook);
    }

}

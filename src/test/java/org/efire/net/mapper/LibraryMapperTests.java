package org.efire.net.mapper;

import org.efire.net.domain.MemberStatus;
import org.efire.net.dto.BookDto;
import org.efire.net.dto.MemberDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
public class LibraryMapperTests {

    private LibraryMapper libraryMapper;

    @BeforeEach
    void setUp() {
        libraryMapper = new LibraryMapperImpl();
    }

    @Test
    void shouldMapToBook() {
        var bookDto = new BookDto();
        bookDto.setIsbn("1");
        bookDto.setAuthorId(2L);
        bookDto.setTitle("The book");

        var book = libraryMapper.dtoToBook(bookDto);

        assertThat(book.getIsbn()).isEqualTo(bookDto.getIsbn());
        assertThat(book.getAuthor().getId()).isEqualTo(bookDto.getAuthorId());
        assertThat(book.getName()).isEqualTo(bookDto.getTitle());
    }

    @Test
    public void shouldMapToMember() {
        var memberDto = new MemberDto();
        memberDto.setFirstName("John");
        memberDto.setLastName("Wick");
        memberDto.setStatus(MemberStatus.ACTIVE);

        var member = libraryMapper.dtoToMember(memberDto);

        assertThat(member.getFirstName()).isEqualTo(memberDto.getFirstName());
        assertThat(member.getLastName()).isEqualTo(memberDto.getLastName());
        assertThat(member.getStatus()).isEqualTo(memberDto.getStatus());
    }

    @Test
    public void shouldReturnNull() {
        var book = libraryMapper.dtoToBook(null);
        var member = libraryMapper.dtoToMember(null);

        assertThat(book).isNull();
        assertThat(member).isNull();
    }
}

package org.efire.net.mapper;

import org.efire.net.domain.Book;
import org.efire.net.domain.Member;
import org.efire.net.dto.BookDto;
import org.efire.net.dto.MemberDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface LibraryMapper {

    @Mapping(source = "title", target = "name")
    @Mapping(source = "authorId", target = "author.id")
    Book dtoToBook(BookDto bookDto);

    Member dtoToMember(MemberDto memberDto);
}

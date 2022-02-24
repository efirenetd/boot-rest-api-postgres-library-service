package org.efire.net.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.efire.net.domain.Book;
import org.efire.net.domain.Member;
import org.efire.net.dto.*;
import org.efire.net.exception.BookNotFoundException;
import org.efire.net.exception.MemberStatusException;
import org.efire.net.service.BookService;
import org.efire.net.service.LendService;
import org.efire.net.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/v1/libraries")
@RequiredArgsConstructor
public class LibraryController {

    private final BookService bookService;
    private final MemberService memberService;
    private final LendService lendService;

    @Operation(summary = "Read all books or get a single book by ISBN")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book found."),
            @ApiResponse(responseCode = "204", description = "No Content."),
            @ApiResponse(responseCode = "500", description = "Error retrieving all books")
    })
    @GetMapping("/book")
    public ResponseEntity<List<Book>> getAllBooks( @Parameter(description = "Book ISBN") @RequestParam(value = "isbn", required = false) String isbn,
                                                   HttpServletResponse response) {
        List<Book> books;
        if (StringUtils.hasText(isbn)) {
            try {
                books = bookService.retrieveBookByISBN(isbn);
                return ResponseEntity.ok(books);
            } catch (BookNotFoundException ex) {
                throw new ResponseStatusException(HttpStatus.NO_CONTENT, ex.getMessage(), ex);
            }
        }
        return ResponseEntity.ok(bookService.retrieveAllBook());

    }


    @Operation(summary = "Retrieve a book by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book found",
                    content = { @Content(mediaType = "application/json",schema = @Schema(implementation = Long.class))}),
            @ApiResponse(responseCode = "204", description = "Book not found."),
            @ApiResponse(responseCode = "500", description = "Unexpected error.")
    })
    @GetMapping("/book/{id}")
    public ResponseEntity<LibraryResponse> getBookById(@Parameter(description = "Id of the book to be retrieve. Cannot be empty", required = true) @PathVariable("id") Long id) {

        var book = bookService.retrieveBook(id);
        var response = LibraryResponse.builder()
                .message("Book found").status("Success").timestamp(Instant.now()).data(book).build();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Register New Book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book successfully created.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = BookDto.class ))}),
            @ApiResponse(responseCode = "500", description = "Unexpected error occurred.")
    })
    @PostMapping("/book")
    public ResponseEntity<LibraryResponse> addBook(@RequestBody BookDto bookDto) {
        var book = bookService.createBook(bookDto);

        var response = LibraryResponse.builder()
                .message("Book successfully created.").status("Success").data(book).timestamp(Instant.now()).build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/book/{id}")
    public void deleteBook(@PathVariable("id") Long id) {

    }

    @Operation(summary = "Lend a book to a member")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book/s successfully lend to a member",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = LibraryResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Unexpected error occurred.")
    })
    @PostMapping("/book/lend")
    public ResponseEntity<LibraryResponse> lendABook(@RequestBody List<LendDto> lendDto) {
        try {
            var borrowedBooks = lendService.doLendBooks(lendDto);

            if (CollectionUtils.isEmpty(borrowedBooks)) {
                var failedResponse = LibraryResponse.builder().message("No book lend to a member")
                        .timestamp(Instant.now()).status("Failed").data(borrowedBooks).build();
                return ResponseEntity.badRequest().body(failedResponse);
            }
            var successResponse = LibraryResponse.builder().message("Book/s successfully lend to a member")
                    .timestamp(Instant.now()).status("Success").data(borrowedBooks).build();
            return ResponseEntity.ok(successResponse);
        } catch (MemberStatusException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    @GetMapping("/book/{id}/lend")
    public ResponseEntity<LibraryResponse> lendBookInfo(@PathVariable("id") Long id) {
        var lend = lendService.retrieveLendBookInfo(id);
        var response = LibraryResponse.builder().status("Success")
                .message("Lend details has been retrieved").data(lend).timestamp(Instant.now()).build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/member")
    public ResponseEntity<LibraryResponse> registerMember(@RequestBody MemberDto memberDto) {
        var member = memberService.createMember(memberDto);
        var response = LibraryResponse.builder().message("Member successfully registered").status("Success")
                .timestamp(Instant.now()).data(member).build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/member/{id}")
    public Member updateMember(@PathVariable("id") Long id, @RequestBody MemberDto memberDto) {
        return memberService.updateMember(id, memberDto);
    }
}

package org.efire.net.service;

import lombok.RequiredArgsConstructor;
import org.efire.net.domain.*;
import org.efire.net.dto.LendDto;
import org.efire.net.exception.MemberStatusException;
import org.efire.net.repository.BookRepository;
import org.efire.net.repository.LendRepository;
import org.efire.net.repository.MemberRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LendService {

    private final LendRepository lendRepository;
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;

    public List<String> doLendBooks(List<LendDto> lendBookRequest) {

        List<String> borrowedBooks = new ArrayList<>();
        for (LendDto lendDto: lendBookRequest) {

            var book = bookRepository.findById(lendDto.getBookId()).orElseThrow(() ->
                    new EntityNotFoundException("Cannot find any book with ID: " + lendDto.getBookId()));
            var member = memberRepository.findById(lendDto.getMemberId()).orElseThrow(() ->
                    new EntityNotFoundException("Member not found with ID: " + lendDto.getMemberId()));

            if (member.getStatus().equals(MemberStatus.DEACTIVATED))
                throw new MemberStatusException("Member is NOT active. Cannot proceed with the request.");

            lendRepository.findByBookAndStatus(book, LendStatus.AVAILABLE).ifPresentOrElse(existingLend -> {
                borrowedBooks.add(book.getName());
                constructLend(existingLend, book, member);
                lendRepository.save(existingLend);
            }, () -> {
                var optLend = lendRepository.findByBookAndStatus(book, LendStatus.BORROWED);
                if (!optLend.isPresent()) {
                    var lend = new Lend();
                    borrowedBooks.add(book.getName());
                    constructLend(lend, book, member);
                    lendRepository.save(lend);
                }
            });
        }
        return borrowedBooks;
    }

    private void constructLend(Lend lend, Book book, Member member) {
        lend.setBook(book);
        lend.setMember(member);
        lend.setStatus(LendStatus.BORROWED);
        lend.setStartOn(Instant.now());
        lend.setDueOn(Instant.now().plus(30, ChronoUnit.DAYS));
    }

    public Lend retrieveLendBookInfo(Long bookId) {
        var book = bookRepository.findById(bookId).orElseThrow(() -> new EntityNotFoundException("Book not found"));
        return lendRepository.findByBook(book).orElseThrow(EntityNotFoundException::new);
    }
}

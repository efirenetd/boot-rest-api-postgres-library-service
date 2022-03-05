package org.efire.net.repository;

import org.efire.net.domain.Book;
import org.efire.net.domain.Lend;
import org.efire.net.domain.LendStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LendRepository extends JpaRepository<Lend, Long>, RevisionRepository<Lend, Long, Long> {

    Optional<Lend> findByBookAndStatus(Book book, LendStatus status);

    Optional<Lend> findByBook(Book book);
}

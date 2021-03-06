package org.efire.net.repository;

import org.efire.net.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long>, RevisionRepository<Author, Long, Long> {
}

package org.efire.net.repository;

import org.efire.net.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>, RevisionRepository<Member, Long, Long> {
}

package org.efire.net.service;

import lombok.RequiredArgsConstructor;
import org.efire.net.domain.Member;
import org.efire.net.domain.MemberStatus;
import org.efire.net.dto.MemberDto;
import org.efire.net.mapper.LibraryMapper;
import org.efire.net.repository.MemberRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final LibraryMapper libraryMapper;

    public Member createMember(MemberDto memberDto) {
        var member = libraryMapper.dtoToMember(memberDto);
        member.setStatus(MemberStatus.ACTIVE);
        return memberRepository.save(member);
    }

    public Member updateMember(Long id, MemberDto memberDto) {
        var existingMember = memberRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Member not found with id: " + id));

        existingMember.setFirstName(memberDto.getFirstName());
        existingMember.setLastName(memberDto.getLastName());

        return memberRepository.save(existingMember);

    }
}

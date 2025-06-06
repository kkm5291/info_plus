package com.example.infoplus.domain.member.repository;

import com.example.infoplus.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}

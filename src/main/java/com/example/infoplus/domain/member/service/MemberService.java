package com.example.infoplus.domain.member.service;

import com.example.infoplus.domain.member.entity.Member;

public interface MemberService {

    void chargePoint(Member member, Long point);

    Member findById(Long memberId);
}

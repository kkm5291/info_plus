package com.example.infoplus.domain.member.service;

import com.example.infoplus.domain.member.entity.Member;
import com.example.infoplus.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    public static final String MEMBER_NOT_FOUND_ERROR = "존재하지 않는 사용자 입니다.";
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public void chargePoint(Member member, Long point) {
        member.convertAmountToPoint(point);
    }

    @Override
    @Transactional
    public Member findById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException(MEMBER_NOT_FOUND_ERROR));
    }

}

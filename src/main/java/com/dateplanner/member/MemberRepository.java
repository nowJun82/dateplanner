package com.dateplanner.member;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByLoginId (String loginId);
    Optional<Member> findByNickname (String nickname);
    Optional<Member> findByEmail (String email);
    Optional<Member> findByPhoneNumber (String phoneNumber);
}
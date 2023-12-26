package com.dateplanner.member;

import com.dateplanner.util.RsData;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof User user) {
            return memberRepository.findByLoginId(user.getUsername()).orElse(null);
        }
        return null;
    }

    @Transactional
    public RsData<Member> memberSignup(String loginId, String password, String name, String nickname, String email, String phoneNumber) {
        if (findByLoginId(loginId).isPresent()) {
            return RsData.of("F-1", "%s은(는) 이미 사용 중인 아이디입니다.".formatted(loginId));
        }
        if (findByNickname(nickname).isPresent()) {
            return RsData.of("F-1", "%s은(는) 이미 사용 중인 닉네임입니다.".formatted(nickname));
        }
        if (findByEmail(email).isPresent()) {
            return RsData.of("F-1", "%s은(는) 이미 인증 된 이메일입니다.".formatted(email));
        }
        if (findByPhoneNumber(phoneNumber).isPresent()) {
            return RsData.of("F-1", "%s은(는) 이미 인증 된 전화번호입니다.".formatted(phoneNumber));
        }

        Member member = Member
                .builder()
                .loginId(loginId)
                .password(passwordEncoder.encode(password))
                .name(name)
                .nickname(nickname)
                .email(email)
                .phoneNumber(phoneNumber)
                .createDate(LocalDateTime.now())
                .build();

        member = memberRepository.save(member);

        return RsData.of("S-1", "회원가입이 완료되었습니다.", member);
    }

    public Optional<Member> findById(Long id) {
        return memberRepository.findById(id);
    }

    public Optional<Member> findByLoginId(String loginId) {
        return memberRepository.findByLoginId(loginId);
    }

    public Optional<Member> findByNickname(String nickname) {
        return memberRepository.findByNickname(nickname);
    }

    public Optional<Member> findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    public Optional<Member> findByPhoneNumber(String phoneNumber) {
        return memberRepository.findByPhoneNumber(phoneNumber);
    }

    public RsData checkLoginIdDup (String loginId) {
        if (findByLoginId(loginId).isPresent()) return RsData.of("F-1", "%s은(는) 이미 사용 중인 아이디입니다.".formatted(loginId));

        return RsData.of("S-1", "%s은(는) 사용 가능한 아이디입니다.".formatted(loginId));
    }

    public RsData checkNicknameDup (String nickname) {
        if (findByNickname(nickname).isPresent()) return RsData.of("F-1", "%s은(는) 이미 사용 중인 닉네임입니다.".formatted(nickname));

        return RsData.of("S-1", "%s은(는) 사용 가능한 닉네임입니다.".formatted(nickname));
    }

    public RsData checkEmailDup (String email) {
        if (findByEmail(email).isPresent()) return RsData.of("F-1", "%s은(는) 이미 인증 된 이메일입니다.".formatted(email));

        return RsData.of("S-1", "%s은(는) 사용 가능한 이메일입니다.".formatted(email));
    }

    public RsData checkPhoneNumberDup (String phoneNumber) {
        if (findByPhoneNumber(phoneNumber).isPresent()) return RsData.of("F-1", "%s은(는) 이미 인증 된 전화번호입니다.".formatted(phoneNumber));

        return RsData.of("S-1", "%s은(는) 사용 가능한 전화번호입니다.".formatted(phoneNumber));
    }
}
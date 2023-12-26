package com.dateplanner.util;

import com.dateplanner.member.Member;
import com.dateplanner.member.MemberRepository;
import com.dateplanner.member.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Optional;

@Configuration
@AllArgsConstructor
@Profile("!prod")
public class NotProd {
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @Bean
    public ApplicationRunner init() {
        return args -> {
            System.out.println("\u001B[38;5;" + Ansi.getColor("blue") + "m" + "<<< Run InitData >>>" + "\u001B[0m");
            Optional<Member> findByLoginId = memberService.findByLoginId("admin999");
            if (findByLoginId.isEmpty()) {
                System.out.println("\u001B[38;5;" + Ansi.getColor("green") + "m" + "<<< Add InitData >>>" + "\u001B[0m");

                Member member1 = memberService.memberSignup("admin999", "qweasdzxc", "관리자", "administrator", "admin@email.com", "01012345678").getData();
                member1.setPoint(67435);
                member1.setRole("admin");
                memberRepository.save(member1);

                System.out.println("\u001B[38;5;" + Ansi.getColor("blue") + "m" + "<<< InitData Addition Completed >>>" + "\u001B[0m");
            }
        };

    }
}
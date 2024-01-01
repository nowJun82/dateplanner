package com.dateplanner.member;

import com.dateplanner.util.Ansi;
import com.dateplanner.util.Rq;
import com.dateplanner.util.RsData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/member")
@RequiredArgsConstructor
@Controller
public class MemberController {
    private final HttpServletRequest httpServletRequest;
    private final Rq rq;
    private final MemberService memberService;

    @PreAuthorize("isAnonymous()")
    @GetMapping("/login")
    public String showMemberLogin() {
        System.out.println("\u001B[38;5;" + Ansi.getColor("blue") + "m" + "<<< Login Page >>>" + "\u001B[0m");

        String referer = httpServletRequest.getHeader("Referer");
        httpServletRequest.getSession().setAttribute("previousUrl", referer);

        if (memberService.getCurrentUser() != null) {
            return "redirect:/";
        }

        return "dateplanner/member/login_form";
    }

    @PreAuthorize("isAnonymous()")
    @GetMapping("/signup")
    public String showSignup() {
        System.out.println("\u001B[38;5;" + Ansi.getColor("blue") + "m" + "<<< Signup Page >>>" + "\u001B[0m");

        if (memberService.getCurrentUser() == null) {
            return "dateplanner/member/signup_form";
        } else {
            return "redirect:/";
        }
    }

    @PreAuthorize("isAnonymous()")
    @PostMapping("/signup")
    public String doSignup(@Valid SignupForm signupForm) {
        System.out.println("\u001B[38;5;" + Ansi.getColor("green") + "m" + "<<< LoginId : %s >>>".formatted(signupForm.getLoginId()) + "\u001B[0m");
        System.out.println("\u001B[38;5;" + Ansi.getColor("green") + "m" + "<<< Password : %s >>>".formatted(signupForm.getPassword()) + "\u001B[0m");
        System.out.println("\u001B[38;5;" + Ansi.getColor("green") + "m" + "<<< Name : %s >>>".formatted(signupForm.getName()) + "\u001B[0m");
        System.out.println("\u001B[38;5;" + Ansi.getColor("green") + "m" + "<<< Nickname : %s >>>".formatted(signupForm.getNickname()) + "\u001B[0m");
        System.out.println("\u001B[38;5;" + Ansi.getColor("green") + "m" + "<<< Email : %s >>>".formatted(signupForm.getEmail()) + "\u001B[0m");
        System.out.println("\u001B[38;5;" + Ansi.getColor("green") + "m" + "<<< PhoneNumber : %s >>>".formatted(signupForm.getPhoneNumber()) + "\u001B[0m");

        RsData<Member> signupRs = memberService.memberSignup(
                signupForm.getLoginId(),
                signupForm.getPassword(),
                signupForm.getName(),
                signupForm.getNickname(),
                signupForm.getEmail(),
                signupForm.getPhoneNumber()
        );

        if (signupRs.isFail()) {
            rq.historyBack(signupRs.getMsg());
            return "static.resource/js";
        }

        return rq.redirect("/", signupRs.getMsg());
    }

    @PreAuthorize("isAnonymous()")
    @GetMapping("/loginAndSignup")
    public String showLoginAndSignup() {
        if (memberService.getCurrentUser() != null) {
            return "redirect:/" + httpServletRequest.getHeader("Referer");
        }
        return "dateplanner/member/loginAndSignup";
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SignupForm {
        @NotBlank
        private String loginId;

        @NotBlank
        private String password;

        @NotBlank
        private String name;

        @NotBlank
        private String nickname;

        @NotBlank
        @Email
        private String email;

        @NotBlank
        private String phoneNumber;
    }

    @GetMapping("checkLoginIdDup")
    @ResponseBody
    public RsData checkLoginIdDup(@RequestParam("loginId") String loginId) {
        System.out.println("\u001B[38;5;" + Ansi.getColor("green") + "m" + "<<< checkLoginIdDup : %s >>>".formatted(loginId) + "\u001B[0m");

        return memberService.checkLoginIdDup(loginId);
    }
    @GetMapping("checkNicknameDup")
    @ResponseBody
    public RsData checkNicknameDup(@RequestParam("nickname") String nickname) {
        System.out.println("\u001B[38;5;" + Ansi.getColor("green") + "m" + "<<< checkNicknameDup : %s >>>".formatted(nickname) + "\u001B[0m");

        return memberService.checkNicknameDup(nickname);
    }

    @GetMapping("checkEmailDup")
    @ResponseBody
    public RsData checkEmailDup(@RequestParam("email") String email) {
        System.out.println("\u001B[38;5;" + Ansi.getColor("green") + "m" + "<<< checkEmailDup : %s >>>".formatted(email) + "\u001B[0m");

        return memberService.checkEmailDup(email);
    }

    @GetMapping("checkPhoneNumberDup")
    @ResponseBody
    public RsData checkPhoneNumberDup(@RequestParam("phoneNumber") String phoneNumber) {
        System.out.println("\u001B[38;5;" + Ansi.getColor("green") + "m" + "<<< checkPhoneNumberDup : %s >>>".formatted(phoneNumber) + "\u001B[0m");

        return memberService.checkPhoneNumberDup(phoneNumber);
    }
}
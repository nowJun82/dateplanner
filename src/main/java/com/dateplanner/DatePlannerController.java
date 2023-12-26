package com.dateplanner;

import com.dateplanner.util.Ansi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class DatePlannerController {
    @GetMapping("/")
    public String showMain() {
        System.out.println("\u001B[38;5;" + Ansi.getColor("blue") + "m" + "<<< Main Page >>>" + "\u001B[0m");

        return "main_page";
    }
}
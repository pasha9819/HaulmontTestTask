package ru.pasha.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainPageRedirectController {
    /**
     * Редирект на страницу с таблицей врачей
     * @return url перенаправления
     */
    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
    public String redirect() {
        return "redirect:/doctors";
    }
}

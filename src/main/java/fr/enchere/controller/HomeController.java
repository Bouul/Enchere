package fr.enchere.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @GetMapping("/acquisition-page")
    public String acquisitionPage() {
        return "acquisition-page";
    }

    @GetMapping("/auction-list-pages")
    public String acquisitionListPage() {
        return "auction-list-pages";
    }

    @GetMapping("/bidding-page")
    public String biddingPage() {
        return "/bidding-page";
    }

    @GetMapping("/profileModification")
    public String profileModification() {
        return "/profileModification";
    }

    @GetMapping("/profilView")
    public String profilView() {
        return "/profilView";
    }

    @GetMapping("/sell-item-page")
    public String sellItemPage() {
        return "/sell-item-page";
    }

}

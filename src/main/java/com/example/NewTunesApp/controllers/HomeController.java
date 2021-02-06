package com.example.NewTunesApp.controllers;

import com.example.NewTunesApp.data_access.TrackRepository;
import com.example.NewTunesApp.models.Track;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

@Controller
public class HomeController {

    TrackRepository trackRep = new TrackRepository();

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Model model){
        model.addAttribute("hello", "welcome");
        model.addAttribute("songs", trackRep.getRandomTrack());
        model.addAttribute("artists", trackRep.getRandomArtist());
        model.addAttribute("genres", trackRep.getRandomGenre());
        return "home";
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String getTrack(@RequestParam("searchWord") String searchWord, Model model) {
        model.addAttribute("searchWord", searchWord);
        model.addAttribute("tracks", trackRep.getTrack(searchWord));
        return "search";
    }
}
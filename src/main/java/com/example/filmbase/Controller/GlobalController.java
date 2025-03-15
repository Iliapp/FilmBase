package com.example.filmbase.Controller;

import com.example.filmbase.Manager.DataBaseManager;
import com.example.filmbase.Model.Film;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

@Controller
public class GlobalController {

    private final DataBaseManager dbManager = new DataBaseManager();

    @GetMapping("/")
    public String index(Model model) {
        List<Film> films = dbManager.getAllFilmInfo();

        List<Film> anime = new ArrayList<>();
        List<Film> serials = new ArrayList<>();
        List<Film> movies = new ArrayList<>();

        for (Film film : films) {
            switch (film.getType()) {
                case "FILM":
                    movies.add(film);
                    break;
                case "ANIME":
                    anime.add(film);
                    break;
                case "SERIAL":
                    serials.add(film);
                    break;
            }
        }

        model.addAttribute("films", movies);
        model.addAttribute("anime", anime);
        model.addAttribute("serials", serials);
        return "index";
    }

    @PostMapping("/add_film")
    public String addFilm(@RequestParam String name, @RequestParam String url, @RequestParam String type) {
        dbManager.addFilm(name, url, type);
        return "redirect:/";
    }
}

package ru.ghost.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.ghost.models.Movie;
import ru.ghost.repositorys.MovieRepository;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class MediaController {

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping("/music")
    public String music(Model model) {

        return "music";
    }

    @GetMapping("/movies")
    public String moves(Model model) {
        Iterable<Movie> movies = movieRepository.findAll();
        model.addAttribute("movies", movies);
        return "movies";
    }

    @GetMapping("/movie/{id}")
    public String move(@PathVariable(value = "id") Long id, Model model) {
        Movie movie = movieRepository.findById(id).orElseThrow();
        movie.setImage(movie.getImage());
        movie.setVideo(movie.getVideo());
        model.addAttribute("movie", movie);
        return "movie";
    }

    @GetMapping("/movie/new")
    public String newMovie(Model model){
        return "movie-new";
    }

    @PostMapping("/movie/new")
    public String newMovieSave(Movie movie, @RequestParam("img") MultipartFile img, @RequestParam("file") MultipartFile file, Model model){
        Movie newMovie = new Movie();

        if (img !=null){
            File dirImage = new File(uploadPath + "movies/image/");
            if (!dirImage.exists())
                dirImage.mkdirs();

            String imageName = img.getOriginalFilename();
            newMovie.setImage(imageName);
            try {
                img.transferTo(new File(uploadPath + "movies/image/" + imageName));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (file !=null){
            File dirVideo = new File(uploadPath + "movies/video/");
            if (!dirVideo.exists())
                dirVideo.mkdirs();

            String videoName = file.getOriginalFilename();
            newMovie.setVideo(videoName);
            try {
                file.transferTo(new File(uploadPath + "movies/video/" + videoName));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        newMovie.setTitle(movie.getTitle());;
        newMovie.setText(movie.getText());
        newMovie.setGenreSet(movie.getGenreSet());
        newMovie.setData( new SimpleDateFormat("dd.MM.yyyy").format(new Date()));
        movieRepository.save(newMovie);
        return "redirect:/movies";
    }
}



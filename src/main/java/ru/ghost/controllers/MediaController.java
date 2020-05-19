package ru.ghost.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.ghost.enums.Genre;
import ru.ghost.enums.MusicGenre;
import ru.ghost.enums.Section;
import ru.ghost.models.FileMy;
import ru.ghost.models.Movie;
import ru.ghost.models.Music;
import ru.ghost.repositorys.FileRepository;
import ru.ghost.repositorys.MovieRepository;
import ru.ghost.repositorys.MusicRepository;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;

@Controller
public class MediaController {

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MusicRepository musicRepository;

    @Autowired
    private FileRepository fileRepository;

    @GetMapping("/music")
    public String music(Model model, @PageableDefault(value = 20, direction = Sort.Direction.DESC) Pageable page) {
        Page<Music> musics = musicRepository.findAll(page);
        model.addAttribute("page", musics);
        return "music";
    }

    @GetMapping("/music/new")
    public String newMusic(Model model){
        model.addAttribute("genres", MusicGenre.values());
        return "music-new";
    }

    @PostMapping("/music/new")
    public String newMusicSave(@RequestParam("music_file") MultipartFile musicFile, Music music, Model model){
        Music newMusic = new Music();

        if (music != null){

            String musicName = musicFile.getOriginalFilename();
            newMusic.setTitle(musicName);
            try {
                musicFile.transferTo(new File(uploadPath + "musics/" + musicName));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        newMusic.setGenreSet(music.getGenreSet());
        newMusic.setData( new SimpleDateFormat("dd.MM.yyyy").format(new Date()));
        musicRepository.save(newMusic);
        return "redirect:/music";
    }

    @GetMapping("/file")
    public String file (Model model, @PageableDefault(value = 10, direction = Sort.Direction.DESC) Pageable page) {
        Page<FileMy> files = fileRepository.findAll(page);
        model.addAttribute("page", files);
        return "file";
    }

    @GetMapping("/file/new")
    public String fileNew(Model model){
        return "file-new";
    }

    @PostMapping("/file/new")
    public String newFile(@RequestParam("file") MultipartFile file, Model model){
        FileMy newFile = new FileMy();

        if (file != null){

            String fileName = file.getOriginalFilename();
            newFile.setTitle(fileName);
            try {
                file.transferTo(new File(uploadPath + "files/" + fileName));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        newFile.setData( new SimpleDateFormat("dd.MM.yyyy").format(new Date()));
        fileRepository.save(newFile);
        return "redirect:/file";
    }

    @GetMapping("/files-post")
    public String filePosts (Model model) {
        File folder = new File(uploadPath + "posts/image/");
        String[] files = folder.list();
        model.addAttribute("files", files);
        return "files-post";
    }

    @PostMapping("/file-post")
    public String newFilePost(@RequestParam("file_post") MultipartFile file, Model model){
        if (file != null){

            try {
                file.transferTo(new File(uploadPath + "posts/image/" + file.getOriginalFilename()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return "redirect:/files-post";
    }

    @GetMapping("/movies")
    public String moves(Model model, @PageableDefault(value = 18, direction = Sort.Direction.DESC) Pageable page) {
        Page<Movie> movies = movieRepository.findAll(page);
        model.addAttribute("page", movies);
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
        model.addAttribute("genres",  Genre.values());
        return "movie-new";
    }

    @PostMapping("/movie/new")
    public String newMovieSave(Movie movie, @RequestParam("img") MultipartFile img, @RequestParam("poster-img") MultipartFile posterImage, @RequestParam("file") MultipartFile file, Model model){
        Movie newMovie = new Movie();

        if (img != null){

            String imageName = img.getOriginalFilename();
            newMovie.setImage(imageName);
            try {
                img.transferTo(new File(uploadPath + "movies/image/" + imageName));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (posterImage != null){

            String posterName = posterImage.getOriginalFilename();
            newMovie.setPoster(posterName);
            try {
                posterImage.transferTo(new File(uploadPath + "movies/poster/" + posterName));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (file != null){

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



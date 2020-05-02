package ru.ghost.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.ghost.models.Post;
import ru.ghost.models.User;
import ru.ghost.repositorys.PostRepository;
import ru.ghost.repositorys.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class MainController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String home(Model model) {
        return "home";
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        return "registration";
    }

    @PostMapping("/registration")
    public String registr(User user, Model model) {
        User userDb = userRepository.findByUsername(user.getUsername());

        if (userDb != null){
            model.addAttribute("message", "User already exists");
            return "registration";
        }
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "redirect:/admin";
    }

    @GetMapping("/user")
    public String user(Principal principal,Model model) {
        User user = userRepository.findByUsername(principal.getName());
        model.addAttribute("user", user.getUsername());
        model.addAttribute("email", user.getEmail());
        return "user";
    }

    @PostMapping("/user")
    public String userSave(Principal principal, @RequestParam String email, @RequestParam String password, Model model) {
        User user = userRepository.findByUsername(principal.getName());
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
        return "redirect:/";
    }

    @GetMapping("/posts")
    public String getPosts(Model model, @PageableDefault(value = 6, direction = Sort.Direction.DESC) Pageable page){
        Page<Post> posts = postRepository.findAll(page);
        model.addAttribute("page", posts);
        return "posts";
    }

    @GetMapping("/post/{id}")
    public String postId(@PathVariable(value = "id") Long id, Model model){
        model.addAttribute("post", postRepository.findById(id).orElseThrow());
        return "post";
    }

    @GetMapping("/post/new")
    public String newPosts(Model model){
        return "post-new";
    }

    @PostMapping("/post/new")
    public String newPostsSave(Post post, @AuthenticationPrincipal User user, Model model){
        Post newPost = new Post();
        newPost.setTitle(post.getTitle());
        newPost.setAnons(post.getAnons());
        newPost.setText(post.getText());
        newPost.setAuthor(user);
        newPost.setSections(post.getSections());
        newPost.setData( new SimpleDateFormat("dd.MM.yyyy").format(new Date()));
        postRepository.save(newPost);
        return "redirect:/posts";
    }

    @GetMapping("/admin")
    public String admin(Model model){
        Iterable<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "admin";
    }

    @GetMapping("/post-user/{id}")
    public String postUser(@PathVariable(value = "id") Long id, Model model){
        User user = userRepository.findById(id).orElseThrow();
        model.addAttribute("posts", user.getPosts());
        return "posts";
    }
}


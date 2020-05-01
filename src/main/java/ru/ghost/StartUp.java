package ru.ghost;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.ghost.enums.Role;
import ru.ghost.models.User;
import ru.ghost.repositorys.UserRepository;

import java.io.File;
import java.util.Collections;

@Component
public class StartUp {

    @Value("${upload.path}")
    private String uploadPath;

    @Value("${upload.username}")
    private String username;

    @Value("${upload.pass}")
    private String pass;

    @Value("${upload.email}")
    private String email;

    private File dir;

    @Autowired
    private UserRepository userRepository;

    @EventListener(ContextRefreshedEvent.class)
    public void contextRefreshedEvent() {

        dir = new File(uploadPath + "movies/image/");
        if (!dir.exists())
            dir.mkdirs();

        dir = new File(uploadPath + "movies/video/");
        if (!dir.exists())
            dir.mkdir();

        dir = new File(uploadPath + "musics/");
        if (!dir.exists())
            dir.mkdir();

        dir = new File(uploadPath + "files/");
        if (!dir.exists())
            dir.mkdir();

        User user = userRepository.findByUsername(username);

        if(user == null) {
            User admin = new User();
            admin.setUsername(username);
            admin.setPassword(pass);
            admin.setEmail(email);
            admin.setRoles(Collections.singleton(Role.ADMIN));
            admin.setEnabled(true);
            userRepository.save(admin);
        }
    }
}

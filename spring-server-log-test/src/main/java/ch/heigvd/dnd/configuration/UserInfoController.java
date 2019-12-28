package ch.heigvd.dnd.configuration;

import ch.heigvd.dnd.api.exceptions.ValidationException;
import ch.heigvd.dnd.entities.UserEntity;
import ch.heigvd.dnd.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.util.Map;

@RestController
public class UserInfoController {


    private UserRepository userRepository;

//    private HashData hashData = new HashData();

    public UserInfoController(UserRepository userInfoRepository) {
        this.userRepository = userInfoRepository;
    }

    @PostMapping("/user")
    public Boolean create(@RequestBody Map<String, String> body) throws NoSuchAlgorithmException {
        String username = body.get("username");
        if (userRepository.existsByUsername(username)){

            throw new ValidationException("Username already existed");

        }

        String password = body.get("password");
        String encodedPassword = new BCryptPasswordEncoder().encode(password);
//        String hashedPassword = hashData.get_SHA_512_SecurePassword(password);
        String fullname = body.get("fullname");
        userRepository.save(new UserEntity(username, encodedPassword));
        return true;
    }

}

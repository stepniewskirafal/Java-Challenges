package pl.rstepniewski.internetshop.services;

import org.springframework.stereotype.Service;
import pl.rstepniewski.internetshop.model.User;
import pl.rstepniewski.internetshop.repositories.UserRepository;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findById(Long id){
        return userRepository.findById(id);
    };
}

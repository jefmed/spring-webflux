package jefmed.webflux.models.services;

import jefmed.webflux.models.entities.User;
import jefmed.webflux.models.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;

    public Flux<User> findAllUsers(){
        return userRepository.findAll();
    }

    public Mono<User> findUserByCode(String code){
        return userRepository.findById(code);
    }

    public Mono<User> createUser(User user){
        return userRepository.save(user);
    }

    public Mono<Void> deleteUser(String code){
        return userRepository.findById(code)
                .flatMap(existingUser -> userRepository.deleteById(existingUser.getCode()));
    }

    public Mono<User> updateUser(String code, User user){
        return userRepository.findById(code)
                .flatMap(existingUser -> {
                    existingUser.setName(user.getName());
                    return userRepository.save(existingUser);
                });
    }

}

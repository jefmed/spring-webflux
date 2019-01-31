package jefmed.webflux.configs;

import jefmed.webflux.models.entities.User;
import jefmed.webflux.models.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Configuration
@Component
@AllArgsConstructor
public class DummyData implements CommandLineRunner {

    private final UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        userRepository.deleteAll()
                .thenMany(Flux.just("nhanha", "joaquim", "alice")
                        .map(name -> new User(UUID.randomUUID().toString(), name))
                        .flatMap(userRepository::save)).subscribe();
    }
}

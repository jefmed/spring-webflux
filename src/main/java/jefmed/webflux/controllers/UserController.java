package jefmed.webflux.controllers;

import jefmed.webflux.models.entities.User;
import jefmed.webflux.models.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    @GetMapping
    public ResponseEntity<Flux<User>> getAllUsers(){
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @GetMapping("{code}")
//    @ResponseStatus(HttpStatus.OK)
    public Mono<ResponseEntity> getUserByCode(@PathVariable String code){
        return userService.findUserByCode(code)
                .map(existingUser -> new ResponseEntity(existingUser, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity(HttpStatus.NOT_FOUND));
//        return ResponseEntity.ok(userService.findUserByCode(code));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<User> createUser(@Valid @RequestBody User user){
        return userService.createUser(user);
    }

    @DeleteMapping("{code}")
    public Mono<ResponseEntity> deleteUser(@PathVariable String code){
        return userService.deleteUser(code)
                .map(existingUser -> new ResponseEntity(existingUser, HttpStatus.NO_CONTENT));
//                .defaultIfEmpty(new ResponseEntity(HttpStatus.NOT_FOUND));
    }

    @PutMapping("{code}")
    public Mono<ResponseEntity> updateUser(@PathVariable String code, @Valid @RequestBody User user){
        return userService.updateUser(code, user)
                .map(savedUser -> new ResponseEntity(savedUser, HttpStatus.NO_CONTENT))
                .defaultIfEmpty(new ResponseEntity(HttpStatus.NOT_FOUND));
    }

}

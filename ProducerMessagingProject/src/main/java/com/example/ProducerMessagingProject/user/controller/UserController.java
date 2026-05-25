package com.example.ProducerMessagingProject.user.controller;


import com.example.ProducerMessagingProject.security.jwt.JwtResponse;
import com.example.ProducerMessagingProject.user.LoginDto;
import jakarta.validation.Valid;
import com.example.ProducerMessagingProject.user.dto.UserInputDto;
import com.example.ProducerMessagingProject.user.dto.UserOutputDto;
import com.example.ProducerMessagingProject.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<UserOutputDto>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserOutputDto> findById(@PathVariable UUID id){
        return ResponseEntity.ok(service.findById(id));
    }
    @PostMapping
    public ResponseEntity<UserOutputDto> create(@RequestBody @Valid UserInputDto dto){
        return ResponseEntity.ok(service.create(dto));
    }
    @PutMapping("/{id}")
    public ResponseEntity<UserOutputDto> update(@PathVariable UUID id,
                                            @RequestBody @Valid UserInputDto dto){
        return ResponseEntity.ok(service.update(id,dto));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deActivate(@PathVariable UUID id){
            service.delete(id);
            return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody @Valid LoginDto dto){
        return ResponseEntity.ok(service.login(dto));
    }

}

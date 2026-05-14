package com.example.ProducerMenssagingProject.user;

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
    public ResponseEntity<?> findAll(){
        try{
            return ResponseEntity.ok(service.findAll());
        }
        catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id){
        try{
            return ResponseEntity.ok(service.findById(id));
        }
        catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }

    }
    @PostMapping
    public ResponseEntity<?> create(@RequestBody UserInputDto dto){
        try{
            return ResponseEntity.ok(service.create(dto));
        }
        catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }

    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id,
                                            @RequestBody UserInputDto dto){
        try{
            return ResponseEntity.ok(service.update(id,dto));
        }
        catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deActivate(@PathVariable UUID id){
        try{
            service.delete(id);
            return ResponseEntity.noContent().build();
        }
        catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }

    }

}

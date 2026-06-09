package com.example.ProducerMessagingProject.user;

import com.example.ProducerMessagingProject.BusinessException;
import com.example.ProducerMessagingProject.JwtResponse;
import com.example.ProducerMessagingProject.LoginRateLimitService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    private final LoginRateLimitService rateLimitService;

    public UserController(UserService service, LoginRateLimitService rateLimitService) {
        this.service = service;
        this.rateLimitService = rateLimitService;
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
    public ResponseEntity<JwtResponse> login(HttpServletRequest request, @RequestBody @Valid LoginDto dto){
        if(rateLimitService.isBlocked(request.getRemoteAddr())){
            throw new BusinessException("too many request to login", HttpStatus.TOO_MANY_REQUESTS);
        }

        try{
            return ResponseEntity.ok(service.login(dto));
        }
        catch (BusinessException ex){
            rateLimitService.registerFailedAttempt(request.getRemoteAddr());
            throw ex;
        }

    }

}

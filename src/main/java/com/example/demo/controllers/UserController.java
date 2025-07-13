package com.example.demo.controllers;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.CONFLICT;


import com.example.demo.dto.UserDTO;
import com.example.demo.exceptions.AlreadyExistsException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.model.User;
import com.example.demo.request.CreateUserRequest;
import com.example.demo.request.UpdateUserRequest;
import com.example.demo.response.APIResponse;
import com.example.demo.service.user.IUserService;
import com.example.demo.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/users")
public class UserController {

    private final IUserService userService;

    @GetMapping("/{userId}/user")
    public ResponseEntity<APIResponse> getUserById(@PathVariable Long id){
        try{
            User user = userService.getUserById(id);
            UserDTO userDto = userService.convertToDto(user);
        return ResponseEntity.ok(new APIResponse("Found user!", userDto));
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse("User with id " + id + " not found", null));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<APIResponse> createUser(@RequestBody CreateUserRequest request){
        try{
            User user =userService.createUser(request);
            UserDTO userDto = userService.convertToDto(user);
        return ResponseEntity.ok(new APIResponse("Created user!", userDto));
        }catch(AlreadyExistsException e){
            return ResponseEntity.status(CONFLICT).body(new APIResponse("User already exists in DB!",null));

        }
    }

    @PutMapping("/{userId}/update")
    public ResponseEntity<APIResponse> updateUser(@PathVariable Long userId, @RequestBody UpdateUserRequest request){
        try{
            User user = userService.updateUser(request,userId);
            UserDTO userDto = userService.convertToDto(user);
            return ResponseEntity.ok(new APIResponse("Updated user!", userDto));
        } catch(ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse("User with id " + userId + " not found", null));
        }

    }

    @DeleteMapping("/{userId}/delete")
    public ResponseEntity<APIResponse> deleteUser(@PathVariable Long userId){
        try{
            userService.deleteUser(userId);
            return ResponseEntity.ok(new APIResponse("Deleted user!", null));
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse("User with id " + userId + " not found", null));
        }
    }
}

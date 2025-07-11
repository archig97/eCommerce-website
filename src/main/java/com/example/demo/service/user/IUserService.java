package com.example.demo.service.user;

import com.example.demo.model.User;
import com.example.demo.request.CreateUserRequest;
import com.example.demo.request.UpdateUserRequest;

public interface IUserService {

    User getUserById(Long userId);
    User createUser(CreateUserRequest request);
    User updateUser(UpdateUserRequest request, Long userId);
    void deleteUser(Long userId);
}

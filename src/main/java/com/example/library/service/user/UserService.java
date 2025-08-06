package com.example.library.service.user;

import com.example.library.dto.user.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getUsers();

    UserDto getUserById(Long id);

    void deleteUserById(Long id);
}

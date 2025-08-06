package com.example.library.service.user.impl;

import com.example.library.dto.user.UserDto;
import com.example.library.entity.User;
import com.example.library.exception.NotFoundException;
import com.example.library.mapper.user.UserMapper;
import com.example.library.repository.user.UserRepository;
import com.example.library.service.user.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserDto> getUsers() {
        return userRepository.findAll().stream().map(userMapper::toDto).toList();
    }

    @Override
    public UserDto getUserById(Long id) {
        User userEntity = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("yanlis id"));

        return userMapper.toDto(userEntity);
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }


}

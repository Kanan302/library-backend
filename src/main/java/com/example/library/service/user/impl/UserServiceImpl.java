package com.example.library.service.user.impl;

import com.example.library.dto.user.UserDto;
import com.example.library.entity.books.Books;
import com.example.library.entity.user.User;
import com.example.library.exception.NotFoundException;
import com.example.library.mapper.user.UserMapper;
import com.example.library.repository.books.BooksRepository;
import com.example.library.repository.user.UserRepository;
import com.example.library.service.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BooksRepository booksRepository;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, BooksRepository booksRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.booksRepository = booksRepository;
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

    @Override
    @Transactional
    public void markBookAsReadByUser(Long userId, Long bookId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User tapılmadı: " + userId));
        Books book = booksRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException("Kitab tapılmadı: " + bookId));

        if (!user.getReadBooks().contains(book)) {
            user.getReadBooks().add(book);
        }

        if (!book.getReaders().contains(user)) {
            book.getReaders().add(user);
        }

        userRepository.save(user);
        booksRepository.save(book);
    }
}

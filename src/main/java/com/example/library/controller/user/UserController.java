package com.example.library.controller.user;

import com.example.library.config.ApiResponse;
import com.example.library.dto.user.UserDto;
import com.example.library.service.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping()
    public ResponseEntity<ApiResponse<List<UserDto>>> getAllUsers() {
        List<UserDto> responseDto = userService.getUsers();
        return ResponseEntity.ok(new ApiResponse<>(200, "hesablar getirildi", responseDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDto>> getUser(@PathVariable("id") Long id) {
        UserDto responseDto = userService.getUserById(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "hesab getirildi", responseDto));
    }

    @DeleteMapping("{id}/delete")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "hesab silindi", null));
    }
}

package me.devking2106.useddeal.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import lombok.RequiredArgsConstructor;
import me.devking2106.useddeal.controller.request.UserLoginRequest;
import me.devking2106.useddeal.controller.response.UserLoginResponse;
import me.devking2106.useddeal.dto.UserDetailDto;
import me.devking2106.useddeal.dto.UserFindDto;
import me.devking2106.useddeal.dto.UserSaveDto;
import me.devking2106.useddeal.entity.User;
import me.devking2106.useddeal.service.UserLoginService;
import me.devking2106.useddeal.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

	private final UserService userService;
	private final UserLoginService userLoginService;

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/users/register")
	public String register(@Valid @RequestBody UserSaveDto userSaveDto) {
		userService.saveUser(userSaveDto);
		return "회원가입을 성공했습니다";
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/users/{id}")
	public UserDetailDto findById(@PathVariable Long id) {
		return userService.findById(id);
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/users/profile")
	public UserDetailDto getUserProfile(@SessionAttribute("ID") Long id) {
		return userService.findById(id);
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/users")
	public List<UserFindDto> findByNickName(@RequestParam String nickname) {
		return userService.findByNickName(nickname);
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/users/duplicate/{id}")
	public boolean idCheck(@PathVariable String id) {
		return userService.isDuplicatedUserId(id);
	}

	@PostMapping("/users/login")
	public ResponseEntity<UserLoginResponse> login(@Valid @RequestBody UserLoginRequest userLoginRequest, HttpSession httpSession) {
		return userLoginService.login(userLoginRequest, httpSession);
	}

	@GetMapping("/users/logout")
	public void logout(HttpSession session) {
		userLoginService.logout(session);
	}


}

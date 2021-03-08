package me.devking2106.useddeal.service;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.devking2106.useddeal.common.utils.SHA256Util;
import me.devking2106.useddeal.controller.request.UserLoginRequest;
import me.devking2106.useddeal.controller.response.UserLoginResponse;
import me.devking2106.useddeal.entity.User;
import me.devking2106.useddeal.repository.mapper.UserMapper;

@Service
@RequiredArgsConstructor
public class UserLoginService {

	private final UserMapper userMapper;

	public ResponseEntity<UserLoginResponse> login(UserLoginRequest loginRequest, HttpSession session) {
		String id = loginRequest.getId();
		String encryptPassword = SHA256Util.encodeSHA256(loginRequest.getPassword());
		User userInfo = userMapper.findByIdAndPassword(id, encryptPassword);
		ResponseEntity<UserLoginResponse> responseEntity = null;
		UserLoginResponse userLoginResponse;
		if (ObjectUtils.isEmpty(userInfo)) {
			userLoginResponse = UserLoginResponse.FAIL;
			responseEntity = new ResponseEntity<>(userLoginResponse, HttpStatus.UNAUTHORIZED);
		} else if (User.Status.ACTIVE.equals(userInfo.getStatus())) {
			userLoginResponse = UserLoginResponse.SUCCESS;
			setSessions(userInfo, session);
			responseEntity = new ResponseEntity<>(userLoginResponse, HttpStatus.OK);
		}
		return responseEntity;
	}

	public void logout(HttpSession session) {
		session.invalidate();
	}

	private void setSessions(User user, HttpSession session) {
		session.setAttribute("ID", user.getId());
		session.setAttribute("ROLE", user.getRole());
		session.setMaxInactiveInterval(60 * 30);
	}
}

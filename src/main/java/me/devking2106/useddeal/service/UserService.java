package me.devking2106.useddeal.service;

import static me.devking2106.useddeal.service.LocationService.*;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import lombok.RequiredArgsConstructor;
import me.devking2106.useddeal.common.utils.SHA256Util;
import me.devking2106.useddeal.dto.UserDetailDto;
import me.devking2106.useddeal.dto.UserFindDto;
import me.devking2106.useddeal.dto.UserSaveDto;
import me.devking2106.useddeal.entity.Location;
import me.devking2106.useddeal.entity.User;
import me.devking2106.useddeal.error.exception.user.UserDuplicateUserIdException;
import me.devking2106.useddeal.error.exception.user.UserNotFoundException;
import me.devking2106.useddeal.error.exception.user.UserSaveFailedException;
import me.devking2106.useddeal.repository.mapper.LocationMapper;
import me.devking2106.useddeal.repository.mapper.UserMapper;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserMapper userMapper;
	private final LocationMapper locationMapper;

	public static void userIsEmpty(Object user) {
		if (ObjectUtils.isEmpty(user)) {
			throw new UserNotFoundException();
		}
	}

	public void saveUser(UserSaveDto userSaveDto) {
		boolean duplicatedUserIdResult = isDuplicatedUserId(userSaveDto.getUserId());
		if (duplicatedUserIdResult) {
			throw new UserDuplicateUserIdException();
		}
		String userSaveDtoLocationName = userSaveDto.getLocationName();
		Location locationInfo = locationMapper.findByLocationName(userSaveDtoLocationName);
		locationIsEmpty(locationInfo);
		User userInfo = userSaveDto.toEntity(locationInfo);
		System.out.println("userInfo = " + userInfo);
		int saveResult = userMapper.save(userInfo);
		if (saveResult != 1) {
			throw new UserSaveFailedException();
		}
	}

	@Transactional(readOnly = true)
	public boolean isDuplicatedUserId(String userId) {
		return userMapper.idCheck(userId);
	}

	@Transactional(readOnly = true)
	public UserDetailDto findById(Long id) {
		User userInfo = userMapper.findById(id);
		userIsEmpty(userInfo);
		return UserDetailDto.userToDetailDto(userInfo);
	}

	@Transactional(readOnly = true)
	public List<UserFindDto> findByNickName(String nickname) {
		return userMapper.findByNickName(nickname);
	}

}

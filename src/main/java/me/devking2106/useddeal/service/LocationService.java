package me.devking2106.useddeal.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import lombok.RequiredArgsConstructor;
import me.devking2106.useddeal.dto.LocationFindDto;
import me.devking2106.useddeal.dto.LongitudeAndLatitude;
import me.devking2106.useddeal.dto.UserDetailDto;
import me.devking2106.useddeal.entity.Location;
import me.devking2106.useddeal.entity.User;
import me.devking2106.useddeal.error.exception.location.LocationNotFoundException;
import me.devking2106.useddeal.error.exception.user.UserRegionAuthFailedException;
import me.devking2106.useddeal.repository.mapper.LocationMapper;
import me.devking2106.useddeal.repository.mapper.UserMapper;

@Service
@RequiredArgsConstructor
public class LocationService {

	private final LocationMapper locationMapper;
	private final UserMapper userMapper;

	public static void locationIsEmpty(Location location) {
		if (ObjectUtils.isEmpty(location)) {
			throw new LocationNotFoundException();
		}
	}

	@Transactional(readOnly = true)
	public Location findByLocationName(String locationName) {
		Location locationInfo = locationMapper.findByLocationName(locationName);
		locationIsEmpty(locationInfo);
		return locationInfo;
	}

	@Transactional(readOnly = true)
	public List<LocationFindDto> findAll(String region, Long userId) {
		// 현재 내 위치의 위도 경도
		User userInfo = userMapper.findById(userId);
		return locationMapper.findAll(region, userInfo.getLongitudeAndLatitude());
	}

	public String regionAuth(String locationName, Long userId) {
		Location locationInfo = findByLocationName(locationName);
		int updateCount = userMapper.updateLocation(locationInfo, userId);
		if (updateCount < 1) {
			throw new UserRegionAuthFailedException();
		}
		return locationInfo.getTown() + " 동네 인증 성공";
	}
}

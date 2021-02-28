package me.devking2106.useddeal.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import lombok.RequiredArgsConstructor;
import me.devking2106.useddeal.dto.LocationFindDto;
import me.devking2106.useddeal.entity.Location;
import me.devking2106.useddeal.error.exception.location.LocationNotFoundException;
import me.devking2106.useddeal.repository.mapper.LocationMapper;

@Service
@RequiredArgsConstructor
public class LocationService {

	private final LocationMapper locationMapper;

	public static void locationIsEmpty(Object locations) {
		if (ObjectUtils.isEmpty(locations)) {
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
	public List<LocationFindDto> findAll(String region) {
		// 현재 내 위치의 위도 경도
		double latitude = 37.587111;
		double longitude = 126.969069;

		return locationMapper.findAll(region, latitude, longitude);
	}
}

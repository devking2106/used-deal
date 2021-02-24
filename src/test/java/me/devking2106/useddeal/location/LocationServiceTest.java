package me.devking2106.useddeal.location;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import me.devking2106.useddeal.dto.LocationFindDto;
import me.devking2106.useddeal.repository.mapper.LocationMapper;

@ExtendWith(MockitoExtension.class)
public class LocationServiceTest {

	@Mock
	private LocationMapper locationMapper;

	@Test
	@DisplayName("동네 조회 테스트 - 성공")
	void findLocation() {
		String region = "서울 종로구 청운동";
		double latitude = 37.587111;
		double longitude = 126.969069;

		assertNotNull(locationMapper.findAll(region, latitude, longitude));
	}

	@Test
	@DisplayName("동네 조회 테스트 - 실패")
	void findFailedLocation() {
		String region = "서울 종로구 청운1동";
		double latitude = 37.587111;
		double longitude = 126.969069;
		List<LocationFindDto> locations = locationMapper.findAll(region, latitude, longitude);
		assertEquals(locations.size(), 0);
	}
}

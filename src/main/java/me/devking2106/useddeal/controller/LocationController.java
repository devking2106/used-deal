package me.devking2106.useddeal.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import me.devking2106.useddeal.dto.LocationFindDto;
import me.devking2106.useddeal.service.LocationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LocationController {

	private final LocationService locationService;

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/locations")
	public List<LocationFindDto> findAll(
		@RequestParam(value = "region", required = false) String region) {
		return locationService.findAll(region);
	}

	// 임시 동네 인증 핸들러
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/locations/region-auth/{locationName}")
	public String findByLocationName(@PathVariable String locationName) {
		String town = locationService.findByLocationName(locationName).getTown();
		return town + " 동네 인증 성공";
	}
}

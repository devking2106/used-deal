package me.devking2106.useddeal.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

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
		@RequestParam(value = "region", required = false) String region, @SessionAttribute("ID") Long userId) {
		return locationService.findAll(region, userId);
	}

	// 임시 동네 인증 핸들러
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/locations/region-auth/{locationName}")
	public String regionAuth(@PathVariable String locationName, @SessionAttribute("ID") Long userId) {
		return locationService.regionAuth(locationName, userId);
	}
}

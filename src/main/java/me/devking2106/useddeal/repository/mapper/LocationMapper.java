package me.devking2106.useddeal.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import me.devking2106.useddeal.dto.LocationFindDto;
import me.devking2106.useddeal.dto.LongitudeAndLatitude;
import me.devking2106.useddeal.entity.Location;

@Mapper
public interface LocationMapper {

	Location findByLocationName(String locationName);

	List<LocationFindDto> findAll(@Param("region") String region,
		@Param("longitudeAndLatitude") LongitudeAndLatitude longitudeAndLatitude);
}

package me.devking2106.useddeal.common.utils;

import org.springframework.util.ObjectUtils;

import me.devking2106.useddeal.dto.BoardDetailDto;
import me.devking2106.useddeal.entity.Location;
import me.devking2106.useddeal.error.exception.board.BoardNotFoundException;
import me.devking2106.useddeal.error.exception.location.LocationNotFoundException;

public class CustomUtil {

	public static void boardIsEmpty(BoardDetailDto board) {
		if (ObjectUtils.isEmpty(board)) {
			throw new BoardNotFoundException();
		}
	}

	public static void locationIsEmpty(Location location) {
		if (ObjectUtils.isEmpty(location)) {
			throw new LocationNotFoundException();
		}
	}

}

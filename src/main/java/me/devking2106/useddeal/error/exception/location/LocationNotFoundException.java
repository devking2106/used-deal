package me.devking2106.useddeal.error.exception.location;

import me.devking2106.useddeal.error.exception.common.ExceptionStatus;
import me.devking2106.useddeal.error.exception.common.UsedDealException;

public class LocationNotFoundException extends UsedDealException {

	public LocationNotFoundException() {
		super(ExceptionStatus.LOCATION_NOT_FOUND_EXCEPTION);
	}
}

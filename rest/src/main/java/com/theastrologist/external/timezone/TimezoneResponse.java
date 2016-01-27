package com.theastrologist.external.timezone;

import com.theastrologist.external.GoogleStatus;
import lombok.Data;

/**
 * Created by Samy on 27/01/2016.
 */
@Data
public class TimezoneResponse {
	public long dstOffset;
	public long rawOffset;
	public GoogleStatus status;
	public String timeZoneId;
	public String timeZoneName;
}

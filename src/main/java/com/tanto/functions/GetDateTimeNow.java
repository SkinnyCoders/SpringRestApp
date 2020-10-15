package com.tanto.functions;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class GetDateTimeNow {
	public String DateTimeNow() {
		//get date time now
		Instant dateNow = Instant.now();
		ZonedDateTime nowAsiaSingapore = dateNow.atZone(ZoneId.of("Asia/Jakarta"));
	    LocalDateTime utc1 = nowAsiaSingapore.toLocalDateTime();
	    //formated date time
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	    String formatDateTime = utc1.format(formatter);
	    return formatDateTime;
	}

}

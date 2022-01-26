package com.travel.tripsncostcalulator;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Trip {
	
	private LocalDateTime started;
	private LocalDateTime finished;
	private Long durationInSeconds;
	private String fromStopId;
	private String toStopId;
	private Double chargeAmount;
	private String companyId;
	private String busId;
	private String pan;
	private TripStatus tripStatus;

}

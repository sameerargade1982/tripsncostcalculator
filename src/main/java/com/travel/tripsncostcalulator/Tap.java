package com.travel.tripsncostcalulator;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@AllArgsConstructor
@ToString
public class Tap {

	private LocalDateTime dateTimeUTC;
	private TapType tapType;
	private String stopId;
	private String companyId;
	private String busId;
	private String PAN;
	
}

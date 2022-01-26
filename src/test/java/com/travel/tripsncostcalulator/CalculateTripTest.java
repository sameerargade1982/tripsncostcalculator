package com.travel.tripsncostcalulator;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class CalculateTripTest {
	
	@Test
	void newtrip_calculateTrip_returns_incomplete_trip() {
		CalculateTrip calculateTrip = new CalculateTrip();
		LocalDateTime localDateTime = LocalDateTime.now();
		Tap tap = Tap.builder().PAN("12345").tapType(TapType.ON).stopId("Stop1").dateTimeUTC(localDateTime).build();
		Trip trip = calculateTrip.calculateTrip(null,tap);
		assertNotNull(trip);
		assertEquals(tap.getPAN(),trip.getPan());
		assertEquals(tap.getDateTimeUTC(), trip.getStarted());
		assertEquals(tap.getStopId(), trip.getFromStopId());
		assertEquals(TripStatus.INCOMPLETE, trip.getTripStatus());
	}
	
	@Test
	void newTrip_calculateTrip_returns_completed_trip() {
		CalculateTrip calculateTrip = new CalculateTrip();
		LocalDateTime localDateTime = LocalDateTime.now();
		Tap tap1 = Tap.builder().PAN("12345").tapType(TapType.ON).stopId("Stop1").dateTimeUTC(localDateTime).build();
		Tap tap2 = Tap.builder().PAN("12345").tapType(TapType.OFF).stopId("Stop2")
				.dateTimeUTC(localDateTime.plusMinutes(10L)).build();
		Trip inCompletetrip = calculateTrip.calculateTrip(null,tap1);
		assertNotNull(inCompletetrip);
		assertEquals(TripStatus.INCOMPLETE, inCompletetrip.getTripStatus());
		Trip completedTrip = calculateTrip.calculateTrip(inCompletetrip,tap2);
		assertNotNull(completedTrip);
		assertTrip(tap1, tap2, completedTrip, TripStatus.COMPLETED,3.25);
	}
	
	@Test
	void newTrip_calculateTrip_returns_cancelled_trip() {
		CalculateTrip calculateTrip = new CalculateTrip();
		LocalDateTime localDateTime = LocalDateTime.now();
		Tap tap6 = Tap.builder().PAN("12348").tapType(TapType.ON).stopId("Stop1").dateTimeUTC(localDateTime).build();
		Tap tap7 = Tap.builder().PAN("12348").tapType(TapType.OFF).stopId("Stop1")
				.dateTimeUTC(localDateTime.plusSeconds(5L)).build();
		Trip inCompletetrip = calculateTrip.calculateTrip(null,tap6);
		assertNotNull(inCompletetrip);
		assertEquals(TripStatus.INCOMPLETE, inCompletetrip.getTripStatus());
		Trip completedTrip = calculateTrip.calculateTrip(inCompletetrip,tap7);
		assertNotNull(completedTrip);
		assertTrip(tap6, tap7, completedTrip, TripStatus.CANCELLED,0.0);
		}

	private void assertTrip(Tap tap1, Tap tap2, Trip completedTrip, TripStatus tripStatus,Double tripCharge) {
		assertEquals(tap1.getPAN(),completedTrip.getPan());
		assertEquals(tap2.getPAN(),completedTrip.getPan());
		assertEquals(tap1.getDateTimeUTC(), completedTrip.getStarted());
		assertEquals(tap2.getDateTimeUTC(), completedTrip.getFinished());
		assertEquals(tap1.getStopId(), completedTrip.getFromStopId());
		assertEquals(tap2.getStopId(), completedTrip.getToStopId());
		assertEquals(tripStatus, completedTrip.getTripStatus());
		assertEquals(tripCharge,completedTrip.getChargeAmount());
	}
}

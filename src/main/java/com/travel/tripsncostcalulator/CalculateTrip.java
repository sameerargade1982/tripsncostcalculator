package com.travel.tripsncostcalulator;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CalculateTrip {

	private Set<Trip> trips = new HashSet<>();

	public Set<Trip> getTrips(){
		return this.trips;
	}
	
	public Trip calculateTrip(Trip trip, Tap tap) {
		if (trip == null) {
			trip = new Trip();
			trip.setPan(tap.getPAN());
			trip.setCompanyId(tap.getCompanyId());
			trip.setBusId(tap.getBusId());
			trip.setStarted(tap.getDateTimeUTC());
			if (trip.getTripStatus() == null) {
				trip.setTripStatus(TripStatus.INCOMPLETE);
			}
			trip.setFromStopId(tap.getStopId());
			trip.setChargeAmount(calculateCompletedTripFare(trip.getFromStopId(), null));
			
		} else if (trip.getPan().equals(tap.getPAN())) {
			trip.setChargeAmount(calculateCompletedTripFare(trip.getFromStopId(), tap.getStopId()));
			trip.setCompanyId(tap.getCompanyId());
			trip.setBusId(tap.getBusId());
			trip.setDurationInSeconds(calculateTripDurationInSeconds(trip.getStarted(), tap.getDateTimeUTC()));
			trip.setFinished(tap.getDateTimeUTC());
			trip.setToStopId(tap.getStopId());
			trip.setTripStatus(calculateTripStatus(trip, tap));
		}
		return trip;
	}

	TripStatus calculateTripStatus(Trip trip, Tap tap) {

		if (trip.getTripStatus().equals(TripStatus.INCOMPLETE)) {

			if (tap.getDateTimeUTC().isAfter(trip.getStarted())) {
				if (tap.getTapType().equals(TapType.OFF) && !tap.getStopId().equals(trip.getFromStopId())) {
					return TripStatus.COMPLETED;
				} else if (tap.getTapType().equals(TapType.OFF) && tap.getStopId().equals(trip.getFromStopId())) {
					return TripStatus.CANCELLED;
				}
			}
		}
		return trip.getTripStatus();
	}



	Double calculateCompletedTripFare(String startStop, String endStop) {
		Double tripFare = 0.0;

		if (startStop.equals(endStop)) {
			return tripFare;
		}
		if (startStop.equals("Stop1")) {
			if (endStop == null || endStop.equals("Stop3")) {
				tripFare = fareForTripBetweenStops1And3();
			} else {
				tripFare = fareForTripBetweenStops1And2();
			}

		}

		if (startStop.equals("Stop2")) {
			if (endStop == null || endStop.equals("Stop3")) {
				tripFare = fareForTripBetweenStops2And3();
			} else {
				tripFare = fareForTripBetweenStops1And2();
			}

		}

		if (startStop.equals("Stop3")) {
			if (endStop == null || endStop.equals("Stop1")) {
				tripFare = fareForTripBetweenStops1And3();
			} else {
				tripFare = fareForTripBetweenStops2And3();
			}

		}

		return tripFare;

	}
	
	Long calculateTripDurationInSeconds(LocalDateTime fromTime, LocalDateTime toTime) {
		if (fromTime.getMinute() == toTime.getMinute()) {
			return 0L;
		}
		Duration duration = Duration.between(fromTime, toTime);
		return duration.getSeconds();
	}


	Double fareForTripBetweenStops1And2() {

		return 3.25;

	}

	Double fareForTripBetweenStops2And3() {

		return 5.50;
	}

	Double fareForTripBetweenStops1And3() {

		return 7.30;
	}

	TripStatus calculateTripStatus(TapType tapType1, TapType tapType2) {

		if (tapType1.equals(tapType2)) {

			return TripStatus.INCOMPLETE;
		}

		if (tapType1.equals(TapType.ON) && tapType2.equals(TapType.OFF)) {

			return TripStatus.COMPLETED;
		}

		return TripStatus.CANCELLED;
	}

	public void processTrip(Tap tap) {
		Trip tripToProcess = trips.stream().filter(trip -> trip.getPan().equals(tap.getPAN())).filter(trip->trip.getTripStatus().equals(TripStatus.INCOMPLETE)).findFirst().orElse(null);
		trips.add(calculateTrip(tripToProcess, tap));
	}

	public static void main(String args[]) {

		List<Tap> taps = new ArrayList<>();
		Comparator<Tap> compareByPan = Comparator.comparing(Tap::getPAN);
		Comparator<Tap> compareByTripTime = Comparator.comparing(Tap::getDateTimeUTC);
		CalculateTrip calculateTrip = new CalculateTrip();

		LocalDateTime localDateTime = LocalDateTime.now();
		Tap tap11 = Tap.builder().PAN("12345").tapType(TapType.ON).stopId("Stop1").dateTimeUTC(localDateTime).build();
		Tap tap22 = Tap.builder().PAN("12345").tapType(TapType.OFF).stopId("Stop2")
				.dateTimeUTC(localDateTime.plusMinutes(10L)).build();
		Tap tap3 = Tap.builder().PAN("12346").tapType(TapType.ON).stopId("Stop3").dateTimeUTC(localDateTime).build();
		Tap tap4 = Tap.builder().PAN("12346").tapType(TapType.OFF).stopId("Stop1")
				.dateTimeUTC(localDateTime.plusMinutes(15L)).build();
		Tap tap5 = Tap.builder().PAN("12347").tapType(TapType.OFF).stopId("Stop1").dateTimeUTC(localDateTime).build();
		Tap tap6 = Tap.builder().PAN("12348").tapType(TapType.ON).stopId("Stop1").dateTimeUTC(localDateTime).build();
		Tap tap7 = Tap.builder().PAN("12348").tapType(TapType.OFF).stopId("Stop1")
				.dateTimeUTC(localDateTime.plusSeconds(5L)).build();
		
		Tap tap8 = Tap.builder().PAN("12345").tapType(TapType.ON).stopId("Stop2").dateTimeUTC(localDateTime.plusMinutes(30L)).build();
		Tap tap9 = Tap.builder().PAN("12345").tapType(TapType.OFF).stopId("Stop3")
				.dateTimeUTC(localDateTime.plusMinutes(40L)).build();

		taps.add(tap4);
		taps.add(tap11);
		taps.add(tap22);
		taps.add(tap3);
		taps.add(tap5);
		taps.add(tap6);
		taps.add(tap7);
		taps.add(tap8);
		taps.add(tap9);

		Stream<Tap> tapsw = taps.stream().sorted(compareByPan).sorted(compareByTripTime);
		tapsw.peek(calculateTrip::processTrip).collect(Collectors.toList());
		calculateTrip.getTrips().forEach(System.out::println);
		
	}
}

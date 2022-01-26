package com.travel.tripsncostcalulator;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.travel.tripsncostcalulator.parser.FileParser;
import com.travel.tripsncostcalulator.utils.FileWriter;

public class Main {

	public static void main(String args[]) throws Exception {
		Comparator<Tap> compareByPan = Comparator.comparing(Tap::getPAN);
		Comparator<Tap> compareByTripTime = Comparator.comparing(Tap::getDateTimeUTC);
		CalculateTrip calculateTrip = new CalculateTrip();
		FileParser fp = new FileParser();
		FileWriter fw = new FileWriter();

		List<Tap> taps = fp.readFile(args[0]);
		Stream<Tap> sortedTaps = taps.stream().sorted(compareByPan).sorted(compareByTripTime);
		sortedTaps.peek(calculateTrip::processTrip).collect(Collectors.toList());
		Set<Trip> trips = calculateTrip.getTrips();
		fw.writeFile(args[1], trips);
	}
}

package com.travel.tripsncostcalulator.parser;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.travel.tripsncostcalulator.Tap;
import com.travel.tripsncostcalulator.TapType;
import com.travel.tripsncostcalulator.utils.FileReader;

public class FileParser {

	FileReader fileReader = new FileReader();
	List<Tap> taps = new ArrayList<Tap>();
	LocalDateTime parseDate(String inDate) {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime parsedDate = LocalDateTime.parse(inDate,formatter);
		return parsedDate;
	}
	
	public List<Tap> readFile(String fileName){
		try {
			Stream<String> lines = fileReader.readFile(fileName);
			return lines.filter(line-> !(line.startsWith("id")))
			.map(line-> {
			String [] tapAttributes =	line.split(",");
			return new Tap(parseDate(tapAttributes[1]),TapType.valueOf(tapAttributes[2]), tapAttributes[3], tapAttributes[4], tapAttributes[5], 
					tapAttributes[6]);
			}).collect(Collectors.toList());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}

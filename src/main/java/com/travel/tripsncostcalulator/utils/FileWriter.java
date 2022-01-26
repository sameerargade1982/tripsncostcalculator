package com.travel.tripsncostcalulator.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Set;

import com.travel.tripsncostcalulator.Trip;

public class FileWriter {
	public void writeFile(String fileName,Set<Trip> trips ) throws IOException {
		File f = new File(fileName);
		Path path = Paths.get(f.toURI());
		trips.forEach(trip-> {
			try {
				Files.writeString(path, trip.toString(), StandardOpenOption.APPEND);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}		

}

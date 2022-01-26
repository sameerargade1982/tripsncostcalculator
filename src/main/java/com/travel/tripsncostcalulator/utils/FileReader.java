package com.travel.tripsncostcalulator.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileReader {
	public Stream<String> readFile(String fileName) throws IOException {
		File f = new File(fileName);
		Path path = Paths.get(f.toURI());
		return Files.lines(path);
	}
}

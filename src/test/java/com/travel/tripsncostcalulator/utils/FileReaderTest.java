package com.travel.tripsncostcalulator.utils;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.file.NoSuchFileException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


public class FileReaderTest {
	
	static FileReader fr = null;
	
	@BeforeAll
	static void setup() throws Exception{
		fr = new FileReader();
	}
	
	@Test
	void readFile_fileIsMissing_ThrowsException() throws Exception{
		assertThrows(NoSuchFileException.class,() -> fr.readFile("//Data//missingFile"));
	}

}
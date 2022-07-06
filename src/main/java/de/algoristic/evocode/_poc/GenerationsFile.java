package de.algoristic.evocode._poc;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Path;
import java.util.Iterator;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

public class GenerationsFile {
	
	private static enum Headers {
		GenerationNumber
	}

	private final File file;
	
	private GenerationsFile(final File file) {
		this.file = file;
	}
	
	public static GenerationsFile createNew(Path path) {
		try {
			File file = path.toFile();
			FileWriter out = new FileWriter(file);
			CSVPrinter printer = CSVFormat.DEFAULT
				.builder()
				.setHeader(Headers.class)
				.build()
				.print(out);
			printer.flush();
			printer.close();
			return new GenerationsFile(file);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static GenerationsFile load(Path path) {
		File file = path.toFile();
		return new GenerationsFile(file);
	}

	public int getLastGrownGeneration() {
		try {
			Reader in = new FileReader(file);
			Iterable<CSVRecord> records = CSVFormat.DEFAULT
				.builder()
				.setHeader(Headers.class)
				.build()
				.parse(in);
			Iterator<CSVRecord> iterator = records.iterator();
			while(iterator.hasNext()) {
				CSVRecord record = iterator.next();
				if(!iterator.hasNext()) {
					String generationLiteral = record.get(Headers.GenerationNumber);
					return Integer.valueOf(generationLiteral);
				}
			}
			return -1;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}

package de.algoristic.evocode.util;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class NumberSystemUtils {

	public static List<String> terminatorChars() {
		return Arrays.asList("g,h,i,j,k,l,m,n,o,p".split(","));
	}

	public static String terminatorCharsRegex() {
		return terminatorChars().stream().collect(Collectors.joining("|", "[", "]"));
	}
	
	public static String randomTerminatorChar() {
		return pickRandom(terminatorChars());
	}

	public static List<String> decimalChars() {
		return IntStream.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9).mapToObj(String::valueOf).collect(Collectors.toList());
	}

	public static String randomDecimalChar() {
		return pickRandom(decimalChars());
	}

	public static List<String> hexChars() {
		return Arrays.asList("0,1,2,3,4,5,6,7,8,9,0,a,b,c,d,e,f".split(","));
	}

	public static String randomHexChar() {
		return pickRandom(hexChars());
	}

	private static <T> T pickRandom(List<T> ls) {
		int max = ls.size();
		int random = (new Random()).nextInt(max);
		return ls.get(random);
	}
}

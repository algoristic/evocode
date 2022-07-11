package de.algoristic.evocode.util;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class NumberSystemUtils {

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

	public static String hexToBinary(String hexString) {
		hexString = hexString.toUpperCase();
		int val = Integer.parseInt(hexString, 16);
		return Integer.toBinaryString(val);
	}

	public static int hexToDecimal(String hexString) {
		hexString = hexString.toUpperCase();
		return Integer.parseInt(hexString, 16);
	}

	public static int binaryToDecimal(String binString) {
		if(binString.isEmpty()) return 0;
		return Integer.parseInt(binString, 2);
	}

	public static <T> T pickRandom(List<T> ls) {
		int max = ls.size();
		int random = (new Random()).nextInt(max);
		return ls.get(random);
	}
}

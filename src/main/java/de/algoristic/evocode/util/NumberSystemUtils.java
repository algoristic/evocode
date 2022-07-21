package de.algoristic.evocode.util;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.lang3.StringUtils;

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

	public static String hexToBinaryWithLeadingZeroes(String hexString) {
		int length = (4 * hexString.length());
		String binary = hexToBinary(hexString);
		binary = StringUtils.leftPad(binary, length, '0');
		return binary;
	}

	public static String hexToBinary(String hexString) {
		return new BigInteger(hexString, 16).toString(2);
	}

	public static String hexToBinary(Character hexChar) {
		String hexString = String.valueOf(hexChar);
		String binaryString = hexToBinary(hexString);
		binaryString = StringUtils.leftPad(binaryString, 4, '0');
		return binaryString;
	}

	public static String binaryFlip(String binaryString, double chance) {
		StringBuffer resultBuffer = new StringBuffer();
		for(int i = 0; i < binaryString.length(); i++) {
			Character zeroOrOne = binaryString.charAt(i);
			if(! ((zeroOrOne == '1') || (zeroOrOne == '0'))) throw new RuntimeException("Unintended use: " + binaryString + "is no binary string.");
			double rnd = Math.random();
			if(rnd <= chance) {
				zeroOrOne = flip(zeroOrOne);
			}
			resultBuffer.append(zeroOrOne);
		}
		return resultBuffer.toString();
	}

	private static Character flip(Character zeroOrOne) {
		if(zeroOrOne == '1') return '0';
		else return '1';
	}

	public static String binaryToHex(String binaryString) {
		return new BigInteger(binaryString, 2).toString(16);
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

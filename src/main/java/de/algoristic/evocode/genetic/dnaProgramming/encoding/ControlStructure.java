package de.algoristic.evocode.genetic.dnaProgramming.encoding;

import static de.algoristic.evocode.util.NumberSystemUtils.binaryToDecimal;
import static de.algoristic.evocode.util.NumberSystemUtils.hexToBinary;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public abstract class ControlStructure {

	private final static String[] OPERATORS = {
		"<", ">", "<=", ">="
	};
	
	private final static List<Function<String, ControlStructure>> CONTROL_STRUCTURES;
	
	static {
		CONTROL_STRUCTURES = new ArrayList<>();
		CONTROL_STRUCTURES.add(operator -> new SimpleCondition(operator) );
		CONTROL_STRUCTURES.add(operator -> new HeadControlLoop(operator));
		CONTROL_STRUCTURES.add(operator -> new TailControlLoop(operator));
	}

	protected final List<Actor> actors = new ArrayList<>();
	protected final String operator;

	protected ControlStructure(final String operator) {
		this.operator = operator;
	}

	public void wrap(final List<Actor> actors) {
		this.actors.addAll(actors);
	}

	public abstract String getStart();
	public abstract String getEnd();

	public static ControlStructure of(ControlCodon controlCodon) {
		String controlEncoding = hexToBinary(controlCodon.getControlStructureCode());
		String controlStructureCode = controlEncoding.substring(0, 2);
		String controlOperatorCode = controlEncoding.substring(2);
		
		int controlOperatorPosition = binaryToDecimal(controlOperatorCode);
		int controlStructurePosition = binaryToDecimal(controlStructureCode);
		
		int operatorIndex = (controlOperatorPosition % OPERATORS.length);
		String operator = OPERATORS[operatorIndex];
		
		int controlStructureIndex = (controlStructurePosition % CONTROL_STRUCTURES.size());
		Function<String, ControlStructure> constructor = CONTROL_STRUCTURES.get(controlStructureIndex);
		return constructor.apply(operator);
	}
	
	private static class SimpleCondition extends ControlStructure {

		public SimpleCondition(final String operator) {
			super(operator);
		}

		@Override
		public String getStart() {
			return new StringBuffer("if (")
				.append(actors.get(0))
				.append(" ").append(operator).append(" ")
				.append(actors.get(1))
				.append(") {")
				.toString();
		}

		@Override
		public String getEnd() {
			return "}";
		}
	}
	private static class HeadControlLoop extends ControlStructure {

		public HeadControlLoop(String operator) {
			super(operator);
		}

		@Override
		public String getStart() {
			return new StringBuffer("while (")
				.append(actors.get(0))
				.append(" ").append(operator).append(" ")
				.append(actors.get(1))
				.append(") {")
				.toString();
		}

		@Override
		public String getEnd() {
			return "}";
		}
	}
	
	private static class TailControlLoop extends ControlStructure {

		public TailControlLoop(String operator) {
			super(operator);
		}

		@Override
		public String getStart() {
			return "do {";
		}

		@Override
		public String getEnd() {
			return new StringBuffer("} while (")
				.append(actors.get(0))
				.append(" ").append(operator).append(" ")
				.append(actors.get(1))
				.append(") {")
				.toString();
		}
	}
}

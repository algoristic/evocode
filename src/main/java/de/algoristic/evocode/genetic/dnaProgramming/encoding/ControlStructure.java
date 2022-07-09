package de.algoristic.evocode.genetic.dnaProgramming.encoding;

import static de.algoristic.evocode.util.NumberSystemUtils.binaryToDecimal;
import static de.algoristic.evocode.util.NumberSystemUtils.hexToBinary;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.apache.commons.lang3.StringUtils;

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
		CONTROL_STRUCTURES.add(_o -> new SimpleCodeBlock());
	}

	protected final String operator;
	protected Sensor leftOperand;
	protected Sensor rightOperand;
	protected final List<Actor> actors = new ArrayList<>();
	
	protected ControlStructure(final String operator) {
		this.operator = operator;
	}

	private ControlStructure withLeftHand(Sensor leftOperand) {
		this.leftOperand = leftOperand;
		return this;
	}

	private ControlStructure withRightHand(Sensor rightOperand) {
		this.rightOperand = rightOperand;
		return this;
	}

	public void wrap(final List<Actor> actors) {
		this.actors.addAll(actors);
	}

	public String getIndent() {
		return "\t";
	}

	public abstract String getStart();
	public abstract String getEnd();

	public String resolve(String indent) {
		return indent + "\n";
	}

	public static ControlStructure of(ControlCodon controlCodon, Sensor leftOperand, Sensor rightOperand) {
		String controlEncoding = hexToBinary(controlCodon.getControlStructureCode());
		controlEncoding = StringUtils.leftPad(controlEncoding, 4, "0");
		String controlStructureCode = controlEncoding.substring(0, 2);
		String controlOperatorCode = controlEncoding.substring(2);
		
		int controlOperatorPosition = binaryToDecimal(controlOperatorCode);
		int controlStructurePosition = binaryToDecimal(controlStructureCode);
		
		int operatorIndex = (controlOperatorPosition % OPERATORS.length);
		String operator = OPERATORS[operatorIndex];
		
		int controlStructureIndex = (controlStructurePosition % CONTROL_STRUCTURES.size());
		Function<String, ControlStructure> constructor = CONTROL_STRUCTURES.get(controlStructureIndex);
		return constructor.apply(operator)
			.withLeftHand(leftOperand)
			.withRightHand(rightOperand);
	}
	
	private static class SimpleCondition extends ControlStructure {

		public SimpleCondition(final String operator) {
			super(operator);
		}

		@Override
		public String getStart() {
			return new StringBuffer("if (")
				.append(leftOperand)
				.append(" ").append(operator).append(" ")
				.append(rightOperand)
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
				.append(leftOperand)
				.append(" ").append(operator).append(" ")
				.append(rightOperand)
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
				.append(leftOperand)
				.append(" ").append(operator).append(" ")
				.append(rightOperand)
				.append(");")
				.toString();
		}
	}
	
	private static class SimpleCodeBlock extends ControlStructure {

		public SimpleCodeBlock() {
			super("");
		}

		@Override
		public String getStart() {
			return "";
		}

		@Override
		public String getEnd() {
			return "";
		}
		
		@Override
		public String getIndent() {
			return "";
		}
	}
}

package de.algoristic.evocode.util;

import java.util.ArrayList;
import java.util.List;

public class Pattern {

	private final String pattern;
	private final List<Variable> variables;

	public Pattern(String pattern) {
		this.pattern = pattern;
		this.variables = new ArrayList<>();
	}

	public Pattern addVariable(String variable, Object value) {
		variables.add(new Variable(variable, value));
		return this;
	}

	private class Variable {
		private final String symbol;
		private final Object value;

		public Variable(String symbol, Object value) {
			this.symbol = symbol;
			this.value = value;
		}

		public String getSymbol() {
			return symbol;
		}

		public Object getValue() {
			return value;
		}

	}

	public String compile() {
		String resolvedPattern = pattern;
		for (Variable variable : variables) {
			String symbol = variable.getSymbol();
			if (resolvedPattern.contains(symbol)) {
				Object value = variable.getValue();
				String resolvableValue = String.valueOf(value);
				resolvedPattern = resolvedPattern.replace(symbol, resolvableValue);
			}
		}
		return resolvedPattern;
	}
}

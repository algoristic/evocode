package de.algoristic.evocode.genetic;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import de.algoristic.evocode.app.conf.EvolutionSettings;
import de.algoristic.evocode.app.io.FitnessValue;
import de.algoristic.evocode.app.periphery.ScriptEngineAdaptor;

public class FitnessFunction {

	private static final Map<String, ValueExractor> AVAILABLE_EXPRESSIONS;
	
	static {
		AVAILABLE_EXPRESSIONS = new HashMap<>();
		
		AVAILABLE_EXPRESSIONS.put("[score]", r -> r.getScore());
		AVAILABLE_EXPRESSIONS.put("[percentage]", r -> r.getPercentage());
		AVAILABLE_EXPRESSIONS.put("[rank]", r -> r.getRank());
		AVAILABLE_EXPRESSIONS.put("[survival]", r -> r.getSurvival());
		AVAILABLE_EXPRESSIONS.put("[lastSurvivorBonus]", r -> r.getLastSurvivorBonus());
		
		AVAILABLE_EXPRESSIONS.put("[bulletDamage]", r -> r.getBulletDamage());
		AVAILABLE_EXPRESSIONS.put("[bulletDamageBonus]", r -> r.getBulletDamageBonus());
		AVAILABLE_EXPRESSIONS.put("[ramDamage]", r -> r.getRamDamage());
		AVAILABLE_EXPRESSIONS.put("[ramDamageBonus]", r -> r.getRamDamageBonus());
		
		AVAILABLE_EXPRESSIONS.put("[firsts]", r -> r.getFirsts());
		AVAILABLE_EXPRESSIONS.put("[seconds]", r -> r.getSeconds());
		AVAILABLE_EXPRESSIONS.put("[thirds]", r -> r.getThirds());
	}
	
	private final String function;

	public FitnessFunction(final String function) {
		this.function = function;
	}

	public void evaluate(final FitnessValue rawValue) {
		String resolvableFunction = resolveVariables(rawValue);
		ScriptEngineAdaptor scriptEngine = ScriptEngineAdaptor.getInstance();
		double fitness = scriptEngine.evaluate(resolvableFunction);
		rawValue.setValue(fitness);
	}
	
	private String resolveVariables(FitnessValue rawValue) {
		String resolvableFunction = function;
		for(final String variable : AVAILABLE_EXPRESSIONS.keySet()) {
			if(function.contains(variable)) {
				final String key = variable;
				final ValueExractor extractor = AVAILABLE_EXPRESSIONS.get(key);
				final int value = extractor.extractValue(rawValue);
				String literalValue = String.valueOf(value);
				resolvableFunction = resolvableFunction.replace(variable, literalValue);
			}
		}
		if(resolvableFunction.contains("[")) {
			throw new RuntimeException("Fitness function contains unresolvable variables");
		}
		return resolvableFunction;
	}

	public static FitnessFunction getFunctionFor(int generationNumber) {
		EvolutionSettings settings = new EvolutionSettings();
		String function = settings.getFitnessFunction(generationNumber);
		return new FitnessFunction(function);
	}

	private interface ValueExractor extends Function<FitnessValue, Integer> {
		
		default public Integer extractValue(FitnessValue rawValue) {
			return this.apply(rawValue);
		}
		
	}
}

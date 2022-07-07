package de.algoristic.evocode.genetic;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import robocode.BattleResults;

public class FitnessFunction {

	private static final Map<String, ValueExractor> AVAILABLE_EXPRESSIONS;
	
	static {
		AVAILABLE_EXPRESSIONS = new HashMap<>();
		
		AVAILABLE_EXPRESSIONS.put("[score]", r -> r.getScore());
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

	public FitnessFunction(String function) {
		this.function = function;
	}
	
	public double evaluate(final BattleResults results) {
		String resolvableFunction = resolveVariables(results);
		ScriptEngineAdaptor scriptEngine = ScriptEngineAdaptor.getInstance();
		double result = scriptEngine.evaluate(resolvableFunction);
		return result;
	}
	
	private String resolveVariables(BattleResults battleResults) {
		String resolvableFunction = function;
		for(final String variable : AVAILABLE_EXPRESSIONS.keySet()) {
			if(function.contains(variable)) {
				final String key = variable;
				final ValueExractor extractor = AVAILABLE_EXPRESSIONS.get(key);
				final int value = extractor.extractValue(battleResults);
				String literalValue = String.valueOf(value);
				resolvableFunction = resolvableFunction.replace(variable, literalValue);
			}
		}
		if(resolvableFunction.contains("[")) {
			throw new RuntimeException("Fitness function contains unresolvable variables");
		}
		return resolvableFunction;
	}
	
	private interface ScriptEngineAdaptor {
		public double evaluate(String expression);
		
		public static ScriptEngineAdaptor getInstance() {
			return new InternalScriptEngine();
		}
	}
	
	private static class InternalScriptEngine implements ScriptEngineAdaptor {

		static ScriptEngine internalScriptEngineSingleton;
		
		static {
			ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
			internalScriptEngineSingleton = scriptEngineManager.getEngineByName("JavaScript");
			
		}
		
		@Override
		public double evaluate(String expression) {
			Object result;
			try {
				result = internalScriptEngineSingleton.eval(expression);
			} catch (ScriptException e) {
				throw new RuntimeException("Fitness function could not be evaluated", e);
			}
			double evaluated = 0;
			if(result instanceof Double) {
				evaluated = (double) result;
			} else if(result instanceof Integer) {
				evaluated = (double) (int) result;
			}
			return evaluated;
		}	
	}
	
	private interface ValueExractor extends Function<BattleResults, Integer> {
		
		default public Integer extractValue(BattleResults battleResults) {
			return this.apply(battleResults);
		}
		
	}
}

package hr.fer.zemris.lsystems.impl;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.custom.collections.Dictionary;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl.LSystemImpl;

class LSystemBuilderImplTest {
	Vector2D origin = new Vector2D(0,0);
	double angle = 0; 
	double unitLength = 1;
	double unitLengthDegreeScaler = 1/3;
	Dictionary<String, String> productions = new Dictionary<String, String>();
	Dictionary<String, Command> actions = new Dictionary<String, Command>();
	String axiom = "";

	@Test
	public void generateTest(){
		productions.put("F", "F+F--F+F");
		axiom = "F";
		LSystemBuilderImpl.LSystemImpl lSystem = new LSystemImpl(origin, angle, unitLength, 
																 unitLengthDegreeScaler, 
																 productions, actions, axiom);
		assertEquals("F", lSystem.generate(0));
		assertEquals("F+F--F+F", lSystem.generate(1));
		assertEquals("F+F--F+F+F+F--F+F--F+F--F+F+F+F--F+F", lSystem.generate(2));
	}

}

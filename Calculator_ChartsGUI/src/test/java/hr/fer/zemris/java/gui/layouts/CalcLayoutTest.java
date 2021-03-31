package hr.fer.zemris.java.gui.layouts;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.junit.jupiter.api.Test;

class CalcLayoutTest {

	@Test
	public void boundExceptionTest() {
		assertThrows(CalcLayoutException.class, () -> {
			new RCPosition(-1, 6);
		});
		
		assertThrows(CalcLayoutException.class, () -> {
			new RCPosition(6, 6);
		});
		
		assertThrows(CalcLayoutException.class, () -> {
			new RCPosition(4, -1);
		});
		
		assertThrows(CalcLayoutException.class, () -> {
			new RCPosition(4, 8);
		});
		
		assertThrows(CalcLayoutException.class, () -> {
			new RCPosition(1, 3);
		});
	}
	
	@Test
	public void addingMoreComponentsTest() {
		
		assertThrows(CalcLayoutException.class, () -> {
			JPanel panel = new JPanel(new CalcLayout(2));
			panel.add(new JLabel(""), new RCPosition(1,1));
			panel.add(new JLabel("1"), new RCPosition(1,1));
		});
	
	}
	
	@Test
	public void prefferedSizeTest() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel("");
		l1.setPreferredSize(new Dimension(10, 30));
		JLabel l2 = new JLabel("");
		l2.setPreferredSize(new Dimension(20, 15));
		p.add(l1, new RCPosition(2, 2));
		p.add(l2, new RCPosition(3, 3));
		Dimension dim = p.getPreferredSize();
		
		assertEquals(152, dim.width);
		assertEquals(158, dim.height);
	}
	
	
	@Test
	public void prefferedSizeTest2() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel("");
		l1.setPreferredSize(new Dimension(108, 15));
		JLabel l2 = new JLabel("");
		l2.setPreferredSize(new Dimension(16, 30));
		p.add(l1, new RCPosition(1, 1));
		p.add(l2, new RCPosition(3, 3));
		Dimension dim = p.getPreferredSize();
		
		assertEquals(152, dim.width);
		assertEquals(158, dim.height);
	}

}

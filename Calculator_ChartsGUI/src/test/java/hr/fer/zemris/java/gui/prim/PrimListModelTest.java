package hr.fer.zemris.java.gui.prim;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.swing.JList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PrimListModelTest {
	
	PrimListModel listModel;
	JList<Integer> rightList;
	JList<Integer> leftList;

	@BeforeEach
	private void setup() {
		listModel = new PrimListModel();
		
		rightList = new JList<Integer>(listModel);
		leftList = new JList<Integer>(listModel);
	}
	
	@Test
	public void checkifNumbersAreEqual() {
		
		for(int i = 0; i < 100; i++) {
			listModel.add(listModel.next());
			assertEquals(listModel.getElementAt(i), leftList.getModel().getElementAt(i));
			assertEquals(listModel.getElementAt(i), rightList.getModel().getElementAt(i));
		}
	
	}
}

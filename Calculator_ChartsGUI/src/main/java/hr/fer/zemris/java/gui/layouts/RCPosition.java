package hr.fer.zemris.java.gui.layouts;


/**
 * Class that represents position of component in calculator layout
 * by this way: (rows, columns). First position is number 1.
 * 
 * @author Marko
 *
 */
public class RCPosition {
	
	private int rowIndex;
	private int columnIndex;
	
	/**
	 * Constructor.
	 * 
	 * @param rowIndex
	 * @param columnIndex
	 */
	public RCPosition(int rowIndex, int columnIndex) {
		String message = "";
		message = checkPosition(rowIndex, columnIndex);
		if(!message.equals("")){
			throw new CalcLayoutException(message);
		}
		else {
			this.rowIndex = rowIndex;
			this.columnIndex = columnIndex;
		}
	}
	
	
	/**
	 * Method that takes the string configuration of the
	 * indexes and returns the RCPosition of the component.
	 * 
	 * @param text {@code String} string representation of layout indexes
	 * @return {@code RCPosition} of the component
	 */
	public static RCPosition parse(String text) {
		String[] indexes = text.split(",");
		int rowIndex = Integer.parseInt(indexes[0]);
		int columnIndex = Integer.parseInt(indexes[1]);
		
		return new RCPosition(rowIndex, columnIndex);
	}
	
	/**
	 * Getter.
	 * @return column index of the component
	 */
	public int getColumnIndex() {
		return columnIndex;
	}
	
	
	/**
	 * Getter.
	 * @return row index of the component
	 */
	public int getRowIndex() {
		return rowIndex;
	}
	
	@Override
	public String toString() {
		return "(" + rowIndex + "," + columnIndex + ")";
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof RCPosition)) {
			return false;
		}
		RCPosition other = (RCPosition) obj;
		if(this.rowIndex == other.rowIndex && this.columnIndex == other.columnIndex) {
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return Integer.valueOf(columnIndex).hashCode() + Integer.valueOf(rowIndex).hashCode();
	}
	
	
	/**
	 * Method that checks whether the defined position of the component
	 * should be accepted, and if not it returns the appropriate 
	 * message that will be encapsuled into an CalcLayout Exception.
	 * 
	 * @param rowIndex {@code int} row index of component
	 * @param columnIndex {@code int} column index of component
	 * @return {@code String} message for the user
	 */
	private String checkPosition(int rowIndex, int columnIndex) {
		if(rowIndex < 1 || rowIndex > 5) {
			return "Row index " + rowIndex + " is not defined.";
		}
		else if(columnIndex < 1 || columnIndex > 7) {
			return "Column index " + columnIndex + " is not defined.";
		}
		else if(rowIndex==1 && (columnIndex > 1 && columnIndex < 6)) {
			return "Can't define component on (" + rowIndex + "," + columnIndex + ").";
		}
		else return "";
	}

}

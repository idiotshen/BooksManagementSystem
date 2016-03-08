package view;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class MytableModel extends DefaultTableModel{

	public MytableModel(Vector tableData, Vector columnTitle) {
		// TODO Auto-generated constructor stub
		super(tableData, columnTitle);
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}


}

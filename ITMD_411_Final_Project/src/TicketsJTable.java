/**
 * This program ...
 * 
 * @author Yulia Thonippara (A20411313)
 * Final project for ITMD 411 Spring 2020
 */

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;
 
public class TicketsJTable {
	
	@SuppressWarnings("unused")
	private final DefaultTableModel tableModel = new DefaultTableModel();

	public static DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {

		ResultSetMetaData metaData = rs.getMetaData();

		// Names of columns
		Vector<String> columnNames = new Vector<String>();
		int columnCount = metaData.getColumnCount();
		for (int column = 1; column <= columnCount; column++) {
			columnNames.add(metaData.getColumnName(column));
		}

		// Data of the table
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		while (rs.next()) {
			Vector<Object> vector = new Vector<Object>();
			for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
				vector.add(rs.getObject(columnIndex));
			}
			data.add(vector);
		}
		// Return data/column names for JTable
		return new DefaultTableModel(data, columnNames);
		
	} // End buildTableModel()
	
} // End class TicketsJTable
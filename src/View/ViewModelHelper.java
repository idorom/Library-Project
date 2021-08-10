package View;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import LibExceptions.IleagalNumberExceptionGui;

public class ViewModelHelper {

	// Sort JTable on 2 columns selected in the function parameters
	public static void SortTableOn2Columns(JTable table, int Column1, int Column2)
	{
		TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
		table.setRowSorter(sorter);
		List<RowSorter.SortKey> sortKeys = new ArrayList<>();		 		
		sortKeys.add(new RowSorter.SortKey(Column1, SortOrder.ASCENDING));		 
		sortKeys.add(new RowSorter.SortKey(Column2, SortOrder.ASCENDING));
		sorter.setSortKeys(sortKeys);
		sorter.sort();
	}
	
	// Convert text to number and validate the result
	public static int IntK(String KNumber) throws NumberFormatException, IleagalNumberExceptionGui
	{
		int k=-1;
		try {

			k= Integer.parseInt(KNumber);

			if(k<=0)
				throw new IleagalNumberExceptionGui("Not a Positive Number");

		}catch(NumberFormatException e) {
			throw new IleagalNumberExceptionGui("Not a Number "+ KNumber);
		}
		return k;
	}
	
	// align table column to the right
	public static void alignTableColumnRight(JTable table, int columnIndex)
	{
		// right align 2nd column
	      TableColumnModel columnModel = table.getColumnModel();
	      TableColumn column = columnModel.getColumn(columnIndex); 
	      DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
	      renderer.setHorizontalAlignment(JLabel.RIGHT);
	      column.setCellRenderer(renderer);
	}
	
}

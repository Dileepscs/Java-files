package task2_JDBC_AWT;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class Screen extends JFrame {
	static JTable table = null;
	JButton buttons[] = new JButton[10];
	JTextField textFields[] = new JTextField[6];
	DataContent d;
	Screen() {
		// JLabel title, lempNo, lname, ljob, lsalary, ldeptno, lhireDate;
		// JTextField empno, name, job, salary, deptno;
		// JButton first, next, prev, last, add, edit, delete, save, clear, exit;
		//declarations
		JLabel[] labels = new JLabel[7];
		
		
		ActionListener ae = null;
		d = new DataContent();
		// createing instances of LABLES
		labels[0] = new JLabel("EMPLOYEE CRED");
		labels[1] = new JLabel("EMP NO  ");
		labels[2] = new JLabel("NAME  ");
		labels[3] = new JLabel("JOB  ");
		labels[4] = new JLabel("SALARY  ");
		labels[5] = new JLabel("DEPT NO  ");
		labels[6] = new JLabel("HIRE DATE  ");

		// creating instances of TExtfields
		textFields[0] = new JTextField();
		textFields[1] = new JTextField();
		textFields[2] = new JTextField();
		textFields[3] = new JTextField();
		textFields[4] = new JTextField();
		DateTextField hireDate = new DateTextField();
		textFields[5] = hireDate;
		// add(hireDate);
		// creatin instances of buttons
		buttons[0] = new JButton("FIRST");
		buttons[1] = new JButton("NEXT");
		buttons[2] = new JButton("PREVIOUS");
		buttons[3] = new JButton("LAST");
		buttons[4] = new JButton("ADD");
		buttons[5] = new JButton("EDIT");
		buttons[6] = new JButton("DELETE");
		buttons[7] = new JButton("SAVE");
		buttons[8] = new JButton("CLEAR");
		buttons[9] = new JButton("EXIT");

		// adding components to the frame
		for (int i = 0; i < labels.length; i++) {
			add(labels[i]);
		}
		for (int i = 0; i < textFields.length; i++) {
			add(textFields[i]);
			textFields[i].setEditable(false);
		}
		for (int i = 0; i < buttons.length; i++) {
			add(buttons[i]);
		}
		//ActionListner to the buttons
		ae = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(e.getSource() == buttons[0]) {	//action for FIRST button
					setDisableFields();
					table = constructTable();
					showRecord(0);
				}
				if(e.getSource() == buttons[1]) {	//action for NEXT button
					setDisableFields();
					if(textFields[0].getText()!="") {
						int row = Integer.parseInt(textFields[0].getText());
						if(row+1 == table.getRowCount()) {
							//warning to say it is the last record
							
						}
						else {
//							showRecord(row+1);
						}
					}
				}
				if(e.getSource() == buttons[2]) {	//action for PREVIOUS button
					setDisableFields();
					if(textFields[0].getText()!="") {
						String row = (textFields[0].getText());
						if(row.equals(table.getValueAt(0, 0))) {
							//warning to say it is the First record
							
						}
						else {
							showRecord(Integer.parseInt(row)-1);
						}
					}
				}
				if(e.getSource() == buttons[3]) {	//action for LAST button
					setDisableFields();
					showRecord(table.getRowCount()-1);
				}
				if(e.getSource() == buttons[4]) {	//action for ADD button
					setDisableFields();
					System.out.println("hello");
				}
				if(e.getSource() == buttons[5]) {	//action for EDIT button
					
					setEnableFields();
				}
				if(e.getSource() == buttons[6]) {	//action for DELETE button
					setDisableFields();
					if(textFields[0].getText()!="") {
						int row = Integer.parseInt(textFields[0].getText());
						
					}
					clearFields();
				}
				if(e.getSource() == buttons[7]) {	//action for SAVE button
					System.out.println("hello");
				}
				if(e.getSource() == buttons[8]) {	//action for CLEAR button
					clearFields();
				}
				if(e.getSource() == buttons[9]) {	//action for EXIT button
					System.exit(0);
				}
				
			}
		};		
		// positioning of the components
		int top = 100, left = 100, width = 70, height = 30;
		labels[0].setBounds(left + 250, top - 50, width + 100, height);
		int col = 1, i;
		// setting position of labels and textfields to the frame
		for (i = 1; i < labels.length; i++) {
			if (col % 2 != 0) {
				labels[i].setBounds(left, top + (i * height) + 10, width, height);
				textFields[i - 1].setBounds(left + 10 + width, top + (i * height) + 10, width + 100, height);
			} else {
				labels[i].setBounds(left + (6 * width), top + ((i - 1) * height) + 10, width, height);
				textFields[i - 1].setBounds(left + (6 * width) + 10 + width, top + ((i - 1) * height) + 10, width + 100,
						height);
			}
			col++;
			
		}
		left += 70;
		top += (height * ++i);
		int temp = top;
		col = 1;
		width = 80;
		// setting buttons to the frame
		for (i = 0; i < buttons.length; i++) {
			buttons[i].setBounds(left + (col++ * (width+20)), top, width, height);
			if (col % 5 == 0) {
				col = 1;
				top = top + height+20;
			}
			buttons[i].addActionListener(ae);
			if(i==7)
				left += 100;
		}
		
		//construction of table
		table = constructTable();
		
		//table dimensions
		JScrollPane sp = new JScrollPane(table);
		sp.setBounds(100, 500, 900, 200);
		sp.setEnabled(true);
		add(sp);
		showRecord(0);
		setSize(1100, 1100);
		setLayout(null);
		setVisible(true);
	}

	private JTable constructTable() {
		// TODO Auto-generated method stub
//		String[] columns = d.getColumns();
//		String[][] data = d.getData();
		String[][] data = {
		        { "101","Kundan Kumar Jha", "CLERK","12000", "20","03/05/2021" },
		        { "102","Anand Jha", "MANAGER","25000", "20","31/07/2019" },
		        
		};
		 
		        // Column Names
		String[] columns = { "EMP NO", "NAME","JOB","SALARY", "Department NO","HIRE DATE" };
		 
		 // Initializing the JTable
		JTable t =  new JTable(data, columns);
		t.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				for(int i=0;i<textFields.length;i++) {
					textFields[i].setText("");
				}
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				int row = table.getSelectedRow();
				showRecord(row);
			}
		});
		return t;
	}
	public void showRecord(int row) {
		for(int i=0;i<textFields.length;i++) {
			textFields[i].setText(table.getValueAt(row, i)+"");
		}
	}
	public void clearFields() {
		for(int i=0;i<textFields.length;i++) {
			textFields[i].setText("");
		}
	}
	public void setDisableFields() {
		for(int i=0;i<textFields.length;i++) {
			textFields[i].setEditable(false);
		}
	}
	public void setEnableFields() {
		for(int i=0;i<textFields.length;i++) {
			textFields[i].setEditable(true);
		}
	}
	
	
	public static void main(String args[]) {
		new Screen();
	}
}
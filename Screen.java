package task2_JDBC_AWT;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class Screen extends JFrame {

	static JTable table = null;
	JButton buttons[] = new JButton[10];
	JTextField textFields[] = new JTextField[6];
	BackEnd d;
	int cursor = 0;
	boolean flag = true;
	Frame f = null;

	Screen() {
		f = this;
		// JLabel title, lempNo, lname, ljob, lsalary, ldeptno, lhireDate;
		// JTextField empno, name, job, salary, deptno;
		// JButton first, next, prev, last, add, edit, delete, save, clear, exit;
		// declarations
		JLabel[] labels = new JLabel[7];

		ActionListener ae = null;
		d = new BackEnd();
		// createing instances of LABLES
		labels[0] = new JLabel("EMPLOYEE CRED");
		labels[1] = new JLabel("EMP NO  ");
		labels[2] = new JLabel("NAME  ");
		labels[3] = new JLabel("JOB  ");
		labels[4] = new JLabel("SALARY  ");
		labels[5] = new JLabel("HIRE DATE  ");
		labels[6] = new JLabel("DEPT NO  ");

		// creating instances of TExtfields
		textFields[0] = new JTextField();
		textFields[1] = new JTextField();
		textFields[2] = new JTextField();
		textFields[3] = new JTextField();

		DateTextField hireDate = new DateTextField();
		textFields[4] = hireDate;
		textFields[5] = new JTextField();
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
		// ActionListner to the buttons
		ae = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (e.getSource() == buttons[0]) { // action for FIRST button
					setDisableFields();
					table.setModel(constructModel());
					showRecord(0);
				}
				if (e.getSource() == buttons[1]) { // action for NEXT button
					setDisableFields();
					table.setModel(constructModel());
					if (textFields[0].getText() != "") {
						if (cursor + 1 == table.getRowCount()) {
							// warning to say it is the last record

						} else {
							showRecord(cursor + 1);
						}
					}
				}
				if (e.getSource() == buttons[2]) { // action for PREVIOUS button
					setDisableFields();
					table.setModel(constructModel());
					if (textFields[0].getText() != "") {
						if (cursor - 1 == -1) {
							// warning to say it is the First record

						} else {
							showRecord(cursor - 1);
						}
					}
				}
				if (e.getSource() == buttons[3]) { // action for LAST button
					setDisableFields();
					table.setModel(constructModel());
					// cursor = table.getRowCount() - 1;
					showRecord(table.getRowCount() - 1);
				}
				if (e.getSource() == buttons[4]) { // action for ADD button
					setEnableFields();
					clearFields();
					textFields[0].setEditable(false);
					int last_row_id = Integer.parseInt(table.getValueAt(table.getRowCount() - 1, 1).toString()) + 1;
					textFields[0].setText(last_row_id + "");
					flag = true;
				}
				if (e.getSource() == buttons[5]) { // action for EDIT button
					setEnableFields();
					textFields[1].setEnabled(false);
					flag = false;
				}
				if (e.getSource() == buttons[6]) { // action for DELETE button
					setDisableFields();
					if (textFields[0].getText() != "") {
						int row = Integer.parseInt(textFields[0].getText());
						// prompt to confirm delete
						d.deleteRecord(row);
					}
					table.setModel(constructModel());
					clearFields();
				}
				if (e.getSource() == buttons[7]) { // action for SAVE button
					if (!isEmptyFieldsAvailable()) {
						int id = Integer.parseInt(textFields[0].getText());
						String name = textFields[1].getText();
						String job = textFields[2].getText();
						String salary = textFields[3].getText();
						// String hDate = textFields[4].getText();
						// SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-DD");
						int deptno = Integer.parseInt(textFields[5].getText());
						d.addRecord(id, name, job, salary, new Date(hireDate.getDate().getTime()), deptno, flag);
						setDisableFields();
						table.setModel(constructModel());
					} else {
						// warning for empty fields

					}
				}
				if (e.getSource() == buttons[8]) { // action for CLEAR button
					clearFields();
				}
				if (e.getSource() == buttons[9]) { // action for EXIT button
					System.exit(0);
				}

			}

			private boolean isEmptyFieldsAvailable() {
				for (int i = 0; i < textFields.length; i++) {
					if (textFields[i].getText() == "") {
						return true;
					}
				}
				return false;
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
		col = 1;
		width = 80;
		// setting buttons to the frame
		for (i = 0; i < buttons.length; i++) {
			buttons[i].setBounds(left + (col++ * (width + 20)), top, width, height);
			if (col % 5 == 0) {
				col = 1;
				top = top + height + 20;
			}
			buttons[i].addActionListener(ae);
			if (i == 7)
				left += 100;
		}

		// construction of table
		table = new JTable() {
			public boolean isCellEditable(int nRow, int nCol) {
				return false;
			}
		};
		table.setCellSelectionEnabled(true);
		table.setModel(constructModel());
		JScrollPane sp = new JScrollPane(table);
		sp.setBounds(100, 500, 900, 200);
		add(sp);
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				cursor = table.getSelectedRow();
				System.out.println(table.getSelectedRow());
				if (cursor > -1) {
					showRecord(cursor);
				}
				table.setModel(constructModel());
			}
		});

		// table dimensions

		showRecord(0);
		setSize(1100, 800);// screen dimensions
		setLayout(null);
		setVisible(true);
	}

	private boolean getConfirmation(String msg) {
		int option = JOptionPane.showConfirmDialog(f, msg);
		if (option = JOptionPane.YES_OPTION)
			return true;
		return false;
	}

	private DefaultTableModel constructModel() {
		String[] columns = d.getColumns();
		String[][] data = d.getData();

		DefaultTableModel model = new DefaultTableModel(data, columns);
		return model;
	}

	public void showRecord(int row) {

		for (int i = 0; i < textFields.length; i++) {
			textFields[i].setText(table.getValueAt(row, i + 1) + "");
		}
		if (row > -1 && row < table.getRowCount()) {
			cursor = row;
		}
	}

	public void clearFields() {
		for (int i = 0; i < textFields.length; i++) {
			textFields[i].setText("");
		}
	}

	public void setDisableFields() {
		for (int i = 0; i < textFields.length; i++) {
			textFields[i].setEditable(false);
		}
	}

	public void setEnableFields() {
		for (int i = 1; i < textFields.length; i++) {
			textFields[i].setEditable(true);
		}
	}

	public static void main(String args[]) {
		new Screen();
	}
}

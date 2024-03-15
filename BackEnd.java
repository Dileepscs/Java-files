package task2_JDBC_AWT;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import jdbc_practice.Db;

public class BackEnd {
	Connection conn = null;

	String table = "emp_i222";
	int cols;

	BackEnd() {
		try {
			conn = Db.connect();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String[] getColumns() {
		ArrayList<String> arr = new ArrayList<>();
		try {
			DatabaseMetaData meta = conn.getMetaData();
			ResultSet rs = meta.getColumns(null, null, table, null);
			arr = new ArrayList<>();
			arr.add("S No");
			while (rs.next()) {
				arr.add(rs.getString(4));
			}
		} catch (SQLException e) {
			System.out.println("Error in column retrival");
		}

		// conversion of arraylist to array
		int i = 0;
		// System.out.println(arr.size());
		String s[] = new String[arr.size()];
		cols = arr.size();
		for (String t : arr) {
			s[i] = t;
			// System.out.println("t : " + t + "\n\t s[" + i + "] : " + s[i]);
			i++;
		}
		return s;
	}

	public int getRows(ResultSet res) {
		int totalRows = 0;
		try {
			res.last();
			totalRows = res.getRow();
			res.beforeFirst();
		} catch (Exception ex) {
			return 0;
		}
		return totalRows;
	}

	public ResultSet getResultSet() throws SQLException {
		Statement st;
		ResultSet rs = null;
		st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		String query = "select * from " + table + " ORDER BY eid ASC";
		rs = st.executeQuery(query);
		return rs;
	}

	public String[][] getData() {

		ArrayList<ArrayList<String>> arr = new ArrayList<>();
		try {
			ResultSet rs = getResultSet();
			int sno = 1;
			rs.beforeFirst();
			while (rs.next()) {
				ArrayList<String> temp = new ArrayList<>();
				temp.add(sno++ + "");
				for (int j = 1; j < cols; j++) {
					temp.add(rs.getString(j));
					// System.out.println(rs.getObject(j));
				}
				// System.out.println(temp);
				arr.add(temp);
			}
		} catch (SQLException e) {
			System.out.println("Error in data retrival");
		}
		// System.out.println(arr);
		// conversion list to arrays
		String[][] s = new String[arr.size()][cols];
		// System.out.println(arr.size());
		for (int i = 0; i < s.length; i++) {
			ArrayList<String> t = arr.get(i);
			int j = 0;
			// System.out.println(t);
			for (String ste : t) {
				s[i][j++] = ste;
			}
		}
		return s;
		// return rs;
	}

	void deleteRecord(int id) {
		try {
			ResultSet rs = getResultSet();
			rs.beforeFirst();
			while (rs.next()) {
				// System.out.println(rs.getInt(1));
				if (id == rs.getInt(1)) {
					rs.deleteRow();
					break;
				}
			}
			// rs.refreshRow();
		} catch (SQLException e) {
			System.out.println("Error in deletion of record");
		}
	}

	public void addRecord(int id, String name, String job, String salary, Date hDate, int deptno, boolean flag) {
		// TODO Auto-generated method stub
		try {
			ResultSet rs = getResultSet();
			if (flag) {
				// adding new record
				rs.moveToInsertRow();
				rs.updateInt(1, id);
				rs.updateString(2, name);
				rs.updateString(3, job);
				rs.updateFloat(4, Float.parseFloat(salary));
				rs.updateDate(5, (java.sql.Date) hDate);
				rs.updateInt(6, deptno);
				rs.insertRow();
			} else {
				rs.beforeFirst();
				while (rs.next()) {
					if (rs.getInt(1) == id) {
						rs.updateString(2, name);
						rs.updateString(3, job);
						rs.updateFloat(4, Float.parseFloat(salary));
						rs.updateDate(5, (java.sql.Date) hDate);
						rs.updateInt(6, deptno);
						rs.updateRow();
						break;
					}
				}

			}
			// rs.refreshRow();
		} catch (SQLException e) {
			System.out.println("Error in creation or updation of records");
			e.printStackTrace();
		}
	}
}

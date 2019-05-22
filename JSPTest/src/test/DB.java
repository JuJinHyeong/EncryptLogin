package test;

import java.sql.*;

import org.json.simple.JSONObject;

public class DB {
	

	public static Integer connect(String id,String pw) {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		String server = "localhost:3306"; // MySQL ���� �ּ�
		String database = "test"; // MySQL DATABASE �̸�
		String user_name = "root"; // MySQL ���� ���̵�
		String password = "crypto13"; // MySQL ���� ��й�ȣ
		
		Integer confirm=0;

		// 1.����̹� �ε�
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

		} catch (ClassNotFoundException e) {
			System.err.println(" !! <JDBC ����> Driver load ����: " + e.getMessage());
			e.printStackTrace();
		}

		// 2.����
		try {
			con = DriverManager.getConnection(
					"jdbc:mysql://" + server + "/" + database + "?serverTimezone=UTC&useSSL=false", user_name,
					password);
			System.out.println("���������� ����Ǿ����ϴ�.");

		} catch (SQLException e) {
			System.err.println("con ����:" + e.getMessage());
			e.printStackTrace();
		}
		try {
			pw=hash(pw);
			String query = "SELECT Count(*) FROM user WHERE user_id=\"" + id + "\" AND user_password=\"" + pw+"\"";
			stmt = con.createStatement();
			rs = stmt.executeQuery(query.toString());
			
			while(rs.next()) {
				//JSONObject json=new JSONObject();
				System.out.println(rs.getString(1));
				if(rs.getInt(1)==1) {
					System.out.println("success");
					confirm=1;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// 3.����
		try {
			if (con != null)
				con.close();
		} catch (SQLException e) {
		}
		
		return confirm;

	}
	public static String hash(String pw) {
		SHA sha256 = new SHA();
		byte[] arr = sha256.hash(pw.getBytes());
		String result="";
		for(int i=0; i<arr.length; i++){
            result+=Integer.toString(arr[i] & 0xFF, 16);
        }
		return result;
	}
}

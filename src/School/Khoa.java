package School;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Khoa {

	private int makhoa;
	private String tenkhoa;
	private Scanner scanner;
	private static Connection conn = null;
	
	private static void initDatabase() {
		conn = sqlconnection.dbConnector();
	}
	
	public Khoa() {
		this.makhoa = -1;
		this.tenkhoa = "";
		initDatabase();
	}
	
	public Khoa(int maKh, String tenKh) {
		this.makhoa = maKh;
		this.tenkhoa = tenKh;
		initDatabase();
	}

	public int getMakhoa() {
		return makhoa;
	}

	public void setMakhoa(int makhoa) {
		this.makhoa = makhoa;
	}

	public String getTenkhoa() {
		return tenkhoa;
	}

	public void setTenkhoa(String tenkhoa) {
		this.tenkhoa = tenkhoa;
	}
	
	public void NhapKhoa() {

		scanner = new Scanner(System.in);
		System.out.print("Nhap ma khoa: ");
		makhoa = scanner.nextInt();
		scanner.nextLine();
		System.out.print("Nhap ten khoa: ");
		tenkhoa = scanner.nextLine().trim();

		if (makhoa <= 0 || tenkhoa.isEmpty()) {
			System.out.println("Thong tin khong hop le!");
			System.out.println("Ma khoa so nguyen duong va ten khoa khong de trong");
			System.out.println(makhoa + " " + tenkhoa);
			return;
		}
		
		// Try to add to database
		if (ThemKhoa(makhoa, tenkhoa)) {
			System.out.println("Them thanh cong: ");
			System.out.println("Ma khoa: " + makhoa);
			System.out.println("Ten khoa: " + tenkhoa);
		} else {
			System.out.println("That bai!");
		}
	}
	
	public boolean ThemKhoa(int maKh, String tenkhoa) {
		
		try {
			
            if (conn.isClosed()) {
            	conn = sqlconnection.dbConnector();
            }
			
			String query = "INSERT INTO KHOA(makhoa, tenkhoa) VALUES (? ,?)";
			
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, makhoa);
			pstmt.setString(2, tenkhoa);
			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		finally {
			  if (conn != null) {
			    try {
			    	conn.close(); // <-- This is important
			    } catch (SQLException e) {
			      /* handle exception */
			    }
			  }
		}
	}
	
	public boolean KiemTraKhoa() {
		try {
			
            if (conn.isClosed()) {
            	conn = sqlconnection.dbConnector();
            }
			
			String query = "SELECT COUNT(*) FROM KHOA";
			PreparedStatement pstmt = conn.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {				
				return rs.getInt(1) == 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			  if (conn != null) {
			    try {
			    	conn.close(); // <-- This is important
			    } catch (SQLException e) {
			      /* handle exception */
			    }
			  }
		}
		return true;
	}
	
	public void DanhSachKhoa() {
		try {
			
            if (conn.isClosed()) {
            	conn = sqlconnection.dbConnector();
            }
			
			String query = "SELECT makhoa, tenkhoa FROM KHOA ORDER BY makhoa";
			PreparedStatement pstmt = conn.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			System.out.printf("%-10s %-30s%n", "Ma Khoa", "Ten Khoa");
			System.out.println("=".repeat(40));
			while (rs.next()) {
				int makhoa = rs.getInt("makhoa");
				String tenkhoa = rs.getString("tenkhoa");
				System.out.printf("%-10d %-30s%n", makhoa, tenkhoa);	
			}
			System.out.println("=".repeat(40));
			pstmt.close();
			rs.close();
		}catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Danh sach trong, hay tao khoa");
		}
		finally {
			  if (conn != null) {
			    try {
			    	conn.close(); // <-- This is important
			    } catch (SQLException e) {
			      /* handle exception */
			    }
			  }
		}
	}
	
	public boolean KiemTraTenKhoa(String tenKh) {
		try {
            if (conn.isClosed()) {
            	conn = sqlconnection.dbConnector();
            }
			
			String query = "SELECT COUNT(*) FROM KHOA WHERE tenkhoa = ?";
			PreparedStatement pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, tenKh);
			ResultSet rs = pstmt.executeQuery();
			int id = 0;
			if (rs.next()) {
				id = rs.getInt(1);
			}
			rs.close();
			return id > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			  if (conn != null) {
			    try {
			    	conn.close(); // <-- This is important
			    } catch (SQLException e) {
			      /* handle exception */
			    }
			  }
		}
		return false;
	}
	
}

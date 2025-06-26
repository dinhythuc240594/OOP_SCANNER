package School;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Khoa {

	private int makhoa;
	private String tenkhoa;
	private Scanner scanner;
	private static Connection conn = null;
	
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
	
	private static void initDatabase() {
		conn = sqlconnection.dbConnector();
	}
	
	public void NhapKhoa() {
		System.out.print("Nhap ma khoa: ");
		makhoa = scanner.nextInt();
		
		System.out.print("Nhap ten khoa: ");
		tenkhoa = scanner.nextLine();
		
		// Validate input
		if (makhoa <= 0 || tenkhoa.isEmpty()) {
			System.out.println("Thong tin khong hop le!");
			System.out.println("Ma khoa so nguyen duong va ten khoa khong de trong");
			return;
		}
		
		// Try to add to database
		if (ThemKhoa(makhoa, tenkhoa)) {
			System.out.println("Them thanh cong: ");
			System.out.printf("Ma khoa: ", makhoa);
			System.out.printf("Ten khoa: ", tenkhoa);
		} else {
			System.out.println("That bai!");
		}
		
	}
	
	private boolean ThemKhoa(int maKh, String tenKh) {
		
		try {
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
	}
	
	public boolean KiemTraKhoa() {
		try {
			String query = "SELECT COUNT(*) FROM KHOA";
			PreparedStatement pstmt = conn.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {				
				return rs.getInt(1) == 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public void DanhSachKhoa() {
		try {
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
		}catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Danh sach trong, hay tao khoa");
		}
	}
	
	public boolean KiemTraTenKhoa(String tenKh) {
		try {
			String query = "SELECT COUNT(*) FROM KHOA WHERE tenkhoa = ?";
			PreparedStatement pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, tenkhoa);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1) > 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
}

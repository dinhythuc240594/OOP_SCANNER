package School;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class MonHoc extends Khoa {

	private String maMonHoc;
	private String tenMonHoc;
	private int sotinchi;
	private String tenkhoa;
	private Scanner scanner;
	private static Connection conn = null;
	
	public MonHoc() {
		super();
		this.maMonHoc = "";
		this.tenMonHoc = "";
		this.sotinchi = 0;
		initDatabase();
	}
	
	public MonHoc(int maKh, String tenKh, String maMH, String tenMH, int sotinchi) {
		super(maKh, tenKh);
		this.maMonHoc = maMH;
		this.tenMonHoc = tenMH;
		this.sotinchi = sotinchi;
		initDatabase();
	}

	public String getMaMonHoc() {
		return maMonHoc;
	}

	public void setMaMonHoc(String maMonHoc) {
		this.maMonHoc = maMonHoc;
	}

	public String getTenMonHoc() {
		return tenMonHoc;
	}

	public void setTenMonHoc(String tenMonHoc) {
		this.tenMonHoc = tenMonHoc;
	}

	public int getSotinchi() {
		return sotinchi;
	}

	public void setSotinchi(int sotinchi) {
		this.sotinchi = sotinchi;
	}
	
	private static void initDatabase() {
		conn = sqlconnection.dbConnector();
	}
	
	public void ThemMonHoc() {
		System.out.println("==== Them mon hoc moi ====");
		if(KiemTraKhoa()) {
			System.out.println("Chua co thong tin khoa, them khoa truoc khi them mon hoc.");
			NhapKhoa();
			if(KiemTraKhoa()) {
				System.out.println("Khong the them mon hoc vi chua co khoa");
				return;
			}
		}
		
		System.out.print("Nhap ma mon hoc: ");
		maMonHoc = scanner.nextLine();
		
		System.out.print("Nhap ten mon hoc: ");
		tenMonHoc = scanner.nextLine();
		
		System.out.print("Nhap so tin chi: ");
		sotinchi = scanner.nextInt();
		scanner.nextLine();
		
		// Display available Khoa for selection
		System.out.println("\nDanh sách các khoa hiện có:");
		DanhSachKhoa();
		
		System.out.print("Nhập tên khoa: ");
		tenkhoa = scanner.nextLine().trim();
		
		// Validate input
		if (sotinchi > 0 || tenMonHoc.isEmpty() || maMonHoc.isEmpty() || tenkhoa.isEmpty()) {
			System.out.println("Thong tin khong hop le!");
			System.out.println("Ten va ma mon hoc khong de trong, so tin chi lon hon 0");
			return;
		}
		
		// Check if khoa exists
		if (!KiemTraTenKhoa(tenkhoa)) {
			System.out.println("Loi: Khoa '" + tenkhoa + "' khong ton tai!");
			return;
		}
		
		// Try to add to database
		if (ThemMonHoc(maMonHoc, tenMonHoc, sotinchi, tenkhoa)) {
			System.out.println("Them thanh cong: ");
			System.out.printf("Ma mon hoc: ", maMonHoc);
			System.out.printf("Ten mon hoc: ", tenMonHoc);
			System.out.printf("So tin chi: ", sotinchi);
			System.out.printf("Khoa: ", tenkhoa);
		} else {
			System.out.println("That bai!");
		}
	}
	
	private boolean ThemMonHoc(String maMh, String tenMh, int sotinchi, String tenKh) {
		try {
			String query = "INSERT INTO MONHOC (mamh, tenmh, sotinchi, tenkhoa) VALUES (?, ?, ?, ?)";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, maMh);
			pstmt.setString(2, tenMh);
			pstmt.setInt(3, sotinchi);
			pstmt.setString(4, tenKh);
			
			pstmt.executeUpdate();
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void XuatDanhSachMonHoc() {
		System.out.println(" ==== Danh sach tat ca mon hoc ==== ");
		System.out.printf("%-10s %-30s %-15s %-30s%n", "Ma Mon hoc", "Ten Mon Hoc", "So Tin Chi", "Khoa");
		System.out.println("=".repeat(85));
		if(!DanhSachMonHoc()) {
			System.out.println("Danh sach mon hoc trong, hay them vao!!!");
		}
		System.out.println("=".repeat(85));
	}
	
	
	private boolean DanhSachMonHoc() {
		try {
			String query = "SELECT mh.mamonhoc, mh.tenmonhoc, mh.sotinchi, mh.tenkhoa " +
					"FROM MONHOC mh " +
					"ORDER BY mh.mamonhoc";
			PreparedStatement pstmt = conn.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				String mamh = rs.getString("mamh");
				String tenmh = rs.getString("tenmh");
				int sotinchi = rs.getInt("sotinchi");
				String tenkhoa = rs.getString("tenkhoa");
						
				System.out.printf("%-10s %-30s %-15d %-30s%n", 
									mamh, tenmh, sotinchi, (tenkhoa != null ? tenkhoa : "N/A"));
			}
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}

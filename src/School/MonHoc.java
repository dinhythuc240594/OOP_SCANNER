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
	
	private static void initDatabase() {
		conn = sqlconnection.dbConnector();
	}
	
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
	
	public void ThemMonHoc() {
		scanner = new Scanner(System.in);
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
		System.out.println("\nDanh sach khoa hien co:");
		DanhSachKhoa();
		
		System.out.print("Nhap ten khoa: ");
		tenkhoa = scanner.nextLine();
		//// Validate input
		if (sotinchi < 0 || tenMonHoc.isEmpty() || maMonHoc.isEmpty() || tenkhoa.isEmpty()) {
			System.out.println("Thong tin khong hop le!");
			System.out.println("Ten va ma mon hoc khong de trong, so tin chi lon hon 0");
			return;
		}
//		
		 //// Check if khoa exists
		if (!KiemTraTenKhoa(tenkhoa)) {
			System.out.println("Loi: Khoa '" + tenkhoa + "' khong ton tai!");
			return;
		}
		
		//// Try to add to database
		if (ThemMonHoc(maMonHoc, tenMonHoc, sotinchi, tenkhoa)) {
			System.out.println("Them thanh cong: ");
			System.out.println("Ma mon hoc: " + maMonHoc);
			System.out.println("Ten mon hoc: " + tenMonHoc);
			System.out.println("So tin chi: " + sotinchi);
			System.out.println("Khoa: " + tenkhoa);
		} else {
			System.out.println("That bai!");
		}
	}
	
	private boolean ThemMonHoc(String maMh, String tenMh, int sotinchi, String tenKh) {
		try {

            if (conn.isClosed()) {
            	conn = sqlconnection.dbConnector();
            }
			
			String query = "INSERT INTO MONHOC (mamonhoc, tenmonhoc, sotinchi, tenkhoa) VALUES (?, ?, ?, ?)";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, maMh);
			pstmt.setString(2, tenMh);
			pstmt.setInt(3, sotinchi);
			pstmt.setString(4, tenKh);
			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
//			e.printStackTrace();
//			System.out.println("Them mon hoc loi" + e.getMessage());
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

			if (conn.isClosed()) {
            	conn = sqlconnection.dbConnector();
            }

			String query = "SELECT mh.mamonhoc, mh.tenmonhoc, mh.sotinchi, mh.tenkhoa " +
					"FROM MONHOC mh " +
					"ORDER BY mh.mamonhoc";
			PreparedStatement pstmt = conn.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				String mamh = rs.getString("mamonhoc");
				String tenmh = rs.getString("tenmonhoc");
				int sotinchi = rs.getInt("sotinchi");
				String tenkhoa = rs.getString("tenkhoa");
						
				System.out.printf("%-10s %-30s %-15d %-30s%n", 
									mamh, tenmh, sotinchi, (tenkhoa != null ? tenkhoa : "N/A"));
			}
			return true;
		} catch(SQLException e) {
//			e.printStackTrace();
//			System.out.println("Tai danh sach mon hoc loi" + e.getMessage());
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
	
	public void DanhSachMonHocCuaKhoa(String tenkhoa) {
		String query = "SELECT mh.mamonhoc, mh.tenmonhoc, mh.sotinchi, mh.tenkhoa " +
				"FROM MONHOC mh WHERE tenkhoa = ? " +
				"ORDER BY mh.mamonhoc";
		
		try{
			
			if (conn.isClosed()) {
            	conn = sqlconnection.dbConnector();
            }
			
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, tenkhoa);
			
			try (ResultSet rs = pstmt.executeQuery()) {
				System.out.printf("%-10s %-30s %-15s%n", "Ma MH", "TEN mh", "So Tin Chi");
				System.out.println("=".repeat(55));
				
				boolean found = false;
				while (rs.next()) {
					found = true;
					String mamh = rs.getString("mamonhoc");
					String tenmh = rs.getString("tenmonhoc");
					int sotinchi = rs.getInt("sotinchi");
					
					System.out.printf("%-10s %-30s %-15d%n", mamh, tenmh, sotinchi);
				}
				
				if (!found) {
					System.out.println("Khong co mon hoc nao thuoc khoa '" + tenkhoa + "'.");
				}
				
				System.out.println("=".repeat(55));
			}
			
		} catch (SQLException e) {
//			e.printStackTrace();
//			System.out.println("Tai danh sach mon hoc cua khoa loi" + e.getMessage());
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
	
	public int XoaMonHocCuaKhoa(String tenkhoa) {
		String sql = "DELETE FROM MonHoc WHERE tenkhoa = ?";
		
		try{
			
			if (conn.isClosed()) {
            	conn = sqlconnection.dbConnector();
            }
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, tenkhoa);
			
			int rowsAffected = pstmt.executeUpdate();
			return rowsAffected;
			
		} catch (SQLException e) {
//			System.err.println("Lỗi khi xóa môn học: " + e.getMessage());
			return -1;
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
	
	public void ManHinhCapNhatMonHoc() {
		scanner = new Scanner(System.in);
		System.out.println("\n=== CAP NHAT MON HOC ===");
		
		// Input MonHoc code to update
		System.out.print("Nhap ma mon hoc can cap nhat: ");
		String mamh = scanner.nextLine().trim();
		
		if (mamh.isEmpty()) {
			System.out.println("ma mon hoc rong!");
			return;
		}
		
		// Check if MonHoc exists
		if (!monHocExists(mamh)) {
			System.out.println("ma mon hoc '" + mamh + "' khong toon tai!");
			return;
		}
		
		// Display current MonHoc information
		System.out.println("\nThong tin mon hoc hien tai:");
		HienThiMonHoc(mamh);
		
		// Input new information
		System.out.println("\nNhap thong tin can cap nhat:");
		
		System.out.print("Ten mon hoc: ");
		String tenmh = scanner.nextLine().trim();
		
		System.out.print("So tin chi: ");
		int sotinchi = scanner.nextInt();
		if (sotinchi <= 0) {
			System.out.println("So tin chi lon hon 0");
			return;
		}
		
		// Try to update in database
		if (CapNhatMonHoc(mamh, tenmh, sotinchi, tenkhoa)) {
			System.out.println("Cap nhat mon hoc thanh cong!");
			System.out.println("\nThong tin mon hoc sau khi cap nhat:");
			HienThiMonHoc(mamh);
		} else {
			System.out.println("Cap nhat mon hoc that bai!");
		}
	}
	
	/**
	 * Check if MonHoc exists by mamh
	 */
	private boolean monHocExists(String mamh) {
		String sql = "SELECT COUNT(*) FROM MONHOC WHERE mamonhoc = ?";
		try {
	
            if (conn.isClosed()) {
            	conn = sqlconnection.dbConnector();
            }
            PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mamh);
			
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1) > 0;
				}
			}
		} catch (SQLException e) {
//			System.err.println("Lỗi khi kiểm tra môn học: " + e.getMessage());
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
	
	private void HienThiMonHoc(String mamh) {
		String sql = "SELECT mh.mamonhoc, mh.tenmonhoc, mh.sotinchi, mh.tenkhoa " +
					"FROM MONHOC mh " +
					"WHERE mh.mamonhoc = ?";
		
		try {

            if (conn.isClosed()) {
            	conn = sqlconnection.dbConnector();
            }
            
            PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mamh);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				String tenmh = rs.getString("tenmonhoc");
				int sotinchi = rs.getInt("sotinchi");
				String tenkhoa = rs.getString("tenkhoa");
					
				System.out.printf("Mã MH: %s\n", mamh);
				System.out.printf("Tên MH: %s\n", tenmh);
				System.out.printf("Số tín chỉ: %d\n", sotinchi);
				System.out.printf("Khoa: %s\n", (tenkhoa != null ? tenkhoa : "N/A"));
			} else {
				System.out.println("Không tìm thấy môn học có mã: " + mamh);
			}
		} catch (SQLException e) {
//			System.err.println("Lỗi khi hiển thị môn học: " + e.getMessage());
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
	
	private boolean CapNhatMonHoc(String maMh, String tenMh, int sotinchi, String tenKh) {
		
		String sql = "UPDATE MONHOC SET tenmonhoc = ?, sotinchi = ?, tenkhoa = ? " +
				"WHERE mamonhoc = ?";
	
	try {
	
	        if (conn.isClosed()) {
	        	conn = sqlconnection.dbConnector();
	        }
	        
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, maMh);
			pstmt.setString(2, tenMh);
			pstmt.setInt(3, sotinchi);
			pstmt.setString(4, tenKh);
			pstmt.executeUpdate();
			return true;
			
		} catch (SQLException e) {
//			System.err.println("Khong hien thi duocmon hoc: " + e.getMessage());
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

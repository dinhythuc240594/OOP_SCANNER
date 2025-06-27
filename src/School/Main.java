package School;

import java.sql.Connection;
import java.util.Scanner;

public class Main {
	
	private MonHoc mh;
	private static Connection conn = null;
	
	public void XoaTatCaMonHocCuaKhoa() {
		Scanner scanner = new Scanner(System.in);
		mh = new MonHoc();
		System.out.println("\n=== Xoa tat ca mon hoc cua Khoa ===");
		
		System.out.println("Danh sach khoa hien co:");
		mh.DanhSachKhoa();
		
		System.out.print("Nhap khoa muon xoa mon hoc: ");
		String tenKhoa = scanner.nextLine();
		
		// Validate input
		if (tenKhoa.isEmpty()) {
			System.out.println("Ten khoa rong!");
			return;
		}
		
		if (!mh.KiemTraTenKhoa(tenKhoa)) {
			System.out.println("Ten khoa " + tenKhoa + "khong ton tai");
			return;
		}
		
		// Show what will be deleted
		System.out.println("\nCac mon hoc bi xoa:");
		mh.DanhSachMonHocCuaKhoa(tenKhoa);
		
		// Ask for confirmation
		System.out.print("\nBan co chac chan xoa cac mon hoc cua khoa '" + tenKhoa + "'? (y/N): ");
		String confirmation = scanner.nextLine().trim().toLowerCase();
		
		if (!confirmation.equals("y") && !confirmation.equals("yes")) {
			System.out.println("Huy bo thao tac.");
			return;
		}
		
		// Perform deletion
		int deletedCount = mh.XoaMonHocCuaKhoa(tenKhoa);
		
		if (deletedCount >= 0) {
			System.out.println("Xoa thanh cong " + deletedCount + " mon hoc khoa '" + tenKhoa + "'.");
		} else {
			System.out.println("Xóa môn học thất bại!");
		}
	}
	
	
	private void ManHinhDanhSachMonHoc() {
		Scanner scanner = new Scanner(System.in);

		System.out.println("=== Quan Ly Danh Sach Mon Hoc ===");
		System.out.println("1. Hien thi danh sach tat ca mon hoc ");
		System.out.println("2. Them mon hoc moi");
		System.out.println("3. Xoa tat ca mon hoc theo khoa");
		System.out.println("4. Cap nhat thong tin mon hoc");
		System.out.println("5. Thêm khoa mới");
		System.out.println("6. Thoát");
		System.out.print("Vui lòng chọn chức năng (1-6): ");
		
		int choice = scanner.nextInt();
		scanner.nextLine(); // Consume newline
		mh = new MonHoc();

		switch (choice) {
			case 1:
				mh.XuatDanhSachMonHoc();
				break;
			case 2:
				System.out.print("Them mon hoc moi: ");
				mh.ThemMonHoc();
				break;
			case 3:
				XoaTatCaMonHocCuaKhoa();
				break;
			case 4:
				mh.ManHinhCapNhatMonHoc();
				break;
			case 5:
				mh.NhapKhoa();
				break;
			case 6:
				System.out.println("Tam biet!");
				break;
			default:
				System.out.println("Xin hay chon lai!");
		}
		
	}
	
	public static void main(String[] args) {
		// Create an instance of Main and call the function
		Main app = new Main();
		app.ManHinhDanhSachMonHoc();
	}
}

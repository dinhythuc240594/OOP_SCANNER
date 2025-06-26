package School;

import java.util.Scanner;

public class Main {
	
	private MonHoc mh;
	private Khoa kh;
	
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
		
		switch (choice) {
			case 1:
				mh.XuatDanhSachMonHoc();
				break;
			case 2:
				System.out.print("Them mon hoc moi: ");
				mh.ThemMonHoc();
			case 3:
				System.out.print("Nhap khoa muon xoa mon hoc: ");
				String tenKhoa = scanner.nextLine();
//				searchMonHocByName(tenmh);
				break;
			case 4:
//				addNewMonHoc(scanner);
				break;
			case 5:
				kh.NhapKhoa();
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

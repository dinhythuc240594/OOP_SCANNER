-- Create Khoa table
CREATE TABLE KHOA (
    makhoa INTEGER PRIMARY KEY,
    tenkhoa VARCHAR(255) NOT NULL UNIQUE
);

-- Create MonHoc table with foreign key to Khoa
CREATE TABLE MONHOC (
    mamonhoc VARCHAR(50) PRIMARY KEY,
    tenmonhoc VARCHAR(255) NOT NULL,
    sotinchi INTEGER NOT NULL,
    tenkhoa VARCHAR(255),
    FOREIGN KEY (tenkhoa) REFERENCES Khoa(tenkhoa)
);
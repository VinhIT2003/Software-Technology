--1. Thông tin giáo viên sinh năm 1992 đã làm chủ nhiệm
SELECT T.*
FROM Teacher T
JOIN Class C ON T.ID = C.ManagerID
WHERE YEAR(T.Birthday) = 1992;
--2. Thông tin giáo viên sinh năm 1992 chưa làm chủ nhiệm
SELECT T.*
FROM Teacher T
WHERE YEAR(T.Birthday) = 1992
AND NOT EXISTS (
    SELECT 1 FROM Class C WHERE C.ManagerID = T.ID
);
--3. Thông tin lớp trưởng của những lớp kết thúc năm 2015
SELECT S.*
FROM Student S
JOIN Class C ON S.ID = C.Perfect
WHERE C.EndYear = 2015;
--4. Đếm số lần giáo viên “Nguyen Van An” được phân công dạy lớp LH004
SELECT COUNT(*) AS SoLanPhanCong
FROM Course C
JOIN Teacher T ON C.TeacherID = T.ID
WHERE T.Name = 'Nguyen Van An' AND C.ClassID = 'LH004';
--5. Thông tin sinh viên đã thi đậu môn "Computer Network"
SELECT S.*
FROM Student S
JOIN Result R ON S.ID = R.StudentID
JOIN Subject Sub ON R.SubjectID = Sub.ID
WHERE Sub.Name = 'Computer Network' AND R.Mark >= 5;
--6. Số lượng sinh viên thi đậu mỗi môn học
SELECT Sub.Name AS TenMonHoc, COUNT(R.StudentID) AS SoSinhVienThiDau
FROM Result R
JOIN Subject Sub ON R.SubjectID = Sub.ID
WHERE R.Mark >= 5
GROUP BY Sub.Name;
--7. Với mỗi môn học, có hơn X sinh viên thi đậu
SELECT Sub.Name AS TenMonHoc, COUNT(R.StudentID) AS SoSinhVienThiDau
FROM Result R
JOIN Subject Sub ON R.SubjectID = Sub.ID
WHERE R.Mark >= 5
GROUP BY Sub.Name
HAVING COUNT(R.StudentID) > 2;


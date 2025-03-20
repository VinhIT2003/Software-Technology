# Báo Cáo Khảo Sát Hiện Trạng Tổ Chức Hệ Thống

## ***📖 Chương 1: Khảo sát hiện trạng tổ chức hệ thống***



### I. Hiện trạng tổ chức

#### 1) Đối nội
##### a) Cơ cấu tổ chức nội bộ
Phần mềm quản lý bán hàng hoạt động như một hệ thống thống nhất, hỗ trợ quản lý các quy trình kinh doanh và vận hành nội bộ. Cơ cấu tổ chức bao gồm:
- **Ban quản lý & điều hành:** Ra quyết định, quản lý hệ thống và giám sát hoạt động.
- **Bộ phận kinh doanh:** Quản lý đơn hàng, khách hàng, giá cả và chương trình khuyến mãi.
- **Bộ phận kho hàng:** Kiểm soát hàng tồn kho, nhập xuất kho, kiểm kê hàng hóa.
- **Bộ phận kế toán:** Xử lý hóa đơn, thanh toán, công nợ và báo cáo tài chính.
- **Bộ phận kỹ thuật (IT):** Đảm bảo phần mềm vận hành ổn định, hỗ trợ kỹ thuật và bảo mật dữ liệu.
- **Bộ phận chăm sóc khách hàng:** Hỗ trợ khách hàng, giải quyết khiếu nại và tư vấn sản phẩm.

##### b) Sơ đồ cơ cấu tổ chức nội bộ
![image](https://github.com/user-attachments/assets/054be565-fc16-4625-8e6a-f915c9426596)


#### 2) Đối ngoại
##### a) Tổ chức và môi trường của tổ chức về mặt đối ngoại
Phần mềm quản lý bán hàng không chỉ hoạt động nội bộ mà còn tương tác với các yếu tố bên ngoài như:
- **Khách hàng:** Giao tiếp qua đơn hàng, hóa đơn, chương trình khuyến mãi, chăm sóc khách hàng.
- **Nhà cung cấp:** Liên kết nhập hàng, quản lý hợp đồng, kiểm soát chất lượng sản phẩm.
- **Cơ quan thuế & tài chính:** Đáp ứng các yêu cầu về kế toán, hóa đơn điện tử, báo cáo thuế.
- **Ngân hàng & cổng thanh toán:** Hỗ trợ giao dịch tài chính, thanh toán trực tuyến.
- **Đối tác vận chuyển:** Kết nối với đơn vị giao hàng để theo dõi đơn hàng, tối ưu vận chuyển.

##### b) Môi trường kinh doanh ảnh hưởng đến phần mềm
- **Yếu tố công nghệ:** Cập nhật xu hướng công nghệ như AI, Big Data, IoT để nâng cao hiệu quả.
- **Yếu tố pháp lý:** Tuân thủ quy định về bảo mật dữ liệu (GDPR, Nghị định 52/2013/NĐ-CP).
- **Yếu tố thị trường:** Cạnh tranh, nhu cầu khách hàng, xu hướng tiêu dùng.

---

### II. Hiện trạng nghiệp vụ hệ thống

Phần mềm quản lý bán hàng hỗ trợ nhiều nghiệp vụ chính, bao gồm:

| **Nghiệp vụ**                 | **Quy trình liên quan**                                       |
|--------------------------------|----------------------------------------------------------------|
| Quản lý đơn hàng              | Tạo đơn hàng, xử lý thanh toán, in hóa đơn, theo dõi đơn hàng |
| Quản lý danh mục và sản phẩm  | Nhập hàng, xuất hàng, kiểm kê hàng tồn kho theo nhà cung cấp |
| Quản lý khách hàng            | Lưu trữ thông tin khách hàng, lịch sử mua hàng, khuyến mãi   |
| Quản lý nhà cung cấp          | Quản lý thông tin nhà cung cấp, hợp đồng, đặt hàng nhập kho  |
| Quản lý tài chính - kế toán   | Theo dõi doanh thu, công nợ, lập báo cáo tài chính           |
| Báo cáo & thống kê            | Xuất báo cáo doanh thu, báo cáo tồn kho, phân tích dữ liệu bán hàng |

#### Nghiệp vụ dưới góc nhìn của người làm quản lý
Các nghiệp vụ quan trọng nhất bao gồm:
- **Giám sát doanh thu và lợi nhuận:** Theo dõi tình hình bán hàng, hiệu quả kinh doanh.
- **Kiểm soát tồn kho:** Đảm bảo hàng hóa đủ đáp ứng nhu cầu nhưng không bị tồn đọng.
- **Quản lý khách hàng & chăm sóc khách hàng:** Xây dựng quan hệ bền vững với khách hàng.
- **Ra quyết định chiến lược:** Dựa trên báo cáo dữ liệu để điều chỉnh chính sách kinh doanh.

| Nghiệp vụ             | Các công đoạn                                           | Bộ phận liên quan  |
|-----------------------|--------------------------------------------------------|-------------------|
| Quản lý đơn hàng     | Tạo đơn hàng → Thanh toán → In hóa đơn → Cập nhật doanh thu | Người quản lý    |
| Nhập hàng           | Kiểm tra hàng cần nhập → Đặt hàng từ nhà cung cấp → Nhận hàng → Cập nhật tồn kho | Người quản lý    |
| Kiểm kê kho         | Kiểm tra số lượng thực tế → So sánh với dữ liệu → Điều chỉnh nếu cần | Người quản lý    |
| Quản lý khách hàng  | Lưu thông tin → Theo dõi lịch sử mua hàng → Chăm sóc khách hàng | Người quản lý    |
| Báo cáo & thống kê      | Thu thập dữ liệu → Tổng hợp → Phân tích → Xuất báo cáo | Kế toán                |

---

### III. Câu hỏi phỏng vấn khách hàng để lấy yêu cầu xây dựng quy trình nghiệp vụ

#### 📌 Câu hỏi chung về quy trình hiện tại
1. Anh/chị có thể mô tả quy trình bán hàng của cửa hàng/doanh nghiệp không?
2. Hiện tại, anh/chị gặp những khó khăn gì trong quá trình quản lý bán hàng?
3. Những công đoạn nào đang tốn nhiều thời gian nhất?

#### 📌 Câu hỏi về quản lý sản phẩm & nhập hàng
4. Cách anh/chị theo dõi lượng hàng tồn kho hiện nay như thế nào?
5. Tần suất kiểm kê kho hàng là bao lâu? Có gặp khó khăn gì trong việc này không?
6. Khi phát sinh đơn hàng lớn, việc nhập kho và xử lý đơn hàng có bị chậm trễ không?

#### 📌 Câu hỏi về chăm sóc khách hàng 
7. Anh/chị có lưu trữ thông tin khách hàng không? Nếu có, làm cách nào?
8. Anh/chị có theo dõi lịch sử mua hàng của khách không? Nếu có, cách nào?

#### 📌 Câu hỏi về báo cáo & tài chính
9. Anh/chị có cần báo cáo doanh thu, lợi nhuận theo ngày/tháng/năm không?
10. Khi cần kiểm tra công nợ khách hàng, anh/chị mất bao lâu để tổng hợp dữ liệu?
11. Hiện tại, việc theo dõi dòng tiền trong doanh nghiệp có gặp vấn đề gì không?

---

### IV. Phân công công việc

| **Họ tên thành viên**      | **Công việc phân công**                                     | **Tiến độ** |
|---------------------------|-------------------------------------------------------------|------------|
| Trần Quang Vinh          | Trình bày hiện trạng tổ chức hệ thống (đối nội)            | 100%       |
| Đặng Nguyễn Quốc Dương   | Trình bày hiện trạng tổ chức hệ thống (đối nội)            | 100%       |
| Trần Quang Vinh          | Trình bày hiện trạng tổ chức nghiệp vụ hệ thống           | 100%       |
| Nguyễn Trí Đức          | Đặt các câu hỏi phỏng vấn để lấy yêu cầu khách hàng         | 100%       |
| Phạm Quang Khiêm        | Đặt các câu hỏi phỏng vấn để lấy yêu cầu khách hàng        |   100%         |
| Đặng Nguyễn Quốc Dương  | Đặt các câu hỏi phỏng vấn để lấy yêu cầu khách hàng        |  100%          |

---
### V. Các thành viên trong nhóm
- 🚀 Thành viên 1: 3121411226-Trần Quang Vinh(nhóm trưởng):  https://vinhit2003.github.io/MyWebSite/
- 🌟 Thành viên 2: 3121411060-Nguyễn Trí Đức: https://duc01ai.github.io/
- ⚡ Thành viên 3: 3121411045-Đặng Nguyễn Quốc Dương: https://yonorikomana.github.io/DuongCV/
- 🎯 Thành viên 4: 3121411100-Phạm Quang Khiêm: https://khiemne.github.io/khiempham1122.github.io/
---

This is my website and also my group: https://vinhit2003.github.io/MyWebSite/


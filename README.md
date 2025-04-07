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

## ***📖  Chương 2: Đặc Tả Yêu Cầu Phần Mềm

## 🔹 I. Mô Tả Yêu Cầu Khách Hàng (User Stories)
**Laptop Vang** hướng đến việc xây dựng một hệ thống bán hàng trực tuyến chuyên nghiệp, tối ưu hóa **quy trình mua sắm, quản lý đơn hàng và kho hàng** một cách hiệu quả.

---

## 🛒 1. Quy Trình Mua Sắm Của Khách Hàng
Để bắt đầu mua sắm, khách hàng cần **đăng ký tài khoản** với đầy đủ thông tin cá nhân. Sau khi đăng ký thành công, khách hàng có thể **đăng nhập, duyệt sản phẩm, thêm vào giỏ hàng và tiến hành đặt hàng**.

- ✅ Khách hàng có thể đặt **nhiều đơn hàng khác nhau**, mỗi đơn hàng có một **ID riêng** và chứa **nhiều sản phẩm** theo nhu cầu.
- ✅ Khi đặt hàng, khách hàng phải **chọn phương thức thanh toán** và đảm bảo **nhập thông tin chính xác**.
- ✅ Hệ thống sẽ **lưu trữ đơn hàng trong cơ sở dữ liệu** và chờ **admin xử lý**.

---

## 📂 2. Quản Lý Danh Mục & Sản Phẩm
**Admin** chịu trách nhiệm **đăng tải và quản lý** thông tin sản phẩm trên hệ thống.

### 📁 **Danh mục sản phẩm**
- 🏷️ Mỗi sản phẩm thuộc về một **danh mục cụ thể** (ví dụ: Laptop Gaming, Laptop Văn Phòng…).
- 🛠️ **Admin** có thể **thêm, chỉnh sửa hoặc xóa danh mục** khi cần thiết.

### 💻 **Sản phẩm**
- 🔑 Mỗi sản phẩm có **mã sản phẩm duy nhất**, tên, thông số kỹ thuật, giá bán và hình ảnh minh họa.
- 🏷️ Đặc biệt, mỗi sản phẩm có **nhiều chiếc khác nhau**, mỗi chiếc có **mã IMEI riêng** để phân biệt.
- 🛒 Khi hàng hóa được cập nhật lên hệ thống, **khách hàng có thể xem chi tiết và đặt mua**.

---

## 📦 3. Quản Lý Đơn Hàng & Xử Lý Giao Dịch
- 📝 **Admin** có toàn quyền theo dõi và xử lý các đơn hàng do khách hàng đặt.
- ⏳ Khi khách hàng đặt hàng, đơn hàng sẽ có trạng thái **"Waiting" (Đang chờ xử lý)**.
- ✅ Sau khi kiểm tra và xác nhận, **admin cập nhật trạng thái** thành **"Confirming" (Đã xử lý)**.
- 🚫 Nếu khách hàng có hành vi **bom hàng từ 2 đơn trở lên**, tài khoản sẽ bị **xóa và cấm mua sắm vĩnh viễn**.

### 📜 **Hóa đơn xuất hàng**
Sau khi đơn hàng được xác nhận, **admin tiến hành tạo hóa đơn xuất hàng**, bao gồm:
- 🆔 **Mã phiếu xuất**,
- 🛡️ **Mã quản trị viên phụ trách**,
- 👤 **Thông tin khách hàng**,
- 📦 **Chi tiết sản phẩm** (*mã IMEI, giá gốc, giá sau khi áp dụng khuyến mãi*).

### 🎁 **Chương trình khuyến mãi**
Hệ thống hỗ trợ **mã khuyến mãi** để áp dụng các chương trình ưu đãi, chẳng hạn như:
- 🎂 **Tri ân sinh nhật khách hàng**,
- 🎉 **Kỷ niệm 10 năm thành lập công ty**,
- 💰 **Giảm giá cho khách hàng thân thiết**.

---

## 🏪 4. Quản Lý Nhập Hàng & Kho Hàng
**Laptop Vang** chỉ có **một kho trung tâm** để quản lý toàn bộ hàng hóa.

- 📊 Khi hàng sắp hết, **admin kiểm tra số lượng tồn kho** và **tạo phiếu nhập hàng để bổ sung**.
- 📞 **Admin liên hệ với nhà cung cấp** để đặt hàng theo danh mục sản phẩm.
- 📦 Mỗi lô hàng nhập về sẽ chứa **nhiều sản phẩm** thuộc danh mục tương ứng.
- 🔍 Từng sản phẩm trong kho sẽ được **quản lý theo mã IMEI**, giúp phân biệt ngay cả khi cùng model.

📢 Sau khi **nhập hàng thành công**, admin **cập nhật dữ liệu lên hệ thống** để sản phẩm sẵn sàng cho khách hàng đặt mua.

---

## 📊 5. Thống Kê Doanh Thu & Báo Cáo Kinh Doanh
Hệ thống hỗ trợ **thống kê doanh thu** dựa trên dữ liệu **nhập hàng và xuất hàng**, giúp admin đánh giá **tình hình kinh doanh** và đề xuất **chiến lược phù hợp**.

- 📈 Các báo cáo doanh thu sẽ được thể hiện qua **biểu đồ trực quan**, giúp admin dễ dàng theo dõi **xu hướng bán hàng**.
- 📑 Dữ liệu được phân tích theo nhiều tiêu chí như:
  - 🕒 **Thời gian**,
  - 🏷️ **Danh mục sản phẩm**,
  - 📦 **Số lượng đơn hàng**.  

🛠️ Điều này giúp cửa hàng **đưa ra quyết định tối ưu hóa kinh doanh**.

---

## ✉️ 6. Tích Hợp Liên Hệ Khách Hàng Qua Email
Hệ thống tích hợp tính năng **gửi email trực tiếp** đến khách hàng ngay trên nền tảng, giúp **admin liên hệ nhanh chóng** mà không cần đăng nhập vào email riêng.  

📩 **Chức năng này giúp:**
- ✅ **Xác nhận đơn hàng**,
- 🎁 **Gửi thông báo khuyến mãi**,
- 📞 **Hỗ trợ chăm sóc khách hàng**.

---

## 🎯 **Kết Luận**
Với hệ thống quản lý bán hàng trực tuyến chuyên nghiệp, **Laptop Vang** cam kết mang lại:
- 🚀 **Trải nghiệm mua sắm hiện đại**,
- ✅ **Minh bạch trong quản lý đơn hàng**,
- 🔥 **Tối ưu hiệu suất kinh doanh**,
- 🤝 **Nâng cao chất lượng dịch vụ khách hàng**.

---


This is my website and also my group: https://vinhit2003.github.io/MyWebSite/


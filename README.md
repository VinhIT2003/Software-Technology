# Phân tích và thiết kế hệ thống

## 1. Phân tích và thiết kế hệ thống

### 1.1 Đặc tả yêu cầu chức năng

#### Đối với khách hàng:
- **Đăng ký, đăng nhập và quản lý tài khoản**
- **Tìm kiếm, xem và lọc sản phẩm**
- **Thêm sản phẩm vào giỏ hàng**
- **Đặt hàng và thanh toán**
- **Theo dõi đơn hàng**
- **Đánh giá sản phẩm**
- **Nhận thông báo về khuyến mãi và trạng thái đơn hàng**

#### Đối với quản trị viên:
- **Quản lý danh mục sản phẩm**
- **Quản lý kho hàng**
- **Xử lý đơn hàng**
- **Quản lý khách hàng**
- **Tạo báo cáo và thống kê**
- **Quản lý chương trình khuyến mãi**
- **Phân quyền người dùng hệ thống**

### 1.2 Đặc tả yêu cầu phi chức năng
- **Hiệu suất**: Thời gian tải trang < 3 giây, xử lý thanh toán < 5 giây
- **Bảo mật**: Mã hóa dữ liệu người dùng, bảo vệ thông tin thanh toán, xác thực 2 yếu tố
- **Khả năng mở rộng**: Hỗ trợ tối thiểu **1000 người dùng đồng thời**
- **Độ tin cậy**: Hệ thống hoạt động **99.9% thời gian**
- **Khả năng sử dụng**: Giao diện thân thiện, đáp ứng trên mọi thiết bị

### 1.3 Thiết kế kiến trúc hệ thống

#### Kiến trúc tổng thể
- **Frontend**: React.js, Redux, Bootstrap
- **Backend**: Node.js, Express
- **Cơ sở dữ liệu**: MongoDB
- **API Gateway**: Microservices architecture
- **Dịch vụ thanh toán**: Tích hợp với **PayPal, Stripe, VNPay**

#### Sơ đồ kiến trúc
> [Mô tả sơ đồ kiến trúc hệ thống với các thành phần và luồng dữ liệu]

### 1.4 Thiết kế cơ sở dữ liệu

#### Mô hình quan hệ dữ liệu
```plaintext
Users (id, username, email, password, role, status, created_at, updated_at)
Products (id, name, description, price, discount_price, category_id, stock_quantity, image_url, status)
Categories (id, name, parent_id, description, image_url)
Orders (id, user_id, total_amount, payment_method, payment_status, shipping_address, order_status, created_at)
OrderItems (id, order_id, product_id, quantity, price)
Cart (id, user_id, created_at)
CartItems (id, cart_id, product_id, quantity, added_at)
```

#### Index và tối ưu hóa
- **Index** cho các trường tìm kiếm phổ biến: `product_name`, `category_id`, `user_email`
- **Caching** cho dữ liệu sản phẩm và danh mục

### 1.5 Thiết kế giao diện người dùng (UI/UX)

#### Nguyên tắc thiết kế
- **Đơn giản, trực quan và nhất quán**
- **Phản hồi nhanh và rõ ràng**
- **Tương thích với nhiều thiết bị (Responsive design)**
- **Dễ dàng điều hướng**

#### Wireframes
- **Trang chủ**
- **Trang danh mục sản phẩm**
- **Trang chi tiết sản phẩm**
- **Trang giỏ hàng và thanh toán**
- **Dashboard quản trị**

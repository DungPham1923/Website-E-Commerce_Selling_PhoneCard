# Dự án: Shop Bán Thẻ Điện Thoại

## Giới thiệu
Dự án **Shop Bán Thẻ Điện Thoại** được phát triển nhằm cung cấp nền tảng bán các loại thẻ điện thoại online, cho phép khách hàng mua thẻ một cách nhanh chóng, thuận tiện. Hệ thống hỗ trợ các chức năng cơ bản như quản lý thẻ, giao dịch, và các chương trình khuyến mãi hấp dẫn.

## Thông tin cá nhân
- **Họ và tên**: Phạm Anh Dũng  
- **Kỳ học**: 5  
- **Trường**: Đại Học FPT  

## Các tính năng chính
1. **Quản lý danh mục thẻ:**
   - Hỗ trợ nhiều nhà mạng như Viettel, Mobifone, Vinaphone, v.v.
   - Cung cấp các mệnh giá thẻ từ 10.000 đến 500.000 VNĐ.

2. **Giao dịch trực tuyến:**
   - Thanh toán qua nhiều hình thức như ngân hàng trực tuyến (VnPay), ví điện tử.
   - Hỗ trợ mã hóa thông tin giao dịch đảm bảo an toàn.

3. **Chương trình khuyến mãi:**
   - Giảm giá theo dịp lễ, sự kiện.
   - Hỗ trợ sử dụng voucher mua hàng.

4. **Quản trị hệ thống:**
   - Quản lý thông tin khách hàng, nhà mạng, và các giao dịch.
   - Báo cáo doanh thu, phân tích dữ liệu.

## Công nghệ sử dụng
- **Backend**: Java (Servlet, JSP)
- **Frontend**: HTML, CSS, JavaScript
- **Cơ sở dữ liệu**: MySQL
- **Thanh toán trực tuyến**: VnPay
- **IDE**: IntelliJ IDEA, Eclipse
- **Triển khai**: Tomcat Server

## Cấu trúc dự án
├── src/ │ ├── dao/ # Data Access Layer │ ├── model/ # Lớp đối tượng (Entity) │ ├── servlet/ # Controller (Servlet) │ └── utils/ # Tiện ích (hỗ trợ chung) ├── web/ │ ├── css/ # Tệp CSS │ ├── js/ # Tệp JavaScript │ └── views/ # Giao diện JSP ├── resources/ │ └── db.sql # Câu lệnh tạo và khởi tạo cơ sở dữ liệu └── README.md # Tệp hướng dẫn dự án

## Hướng dẫn cài đặt
1. **Yêu cầu hệ thống**:
   - Java 8 hoặc mới hơn.
   - MySQL 5.7 hoặc mới hơn.
   - Apache Tomcat 9 hoặc mới hơn.

2. **Các bước cài đặt**:
   - Clone dự án từ GitHub:  
     ```bash
     git clone https://github.com/username/phone-card-shop.git
     ```
   - Cấu hình cơ sở dữ liệu:
     - Tạo một cơ sở dữ liệu mới trong MySQL.
     - Nhập tệp `db.sql` vào cơ sở dữ liệu.
   - Cập nhật thông tin kết nối trong tệp cấu hình (nếu cần):  
     ```java
     // Ví dụ tệp utils/DatabaseUtils.java
     String DB_URL = "jdbc:mysql://localhost:3306/phone_card_shop";
     String USER = "root";
     String PASS = "password";
     ```
   - Triển khai ứng dụng trên Apache Tomcat.
   - Truy cập ứng dụng tại `http://localhost:8080`.

## Tác giả
- **Họ và tên**: Phạm Anh Dũng  
- **Liên hệ**: phamdung2672004@gmail.com  
- **GitHub**: [github.com/PhamAnhDungdev](https://github.com/PhamAnhDungdev)

---

Cảm ơn bạn đã quan tâm đến dự án này! Nếu có bất kỳ câu hỏi hoặc góp ý nào, vui lòng liên hệ với tôi.

<%-- 
    Document   : order
    Created on : Jun 5, 2024, 9:52:30 PM
    Author     : Bravo 15
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import = "java.text.DecimalFormat" %>
<%@page import = "Model.*" %>
<%@page import = "DAL.*" %>
<%@page import = "java.util.*" %>   
<%@page session="true" %>
<%@ page import="Model.Order" %>
<%@ page import="Model.OrderDetails" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <!-- mobile metas -->
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="viewport" content="initial-scale=1, maximum-scale=1">
        <!-- site metas -->
        <title>The Card Shop - Lịch sử giao dịch</title>
        <meta name="keywords" content="">
        <meta name="description" content="">
        <meta name="author" content="">
        <link rel="icon" href="images/logo/logo_icon.png" type="image/x-icon">
        <!-- site icon -->
        <link rel="icon" href="images/fevicon.png" type="image/png" />
        <!-- bootstrap css -->
        <link rel="stylesheet" href="css/bootstrap.min.css" />
        <!-- site css -->
        <link rel="stylesheet" href="style.css" />
        <!-- responsive css -->
        <link rel="stylesheet" href="css/responsive.css" />
        <!-- color css -->
        <link rel="stylesheet" href="css/colors.css" />

        <!-- select bootstrap -->
        <link rel="stylesheet" href="css/bootstrap-select.css" />
        <!-- scrollbar css -->
        <link rel="stylesheet" href="css/perfect-scrollbar.css" />
        <!-- custom css -->
        <link rel="stylesheet" href="css/custom.css" />
        <!--[if lt IE 9]>-->
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
        <style>
            .modal-lg-custom {
                max-width: 90%;
            }
            .modal-content-custom {
                width: 100%;
            }
        </style>
        <%
            HttpSession sess = request.getSession();
        //
            AccountLoginDAO ald = new AccountLoginDAO();
            UserDAO userDao = new UserDAO();
            GoogleLoginDAO gld = new GoogleLoginDAO();
            OrderDAO o = new OrderDAO();
        
            User user = null;
            AccountLogin account = null;
            GoogleLogin gglogin = null;
            if(sess.getAttribute("account") != null){
                account = (AccountLogin) sess.getAttribute("account");
                user = (User) userDao.getUserById(account.getUser().getID());
            }else if(sess.getAttribute("gguser") != null){
                gglogin = (GoogleLogin) sess.getAttribute("gguser");
                user = (User) userDao.getUserById(gglogin.getUser().getID());
            }else{
                user = null;
                account = null;
            }
             
            String balance = null;
            if(user != null){
                DecimalFormat df = new DecimalFormat("#,###");
                df.setMaximumFractionDigits(0);
                balance = df.format(user.getBalance());
            }

            List<Transaction> depositHistory = (List<Transaction>) request.getAttribute("list");
        %>

    </head>
    <body class="dashboard dashboard_1">
        <div class="full_container">
            <div class="inner_container">
                <!-- Sidebar  -->
                <nav id="sidebar">
                    <div class="sidebar_blog_1">
                        <div class="sidebar-header">
                            <div class="logo_section">
                                <a href="home.jsp"><img class="logo_icon img-responsive" src="images/logo/logo_icon.png" alt="#" /></a>
                            </div>
                        </div>
                        <div class="sidebar_user_info">
                            <div class="icon_setting"></div>
                            <div class="user_profle_side">
                                <% if(gglogin == null && account == null) { %>
                                <div class="user_img"><img class="img-responsive" src="images/logo/logo_icon.png" alt="#" /></div>
                                <div class="user_info">
                                    <h6>The Card Shop</h6>
                                    <p><span class="online_animation"></span></p>
                                </div>
                                <% } else if(user.getFirstName() == null && user.getLastName() != null) { %>
                                <div class="user_img"><img class="img-responsive" src="images/logo/logo_icon.png" alt="#" /></div>
                                <div class="user_info">
                                    <h6><%=user.getLastName()%></h6>
                                    <p><span class="online_animation"></span></p>
                                </div>
                                <% } else if(user.getLastName() == null && user.getFirstName() != null) { %>
                                <div class="user_img"><img class="img-responsive" src="images/layout_img/userfeedback.jpg" alt="#" /></div>
                                <div class="user_info">
                                    <h6><%=user.getFirstName()%></h6>
                                    <p><span class="online_animation"></span></p>
                                </div>
                                <% } else if(user.getLastName() != null && user.getFirstName() != null) { %>
                                <div class="user_img"><img class="img-responsive" src="images/layout_img/userfeedback.jpg" alt="#" /></div>
                                <div class="user_info">
                                    <h6><%=user.getLastName()%> <%=user.getFirstName()%></h6>
                                    <p><span class="online_animation"></span></p>
                                </div>
                                <% } else { %>
                                <div class="user_img"><img class="img-responsive" src="images/layout_img/userfeedback.jpg" alt="#" /></div>
                                <div class="user_info">
                                    <h6>Xin chào!</h6>
                                    <p><span class="online_animation"></span></p>
                                </div>
                                <% } %>
                            </div>
                        </div>
                    </div>
                    <div class="sidebar_blog_2">
                        <h4>Lịch sử giao dịch</h4>
                        <ul class="list-unstyled components">
                            <li><a href="home"><i class="fa fa-home yellow_color"></i> <span>Trang chủ</span></a></li>
                            <li class="active"> 
                                <a href="#dashboard" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle"><i class="fa fa-shopping-cart orange_color"></i> <span>Mua hàng</span></a>
                                <ul class="collapse list-unstyled" id="dashboard">
                                    <li><a href="shop"><i class="fa fa-arrow-circle-right"></i> <span>Sản phẩm</span></a></li>
                                    <li><a href="cart"><i class="fa fa-arrow-circle-right"></i> <span>Giỏ hàng</span></a></li>
                                    <!--<li><a href="checkout"><i class="fa fa-arrow-circle-right"></i> <span>Thanh toán</span></a></li>-->
                                    <li><a href="order"><i class="fa fa-arrow-circle-right"></i> <span>Đơn hàng</span></a></li>
                                </ul>
                            </li>
                            <li class="active">
                                <a href="#additional_page" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle"><i class="fa fa-credit-card green_color"></i> <span>Quản lí thanh toán</span></a>
                                <ul class="collapse list-unstyled" id="additional_page">
                                    <%if(account != null || gglogin != null){%>
                                    <li>
                                        <a href="depositmoney"><i class="fa fa-arrow-circle-right"></i> <span>Nạp tiền</span></a>
                                    </li>
                                    <li>
                                        <a href="displayDeposit"> <i class="fa fa-arrow-circle-right"></i><span>Lịch sử giao dịch</span></a>
                                    </li>
                                    <%} else{%>
                                    <li>
                                        <a href=""> <i class="fa fa-arrow-circle-right"></i><span>Vui lòng đăng nhập!</span></a>
                                    </li>
                                    <%}%>
                                </ul>
                            </li>
                            
                        </ul>
                    </div>
                </nav>
                <!-- end sidebar -->
                <!-- right content -->
                <div id="content">
                    <!-- topbar -->
                    <div class="topbar">
                        <nav class="navbar navbar-expand-lg navbar-light">
                            <div class="full">
                                <button type="button" id="sidebarCollapse" class="sidebar_toggle"><i class="fa fa-bars"></i></button>
                                <div class="logo_section">
                                    <% if(account == null && gglogin == null) { %>
                                    <% } else { %>
                                    <!--<a href="home.jsp"><img class="img-responsive" src="images/logo/logo.png" alt="Logo" /></a>-->
                                    <% } %>
                                </div>
                                <div class="right_topbar">
                                    <div class="icon_info">
                                        <ul>
                                            <% if(account == null && gglogin == null) { %>
                                            <% } else { %>
                                            <br>
                                            <i class="fa fa-credit-card"></i>
                                            <strong>Số dư: </strong><%=balance%> VNĐ
                                            <span class="badge"></span>
                                            <% } %>
                                        </ul>
                                        <ul class="user_profile_dd">
                                            <li>
                                                <% if(account == null && gglogin == null) { %>
                                                <a class="dropdown-toggle" data-toggle="dropdown"><img class="img-responsive rounded-circle" src="images/layout_img/userfeedback.jpg" alt="#" /><span class="name_user"> Tài khoản </span></a>
                                                <div class="dropdown-menu">
                                                    <a class="dropdown-item" href="login.jsp">Đăng nhập</a>
                                                    <a class="dropdown-item" href="register.jsp">Đăng kí</a>
                                                </div>
                                                <% } else { %>
                                                <a class="dropdown-toggle" data-toggle="dropdown"><img class="img-responsive rounded-circle" src="images/layout_img/userfeedback.jpg" alt="#" /><span class="name_user">Tài khoản</span></a>
                                                <div class="dropdown-menu">
                                                    <a class="dropdown-item" href="userprofile.jsp">Thông tin cá nhân</a>
                                                    <a class="dropdown-item" href="help.jsp">Hỗ trợ</a>
                                                    <a class="dropdown-item" href="logoutservlet"><span>Đăng xuất</span> <i class<="fa fa-sign-out"></i></a>
                                                </div>
                                                <% } %>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </nav>
                    </div>
                    <!-- end topbar -->
                    <!-- dashboard inner -->
                    <div class="midde_cont">
                        <div class="container-fluid">
                            <div class="row column_title">
                                <div class="col-md-12">
                                    <div class="page_title" style="padding: 10px;">
                                        <marquee behavior="scroll" direction="left">
                                            <h6 style="color: #FF5722;">Website thực hành SWP391 của Nhóm 2 | SE1801 | FPT <a href="https://www.facebook.com/Anhphanhne" target="_blank" style="letter-spacing: 2px;"> | <i class="fa fa-facebook-square"></i></a>
                                                Bài tập đang trong quá trình hoàn thành và phát triển!</h6>
                                        </marquee>
                                    </div>
                                </div>
                            </div>
                            <!-- graph -->

                            <div class="row">
                                <div class="col-md-12">
                                    <div class="white_shd full margin_bottom_30">
                                        <div class="full padding_infor_info">
                                            <h4>Lịch sử giao dịch</h4>
                                            <table class="table table-striped" style="margin-top: 25px;">
                                                <thead>
                                                    <tr>
                                                        <th scope="col">ID</th>
                                                        <th scope="col">Số tiền giao dịch</th>
                                                        <th scope="col">Loại giao dịch</th>
                                                        <th scope="col">Mã thanh toán</th>
                                                        <th scope="col">Mã ngân hàng</th>
                                                        <th scope="col">Trạng thái</th>
                                                        <th scope="col">Ngày tạo</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <% for (Transaction transaction : depositHistory) { %>
                                                    <tr>
                                                        <td><%= transaction.getId() %></td>
                                                        <td><%= transaction.getAmount() %></td>

                                                        <% if ("Payment".equals(transaction.getType())) { %>
                                                        <td>Thanh toán</td>
                                                        <% } else if ("Deposit".equals(transaction.getType())) { %>
                                                        <td>Nạp tiền</td>
                                                        <% } %>

                                                        <% if (transaction.getPaymentCode() == null) { %>
                                                        <td>Trống</td>
                                                        <% } else { %>
                                                        <td><%= transaction.getPaymentCode() %></td>
                                                        <% } %>

                                                        <% if (transaction.getBankCode() == null) { %>
                                                        <td>Trống</td>
                                                        <% } else { %>
                                                        <td><%= transaction.getBankCode() %></td>
                                                        <% } %>

                                                        <% if ("Success".equals(transaction.getStatus())) { %>
                                                        <td>Thành công</td>
                                                        <% } else if ("Failed".equals(transaction.getStatus())) { %>
                                                        <td>Thất bại</td>
                                                        <% } %>

                                                        <td><%= transaction.getCreatedAt() %></td>
                                                    </tr>
                                                    <% } %>

                                                </tbody>
                                            </table>
                                            <h5 style="color: red; align-content: center;">${requestScope.thongbao}</h5>    
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- end graph -->
                        </div>

                        <!-- footer -->
                        <div class="container-fluid">
                            <div class="footer">
                                <p>Copyright © Bài tập thực hành nhóm của sinh viên đại học FPT Hà Nội<br><br>
                                    TEAM LEADER <a href=""></a> <i class="fa fa-envelope-o"></i> : DungPAHE173131@fpt.edu.vn
                                </p>
                            </div>
                        </div>
                    </div>
                    <!-- end dashboard inner -->
                </div>
            </div>
        </div>

        <!-- Phần còn lại của nội dung -->
    </div> <!-- Kết thúc thẻ content -->

    <!-- Modal for Order Details -->
    <div class="modal fade" id="orderDetailsModal" tabindex="-1" role="dialog" aria-labelledby="orderDetailsModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg modal-lg-custom" role="document" > <!-- Added modal-lg class -->
            <div class="modal-content" >
                <div class="modal-header">
                    <h5 class="modal-title" id="orderDetailsModalLabel">Chi tiết đơn hàng</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body" >
                    <table  class="table table-striped">
                        <thead>
                            <tr>
                                <th>Stt</th>
                                <th>Tên thẻ</th>
                                <th>Mã seri</th>
                                <th>Mã thẻ</th>
                                <th>Giá tiền</th>
                                <th>Khuyến mãi</th>
                                <th>Thành tiền</th>
                                <th>Báo cáo</th>
                            </tr>
                        </thead>
                        <tbody id="orderDetailsContent">

                        </tbody>
                    </table><!-- Order details will be loaded here dynamically -->
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" id="chooseCheckout" tabindex="-1" role="dialog" aria-labelledby="orderDetailsModalLabel" aria-hidden="true" style="margin-top: 8%;">
        <div class="modal-dialog modal-lg" role="document" > <!-- Added modal-lg class -->
            <div class="modal-content" >
                <div class="modal-header">
                    <h5 class="modal-title">Lựa chọn thanh toán: </h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>

                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-md-6 text-center">
                            <!--<a href="checkoutbybalance">-->
                            <button class="btn btn-info" onclick="checkOutByBalance()"><i class="fa fa-money"></i> Thanh toán bằng số dư</button> 
                            <!--</a>-->
                        </div>
                        <div class="col-md-6 text-center">
                            <a href="checkoutbybanking">
                                <button class="btn btn-info"><i class="fa fa-credit-card"></i> Thanh toán bằng ngân hàng <i class="fa fa-arrow-right"></i> </button>
                            </a>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-1"></div>
                    <div id="thongbaoCheckout" class="col-md-8">

                    </div>  
                    <div class="col-md-3"></div>
                </div>
                <div class="modal-footer">
                    <div class="row w-100">
                        <div class="col-md-10"></div>
                        <div class="col-md-2 text-right">
                            <button class="btn btn-light" data-dismiss="modal">Đóng</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- Modal -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
    <!-- jQuery -->
    <!--<script src="js/jquery.min.js"></script>-->
    <script src="js/popper.min.js"></script>
    <!--<script src="js/bootstrap.min.js"></script>-->
    <!-- wow animation -->
    <script src="js/animate.js"></script>
    <!-- select country -->
    <script src="js/bootstrap-select.js"></script>
    <!-- owl carousel -->
    <script src="js/owl.carousel.js"></script> 
    <!-- chart js -->
    <script src="js/Chart.min.js"></script>
    <script src="js/Chart.bundle.min.js"></script>
    <script src="js/utils.js"></script>
    <script src="js/analyser.js"></script>
    <!-- nice scrollbar -->
    <script src="js/perfect-scrollbar.min.js"></script>
    <script>
                                var ps = new PerfectScrollbar('#sidebar');
    </script>
    <!-- custom js -->
    <script src="js/chart_custom_style1.js"></script>
    <script src="js/custom.js"></script>
    <script>
                                function showOrderDetails(orderId) {
                                    console.log('Order ID:', orderId); // Debug orderId
                                    //alert("Hello" + orderId);
                                    //alert("Hello " + orderId);
                                    //window.location.href = "/TheCardWebsite/displayorderdetails?id=" + orderId;
//                                                                    $('#orderDetailsModal').modal('show'); // Hiển thị modal chi tiết đơn hàng
                                    //var hieu = document.getElementById("orderDetailsContent");
                                    //hieu.innerHTML = "Hieu an cut";
                                    $.ajax({
                                        url: '/TheCardWebsite/orderdetails', // URL để gửi yêu cầu
                                        type: 'GET', // Phương thức HTTP
                                        data: {
                                            orderdetailsID: orderId
                                        }, // Gửi orderId làm dữ liệu đến server
                                        success: function (data) {
                                            $('#orderDetailsContent').html(data);
                                            $('#orderDetailsModal').modal('show');
                                        },
                                        error: function (xhr, status, error) {
                                            console.error('Lỗi khi lấy chi tiết đơn hàng:', error); // Xử lý lỗi
                                            $('#orderDetailsContent').html('Không thể tải chi tiết đơn hàng lúc này. Vui lòng thử lại sau.'); // Cập nhật nội dung modal với thông báo lỗi
                                        }
                                    });
                                }
                                function loadMore(orderId) {
                                    // Get the product ID from the data attribute
                                    $('#orderDetailsModal').modal('show');

                                    $.ajax({
                                        url: "/TheCardWebsite/displayorderdetails",
                                        type: "GET",
                                        data: {
                                            orderdetails: orderId
                                        },
                                        success: function (data) {
                                            var row = document.getElementById("orderDetailsModal");
                                            row.innerHTML = data;
                                            //$("#orderDetailsModal").html(data);
                                        },
                                        error: function (xhr) {

                                        }
                                    });
                                }
                                function showChooseCheckout() {
                                    $('#chooseCheckout').modal('show');
                                    $.ajax({
                                        url: '/TheCardWebsite/addtoorders', // URL để gửi yêu cầu
                                        type: 'POST', // Phương thức HTTP
                                        data: {
                                            list: JSON.stringify({list: cartItemIDs})
                                        }
                                        ,
                                        success: function (data) {
                                            $('#orderDetailsContent').html(data); // Update the modal content with the received data
                                            $('#chooseCheckout').modal('show');
                                        }
                                        ,
                                        error: function (xhr, status, error) {
                                            console.error('Lỗi khi lấy chi tiết đơn hàng:', error); // Xử lý lỗi
                                            $('#orderDetailsContent').html('Không thể tải chi tiết đơn hàng lúc này. Vui lòng thử lại sau.'); // Cập nhật nội dung modal với thông báo lỗi
                                        }
                                    }
                                    );
                                }
    </script>
</body>
</html>

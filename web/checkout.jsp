<%-- 
    Document   : shop
    Created on : Jun 4, 2024, 11:41:35 AM
    Author     : Bravo 15
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@page import = "java.text.DecimalFormat" %>
<%@page import = "Model.*" %>
<%@page import = "DAL.*" %>
<%@page import = "java.util.*" %>   
<%@page session="true" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <!-- basic -->
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <!-- mobile metas -->
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="viewport" content="initial-scale=1, maximum-scale=1">
        <!-- site metas -->
        <title>The Card Shop - Thanh toán</title>
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

        <!--<![endif]-->
        <%
            HttpSession sess = request.getSession();
            
            AccountLoginDAO ald = new AccountLoginDAO();
            UserDAO userDao = new UserDAO();
            GoogleLoginDAO gld = new GoogleLoginDAO();
            
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
            
            CartDAO cartDao = new CartDAO();
            String balance = null;
            if(user != null){
                DecimalFormat df = new DecimalFormat("#,###");
                df.setMaximumFractionDigits(0);
                balance = df.format(user.getBalance());
            }
            List<CartItem> dataCartItem = (List<CartItem>) request.getAttribute("list");
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
                                <%if(gglogin == null && account == null){%>
                                <div class="user_img"><img class="img-responsive" src="images/logo/logo_icon.png" alt="#" /></div>
                                <div class="user_info">
                                    <h6>The Card Shop</h6>
                                    <p><span class="online_animation"></span></p>
                                </div>
                                <%}else if(user.getFirstName() == null && user.getLastName() != null ){%>
                                <div class="user_img"><img class="img-responsive" src="images/logo/logo_icon.png" alt="#" /></div>
                                <div class="user_info">
                                    <h6><%=user.getLastName()%></h6>
                                    <p><span class="online_animation"></span></p>
                                </div>
                                <%}else if(user.getLastName() == null  && user.getFirstName() != null){%>
                                <div class="user_img"><img class="img-responsive" src="images/layout_img/userfeedback.jpg" alt="#" /></div>
                                <div class="user_info">
                                    <h6><%=user.getFirstName()%></h6>
                                    <p><span class="online_animation"></span></p>
                                </div>
                                <%}else if(user.getLastName() != null && user.getFirstName() != null){%>
                                <div class="user_img"><img class="img-responsive" src="images/layout_img/userfeedback.jpg" alt="#" /></div>
                                <div class="user_info">
                                    <h6><%=user.getLastName()%> <%=user.getFirstName()%></h6>
                                    <p><span class="online_animation"></span></p>
                                </div>
                                <%}else{%>
                                <div class="user_img"><img class="img-responsive" src="images/layout_img/userfeedback.jpg" alt="#" /></div>
                                <div class="user_info">
                                    <h6>Xin chào!</h6>
                                    <p><span class="online_animation"></span></p>
                                </div>
                                <%}%>

                            </div>
                        </div>
                    </div>
                    <div class="sidebar_blog_2">
                        <h4>Thanh toán</h4>
                        <ul class="list-unstyled components">
                            <li><a href="home"><i class="fa fa-home yellow_color"></i> <span>Trang chủ</span></a></li>
                            <li class="active"> 
                                <a href="#dashboard" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle"><i class="fa fa-shopping-cart orange_color"></i> <span>Mua hàng</span></a>
                                <ul class="collapse list-unstyled" id="dashboard">
                                    <li>
                                        <a href="shop"><i class="fa fa-arrow-circle-right"></i> <span>Sản phẩm</span></a>
                                    </li>
                                    <%if(account != null || gglogin != null){%>
                                    <li>
                                        <a href="cart"><i class="fa fa-arrow-circle-right"></i> <span>Giỏ hàng</span></a>
                                    </li>
                                    <li>
                                        <a href="order"><i class="fa fa-arrow-circle-right"></i> <span>Đơn hàng</span></a>
                                    </li>
                                    <%}%>
                                </ul>
                            </li>
                            <li class="active">
                                <a href="#additional_page" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle"><i class="fa fa-credit-card green_color"></i> <span>Quản lí thanh toán</span></a>
                                <ul class="collapse list-unstyled" id="additional_page">
                                    <%if(account != null || gglogin != null){%>
                                    <li>
                                        <a href="profile.html"><i class="fa fa-arrow-circle-right"></i> <span>Nạp tiền</span></a>
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

                                    <%if(account == null && gglogin == null){%>
                                    <%} else {%>
                                    <!--<a href="home.jsp"><img class="img-responsive" src="images/logo/logo.png" alt="Logo" /></a>-->
                                    <%}%>

                                </div>
                                <div class="right_topbar">
                                    <div class="icon_info">
                                        <ul>
                                            <%if(account == null && gglogin == null){%>
                                            <%}else{%>
                                            </br>
                                            <i class="fa fa-credit-card"></i>
                                            <strong>Số dư: </strong><%=balance%> VNĐ
                                            <span class="badge"></span>
                                            <%}%>
                                        </ul>
                                        <ul class="user_profile_dd">
                                            <li style="padding-left: 30px;">
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
                                                    <% if(user.getRole().getID() == 1) { %>
                                                    <a class="dropdown-item" href="displaybrand">Tới trang admin</a>
                                                    <%}%>
                                                    <a class="dropdown-item" href="logoutservlet">Đăng xuất <i class="fa fa-sign-out"></i></a> 
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

                            <!--Show loại thẻ-->
                            <div class="row column1">
                                <div class="container-fluid">

                                    <!-- row -->
                                    <div class="row">
                                        <!-- invoice section -->
                                        <div class="col-md-12">
                                            <div class="white_shd full margin_bottom_30">
                                                <div class="full graph_head">
                                                    <div class="heading1 margin_0">
                                                        <h2><i class="fa fa-file-text-o"></i> Xác nhận thanh toán</h2>
                                                    </div>
                                                </div>
                                                <div class="full">
                                                    <div class="invoice_inner">
                                                        <div class="row">
                                                            <div class="col-md-6">
                                                                <div class="full invoice_blog padding_infor_info padding-bottom_0">
                                                                    <h4>Nhà cung cấp</h4>
                                                                    <p><strong>The Card Shop</strong><br>     
                                                                        <strong>Điện thoại : </strong><a>0867085558</a><br>  
                                                                        <strong>Email : </strong><a>thecardshop8386@gmail.com</a>
                                                                    </p>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-6">
                                                                <div class="full invoice_blog padding_infor_info padding-bottom_0">
                                                                    <h4>Khách hàng</h4>
                                                                    <p><strong>Mã khách hàng:</strong><a> #${id} </a><br>  
                                                                        <strong>Số điện thoại: </strong><a>${phone}</a><br>  
                                                                        <strong>Email : </strong><a>${email}</a>
                                                                    </p>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="full padding_infor_info">
                                                    <div class="table_row">
                                                        <div class="table-responsive">
                                                            <table class="table table-striped">
                                                                <thead>
                                                                    <tr>
                                                                        <th>Stt</th>
                                                                        <th>Tên sản phẩm</th>
                                                                        <th>Mệnh giá</th>
                                                                        <th>Số lượng</th>
                                                                        <th>Khuyến mãi</th>
                                                                        <th>Thành tiền</th>
                                                                    </tr>
                                                                </thead>
                                                                <tbody>
                                                                    <% for(int i=0; i < dataCartItem.size(); i++) { %>
                                                                    <tr>
                                                                        <td><%=i+1%></td>
                                                                        <td><%=dataCartItem.get(i).getProductCategories().getName()%></td>
                                                                        <td><%=cartDao.formatPrice(dataCartItem.get(i).getProductCategories().getPrice())%> VND</td>
                                                                        <td><%=dataCartItem.get(i).getQuantity()%></td>
                                                                        <td><%=cartDao.formatPrice(dataCartItem.get(i).getProductCategories().getDiscount())%> %</td>
                                                                        <td><%=cartDao.formatPrice(dataCartItem.get(i).getProductCategories().getPrice() * dataCartItem.get(i).getQuantity()- dataCartItem.get(i).getProductCategories().getPrice() * dataCartItem.get(i).getQuantity() * (dataCartItem.get(i).getProductCategories().getDiscount()/100) )%> VND</td>
                                                                    </tr>
                                                                    <%}%>
                                                                </tbody>
                                                            </table>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <!-- row -->
                                    <div class="row">
                                        <div class="col-md-6">
                                            <div class="full white_shd">
                                                <div class="full graph_head">
                                                    <div class="heading1 margin_0">
                                                        <h2>Tổng tiền thanh toán</h2>
                                                    </div>
                                                </div>
                                                <div class="full padding_infor_info">
                                                    <div class="price_table">
                                                        <div class="table-responsive">
                                                            <table class="table">
                                                                <tbody>
                                                                    <tr>
                                                                        <th style="width:30%; font-weight: bold">Tổng tiền:</th>
                                                                         <td>
                                                                            <c:choose>
                                                                                <c:when test="${totalprice != 0}">
                                                                                <fmt:formatNumber pattern="#,###" value="${totalprice}" />
                                                                            </c:when>
                                                                                <c:otherwise>
                                                                                   ${totalprice}
                                                                                </c:otherwise>
                                                                            </c:choose>
                                                                            VNĐ
                                                                        </td>
                                                                    </tr>
                                                                    <tr>
                                                                        <th style="font-weight: bold;">Số lượng: </th>
                                                                        <td>${totalamomunt}</td>
                                                                    </tr>
                                                                    <tr>
                                                                        <th style="font-weight: bold;">Thành tiền:</th>
                                                                        <td>
                                                                            <c:choose>
                                                                                <c:when test="${totalprice != 0}">
                                                                                <fmt:formatNumber pattern="#,###" value="${totalprice}" />
                                                                            </c:when>
                                                                                <c:otherwise>
                                                                                   ${totalprice}
                                                                                </c:otherwise>
                                                                            </c:choose>
                                                                            VNĐ
                                                                        </td>
                                                                        <!--<td>${totalprice} VND</td>-->
                                                                    </tr>
                                                                </tbody>
                                                            </table>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-6">
                                            <div class="full white_shd">
                                                <div class="full graph_head">
                                                    <div class="heading1 margin_0">
                                                        <h2>Phương thức thanh toán</h2>
                                                    </div>
                                                </div>
                                                <div class="full padding_infor_info">
                                                    <ul class="payment_option" style="align-content: center;">
                                                        <a href="#"><li><img src="images/layout_img/visa.png" alt="#" /></li></a>
                                                        <a href="#"><li><img src="images/layout_img/mastercard.png" alt="#" /></li></a>
                                                        <a href="#"><li><img src="images/layout_img/american-express.png" alt="#" /></li></a>
                                                        <a href="#"><li><img src="images/layout_img/paypal.png" alt="#" /></li></a>
                                                    </ul>
                                                    <div class="field margin_0" >
                                                        <label class="label_field hidden">hidden label</label>
                                                        <input type="text" value="${requestScope.orderId}" hidden id="orderId">
                                                        <button type="submit" onclick="showChooseCheckout()" style="margin-top: 30px;margin-right: 100px" class="main_bt"><a style="color: white">Xác nhận thanh toán</a></button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
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


    <div class="modal fade" id="chooseCheckout" tabindex="-1" role="dialog" aria-labelledby="orderDetailsModalLabel" aria-hidden="true" style="margin-top: 8%;">
        <div class="modal-dialog modal-lg modal-lg-custom" role="document" > <!-- Added modal-lg class -->
            <div class="modal-content" >
                <div class="modal-header">
                    <h5 class="modal-title" id="orderDetailsModalLabel">Lựa chọn thanh toán: </h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>

                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-md-6 text-center">
                            <button class="btn btn-info" onclick="checkOutByBalance()"><i class="fa fa-money"></i> Thanh toán bằng số dư</button> 
                            <!--</a>-->
                        </div>
                        <div class="col-md-6 text-center">
                            <form id="checkoutForm" action="checkoutbanking" method="post">
                                <input type="hidden" id="orderId1" name="orderId" value="">
                                <button class="btn btn-info" onclick="checkOutByBanking()"><i class="fa fa-credit-card"></i> Thanh toán bằng ngân hàng <i class="fa fa-arrow-right"></i> </button>
                            </form>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div id="thongbaoCheckout" class="col-md-8">

                    </div>  
                    <div id="loading" class="col-md-8" style="display:none; margin-left: 50px;">
                        <h6>Đang xử lí đơn hàng <i class="fa fa-spinner"></i></h6>
                    </div>
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
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@10/dist/sweetalert2.all.min.js"></script>
    <!--<script src="js/jquery.min.js"></script>-->
    <script src="js/popper.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
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

                                    function showChooseCheckout() { //hàm này gọi AddToOrders, addOrder mới vào database
                                        $('#chooseCheckout').modal('show');
                                        $.ajax({
                                            url: '/TheCardWebsite/addtoorders', // URL để gửi yêu cầu
                                            type: 'GET', // Phương thức HTTP
                                            data: {
                                                timestamp: new Date().getTime()
                                            }
                                            ,
                                            success: function (data) {
                                                $('#thongbaoCheckout').html(data); // Update the modal content with the received data
                                                $('#chooseCheckout').modal('show');
                                            }
                                            ,
                                            error: function (xhr, status, error) {
                                                console.error('Lỗi khi lấy chi tiết đơn hàng:', error); // Xử lý lỗi
                                                $('#orderDetailsContent').html('Không thể thanh toán đơn hàng lúc này1. Vui lòng thử lại sau.'); // Cập nhật nội dung modal với thông báo lỗi
                                            }
                                        }
                                        );
                                    }

//                                    function checkOutByBalance() {//hàm này gọi checkoutByBalance
//                                        var orderId = $('#orderId').val();
//                                        //alert(orderId);
//                                        $.ajax({
//                                            url: '/TheCardWebsite/checkoutbybalance',
//                                            type: 'POST',
//                                            data: {
//                                                orderId: orderId
//                                            },
//                                            beforeSend: function () {
//                                                $('#loading').show();
//                                            },
//                                            success: function (data) {
//                                                //console.log("Response received:", data);
//                                                //$('#thongbaoCheckout').html(data);
//                                                $('#chooseCheckout').modal('hide');
//                                                Swal.fire({
//                                                    title: 'Kết quả xử lý!',
//                                                    html: data,
//                                                    icon: 'info',
//                                                    confirmButtonText: 'Đồng ý'
//                                                });
//                                            },
//                                            error: function (xhr, status, error) {
//                                                console.error('Lỗi khi lấy chi tiết đơn hàng:', error);
//                                                $('#thongbaoCheckout').html('Không thể thanh toán đơn hàng lúc này. Vui lòng thử lại sau.');
//                                            },
//                                            complete: function () {
//                                                $('#loading').hide();
//                                            }
//                                        });
//                                    }

                                    function checkOutByBalance() {
                                        var orderId = $('#orderId').val();

                                        $.ajax({
                                            url: '/TheCardWebsite/checkoutbybalance',
                                            type: 'POST',
                                            data: {
                                                orderId: orderId
                                            },
                                            beforeSend: function () {
                                                $('#loading').show();
                                            },
                                            success: function (data) {
                                                $('#chooseCheckout').modal('hide');
                                                Swal.fire({
                                                    title: 'Kết quả xử lý!',
                                                    html: data,
                                                    icon: 'info',
                                                    confirmButtonText: 'Đồng ý'
                                                }).then((result) => {
                                                    if (result.isConfirmed) {
                                                        window.location.reload(); // Load lại trang khi người dùng ấn Đồng ý
                                                    }
                                                });
                                            },
                                            error: function (xhr, status, error) {
                                                console.error('Lỗi khi lấy chi tiết đơn hàng:', error);
                                                $('#thongbaoCheckout').html('Không thể tải chi tiết đơn hàng lúc này. Vui lòng thử lại sau.');
                                            },
                                            complete: function () {
                                                $('#loading').hide();
                                            }
                                        });
                                    }

                                    function checkOutByBanking() {//hàm này gọi checkoutByBanking

                                        var orderId = $('#orderId').val();
                                        //$('#orderId1').val(orderId);
                                        //$('#checkoutForm').submit();
                                        $.ajax({
                                            url: '/TheCardWebsite/checkoutbybalance',
                                            type: 'POST',
                                            data: {
                                                orderId: orderId
                                            },
                                            beforeSend: function () {
                                                $('#loading').show();
                                            },
                                            success: function (data) {
                                                $('#chooseCheckout').modal('hide');
                                                Swal.fire({
                                                    title: 'Kết quả xử lý!',
                                                    html: data,
                                                    icon: 'info',
                                                    confirmButtonText: 'Đồng ý'
                                                }).then((result) => {
                                                    if (result.isConfirmed) {
                                                        window.location.reload(); // Load lại trang khi người dùng ấn Đồng ý
                                                    }
                                                });
                                            },
                                            error: function (xhr, status, error) {
                                                console.error('Lỗi khi lấy chi tiết đơn hàng:', error);
                                                $('#thongbaoCheckout').html('Không thể tải chi tiết đơn hàng lúc này. Vui lòng thử lại sau.');
                                            },
                                            complete: function () {
                                                $('#loading').hide();
                                            }
                                        });
                                    }
    </script>

    <!-- custom js -->
    <!--<script src="js/chart_custom_style1.js"></script>-->
    <!--<script src="js/custom.js"></script>-->
</body>
</html>



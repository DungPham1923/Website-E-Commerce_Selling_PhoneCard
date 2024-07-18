<%-- 
    Document   : home
    Created on : May 24, 2024, 8:26:11 AM
    Author     : Bravo 15
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
        <title>The card shop - Chi tiết sản phẩm</title>
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
        <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
        <![endif]-->
        <%
            HttpSession sess = request.getSession();
            
            AccountLoginDAO ald = new AccountLoginDAO();
            UserDAO userDao = new UserDAO();
            GoogleLoginDAO gld = new GoogleLoginDAO();
            
            User user = null;
            AccountLogin account = null;
            GoogleLogin gglogin = null;
            //account = (AccountLogin) sess.getAttribute("account");
            //gglogin = (GoogleLogin) sess.getAttribute("gguser");
            //user = (User) userDao.getUserById(account.getUser().getID());
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
            List<Comment> dataComment = (List<Comment>) request.getAttribute("comments");
        %>

        <style>
            .image-container img {
                width: 90%; /* Đảm bảo ảnh chiếm toàn bộ chiều rộng của container */
                height: auto; /* Duy trì tỷ lệ ảnh */
            }

            .price_table_bottom {
                margin-top: 20px;
            }

            .btn-square {
                padding: 10px 20px;
                text-align: center;
                border: 1px solid #17a2b8;
                color: #17a2b8;
                text-decoration: none;
                transition: background-color 0.3s, color 0.3s;
            }

            .btn-square:hover {
                background-color: #17a2b8;
                color: #fff;
            }

            .container-fluid {
                padding-right: 15px;
                padding-left: 15px;
                margin-right: auto;
                margin-left: auto;
            }

            .buttom-left,
            .buttom-right {
                width: 30px;
                height: 30px;
                display: flex;
                align-items: center;
                justify-content: center;
                background-color: rgba(255, 87, 34, 0.5);
                border-radius: 40%;
                color: #fff;
                font-size: 24px;
                transition: background-color 0.3s ease;
                margin: 0px 10px;
            }

            .buttom-left:hover,
            .buttom-right:hover {
                background-color: rgba(255, 87, 34, 0.8);
            }

            .full.graph_head {
                display: flex;
                align-items: center;
                justify-content: space-between;
            }

            .product-container {
                transition: border-color 0.3s ease, box-shadow 0.3s ease;
            }

            .product-container:hover {
                border-color: #00bfff;
                box-shadow: 0px 0px 15px rgba(0, 150, 200, 0.5);
            }
            .full {
                width: 100%;
                box-sizing: border-box;
            }

            .full.graph_head {
                padding: 10px;
                border-bottom: 1px solid gainsboro;
            }

            .full.graph_revenue {
                padding: 10px;
                border: 1px solid gainsboro;
                overflow-x: hidden;
                overflow-y: auto;
            }


            .form-group {
                margin-bottom: 15px;
            }

            .form-control {
                width: 100%;
                max-width: 100%;
                padding: 10px;
                box-sizing: border-box;
            }

            input[type="submit"] {
                max-width: 100%;
                padding: 10px;
                background-color: rgba(255, 87, 34, 1);
                box-shadow: 0px 0px 10px orange;
                color: white;
                border: none;
                cursor: pointer;
                border-radius: 15px;
            }

            input[type="submit"]:hover {
                background-color: orange;
            }

            .form-group {
                display: flex;
                justify-content: flex-end;
                margin-bottom: 15px;
            }


            .modal {
                display: none;
                position: fixed;
                z-index: 1;
                left: 0;
                top: 0;
                width: 100%;
                height: 100%;
                overflow: auto;
                background-color: rgb(0,0,0);
                background-color: rgba(0,0,0,0.4);
                padding-top: 60px;
            }

            .modal-content {
                background-color: #fefefe;
                margin: 5% auto;
                padding: 20px;
                border: 1px solid #888;
                width: 80%;
                max-width: 500px;
            }

            .modal-footer {
                display: flex;
                justify-content: space-between;
            }
            .popBut {
                border-color: #00bfff;
                border-radius: 15px;
                background: cornflowerblue;
                color: white;
                padding: 15px 20px;
                cursor: pointer;
            }
            .popBut:hover{
                background-color: #00bfff;
            }

            .header {
                display: flex;
                align-items: center;
            }
            .header .dot {
                margin: 0 5px;
            }

            .loadButton{
                padding: 5px;
                margin: 10px 0px 0px 10px;
                background-color: rgba(255, 87, 34, 1);
                box-shadow: 0px 0px 10px orange;
                color: white;
                border: none;
                cursor: pointer;
                border-radius: 10px;
            }
            .loadButton:hover {
                background-color: orange;
            }
        </style>

        <script>
            function getQuantity() {
                var quantity = document.getElementById('quantity').value;
                return quantity;
            }

            function checkQuantity() {
                const quantityInput = document.getElementById('quantity');
                const maxQuantity = ${productCate.quantity};
                const quantityWarning = document.getElementById('quantity-warning');

                if (quantityInput.value > maxQuantity) {
                    quantityWarning.style.display = 'block';
                    quantityInput.value = maxQuantity;
                } else {
                    quantityWarning.style.display = 'none';
                }
            }

            function handleAddToCart() {
                var quantity = getQuantity();
                var productId = '${productCate.id}'; // Lấy id sản phẩm từ biến productCate
                var url = 'addtocart?id=' + productId + '&quantity=' + quantity + '&action=add';
                window.location.href = url;
            }

            function handleBuyNow() {
                var quantity = getQuantity();
                var productId = '${productCate.id}'; // Lấy id sản phẩm từ biến productCate
                var url = 'addtocart?id=' + productId + '&quantity=' + quantity + '&action=buy&source=productdetail';
                window.location.href = url;
            }

            function checkSessionAndSubmit(event) {
                var accountExists = document.getElementById("accountExists").value;
                if (accountExists !== "true") {
                    event.preventDefault(); // Ngăn form không được submit
                    openModal();
                }
            }

            function openModal() {
                document.getElementById("loginModal").style.display = "block";
            }

            function closeModal() {
                document.getElementById("loginModal").style.display = "none";
            }

            function redirectToLogin() {
                window.location.href = "/TheCardWebsite/LoginController?pid=${productCate.id}";
            }

            function redirectToRegister() {
                window.location.href = "register.jsp";
            }

            // Close the modal when the user clicks outside of it
            window.onclick = function (event) {
                var modal = document.getElementById("loginModal");
                if (event.target == modal) {
                    closeModal();
                }
            }

            document.addEventListener('DOMContentLoaded', function () {
                // Lấy tất cả các liên kết có lớp 'testimonial-link'
                var links = document.querySelectorAll('.testimonial-link');
                // Lấy tất cả các ảnh có lớp 'testimonial-img'
                var images = document.querySelectorAll('.testimonial-img');

                // Lắng nghe sự kiện nhấp chuột trên mỗi liên kết
                links.forEach(function (link) {
                    link.addEventListener('click', function (event) {
                        // Ngăn chặn hành vi mặc định của liên kết
                        event.preventDefault();

                        // Lấy ID của sản phẩm từ thuộc tính data-id
                        var productId = link.getAttribute('data-id');

                        // Chuyển hướng đến trang productcart với ID sản phẩm
                        window.location.href = 'productdetail?id=' + productId;
                    });
                });

                // Lắng nghe sự kiện nhấp chuột trên mỗi ảnh
                images.forEach(function (image) {
                    image.addEventListener('click', function (event) {
                        // Lấy ID của sản phẩm từ thuộc tính data-id
                        var productId = image.getAttribute('data-id');

                        // Chuyển hướng đến trang productcart với ID sản phẩm
                        window.location.href = 'productdetail?id=' + productId;
                    });
                });
            });
        </script>


    </head>
    <body class="dashboard dashboard_1">
        <div class="full_container">
            <div class="inner_container">
                <!-- Sidebar  -->
                <nav id="sidebar">
                    <div class="sidebar_blog_1">
                        <div class="sidebar-header">
                            <div class="logo_section">
                                <a href="index.html"><img class="logo_icon img-responsive" src="images/logo/logo_icon.png" alt="#" /></a>
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
                        <h4>Chi tiết sản phẩm</h4>
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
                                <div class="col-md-12">
                                    <div class="white_shd full margin_bottom_30">
                                        <div class="full graph_head">
                                            <div class="heading1 margin_0">
                                                <h2>Thông tin thẻ</h2>
                                            </div>
                                        </div>
                                        <div class="full price_table padding_infor_info">
                                            <c:choose>
                                                <c:when test="${not empty productCate}">
                                                    <div class="container-fluid mt-4">
                                                        <div class="row">
                                                            <div class="col-md-6">
                                                                <div class="image-container">
                                                                    <img src="images/logo/${productCate.image}" class="img-fluid" alt="Product Image"/>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-6">
                                                                <div class="info-container">
                                                                    <h3 style="margin-bottom: 10px;">${productCate.name}</h3>
                                                                    <p style="margin-bottom: 10px; font-size: 16px;">Mã sản phẩm: ${productCate.id}</p>
                                                                    <p style="margin-bottom: 10px; font-size: 16px;">Nhà mạng: ${productCate.brand.name}</p>
                                                                    <p style="margin-bottom: 10px; font-size: 16px;">Số lượng hiện có: ${productCate.quantity}</p>
                                                                    <p style="margin-bottom: 10px; font-size: 16px;">Mô tả sản phẩm: ${productCate.description}</p>
                                                                    <p style="margin-bottom: 10px; font-size: 16px; font-weight: bold;">${productCate.price-(productCate.price*productCate.discount)}đ</p>
                                                                    <div style="display: flex;">
                                                                        <div style="padding: 0px;" class="col-md-2">
                                                                            <p style="text-decoration: line-through; margin-bottom: 0px;">${productCate.price}đ</p>
                                                                        </div>
                                                                        <div class="col-md-2">
                                                                            <p style="background-color: rgb(255, 87, 34); color: white; border-radius: 5px; text-align: center;">${productCate.discount*100}%</p>
                                                                        </div>
                                                                    </div>
                                                                    <div style="padding-top: 10px; margin-top: 0px" class="price_table_bottom mt-3">
                                                                        <div class="quantity-container mb-3">
                                                                            <div class="d-flex align-items-center">
                                                                                <label style="font-size: 16px;" for="quantity" class="mr-2">Số lượng:</label>
                                                                                <input type="number" id="quantity" name="quantity" class="form-control" style="width: 70px; font-size: 16px;" min="1" max="${productCate.quantity}" value="1" oninput="checkQuantity()">
                                                                            </div>
                                                                            <p id="quantity-message" class="text-danger mt-2">${requestScope.error}</p>
                                                                            <p id="quantity-success" class="text-success mt-2">${param.success}</p> 
                                                                            <p id="quantity-warning" class="text-danger mt-2" style="display:none;">Số lượng bạn chọn đạt mức tối đa của sản phẩm này</p>
                                                                        </div>
                                                                        <div class="button-container d-flex">
                                                                            <a style="font-size: 16px;" class="btn btn-outline-info btn-square mr-1" id="add-to-cart" href="#" onclick="handleAddToCart()"><i class="fa fa-shopping-cart"></i> Thêm vào giỏ</a>
                                                                            <a style="font-size: 16px;" class="btn btn-outline-info btn-square ml-1" id="buy-now" href="#" onclick="handleBuyNow()"> Mua ngay</a>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </c:when>
                                                <c:otherwise>
                                                    <p>Product not found.</p>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!-- end graph -->
                            <div class="row column3">
                                <!-- testimonial -->
                                <div class="col-md-12">
                                    <div  style="background-color:  white;" class="dark_bg full margin_bottom_30">
                                        <div class="full graph_head">
                                            <div class="heading1 margin_0">
                                                <h2 style="color: black;">Sản phẩm liên quan</h2>
                                            </div>
                                            <div style="padding-right: 20px; display: flex">
                                                <a class="buttom-left" href="#testimonial_slider" data-slide="prev">
                                                    <i class="fa fa-angle-left"></i>
                                                </a>
                                                <a class="buttom-right" href="#testimonial_slider" data-slide="next">
                                                    <i class="fa fa-angle-right"></i>
                                                </a>
                                            </div>
                                        </div>
                                        <div  style="border-top-width: 1px; border-top-style: solid; border-top-color: gainsboro;" class="full graph_revenue">
                                            <div class="row">
                                                <div class="col-md-12">
                                                    <div class="content testimonial">
                                                        <div id="testimonial_slider" class="carousel slide" data-ride="carousel">
                                                            <!-- Wrapper for carousel items -->
                                                            <div class="carousel-inner">
                                                                <c:forEach var="prelate" items="${productRelate}" varStatus="status">
                                                                    <c:if test="${status.index % 4 == 0}">
                                                                        <div class="item carousel-item ${status.index == 0 ? 'active' : ''}">
                                                                            <div class="row">
                                                                            </c:if>
                                                                            <div class="col-md-3">
                                                                                <div class="product-container" style="border: 1px solid #DCDDDE; border-radius: 5px; padding: 10px; height: 100%;">
                                                                                    <div>
                                                                                        <img class="testimonial-img" src="images/logo/${prelate.image}" alt="" data-id="${prelate.id}" style="width: 100%; height: auto; border-radius: 5px; cursor: pointer;">
                                                                                    </div>
                                                                                    <a style="color: black; text-align: center; margin-top: 10px; cursor: pointer" class="testimonial-link" data-id="${prelate.id}" href="productcart?id=${prelate.id}">${prelate.name}</a>
                                                                                </div>
                                                                            </div>

                                                                            <c:if test="${status.index % 4 == 3 || status.index == productRelate.size() - 1}">
                                                                            </div>
                                                                        </div>
                                                                    </c:if>
                                                                </c:forEach>
                                                            </div>
                                                        </div>

                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!<!-- Binh luan -->
                            <div class="row column3">
                                <!-- testimonial -->
                                <div class="col-md-12">
                                    <div  style="background-color:  white;" class="dark_bg full margin_bottom_30">
                                        <div class="full graph_head">
                                            <div class="heading1 margin_0">
                                                <h2 style="color: black;">Bình luận</h2>
                                            </div>
                                        </div>
                                        <div  style="border-top-width: 1px; border-top-style: solid; border-top-color: gainsboro;" class="full graph_revenue">
                                            <form action="comment" method="POST" onsubmit="checkSessionAndSubmit(event)">
                                                <div class="form-group">
                                                    <textarea name="comment" id="message" cols="30" rows="5" class="form-control" placeholder="Nhập nội dung bình luận" required=""></textarea>
                                                </div>
                                                <input type="hidden" value="${productCate.id}" name="pid"/>
                                                <input type="hidden" id="accountExists" value="<%= session.getAttribute("account") != null %>" />
                                                <div class="form-group">
                                                    <input type="submit" value="Gửi đánh giá">
                                                </div>
                                            </form>
                                            <div id="commentSection" data-product-id="${productCate.id}">
                                                <div id="content1">
                                                    <% for (Comment o : dataComment) { %>
                                                    <div class="comment-item product" style="margin-top: 10px; border-top: 1px solid gainsboro; padding-bottom: 10px; border-radius: 5px;">
                                                        <div class="header">
                                                            <p class="user-name" style="font-weight: bold; color: green; padding: 10px 0px 0px 10px; margin-bottom: 5px;"><%= o.getUser().getLastName() %> <%= o.getUser().getFirstName() %></p>
                                                            <p class="dot" style="margin: 0 5px; color: gray;">.</p>
                                                            <p class="created-at" style="color: gray; margin: 0px; padding: 10px 0px 0px 0px"><%= o.getCreatedAt() %></p>
                                                        </div>
                                                        <p style="padding-left: 30px; margin: 0px; color: black;"><%= o.getMessage() %></p>
                                                    </div>
                                                    <% } %>
                                                </div>
                                                <button id="loadMoreButton" onclick="loadMore()" class="loadButton">Hiển thị thêm</button>
                                            </div>

                                            <div id="loginModal" class="modal">
                                                <div class="modal-content">
                                                    <p style="color: green; font-size: 20px">Bạn cần đăng nhập để gửi đánh giá.</p>
                                                    <div class="modal-footer">
                                                        <button class="popBut col-md-6" onclick="redirectToRegister()">Đăng ký</button>
                                                        <button class="popBut col-md-6" onclick="redirectToLogin()">Đăng nhập</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
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
                <!-- jQuery -->
                <script src="js/jquery.min.js"></script>
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
                </script>

                <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
                <script>
                                                            function loadMore() {
                                                                // Get the product ID from the data attribute
                                                                var productId = document.getElementById("commentSection").getAttribute("data-product-id");
                                                                var amount = document.getElementsByClassName("product").length;

                                                                $.ajax({
                                                                    url: "/TheCardWebsite/load",
                                                                    type: "GET",
                                                                    data: {
                                                                        productId: productId,
                                                                        amount: amount
                                                                    },
                                                                    success: function (data) {
                                                                        var row = document.getElementById("content1");
                                                                        row.innerHTML += data;
                                                                    },
                                                                    error: function (xhr) {
                                                                        
                                                                    }
                                                                });
                                                            }
                </script>

                <!-- custom js -->
                <script src="js/chart_custom_style1.js"></script>
                <script src="js/custom.js"></script>
                </body>
                </html>


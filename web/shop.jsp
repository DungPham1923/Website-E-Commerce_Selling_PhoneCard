<%-- 
    Document   : shop
    Created on : Jun 4, 2024, 11:41:35 AM
    Author     : Bravo 15
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <title>The Card Shop - Cửa hàng</title>
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
        <style>
            /* Style for the button */
            .btn.sr-btn {
                position: relative;
                overflow: hidden;
            }

            /* Add an effect when the button is clicked */
            .btn.sr-btn:active {
                transform: scale(0.95);
            }

            /* Animation for the button click */
            .btn.sr-btn::after {
                content: "";
                position: absolute;
                width: 100%;
                height: 100%;
                top: 0;
                left: 0;
                background: rgba(255, 255, 255, 0.5);
                opacity: 0;
                transition: opacity 0.4s;
            }

            .btn.sr-btn:active::after {
                opacity: 1;
                transition: none;
            }
        </style>
        <script>
            function filterbyall() {
                // Lấy giá trị của thẻ input
                var inputValue = document.getElementById('inputField').value;

                // Tạo một form tạm thời để gửi dữ liệu
                var form = document.createElement('form');
                form.method = 'POST';
                form.action = 'abc'; // URL của servlet

                // Tạo một input ẩn và gán giá trị từ inputField
                var input = document.createElement('input');
                input.type = 'hidden';
                input.name = 'inputField'; // Tên của tham số mà servlet sẽ nhận
                input.value = inputValue;

                // Thêm input vào form
                form.appendChild(input);

                // Thêm form vào body và nộp form
                document.body.appendChild(form);
                form.submit();
            }
        </script> 
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
            String balance = null;
            if(user != null){
                DecimalFormat df = new DecimalFormat("#,###");
                df.setMaximumFractionDigits(0);
                balance = df.format(user.getBalance());
            }
            


            //List categories / Product
            BrandDAO brandDAO= new BrandDAO();
            List<Brand> dataBrand = brandDAO.getListBrand();
            ProductCategoriesDAO productDAO = new ProductCategoriesDAO();
            List<Double> dataPrice = productDAO.getDistinctPrices();
            List<ProductCategories> dataProduct = null;
            if(request.getAttribute("dataProduct") != null){
                dataProduct = (List<ProductCategories>) request.getAttribute("dataProduct");
            }else{
                dataProduct = productDAO.getListProduct();
            }   

            //int pageAmount = (int) productDAO.getTotalProductCategories();
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
                        <h4>Cửa hàng</h4>
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
                                            <h3>Tìm kiếm sản phẩm</h3>
                                            <div class="inbox-head" style="margin: 0px 0px; padding: 20px; color: black;"> 
                                                <div class="mail-option" style="margin: 0px 0px; padding-bottom: 0px;" >
                                                    <div class="chk-all">
                                                        <div class="btn-group">
                                                            <c:choose>
                                                                <c:when test="${not empty requestScope.brandName}">
                                                                    <a data-toggle="dropdown" href="#" class="btn mini all" aria-expanded="false"> ${requestScope.brandName} <i class="fa fa-angle-down "></i></a>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                    <a data-toggle="dropdown" href="#" class="btn mini all" aria-expanded="false"> Nhà mạng <i class="fa fa-angle-down "></i></a>
                                                                    </c:otherwise>
                                                                </c:choose>

                                                            <ul id="idBrand" class="dropdown-menu" x-placement="bottom-start" style="position: absolute; transform: translate3d(0px, 21px, 0px); top: 0px; left: 0px; will-change: transform; color: black">
                                                                <li><a href="shop?idBrand=0&idPage=${param.idPage}&size=${param.size}&price=${param.price}&name=${param.name}&sort=${param.sort}"> Tất cả</a></li>
                                                                    <%for (Brand x : dataBrand) {%>
                                                                <li><a href="shop?idBrand=<%= x.getId()%>&idPage=${param.idPage}&size=${param.size}&price=${param.price}&name=${param.name}&sort=${param.sort}" > <%=x.getName()%> </a></li>
                                                                    <%}%>
                                                            </ul>
                                                        </div>
                                                    </div>
                                                    <div class="btn-group hidden-phone">
                                                        <c:choose>
                                                            <c:when test="${not empty requestScope.price}">
                                                                <a data-toggle="dropdown" href="#" class="btn mini blue" aria-expanded="false">
                                                                    ${requestScope.price} 
                                                                    <i class="fa fa-angle-down "></i>
                                                                </a>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <a data-toggle="dropdown" href="#" class="btn mini blue" aria-expanded="false">
                                                                    Mệnh giá
                                                                    <i class="fa fa-angle-down "></i>
                                                                </a>
                                                            </c:otherwise>
                                                        </c:choose>

                                                        <ul id="price" class="dropdown-menu" x-placement="bottom-start" style="position: absolute; transform: translate3d(0px, 32px, 0px); top: 0px; left: 0px; will-change: transform;">
                                                            <li><a href="shop?price=0&idBrand=${param.idBrand}&idPage=${param.idPage}&size=${param.size}&name=${param.name}&sort=${param.sort}"><i class="fa fa-dollar"></i> Tất cả</a></li>
                                                                <%for (Double x : dataPrice) {%>
                                                            <li><a href="shop?price=<%=x%>&idBrand=${param.idBrand}&idPage=${param.idPage}&size=${param.size}&name=${param.name}&sort=${param.sort}"><i class="fa fa-dollar"></i>  <%=productDAO.formatPrice(x)%> VNĐ</a></li>
                                                                <%}%> 
                                                        </ul>
                                                    </div>
                                                    <div class="btn-group">
                                                        <c:choose>
                                                            <c:when test="${not empty requestScope.sort}">
                                                                <a data-toggle="dropdown" href="#" class="btn mini blue" aria-expanded="false">
                                                                    ${requestScope.sort}
                                                                    <i class="fa fa-angle-down "></i>
                                                                </a>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <a data-toggle="dropdown" href="#" class="btn mini blue" aria-expanded="false">
                                                                    Sắp xếp
                                                                    <i class="fa fa-angle-down "></i>
                                                                </a>
                                                            </c:otherwise>
                                                        </c:choose>
                                                        <ul class="dropdown-menu" x-placement="bottom-start" style="position: absolute; transform: translate3d(0px, 32px, 0px); top: 0px; left: 0px; will-change: transform;">
                                                            <li><a href="shop?sort=desc&idBrand=${param.idBrand}&idPage=${param.idPage}&size=${param.size}&name=${param.name}"><i class="fa fa-arrow-circle-down"></i> Giảm dần</a></li>
                                                            <li><a href="shop?sort=asc&idBrand=${param.idBrand}&idPage=${param.idPage}&size=${param.size}&name=${param.name}"><i class="fa fa-arrow-circle-up"></i> Tăng dần</a></li>
                                                        </ul>
                                                    </div>
                                                    <!--onsubmit="loadpage(); return false;"-->
                                                    <form  action="shop" method="GET" class="pull-right position search_inbox">
                                                        <div class="input-append">
                                                            <input id="idBrand" type="text" value="${param.idBrand}" name="idBrand" hidden/>
                                                            <input type="text" value="${param.size}" name="size" hidden/>
                                                            <input type="text" value="${param.price}" name="price" hidden/>
                                                            <input type="text" value="${param.idPage}" name="index" hidden/>
                                                            <input id="name" type="text" class="sr-input" name="name" placeholder="Tìm kiếm sản phẩm" value="${requestScope.namesearch}"/>
                                                            <button class="btn sr-btn" type="submit"><i class="fa fa-search"></i></button>
                                                        </div>
                                                    </form>
                                                </div>    
                                            </div>
                                            <div style="align-content: center; margin-top: 20px">
                                                <h6 style="color: orangered; font-family:sans-serif; text-align: center"> ${requestScope.error}</h6>   
                                                <h6 style="color: #28a745; font-family:sans-serif; text-align: center"> ${requestScope.thongbao}</h6>
                                                <h6 style="color: green; font-family:sans-serif; text-align: center"> ${param.success}</h6>
                                            </div>

                                            <div class="full price_table padding_infor_info">
                                                <div class="row">
                                                    <!-- column price -->

                                                    <%for (ProductCategories x : dataProduct) {%>
                                                    <div class="col-lg-3 col-md-6 col-sm-6 col-xs-12">
                                                        <div class="table_price full hover-border" style="border-top: 2px solid #DCDDDE;">
                                                            <div class="inner_table_price" style="border: 1px solid #DCDDDE;">
                                                                <div class="price_table_head blue1_bg" >
                                                                    <a href="productdetail?id=<%=x.getId()%>"><img class="img-responsive rounded-0" src="images/logo/<%=x.getImage()%>" alt="#" /></a>
                                                                    <p style="font-size: 13px; color: white; text-align: center; margin-bottom: 0px;"><span class="price_no"></span>Mệnh giá: <%=productDAO.formatPrice(x.getPrice())%> VNĐ</p>
                                                                </div>


                                                                <div class="price_table_inner" style="border: 0px; background-color: #d5d7de">
                                                                    <div class="cont_table_price_blog" style="border: 0px; margin-bottom: 0px; ">
                                                                        <p style="font-size: 15px; text-align: center; text-height: bold; color: black; padding: 5px 0px; "><span class="price_no"></span>Giá: <%=productDAO.formatPrice(x.getPrice())%> VNĐ </p>
                                                                    </div>
                                                                </div>
                                                                <div class="price_table_bottom" style="height: 10px; padding: 0px;">
                                                                    <div class="d-flex justify-content-center align-items-center h-100" style="margin: 18px 5px;">
                                                                        <a style="margin: 10px 5px;" class="btn btn-outline-info btn-square" href="productdetail?id=<%=x.getId()%>"><i class="fa fa-search"></i></a>                                                                    
                                                                        <a style="margin: 10px 5px;" class="btn btn-outline-info btn-square" href="addtocart?id=<%=x.getId()%>&quantity=1&action=shop"><i class="fa fa-shopping-cart"></i></a>
                                                                        <a style="margin: 10px 5px;" class="btn btn-outline-info btn-square" href="addtocart?id=<%=x.getId()%>&quantity=1&action=buy&source=shop"><i class=""></i>Mua</a>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <%}%>
                                                    
                                                    <div class="full padding_infor_info padding_top_20" style="margin-top: 30px; align-content: center">
                                                        <div class="mail-option" >
                                                            <div class="chk-all">
                                                                <div class="btn-group">
                                                                    <a data-toggle="dropdown" href="#" class="btn mini all" aria-expanded="false"> Lựa chọn hiển thị <i class="fa fa-angle-down "></i></a>
                                                                    <ul class="dropdown-menu">
                                                                        <li><a href="shop?idBrand=${param.idBrand}&idPage=${param.idPage}&size=4&price=${param.price}&name=${param.name}&sort=${param.sort}" onclick="loadPage(event, 'shop?size=4&idPage=${param.idPage}')" >4 sản phẩm</a></li>
                                                                        <li><a href="shop?idBrand=${param.idBrand}&idPage=${param.idPage}&size=8&price=${param.price}&name=${param.name}&sort=${param.sort}" onclick="loadPage()"> 8 sản phẩm</a></li>
                                                                        <li><a href="shop?idBrand=${param.idBrand}&idPage=${param.idPage}&size=12&price=${param.price}&name=${param.name}&sort=${param.sort}" onclick="loadPage()"> 12 sản phẩm</a></li>
                                                                    </ul>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="pagination button_section button_style2" style="display: flex; justify-content: center; align-items: center;">
                                                            <div class="btn-group mr-2" role="group" aria-label="First group">
                                                                <c:choose>
                                                                    <c:when test="${requestScope.amountPage == 1 || requestScope.amountPage < 1}">
                                                                        <button type="button" class=" btn" style="border-bottom-left-radius: 0px; border-radius: 0px;">
                                                                            ${amountPage}
                                                                        </button>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <a class="np-btn" style="witdh: 45px; height: 40px" href="shop?idPage=${index>=1?(index-1):(1)}&size=${param.size}&idBrand=${param.idBrand}&name=${param.name}&price=${param.price}&sort=${param.sort}">
                                                                            <c:choose>
                                                                                <c:when test="${index == 1}">

                                                                                </c:when>
                                                                                <c:otherwise>
                                                                                    <button type="submit" class="btn" style="margin-right: 10px;"><i class="fa fa-angle-left pagination-left"></i> </button>
                                                                                </c:otherwise>
                                                                            </c:choose>
                                                                        </a>
                                                                        <c:forEach begin="1" end="${requestScope.amountPage}" var="i">
                                                                            <a href="shop?idPage=${i}&size=${param.size}&idBrand=${param.idBrand}&name=${param.name}&price=${param.price}&sort=${param.sort}" style="color: white">
                                                                                <c:choose>
                                                                                    <c:when test="${requestScope.index == i}">
                                                                                        <button type="button" class="active btn" style="border-bottom-left-radius: 0px; border-radius: 0px;">
                                                                                            ${i}
                                                                                        </button> 
                                                                                    </c:when>
                                                                                    <c:otherwise>
                                                                                        <button type="button" onclick="loadPage()" class="btn" style="border-bottom-left-radius: 0px; border-radius: 0px;">
                                                                                            ${i}
                                                                                        </button> 
                                                                                    </c:otherwise>
                                                                                </c:choose>
                                                                            </a>
                                                                        </c:forEach>

                                                                        <a class="np-btn" style="witdh: 45px; height: 40px" href="shop?idPage=${index < amountPage?(index + 1):(amountPage)}&size=${param.size}&idBrand=${param.idBrand}&name=${param.name}&price=${param.price}&sort=${param.sort}">
                                                                            <c:choose>
                                                                                <c:when test="${index == amountPage}">

                                                                                </c:when>
                                                                                <c:otherwise>
                                                                                    <button type="submit" class="btn" style="margin-left: 10px;"><i class="fa fa-angle-right pagination-right"></i> </button>
                                                                                </c:otherwise>
                                                                            </c:choose>
                                                                        </a>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </div>
                                                        </div>
                                                    </div>


                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- end graph -->
                            <!--Thu - Iter 3-->
                            <!--Notification Modal -->
                            <div class="modal fade" id="notificationModal" tabindex="-1" role="dialog" aria-labelledby="notificationModalLabel" aria-hidden="true">
                                <div class="modal-dialog" role="document">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title" id="notificationModalLabel">Thông báo</h5>
                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                <span aria-hidden="true">&times;</span>
                                            </button>
                                        </div>
                                        <div class="modal-body">
                                            <!-- Error message will be injected here -->
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-primary" data-dismiss="modal">Đóng</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- footer -->
                        <div class="container-fluid">
                            <div id="name12" class="footer"> 
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

        <!--Ajax--> 
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script>
                                                                                            function loadpage() {
                                                                                                var name = document.getElementById("name").value;
                                                                                                //alert(name);
                                                                                                $.ajax({
                                                                                                    url: "filterbybrand",
                                                                                                    type: "get", // Use GET method
                                                                                                    data: {
                                                                                                        name: name
                                                                                                    },
                                                                                                    success: function (response) {
                                                                                                        // document.getElementById("name").innerHTML = data.namesearch;
                                                                                                        document.getElementById("text").innerHTML += response;
                                                                                                        alert(response);
                                                                                                    },
                                                                                                    error: function (xhr) {
                                                                                                        // Handle errors during the AJAX request
                                                                                                        console.error("An error occurred while loading the page:", xhr);
                                                                                                        console.error("Có lỗi xảy ra khi tải trang:", xhr);
                                                                                                        console.error("Trạng thái lỗi:", status);
                                                                                                        console.error("Thông báo lỗi:", error);
                                                                                                        // Optional: Display an error message to the user
                                                                                                        alert("An error occurred while loading the page. Please try again.");
                                                                                                    }
                                                                                                });
                                                                                            }



                                                                                            function filterByBrand(brandid, idPage, price) {
                                                                                                event.preventDefault();
                                                                                                console.log("Brand ID:", brandid);
                                                                                                console.log("Page ID:", idPage);
                                                                                                console.log("Price:", price);
                                                                                                var url = "filterbybrand?brandid=" + brandid + "&price=" + price + "&idPage=" + idPage;
                                                                                                // Gọi hàm loadPage với các tham số tương ứng
                                                                                                //loadPage(event, 'filterbybrand', brandid, price, idPage);
                                                                                                window.location.href = url;
                                                                                            }
                                                                                            //                                                                                            function loadpage(price, name) {
                                                                                            //                                                                                                // Hàm xử lý logic bạn muốn thực hiện
                                                                                            //                                                                                                
                                                                                            //                                                                                                alert("Function activated!" + price + name);
                                                                                            //                                                                                                // Ví dụ: thực hiện hành động gì đó với các tham số này
                                                                                            //                                                                                            }
                                                                                            function myFunction() {
                                                                                                alert("Function activated!");
                                                                                            }

        </script>  
        <!-- custom js -->
        <script src="js/chart_custom_style1.js"></script>
        <script src="js/custom.js"></script>
    </body>
</html>



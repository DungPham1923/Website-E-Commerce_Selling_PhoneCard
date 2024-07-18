<%-- 
    Document   : home
    Created on : May 24, 2024, 8:26:11 AM
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
        <title>The Card Shop - Trang chủ</title>
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
            /*Slide*/
            #testimonial_slider_1.carousel {
                width: 86%;
                margin: 35px 7% 35px;
            }

            #testimonial_slider_1 .carousel-inner {
                padding: 0;
                text-align: center;
            }

            #testimonial_slider_1.carousel .carousel-item {
                color: #999;
                font-size: 14px;
                text-align: center;
                overflow: hidden;
                min-height: auto;
            }

            #testimonial_slider_1.carousel .carousel-item a {
                color: #eb7245;
            }

            #testimonial_slider_1.carousel .img-box {
                width: 120px;
                height: 120px;
                margin: 0 auto;
                border-radius: 50%;
            }

            #testimonial_slider_1.carousel .img-box img {
                width: 100%;
                height: 100%;
                display: block;
                border-radius: 50%;
            }

            #testimonial_slider_1.carousel .testimonial {
                padding: 30px 0 10px;
                color: rgba(255, 255, 255, .7);
                font-size: 15px;
                line-height: 24px;
            }

            #testimonial_slider_1.carousel .overview {
                text-align: center;
                padding-bottom: 5px;
                font-size: 14px;
                color: #1ed085;
                font-weight: 500;
                line-height: 14px;
            }

            #testimonial_slider_1.carousel .overview b {
                color: #fff;
                font-size: 16px;
                text-transform: none;
                display: block;
                padding-bottom: 5px;
                font-weight: 500;
            }

            #testimonial_slider_1.carousel .star-rating i {
                font-size: 18px;
                color: #ffdc12;
            }

            #testimonial_slider_1.carousel .carousel-control {
                width: 30px;
                height: 30px;
                border-radius: 50%;
                background: #fff;
                text-shadow: none;
                top: 0;
                opacity: 1;
            }

            #testimonial_slider_1.carousel .carousel-control i {
                font-size: 20px;
                margin-right: 2px;
                color: #15283c;
                margin-top: -2px;
            }

            #testimonial_slider_1.carousel .carousel-control-prev {
                left: auto;
                right: 40px;
            }

            #testimonial_slider_1.carousel .carousel-control-next i {
                margin-right: -2px;
                margin-top: -2px;
            }

            #testimonial_slider_1.carousel .carousel-indicators {
                bottom: 15px;
            }

            #testimonial_slider_1.carousel .carousel-indicators li,
            #testimonial_slider_1.carousel .carousel-indicators li.active {
                width: 11px;
                height: 11px;
                margin: 1px 5px;
                border-radius: 50%;
            }

            #testimonial_slider_1.carousel .carousel-indicators li {
                background: #e2e2e2;
                border-color: transparent;
            }

            #testimonial_slider_1.carousel .carousel-indicators li.active {
                border: none;
                background: #888;
            }
        </style>

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
            
            BrandDAO brandDao = new BrandDAO();
            List<Brand> dataBrand = brandDao.getListBrand();
            request.setAttribute("dataBrand", dataBrand);
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
                                    <!--<p><span class="online_animation"></span></p>-->
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
                        <h4>Trang chủ</h4>
                        <ul class="list-unstyled components">
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
                                    <div class="page_title" style="padding: 10px">
                                        <marquee behavior="scroll" direction="left">
                                            <h6 style="color: #FF5722;">Website thực hành SWP391 của Nhóm 2 | SE1801 | FPT <a href="https://www.facebook.com/Anhphanhne" target="_blank" style="letter-spacing: 2px;"> | <i class="fa fa-facebook-square"></i></a>
                                                 Bài tập đang trong quá trình hoàn thành và phát triển!</h6>
                                        </marquee>
                                    </div>
                                </div>
                            </div>

                            <!-- graph -->
                            <div class="row column3">
                                <!-- testimonial -->
                                <div class="col-md-12">
                                    <div  style="background-color:  white;" class="dark_bg full margin_bottom_30">
                                        <div class="full graph_head" style="display: flex; justify-content: space-between;">
                                            <div class="heading1 margin_0">
                                                <h2 style="color: black;">MUA MÃ THẺ</h2>
                                            </div>
                                            <div style="padding-right: 20px; display: flex;">
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
                                                                <c:forEach var="prelate" items="${dataBrand}" varStatus="status">
                                                                    <c:if test="${status.index % 4 == 0}">
                                                                        <div class="item carousel-item ${status.index == 0 ? 'active' : ''}">
                                                                            <div class="row">
                                                                            </c:if>
                                                                            <div class="col-md-3">
                                                                                <div class="product-container" style="border: 1px solid #DCDDDE; border-radius: 5px; padding: 10px; height: 100%;">
                                                                                    <div>
                                                                                        <img class="testimonial-img" src="images/logo/${prelate.image}" alt="" data-id="${prelate.id}" style="width: 100%; height: auto; border-radius: 5px; cursor: pointer;">
                                                                                    </div>
                                                                                    <a style="color: black; text-align: center; margin-top: 10px; cursor: pointer" class="testimonial-link" data-id="${prelate.id}" href="shop?idBrand=${prelate.id}&idPage=${param.idPage}&size=${param.size}&price=${param.price}&name=${param.name}">${prelate.name}</a>
                                                                                    <div class="center" style="margin-top: 5px;"><a class="main_bt" style="color: white;" href="shop?idBrand=${prelate.id}&idPage=${param.idPage}&size=${param.size}&price=${param.price}&name=${param.name}">Xem</a></div>

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
                            <!--Show loại thẻ-->

                            <!-- end graph -->
                            <div class="row column3">
                                <!-- testimonial -->
                                <div class="col-md-6">
                                    <div class="dark_bg full margin_bottom_30">
                                        <div class="full graph_head">
                                            <div class="heading1 margin_0">
                                                <h2>Phản hồi</h2>
                                            </div>
                                        </div>
                                        <div class="full graph_revenue">
                                            <div class="row">
                                                <div class="col-md-12">
                                                    <div class="content testimonial">
                                                        <div id="testimonial_slider_1" class="carousel slide" data-ride="carousel">
                                                            <!-- Wrapper for carousel items -->
                                                            <div class="carousel-inner">
                                                                <div class="item carousel-item active">
                                                                    <div class="img-box"><img src="images/layout_img/userfeedback.jpg" alt="ImageAnonymous"></div>
                                                                    <p class="testimonial">Tôi đã mua thẻ ở trang web này nhiều lần, thật sự tin tưởng về chất lượng và dịch vụ của trang...</p>
                                                                    <p class="overview"><b>Phạm Anh Dũng</b>Thân thiết</p>
                                                                </div>
                                                                <div class="item carousel-item">
                                                                    <div class="img-box"><img src="images/layout_img/userfeedback.jpg" alt="ImageAnonymous"></div>
                                                                    <p class="testimonial">Các bạn quản trị viên hỗ trợ và chăm sóc khách hàng tốt, sản phẩm tốt, tôi đã mua nhiều lần...</p>
                                                                    <p class="overview"><b>Hoàng Tiến Đạt</b>Khách quen</p>
                                                                </div>
                                                                <div class="item carousel-item">
                                                                    <div class="img-box"><img src="images/layout_img/userfeedback.jpg" alt="ImageAnonymous"></div>
                                                                    <p class="testimonial">Một người hay liên lạc công việc như tôi thường xuyên phải đi mua thẻ điện thoại, giờ có trang web này tôi có thể mua online thật tuyệt vời...</p>
                                                                    <p class="overview"><b>Đỗ Huy Sơn</b>Mua nhiều</p>
                                                                </div>
                                                            </div>
                                                            <!-- Carousel controls -->
                                                            <a class="carousel-control left carousel-control-prev" href="#testimonial_slider_1" data-slide="prev">
                                                                <i class="fa fa-angle-left"></i>
                                                            </a>
                                                            <a class="carousel-control right carousel-control-next" href="#testimonial_slider_1" data-slide="next">
                                                                <i class="fa fa-angle-right"></i>
                                                            </a>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <!-- end testimonial -->
                                <!-- progress bar -->
                                <div class="col-md-6">
                                    <div class="white_shd full margin_bottom_30">
                                        <div class="full graph_head">
                                            <div class="heading1 margin_0">
                                                <h2>Tỉ lệ mua hàng</h2>
                                            </div>
                                        </div>
                                        <div class="full progress_bar_inner">
                                            <div class="row">
                                                <div class="col-md-12">
                                                    <div class="progress_bar">
                                                        <!-- Skill Bars -->
                                                        <span class="skill" style="width:98%;">Viettel <span class="info_valume">98%</span></span>                  
                                                        <div class="progress skill-bar ">
                                                            <div class="progress-bar progress-bar-animated progress-bar-striped" role="progressbar" aria-valuenow="98" aria-valuemin="0" aria-valuemax="100" style="width: 98%;">
                                                            </div>
                                                        </div>
                                                        <span class="skill" style="width:85%;">Mobiphone <span class="info_valume">85%</span></span>   
                                                        <div class="progress skill-bar">
                                                            <div class="progress-bar progress-bar-animated progress-bar-striped" role="progressbar" aria-valuenow="85" aria-valuemin="0" aria-valuemax="100" style="width: 85%;">
                                                            </div>
                                                        </div>
                                                        <span class="skill" style="width:70%;">Vinaphone <span class="info_valume">70%</span></span>
                                                        <div class="progress skill-bar">
                                                            <div class="progress-bar progress-bar-animated progress-bar-striped" role="progressbar" aria-valuenow="70" aria-valuemin="0" aria-valuemax="100" style="width: 70%;">
                                                            </div>
                                                        </div>
                                                        <span class="skill" style="width:82%;">Vietnamobi <span class="info_valume">82%</span></span>
                                                        <div class="progress skill-bar">
                                                            <div class="progress-bar progress-bar-animated progress-bar-striped" role="progressbar" aria-valuenow="82" aria-valuemin="0" aria-valuemax="100" style="width: 82%;">
                                                            </div>
                                                        </div>
                                                        <span class="skill" style="width:48%;">Other <span class="info_valume">48%</span></span>
                                                        <div class="progress skill-bar">
                                                            <div class="progress-bar progress-bar-animated progress-bar-striped" role="progressbar" aria-valuenow="48" aria-valuemin="0" aria-valuemax="100" style="width: 48%;">
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <!-- end progress bar -->
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
        <!-- custom js -->
        <script src="js/chart_custom_style1.js"></script>
        <script src="js/custom.js"></script>
    </body>
</html>


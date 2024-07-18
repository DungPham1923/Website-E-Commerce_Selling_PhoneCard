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
        <title>The Card Shop - Quản lý báo cáo</title>
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
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <style>
            .fa-spinner {
                color: #4CAF50; /* Màu xanh lá cây */
                font-size: 24px;  /* Kích thước */
            }
        </style>
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
            ProductCategoriesDAO pcateDao = new ProductCategoriesDAO();
            CartDAO cartDao = new CartDAO();
            OrderDAO  orderDAO = new OrderDAO();
            String balance = null;
            if(user != null){
                DecimalFormat df = new DecimalFormat("#,###");
                df.setMaximumFractionDigits(0);
                balance = df.format(user.getBalance());
            }
            List<Report> dataReport = (List<Report>) request.getAttribute("list");
            
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
                        <h4>Quản lí báo cáo</h4>
                        <ul class="list-unstyled components">
                            <li><a href="displaybrand"><i class="fa fa-edit yellow_color"></i> <span>Quản lý sản phẩm</span></a></li>
                            <li><a href="manageruseracc"><i class="fa fa-edit orange_color"></i> <span>Quản lý người dùng</span></a></li>
                            <li><a href="displayorderlist"><i class="fa fa-edit purple_color"></i> <span>Quản lý đơn hàng</span></a></li>
                            <li><a href="loadreport"><i class="fa fa-edit red_color"></i> <span>Quản lý báo cáo</span></a></li>
                            <li><a href="displayfeedback"><i class="fa fa-edit green_color"></i> <span>Quản lý phản hồi</span></a></li> 
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
                                                    <a class="dropdown-item" href="home">Tới trang bán hàng</a>
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
                                                        <h2> Phản hồi báo cáo sản phẩm</h2>
                                                        <!--                                                        <i class="fa fa-file-text-o"></i>-->
                                                    </div>
                                                </div>

                                                <div class="full padding_infor_info">
                                                    <div class="table_row">
                                                        <div class="table-responsive">
                                                            <table class="table table-striped">
                                                                <thead>
                                                                    <tr>
                                                                        <th style="text-align: center; padding: 14px 0px">Stt</th>
                                                                        <th style="text-align: center; padding: 14px 2px">OrderID</th>
                                                                        <th style="text-align: center; padding: 14px 0px">Sản Phẩm</th>
                                                                        <th style="text-align: center; padding: 14px 0px">Seri</th>
                                                                        <th style="text-align: center; padding: 14px 0px">Code</th>
                                                                        <th style="text-align: center; padding: 14px 0px">Trạng Thái</th>
                                                                        <th style="text-align: center; padding: 12px 0px">Thao Tác</th>
                                                                    </tr>
                                                                </thead>
                                                                <tbody>
                                                                    <% for(int i=0; i < dataReport.size(); i++) { %>
                                                                    <% if(dataReport.get(i).getStatus().equals("No repli & No compensation yet") ){ %>
                                                                    <tr>
                                                                        <td style="padding: 12px 20px"><%=i+1%></td>
                                                                        <td style="text-align: center; padding: 12px 10px"><%=orderDAO.getOrderDetailsByintId(dataReport.get(i).getOrderDetailId()).getID()%></td>
                                                                        <td style="text-align: center; padding: 12px 10px"><%=dataReport.get(i).getProductCategoriesName() %></td>
                                                                        <td style="text-align: center; padding: 12px 10px"><%=dataReport.get(i).getProductCardId().getSeri() %></td>
                                                                        <td style="text-align: center; padding: 12px 7px"><%=dataReport.get(i).getProductCardId().getCode() %></td>
                                                                        <td style="text-align: center;"><i class="fas fa-spinner fa-spin" style=" font-size: 24px; padding: 0px 0px;"></i></td>
                                                                        <td style="padding: 7px 10px">
                                                                            <a onclick="acceptReport(<%=dataReport.get(i).getOrderDetailId() %>)" class="btn btn-info" style="color: white;display: inline-block;" >Chấp thuận</a>
                                                                            <a onclick="refuseReport(<%=dataReport.get(i).getOrderDetailId() %>)" class="btn btn-danger" style="color: white;display: inline-block;" >Từ chối</a>
                                                                        </td>
                                                                    </tr>
                                                                    <% }else{ %>
                                                                    <tr>
                                                                        <td style="padding: 12px 20px"><%=i+1%></td>
                                                                        <td style="text-align: center; padding: 12px 10px"><%=orderDAO.getOrderDetailsByintId(dataReport.get(i).getOrderDetailId()).getID()%></td>
                                                                        <td style="text-align: center; padding: 12px 10px"><%=dataReport.get(i).getProductCategoriesName() %></td>
                                                                        <td style="text-align: center; padding: 12px 10px"><%=dataReport.get(i).getProductCardId().getSeri() %></td>
                                                                        <td style="text-align: center; padding: 12px 7px"><%=dataReport.get(i).getProductCardId().getCode() %></td>
                                                                        <% if(dataReport.get(i).getStatus().equals("Replied & Compensated") ){ %>
                                                                        <td style="text-align: center;"><i class="fas fa-check" style="color: green; font-size: 24px;"></i></td>
                                                                            <% }else{ %>
                                                                        <td style="text-align: center;"><i class="fas fa-times" style="color: red; font-size: 24px;"></i></td>
                                                                            <%}%>
                                                                        <td> </td>
                                                                    </tr>
                                                                    <%}%>
                                                                    <%}%>
                                                                </tbody>
                                                            </table>
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
                                function acceptReport(idorderDetail) {
                                    // Hiển thị pop-up SweetAlert với thẻ input
                                    Swal.fire({
                                        title: 'Phản hồi sản phẩm',
                                        text: 'Vui lòng nhập phản hồi:',
                                        input: 'text',
                                        inputPlaceholder: 'Phản hồi báo cáo',
                                        showCancelButton: true,
                                        confirmButtonText: 'Gửi phản hồi',
                                        cancelButtonText: 'Hủy',
                                        preConfirm: (reason) => {
                                            if (!reason) {
                                                Swal.showValidationMessage('Bạn cần nhập phản hồi');
                                            }
                                            return {reason: reason};
                                        }
                                    }).then((result) => {
                                        if (result.isConfirmed) {
                                            const reason = result.value.reason;
                                            // Chuyển hướng đến servlet với idorder và lý do báo cáo là các tham số
                                            const url = "acceptreport?id=" + idorderDetail + "&reason=" + encodeURIComponent(reason);
                                            window.location.href = url;
                                        }
                                    });
                                }

                                function refuseReport(idorderDetail) {
                                    // Hiển thị pop-up SweetAlert với thẻ input
                                    Swal.fire({
                                        title: 'Phản hồi sản phẩm',
                                        text: 'Vui lòng nhập phản hồi:',
                                        input: 'text',
                                        inputPlaceholder: 'Phản hồi báo cáo',
                                        showCancelButton: true,
                                        confirmButtonText: 'Gửi phản hồi',
                                        cancelButtonText: 'Hủy',
                                        preConfirm: (reason) => {
                                            if (!reason) {
                                                Swal.showValidationMessage('Bạn cần nhập phản hồi');
                                            }
                                            return {reason: reason};
                                        }
                                    }).then((result) => {
                                        if (result.isConfirmed) {
                                            const reason = result.value.reason;
                                            // Chuyển hướng đến servlet với idorder và lý do báo cáo là các tham số
                                            const url = "refusereport?id=" + idorderDetail + "&reason=" + encodeURIComponent(reason);
                                            window.location.href = url;
                                        }
                                    });
                                }
    </script>
    <!-- custom js -->
    <script src="js/chart_custom_style1.js"></script>
    <script src="js/custom.js"></script>
</body>
</html>



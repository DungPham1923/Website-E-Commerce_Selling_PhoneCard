<%-- 
    Document   : manageorder
    Created on : Jul 8, 2024, 11:32:34 PM
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
        <title>The Card Shop - Quản lý đơn hàng</title>
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
            //List<Order> data = o.getListOrderByUserID(account.getUser().getID());
            List<Order> data = (List<Order>) request.getAttribute("dataOrder");
            //List<OrderDetails> dataa = (List<OrderDetails>) request.getAttribute("list");
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
                        <h4>Quản lý đơn hàng</h4> 
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

                            <div class="row">
                                <div class="col-md-12">
                                    <div class="white_shd full margin_bottom_30">
                                        <div class="full padding_infor_info">
                                            <h4>Quản lí đơn hàng</h4>
                                            
                                            <div class="inbox-head" style="margin: 0px 0px; padding: 20px; color: black;"> 
                                                <form action="searchorder" method="GET" class="pull-right position search_inbox">
                                                    <div class="input-append">
                                                        <input type="text" class="sr-input" name="orderId" placeholder="Tìm kiếm đơn hàng" value="${requestScope.orderId}"/>
                                                        <button class="btn sr-btn" type="submit"><i class="fa fa-search"></i></button>
                                                    </div>
                                                </form>
                                            </div>
                                            
                                            <table class="table table-striped" style="margin-top: 25px;">
                                                <thead>
                                                    <tr>
                                                        <th>ID</th>
                                                        <th>Tổng tiền</th>
                                                        <th>Trạng thái</th>
                                                        <th>Ngày đặt hàng</th>
                                                        <th>Chi tiết</th>
                                                        <th>Khách hàng</th>
                                                        <!--<th style="align-content: center; align-items: center;">Xóa đơn hàng</th>-->
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <%if(data != null) {%>
                                                    <% for(int i=0;i < data.size(); i++) { %>
                                                    <tr>
                                                        <td><%= data.get(i).getId()%></td>
                                                        <td><%= data.get(i).getTotalAmount() %> VNĐ</td> 
                                                        <td><%= data.get(i).getStatus() %></td> 
                                                        <td><%= data.get(i).getCreatedAt() %></td> 
                                                        <td><button class="btn btn-info" onclick="showOrderDetails(<%= data.get(i).getId()%>)">Chi tiết đơn hàng</button></td>
                                                        <td><button style="width: 128.53px;" class="btn btn-info" onclick="showUserDetails(<%= data.get(i).getUserId()%>)">Khách hàng <i class="fa fa-user"></i></button></td>
                                                        <!--<td><button class="btn btn-danger"">Xóa đơn hàng <i class="fa fa-trash"></i></button> </td>-->
                                                    </tr>
                                                    <%}%>   
                                                    <%}else {%>
                                                    <%}%>
                                                </tbody>
                                            </table>
                                            <h5 style="color: red; align-content: center;">${requestScope.thongbao}</h5>

                                            <div class="full padding_infor_info padding_top_20" style="margin-top: 30px; align-content: center">
                                                <div class="pagination button_section button_style2" style="display: flex; justify-content: center; align-items: center;">
                                                    <div class="btn-group mr-2" role="group" aria-label="First group">
                                                        <c:choose>
                                                            <c:when test="${requestScope.amountPage == 1 || requestScope.amountPage < 1}">
                                                                <button type="button" class="btn" style="border-bottom-left-radius: 0px; border-radius: 0px;">
                                                                    ${amountPage}
                                                                </button>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <a class="np-btn" style="witdh: 45px; height: 40px" href="displayorderlist?idPage=${index>=1?(index-1):(1)}">
                                                                    <c:choose>
                                                                        <c:when test="${index == 1}">

                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            <button type="submit" class="btn" style="margin-right: 10px;"><i class="fa fa-angle-left pagination-left"></i> </button>
                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                </a>
                                                                <c:forEach begin="1" end="${requestScope.amountPage}" var="i">
                                                                    <a href="displayorderlist?idPage=${i}" style="color: white">
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
                                                                <a class="np-btn" style="witdh: 45px; height: 40px" href="displayorderlist?idPage=${index < amountPage?(index + 1):(amountPage)}">
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
                            </tr>
                        </thead>
                        <tbody id="orderDetailsContent">

                        </tbody>
                    </table><!-- Order details will be loaded here dynamically -->
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" id="moreDetail" tabindex="-1" role="dialog" aria-labelledby="moreDetailLabel" aria-hidden="true">
        <div style="max-width: 60%; width: 60%;" class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="moreDetailLabel">Thông tin người dùng</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body" id="moreDetailBody">
                    <div id="moreDetailContent"></div>
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
                                                                                    function showUserDetails(userID) {
                                                                                        //alert('hello');
                                                                                        $('#moreDetail').modal('show');
                                                                                        $.ajax({
                                                                                            url: '/TheCardWebsite/showuserdetail',
                                                                                            type: 'GET',
                                                                                            data: {userID: userID},
                                                                                            success: function (data) {
                                                                                                $('#moreDetailContent').html(data);
                                                                                                $('#moreDetail').modal('show');
                                                                                            },
                                                                                            error: function (xhr) {
                                                                                                console.error('Error fetching user details:', xhr);
                                                                                            }
                                                                                        });
                                                                                    }

                                                                                    function showOrderDetails(orderId) {
                                                                                        console.log('Order ID:', orderId); // Debug orderId
                                                                                        //alert("Hello" + orderId);

                                                                                        $.ajax({
                                                                                            url: '/TheCardWebsite/managerorder', // URL để gửi yêu cầu
                                                                                            type: 'GET', // Phương thức HTTP
                                                                                            data: {
                                                                                                orderId: orderId
                                                                                            },
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

    </script>
    <!-- custom js -->
    <script src="js/chart_custom_style1.js"></script>
    <script src="js/custom.js"></script>

</body>
</html>

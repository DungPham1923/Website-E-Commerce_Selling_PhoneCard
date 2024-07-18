
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import = "java.text.DecimalFormat" %>
<%@page import = "Model.*" %>
<%@page import = "DAL.*" %>
<%@page import = "java.util.*" %>   
<%@page session="true" %>
<%@ page import="Model.*" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <!-- mobile metas -->
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="viewport" content="initial-scale=1, maximum-scale=1">
        <!-- site metas -->
        <title>The Card Shop - Quản lý người dùng</title>
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
        <!-- Bao gồm CSS của Bootstrap -->
        <!--<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">-->

        <!-- Bao gồm JS của Bootstrap và jQuery -->
        <!--<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>-->
        <!--<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>-->

        <style>
            .modal-lg-custom {
                max-width: 90%;
            }
            .modal-content-custom {
                width: 100%;
            }
            .full.graph_head {
                background-color: #f8f9fa; /* Light background color */
                padding: 10px; /* Padding around the content */
                border-radius: 8px; /* Rounded corners */
                box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); /* Subtle shadow for depth */
                justify-content: space-between;
            }

            .header-container {
                display: flex; /* Flexbox for layout */
                align-items: center; /* Center items vertically */
                justify-content: space-between; /* Space between the h2 and form */
            }

            .heading1 {
                font-size: 24px; /* Larger font size for heading */
                font-weight: bold; /* Bold text */
                color: #343a40; /* Dark text color */
                margin: 0; /* Remove default margin */
            }

            .search-form {
                display: flex; /* Flexbox for layout */
                align-items: center; /* Center vertically */
            }

            .input-group {
                margin-left: 700px;
                display: flex; /* Flexbox for input and button */
                border: 1px solid #ced4da; /* Border around the input group */
                /*border-radius: 4px;  Rounded corners */
                overflow: hidden; /* Clip children if overflow */
                background-color: #ffffff; /* White background for input */
            }

            .sr-input {
                border: none; /* Remove default input border */
                padding: 10px 10px; /* Padding inside the input */
                font-size: 14px; /* Larger font size */
                flex-grow: 1; /* Take up remaining space */
            }


            .sr-btn {
                background-color: orangered; /* Primary blue button */
                color: white; /* White text color */
                border-radius: 0px; /* Remove default button border */
                padding: 8px 10px; /* Padding inside the button */
                cursor: pointer; /* Pointer cursor on hover */
            }

            .sr-btn i {
                font-size: 18px; /* Larger icon size */
            }

            .sr-btn:hover {
                background-color: orange ; /* Darker blue on hover */
            }

            .sr-input:focus {
                outline: none; /* Remove default focus outline */
                box-shadow: none; /* Remove default box shadow */
            }

            .delete-toggle {
                appearance: none;
                -webkit-appearance: none;
                width: 50px;
                height: 25px;
                background-color: #4CAF50;
                border-radius: 25px;
                position: relative;
                cursor: pointer;
                outline: none;
                transition: background-color 0.2s;
            }

            .delete-toggle:checked {
                background-color: #f44336;
            }

            .delete-toggle:before {
                content: '';
                position: absolute;
                width: 21px;
                height: 21px;
                background-color: #ffffff;
                border-radius: 50%;
                top: 2px;
                left: 27px;
                transition: transform 0.2s, left 0.2s;
            }

            .delete-toggle:checked:before {
                left: 2px;
            }
            .form-container {
                max-width: 100%;
                margin: auto auto;
                padding: 20px;
                border: 1px solid #ccc;
                border-radius: 10px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            }
            .password-wrapper {
                position: relative;
            }
            .password-wrapper input {
                padding-right: 40px;
            }
            .password-wrapper .toggle-password {
                position: absolute;
                margin-top: 15px;
                top: 50%;
                right: 10px;
                transform: translateY(-50%);
                cursor: pointer;
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
            List<Order> data = (List<Order>) request.getAttribute("data");
            List<OrderDetails> dataa = (List<OrderDetails>) request.getAttribute("list");
            List<AccountLogin> dataUser = (List<AccountLogin>) request.getAttribute("allAcc");
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
                        <h4>Sản phẩm</h4>
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
                                            <li>
                                                <%if(account == null && gglogin == null){%>
                                                <a class="dropdown-toggle" data-toggle="dropdown"><img class="img-responsive rounded-circle" src="images/layout_img/userfeedback.jpg" alt="#" /><span class="name_user"> Tài khoản  </span></a>
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
                                            <div class="heading1 margin_0 header-container">
                                                <h2>Quản lý người dùng</h2>
                                                <form action="managersearchuser" method="GET" class="search-form">
                                                    <div class="input-group">
                                                        <input id="name" type="text" class="sr-input" name="userName" placeholder="Tìm kiếm người dùng" value="${requestScope.namesearch}" required=""/>
                                                        <button class="btn sr-btn" type="submit"><i class="fa fa-search"></i></button>
                                                    </div>
                                                </form>
                                            </div>
                                        </div>
                                        <div style="padding: 0px 20px 20px 20px" class="full price_table padding_infor_info">
                                            <form action="manageruseracc" method="post">
                                                <table class="table table-striped" style="margin-bottom: 15px">
                                                    <thead>
                                                        <tr>
                                                            <th style="padding: 10px"></th>
                                                            <th style="padding: 10px">ID</th>
                                                            <th style="padding: 10px">Tên tài khoản</th>
                                                            <th style="padding: 10px">Tên người dùng</th>
                                                            <th style="padding: 10px">Ngày khởi tạo</th>
                                                            <th style="padding: 10px">Toàn bộ đơn đã mua</th>
                                                            <th style="padding: 10px">Thông tin người dùng</th>
                                                            <th style="padding: 10px">Trạng thái</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <% if (dataUser != null && !dataUser.isEmpty()) { %>
                                                        <% for (int i = 0; i < dataUser.size(); i++) { %>
                                                        <tr>
                                                            <td style="padding: 10px">
                                                                <input type="checkbox" name="userIds" value="<%= dataUser.get(i).getID() %>">
                                                            </td>
                                                            <td style="padding: 10px"><%= dataUser.get(i).getID() %></td>
                                                            <td style="padding: 10px"><%= dataUser.get(i).getUserName() %></td>
                                                            <td style="padding: 10px"><%= dataUser.get(i).getUser().getFirstName() %> <%= dataUser.get(i).getUser().getLastName() %></td>
                                                            <td style="padding: 10px"><%= dataUser.get(i).getCreatedAt() %></td>
                                                            <td style="padding: 10px">
                                                                <button class="btn btn-info" type="button" onclick="showOrder(<%= dataUser.get(i).getUser().getID() %>)">Xem các đơn hàng</button>
                                                            </td>
                                                            <td style="padding: 10px">
                                                                <button class="btn btn-info" type="button" onclick="showUserDetails(<%= dataUser.get(i).getUser().getID() %>)">Xem và tùy chỉnh</button>
                                                            </td>
                                                            <td style="padding: 10px">
                                                                <input type="checkbox" class="delete-toggle" 
                                                                       data-user-id="<%= dataUser.get(i).getUser().getID() %>" 
                                                                       <%= dataUser.get(i).getIsDelete() ? "checked" : "" %>
                                                                       onchange="handleCheckboxChange(this)">
                                                            </td>
                                                        </tr>
                                                        <% } %>
                                                        <% } else { %>
                                                        <tr>
                                                            <td colspan="8">No data available</td>
                                                        </tr>
                                                        <% } %>
                                                    </tbody>
                                                </table>
                                                <button type="submit"  class="btn btn-danger">Thay đổi trạng thái</button>
                                            </form>

                                        </div>
                                    </div>
                                </div>
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

            <!-- Bootstrap Modal -->
            <div class="modal fade" id="orderModal" tabindex="-1" role="dialog" aria-labelledby="orderModalLabel" aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="orderModalLabel">Thông tin đơn mua</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body" id="orderModalBody">
                            <table  class="table table-striped">
                                <thead>
                                    <tr>
                                        <th>STT</th>
                                        <th>Tổng giá tiền</th>
                                        <th>Trạng thái</th>
                                        <th>Ngày đặt hàng</th>
                                    </tr>
                                </thead>
                                <tbody id="orderContent">

                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>

            <div class="modal fade" id="moreDetail" tabindex="-1" role="dialog" aria-labelledby="moreDetailLabel" aria-hidden="true">
                <div style="max-width: 40%; width: 60%;" class="modal-dialog" role="document">
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
            </script>
            <!-- custom js -->
            <script src="js/chart_custom_style1.js"></script>
            <script src="js/custom.js"></script>
            <script>
                                                                           function showOrder(userID) {
                                                                               console.log('Account ID:', userID);
                                                                               $.ajax({
                                                                                   url: '/TheCardWebsite/showorder',
                                                                                   type: 'GET',
                                                                                   data: {userID: userID},
                                                                                   success: function (data) {
                                                                                       $('#orderContent').html(data);
                                                                                       $('#orderModal').modal('show');
                                                                                   },
                                                                                   error: function (xhr) {
                                                                                       console.error('Error fetching order data:', xhr);
                                                                                   }
                                                                               });
                                                                           }

                                                                           function showUserDetails(userID) {
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
//                                                                $('#moreDetail').on('hidden.bs.modal', function () {
//                                                                    $('#moreDetailContent').empty();
//                                                                    $(this).removeData('bs.modal').find('.modal-content').empty();
//                                                                });
                                                                           $(document).ready(function () {
                                                                               var msg = "<%= (request.getAttribute("msgCustom") != null) ? request.getAttribute("msgCustom") : "" %>";
                                                                               if (msg != "") {
                                                                                   alert("Thông báo: " + msg);
                                                                               }
                                                                           });

                                                                           function handleCheckboxChange(checkbox) {
                                                                               var userId = checkbox.getAttribute('data-user-id');
                                                                               var isChecked = checkbox.checked;

                                                                               var xhr = new XMLHttpRequest();
                                                                               xhr.open("POST", "toggleuserstatus", true);
                                                                               xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

                                                                               xhr.onreadystatechange = function () {
                                                                                   if (xhr.readyState === XMLHttpRequest.DONE) {
                                                                                       if (xhr.status === 200) {
                                                                                           console.log("Request successful");
                                                                                           // Handle success
                                                                                       } else {
                                                                                           console.error("Request failed");
                                                                                           // Handle error
                                                                                       }
                                                                                   }
                                                                               };

                                                                               xhr.send("userId=" + encodeURIComponent(userId) + "&isChecked=" + encodeURIComponent(isChecked));
                                                                           }
                                                                           function togglePasswordVisibility() {
                                                                               var passwordInput = document.getElementById('password');
                                                                               var toggleIcon = document.querySelector('.toggle-password');
                                                                               if (passwordInput.type === 'password') {
                                                                                   passwordInput.type = 'text';
                                                                                   toggleIcon.classList.remove('fa-eye');
                                                                                   toggleIcon.classList.add('fa-eye-slash');
                                                                               } else {
                                                                                   passwordInput.type = 'password';
                                                                                   toggleIcon.classList.remove('fa-eye-slash');
                                                                                   toggleIcon.classList.add('fa-eye');
                                                                               }
                                                                           }
            </script>
    </body>
</html>


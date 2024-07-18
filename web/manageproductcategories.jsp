<!--Thu - Iter 3-->
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
        <title>The Card Shop - Quản lý danh mục thẻ</title>
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
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
        <![endif]-->
        <style>
            /* Apply styles to the buttons */
            .my-button {
                border: none;
                width: 30px;
                height: 30px;
                text-align: center;
                align-content: center;
                border-radius: 5px;
                display: inline-block;
                vertical-align: middle;
                transition: background-color 0.3s ease; /* Add a smooth transition effect */
            }

            .edit{
                background-color: #18A66A;
            }

            /* Apply the shading effect on hover */
            .edit:hover {
                background-color: #12804D; /* Slightly darker shade */
            }
            .delete{
                background-color: red;
            }
            .delete:hover{
                background-color: darkred;
            }
            .switch {
                position: relative;
                display: inline-block;
                width: 40px; /* Smaller width */
                height: 20px; /* Smaller height */
            }
            .switch input {
                opacity: 0;
                width: 0;
                height: 0;
            }
            .slider {
                position: absolute;
                cursor: pointer;
                top: 0;
                left: 0;
                right: 0;
                bottom: 0;
                background-color: #ccc;
                transition: .4s;
            }
            .slider:before {
                position: absolute;
                content: "";
                height: 14px; /* Smaller knob height */
                width: 14px; /* Smaller knob width */
                left: 3px; /* Adjusted for smaller switch */
                bottom: 3px; /* Adjusted for smaller switch */
                background-color: white;
                transition: .4s;
            }
            input:checked + .slider {
                background-color: #2196F3;
            }
            input:checked + .slider:before {
                transform: translateX(20px); /* Adjusted for smaller switch */
            }
            .slider.round {
                border-radius: 20px; /* Smaller border radius */
            }
            .slider.round:before {
                border-radius: 50%;
            }
            textarea {
                width: 100%;
                padding: 10px;
                border: 1px solid #ccc;
                border-radius: 5px;
                height: 100px;
                resize: vertical;
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
                        <h4>Quản lý thể loại</h4>
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

                            <!--Show cart-->
                            <div class="row column1">
                                <div class="col-md-12">
                                    <div class="white_shd full margin_bottom_30">
                                        <div class="full graph_head">
                                            <div style="display: flex; justify-content: space-between; align-items: end;">
                                                <div class="heading1 margin_0 col-lg-2">
                                                    <h3>Loại thẻ</h3>
                                                </div>
                                                <div class="button_block col-lg-2" style="margin-top: 30px">
                                                    <a href="#" style="text-align: center; background-color: transparent; color: black; padding: 10px 10px; font-size: 15px; border-radius: 10px; border: 1px solid black;"
                                                       class="button_sction add-category-btn" data-toggle="modal" data-target="#addCategoryModal"
                                                       data-brand="${brandname}">Thêm loại thẻ</a>
                                                </div>
                                            </div>
                                            <div class="full padding_infor_info" style="padding: 10px 5px;">
                                                <div class="table_row">
                                                    <div class="table-responsive">
                                                        <table class="table table-striped">
                                                            <thead>
                                                                <tr>
                                                                    <th style="text-align: center; font-family: sans-serif; width: 10px; height: 30px">ID</th>
                                                                    <th style="text-align: center; font-family: sans-serif">Thẻ</th>
                                                                    <th></th>
                                                                    <th style="text-align: center; font-family: sans-serif; padding: 14px 0px">Giá</th>
                                                                    <th style="text-align: center; font-family: sans-serif; padding: 14px 0px">Số lượng</th>
                                                                    <th style="text-align: center; font-family: sans-serif; padding: 14px 0px">Khuyến Mãi</th>
                                                                    <th style="text-align: center; font-family: sans-serif; padding: 14px 0px">Mô tả</th>
                                                                    <th style="text-align: center; font-family: sans-serif; padding: 14px 0px">Hoạt động</th>
                                                                    <th style="text-align: center; font-family: sans-serif">Thao tác</th>
                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                                <c:forEach items="${requestScope.product}" var="c">
                                                                    <tr>
                                                                        <td style="width: 5px; text-align: center; align-content: center;">
                                                                            ${c.getId()}
                                                                        </td>
                                                                        <td style="width: 50px; text-align: center; align-content: center;">
                                                                            <a href="productcard?pcid=${c.getId()}"><img style="width: 200%;" src="images/logo/${c.getBrand().getImage()}" alt="${c.getName()}"></a>
                                                                        </td>
                                                                        <td style="width: 150px; padding: 12px 10px; align-content: center;">
                                                                            <a href="productcard?pcid=${c.getId()}"> ${c.getName()}</a>
                                                                        </td>
                                                                        <td style="text-align: center; width: 120px; padding: 12px 10px; align-content: center;">
                                                                            <fmt:formatNumber pattern="#,###" value="${c.getPrice()}"/> VNĐ
                                                                        </td>
                                                                        <td style="text-align: center; width: 80px; align-content: center;">
                                                                            ${c.getQuantity()}
                                                                        </td>
                                                                        <td style="text-align: center; width: 80px; padding: 0px 10px; align-content: center;">
                                                                            ${c.getDiscount()} %
                                                                        </td>
                                                                        <td style="text-align: center; align-content: center;">${c.getDescription()}</td>
                                                                        <td style="width: 20px; text-align: center; align-content: center;">
                                                                            <div style="display: flex; justify-content: center;">
                                                                                <form id="toggleForm-${c.getId()}">
                                                                                    <input type="hidden" name="brandid" value="${c.getBrand().getId()}"/> 
                                                                                    <input type="hidden" name="pcid" value="${c.getId()}"/>
                                                                                    <label class="switch">
                                                                                        <input type="checkbox" id="toggleSwitch-${c.getId()}" ${c.getIsDelete() ? '' : 'checked'} />
                                                                                        <span class="slider round"></span>
                                                                                    </label>
                                                                                </form>
                                                                            </div>
                                                                        </td>
                                                                        <td style="width: 20px; text-align: center; align-content: center;">
                                                                            <div style="display: flex; justify-content: center;">
                                                                                <a style="margin: 10px 5px;" class="btn btn-outline-info btn-square edit-category-btn" href="#" 
                                                                                   data-toggle="modal" data-target="#editCategoryModal"
                                                                                   data-id="${c.getId()}" data-categoryname="${c.getName()}" data-brandid="${c.getBrand().getId()}" data-brandname="${c.getBrand().getName()}"
                                                                                   data-price="${c.getPrice()}" data-quantity="${c.getQuantity()}" data-description="${c.getDescription()}"
                                                                                   data-discount="${c.getDiscount()}" data-discount-from="${c.getDiscountFrom()}" data-discount-to="${c.getDiscountTo()}">
                                                                                    <i class="fa fa-edit"></i>
                                                                                </a>   
                                                                            </div>
                                                                        </td>
                                                                    </tr>
                                                                </c:forEach>
                                                            </tbody>
                                                        </table>
                                                    </div>
                                                </div>
                                                <div class="pagination button_section button_style2" style="display: flex; justify-content: center; align-items: center;">
                                                    <c:forEach begin="1" end="${endP}" var="i">
                                                        <a href="productcategories?brandid=${param.brandid}&index=${i}" class="pagination-number" style="background-color: #18A66A; padding: 5px 10px; margin: 0 5px; border-radius: 5px; color: white; text-decoration: none;">
                                                            ${i}
                                                        </a>
                                                    </c:forEach>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <!-- end graph -->
                            </div>
                            <!--Edit Modal -->
                            <div class="modal fade" id="editCategoryModal" tabindex="-1" role="dialog" aria-labelledby="editCategoryModalLabel" aria-hidden="true">
                                <div class="modal-dialog" role="document">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title" id="editCategoryModalLabel">Chỉnh sửa danh mục thẻ</h5>
                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                <span aria-hidden="true">&times;</span>
                                            </button>
                                        </div>
                                        <div class="modal-body">
                                            <form action="updatecategory" method="POST" enctype="multipart/form-data">
                                                <input type="hidden" id="brandid" name="brandid"/>
                                                <input type="hidden" id="categoryid" name="categoryid" class="form-control" readonly/>
                                                <div class="form-group" id="showbrandname"></div>
                                                <div class="form-group">
                                                    <label>Tên danh mục</label>
                                                    <input type="text" id="categoryname" name="categoryname" class="form-control" required/>
                                                    <small id="categorynameError" class="text-danger"></small>
                                                </div>
                                                <input type="hidden" id="brandname" name="brandname" class="form-control" readonly/>
                                                <div class="form-group">
                                                    <label>Giá (VND)</label>
                                                    <input type="text" id="price" name="price" class="form-control" required onkeypress="return event.charCode >= 48 && event.charCode <= 57"/>
                                                    <input type="hidden" id="quantity" name="quantity" class="form-control" readonly/>
                                                </div>
                                                <div class="form-group">
                                                    <label>Mô tả</label>
                                                    <textarea name="description" id="description" class="form-control"></textarea>
                                                    <small id="descriptionError" class="text-danger"></small>
                                                </div>
                                                <div class="form-group" style="display: flex;">
                                                    <div class="col-lg-3" style="padding: 0; margin-right: 20px;">
                                                        <label>Khuyến mãi (%)</label>
                                                        <input type="number" id="discount" name="discount" class="form-control" min="0" max="30" onkeypress="return event.charCode >= 48 && event.charCode <= 57"/>
                                                    </div>
                                                    <div class="col-lg-4" style="padding: 0; margin-right: 15px;">
                                                        <label>Từ</label>
                                                        <input type="date" id="discountfrom" name="discountfrom" class="form-control" min="<?= date('Y-m-d'); ?>" />
                                                        <small id="discountfromError" class="text-danger"></small>
                                                    </div>
                                                    <div class="col-lg-4" style="padding: 0;">
                                                        <label>Đến</label>
                                                        <input type="date" id="discountto" name="discountto" class="form-control" min="<?= date('Y-m-d'); ?>" />
                                                        <small id="discounttoError" class="text-danger"></small>
                                                    </div>
                                                </div>
                                                <button type="submit" id="submitButton" class="btn btn-success btn-block">Lưu</button>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!--Add Modal -->
                            <div class="modal fade" id="addCategoryModal" tabindex="-1" role="dialog" aria-labelledby="addCategoryModalLabel" aria-hidden="true">
                                <div class="modal-dialog" role="document">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title" id="addCategoryModalLabel">Thêm danh mục thẻ</h5>
                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                <span aria-hidden="true">&times;</span>
                                            </button>
                                        </div>
                                        <div class="modal-body">
                                            <form action="addcategory" method="POST" enctype="multipart/form-data">
                                                <input type="hidden" name="brandid" value="${param.brandid}"/>
                                                <div class="form-group">
                                                    <label>Tên danh mục</label>
                                                    <input type="text" id="addcategoryname" name="categoryname" class="form-control" required/>
                                                    <small id="addcategorynameError" class="text-danger"></small>
                                                </div>
                                                <input type="hidden" id="addbrandname" name="brandname" class="form-control" readonly/>
                                                <div class="form-group">
                                                    <label>Giá (VND)</label>
                                                    <input type="text" id="addprice" name="price" class="form-control" required onkeypress="return event.charCode >= 48 && event.charCode <= 57"/>
                                                </div>
                                                <div class="form-group">
                                                    <label>Mô tả</label>
                                                    <textarea name="description" id="adddescription" class="form-control"></textarea>
                                                    <small id="adddescriptionError" class="text-danger"></small>
                                                </div>
                                                <button type="submit" id="addSubmitButton" class="btn btn-success btn-block">Thêm</button>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>
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
        <script>
            document.addEventListener('DOMContentLoaded', function () {
                // Select all toggle switch inputs
                const toggleSwitches = document.querySelectorAll('input[type="checkbox"][id^="toggleSwitch-"]');
                toggleSwitches.forEach(function (toggleSwitch) {
                    toggleSwitch.addEventListener('change', function () {
                        const form = this.closest('form');
                        const formData = new FormData(form);
                        const isChecked = this.checked;
                        formData.append('action', isChecked ? 'activate' : 'deactivate'); // Add action parameter

                        fetch('updatecategorystatus', {
                            method: 'POST',
                            body: formData
                        })
                                .then(response => response.json())
                                .then(data => {
                                    if (data.success) {
                                        alert('Cập nhật trạng thái thành công!');
                                    } else {
                                        alert('Cập nhật trạng thái thất bại!');
                                    }
                                })
                                .catch(error => {
                                    console.error('Error:', error);
                                    alert('Đã có lỗi xảy ra, vui lòng thử lại sau!.');
                                });
                    });
                });
                const editCategoryButtons = document.querySelectorAll('.edit-category-btn');
                editCategoryButtons.forEach(button => {
                    button.addEventListener('click', function () {
                        const id = this.getAttribute('data-id');
                        const brandId = this.getAttribute('data-brandid');
                        const categoryName = this.getAttribute('data-categoryname');
                        const brandName = this.getAttribute('data-brandname');
                        const price = this.getAttribute('data-price');
                        const quantity = this.getAttribute('data-quantity');
                        const description = this.getAttribute('data-description');
                        const discount = this.getAttribute('data-discount');
                        const discountFrom = this.getAttribute('data-discount-from');
                        const discountTo = this.getAttribute('data-discount-to');
                        const showBrandName = this.getAttribute('data-brandname');

                        document.getElementById('showbrandname').textContent = `Nhãn hàng: ` + showBrandName;
                        document.getElementById('categoryid').value = id;
                        document.getElementById('brandid').value = brandId;
                        document.getElementById('categoryname').value = categoryName;
                        document.getElementById('brandname').value = brandName;
                        document.getElementById('price').value = Math.round(price);
                        document.getElementById('quantity').value = quantity;
                        document.getElementById('description').value = description;
                        document.getElementById('discount').value = discount;
                        document.getElementById('discountfrom').value = discountFrom;
                        document.getElementById('discountto').value = discountTo;
                    });
                });

                const categorynameInput = document.getElementById('categoryname');
                const descriptionInput = document.getElementById('description');
                const discountfromInput = document.getElementById('discountfrom');
                const discounttoInput = document.getElementById('discountto');
                const discountInput = document.getElementById('discount');

                const categorynameError = document.getElementById('categorynameError');
                const descriptionError = document.getElementById('descriptionError');
                const discountfromError = document.getElementById('discountfromError');
                const discounttoError = document.getElementById('discounttoError');

                // Category name validation
                categorynameInput.addEventListener('input', function () {
                    // Validate categoryname length
                    if (categorynameInput.value.length > 40) {
                        categorynameError.textContent = 'Tên danh mục không được quá 40 ký tự.';
                        submitButton.disabled = true;
                    } else {
                        categorynameError.textContent = '';
                        submitButton.disabled = false;
                    }
                });

                // Description validation
                descriptionInput.addEventListener('input', function () {
                    // Validate description length
                    if (descriptionInput.value.length > 255) {
                        descriptionError.textContent = 'Mô tả không được quá 255 ký tự.';
                        submitButton.disabled = true;
                    } else {
                        descriptionError.textContent = '';
                        submitButton.disabled = false;
                    }
                });

                discountInput.addEventListener('input', function () {
                    validateDiscount();
                });

                discountfromInput.addEventListener('input', function () {
                    validateDiscount();
                });

                discounttoInput.addEventListener('input', function () {
                    validateDiscount();
                });

                function validateDiscount() {
                    let isValid = true;
                    const now = new Date().toISOString().split('T')[0];

                    // Validate discount input
                    if (parseFloat(discountInput.value) > 0) {
                        if (!discountfromInput.value) {
                            discountfromError.textContent = 'Ngày khuyến mãi từ phải được điền.';
                            isValid = false;
                        } else if (discountfromInput.value < now) {
                            discountfromError.textContent = 'Ngày khuyến mãi từ không được là ngày trong quá khứ.';
                            isValid = false;
                        } else {
                            discountfromError.textContent = '';
                        }

                        if (!discounttoInput.value) {
                            discounttoError.textContent = 'Ngày khuyến mãi đến phải được điền.';
                            isValid = false;
                        } else if (discounttoInput.value < now) {
                            discounttoError.textContent = 'Ngày khuyến mãi đến không được là ngày trong quá khứ.';
                            isValid = false;
                        } else if (discounttoInput.value < discountfromInput.value) {
                            discounttoError.textContent = 'Ngày khuyến mãi đến phải lớn hơn hoặc bằng ngày khuyến mãi từ.';
                            isValid = false;
                        } else {
                            discounttoError.textContent = '';
                        }

                        // Make date inputs editable if discount is greater than 0
                        discountfromInput.readOnly = false;
                        discounttoInput.readOnly = false;
                    } else {
                        discountfromError.textContent = '';
                        discounttoError.textContent = '';

                        // Make date inputs read-only and clear their values if discount is 0 or 0.0
                        if (discountfromInput.value) {
                            discountfromInput.value = '';
                        }
                        if (discounttoInput.value) {
                            discounttoInput.value = '';
                        }
                        discountfromInput.readOnly = true;
                        discounttoInput.readOnly = true;
                    }

                    // Enable or disable the submit button based on validity
                    submitButton.disabled = !isValid;
                }

                // Initial call to set the correct state on page load
                validateDiscount();

                // Event listener for modal show to reset form and validation
                $('#editCategoryModal').on('shown.bs.modal', function () {
                    // Clear input fields
                    categorynameError.textContent = '';
                    descriptionError.textContent = '';
                    discountfromError.textContent = '';
                    discounttoError.textContent = '';
                    submitButton.disabled = false;
                });
                // Event listener for modal hide to reset form and validation
                $('#editCategoryModal').on('hidden.bs.modal', function () {
                    // Clear input fields
                    categorynameError.textContent = '';
                    descriptionError.textContent = '';
                    discountfromError.textContent = '';
                    discounttoError.textContent = '';
                    submitButton.disabled = false;
                });

                const addCategoryButtons = document.querySelectorAll('.add-category-btn');
                addCategoryButtons.forEach(button => {
                    button.addEventListener('click', function () {
                        const brandName = this.getAttribute('data-brand');

                        document.getElementById('addbrandname').value = brandName;
                    });
                });

                const addCategorynameInput = document.getElementById('addcategoryname');
                const addDescriptionInput = document.getElementById('adddescription');

                const addCategorynameError = document.getElementById('addcategorynameError');
                const addDescriptionError = document.getElementById('adddescriptionError');

                // Category name validation
                addCategorynameInput.addEventListener('input', function () {
                    // Validate categoryname length
                    if (addCategorynameInput.value.length > 40) {
                        addCategorynameError.textContent = 'Tên danh mục không được quá 40 ký tự.';
                        addSubmitButton.disabled = true;
                    } else {
                        addCategorynameError.textContent = '';
                        addSubmitButton.disabled = false;
                    }
                });

                // Description validation
                addDescriptionInput.addEventListener('input', function () {
                    // Validate description length
                    if (addDescriptionInput.value.length > 255) {
                        addDescriptionError.textContent = 'Mô tả không được quá 255 ký tự.';
                        addSubmitButton.disabled = true;
                    } else {
                        addDescriptionError.textContent = '';
                        addSubmitButton.disabled = false;
                    }
                });

                // Event listener for modal show to reset form and validation
                $('#addCategoryModal').on('shown.bs.modal', function () {
                    // Clear input fields
                    addCategorynameInput.value = '';
                    addDescriptionInput.value = '';
                    addCategorynameError.textContent = '';
                    addDescriptionError.textContent = '';
                    addSubmitButton.disabled = false;
                });
                // Event listener for modal hide to reset form and validation
                $('#addCategoryModal').on('hidden.bs.modal', function () {
                    // Clear input fields
                    addCategorynameInput.value = '';
                    addDescriptionInput.value = '';
                    addCategorynameError.textContent = '';
                    addDescriptionError.textContent = '';
                    addSubmitButton.disabled = false;
                });

                $(document).ready(function () {
                    const errorMessage = '${error}';
                    const successMessage = '${success}';
                    if (errorMessage) {
                        $('#notificationModal .modal-body').text(errorMessage);
                        $('#notificationModal').modal('show');
                    } else if (successMessage) {
                        $('#notificationModal .modal-body').text(successMessage);
                        $('#notificationModal').modal('show');
                    }
                });
            });
        </script>
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
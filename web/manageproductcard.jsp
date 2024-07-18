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
        <title>The Card Shop - Quản lý thẻ</title>
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
                        <h4>Quản lý thẻ</h4>
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
                                                <div class="heading1 margin_0 col-lg-8">
                                                    <h3>Mã thẻ</h3>
                                                </div>
                                                <div class="button_block col-lg-2" >
                                                    <a href="#" style="text-align: center; background-color: transparent; color: black; padding: 10px 10px; font-size: 15px; border-radius: 10px; border: 1px solid black;"
                                                       class="button_sction add-card-btn" data-toggle="modal" data-target="#addCardModal"
                                                       data-category-name="${pcname}">Thêm thẻ</a>
                                                </div>
                                                <div class="button_block">
                                                    <a href="#" style="text-align: center; background-color: transparent; color: black; padding: 10px 10px; font-size: 15px; border-radius: 10px; border: 1px solid black;"
                                                       class="button_sction add-card-btn" data-toggle="modal" data-target="#importFromExcelModal"
                                                       data-category-name="${pcname}">Thêm từ Exel</a>
                                                </div>
                                            </div>
                                            <div class="full padding_infor_info" style="padding: 10px 5px;">
                                                <div class="table_row">
                                                    <div class="table-responsive">
                                                        <table class="table table-striped">
                                                            <thead>
                                                                <tr>
                                                                    <th style="text-align: center; font-family: sans-serif; width: 10px; height: 30px">ID</th>
                                                                    <th style="text-align: center; font-family: sans-serif">Seri</th>
                                                                    <th style="text-align: center; font-family: sans-serif; padding: 14px 0px">Code</th>
                                                                    <th style="text-align: center; font-family: sans-serif; padding: 14px 0px">Ngày hết hạn</th>
                                                                    <th style="text-align: center; font-family: sans-serif; padding: 14px 0px">Hoạt động</th>
                                                                    <th style="text-align: center; font-family: sans-serif">Thao tác</th>
                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                                <c:forEach items="${requestScope.productcard}" var="c">
                                                                    <tr>
                                                                        <td style="width: 10px; text-align: center; align-content: center;">
                                                                            ${c.getID()}
                                                                        </td>
                                                                        <td style="text-align: center; width: 100px; align-content: center;">${c.getSeri()}</td>
                                                                        <td style="text-align: center; width: 100px; align-content: center;">
                                                                            ${c.getCode()}
                                                                        </td>
                                                                        <td style="text-align: center; width: 120px; padding: 0px 10px; align-content: center;">
                                                                            ${c.getExpiredDate()}
                                                                        </td>
                                                                        <td style="width: 20px; text-align: center; align-content: center;">
                                                                            <div style="display: flex; justify-content: center;">
                                                                                <form id="toggleForm-${c.getID()}">
                                                                                    <input type="hidden" name="cardid" value="${c.getID()}"/>
                                                                                    <input type="hidden" name="pcid" value="${c.getProductCategories().getId()}"/>
                                                                                    <label class="switch">
                                                                                        <input type="checkbox" id="toggleSwitch-${c.getID()}" ${c.getIsDelete() ? '' : 'checked'} disabled/>
                                                                                        <span class="slider round"></span>
                                                                                    </label>
                                                                                </form>
                                                                            </div>
                                                                        </td>
                                                                        <td style="width: 20px; text-align: center; align-content: center;">
                                                                            <div style="display: flex; justify-content: center;">
                                                                                <a style="margin: 10px 5px;" class="btn btn-outline-info btn-square edit-card-btn" href="#" 
                                                                                   data-toggle="modal" data-target="#editCardModal"
                                                                                   data-id="${c.getID()}" data-seri="${c.getSeri()}" data-code="${c.getCode()}" data-expired-date="${c.getExpiredDate()}">
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
                                                        <a href="?pcid=${param.pcid}&index=${i}" class="pagination-number" style="background-color: #18A66A; padding: 5px 10px; margin: 0 5px; border-radius: 5px; color: white; text-decoration: none;">
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
                            <div class="modal fade" id="editCardModal" tabindex="-1" role="dialog" aria-labelledby="editCardModalLabel" aria-hidden="true">
                                <div class="modal-dialog" role="document">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title" id="editCardModalLabel">Chỉnh sửa thẻ</h5>
                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                <span aria-hidden="true">&times;</span>
                                            </button>
                                        </div>
                                        <div class="modal-body">
                                            <form action="updatecard" method="POST" enctype="multipart/form-data">
                                                <input type="hidden" name="pcid" value="${param.pcid}"/>
                                                <input type="hidden" id="cardid" name="cardid" class="form-control" readonly/>
                                                <div class="form-group">
                                                    <label>Seri</label>
                                                    <input type="text" id="seri" name="seri" class="form-control" required/>
                                                    <small id="seriError" class="text-danger"></small>
                                                </div>
                                                <div class="form-group">
                                                    <label>Code</label>
                                                    <input type="text" id="code" name="code" class="form-control" required/>
                                                    <small id="codeError" class="text-danger"></small>
                                                </div>
                                                <div class="form-group">
                                                    <label>Ngày hết hạn</label>
                                                    <input type="date" id="expireddate" name="expireddate" class="form-control" min="<?= date('Y-m-d'); ?>" required/>
                                                    <small id="expireddateError" class="text-danger"></small>
                                                </div>
                                                <button type="submit" id="submitButton" class="btn btn-success btn-block">Lưu</button>
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
                                            <button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!--Add Modal -->
                            <div class="modal fade" id="addCardModal" tabindex="-1" role="dialog" aria-labelledby="addCardModalLabel" aria-hidden="true">
                                <div class="modal-dialog" role="document">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title" id="addCardModalLabel">Thêm thẻ</h5>
                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                <span aria-hidden="true">&times;</span>
                                            </button>
                                        </div>
                                        <div class="modal-body">
                                            <form action="addcard" method="POST" enctype="multipart/form-data">
                                                <input type="hidden" name="pcid" value="${param.pcid}"/>
                                                <input type="hidden" id="categoryname" name="categoryname" class="form-control" readonly/>
                                                <div class="form-group">
                                                    <label>Seri</label>
                                                    <input type="text" id="addseri" name="seri" class="form-control" required/>
                                                    <small id="addSeriError" class="text-danger"></small>
                                                </div>
                                                <div class="form-group">
                                                    <label>Code</label>
                                                    <input type="text" id="addcode" name="code" class="form-control" required/>
                                                    <small id="addCodeError" class="text-danger"></small>
                                                </div>
                                                <div class="form-group">
                                                    <label>Ngày hết hạn</label>
                                                    <input type="date" id="addexpireddate" name="expireddate" class="form-control" min="<?= date('Y-m-d'); ?>" required/>
                                                    <small id="addExpiredDateError" class="text-danger"></small>
                                                </div>
                                                <button type="submit" id="addSubmitButton" class="btn btn-success btn-block">Lưu</button>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!--Import from excel Modal -->
                            <div class="modal fade" id="importFromExcelModal" tabindex="-1" role="dialog" aria-labelledby="importFromExcelModalLabel" aria-hidden="true">
                                <div class="modal-dialog" role="document">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title" id="importFromExcelModalLabel">Thêm từ Excel</h5>
                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                <span aria-hidden="true">&times;</span>
                                            </button>
                                        </div>
                                        <div class="modal-body">
                                            <form action="importfromexcel" method="POST" enctype="multipart/form-data">
                                                <input type="hidden" name="pcid" value="${param.pcid}"/>
                                                <div class="form-group">
                                                    <label for="file">Lựa chọn file excel: </label>
                                                    <input type="file" id="file" name="file" accept=".xls,.xlsx">
                                                    <br/>
                                                    <small id="IFEError" class="text-danger"></small>
                                                </div>
                                                <button type="submit" id="IFESubmitButton" class="btn btn-success btn-block">Import</button>
                                            </form>
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
                    const editCardButtons = document.querySelectorAll('.edit-card-btn');
                    editCardButtons.forEach(button => {
                        button.addEventListener('click', function () {
                            const id = this.getAttribute('data-id');
                            const seri = this.getAttribute('data-seri');
                            const code = this.getAttribute('data-code');
                            const expiredDate = this.getAttribute('data-expired-date');

                            document.getElementById('cardid').value = id;
                            document.getElementById('seri').value = seri;
                            document.getElementById('code').value = code;
                            document.getElementById('expireddate').value = expiredDate;
                        });
                    });
                    const seriInput = document.getElementById('seri');
                    const codeInput = document.getElementById('code');
                    const expiredDateInput = document.getElementById('expireddate');

                    const seriError = document.getElementById('seriError');
                    const codeError = document.getElementById('codeError');
                    const expiredDateError = document.getElementById('expireddateError');

                    // seri validation
                    seriInput.addEventListener('input', function () {
                        if (seriInput.value.length !== 16) {
                            seriError.textContent = 'Seri phải chứa 16 ký tự.';
                            submitButton.disabled = true;
                        } else {
                            seriError.textContent = '';
                            submitButton.disabled = false;
                        }
                    });

                    // code validation
                    codeInput.addEventListener('input', function () {
                        if (codeInput.value.length !== 16) {
                            codeError.textContent = 'Code phải chứa 16 ký tự.';
                            submitButton.disabled = true;
                        } else {
                            codeError.textContent = '';
                            submitButton.disabled = false;
                        }
                    });

                    // expired date validation
                    const now = new Date().toISOString().split('T')[0];
                    expiredDateInput.addEventListener('input', function () {
                        if (expiredDateInput.value && expiredDateInput.value < now) {
                            expiredDateError.textContent = 'Ngày hết hạn không được là ngày trong quá khứ.';
                            submitButton.disabled = true;
                        } else {
                            expiredDateError.textContent = '';
                            submitButton.disabled = false;
                        }
                    });

                    // Event listener for modal show to reset form and validation
                    $('#editCardModal').on('shown.bs.modal', function () {
                        // Clear input fields
                        seriError.textContent = '';
                        codeError.textContent = '';
                        expiredDateError.textContent = '';
                        submitButton.disabled = false;
                    });
                    // Event listener for modal hide to reset form and validation
                    $('#editCardModal').on('hidden.bs.modal', function () {
                        // Clear input fields
                        seriError.textContent = '';
                        codeError.textContent = '';
                        expiredDateError.textContent = '';
                        submitButton.disabled = false;
                    });

                    // show notification popup
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

                    const addCardButtons = document.querySelectorAll('.add-card-btn');
                    addCardButtons.forEach(button => {
                        button.addEventListener('click', function () {
                            const categoryName = this.getAttribute('data-category-name');

                            document.getElementById('categoryname').value = categoryName;
                        });
                    });

                    const addSeriInput = document.getElementById('addseri');
                    const addCodeInput = document.getElementById('addcode');
                    const addExpiredDateInput = document.getElementById('addexpireddate');

                    const addSeriError = document.getElementById('addSeriError');
                    const addCodeError = document.getElementById('addCodeError');
                    const addExpiredDateError = document.getElementById('addExpiredDateError');

                    // Seri validation
                    addSeriInput.addEventListener('input', function () {
                        if (addSeriInput.value.length !== 16) {
                            addSeriError.textContent = 'Seri phải chứa 16 ký tự.';
                            addSubmitButton.disabled = true;
                        } else {
                            addSeriError.textContent = '';
                            addSubmitButton.disabled = false;
                        }
                    });

                    // Description validation
                    addCodeInput.addEventListener('input', function () {
                        // Validate description length
                        if (addCodeInput.value.length !== 16) {
                            addCodeError.textContent = 'Code phải chứa 16 ký tự.';
                            addSubmitButton.disabled = true;
                        } else {
                            addCodeError.textContent = '';
                            addSubmitButton.disabled = false;
                        }
                    });

                    // expired date validation
                    addExpiredDateInput.addEventListener('input', function () {
                        if (addExpiredDateInput.value && addExpiredDateInput.value < now) {
                            addExpiredDateError.textContent = 'Ngày hết hạn không được là ngày trong quá khứ.';
                            submitButton.disabled = true;
                        } else {
                            addExpiredDateError.textContent = '';
                            submitButton.disabled = false;
                        }
                    });

                    // Event listener for modal show to reset form and validation
                    $('#addCardModal').on('shown.bs.modal', function () {
                        // Clear input fields
                        addSeriInput.value = '';
                        addCodeInput.value = '';
                        addExpiredDateInput.value = '';
                        addSeriError.textContent = '';
                        addCodeError.textContent = '';
                        addExpiredDateError.textContent = '';
                        addSubmitButton.disabled = false;
                    });
                    // Event listener for modal hide to reset form and validation
                    $('#addCardModal').on('hidden.bs.modal', function () {
                        // Clear input fields
                        addSeriInput.value = '';
                        addCodeInput.value = '';
                        addExpiredDateInput.value = '';
                        addSeriError.textContent = '';
                        addCodeError.textContent = '';
                        addExpiredDateError.textContent = '';
                        addSubmitButton.disabled = false;
                    });

                    const fileInput = document.getElementById('file');
                    const errorElement = document.getElementById('IFEError');

                    fileInput.addEventListener('input', function () {
                        const filePath = fileInput.value;
                        const allowedExtensions = /(\.xls|\.xlsx)$/i;
                        if (!allowedExtensions.exec(filePath)) {
                            // File type is not allowed
                            errorElement.textContent = 'Please select a valid Excel file (.xls or .xlsx).';
                            IFESubmitButton.disabled = true;
                        } else {
                            errorElement.textContent = '';
                            IFESubmitButton.disabled = false;
                        }
                    });
                    // Event listener for modal show to reset form and validation
                    $('#importFromExcelModal').on('shown.bs.modal', function () {
                        // Clear input fields
                        fileInput.value = '';
                        errorElement.textContent = '';
                        IFESubmitButton.disabled = false;
                    });
                    // Event listener for modal hide to reset form and validation
                    $('#importFromExcelModal').on('hidden.bs.modal', function () {
                        // Clear input fields
                        fileInput.value = '';
                        errorElement.textContent = '';
                        IFESubmitButton.disabled = false;
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
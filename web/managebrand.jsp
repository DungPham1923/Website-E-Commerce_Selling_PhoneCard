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
        <title>The Card Shop - Quản lý thương hiệu</title>
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
        <link rel="stylesheet" href="path/to/your/css/styles.css">
        <!--[if lt IE 9]>-->
<!--        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>-->
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
            List<Order> data = (List<Order>) request.getAttribute("data");
            List<OrderDetails> dataa = (List<OrderDetails>) request.getAttribute("list");
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
                        <h4>Quản lý thương hiệu</h4>
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
                            <div class="row column1">
                                <div class="col-md-12">
                                    <div class="card">
                                        <div class="card-header bg-light">
                                            <form class="form-inline" action="searchBrands" method="GET">
                                                <button type="button" class="btn btn-success ml-2" data-toggle="modal" data-target="#addBrandModal">Thêm mới+</button>
                                            </form>

                                        </div>
                                        <div class="card-header bg-light">
                                            <div class="d-flex justify-content-between align-items-center">
                                                <h5 class="card-title mb-0">Quản lí thương hiệu</h5>
                                                <form class="form-inline" action="searchBrands" method="GET">
                                                    <%if(request.getAttribute("key")!=null){
                                                      String key = (String) request.getAttribute("key");%>
                                                    <input class="form-control mr-sm-2" type="search" placeholder="Nhập tên thương hiệu" aria-label="Search" name="keyword" value="<%= key%>"/>
                                                    <%} else{%>
                                                    <input class="form-control mr-sm-2" type="search" placeholder="Nhập tên thương hiệu" aria-label="Search" name="keyword"/>
                                                    <%}%>
                                                    <button class="btn btn-outline-primary my-2 my-sm-0" type="submit"><i class="fa fa-search"></i>Tìm kiếm</button>
                                                </form>
                                            </div>
                                        </div>
                                        <div class="card-body">
                                            <div class="table-responsive">
                                                <table class="table table-striped">
                                                    <thead>
                                                        <tr>
                                                            <th style="text-align: center; font-weight: bold; font-family: sans-serif; font-size: 15px;">ID</th>
                                                            <th style="text-align: center; font-weight: bold; font-family: sans-serif; font-size: 15px;">Tên</th>
                                                            <th style="text-align: center; font-weight: bold; font-family: sans-serif; font-size: 15px; width: 100px;">Ảnh</th>
                                                            <th style="text-align: center; font-weight: bold; font-family: sans-serif; font-size: 15px;">Ngày tạo</th>
                                                            <th style="text-align: center; font-weight: bold; font-family: sans-serif; font-size: 15px;">Ngày cập nhật</th>
                                                            <th style="text-align: center; font-weight: bold; font-family: sans-serif; font-size: 15px;">Ngày xóa</th>
                                                            <th style="text-align: center; font-weight: bold; font-family: sans-serif; font-size: 15px;">Trạng thái</th>
                                                            <th style="text-align: center; font-weight: bold; font-family: sans-serif; font-size: 15px;">Xóa bởi</th>
                                                            <th style="text-align: center; font-weight: bold; font-family: sans-serif; font-size: 15px;">Điều chỉnh</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <% 
                                                            List<Brand> brands = (List<Brand>) request.getAttribute("brands");
                                                            if (brands != null) {
                                                                for (Brand brand : brands) {
                                                        %>
                                                        <tr>
                                                            <td onclick="window.location.href = 'productcategories?brandid=<%=brand.getId()%>';" style="cursor: pointer;"><%= brand.getId() %></td>
                                                            <td style="text-align: center" onclick="window.location.href = 'productcategories?brandid=<%=brand.getId()%>';" style="cursor: pointer;"><%= brand.getName() %></td>
                                                            <td style="text-align: center" onclick="window.location.href = 'productcategories?brandid=<%=brand.getId()%>';" style="cursor: pointer;"><img src="images/logo/<%= brand.getImage() %>" alt="<%= brand.getName() %>" width="100" height="50"/></td>
                                                            <%if(brand.getCreatedAt() == null){%>
                                                            <td style="text-align: center">Trống</td>
                                                            <%}else {%>
                                                            <td style="text-align: center" > <%= brand.getCreatedAt() %></td>
                                                            <%}%>
                                                            
                                                             <%if(brand.getUpdatedAt() == null){%>
                                                            <td style="text-align: center">Trống</td>
                                                            <%}else {%>
                                                            <td style="text-align: center" > <%= brand.getUpdatedAt() %></td>
                                                            <%}%>
                                                            
                                                            <%if(brand.getDeletedAt() == null){%>
                                                            <td style="text-align: center">Trống</td>
                                                            <%}else {%>
                                                            <td style="text-align: center" > <%= brand.getDeletedAt() %></td>
                                                            <%}%>
                                                              
                                                            <%if(brand.getIsDelete() == false){%>
                                                            <td style="text-align: center">Đang hoạt động</td>
                                                            <%}else {%>
                                                            <td style="text-align: center" > Ngừng hoạt động</td>
                                                            <%}%>

                                                            <%if(brand.getDeletedBy() == 0){%>
                                                            <td style="text-align: center">Trống</td>
                                                            <%}else {%>
                                                            <td style="text-align: center" ><%=brand.getDeletedBy() %></td>
                                                            <%}%>
                                                            
                                                            <td class="d-flex justify-content-start align-items-center">
                                                                <%if(brand.getIsDelete()==false){%>
                                                                <a class="btn btn-info btn-sm text-light mr-2" data-id="<%= brand.getId() %>" data-brandName="<%= brand.getName() %>" data-brandImage="images/logo/<%= brand.getImage() %>" data-toggle="modal" data-target="#updateBrandModal">
                                                                    Chỉnh sửa
                                                                </a>
                                                                <%int userId = Integer.parseInt(request.getAttribute("userId")+"");%>

                                                                <form class="d-inline" id="deleteForm" action="deleteBrand" method="POST">
                                                                    <input type="hidden" id="deleteBrandId" name="brandId">
                                                                    <input type="hidden" id="deleteUserId" name="userId">
                                                                </form>
                                                                <a href="#" class="btn btn-danger btn-sm" onclick="confirmDelete(<%= brand.getId()%>,<%= userId %>)">Xóa</a>
                                                                <%}else{%>
                                                                <form class="d-inline" id="restoreBrand" action="restoreBrand" method="POST">
                                                                    <input type="hidden" id="restoreBrandId" name="brandId">
                                                                </form>
                                                                <a href="#" class="btn btn-success btn-sm" onclick="restore('<%= brand.getId() %>')">Khôi phục</a>
                                                                <%}%>
                                                            </td>
                                                        </tr>
                                                        <%      }
                                                            } %>
                                                    </tbody>
                                                </table>
                                                <script>
                                                    function confirmDelete(brandId, userId) {
                                                        if (confirm("Bạn có chắc chắn muốn xóa sản phẩm này không?")) {
                                                            document.getElementById('deleteBrandId').value = brandId;
                                                            document.getElementById('deleteUserId').value = userId;
                                                            document.getElementById('deleteForm').submit();
                                                        }
                                                    }
                                                    function restore(brandId) {
                                                        document.getElementById('restoreBrandId').value = brandId;
                                                        document.getElementById('restoreBrand').submit();
                                                    }
                                                </script>
                                                <script>
                                                    function showDeleteSuccess() {
                                                        alert("Đã xóa nhãn hiệu thành công!");
                                                    }
                                                </script>
                                                <% if (request.getAttribute("deleteSuccess") != null && (boolean) request.getAttribute("deleteSuccess")) { %>
                                                <script>
                                                    showDeleteSuccess();
                                                </script>
                                                <% } %>
                                            </div>
                                        </div>


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
                            <!-- end dashboard inner -->
                        </div>
                        <!-- end dashboard inner -->
                    </div>
                </div>
            </div>

            <!-- Phần còn lại của nội dung -->
        </div>
        <!-- Kết thúc thẻ content -->
        <div class="modal fade" id="addBrandModal" tabindex="-1" role="dialog" aria-labelledby="addBrandModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-lg" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="addBrandModalLabel">Thêm thương hiệu</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <form id="addBrandForm" action="addBrand" method="POST" enctype="multipart/form-data">
                            <div class="form-group">
                                <label for="name">Tên:</label>
                                <input type="text" class="form-control" id="name" name="name" required>
                            </div>
                            <div class="form-group">
                                <label for="image">Ảnh:</label>
                                <input type="file" class="form-control" id="image" name="image" required>
                            </div>
                            
                            <button type="submit" class="btn btn-primary">Thêm mới</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" id="updateBrandModal" tabindex="-1" role="dialog" aria-labelledby="updateBrandModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="updateBrandModalLabel">Chỉnh sửa thương hiệu</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form id="updateBrandForm" action="updateBrand" method="POST" enctype="multipart/form-data">
                        <div class="form-group">
                            <label for="name">Tên mới:</label>
                            <input type="text" class="form-control" id="brandName" name="name" required>
                        </div>
                        <div class="form-group">
                            <label for="image">Ảnh mới:</label>
                            <input type="file" class="form-control" id="brandImage" name="image">
                        </div>
                        <div class="form-group">
                            <img id="newImagePreview" src="" class="img-fluid" style="max-width: 100px; max-height: 100px;">
                        </div>
                        <div class="form-group">
                            <label for="currentImage">Ảnh hiện tại:</label>
                            <img id="currentImage" src="" alt="Current Image" class="img-fluid" style="max-width: 100px; max-height: 100px;">
                        </div>
                        <input type="hidden" id="brandId" name="brandId">
                        <button type="submit" class="btn btn-primary">Chỉnh sửa</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<% 
    if (request.getAttribute("errorName") != null) 
    { 
%>
<script type="text/javascript">
    window.setTimeout(function () {
        alert("Tên thương hiệu đã tồn tại");
    }, 1000);
</script>
<% 
    } 
%>

<script>

//    function getProductCategories(id) {//
//        Window.Location.Href = productcategories?brandid = id;
//    }

    document.addEventListener('DOMContentLoaded', function () {
        $('#updateBrandModal').on('show.bs.modal', function (event) {
            var button = $(event.relatedTarget); // Button that triggered the modal
            var brandId = button.data('id');// Extract info from data-* attributes
            var brandName = button.data('brandname');
            var brandImage = button.data('brandimage');
            var modal = $(this);
            console.log(brandId + brandName + brandImage);
            modal.find('#brandId').val(brandId);
            modal.find('#brandName').val(brandName);
            modal.find('#brandImage').val(null);
            modal.find('#currentImage').attr('src', brandImage);
            modal.find('#newImagePreview').attr('src', '');
        });

        document.getElementById('brandImage').addEventListener('change', function (event) {
            var input = event.target;
            var file = input.files[0];
            if (file) {
                var reader = new FileReader();
                reader.onload = function (e) {
                    document.getElementById('newImagePreview').src = e.target.result;
                };
                reader.readAsDataURL(file);
            } else {
                document.getElementById('newImagePreview').src = '';
            }
        });
    });
</script>
<!-- Modal for Order Details -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
<!-- jQuery -->
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
</script>
<!-- custom js -->
<script src="js/chart_custom_style1.js"></script>
<script src="js/custom.js"></script>
</body>
</html>
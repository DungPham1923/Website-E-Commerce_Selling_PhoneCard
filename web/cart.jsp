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
        <title>The Card Shop - Giỏ hàng</title>
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
        <!--<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>-->
        <!--<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>-->
        <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
        <![endif]-->
        <style>
            .delete-button {
                background-color: #e74c3c; /* Red background color */
                color: white; /* White text color */
                border: none; /* No border */
                padding: 5px 15px; /* Padding around the text */
                font-size: 16px; /* Font size */
                border-radius: 5px; /* Rounded corners */
                cursor: pointer; /* Pointer cursor on hover */
                transition: background-color 0.3s ease; /* Smooth transition for background color */
            }

            .delete-button:hover {
                background-color: #c0392b; /* Darker red on hover */
            }

            .delete-button:focus {
                outline: none; /* Remove the outline on focus */
                box-shadow: 0 0 0 3px rgba(231, 76, 60, 0.4); /* Add a shadow on focus */
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
                        <h4>Giỏ hàng</h4>
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

                            <!--Show cart-->
                            <div class="row column1">
                                <div class="col-md-12">
                                    <div class="white_shd full margin_bottom_30">
                                        <div class="full graph_head" style="padding: 18px 15px;">
                                            <div class="heading1 margin_0">
                                                <h2>Giỏ hàng</h2>
                                            </div>

                                            <div class="full padding_infor_info" style="padding: 10px 5px;">
                                                <div class="table_row">
                                                    <div class="table-responsive">
                                                        <table class="table table-striped">
                                                            <thead>
                                                                <tr>
                                                                    <th style="text-align: center; font-family: sans-serif; width: 30px; height: 30px">Chọn</th>
                                                                    <th style="text-align: center; font-family: sans-serif">Thẻ</th>
                                                                    <th></th>
                                                                    <th style="text-align: center; font-family: sans-serif; padding: 14px 0px">Đơn giá</th>
                                                                    <th style="text-align: center; font-family: sans-serif; padding: 14px 0px">Khuyễn mãi</th>
                                                                    <th style="text-align: center; font-family: sans-serif; padding: 14px 0px; width: 65px">Số lượng</th>
                                                                    <th style="text-align: center; font-family: sans-serif; padding: 14px 0px">Thành tiền</th>
                                                                    <th style="text-align: center; font-family: sans-serif">Xóa</th>
                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                                <c:forEach items="${requestScope.cart}" var="c">
                                                                    <tr>
                                                                        <td style="width: 70px; text-align: center; align-content: center;">
                                                                            <input type="checkbox" id="checkbox${c.getID()}" name="select" class="product-checkbox" 
                                                                                   data-id="${c.getID()}"
                                                                                   data-price="${c.getProductCategories().getPrice()}" 
                                                                                   data-discount="${c.getProductCategories().getDiscount()}" 
                                                                                   data-quantity="${c.getQuantity()}" 
                                                                                   data-total-price="${c.getProductCategories().getPrice() * c.getQuantity() * (1-c.getProductCategories().getDiscount() / 100)}"
                                                                                   onclick="updateTotal()" 
                                                                                   style="transform: scale(1.5); width: 20px; padding: 12px 10px">
                                                                        </td>
                                                                        <td style="width: 70px;">
                                                                            <img style="width: 200%;" src="images/logo/${c.getProductCategories().getImage()}" alt="${c.getProductCategories().getName()}"/>
                                                                        </td>
                                                                        <td style="width: 250px; padding: 12px 10px; ">${c.getProductCategories().getName()}</td>
                                                                        <td style="text-align: center; width: 120px; padding: 12px 10px">
                                                                            <fmt:formatNumber pattern="#,###" value="${c.getProductCategories().getPrice()}"/> VNĐ
                                                                        </td>
                                                                        <td style="text-align: center; width: 100px;">
                                                                            <c:choose>
                                                                                <c:when test="${c.getProductCategories().getDiscount() != null && c.getProductCategories().getDiscount() != 0}">
                                                                                    <fmt:formatNumber pattern="#,###" value="${c.getProductCategories().getDiscount()}"/>
                                                                                </c:when>
                                                                            </c:choose>
                                                                        </td>
                                                                <form id="quantityForm${c.getID()}" action="updatequantity" method="POST">
                                                                    <input type="hidden" name="cartid" value="${c.getID()}"/>
                                                                    <td id="totalPrice${c.getID()}" style="width: 120px; text-align: center; display: flex; justify-content: center; align-items: center; align-content: center">
                                                                        <button type="submit" style="width: 30%; background-color: transparent; border-width: 1px;" onclick="handleDecrease('${c.getID()}')">-</button>
                                                                        <input style="width: 30%; text-align: center;" type="text" name="quantity" id="quantity${c.getID()}" value="${c.getQuantity()}" max="${c.getProductCategories().getQuantity()}" onkeypress="return event.charCode >= 48 && event.charCode <= 57"/>
                                                                        <button type="submit" style="width: 30%; background-color: transparent; border-width: 1px;" onclick="handleIncrease('${c.getID()}')">+</button>
                                                                    </td>
                                                                </form>
                                                                <td style="text-align: center; width: 120px; padding: 0px 10px; align-content: center;">
                                                                    <fmt:formatNumber pattern="#,###" value="${c.getProductCategories().getPrice() * c.getQuantity() * (1-c.getProductCategories().getDiscount() / 100)}"/> VNĐ
                                                                </td>
                                                                <form action="deletefromcart" method="POST">
                                                                    <input type="hidden" name="cartid" value="${c.getID()}"/>
                                                                    <td style="text-align: center; padding: 0px; align-content: center;">
                                                                        <button style="width: 45px; height: 35px; text-align: center;" type="submit" 
                                                                                onclick="return confirm('Bạn có chắc chắn muốn xóa sản phẩm này ra khỏi giỏ hàng không?');"
                                                                                class="delete-button">
                                                                            <i class="fa fa-trash" aria-hidden="true"></i>
                                                                        </button>
                                                                    </td>
                                                                </form>
                                                                </tr>
                                                            </c:forEach>
                                                            </tbody>
                                                        </table>
                                                        <form id="cartForm" action="checkout" method="POST" style="margin: 50px 0px">
                                                            <div id="hiddenInputsContainer"></div>
                                                            <div id="hiddenTotalContainer"></div>
                                                            <input type="hidden" id="totalAmount" name="totalAmount" value="0"/>
                                                            <input type="hidden" id="totalQuantity" name="totalQuantity" value="0">
                                                            <input type="hidden" id="orderId" name="orderId" value="">
                                                            <div class="heading1 margin_bottom_30">
                                                                <h2 id="totalPriceDisplay" >Tổng tiền thanh toán: 0 VNĐ</h2>
                                                            </div>
                                                            <div class="button_block" style="margin-top: 30px">
                                                                <button type="submit" id="checkoutButton" class="button_sction" onclick="event.preventDefault();submitFormToAddOrder()"
                                                                style="background-color: transparent; color: black; padding: 10px 10px; font-size: 15px; border-radius: 10px; cursor: pointer;">Thanh toán</button>
                                                            </div>
                                                        </form>
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
            <script>
                // Function to update quantity and ensure checkbox is checked
                function updateQuantity(id, quantity) {
                    $.ajax({
                        url: '/TheCardWebsite/updatequantity', // the URL of your servlet
                        type: 'POST', // http method
                        data: {cartid: id, quantity: quantity}, // data sent with the post request
                        success: function (response) {
                            console.log('The quantity was updated successfully!');

                            // Ensure the corresponding checkbox is checked
                            document.getElementById('checkbox' + id).checked = true;

                            // Recalculate the total price
                            var price = parseFloat(document.getElementById('price' + id).value);
                            var discount = parseFloat(document.getElementById('discount' + id).value);
                            var totalPrice = price * quantity * (1 - discount / 100);

                            // Update the total price in the HTML
                            document.getElementById('totalPrice' + id).innerHTML = totalPrice.toLocaleString() + ' VNĐ';

                            // Update the total amount in the price table
                            updateTotal();
                        },
                        error: function (xhr, errmsg, err) {
                            console.log('There was an error updating the quantity: ' + errmsg);
                        }
                    });
                }

                // Function to handle increase/decrease button
                function handleDecrease(id) {
//                    event.preventDefault();
                    value = parseInt(document.getElementById('quantity' + id).value, 10);
                    value = isNaN(value) ? 1 : value;
                    if (value <= 1) {
                        if (confirm('Bạn có chắc chắn muốn xóa sản phẩm này ra khỏi giỏ hàng không?')) {
                            // Change form action to deletefromcart and submit the form
                            var form = document.getElementById('quantityForm' + id);
                            form.action = 'deletefromcart';
                            form.submit();
                        }
                    } else {
                        value = value - 1;
                        document.getElementById('quantity' + id).value = value;
                        updateQuantity(id, value);
                    }
                }

                function handleIncrease(id) {
//                    event.preventDefault();
                    var input = document.getElementById('quantity' + id);
                    var value = parseInt(input.value, 10);
                    var max = parseInt(input.getAttribute('max'), 10);
                    value = isNaN(value) ? 0 : value;
                    if (value < max) {
                        value++;
                        input.value = value;
                        updateQuantity(id, value);
                    } else {
                        // Show alert if value exceeds the max limit
                        alert('Số lượng thẻ bạn mua vượt quá số lượng thẻ chúng tôi có');
                        var increaseButton = document.querySelector(`button[onclick="handleIncrease('${id}')"]`);
                        increaseButton.disabled = true;
                    }
                }

                function submitFormToAddOrder() {
                    event.preventDefault();
                    var form = $('#cartForm');
                    var formData = form.serialize();
                    
                    $.ajax({
                        url: '/TheCardWebsite/addtoorders',
                        type: 'POST',
                        data: formData,
                        success: function (response) {
                            console.log('Success:', response);
                            // Sau khi gửi yêu cầu thành công, có thể tiếp tục submit form tới checkout
                            //doccument.getElementById("cartForm").innerHTML = response;
                            $('#orderId').val(response.orderId);
                            form.attr('action', 'checkout');
                            form.off('submit').submit(); // Off submit event to avoid recursive submission
                        },
                        error: function (xhr, status, error) {
                            console.error('There has been a problem with your Ajax operation:', error);
                        }
                    });
                }

                // Function to update the total amount based on selected checkboxes
                document.addEventListener("DOMContentLoaded", function () {
                    function updateTotal() {
                        let total = 0;
                        let totalQuantity = 0;
                        document.querySelectorAll('.product-checkbox:checked').forEach(checkbox => {
                            const price = parseFloat(checkbox.getAttribute('data-price'));
                            const discount = parseFloat(checkbox.getAttribute('data-discount')) || 0;
                            const quantity = parseInt(checkbox.getAttribute('data-quantity'), 10);
                            const discountedPrice = price * quantity * (1 - discount / 100);
                            total += discountedPrice;
                            totalQuantity += quantity;
                        });

                        document.getElementById('totalPriceDisplay').innerHTML = 'Tổng thanh toán: ' + total.toLocaleString() + ' VNĐ';
                        document.getElementById('totalAmount').value = total;
                        document.getElementById('totalQuantity').value = totalQuantity;
                        updateSelectedProducts();
                        updateSelectedTotalPrice();

                        // Disable the checkout button if total is 0
                        const checkoutButton = document.getElementById('checkoutButton');
                        if (total === 0) {
                            checkoutButton.disabled = true;
                            checkoutButton.style.opacity = 0.5;

                        } else {
                            checkoutButton.disabled = false;
                            checkoutButton.style.opacity = 1;
                        }
                    }
                    //chỗ này sẽ check và tạo ra 1 thẻ input để gửi các thẻ được chọn tới trang check out
                    function updateSelectedProducts() {
                        const container = document.getElementById('hiddenInputsContainer');
                        container.innerHTML = '';  // Clear existing inputs

                        document.querySelectorAll('.product-checkbox:checked').forEach(checkbox => {
                            const id = checkbox.getAttribute('data-id');
                            const input = document.createElement('input');
                            input.type = 'hidden';
                            input.name = 'selectedProducts';
                            input.value = id;
                            container.appendChild(input);
                        });
                    }

                    // check tổng tiền và gửi tới trang check out
                    function updateSelectedTotalPrice() {
                        const container = document.getElementById('hiddenTotalContainer');
                        container.innerHTML = '';  // Clear existing inputs

                        document.querySelectorAll('.product-checkbox:checked').forEach(checkbox => {
                            const id = checkbox.getAttribute('data-total-price');
                            const input = document.createElement('input');
                            input.type = 'hidden';
                            input.name = 'totalPrice';
                            input.value = id;
                            container.appendChild(input);
                        });
                    }

                    function checkTotalAmount() {
                        const totalAmount = parseFloat(document.getElementById('totalAmount').value);
                        if (totalAmount === 0) {
                            alert('Vui lòng chọn một sản phẩm');
                        } else {
                            // Submit the form to "checkout"
                            document.getElementById('cartForm').submit();
                        }
                    }

                    // Event listeners for checkboxes
                    document.querySelectorAll('.product-checkbox').forEach(checkbox => {
                        checkbox.addEventListener('change', function () {
                            updateTotal();  // Update total when checkbox state changes
                        });
                    });

                    // Ensure checkbox state is persisted
                    window.addEventListener('load', () => {
                        document.querySelectorAll('.product-checkbox').forEach(checkbox => {
                            const id = checkbox.getAttribute('data-id');
                            if (checkbox.checked) {
                                document.getElementById(`quantity${id}`).value = checkbox.getAttribute('data-quantity');
                            }
                        });
                        updateTotal();  // Initialize the total amount display
                    });
                });
            </script>
            <!-- jQuery -->
            <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
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
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html lang="en">
    <head>
        <!-- basic -->
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <!-- mobile metas -->
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="viewport" content="initial-scale=1, maximum-scale=1">
        <!-- site metas -->
        <title>The card shop - Đăng nhập</title>
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
        <!-- calendar file css -->
        <link rel="stylesheet" href="js/semantic.min.css" />
        <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
        <![endif]-->
        <style>
            .container {
                display: flex;
                flex-direction: column;
            }
            .styled-inputimg {
                width: 150px;
                height: 35px;
                border-radius: 25px;
            }

            .styled-input {
                width: 150px;
                height: 35px;
                padding: 10px 15px;
                font-size: 16px;
                border: 2px solid #ccc;
                border-radius: 25px;
                outline: none;
                transition: all 0.3s ease;
                margin-left: 5px;
                margin-top: 5px;
            }

            .styled-input:focus {
                border-color: #007BFF;
                box-shadow: 0 0 10px rgba(0, 123, 255, 0.5);
            }

            .styled-input::placeholder {
                color: #aaa;
            }
        </style>

        <%
            //Map<String, String> captchaHash = (HashMap<String, String>) request.getAttribute("map");
        %>
    </head>
    <body class="inner_page login">
        <div class="full_container">
            <div class="container">
                <div class="center verticle_center full_height">
                    <div class="login_section">
                        <div class="logo_login">
                            <div class="center">
                                <!--<img width="210" src="images/logo/logo.png" alt="#" />-->
                            </div>
                        </div>
                        <div class="login_form">
                            <h2 class="center" style="font-family: 'Arial', sans-serif;">Đăng nhập</h2><br/>
                            <form id="loginForm" action="LoginController" method="POST">
                                <input type="hidden" id="pid" name="pid" value="">
                                <fieldset>
                                    <div class="field">
                                        <label class="label_field">Tài khoản</label>
                                        <input type="text" name="username" value="${cookie.username.value}" placeholder="Nhập tài khoản" required="" />
                                    </div>
                                    <div class="field">
                                        <label class="label_field">Mật khẩu</label>
                                        <input type="password" name="password" value="${cookie.password.value}" placeholder="Nhập mật khẩu" required=""/>
                                        <div style="color: red;margin-left: 150px;margin-top: 5px">
                                            <c:if test="${not empty wrongAcc}">
                                                <script>
                                                    alert("${wrongAcc}");
                                                </script>
                                            </c:if>
                                        </div>
                                    </div>
                                    <div class="field" style="display: flex; justify-content: space-between; align-items: center;">
                                        <div>
                                            <label class="label_field">Captcha</label>
                                            <img id="captchaImage" class="styled-inputimg" src="captcha" alt="CAPTCHA Image"/>
                                            <input id="CaptchaInput" style="width:150px" class="styled-input" type="text" name="captcha" placeholder="Captcha" required/>
                                            <input id="idCaptcha" type="text" value="" name="idCaptcha" hidden>
                                            <div style="color: red;margin-left: 150px;margin-top: 5px">
                                                <c:if test="${not empty wrongCaptcha}">
                                                    ${wrongCaptcha}
                                                </c:if>
                                            </div>
                                        </div>
                                        <a href="javascript:void(0);" onclick="refreshCaptcha(); resetCaptchaNumber();"><i class="fa fa-refresh"></i> <span>Mã mới</span></a>
                                    </div>

                                    <div class="field">
                                        <label class="label_field hidden">hidden label</label>
                                        <label class="form-check-label">
                                            <input type="checkbox" class="form-check-input" ${cookie.crem.value != null ? 'checked' : ''} name="remember" value="true"> Ghi nhớ đăng nhập <br/>
                                        </label>

                                        <a class="forgot" href="register.jsp">Đăng kí tài khoản</a>
                                        <a class="forgot" href="requestchangepass.jsp">Quên mật khẩu?</a> 


                                    </div>
                                    <div class="field margin_0" style="margin-top: -50px;">
                                        <label class="label_field hidden">hidden label</label>
                                        <a href="home" style="align-content: center;">
                                            <i class="fa fa-home" style="font-size: 40px; align-content: center;" ></i>
                                        </a>
                                        <button class="main_bt" type="submit">Đăng nhập</button>
                                        <button class="main_bt" onclick="loginWithGoogle()"> Đăng nhập google </button>
                                    </div>
                                </fieldset>
                            </form>
                        </div>
                    </div>
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
        <!-- nice scrollbar -->
        <script src="js/perfect-scrollbar.min.js"></script>
        <script>
                                            var ps = new PerfectScrollbar('#sidebar');
        </script>
        <script>
            function resetCaptchaNumber() {
                document.getElementById('CaptchaInput').value = '';
            }
            function refreshCaptcha() {
                var xhr = new XMLHttpRequest();
                xhr.open("GET", "captcha", true);
                xhr.responseType = "blob";
                xhr.onload = function () {
                    if (this.status === 200) {
                        var blob = this.response;
                        var url = URL.createObjectURL(blob);
                        document.getElementById("captchaImage").src = url;
                        var captchaId = this.getResponseHeader("Captcha-Id");
                        document.getElementById("idCaptcha").value = captchaId;
                    }
                };
                xhr.send();
            }

            // Gọi hàm refreshCaptcha() khi tải trang để cập nhật CAPTCHA lần đầu tiên
            document.addEventListener("DOMContentLoaded", function () {
                refreshCaptcha();
            });

//            function refreshCaptcha() {
//                var captchaImage = document.getElementById("captchaImage");
//                captchaImage.src = "captcha?" + new Date().getTime();
//                //resetCaptchaNumber(); 
//            }

            function loginWithGoogle() {
                window.location.href = 'https://accounts.google.com/o/oauth2/auth?scope=profile%20email&redirect_uri=http://localhost:2069/TheCardWebsite/loginwithgooglehandler&response_type=code&client_id=511791771675-u007c4hhl3h7knf297cr1u9hqmaeo8j6.apps.googleusercontent.com';
            }
            function getUrlParameter(name) {
                name = name.replace(/[\[]/, '\\[').replace(/[\]]/, '\\]');
                var regex = new RegExp('[\\?&]' + name + '=([^&#]*)');
                var results = regex.exec(location.search);
                return results === null ? '' : decodeURIComponent(results[1].replace(/\+/g, ' '));
            }

            // Hàm để cập nhật giá trị pid vào input ẩn
            function updatePidInForm() {
                var pid = getUrlParameter('pid');
                document.getElementById('pid').value = pid;
            }

            // Gọi hàm cập nhật khi trang được load
            window.onload = function () {
                updatePidInForm();
            };
            document.getElementById('loginForm').addEventListener('submit', function () {
                updatePidInForm(); // Cập nhật giá trị pid trước khi submit
            });

        </script> 
        <!-- custom js -->
        <script src="js/custom.js"></script>
    </body>
</html>

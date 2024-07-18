<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
        <title>The Card Shop - Xác nhận Email</title>
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
                            <H2 class="center" style="font-family: 'Arial', sans-serif;">Xác thực OTP</H2></br>

                            <form action="verifyuserregisterservlet" method="post">
                                <div class="field" style="display: flex; justify-content: center; flex-direction: column; align-items: center;">
                                    <h5 style="text-align: center;">Vui lòng nhập mã số chúng tôi đã gửi cho bạn qua email ${email}.
                                        <br/> Mã xác thực có giá trị trong 60s</h5> <br/>
                                        <div class="countdown-container">
                                            <span id="countdown">60</span>
                                        </div>
                                    <input type="text" name="otp" placeholder="" required/>
                                    <input type="hidden" name="email" value="${email}"/>
                                    <input type="hidden" name="username" value="${username}"/>
                                    <input type="hidden" name="password" value="${password}"/>
                                </div>
                                <div class="field margin_0" style="display: flex; justify-content: center;">
                                    <button type="submit" class="main_bt">Xác nhận</button>
                                </div>
                                <div class="field margin_0" style="display: flex; justify-content: center;">
                                    <h6 style="color: orangered;">${invalidOTP}</h6>
                                </div>
                            </form>
                            <form action="resendemail" method="post">
                                <div class="field margin_top_50" style="justify-content: center; text-align: center;">
                                    <h6 style="margin-top: 30px;"> Chưa nhận được mã? 
                                        <input type="hidden" name="action" value="resend">
                                        <input type="hidden" name="mail" value="${email}">
                                        <button type="submit" style="background:none; border:none; color:blue; text-decoration:underline; cursor:pointer;">Gửi lại</button>
                                    </h6>
                                </div>
                                <div class="field margin_top_50" style="justify-content: center; text-align: center; ">
                                    <h6 style="margin-top: 30px; color: green;"> ${resentSuccess} </h6>
                                </div>
                            </form>
                            <div id="successPopup" style="display: none; position: fixed; top: 52%; left: 50%; background-color: #ffffff; transform: translate(-50%, -50%); padding: 20px; text-align: center;">
                                <!--<h2>Registration Successful!</h2>-->
                                <h3 style="height: 300px; width: 500px; margin-top: 50px;">${verifySuccess}</h3>
                                <button class="main_bt" onclick="redirectToLogin()">OK</button>
                            </div>

                            <script>
                                // Function to redirect to the login page
                                function redirectToLogin() {
                                    window.location.href = "login.jsp";
                                }

                                // Display the success popup if the message is present
                                document.addEventListener("DOMContentLoaded", function () {
                                    var verifySuccessMessage = "${verifySuccess}";
                                    if (verifySuccessMessage !== "") {
                                        document.getElementById("successPopup").style.display = "block";
                                    }
                                });
                            </script>
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
            window.onload = function () {
                let countdownNumber = 60;
                const countdownElement = document.getElementById('countdown');

                const countdown = setInterval(() => {
                    countdownNumber--;
                    countdownElement.textContent = countdownNumber;

                    if (countdownNumber <= 0) {
                        clearInterval(countdown);
                        countdownElement.textContent = "Hết giờ!";
                    }
                }, 1000);
            };
        </script>
        <!-- custom js -->
        <script src="js/custom.js"></script>
    </body>
</html>

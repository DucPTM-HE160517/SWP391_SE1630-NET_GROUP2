<%-- 
    Document   : chgpwd
    Created on : Sep 30, 2022, 11:20:44 PM
    Author     : ThuongTTHE163555
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="../base/headerImport.jsp" />
        <title>Hostalpy - Change Password</title>
        <style>

            .form-segment h2 {
                color: #fff;
            }

            .form-segment p {
                color: #fff;
                font-size: 16px;
            }

            .form-segment h1 {
                font-size: 24px;
                font-weight: 700;
            }

            .form-segment .btn {
                height: auto;
            }

            .form-segment .btn.btn-primary {
                background: var(--main-bg-color);
                font-weight: 500;
                border: 1px solid white;
                border-radius: 30px;
            }

            .form-segment .btn.btn-primary a {
                color: #fff;
                font-weight: bold;
            }

            .form-segment input {
                display: block;
                background: #F4F8F7;
                border: none;
                border-radius: 5px;
                font-size: 16px;
                height: 50px;
                width: 70%;
                padding: 0 30px;
                margin: 10px auto;
            }

            .form-segment input:focus-visible {
                border: none;
                outline: none;
            }

            .form-segment.register {
                height: 70%;
            }

            .form-segment input::placeholder {
                color: #ccc;
            }
        </style>
    </head>

    <body>
        <jsp:include page="../component/navbar.jsp" />
        <div class="container mt-[50px] mb-[100px]">
            <div class="form-segment shadow w-[30%] p-5 mx-auto rounded-2xl shadow-xl mt-5 border border-[#eee]" style="">
                <div class="text-center">
                    <h1 class="mb-5">Change Password</h1>
                    <form action="chgpwd" class="form-login" method="POST">
                        <input type="password" name="oldpassword" placeholder="Old Password" class="mt-10" minlength="3" maxlength="20" required/>
                        <input type="password" name="password" id="password" placeholder="New Password" minlength="3" maxlength="20" required/>
                        <input type="password" name="repassword" id="repassword"  placeholder="Re New Password" minlength="3" maxlength="20" required/>
                        <p id='message'></p>
                        <div class="mt-3"></div>
                        <p style="color: #575757">${requestScope.result}</p>
                        <button id="confirm-btn" class="btn btn-primary w-50 py-3 mt-5 disabled"><a type="submit">CHANGE</a></button>
                    </form>
                </div>
            </div>
        </div>
    <jsp:include page="../base/footer.jsp" />
    <script>
            $('#repassword').on('keyup', function () {
                if ($('#password').val() == $('#repassword').val()) {
                    $('#confirm-btn').removeClass("disabled");
                    $('#message').html('').css('color', 'green');
                } else {
                    $('#confirm-btn').addClass(" disabled");
                    $('#message').html('New Password Is Not Matching').css('color', 'red');
                }

            });
        </script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"
                integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p"
        crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js"
                integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF"
        crossorigin="anonymous"></script>
    </body>


</html>

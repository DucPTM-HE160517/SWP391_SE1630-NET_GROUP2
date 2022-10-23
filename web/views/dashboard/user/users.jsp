<%-- 
    Document   : host/properties
    Created on : Oct 9, 2022, 10:39:52 PM
    Author     : DucPTMHE160517
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <!--begin::Head-->
    <head>
        <jsp:include page="../base/headImport.jsp" />
        <style>

        </style>
    </head>
    <!-- end::Header -->
    <!--begin::Body-->
    <body id="kt_body" class="header-fixed header-tablet-and-mobile-fixed aside-fixed">
        <!--begin::Main-->
        <div class="d-flex flex-column flex-root">
            <!--begin::Page-->
            <div class="page d-flex flex-row flex-column-fluid">
                <jsp:include page="../component/aside.jsp" />
                <!--begin::Wrapper-->
                <div class="wrapper d-flex flex-column flex-row-fluid" id="kt_wrapper">
                    <jsp:include page="../component/header.jsp" />
                    <!--begin::Content-->
                    <div class="content d-flex flex-column flex-column-fluid fs-6" id="kt_content">
                        <!--begin::Container-->
                        <div class="container-fluid" id="kt_content_container">
                            <div class="table-responsive">
                                <table class="table">
                                    <thead>
                                        <tr class="fw-bold fs-6 text-gray-800">
                                            <th>#</th>
                                            <th>Avatar</th>
                                            <th>Name</th>
                                            <th>Username</th>
                                            <th>Phone</th>
                                            <th>Email</th>
                                            <th>Address</th>
                                            <th>Role</th>
                                            <th>Is Verify</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td>1</td>
                                            <td>
                                                <img 
                                                    src="${baseURL}/assets/images/avatar.jpg" 
                                                    class="rounded-circle"
                                                    height="45rem"/>
                                            </td>
                                            <td>Toti Pham</td>
                                            <td>totipham</td>
                                            <td>0123456789</td>
                                            <td>ducptm68@gmail.com</td>
                                            <td>Thạch Hoà, Thạch Thất, Hà Nội</td>
                                            <td>Renter</td>
                                            <td>True</td>
                                        </tr>
                                        <tr>
                                            <td>Garrett Winters</td>
                                            <td>Accountant</td>
                                            <td>Tokyo</td>
                                            <td>63</td>
                                            <td>2011/07/25</td>
                                            <td>$170,750</td>
                                            <td>63</td>
                                            <td>2011/07/25</td>
                                            <td>$170,750</td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <!--end::Container-->
                    </div>
                    <!--end::Content-->
                    <jsp:include page="../component/footer.jsp" />
                </div>
                <!--end::Wrapper-->
            </div>
            <!--end::Page-->
        </div>
        <!--end::Main-->

        <!--end::Drawers-->
        <jsp:include page="../component/scrolltop.jsp" />
        <jsp:include page="../base/footImport.jsp" />

        <script>
            function askToDelete(pid) {
                if (confirm("Are you sure to delete this property ?") == true) {
                    window.location.replace("${baseURL}/dashboard/deleteproperty?pid=" + pid)
                }
            }

        </script>
    </body>
    <!--end::Body-->

</html>

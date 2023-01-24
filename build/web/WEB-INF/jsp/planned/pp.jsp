<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <style>
            body {
                font-family: "Lato", sans-serif;
            }

            .sidenav {
                height: 100%;
                width: 160px;
                position: fixed;
                z-index: 1;
                top: 0;
                left: 0;
                background-color: green;
                overflow-x: hidden;
                padding-top: 20px;
            }



            .sidenav button:hover {
                background-color: red;
            }

            .main {
                margin-left: 160px; /* Same as the width of the sidenav */
                font-size: 28px; /* Increased text to enable scrolling */
                padding: 0px 10px;
            }

            @media screen and (max-height: 450px) {
                .sidenav {padding-top: 15px;}
                .sidenav a {font-size: 18px;}
            }

            /* ---------------------------------------------------
            for sticky header for table
            -----------------------------------------------------*/
            /* Fixed Headers */

            th {
                text-align: center;
                position: -webkit-sticky;
                position: sticky;
                top: 40px;
                z-index: 2;

            }

            th[scope=row] {
                position: -webkit-sticky;
                position: sticky;
                left: 0;
                z-index: 1;
            }

            th[scope=row] {
                vertical-align: top;
                color: inherit;
                background-color: inherit;
                background: linear-gradient(90deg, transparent 0%, transparent calc(100% - .05em), #d6d6d6 calc(100% - .05em), #d6d6d6 100%);
            }
            th {
                vertical-align: bottom;
                background-color: blue;
                color: #fff;
                border:solid black 1px;
            }

            tr td {
                border:solid black 1px;

            }


            .navbar {
                overflow: hidden;
                background-color: #333;
                position: fixed;
                top: 0;
                width: 100%;
            }

            .navbar a {
                float: left;
                display: block;
                color: #f2f2f2;
                text-align: center;
                padding: 14px 16px;
                text-decoration: none;
                font-size: 17px;
            }

            .navbar a:hover {
                background: #ddd;
                color: black;
            }
        </style>
    </head>
    <body>

        <div class="sidenav">

            <c:forEach var="routeNumber" items="${routeNumbers}"  >
                <center>
                    <button style='font-size: 20px; width:60px; border-radius: 50%;' class="btn btn-warning" onclick="ajax(${routeNumber})"> ${routeNumber}</button>
                </center>
                <br>

            </c:forEach>

        </div>
        <!-- As a heading -->
        <div class="navbar">
            <a href="#home">Home</a>
            <a href="#news">News</a>
            <a href="#contact">Contact</a>
        </div>

        <div class="main">
            <table id="mainTable" >
                <thead>

                    <tr>
                        <th>გასვლის დრო</th>
                        <th>მიმართულება</th>
                        <th>მისვლის დრო</th>
                    </tr>
                </thead>
                <tbody id="body">
                    <tr>
                        <td colspan='16' align="center">
                            მარშრუტ #: ${plannedRoutes.number}
                        </td>
                    </tr>
                    <c:forEach var="day" items="${plannedRoutes.days}">
                        <tr style="background-color:#4863A0">
                            <td colspan='3' align="center">
                                თარიღი: ${day.value.getDateStamp()}
                            </td>
                        </tr>
                        <c:forEach var="exodus" items="${day.value.exoduses}">
                            <tr style="background-color:lightblue">
                                <td colspan='16' align="center">
                                    გასვლა #: ${exodus.value.number}
                                </td>
                            </tr>
                            <c:forEach var="tripVoucher" items="${exodus.value.tripVouchers}">

                                <tr>
                                    <td colspan='3' align="center">
                                        მარშრუტა #: ${plannedRoutes.number}.
                                        თარიღი: ${day.value.dateStamp}.
                                        გასვლა #: ${exodus.value.number}.
                                        საგზურის  #: ${tripVoucher.value.number}.

                                    </td>
                                </tr>
                                <c:forEach var="tripPeriod" items="${tripVoucher.value.tripPeriods}">
                                    <tr>


                                        <td align="center">${tripPeriod.getStartTimeString()} </td>
                                        <td align="center">${tripPeriod.typeG} </td>
                                        <td align="center">${tripPeriod.getArrivalTimeString()} </td>

                                    </tr>
                                </c:forEach>
                            </c:forEach>
                        </c:forEach>
                    </c:forEach>
                </tbody>
            </table> 
        </div>
        <!-- jQuery CDN -  -->
        <script type="text/javascript" src="http://code.jquery.com/jquery-1.10.1.min.js"></script>

        <script type="text/javascript">
                        $(document).ready(function () {
                            $("#sidebar").mCustomScrollbar({
                                theme: "minimal"
                            });
                            $('#sidebarCollapse').on('click', function () {
                                $('#sidebar, #content').toggleClass('active');
                                $('.collapse.in').toggleClass('in');
                                $('a[aria-expanded=true]').attr('aria-expanded', 'false');
                            });
                        });
                        //------------------------------------------------------

                        function ajax(routeNumber) {
                            $("#body").html("");
                            $.ajax({
                                url: 'requestRoute.htm?number=' + routeNumber,
                                //  contentType: "application/x-www-form-urlencoded;charset=UTF-8",

                                success: function (status) {
                                    $("#body").html(status);
                                    console.log(status)
                                }
                            });
                        }

        </script> 
    </body>
</html> 


<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="Analysis.DriverAnalys"%>
<%@page import="java.util.Date"%>
<%@page import="BasicModels.TripVoucher"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.TreeMap"%>
<%@page import="java.util.Map"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>DDDDDDDDDDD</title>
        <!-- Bootstrap CSS CDN -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css" integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4" crossorigin="anonymous">

        <!-- Scrollbar Custom CSS -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/malihu-custom-scrollbar-plugin/3.1.5/jquery.mCustomScrollbar.min.css">

        <!-- Font Awesome JS -->
        <script defer src="https://use.fontawesome.com/releases/v5.0.13/js/solid.js" integrity="sha384-tzzSw1/Vo+0N5UhStP3bvwWPq+uvzCMfrN1fEFe+xBmv1C/AtVX5K0uZtmcHitFZ" crossorigin="anonymous"></script>
        <script defer src="https://use.fontawesome.com/releases/v5.0.13/js/fontawesome.js" integrity="sha384-6OIrr52G08NpOFSZdxxz1xdNSndlD4vdcf/q2myIUVO0VsqaGHJsB0RaBE01VTOY" crossorigin="anonymous"></script>
        <style>

            /*
        DEMO STYLE
            */

            @import "https://fonts.googleapis.com/css?family=Poppins:300,400,500,600,700";
            body {
                font-family: 'Poppins', sans-serif;
                background: #fafafa;
            }

            p {
                font-family: 'Poppins', sans-serif;
                font-size: 1.1em;
                font-weight: 300;
                line-height: 1.7em;
                color: #999;
            }

            a,
            a:hover,
            a:focus {
                color: inherit;
                text-decoration: none;
                transition: all 0.3s;
            }

            .navbar {
                padding: 0px 0px;
                background: #fff;
                border: none;
                border-radius: 0;
                margin-bottom: 0px;
                box-shadow: 1px 1px 3px rgba(0, 0, 0, 0.1);
            }

            .navbar-btn {
                box-shadow: none;
                outline: none !important;
                border: none;
            }

            .line {
                width: 100%;
                height: 1px;
                border-bottom: 1px dashed #ddd;
                margin: 40px 0;
            }

            /* ---------------------------------------------------
                SIDEBAR STYLE
            ----------------------------------------------------- */

            .wrapper {
                display: flex;
                width: 100%;
            }

            #sidebar {
                width: 150px;
                position: fixed;
                top: 0;
                left: 0;
                height: 100vh;
                z-index: 999;
                background: #7386D5;
                color: #fff;
                transition: all 0.3s;
            }

            #sidebar.active {
                margin-left: -150px;
            }

            #sidebar .sidebar-header {
                padding: 20px;
                background: #6d7fcc;
            }

            #sidebar ul.components {
                padding: 20px 0;
                border-bottom: 1px solid #47748b;
            }

            #sidebar ul p {
                color: #fff;
                padding: 10px;
            }

            #sidebar ul li a {
                padding: 10px;
                font-size: 1.1em;
                display: block;
            }

            #sidebar ul li a:hover {
                color: #7386D5;
                background: #fff;
            }

            #sidebar ul li.active>a,
            a[aria-expanded="true"] {
                color: #fff;
                background: #6d7fcc;
            }

            a[data-toggle="collapse"] {
                position: relative;
            }

            .dropdown-toggle::after {
                display: block;
                position: absolute;
                top: 50%;
                right: 20px;
                transform: translateY(-50%);
            }

            ul ul a {
                font-size: 0.9em !important;
                padding-left: 30px !important;
                background: #6d7fcc;
            }

            ul.CTAs {
                padding: 20px;
            }

            ul.CTAs a {
                text-align: center;
                font-size: 0.9em !important;
                display: block;
                border-radius: 5px;
                margin-bottom: 5px;
            }

            a.download {
                background: #fff;
                color: #7386D5;
            }

            a.article,
            a.article:hover {
                background: #6d7fcc !important;
                color: #fff !important;
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

            /* ---------------------------------------------------
                CONTENT STYLE
            ----------------------------------------------------- */

            #content {
                width: calc(100% - 150px);
                padding: 5px;
                min-height: 100vh;
                transition: all 0.3s;
                position: absolute;
                top: 0;
                right: 0;
            }

            #content.active {
                width: 100%;
            }

            /* ---------------------------------------------------
                MEDIAQUERIES
            ----------------------------------------------------- */

            @media (max-width: 768px) {
                #sidebar {
                    margin-left: -150px;
                }
                #sidebar.active {
                    margin-left: 0;
                }
                #content {
                    width: 100%;
                }
                #content.active {
                    width: calc(100% - 150px);
                }
                #sidebarCollapse span {
                    display: none;
                }
            }
            /* ---------------------------------------------------
                          
            ----------------------------------------------------- */
            input[type="checkbox"]{
                width: 20px; /*Desired width*/
                height: 20px; /*Desired height*/
            }

            tr td {
                border:solid black 1px;

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

            /* -----------------
           pagination
            
            --------------------*/
            .pagination {
                display: inline-block;
            }

            .pagination a {
                color: black;
                float: left;
                padding: 8px 16px;
                text-decoration: none;
                transition: background-color .3s;
                border: 1px solid #ddd;
            }

            .pagination a.active {
                width:100px;
                background-color: #4CAF50;
                color: white;
                border: 1px solid #4CAF50;
            }

            .pagination a:hover:not(.active) {background-color: #ddd;}


        </style>
        <script type="text/javascript"
        src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
    </head>
    <body>




        <!-- Page Content  -->
        <div id="content">

            <nav class="navbar navbar-expand-lg navbar-light bg-light sticky-top">
                <div class="container-fluid">
                    <div></div>
                    <center>
                        <h2>DDDDDD</h2>
                    </center>

                    <button type="button" class="btn btn-warning">
                        <span> <a href="plannedRoutesExcelExportDashboard.htm">ექსელში ექსპორტი</a> </span>
                    </button>

                </div>
            </nav>

            <table id="mainTable" >
                <thead>
                    <tr>
                        <th>Driver Nmber</th>
                        <th>Driver Name</th>
                    </tr>
                </thead>



                <tbody>
                    <%
                        TreeMap<Integer, DriverAnalys> driversAnalysis = (TreeMap) request.getAttribute("driversAnalysis");

                        DriverAnalys da = driversAnalysis.firstEntry().getValue();
                        TreeMap<Date, TripVoucher> tp = da.getTripVouchers();
                        out.println("<tr>");
                        out.println("<td>");
                        out.println("");
                        out.println("</td>");
                        out.println("<td>");
                        out.println("");
                        out.println("</td>");
                        for (Map.Entry<Date, TripVoucher> tripVoucherEntrySet : tp.entrySet()) {
                            Date date = tripVoucherEntrySet.getKey();
                            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                            String formattedDate = dateFormat.format(date);
                            out.println("<td>");
                            out.println(formattedDate);
                            out.println("</td>");
                        }
                        out.println("</tr>");
                        for (Map.Entry<Integer, DriverAnalys> driversAnalysisEntrySet : driversAnalysis.entrySet()) {

                            out.println("<tr>");

                            out.println("<td>");
                            out.println(driversAnalysisEntrySet.getKey());
                            out.println("</td>");

                            out.println("<td>");
                            out.println(driversAnalysisEntrySet.getValue().getDriverName());
                            out.println("</td>");

                            DriverAnalys driverAnalys = driversAnalysisEntrySet.getValue();
                            TreeMap<Date, TripVoucher> tripVouchers = driverAnalys.getTripVouchers();
                            for (Map.Entry<Date, TripVoucher> tripVoucherEntrySet : tripVouchers.entrySet()) {
                                TripVoucher tripVoucher = tripVoucherEntrySet.getValue();
                                if (tripVoucher == null) {
                                    out.println("<td>");
                                    out.println("");
                                    out.println("</td>");
                                } else {
                                    out.println("<td>");
                                    out.println(tripVoucher.getBaseLeavingTimeActualString()
                                            + "<br>" + tripVoucher.getBaseReturnTimeVerifiedString()
                                            + "<br>" + tripVoucher.getWorkedHoursVerifiedString());
                                    out.println("</td>");
                                }

                            }

                            out.println("</tr>");
                        }
                    %>
                </tbody>
            </table>

            <hr>

        </div>
    </div>

    <!-- jQuery CDN -  -->
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
    <!-- Popper.JS -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js" integrity="sha384-cs/chFZiN24E4KMATLdqdvsezGxaGsi4hLGOzlXwp5UZB1LY//20VyM2taTB4QvJ" crossorigin="anonymous"></script>
    <!-- Bootstrap JS -->
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js" integrity="sha384-uefMccjFJAIv6A+rW+L4AHf99KvxDjWSu1z9VI8SKNVmz4sk7buKt/6v9KI65qnm" crossorigin="anonymous"></script>
    <!-- jQuery Custom Scroller CDN -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/malihu-custom-scrollbar-plugin/3.1.5/jquery.mCustomScrollbar.concat.min.js"></script>

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
            $("#body").html("<tr><td colspan='5'><center><h3> დაელოდე, მონაცემები იტვირთება...</h3></center></td></tr>")
            $.ajax({
                url: 'requestPlannedRoute.htm?number=' + routeNumber,
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

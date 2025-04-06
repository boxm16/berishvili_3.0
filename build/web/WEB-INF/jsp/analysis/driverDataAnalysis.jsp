<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Driver Data Analysis</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                margin: 20px;
                line-height: 1.6;
            }
            h1 {
                color: #2c3e50;
                margin-bottom: 20px;
                text-align: center;
            }
            .table-container {
                width: 100%;
                margin: 0 auto;
                max-height: 600px; /* Adjust this value based on your needs */
                overflow-y: auto;
                box-shadow: 0 0 10px rgba(0,0,0,0.1);
            }
            .driver-table {
                width: 100%;
                border-collapse: collapse;
            }
            .driver-table th {
                background-color: #3498db;
                color: white;
                padding: 12px;
                text-align: left;
                position: sticky;
                top: 0;
                z-index: 10;
            }
            .driver-table td {
                padding: 10px;
                border-bottom: 1px solid #ddd;
            }
            .driver-table tr:nth-child(even) {
                background-color: #f2f2f2;
            }
            .driver-table tr:hover {
                background-color: #e6f7ff;
            }
            .table-number {
                width: 15%;
                text-align: center;
            }
            .driver-name {
                width: 85%;
            }
            .no-drivers {
                text-align: center;
                padding: 20px;
                color: #666;
            }
        </style>
    </head>
    <body>
    <center>   <h3>Driver Data Analysis</h3></center>

    <c:choose>
        <c:when test="${not empty driversData}">
            <div class="table-container">
                <table class="driver-table">
                    <thead>
                        <tr>
                            <th class="table-number">Table Number</th>
                            <th class="driver-name">Driver Name</th>
                            <th class="driver-name">Base Number</th>
                            <th class="driver-name">Status</th>
                            <th class="driver-name">Period</th>
                            <th class="driver-name">Bus Type</th>

                            <th class="driver-name">Bus Number</th>

                            <th class="driver-name">Leave Type</th>
                            <th class="driver-name">Leave Status</th>
                            <th class="driver-name">Leave Period</th>

                            <c:forEach begin="1" end="31" var="i">
                                <th class="driver-name">Day ${i}</th>
                                </c:forEach>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${driversData}" var="driverEntry">
                            <tr>
                                <td class="table-number">${driverEntry.value.tabelNumber}</td>
                                <td class="driver-name">${driverEntry.value.name}</td>
                                <td class="driver-name">${driverEntry.value.baseNumber}</td>
                                <td class="driver-name">${driverEntry.value.status}</td>
                                <td class="driver-name">${driverEntry.value.period}</td>
                                <td class="driver-name">${driverEntry.value.busType}</td>

                                <td class="driver-name">${driverEntry.value.busNumber}</td>

                                <td class="driver-name">${driverEntry.value.leaveType}</td>
                                <td class="driver-name">${driverEntry.value.leaveStatus}</td>
                                <td class="driver-name">${driverEntry.value.leavePeriod}</td>

                                <c:forEach items="${driverEntry.value.driverDataAnalysisHolders}" var="entry">
                                    <td class="driver-name">${entry.value.workingHours}/${entry.value.nariadi}</td>
                                </c:forEach>

                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:when>
        <c:otherwise>
            <div class="no-drivers">
                <p>No driver data available.</p>
            </div>
        </c:otherwise>
    </c:choose>
</body>
</html>
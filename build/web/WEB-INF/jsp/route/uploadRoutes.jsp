<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>მარშრუტების ატვირთვა</title>

        <style>
            table, tr, td, th {
                border: 1px solid black;
                border-collapse: collapse;
            }
        </style>
    </head>
    <body>

    <center>
        <a href="index.htm">საწყის გვერდზე გადასვლა</a>
        <hr>
        <h1>მარშრუტების ატვირთვა</h1>
        <hr>
        <div>
            <h2>ექსელის ფაილი უნდა იყოს შედგენილი შემდეგი ფორმით</h2>
            <table>
                <tr>
                    <th>მარშრუტის ნომერი</th>
                    <th>მარშრუტის სქემა</th>
                    <th>პუნქტი "A"</th>
                    <th>პუნქტი "B"</th>
                </tr>
                <tbody>
                    <tr>
                        <td>
                            101
                        </td>
                        <td>
                            XXXXXXXXXX
                        </td>
                        <td>
                            AAAAAA
                        </td>
                        <td>
                            BBBBBB
                        </td>
                    </tr>
                    <tr>
                        <td>
                            102
                        </td>
                        <td>
                            XXXXXXXXXX
                        </td>
                        <td>
                            AAAAAA                        
                        </td>
                        <td>
                            BBBBBB
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        <hr>
        <h2> <a href="downloadRoutes.htm">არსებული მარშრუტების ჩამოტვირთვა</a></h2>
        <hr>
        <h2>შეარჩიეთ ასატვირთი ფაილი: </h2>
        <h2 style="color:red">${uploadStatus}<br>${errorMessage}</h2>
        <form action="uploadRoutes.htm" method="post" enctype="multipart/form-data">  
            <input type="file" name="file"/>  
            <input type="submit" value="ატვირთვა"/>  
        </form>  
    </center>
</body>
</html>
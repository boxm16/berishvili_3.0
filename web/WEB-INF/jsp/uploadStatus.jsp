<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Upload Status</title>
        <script type="text/javascript"
        src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
        <script type="text/javascript">
            function crunchifyAjax() {
                $.ajax({
                    url: 'uploadStatusAjax.htm',
                    success: function (data) {
                        const myArray = data.split(":");
                        let status = myArray[1];
                        if (status === 'completed') {
                            window.location.href = 'index.htm';
                        } else {
                            $('#result').html(data);
                        }
                    }
                });
            }
        </script>
        <script type="text/javascript">
            var intervalId = 0;
            intervalId = setInterval(crunchifyAjax, 1000);
        </script>
    </head>
    <body>
        <div align="center">
            <center>
                <h1>Upload Status</h1>
                <br> <br> ${message} <br> <br>
                <h1>  <div id="result"></div></h1>
            </center>
        </div>
    </body>
</html>

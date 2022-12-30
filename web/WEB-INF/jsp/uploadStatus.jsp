<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Upload Status</title>
        <script type="text/javascript"
        src="http://code.jquery.com/jquery-1.10.1.min.js"></script>

    </head>
    <body>
        <div align="center">
            <center>
                <h1>Upload Status</h1>
                <br> <br> ${message} <br> <br>
                <h1>  <div id="result"></div></h1>
            </center>
        </div>
        <script type="text/javascript">
            var intervalId = 0;
            intervalId = setInterval(crunchifyAjax, 1000);
            var sec=0;
            function crunchifyAjax() {
                $.ajax({
                    url: 'uploadStatusAjax.htm',
                    success: function (status) {
                        if (status === 'completed') {
                            window.location.href = 'index.htm';
                        } else {
                            $('#result').html(sec+"<hr>"+status);
                        }
                    }
                });
                sec++;
            }
        </script>

    </body>
</html>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Tech Man</title>
    </head>
    <body>
    <center>
        <h1><a href='index.htm'>INDEX</a></h1>
        <hr>
        <h1>TechMan</h1>
        <h2>Host Name: ${hostName}</h2>
        <hr>
        <h2>
            'Uploads' Directory Exists : ${uploadsDirectoryExists}
            <br>
            <a href="createUploadDirecotry.htm">Create Upload Directory</a>

        </h2>
        <hr>
        <h1>Database Manipulations</h1>
        <h2>
            <a href="createDatabaseSchema.htm">Create  Database Schema</a>
            <br>
            ${databaseSchemaCreationStatus}
            <br>
            <a href="createDatabaseTables.htm">Create  Database Tables</a>
            <br>
            ${databaseTablesCreationStatus}
            <hr>
            <a href="deleteDatabaseTables.htm">Delete  Database Tables</a>
            <br>
            ${databaseTablesDeletionStatus}



        </h2>
    </center>
</body>
</html>

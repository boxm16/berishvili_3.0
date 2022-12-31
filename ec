[33mcommit 8a59d2116410a635e154aa82744495bcc590c94c[m[33m ([m[1;36mHEAD -> [m[1;32mmaster[m[33m)[m
Author: Michail Sitmalidis <boxm16@yahoo.com>
Date:   Fri Dec 30 22:50:48 2022 +0200

    Upload Thread Added. Still need work

[1mdiff --git a/build/web/WEB-INF/classes/ExcelReading/ExcelReader$1.class b/build/web/WEB-INF/classes/ExcelReading/ExcelReader$1.class[m
[1mnew file mode 100644[m
[1mindex 0000000..fad613b[m
Binary files /dev/null and b/build/web/WEB-INF/classes/ExcelReading/ExcelReader$1.class differ
[1mdiff --git a/build/web/WEB-INF/classes/ExcelReading/ExcelReader$SheetHandler$LruCache.class b/build/web/WEB-INF/classes/ExcelReading/ExcelReader$SheetHandler$LruCache.class[m
[1mnew file mode 100644[m
[1mindex 0000000..1886980[m
Binary files /dev/null and b/build/web/WEB-INF/classes/ExcelReading/ExcelReader$SheetHandler$LruCache.class differ
[1mdiff --git a/build/web/WEB-INF/classes/ExcelReading/ExcelReader$SheetHandler.class b/build/web/WEB-INF/classes/ExcelReading/ExcelReader$SheetHandler.class[m
[1mnew file mode 100644[m
[1mindex 0000000..ba82dde[m
Binary files /dev/null and b/build/web/WEB-INF/classes/ExcelReading/ExcelReader$SheetHandler.class differ
[1mdiff --git a/build/web/WEB-INF/classes/ExcelReading/ExcelReader.class b/build/web/WEB-INF/classes/ExcelReading/ExcelReader.class[m
[1mnew file mode 100644[m
[1mindex 0000000..7df0740[m
Binary files /dev/null and b/build/web/WEB-INF/classes/ExcelReading/ExcelReader.class differ
[1mdiff --git a/build/web/WEB-INF/classes/Uploading/PlannedDataUploadController.class b/build/web/WEB-INF/classes/Uploading/PlannedDataUploadController.class[m
[1mindex f8e5574..8698b33 100644[m
Binary files a/build/web/WEB-INF/classes/Uploading/PlannedDataUploadController.class and b/build/web/WEB-INF/classes/Uploading/PlannedDataUploadController.class differ
[1mdiff --git a/build/web/WEB-INF/classes/Uploading/PlannedDataUploadThread.class b/build/web/WEB-INF/classes/Uploading/PlannedDataUploadThread.class[m
[1mnew file mode 100644[m
[1mindex 0000000..aea0f77[m
Binary files /dev/null and b/build/web/WEB-INF/classes/Uploading/PlannedDataUploadThread.class differ
[1mdiff --git a/build/web/WEB-INF/classes/Uploading/UploadStatusAjaxController.class b/build/web/WEB-INF/classes/Uploading/UploadStatusAjaxController.class[m
[1mindex 1fbeb1d..65ebf00 100644[m
Binary files a/build/web/WEB-INF/classes/Uploading/UploadStatusAjaxController.class and b/build/web/WEB-INF/classes/Uploading/UploadStatusAjaxController.class differ
[1mdiff --git a/build/web/WEB-INF/jsp/uploadStatus.jsp b/build/web/WEB-INF/jsp/uploadStatus.jsp[m
[1mindex 99f3545..f313132 100644[m
[1m--- a/build/web/WEB-INF/jsp/uploadStatus.jsp[m
[1m+++ b/build/web/WEB-INF/jsp/uploadStatus.jsp[m
[36m@@ -6,34 +6,34 @@[m
         <title>Upload Status</title>[m
         <script type="text/javascript"[m
         src="http://code.jquery.com/jquery-1.10.1.min.js"></script>[m
[32m+[m
[32m+[m[32m    </head>[m
[32m+[m[32m    <body>[m
[32m+[m[32m        <div align="center">[m
[32m+[m[32m            <center>[m
[32m+[m[32m                <h1>Upload Status</h1>[m
[32m+[m[32m                <br> <br> ${message} <br> <br>[m
[32m+[m[32m                <h1>  <div id="result"></div></h1>[m
[32m+[m[32m            </center>[m
[32m+[m[32m        </div>[m
         <script type="text/javascript">[m
[32m+[m[32m            var intervalId = 0;[m
[32m+[m[32m            intervalId = setInterval(crunchifyAjax, 1000);[m
[32m+[m[32m            var sec=0;[m
             function crunchifyAjax() {[m
                 $.ajax({[m
                     url: 'uploadStatusAjax.htm',[m
[31m-                    success: function (data) {[m
[31m-                        const myArray = data.split(":");[m
[31m-                        let status = myArray[1];[m
[32m+[m[32m                    success: function (status) {[m
                         if (status === 'completed') {[m
                             window.location.href = 'index.htm';[m
                         } else {[m
[31m-                            $('#result').html(data);[m
[32m+[m[32m                            $('#result').html(sec+"<hr>"+status);[m
                         }[m
                     }[m
                 });[m
[32m+[m[32m                sec++;[m
             }[m
         </script>[m
[31m-        <script type="text/javascript">[m
[31m-            var intervalId = 0;[m
[31m-            intervalId = setInterval(crunchifyAjax, 1000);[m
[31m-        </script>[m
[31m-    </head>[m
[31m-    <body>[m
[31m-        <div align="center">[m
[31m-            <center>[m
[31m-                <h1>Upload Status</h1>[m
[31m-                <br> <br> ${message} <br> <br>[m
[31m-                <h1>  <div id="result"></div></h1>[m
[31m-            </center>[m
[31m-        </div>[m
[32m+[m
     </body>[m
 </html>[m
[1mdiff --git a/build/web/WEB-INF/lib/commons-compress-1.20.jar b/build/web/WEB-INF/lib/commons-compress-1.20.jar[m
[1mnew file mode 100644[m
[1mindex 0000000..17c1c7b[m
Binary files /dev/null and b/build/web/WEB-INF/lib/commons-compress-1.20.jar differ
[1mdiff --git a/build/web/WEB-INF/lib/poi-5.0.0.jar b/build/web/WEB-INF/lib/poi-5.0.0.jar[m
[1mnew file mode 100644[m
[1mindex 0000000..d239022[m
Binary files /dev/null and b/build/web/WEB-INF/lib/poi-5.0.0.jar differ
[1mdiff --git a/build/web/WEB-INF/lib/poi-ooxml-5.0.0.jar b/build/web/WEB-INF/lib/poi-ooxml-5.0.0.jar[m
[1mnew file mode 100644[m
[1mindex 0000000..574e6c1[m
Binary files /dev/null and b/build/web/WEB-INF/lib/poi-ooxml-5.0.0.jar differ
[1mdiff --git a/build/web/WEB-INF/lib/poi-ooxml-full-5.0.0.jar b/build/web/WEB-INF/lib/poi-ooxml-full-5.0.0.jar[m
[1mnew file mode 100644[m
[1mindex 0000000..1966707[m
Binary files /dev/null and b/build/web/WEB-INF/lib/poi-ooxml-full-5.0.0.jar differ
[1mdiff --git a/build/web/WEB-INF/lib/xmlbeans-4.0.0.jar b/build/web/WEB-INF/lib/xmlbeans-4.0.0.jar[m
[1mnew file mode 100644[m
[1mindex 0000000..7744826[m
Binary files /dev/null and b/build/web/WEB-INF/lib/xmlbeans-4.0.0.jar differ
[1mdiff --git a/nbproject/build-impl.xml b/nbproject/build-impl.xml[m
[1mindex ea69284..b6d6a2f 100644[m
[1m--- a/nbproject/build-impl.xml[m
[1m+++ b/nbproject/build-impl.xml[m
[36m@@ -986,6 +986,11 @@[m [mexists or setup the property manually. For example like this:[m
         <copyfiles files="${libs.jstl.classpath}" iftldtodir="${build.web.dir}/WEB-INF" todir="${dist.ear.dir}/lib"/>[m
         <copyfiles files="${file.reference.commons-fileupload-1.3.3.jar}" iftldtodir="${build.web.dir}/WEB-INF" todir="${dist.ear.dir}/lib"/>[m
         <copyfiles files="${file.reference.commons-io-2.6.jar}" iftldtodir="${build.web.dir}/WEB-INF" todir="${dist.ear.dir}/lib"/>[m
[32m+[m[32m        <copyfiles files="${file.reference.poi-5.0.0.jar}" iftldtodir="${build.web.dir}/WEB-INF" todir="${dist.ear.dir}/lib"/>[m
[32m+[m[32m        <copyfiles files="${file.reference.commons-compress-1.20.jar}" iftldtodir="${build.web.dir}/WEB-INF" todir="${dist.ear.dir}/lib"/>[m
[32m+[m[32m        <copyfiles files="${file.reference.xmlbeans-4.0.0.jar}" iftldtodir="${build.web.dir}/WEB-INF" todir="${dist.ear.dir}/lib"/>[m
[32m+[m[32m        <copyfiles files="${file.reference.poi-ooxml-full-5.0.0.jar}" iftldtodir="${build.web.dir}/WEB-INF" todir="${dist.ear.dir}/lib"/>[m
[32m+[m[32m        <copyfiles files="${file.reference.poi-ooxml-5.0.0.jar}" iftldtodir="${build.web.dir}/WEB-INF" todir="${dist.ear.dir}/lib"/>[m
         <mkdir dir="${build.web.dir}/META-INF"/>[m
         <manifest file="${build.web.dir}/META-INF/MANIFEST.MF" mode="update"/>[m
     </target>[m
[36m@@ -994,6 +999,11 @@[m [mexists or setup the property manually. For example like this:[m
         <copyfiles files="${libs.jstl.classpath}" todir="${build.web.dir}/WEB-INF/lib"/>[m
         <copyfiles files="${file.reference.commons-fileupload-1.3.3.jar}" todir="${build.web.dir}/WEB-INF/lib"/>[m
         <copyfiles files="${file.reference.commons-io-2.6.jar}" todir="${build.web.dir}/WEB-INF/lib"/>[m
[32m+[m[32m        <copyfiles files="${file.reference.poi-5.0.0.jar}" todir="${build.web.dir}/WEB-INF/lib"/>[m
[32m+[m[32m        <copyfiles files="${file.reference.commons-compress-1.20.jar}" todir="${build.web.dir}/WEB-INF/lib"/>[m
[32m+[m[32m        <copyfiles files="${file.reference.xmlbeans-4.0.0.jar}" todir="${build.web.dir}/WEB-INF/lib"/>[m
[32m+[m[32m        <copyfiles files="${file.reference.poi-ooxml-full-5.0.0.jar}" todir="${build.web.dir}/WEB-INF/lib"/>[m
[32m+[m[32m        <copyfiles files="${file.reference.poi-ooxml-5.0.0.jar}" todir="${build.web.dir}/WEB-INF/lib"/>[m
     </target>[m
     <target depends="init" if="dist.ear.dir" name="-clean-webinf-lib">[m
         <delete dir="${build.web.dir}/WEB-INF/lib"/>[m
[1mdiff --git a/nbproject/genfiles.properties b/nbproject/genfiles.properties[m
[1mindex 44b648c..e7beb80 100644[m
[1m--- a/nbproject/genfiles.properties[m
[1m+++ b/nbproject/genfiles.properties[m
[36m@@ -1,8 +1,8 @@[m
[31m-build.xml.data.CRC32=1e64e61a[m
[32m+[m[32mbuild.xml.data.CRC32=baab6cdd[m
 build.xml.script.CRC32=f1a67363[m
 build.xml.stylesheet.CRC32=1707db4f@1.84.0.1[m
 # This file is used by a NetBeans-based IDE to track changes in generated files such as build-impl.xml.[m
 # Do not edit this file. You may delete it but then the IDE will never regenerate such files for you.[m
[31m-nbproject/build-impl.xml.data.CRC32=1e64e61a[m
[31m-nbproject/build-impl.xml.script.CRC32=1f8c24a4[m
[32m+[m[32mnbproject/build-impl.xml.data.CRC32=baab6cdd[m
[32m+[m[32mnbproject/build-impl.xml.script.CRC32=3e9ac38d[m
 nbproject/build-impl.xml.stylesheet.CRC32=334708a0@1.84.0.1[m
[1mdiff --git a/nbproject/project.properties b/nbproject/project.properties[m
[1mindex ba1e692..4798bbf 100644[m
[1m--- a/nbproject/project.properties[m
[1m+++ b/nbproject/project.properties[m
[36m@@ -28,8 +28,13 @@[m [mdist.war=${dist.dir}/${war.name}[m
 endorsed.classpath=\[m
     ${libs.javaee-endorsed-api-7.0.classpath}[m
 excludes=[m
[32m+[m[32mfile.reference.commons-compress-1.20.jar=C:\\jar\\BerishviliAllJars\\commons-compress-1.20.jar[m
 file.reference.commons-fileupload-1.3.3.jar=C:\\jar\\BerishviliAllJars\\commons-fileupload-1.3.3.jar[m
 file.reference.commons-io-2.6.jar=C:\\jar\\BerishviliAllJars\\commons-io-2.6.jar[m
[32m+[m[32mfile.reference.poi-5.0.0.jar=C:\\jar\\BerishviliAllJars\\poi-5.0.0.jar[m
[32m+[m[32mfile.reference.poi-ooxml-5.0.0.jar=C:\\jar\\BerishviliAllJars\\poi-ooxml-5.0.0.jar[m
[32m+[m[32mfile.reference.poi-ooxml-full-5.0.0.jar=C:\\jar\\BerishviliAllJars\\poi-ooxml-full-5.0.0.jar[m
[32m+[m[32mfile.reference.xmlbeans-4.0.0.jar=C:\\jar\\BerishviliAllJars\\xmlbeans-4.0.0.jar[m
 includes=**[m
 j2ee.compile.on.save=true[m
 j2ee.copy.static.files.on.save=true[m
[36m@@ -42,7 +47,12 @@[m [mjavac.classpath=\[m
     ${libs.spring-framework400.classpath}:\[m
     ${libs.jstl.classpath}:\[m
     ${file.reference.commons-fileupload-1.3.3.jar}:\[m
[31m-    ${file.reference.commons-io-2.6.jar}[m
[32m+[m[32m    ${file.reference.commons-io-2.6.jar}:\[m
[32m+[m[32m    ${file.reference.poi-5.0.0.jar}:\[m
[32m+[m[32m    ${file.reference.commons-compress-1.20.jar}:\[m
[32m+[m[32m    ${file.reference.xmlbeans-4.0.0.jar}:\[m
[32m+[m[32m    ${file.reference.poi-ooxml-full-5.0.0.jar}:\[m
[32m+[m[32m    ${file.reference.poi-ooxml-5.0.0.jar}[m
 # Space-separated list of extra javac options[m
 javac.compilerargs=[m
 javac.debug=true[m
[1mdiff --git a/nbproject/project.xml b/nbproject/project.xml[m
[1mindex 032e4ca..673d774 100644[m
[1m--- a/nbproject/project.xml[m
[1m+++ b/nbproject/project.xml[m
[36m@@ -22,6 +22,26 @@[m
                     <file>${file.reference.commons-io-2.6.jar}</file>[m
                     <path-in-war>WEB-INF/lib</path-in-war>[m
                 </library>[m
[32m+[m[32m                <library dirs="200">[m
[32m+[m[32m                    <file>${file.reference.poi-5.0.0.jar}</file>[m
[32m+[m[32m                    <path-in-war>WEB-INF/lib</path-in-war>[m
[32m+[m[32m                </library>[m
[32m+[m[32m                <library dirs="200">[m
[32m+[m[32m                    <file>${file.reference.commons-compress-1.20.jar}</file>[m
[32m+[m[32m                    <path-in-war>WEB-INF/lib</path-in-war>[m
[32m+[m[32m                </library>[m
[32m+[m[32m                <library dirs="200">[m
[32m+[m[32m                    <file>${file.reference.xmlbeans-4.0.0.jar}</file>[m
[32m+[m[32m                    <path-in-war>WEB-INF/lib</path-in-war>[m
[32m+[m[32m                </library>[m
[32m+[m[32m                <library dirs="200">[m
[32m+[m[32m                    <file>${file.reference.poi-ooxml-full-5.0.0.jar}</file>[m
[32m+[m[32m                    <path-in-war>WEB-INF/lib</path-in-war>[m
[32m+[m[32m                </library>[m
[32m+[m[32m                <library dirs="200">[m
[32m+[m[32m                    <file>${file.reference.poi-ooxml-5.0.0.jar}</file>[m
[32m+[m[32m                    <path-in-war>WEB-INF/lib</path-in-war>[m
[32m+[m[32m                </library>[m
             </web-module-libraries>[m
             <web-module-additional-libraries/>[m
             <source-roots>[m
[1mdiff --git a/src/java/ExcelReading/ExcelReader.java b/src/java/ExcelReading/ExcelReader.java[m
[1mnew file mode 100644[m
[1mindex 0000000..43f5611[m
[1m--- /dev/null[m
[1m+++ b/src/java/ExcelReading/ExcelReader.java[m
[36m@@ -0,0 +1,168 @@[m
[32m+[m[32mpackage ExcelReading;[m
[32m+[m
[32m+[m[32mimport java.io.IOException;[m
[32m+[m[32mimport java.io.InputStream;[m
[32m+[m[32mimport java.util.HashMap;[m
[32m+[m[32mimport java.util.LinkedHashMap;[m
[32m+[m[32mimport java.util.Map;[m
[32m+[m[32mimport java.util.logging.Level;[m
[32m+[m[32mimport java.util.logging.Logger;[m
[32m+[m[32mimport javax.xml.parsers.ParserConfigurationException;[m
[32m+[m[32mimport org.apache.poi.openxml4j.exceptions.InvalidFormatException;[m
[32m+[m[32mimport org.apache.poi.openxml4j.exceptions.InvalidOperationException;[m
[32m+[m[32mimport org.apache.poi.openxml4j.exceptions.OpenXML4JException;[m
[32m+[m[32mimport org.apache.poi.openxml4j.opc.OPCPackage;[m
[32m+[m[32mimport org.apache.poi.openxml4j.opc.PackageAccess;[m
[32m+[m[32mimport org.apache.poi.util.XMLHelper;[m
[32m+[m[32mimport org.apache.poi.xssf.eventusermodel.XSSFReader;[m
[32m+[m[32mimport org.apache.poi.xssf.model.SharedStringsTable;[m
[32m+[m[32mimport org.xml.sax.Attributes;[m
[32m+[m[32mimport org.xml.sax.ContentHandler;[m
[32m+[m[32mimport org.xml.sax.InputSource;[m
[32m+[m[32mimport org.xml.sax.SAXException;[m
[32m+[m[32mimport org.xml.sax.XMLReader;[m
[32m+[m[32mimport org.xml.sax.helpers.DefaultHandler;[m
[32m+[m
[32m+[m[32m/**[m
[32m+[m[32m * XSSF and SAX (Event API) basic example. See {@link XLSX2CSV} for a fuller[m
[32m+[m[32m * example of doing XSLX processing with the XSSF Event code.[m
[32m+[m[32m */[m
[32m+[m[32m@SuppressWarnings({"java:S106", "java:S4823", "java:S1192"})[m
[32m+[m[32mpublic class ExcelReader {[m
[32m+[m
[32m+[m[32m    private String cellLocation;[m
[32m+[m[32m    private String cellData;[m
[32m+[m[32m    private HashMap<String, String> cells;[m
[32m+[m
[32m+[m[32m    public ExcelReader() {[m
[32m+[m[32m        cells = new HashMap();[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    public HashMap<String, String> getCellsFromExcelFile(String filename) {[m
[32m+[m[32m        read(filename);[m
[32m+[m[32m        return this.cells;[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    private void read(String filename) {[m
[32m+[m[32m        try (OPCPackage pkg = OPCPackage.open(filename, PackageAccess.READ)) {[m
[32m+[m[32m            XSSFReader r = new XSSFReader(pkg);[m
[32m+[m[32m            SharedStringsTable sst = r.getSharedStringsTable();[m
[32m+[m
[32m+[m[32m            XMLReader parser = fetchSheetParser(sst);[m
[32m+[m[32m            // process the first sheet[m
[32m+[m[32m            try (InputStream sheet = r.getSheetsData().next()) {[m
[32m+[m[32m                InputSource sheetSource = new InputSource(sheet);[m
[32m+[m[32m                parser.parse(sheetSource);[m
[32m+[m[32m            }[m
[32m+[m[32m        } catch (InvalidFormatException ex) {[m
[32m+[m[32m            Logger.getLogger(ExcelReader.class.getName()).log(Level.SEVERE, null, ex);[m
[32m+[m[32m        } catch (InvalidOperationException ex) {[m
[32m+[m[32m            Logger.getLogger(ExcelReader.class.getName()).log(Level.SEVERE, null, ex);[m
[32m+[m[32m        } catch (IOException ex) {[m
[32m+[m[32m            Logger.getLogger(ExcelReader.class.getName()).log(Level.SEVERE, null, ex);[m
[32m+[m[32m        } catch (OpenXML4JException ex) {[m
[32m+[m[32m            Logger.getLogger(ExcelReader.class.getName()).log(Level.SEVERE, null, ex);[m
[32m+[m[32m        } catch (SAXException ex) {[m
[32m+[m[32m            Logger.getLogger(ExcelReader.class.getName()).log(Level.SEVERE, null, ex);[m
[32m+[m[32m        } catch (ParserConfigurationException ex) {[m
[32m+[m[32m            Logger.getLogger(ExcelReader.class.getName()).log(Level.SEVERE, null, ex);[m
[32m+[m[32m        }[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    public XMLReader fetchSheetParser(SharedStringsTable sst) throws SAXException, ParserConfigurationException {[m
[32m+[m[32m        XMLReader parser = XMLHelper.newXMLReader();[m
[32m+[m[32m        ContentHandler handler = new SheetHandler(sst);[m
[32m+[m[32m        parser.setContentHandler(handler);[m
[32m+[m[32m        return parser;[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    /**[m
[32m+[m[32m     * See org.xml.sax.helpers.DefaultHandler javadocs[m
[32m+[m[32m     */[m
[32m+[m[32m    private class SheetHandler extends DefaultHandler {[m
[32m+[m
[32m+[m[32m        private final SharedStringsTable sst;[m
[32m+[m[32m        private String lastContents;[m
[32m+[m[32m        private boolean nextIsString;[m
[32m+[m[32m        private boolean inlineStr;[m
[32m+[m
[32m+[m[32m        private final LruCache<Integer, String> lruCache = new LruCache<>(50);[m
[32m+[m
[32m+[m[32m        private class LruCache<A, B> extends LinkedHashMap<A, B> {[m
[32m+[m
[32m+[m[32m            private final int maxEntries;[m
[32m+[m
[32m+[m[32m            public LruCache(final int maxEntries) {[m
[32m+[m[32m                super(maxEntries + 1, 1.0f, true);[m
[32m+[m[32m                this.maxEntries = maxEntries;[m
[32m+[m
[32m+[m[32m            }[m
[32m+[m
[32m+[m[32m            @Override[m
[32m+[m[32m            protected boolean removeEldestEntry(final Map.Entry<A, B> eldest) {[m
[32m+[m[32m                return super.size() > maxEntries;[m
[32m+[m[32m            }[m
[32m+[m[32m        }[m
[32m+[m
[32m+[m[32m        private SheetHandler(SharedStringsTable sst) {[m
[32m+[m[32m            this.sst = sst;[m
[32m+[m[32m        }[m
[32m+[m
[32m+[m[32m        @Override[m
[32m+[m[32m        public void startElement(String uri, String localName, String name,[m
[32m+[m[32m                Attributes attributes) throws SAXException {[m
[32m+[m[32m            // r => reference[m
[32m+[m[32m            /*[m
[32m+[m[32m            if (name.equals("row")) {[m
[32m+[m[32m                // System.out.print("row-");[m
[32m+[m[32m                //System.out.println(attributes.getValue("r"));[m
[32m+[m[32m            }*/[m
[32m+[m[32m            // c => cell[m
[32m+[m[32m            if (name.equals("c")) {[m
[32m+[m[32m                // Print the cell reference[m
[32m+[m[32m                //  System.out.print(attributes.getValue("r") + " - ");[m
[32m+[m[32m                cellLocation = attributes.getValue("r");[m
[32m+[m[32m                // Figure out if the value is an index in the SST[m
[32m+[m[32m                String cellType = attributes.getValue("t");[m
[32m+[m[32m                nextIsString = cellType != null && cellType.equals("s");[m
[32m+[m[32m                inlineStr = cellType != null && cellType.equals("inlineStr");[m
[32m+[m[32m            }[m
[32m+[m[32m            // Clear contents cache[m
[32m+[m[32m            lastContents = "";[m
[32m+[m
[32m+[m[32m        }[m
[32m+[m
[32m+[m[32m        @Override[m
[32m+[m[32m        public void endElement(String uri, String localName, String name)[m
[32m+[m[32m                throws SAXException {[m
[32m+[m[32m            // Process the last contents as required.[m
[32m+[m[32m            // Do now, as characters() may be called more than once[m
[32m+[m[32m            if (nextIsString && !lastContents.trim().isEmpty()) {[m
[32m+[m[32m                Integer idx = Integer.valueOf(lastContents);[m
[32m+[m[32m                lastContents = lruCache.get(idx);[m
[32m+[m[32m                if (lastContents == null && !lruCache.containsKey(idx)) {[m
[32m+[m[32m                    lastContents = sst.getItemAt(idx).getString();[m
[32m+[m[32m                    lruCache.put(idx, lastContents);[m
[32m+[m[32m                }[m
[32m+[m[32m                nextIsString = false;[m
[32m+[m[32m            }[m
[32m+[m
[32m+[m[32m            // v => contents of a cell[m
[32m+[m[32m            // Output after we've seen the string contents[m
[32m+[m[32m            if (name.equals("v") || (inlineStr && name.equals("c"))) {[m
[32m+[m[32m                cellData = lastContents;[m
[32m+[m[32m                //adding cell to cel[m
[32m+[m[32m                cells.put(cellLocation, cellData);[m
[32m+[m[32m                // System.out.println(lastContents);[m
[32m+[m
[32m+[m[32m            }[m
[32m+[m[32m        }[m
[32m+[m
[32m+[m[32m        @Override[m
[32m+[m[32m        public void characters(char[] ch, int start, int length) throws SAXException { // NOSONAR[m
[32m+[m[32m            lastContents += new String(ch, start, length);[m
[32m+[m[32m        }[m
[32m+[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m}[m
[1mdiff --git a/src/java/Uploading/PlannedDataUploadController.java b/src/java/Uploading/PlannedDataUploadController.java[m
[1mindex e31f871..de33e1a 100644[m
[1m--- a/src/java/Uploading/PlannedDataUploadController.java[m
[1m+++ b/src/java/Uploading/PlannedDataUploadController.java[m
[36m@@ -38,6 +38,7 @@[m [mpublic class PlannedDataUploadController {[m
         this.memoryUsage.printMemoryUsage();[m
 [m
         String filename = "plannedDataExcelFile.xlsx";[m
[32m+[m[32m        String filePath = this.basement.getBasementDirectory() + "/uploads/" + filename;[m
         if (file.isEmpty()) {[m
             model.addAttribute("uploadStatus", "Upload could not been completed");[m
             model.addAttribute("errorMessage", "არცერთი ფაილი არ იყო არჩეული");[m
[36m@@ -47,44 +48,19 @@[m [mpublic class PlannedDataUploadController {[m
             byte barr[] = file.getBytes();[m
 [m
             BufferedOutputStream bout = new BufferedOutputStream([m
[31m-                    new FileOutputStream(this.basement.getBasementDirectory() + "/uploads/" + filename));[m
[32m+[m[32m                    new FileOutputStream(filePath));[m
             bout.write(barr);[m
             bout.flush();[m
             bout.close();[m
[31m-            /*[m
[31m-            RouteFactory routeFactory = new RouteFactory();[m
 [m
[31m-            TreeMap<Float, BasicRoute> basicRoutesFromUploadedFile = routeFactory.createBasicRoutesFromUploadedFile();[m
[31m-[m
[31m-            String lastUploadDeletionStatus = uploadDao.deleteLastUpload();[m
[31m-            System.out.println("Last Upload Deletion Status:" + lastUploadDeletionStatus);[m
[31m-            String lastUploadInsertionStatus = uploadDao.insertNewUpload(basicRoutesFromUploadedFile);[m
[31m-            System.out.println("New Upload Insertion Status:" + lastUploadInsertionStatus);[m
[31m-[m
[31m-            Float insertionStatusCode = routeDao.insertUploadedData(basicRoutesFromUploadedFile, model);[m
[31m-            if (insertionStatusCode == 0.00f) {[m
[31m-                System.out.println("New data from excel file has been successfully uploaded into database");[m
[31m-                //  model.addAttribute("unregisteredRoutesMessage", "lalalalalalalalal");[m
[31m-            }[m
[31m-            Instant end = Instant.now();[m
[31m-            System.out.println("Uploading process Ended. Time needed:" + Duration.between(start, end));[m
[31m-            System.out.print("Memory Usage after uploading: ");[m
[31m-            mu.printMemoryUsage();[m
[31m-            System.out.println("--------------------------------------------------");[m
[31m-            if (insertionStatusCode > 0.00f) {[m
[31m-                return "upload-success";[m
[31m-            }[m
[31m-[m
[31m-            //  UploadInsertionThread uit = new UploadInsertionThread();[m
[31m-            // uit.start();[m
[31m-[m
[31m-             */[m
         } catch (Exception e) {[m
             System.out.println(e);[m
             model.addAttribute("uploadStatus", "Upload could not been completed:" + e);[m
             return "upload";[m
         }[m
[31m-[m
[32m+[m[32m        //--------------- Starting new Thread For Upload-----------------------[m
[32m+[m[32m        PlannedDataUploadThread plannedDataUploadThread = new PlannedDataUploadThread(filePath);[m
[32m+[m[32m        plannedDataUploadThread.start();[m
         return "uploadStatus";[m
     }[m
 }[m
[1mdiff --git a/src/java/Uploading/PlannedDataUploadThread.java b/src/java/Uploading/PlannedDataUploadThread.java[m
[1mnew file mode 100644[m
[1mindex 0000000..8eed811[m
[1m--- /dev/null[m
[1m+++ b/src/java/Uploading/PlannedDataUploadThread.java[m
[36m@@ -0,0 +1,31 @@[m
[32m+[m[32mpackage Uploading;[m
[32m+[m
[32m+[m[32mimport ExcelReading.ExcelReader;[m
[32m+[m[32mimport Service.StaticsDispatcher;[m
[32m+[m[32mimport java.util.HashMap;[m
[32m+[m[32mimport java.util.Map;[m
[32m+[m
[32m+[m[32mpublic class PlannedDataUploadThread extends Thread {[m
[32m+[m
[32m+[m[32m    private String filePath;[m
[32m+[m
[32m+[m[32m    public PlannedDataUploadThread(String filePath) {[m
[32m+[m[32m        this.filePath = filePath;[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    @Override[m
[32m+[m[32m    public void run() {[m
[32m+[m[32m        System.out.println("STARTING READING EXCEL FILE");[m
[32m+[m
[32m+[m[32m        StaticsDispatcher.setUploading(true);[m
[32m+[m
[32m+[m[32m        ExcelReader excelReader = new ExcelReader();[m
[32m+[m[32m        HashMap<String, String> cellsFromExcelFile = excelReader.getCellsFromExcelFile(this.filePath);[m
[32m+[m[32m        System.out.println(cellsFromExcelFile.size());[m
[32m+[m[32m        for (Map.Entry<String, String> f : cellsFromExcelFile.entrySet()) {[m
[32m+[m
[32m+[m[32m            System.out.println(f.getKey());[m
[32m+[m[32m        }[m
[32m+[m[32m        StaticsDispatcher.setUploading(false);[m
[32m+[m[32m    }[m
[32m+[m[32m}[m
[1mdiff --git a/src/java/Uploading/UploadStatusAjaxController.java b/src/java/Uploading/UploadStatusAjaxController.java[m
[1mindex 394432b..f3de684 100644[m
[1m--- a/src/java/Uploading/UploadStatusAjaxController.java[m
[1m+++ b/src/java/Uploading/UploadStatusAjaxController.java[m
[36m@@ -1,5 +1,6 @@[m
 package Uploading;[m
 [m
[32m+[m[32mimport Service.StaticsDispatcher;[m
 import org.springframework.stereotype.Controller;[m
 import org.springframework.web.bind.annotation.RequestMapping;[m
 import org.springframework.web.bind.annotation.RequestMethod;[m
[36m@@ -7,19 +8,15 @@[m [mimport org.springframework.web.bind.annotation.ResponseBody;[m
 [m
 @Controller[m
 public class UploadStatusAjaxController {[m
[31m-[m
[31m-    private int x = 0;[m
[31m-[m
[32m+[m[41m  [m
     @RequestMapping(value = "/uploadStatusAjax", method = RequestMethod.GET)[m
     public @ResponseBody[m
     String getTime() {[m
[31m-        //   uploadTime++;[m
[31m-        // String uploadStatus = uploadDao.getUploadStatus();[m
[31m-        x++;[m
[31m-        if (x > 20) {[m
[31m-            x=0;[m
[31m-           return "dddd:completed";[m
[32m+[m[32m        if (StaticsDispatcher.isUploading()) {[m
[32m+[m[32m            return "Reading Uploaded Excel File ...........";[m
[32m+[m[32m        } else {[m
[32m+[m[32m            return "completed";[m
         }[m
[31m-        return x + "YOU STILL NEED TO DO SOME WORK HERE";[m
[32m+[m
     }[m
 }[m
[1mdiff --git a/web/WEB-INF/jsp/uploadStatus.jsp b/web/WEB-INF/jsp/uploadStatus.jsp[m
[1mindex 99f3545..f313132 100644[m
[1m--- a/web/WEB-INF/jsp/uploadStatus.jsp[m
[1m+++ b/web/WEB-INF/jsp/uploadStatus.jsp[m
[36m@@ -6,34 +6,34 @@[m
         <title>Upload Status</title>[m
         <script type="text/javascript"[m
         src="http://code.jquery.com/jquery-1.10.1.min.js"></script>[m
[32m+[m
[32m+[m[32m    </head>[m
[32m+[m[32m    <body>[m
[32m+[m[32m        <div align="center">[m
[32m+[m[32m            <center>[m
[32m+[m[32m                <h1>Upload Status</h1>[m
[32m+[m[32m                <br> <br> ${message} <br> <br>[m
[32m+[m[32m                <h1>  <div id="result"></div></h1>[m
[32m+[m[32m            </center>[m
[32m+[m[32m        </div>[m
         <script type="text/javascript">[m
[32m+[m[32m            var intervalId = 0;[m
[32m+[m[32m            intervalId = setInterval(crunchifyAjax, 1000);[m
[32m+[m[32m            var sec=0;[m
             function crunchifyAjax() {[m
                 $.ajax({[m
                     url: 'uploadStatusAjax.htm',[m
[31m-                    success: function (data) {[m
[31m-                        const myArray = data.split(":");[m
[31m-                        let status = myArray[1];[m
[32m+[m[32m                    success: function (status) {[m
                         if (status === 'completed') {[m
                             window.location.href = 'index.htm';[m
                         } else {[m
[31m-                            $('#result').html(data);[m
[32m+[m[32m                            $('#result').html(sec+"<hr>"+status);[m
                         }[m
                     }[m
                 });[m
[32m+[m[32m                sec++;[m
             }[m
         </script>[m
[31m-        <script type="text/javascript">[m
[31m-            var intervalId = 0;[m
[31m-            intervalId = setInterval(crunchifyAjax, 1000);[m
[31m-        </script>[m
[31m-    </head>[m
[31m-    <body>[m
[31m-        <div align="center">[m
[31m-            <center>[m
[31m-                <h1>Upload Status</h1>[m
[31m-                <br> <br> ${message} <br> <br>[m
[31m-                <h1>  <div id="result"></div></h1>[m
[31m-            </center>[m
[31m-        </div>[m
[32m+[m
     </body>[m
 </html>[m

[33mcommit 7de6dc22d77993466f5888610ee83bd22fff4ef6[m
Author: Michail Sitmalidis <boxm16@yahoo.com>
Date:   Wed Dec 28 22:03:16 2022 +0200

    initial commit

[1mdiff --git a/build.xml b/build.xml[m
[1mnew file mode 100644[m
[1mindex 0000000..a6645b8[m
[1m--- /dev/null[m
[1m+++ b/build.xml[m
[36m@@ -0,0 +1,71 @@[m
[32m+[m[32m<?xml version="1.0" encoding="UTF-8"?>[m
[32m+[m[32m<!-- You may freely edit this file. See commented blocks below for -->[m
[32m+[m[32m<!-- some examples of how to customize the build. -->[m
[32m+[m[32m<!-- (If you delete it and reopen the project it will be recreated.) -->[m
[32m+[m[32m<!-- By default, only the Clean and Build commands use this build script. -->[m
[32m+[m[32m<!-- Commands such as Run, Debug, and Test only use this build script if -->[m
[32m+[m[32m<!-- the Compile on Save feature is turned off for the project. -->[m
[32m+[m[32m<!-- You can turn off the Compile on Save (or Deploy on Save) setting -->[m
[32m+[m[32m<!-- in the project's Project Properties dialog box.-->[m
[32m+[m[32m<project name="berishvili_3.0" default="default" basedir=".">[m
[32m+[m[32m    <description>Builds, tests, and runs the project berishvili_3.0.</description>[m
[32m+[m[32m    <import file="nbproject/build-impl.xml"/>[m
[32m+[m[32m    <!--[m
[32m+[m
[32m+[m[32m    There exist several targets which are by default empty and which can be[m[41m [m
[32m+[m[32m    used for execution of your tasks. These targets are usually executed[m[41m [m
[32m+[m[32m    before and after some main targets. They are:[m[41m [m
[32m+[m
[32m+[m[32m      -pre-init:                 called before initialization of project properties[m[41m [m
[32m+[m[32m      -post-init:                called after initialization of project properties[m[41m [m
[32m+[m[32m      -pre-compile:              called before javac compilation[m[41m [m
[32m+[m[32m      -post-compile:             called after javac compilation[m[41m [m
[32m+[m[32m      -pre-compile-single:       called before javac compilation of single file[m
[32m+[m[32m      -post-compile-single:      called after javac compilation of single file[m
[32m+[m[32m      -pre-compile-test:         called before javac compilation of JUnit tests[m
[32m+[m[32m      -post-compile-test:        called after javac compilation of JUnit tests[m
[32m+[m[32m      -pre-compile-test-single:  called before javac compilation of single JUnit test[m
[32m+[m[32m      -post-compile-test-single: called after javac compilation of single JUunit test[m
[32m+[m[32m      -pre-dist:                 called before archive building[m[41m [m
[32m+[m[32m      -post-dist:                called after archive building[m[41m [m
[32m+[m[32m      -post-clean:               called after cleaning build products[m[41m [m
[32m+[m[32m      -pre-run-deploy:           called before deploying[m
[32m+[m[32m      -post-run-deploy:          called after deploying[m
[32m+[m
[32m+[m[32m    Example of pluging an obfuscator after the compilation could look like[m[41m [m
[32m+[m
[32m+[m[32m        <target name="-post-compile">[m
[32m+[m[32m            <obfuscate>[m
[32m+[m[32m                <fileset dir="${build.classes.dir}"/>[m
[32m+[m[32m            </obfuscate>[m
[32m+[m[32m        </target>[m
[32m+[m
[32m+[m[32m    For list of available properties check the imported[m[41m [m
[32m+[m[32m    nbproject/build-impl.xml file.[m[41m [m
[32m+[m
[32m+[m
[32m+[m[32m    Other way how to customize the build is by overriding existing main targets.[m
[32m+[m[32m    The target of interest are:[m[41m [m
[32m+[m
[32m+[m[32m      init-macrodef-javac:    defines macro for javac compilation[m
[32m+[m[32m      init-macrodef-junit:   defines macro for junit execution[m
[32m+[m[32m      init-macrodef-debug:    defines macro for class debugging[m
[32m+[m[32m      do-dist:                archive building[m
[32m+[m[32m      run:      
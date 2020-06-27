@echo off
set JDK_HOME=c:\JBuilderX\jdk1.4
set SERVER_ROOT="D:\Program Files\Apache Software Foundation\Tomcat 5.0"
set SHOECLASSPATH=.;c:\JavaAdvTopics;%SERVER_ROOT%\common\lib\servlet-api.jar

set WEB_INF_CLASSES=%SERVER_ROOT%\webapps\JavaAdv2WebApp\WEB-INF\classes
rem Create "properties" drectory under "JavaAdv2WebApp" on Tomcat
set WEB_APP_PROPERTIES=%SERVER_ROOT%\webapps\JavaAdv2WebApp\properties


%JDK_HOME%\bin\javac -classpath %SHOECLASSPATH% -d %WEB_INF_CLASSES% -deprecation ShoeStoreServlet.java HtmlResultSet.java
copy .\ShoeStore.properties %WEB_APP_PROPERTIES%
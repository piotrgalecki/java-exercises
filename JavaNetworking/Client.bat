@echo off
set JDK_HOME="C:\Program Files\j2sdk_nb\j2sdk1.4.2"
set NETCLASSPATH=.;c:\JavaAdv

%JDK_HOME%\bin\javac -classpath %NETCLASSPATH% Client.java
%JDK_HOME%\bin\java -classpath %NETCLASSPATH% edu.brandeis.students.PiotrGalecki.Homework2.Networking.Client

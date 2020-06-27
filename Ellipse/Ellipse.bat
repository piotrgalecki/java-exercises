@echo off
set JDK_HOME="C:\Program Files\j2sdk_nb\j2sdk1.4.2"
set ECLASSPATH=.;c:\JavaAdv

%JDK_HOME%\bin\javac -classpath %ECLASSPATH% EllipseAnimatedMain.java
%JDK_HOME%\bin\java -classpath %ECLASSPATH% edu.brandeis.students.PiotrGalecki.Homework2.Ellipse.EllipseAnimatedMain


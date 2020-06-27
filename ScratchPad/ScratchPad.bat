@echo off
set JDK_HOME="C:\Program Files\j2sdk_nb\j2sdk1.4.2"
set SPCLASSPATH=.;c:\JavaAdv

%JDK_HOME%\bin\javac -classpath %SPCLASSPATH% ScratchPad.java
%JDK_HOME%\bin\java -classpath %SPCLASSPATH% edu.brandeis.students.PiotrGalecki.Homework2.ScratchPad.ScratchPad


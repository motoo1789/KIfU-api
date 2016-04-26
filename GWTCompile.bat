@echo off

cd C:\Users\tanaka\git\ForThird\GWTUMLAPI

call ant jar

copy .\build\dist\gwt-umlapi.jar ..\GWTUMLDrawer\war\WEB-INF\lib\gwt-umlapi.jar

cd  ..\GWTUMLDrawer\
call ant  war

del C:\pleiades4-4\tomcat\7\webapps\KIfU.war
copy KIfU.war C:\pleiades4-4\tomcat\7\webapps\

cd ..\GWTUMLAPI

exit /B %errorlevel%

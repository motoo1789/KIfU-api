@echo off

cd C:\Users\tanaka\git\ForThird\GWTUMLAPI

call ant jar

copy .\build\dist\gwt-umlapi.jar ..\GWTUMLDrawer\war\WEB-INF\lib\gwt-umlapi.jar

cd  ..\GWTUMLDrawer\
call ant  war

del C:\pleiades4-3\tomcat\7\webapps\UMLDrawer.war
copy UMLDrawer.war C:\pleiades4-3\tomcat\7\webapps\

cd ..\GWTUMLAPI

exit /B %errorlevel%

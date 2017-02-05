@echo off
setlocal
set CLASS_PATH=lib\${project.build.finalName}.jar
set CLASS_PATH=%CLASS_PATH%;${app.classpath}
start "" /b java -cp %CLASS_PATH% org.ml.primenumbers.PrimeNumbers -gui
endlocal

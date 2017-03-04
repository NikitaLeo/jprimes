@echo off
setlocal
set CLASS_PATH=target\primenumbers-0.1.0.jar
java -cp %CLASS_PATH% org.ml.primenumbers.PrimeNumbers -gui
endlocal

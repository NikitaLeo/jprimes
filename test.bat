@echo off
setlocal
if [%1%] == [] goto usage
if [%2%] == [] goto usage
set ALG=%1%
set MAX=%2%
set CLASS_PATH=target\primenumbers-0.1.0.jar
java -cp %CLASS_PATH% org.ml.primenumbers.PrimeNumbers %MAX% %MAX% 1 test.dat %ALG%
goto stop

:usage
@echo Usage: test algorithm-name
:stop
endlocal

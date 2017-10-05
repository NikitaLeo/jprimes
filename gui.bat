@echo off
setlocal
mvn exec:java -Dexec.mainClass="org.ml.primenumbers.PrimeNumbers" -Dexec.args="-gui"
endlocal

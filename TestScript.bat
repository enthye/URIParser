ECHO OFF
cd ..\URIParser
set Path="C:\Program Files\Java\jdk1.8.0_181\bin"

javac -cp junit-4.12.jar;hamcrest-core-1.3.jar;cases/good.jar; uriparser/TestURIParser.java

ECHO GOOD
java -cp junit-4.12.jar;hamcrest-core-1.3.jar;cases/good.jar;. org.junit.runner.JUnitCore uriparser.TestURIParser
PAUSE

ECHO A
java -cp junit-4.12.jar;hamcrest-core-1.3.jar;cases/badA.jar;. org.junit.runner.JUnitCore uriparser.TestURIParser
ECHO ^A RESULTS
PAUSE

ECHO B
java -cp junit-4.12.jar;hamcrest-core-1.3.jar;cases/badB.jar;. org.junit.runner.JUnitCore uriparser.TestURIParser
ECHO ^B  RESULTS
PAUSE

ECHO C
java -cp junit-4.12.jar;hamcrest-core-1.3.jar;cases/badC.jar;. org.junit.runner.JUnitCore uriparser.TestURIParser
ECHO ^C RESULTS
PAUSE

ECHO D
java -cp junit-4.12.jar;hamcrest-core-1.3.jar;cases/badD.jar;. org.junit.runner.JUnitCore uriparser.TestURIParser
ECHO ^D RESULTS
PAUSE

ECHO E
java -cp junit-4.12.jar;hamcrest-core-1.3.jar;cases/badE.jar;. org.junit.runner.JUnitCore uriparser.TestURIParser
ECHO ^E RESULTS
PAUSE

ECHO F
java -cp junit-4.12.jar;hamcrest-core-1.3.jar;cases/badF.jar;. org.junit.runner.JUnitCore uriparser.TestURIParser
ECHO ^F RESULTS
PAUSE

ECHO G
java -cp junit-4.12.jar;hamcrest-core-1.3.jar;cases/badG.jar;. org.junit.runner.JUnitCore uriparser.TestURIParser
ECHO ^G RESULTS
PAUSE

ECHO H
java -cp junit-4.12.jar;hamcrest-core-1.3.jar;cases/badH.jar;. org.junit.runner.JUnitCore uriparser.TestURIParser
ECHO ^H RESULTS
PAUSE

ECHO I
java -cp junit-4.12.jar;hamcrest-core-1.3.jar;cases/badI.jar;. org.junit.runner.JUnitCore uriparser.TestURIParser
ECHO ^I RESULTS
PAUSE

ECHO J
java -cp junit-4.12.jar;hamcrest-core-1.3.jar;cases/badJ.jar;. org.junit.runner.JUnitCore uriparser.TestURIParser
ECHO ^J RESULTS
PAUSE
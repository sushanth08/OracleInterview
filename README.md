# OracleInterview
Repository to place all the files for oracle interview. We have automated the scenarios for Aconex. A Page Object model has been followed to design the framework.

# Installation
The following tools have to installed in MAC OSX
1. JDK 11.0.1
2. Eclipse IDE for Java Developers
3. Firefox version 64
4. Gecko Driver 23.0
5. TestNG plugin

For the Project to execute, we need to install the dependencies in the Maven Project pom.xml
1. maven
2. selenium
3. testng
4. org json
5. simple json

And we also need to install Xcode through terminal

# Features
1. Screenshots
2. Parallel execution
3. Retry for failed test cases

# Status
https://github.com/sushanth08/OracleInterview : Working

# Execution
There are 3 ways to execute the tests
1. Directly through TestNg plugin in Eclipse
2. Testng.xml
3. Maven command 
     mvn clean test -DsuiteXmlFile=testng.xml
     
# Enhancements
The file upload feature is yet to implemented due to lack of understanding Robot Class in MAC OSX

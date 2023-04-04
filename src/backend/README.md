## Project Name & Pitch 
Coachmate

An application used to connect the Garmin connect and retrieve activity data, built with Springboot framework.
This is a unimelb project of subject COMP90082 which is responsible for acquiring and processing data.
 Data collected in Garmin Connect is acquired through Garmin wearable. devices used by athletes training under CoachingMate.
 
 ## Table of Content

- [Project Name & Pitch](#project-name--pitch)
- [Table of Content](#table-of-content)
- [Project Status](#project-status)
- [Components diagram](#components-diagram)
- [The project structure](#the-project-structure)
- [Description of the database structure](#description-of-the-database-structure)
- [Installation and Setup Instructions](#installation-and-setup-instructions)
  - [1. install JDK](#1-install-jdk)
  - [2. install maven](#2-install-maven)
  - [3. install MongoDB](#3-install-mongodb)
  - [4. install git environment](#4-install-git-environment)
  - [5. Run this project locally](#5-run-this-project-locally)
- [Deployment guidelines](#deployment-guidelines)
  - [1. create a new application on heroku](#1-create-a-new-application-on-heroku)
  - [2. text our app name and region](#2-text-our-app-name-and-region)
  - [3. fork this repository to your own repository](#3-fork-this-repository-to-your-own-repository)
  - [4. Select Github and find this forked project in your own repository](#4-select-github-and-find-this-forked-project-in-your-own-repository)
  - [5. connect successfully](#5-connect-successfully)
  - [6. find our app and click the Openapp button](#6-find-our-app-and-click-the-openapp-button)
  - [7. find our URL in the build log](#7-find-our-url-in-the-build-log)
- [Documents](#documents)
- [Release History](#release-history)



## Project Status

This project is currently in development. 
1. Users can register an account and login to this system. 
2. User can authorised this account to garmin connect so that garmin connect can transfer activity data to this backend.

## Components diagram
![image](https://github.com/chenyuguo/coachingmate/blob/master/Resources/componentPicture/component%20Diagram.png)


## The project structure
 The project adopts the development mode of sub-modules according to functions, and the structure is as follows
 
- coachingmateanalytics.coachingmate.controller: Front controller layer
- coachingmateanalytics.coachingmate.entity: Data entity class
- coachingmateanalytics.coachingmate.dao : Data interface access layer
- coachingmateanalytics.coachingmate.service : Data service interface layer
- coachingmateanalytics.coachingmate.service.serviceImpl : Data service interface implementation layer
- coachingmateanalytics.coachingmate.utils : Tool library
- coachingmateanalytics.coachingmate.intercepter : used to set the response header for all request

- resources/application.yml : Project profile
resources/static/ : Static resource directory

## Description of the database structure 

Mongodb has one database (named coachingDB) which has three collections, which are respectively
- requestToken : Use to store the [RequestToken entity class](https://github.com/chenyuguo/coachingmate/blob/master/src/main/java/coachingmateanalytics/coachingmate/entity/RequestToken.java)
- user : Used to store the [Userpartner entity class](https://github.com/chenyuguo/coachingmate/blob/master/src/main/java/coachingmateanalytics/coachingmate/entity/UserPartner.java)
- activity : Used to store the [Activity entity class](https://github.com/chenyuguo/coachingmate/blob/master/src/main/java/coachingmateanalytics/coachingmate/entity/Activity.java)


## Installation and Setup Instructions

### 1. install JDK
[Official tutorial for JDK installation](https://docs.oracle.com/javase/10/install/installation-jdk-and-jre-macos.htm#JSJIG-GUID-2FE451B0-9572-4E38-A1A5-568B77B146DE)

### 2. install maven
[Official tutorial for Maven installation](http://maven.apache.org/install.html)<br>
take mac os for example
- download 	apache-maven-3.6.3-bin.tar.gz
- tar xzvf apache-maven-3.6.3-bin.tar.gz
 * Alternatively use your preferred archive extraction tool.
 * Add the bin directory of the created directory apache-maven-3.6.3 to the PATH environment variable
 * Confirm with mvn -v in a new shell. The result should look similar to
```
Apache Maven 3.6.3 (cecedd343002696d0abb50b32b541b8a6ba2883f)
Maven home: /opt/apache-maven-3.6.3
Java version: 1.8.0_45, vendor: Oracle Corporation
Java home: /Library/Java/JavaVirtualMachines/jdk1.8.0_45.jdk/Contents/Home/jre
Default locale: en_US, platform encoding: UTF-8
OS name: "mac os x", version: "10.8.5", arch: "x86_64", family: "mac"
```
### 3. install MongoDB
[Official tutorial for mongoDB installation](https://docs.mongodb.com/manual/tutorial/install-mongodb-on-os-x/)

```
brew install mongodb-community@4.4
```

### 4. install git environment
- linux install
```
yum install git
```
- mac install
```
brew install git
```
- Before you can use Git for version management, you need to configure Git by telling git your **username** and your **email account**
```
//configuration
[root@localhost ~]# git config --global user.name flymegoc
[root@localhost ~]# git config --global user.email 343672271@qq.com

//Check the configuration
[root@localhost ~]# git config --list
user.name=flymegoc
user.email=343672271@qq.com
```

### 5. Run this project locally
Clone down this repository. You will need `maven` and `JDK1.8` installed globally on your machine.  

`git clone https://github.com/agogear/coaching-mate.git`

Modify configuration file:
[application-dev.properties](https://github.com/agogear/coaching-mate/blob/master/src/main/resources/application-dev.properties)

1. create your mongodb database with {database name} manually on your local environment
```
spring.data.mongodb.database={database name}
spring.data.mongodb.port=27017
```
2. export data from cloud database:
[cloud mongodb database](mongodb+srv://admin:unimelb0121@cluster0.2qpx8.mongodb.net/coachingMate?retryWrites=true&w=majority)
3. import data to your local mongodb database


Installation:

`cd coachingmate` <br>
`mvn install`  0

To Start Server:

`java -jar ./target/coachingmate-0.0.1-SNAPSHOT.jar`  

To login App:

`localhost:8080/login?username={username}&password={password}`  


## Deployment guidelines
There are serveal steps we need to do to run our app on heroku
### 1. create a new application on heroku
![image](https://github.com/chenyuguo/coachingmate/blob/master/Resources/runonserver/picture/pic/1newApp.jpg)

### 2. text our app name and region
![image](https://github.com/chenyuguo/coachingmate/blob/master/Resources/runonserver/picture/pic/2newCreate.jpg)

### 3. fork this repository to your own repository

### 4. Select Github and find this forked project in your own repository
![image](https://github.com/chenyuguo/coachingmate/blob/master/Resources/runonserver/picture/pic/3connect.jpg)

### 5. connect successfully
![image](https://github.com/chenyuguo/coachingmate/blob/master/Resources/runonserver/picture/pic/4success.jpg)

### 6. find our app and click the Openapp button
![image](https://github.com/chenyuguo/coachingmate/blob/master/Resources/runonserver/picture/pic/5open.jpg)

### 7. find our URL in the build log
![image](https://github.com/chenyuguo/coachingmate/blob/master/Resources/runonserver/picture/pic/6.jpg)

URL: <https://coachingmate2020.herokuapp.com/>


## Documents
- [User Stories](https://github.com/chenyuguo/coachingmate/blob/master/docs/UserStories.pdf)
- [Components Diagram](https://github.com/chenyuguo/coachingmate/blob/master/docs/Components%20Diagram.pdf)
- [motivational model](https://github.com/chenyuguo/coachingmate/blob/master/docs/Motivational%20model.pdf)
- [coachingmate-api](https://github.com/chenyuguo/coachingmate/blob/master/docs/coachingmate-api.pdf)
- [Integrate MongoDB within SpringBoot](https://github.com/chenyuguo/coachingmate/blob/master/docs/Integrate%20MongoDB%20within%20SpringBoot.pdf)

## Release History
- 1.0.0 - Complete the basic setup of front and rear ends (user registration, user login, garmin Connect authorization, and Garmin Connect simulation terminal data)
- 1.0.1 - fix bug: when username is right, password is not checked. and add log aspect.
- 2.0.0 - Solve HTTPS problems requiring certificate
- 3.0.0 - add api document annotations and integrate the parsing .fit data module. The Garmin Connect app can call push URL to push data to the back end，this is the final version


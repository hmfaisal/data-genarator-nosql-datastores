# dataGenerator
# README #


### What is this repository for? ###

* This repository is responsible for data generation

### How do I get set up? ###

* install nodejs latest version
* install npm
* Pull the project
* Go to backend folder and run : mvn spring-boot run
* Go to frontend folder and run : npm install
* Go to frontend folder and run : npm start
* backend will run at localhost: 8080 by default
* backend will run at localhost: 4200 by default
* frontend will communicate with backend by proxy which script is written in frontend folder

### Data generation Process ###

* User provide sample json document
* This tool extract schema from json file
* User provide total data generation volume in Bytes
* User choose data generation technique from 3 technique for every field. By default its set to mimick duplicate
* This tool generate meaningful data in these format: json,sql, cypher, xml
* This tool can also generate data and insert to the database directly for these databases: mysql, mongodb,neo4j
* Data Type support: int, double, float, long, short, boolean, null, words of sentences, alphanumeric, url, email, datetime

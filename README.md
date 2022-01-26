# tripsncostcalculator

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.
 
### Prerequisites

java (JDK 8.0 and above)
maven 3.6
Make sure JAVA_HOME and MVN_HOME are pointing to the correct locations.
clone the code to your local directory
a pom.xml for the parent project and this readme.md file.

### Package & run the jar.

		1. Go to the parent project directory and run mvn clean install.(This creates the jar tripsncostcalulator.jar inside the target folder) (this takes some time when run for the first time). It also runs the tests. If you don't want the tests to run while packaging the jar run #### sudo mvn clean install -DskipTests
		2. To run the Jar make sure you have the taps.txt and trips.txt (empty file)
		3. java -jar target/tripsncostcalulator.jar mytaps.txt mytrips.txt will run the program taking the two files as input and the processed data will be present in the mytrips.txt.
	
## Running the tests

	1. to run the tests please run mvn clean test
  
### Sample Taps.txt

id
 
1,2022-01-26 20:45:59,ON,Stop1,Company1,Bus1,12345

2,2022-01-26 20:58:59,OFF,Stop2,Company1,Bus1,12345

3,2022-01-26 20:45:59,ON,Stop1,Company2,Bus2,12346

4,2022-01-26 20:45:59,OFF,Stop1,Company2,Bus2,12346

5,2022-01-26 20:45:59,ON,Stop1,Company2,Bus2,12347

6,2022-01-26 20:55:59,OFF,Stop3,Company2,Bus2,12347

7,2022-01-26 20:45:59,ON,Stop1,Company2,Bus3,12348

8,2022-01-26 20:55:59,ON,Stop3,Company2,Bus4,12348

9,2022-01-26 21:05:59,OFF,Stop1,Company2,Bus3,12348

### Sample Output Trips.txt

Trip(started=2022-01-26T20:45:59, finished=2022-01-26T20:58:59, durationInSeconds=780, fromStopId=Stop1, toStopId=Stop2, chargeAmount=3.25, companyId=Company1, busId=Bus1, pan=12345, tripStatus=COMPLETED)
Trip(started=2022-01-26T20:45:59, finished=2022-01-26T20:55:59, durationInSeconds=600, fromStopId=Stop1, toStopId=Stop3, chargeAmount=7.3, companyId=Company2, busId=Bus2, pan=12347, tripStatus=COMPLETED)
Trip(started=2022-01-26T20:45:59, finished=2022-01-26T21:05:59, durationInSeconds=1200, fromStopId=Stop1, toStopId=Stop1, chargeAmount=0.0, companyId=Company2, busId=Bus3, pan=12348, tripStatus=CANCELLED)
Trip(started=2022-01-26T20:45:59, finished=2022-01-26T20:45:59, durationInSeconds=0, fromStopId=Stop1, toStopId=Stop1, chargeAmount=0.0, companyId=Company2, busId=Bus2, pan=12346, tripStatus=INCOMPLETE)

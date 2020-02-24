# booking-coding-challenge
This repository contains a solution to the booking.com coding challenge for graduates

## Requirements:
Java - 1.8.0  
Apache Maven - 3.6

## Setup

Clone repository:
```
git clone https://github.com/tudor-ruxandu/booking-coding-challenge.git
```

To compile Part1:
```
mvn clean compile assembly:single@part1
```

To compile Part2:
```
mvn clean compile package
```

_Please make sure to combine all maven steps into a single command, especially for part 1. For some reason, it doesn't work if the steps are done in different commands._

## Part 1 

### Console application

After compiling part 1, run the following command:
```
java -jar target/part1-jar-with-dependencies.jar <pickupLat> <pickupLon> <dropoffLat> <dropoffLon> [<passengers>]
```

Replace the coordinates with real numbers and optionally add the number of passengers to filter by suitable car types

Example:
```
java -jar target/part1-jar-with-dependencies.jar 1.0 2.0 3.0 4.0 5
```
where: 1.0 and 2.0 are pickup coordinates, 3.0 and 4.0 are dropoff coordinates, and 5 is number of passengers

## Part 2 

After compiling part2, start API with the following command:
```
java -jar target/booking-test-0.1.0.jar
```
The website is served at localhost on port 8080

### Sample request
```
http://localhost:8080/booking?pickup=[pickupLat,pickupLon]&dropoff=[dropoffLat,dropoffLong]&passengers=[passengers]
```

Replace the coordinate with real numbers, and optionally specify the number of passengers

Example:
```
http://localhost:8080/bookinggo?pickup=1.0,2.0&dropoff=3.0,4.0&passengers=4
```

# Quick start

* Clone the repository

```
git clone https://github.com/sinaure/sophiaEparking.git
```

* Run application using H2 db

```
mvn clean install
mvn spring-boot:run -Dspring-boot.run.profiles=test
```

* start the app in dev mode (this make use of postgresql):

```
cd docker
make run
```

* build app and upload to docker hub :

```
make build push
```

# After app bootstrap (use H2 conf for below examples)

Fixtures are loaded at application bootstrap are defined in data.sql file


# Demo
get all the parkings : their slots are all marked as availables and no car plate is marked as occupied.

* list all parkings

```
curl -g -X GET http://localhost:8080/parking

[{"id":1,"parking_name":"saint philippe","slots":[{"id":1,"slot_type":"standard","available":true,"plaque":null,"rule":{"id":1,"fix":10.00,"variable":2.70}},{"id":2,"slot_type":"standard","available":true,"plaque":null,"rule":{"id":1,"fix":10.00,"variable":2.70}},{"id":3,"slot_type":"standard","available":true,"plaque":null,"rule":{"id":1,"fix":10.00,"variable":2.70}},{"id":4,"slot_type":"standard","available":true,"plaque":null,"rule":{"id":1,"fix":10.00,"variable":2.70}},{"id":5,"slot_type":"20kw","available":true,"plaque":null,"rule":{"id":1,"fix":10.00,"variable":2.70}},{"id":6,"slot_type":"20kw","available":true,"plaque":null,"rule":{"id":1,"fix":10.00,"variable":2.70}},{"id":7,"slot_type":"20kw","available":true,"plaque":null,"rule":{"id":1,"fix":10.00,"variable":2.70}},{"id":8,"slot_type":"20kw","available":true,"plaque":null,"rule":{"id":1,"fix":10.00,"variable":2.70}},{"id":9,"slot_type":"20kw","available":true,"plaque":null,"rule":{"id":1,"fix":10.00,"variable":2.70}},{"id":10,"slot_type":"20kw","available":true,"plaque":null,"rule":{"id":1,"fix":10.00,"variable":2.70}}]}]
```

* check availability for a Client with a Standard car --> 4 slots availables

```
curl -g -X POST http://localhost:8080/parking/1/checkavailability -H "Content-Type: application/json" -d '{ "plaque" : "FFT6568J", "carType" : "standard" }'

[{"id":1,"slot_type":"standard","available":true,"plaque":null,"rule":{"id":1,"fix":10.00,"variable":2.70}},{"id":2,"slot_type":"standard","available":true,"plaque":null,"rule":{"id":1,"fix":10.00,"variable":2.70}},{"id":3,"slot_type":"standard","available":true,"plaque":null,"rule":{"id":1,"fix":10.00,"variable":2.70}},{"id":4,"slot_type":"standard","available":true,"plaque":null,"rule":{"id":1,"fix":10.00,"variable":2.70}}]

```

* Park a car --> this create a Log with a startDate that is later used for billing

```
curl -g -X POST http://localhost:8080/parking/1/park -H "Content-Type: application/json" -d '{ "plaque" : "FFT6568J", "carType" : "standard" }'

```
After the car has been parked we can see that the slot1 has been marked as notAvailable and the car plate has been added. (http://localhost:8080/parking/1)
If we check again the availability this has decreased to 3 free spots

* Bill client 

```
curl -g -X POST http://localhost:8080/parking/1/bill -H "Content-Type: application/json" -d '{ "plaque" : "FFT6568J", "carType" : "standard" }'

```

* update pricing rule for a parking 

```
curl -g -X POST http://localhost:8080/parking/1/updateRule -H "Content-Type: application/json" -d '{ "fix" : 2.2, "variable" : 1.5 }'

```

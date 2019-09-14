VERSION=1.2.1
PROJECT=asinatra
APP=parking

build:
	mvn clean install
	cp target/parking-1.0.jar .
	docker build  -t ${PROJECT}/${APP}:${VERSION} -t ${PROJECT}/${APP}:latest .
	rm parking-1.0.jar
push: 
	docker push ${PROJECT}/${APP}:${VERSION}
	docker push ${PROJECT}/${APP}:latest	
run:
	docker-compose up -d	

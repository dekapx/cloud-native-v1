# Weather API

- http://localhost:8081/weather-api/api/v1/info
- http://localhost:8081/weather-api/swagger-ui.html

```bash
# build docker image  
$ docker build -t dekapx/weather-api .

# tag the image 
$ docker tag dekapx/weather-api dekapx/weather-api:1.0

# run docker container 
$ docker run -d -p 8081:8081 --name weather-api dekapx/weather-api

# push the tagged image on docker hub
$ docker push dekapx/weather-api:1.0

# deploy to minikube
$ kubectl apply -f deploy/weather-service.yaml

# enable connection with postgres from weather-api
$ kubectl apply -f deploy/postgres-deployment.yaml

# check the IP
$ minikube ip

# check the service
$ kubectl get svc weather-api-service

# access the service
$ kubectl get services

# Command to access the service URL from minikube
$ minikube service weather-api-service 

# find the pod name for weather-api
$ kubectl get pods

# tail the logs for the weather-api pod
$ kubectl logs -f <pod-name>

# Enable docker inside minikube
eval $(minikube docker-env)
```

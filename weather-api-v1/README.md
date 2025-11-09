# Weather API

- http://localhost:8081/weather-api/api/v1/info
- http://localhost:8081/weather-api/swagger-ui.html

```bash
$ docker build -t weather-api .
$ docker run -d -p 8081:8081 --name weather-api weather-api
$ docker tag weather-api dekapx/weather-api:latest
$ docker push dekapx/weather-api:latest
# enable connection with postgres from weather-api in docker-compose
$ docker compose -f deploy/docker-compose-postgres.yaml up -d

# deploy to minikube
$ kubectl apply -f deploy/weather-api-service.yaml

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
```

```bash
kubectl apply -f postgres-deployment.yaml
kubectl apply -f demo-app-deployment.yaml
```

```bash
# Enable docker inside minikube
eval $(minikube docker-env)
```

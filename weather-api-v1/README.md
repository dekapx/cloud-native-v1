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

$ kubectl apply -f deploy/weather-api-service.yaml

# enable connection with postgres from weather-api
$ kubectl apply -f deploy/postgres-deployment.yaml
# check the service is running


$ minikube ip
$ kubectl get svc weather-api-service
$ kubectl get services 
$ minikube service weather-api-service 
  - the output contains the URL to access the service
$ kubectl get pods
  - find the pod name for weather-api
$ kubectl logs <pod-name>
  - view the logs for the weather-api pod
```

```bash
kubectl apply -f postgres-deployment.yaml
kubectl apply -f demo-app-deployment.yaml
```

```bash
# Enable docker inside minikube
eval $(minikube docker-env)

# Build the Docker image inside Minikube
docker build -t demo-app:1.0 .
```

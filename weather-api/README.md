# Weather API

- http://localhost:8081/weather-api/api/v1/info
- http://localhost:8081/weather-api/swagger-ui.html

```bash
$ docker build -t weather-api .
$ docker run -d -p 8081:8081 --name weather-api weather-api
$ docker tag weather-api dekapx/weather-api:latest
$ docker push dekapx/weather-api:latest
$ kubectl apply -f deploy/weather-api-service.yaml

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
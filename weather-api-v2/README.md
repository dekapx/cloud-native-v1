# Weather API

- http://localhost:8081/weather-api/api/v1/info
- http://localhost:8081/weather-api/swagger-ui.html


```bash
# deploy application using docker compose [docker-compose.yml]
$ docker compose up --build
```

```bash
# build docker image  
$ docker build -t dekapx/weather-api .

# tag the image 
$ docker tag dekapx/weather-api dekapx/weather-api:1.0

# run docker container 
$ docker run -d -p 8081:8081 --name weather-api dekapx/weather-api

# push the tagged image on docker hub
$ docker push dekapx/weather-api:1.0
```

```bash
# Enable docker inside minikube
$ eval $(minikube docker-env)

# Build docker image inside minikube
$ docker build -t weather-api:latest .

$ docker push weather-api:latest

# Verify the image is created
$ docker images | grep weather-api

# Apply the kubernetes deployment and service
$ kubectl apply -f deploy/

# Verify the pods are running
$ kubectl get pods

# Access the service
$ minikube service weather-api-service
```

```yaml
# weather-service.yml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: weather-api
spec:
  replicas: 1
  selector:
    matchLabels:
      app: weather-api
  template:
    metadata:
      labels:
        app: weather-api
    spec:
      containers:
        - name: weather-api
          image: dekapx/weather-api:1.0
          ports:
            - containerPort: 8081
---
apiVersion: v1
kind: Service
metadata:
  name: weather-api-service
spec:
  selector:
    app: weather-api
  ports:
    - protocol: TCP
      port: 80
      targetPort: 8081
  type: NodePort
```
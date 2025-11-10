# Weather API

- http://localhost:8081/weather-api/api/v1/info
- http://localhost:8081/weather-api/swagger-ui.html

```bash
# create a volume for postgres data persistence
$ docker volume create pgdata

# run local postgres container in docker
$ docker container run \
    --name docker-postgres -p 5432:5432 \
    -e POSTGRES_USER=dekapx -e POSTGRES_PASSWORD=passw0rd \
    -e POSTGRES_DB=testdb -v pgdata:/var/lib/postgresql/data postgres:16
```

```bash
$ docker compose -f deploy/postgres-service.yml up -d
$ docker compose -f deploy/postgres-service.yml down
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


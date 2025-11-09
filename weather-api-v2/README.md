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

```yaml demo-app-deployment.yaml 
apiVersion: apps/v1
kind: Deployment
metadata:
  name: demo-app
spec:
  replicas: 1
  selector:
    matchLabels:
      app: demo-app
  template:
    metadata:
      labels:
        app: demo-app
    spec:
      containers:
      - name: demo-app
        image: demo-app:1.0
        imagePullPolicy: Never  # Important for Minikube local images
        ports:
        - containerPort: 8080
        env:
        - name: SPRING_DATASOURCE_URL
          value: jdbc:postgresql://postgres:5432/demo_db
        - name: SPRING_DATASOURCE_USERNAME
          value: demo_user
        - name: SPRING_DATASOURCE_PASSWORD
          value: demo_pass
---
apiVersion: v1
kind: Service
metadata:
  name: demo-app
spec:
  type: NodePort
  selector:
    app: demo-app
  ports:
  - port: 8080
    targetPort: 8080
    nodePort: 30080
```

```yaml postgres-deployment.yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: postgres
spec:
  replicas: 1
  selector:
    matchLabels:
      app: postgres
  template:
    metadata:
      labels:
        app: postgres
    spec:
      containers:
      - name: postgres
        image: postgres:15
        env:
        - name: POSTGRES_DB
          value: demo_db
        - name: POSTGRES_USER
          value: demo_user
        - name: POSTGRES_PASSWORD
          value: demo_pass
        ports:
        - containerPort: 5432
        volumeMounts:
        - name: postgres-storage
          mountPath: /var/lib/postgresql/data
      volumes:
      - name: postgres-storage
        emptyDir: {}
---
apiVersion: v1
kind: Service
metadata:
  name: postgres
spec:
  selector:
    app: postgres
  ports:
  - port: 5432
    targetPort: 5432
```
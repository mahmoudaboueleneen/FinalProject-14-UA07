# Amazon Replica: A Massively Scalable Distributed Microservices E-Commerce Application

![Kubernetes](https://img.shields.io/badge/Kubernetes-326CE5.svg?style=for-the-badge&logo=kubernetes&logoColor=white)
![DigitalOcean](https://img.shields.io/badge/DigitalOcean-1433d6?style=for-the-badge&logo=digitalocean&logoColor=white&labelColor=1433d6)
![Nginx](https://img.shields.io/badge/Nginx-009639.svg?style=for-the-badge&logo=nginx&logoColor=white)
![RabbitMQ](https://img.shields.io/badge/RabbitMQ-%23FF6600.svg?style=for-the-badge&amp;logo=rabbitmq&amp;logoColor=white)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![Redis](https://img.shields.io/badge/redis-%23DD0031.svg?style=for-the-badge&logo=redis&logoColor=white)
![MongoDB](https://img.shields.io/badge/MongoDB-%234ea94b.svg?style=for-the-badge&logo=mongodb&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2CA5E0?style=for-the-badge&logo=docker&logoColor=white)
![JUnit5](https://img.shields.io/badge/Junit5-25A162?style=for-the-badge&logo=junit5&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring-boot)
![Spring Security](https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=Spring-Security&logoColor=white)
![Spring Cloud](https://img.shields.io/badge/Spring_Cloud-6DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![GitHub Actions](https://img.shields.io/badge/Github%20Actions-282a2e?style=for-the-badge&logo=githubactions&logoColor=367cfe)
![Bash](https://img.shields.io/badge/Bash-4EAA25?style=for-the-badge&logo=gnubash&logoColor=white)
![OpenTelemetry](https://img.shields.io/badge/OpenTelemetry-000000.svg?style=for-the-badge&logo=opentelemetry&logoColor=white)
![Grafana](https://img.shields.io/badge/Grafana-F46800.svg?style=for-the-badge&logo=grafana&logoColor=white)
![Prometheus](https://img.shields.io/badge/Prometheus-000000?style=for-the-badge&logo=prometheus&labelColor=000000)
![Loki](https://img.shields.io/badge/Loki-F46800.svg?style=for-the-badge&logo=grafana&logoColor=white)
![Tempo](https://img.shields.io/badge/Tempo-3B4EFF.svg?style=for-the-badge&logo=grafana&logoColor=white)

## Builds

![build](https://github.com/mahmoudaboueleneen/FinalProject-14-UA07/actions/workflows/ci.yml/badge.svg)
![build](https://github.com/mahmoudaboueleneen/FinalProject-14-UA07/actions/workflows/cd.yml/badge.svg)

## Table of Contents

1. [Project Overview](#project-overview)
2. [System Design](#system-design)
3. [Components](#components)
    - [Microservices, Databases & Caching](#microservices-databases--caching)
        - [Users Microservice](#users-microservice)
        - [Transactions Microservice](#transactions-microservice)
        - [Merchants Microservice](#merchants-microservice)
        - [Notifications Microservice](#notifications-microservice)
        - [Search Microservice](#search-microservice)
        - [API Documentation](#api-documentation)
    - [Message Queues](#message-queues)
    - [API Gateway](#api-gateway)
4. [Observability](#observability)
    - [Metrics](#metrics)
    - [Logging](#logging)
    - [Tracing](#tracing)
5. [CI/CD](#cicd)
6. [Developing with Docker Compose](#local-development-with-docker-compose)
7. [Deployment to Kubernetes Local Cluster (Minikube)](#deployment-to-kubernetes-local-cluster-minikube)
8. [Testing](#testing)
9. [Contributors & Teams](#contributors--teams)
10. [License](#license)
11. [Credits](#credits)

## Project Overview

This project is a distributed microservices E-commerce application that allows merchants to list products and customers to purchase products using wallet, credit card (Stripe), or cash on delivery (COD), generate invoices, and admins to view sales reports for the system. 
The system is designed to be massively scalable, with each microservice handling a specific domain of the application. The architecture is built using Spring Boot for the backend, with various databases and caching mechanisms to ensure high availability and performance, and uses Kubernetes for a highly available and scalable deployment,
and is designed to be observability-first, with built-in support for metrics, logging, and tracing.

## System Design

![system_design](docs/images/architecture-diagram.png)

## Components

### Microservices, Databases & Caching

#### 👥 Users Microservice

#### 💳 Transactions Microservice

#### 🔎 Search Microservice

#### 🚚 Merchants Microservice

#### 🔔 Notifications Microservice

#### 📰 API Documentation

This project uses OpenAPI 3.0 for API documentation for each microservice. The API documentation is generated automatically from the code using the Springdoc OpenAPI library.

For example, the API documentation for the Users microservice is available at:

```
http://localhost:8085/swagger-ui/index.html
```

![Users Microservice API Documentation](./docs/images/swagger.jpg)

### 📬 Message Queues

This project uses RabbitMQ 🐰 as the message queue for asynchronous communication between microservices. Each microservice that needs to send or receive messages from the message queue has a dedicated RabbitMQ exchange and queue.

### 🌐 API Gateway

## 🔭 Observability

![Observability](./docs/images/observability.jpg)

### Metrics

### Logging

### Tracing

## CI/CD

This project uses GitHub Actions for continuous integration and continuous deployment (CI/CD). The CI/CD pipeline is defined in the `.github/workflows` directory.

All changes to the `main` branch require at least one approval from a code owner of the part(s) of the codebase that were modified, as defined in the `CODEOWNERS` file in the `.github` directory.

### Continuous Integration (CI) Pipeline

The CI pipeline is triggered on every push to the `main` branch and on pull requests. It checks out the code, sets up the JDK, and builds the project using Maven to ensure that the project builds successfully. Finally, it applies formatting to the code using Maven Spotless plugin to ensure that the code adheres to the project's coding standards.

### Continuous Deployment (CD) Pipeline

The CD pipeline is triggered on every push to the `main` branch. It does the following:
1. Builds the project using Maven.
2. Builds Docker images for each microservice that was changed and pushes them to our Docker Hub registry/repository.
3. Deploys the updated microservices to the Kubernetes cluster using the deployment files in the `k8s` directory.
4. Notifies the team via our Discord server about the deployment status, timestamp, commit, author and jobs.

    ![Discord Notification](./docs/images/discord.png)

## Local Development with Docker Compose

This project uses Docker Compose to run the entire system locally for development purposes. In each microservice folder (e.g. `services/users`, `services/transactions`, etc.), there is a `docker-compose.yml` file that defines the service and its dependencies (e.g. its database, cache).
This can be used to run each microservice independently for development and testing purposes if the microservice does not depend on other microservices or on the message queue.

To run the entire system locally, you can use the provided `docker-compose.yml` file in the root directory of the project. This file defines all the services, databases, and message queue needed to run the application, also including the API Gateway and the observability stack (Prometheus, Grafana, Loki, Tempo).

The following commands are generally required for either case.

1. First, install the Docker plugin for Loki, which is used for logging.

    ```bash
    docker plugin install grafana/loki-docker-driver:2.9.2 --alias loki --grant-all-permissions
    ```

2. Then, build the project and start the services using Docker Compose.

    ```bash
    mvn clean install -DskipTests
    docker compose up -d --build
    ```

3. Install stripe cli from this [link](https://github.com/stripe/stripe-cli/releases/tag/v1.27.0) and add its path to your path environment variable on your operating system.
This is necessary to enable the Stripe payment functionality through a stripe sandbox in the Transactions microservice.

4. After that, you can run the following command to start listening to Stripe events and forward them to the Transactions microservice webhook endpoint.
    ```
    stripe login
    stripe listen --forward-to localhost:8084/stripe/webhook
    ```
5. Copy the webhook secret that is printed in the terminal after running the previous command, and paste it in the `application.yml` file of the Transactions microservice in `services/transactions`.
6. Download the Postman collection from the `docs/postman` directory and import it into Postman.
7. Update the Postman collection's `host` variable in the `local` environment to the URL of the API Gateway or the Microservice you want to interact with.

## 🚀 Deployment to Kubernetes Local Cluster (Minikube)

This system is designed to be deployed on a Kubernetes cluster. The deployment files are located in the `k8s` directory. 

To deploy the system on a local Kubernetes cluster, we use Minikube. To set up Minikube, follow these steps:

1. Install Minikube by following the instructions on the [Minikube website](https://minikube.sigs.k8s.io/docs/start/).
2. Install Kubectl by following the instructions on the [Kubernetes website](https://kubernetes.io/docs/tasks/tools/).
3. Start Minikube by running the following command (you can adjust the memory and CPU according to your machine's specifications, but lower values may lead to issues or pods dying due to out of memory OOM errors):

   ```bash
   minikube start --driver=docker --memory=7000 --cpus=8
   ```
4. Run our deployment script to deploy the system to the Minikube (Note: This script is used instead of the `kubectl apply -f ./k8s/ -r` command as it does some additional setup work to set up the Stripe CLI sandbox and dynamically update the K8s secret with the Stripe webhook secret after its deployment):

   ```bash
   ./bash/deploy.sh
   ```
5. Expose the API Gateway service to access it from outside the cluster:

   ```bash
   minikube service apigateway-service
   ```
6. Copy the API Gateway URL that will be opened in your browser. This URL will be used to access the API Gateway and, consequently, the entire system.
7. Download the Postman collection from the `docs/postman` directory and import it into Postman.
8. Update the Postman collection's `host` variable in the `local` environment to the URL of the API Gateway you previously copied.
9. Expose the Grafana service to access it in your browser:
   
   ```bash
   minikube service grafana
   ```
10. Log in to Grafana using the default credentials:
    - Username: `admin`
    - Password: `admin`

## 🧪 Testing

Our system is designed to be tested using JUnit 5 with Spring Boot Test. 

## Contributors & Teams

This project is a collaborative effort of 15 extraordinary engineers split into 3 teams of 5, each responsible for different microservices and components of the system. 

See, additionally, the AUTHORS file for a list of the contributors to this project.

### Team A

This team is responsible for the Users Microservice, API Gateway, CI/CD, Observability, and Kubernetes deployment, service discovery, and load balancing.

- [Mahmoud Abou Eleneen (@mahmoudaboueleneen) - Scrum Master & Team A Leader](https://www.github.com/mahmoudaboueleneen)
- [Maya Khaled Saad (@mayaasaadd)](https://www.github.com/mayaasaadd)
- [Abdelrahman Abouelkheir (@abdelrahmanAbouelkheir)](https://www.github.com/abdelrahmanAbouelkheir)
- [Marwan Amgad (@marwanamgad)](https://www.github.com/marwanamgad)
- [Ahmed Sherif Said (@Ahmedsherif74)](https://www.github.com/Ahmedsherif74)

### Team B

This team is responsible for the Transactions and Notifications Microservices, as well as the Message Queues and the Caching for their microservices.

- [Ibrahim Soltan (@Ibrahim-Soltan) - Team B Leader](https://www.github.com/Ibrahim-Soltan)
- [Hamza Gehad (@Hamza-gehad)](https://www.github.com/Hamza-gehad)
- [Mohamed Ahmed Elfar (@mohamedahmedelfar)](https://www.github.com/mohamedahmedelfar)
- [Khaled Magdy (@khaledmagdy-oss)](https://www.github.com/khaledmagdy-oss)
- [Remas Osama (@RemasOsama)](https://www.github.com/RemasOsama)

### Team C

This team is responsible for the Merchants and Search Microservices, as well as the Caching for their microservices.

- [Ahmed Sherif (@AhmedSherif9) - Team C Leader](https://www.github.com/AhmedSherif9)
- [Mohamed Ahmed Aly (@mohamed-ahmed121)](https://www.github.com/mohamed-ahmed121)
- [Ahmed Hassaballah (@hassaballah17)](https://www.github.com/hassaballah17)
- [Kirollos Naguib (@kirollosn)](https://www.github.com/kirollosn)
- [Youssef Khawaga (@khawagaa)](https://www.github.com/khawagaa)

## License

This project is licensed under the GNU General Public License v3.0. See the [LICENSE](./LICENSE) file for more details.

## Credits

- [Workup - GitHub Repo](https://github.com/Ahmad45123/workup/blob/main/README.md)
- Architecture of Massively Scalable Applications course, the German University in Cairo

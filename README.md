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
![OpenTelemetry](https://img.shields.io/badge/OpenTelemetry-000000.svg?style=for-the-badge&logo=opentelemetry&logoColor=white)
![Grafana](https://img.shields.io/badge/Grafana-F46800.svg?style=for-the-badge&logo=grafana&logoColor=white)
![Prometheus](https://img.shields.io/badge/Prometheus-000000?style=for-the-badge&logo=prometheus&labelColor=000000)

[//]: # (![Loki]&#40;https://img.shields.io/badge/Loki-FFCB2B.svg?style=for-the-badge&logo=grafana-loki&logoColor=black&#41;)

[//]: # (![Tempo]&#40;https://img.shields.io/badge/Tempo-3B4EFF.svg?style=for-the-badge&logo=grafana-tempo&logoColor=white&#41;)

## Builds

## Table of Contents

## Project Overview

Amazon Replica is a massively scalable distributed microservices e-commerce application. It is designed to handle a large number of concurrent users and transactions, making it suitable for high-traffic e-commerce platforms. The application is built using modern technologies and follows best practices in software development.

## System Architecture

![system_design](docs/images/architecture-diagram.png)

## Components

## 🔭 Observability

## Developing with Docker Compose

```
docker plugin install grafana/loki-docker-driver:2.9.2 --alias loki --grant-all-permissions
```

```
mvn clean install -DskipTests
```

```
docker compose up -d --build
```

Install stripe cli from this [link](https://github.com/stripe/stripe-cli/releases/tag/v1.27.0) and add its path to your path environment variable.

```
stripe login
stripe listen --forward-to localhost:8084/stripe/webhook
```

## Deployment to Kubernetes Local Cluster (Minikube)

## Contributors

See the AUTHORS file for a list of contributors to this project.

## License

This project is licensed under the GNU General Public License v3.0. See the [LICENSE](./LICENSE) file for more details.

## Credits

- [Workup](https://github.com/Ahmad45123/workup/blob/main/README.md)
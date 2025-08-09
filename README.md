# JFK Airline Baggage Tracking System

A real-time baggage tracking system for airline operations built with modern cloud technologies and enterprise best practices.

## Architecture

- **Backend**: Java 17 + Spring Boot 3.x
- **Frontend**: React 18 + TypeScript
- **Database**: PostgreSQL 14+ on AWS RDS
- **Infrastructure**: AWS (VPC, RDS, ECS, S3, CloudFront)
- **DevOps**: Terraform, Docker, GitHub Actions

### Phase 1 Complete: Infrastructure Foundation
- AWS VPC with Multi-AZ setup
- PostgreSQL RDS database with encryption
- Security groups and network isolation
- Secrets management for credentials
- Infrastructure as Code with Terraform

### Phase 2 In Progress: Backend Development
- Spring Boot application structure
- Database schema and JPA entities
- REST APIs for baggage tracking
- Authentication and security

## Prerequisites

- AWS Account with billing alerts configured
- AWS CLI installed and configured
- Terraform 1.6+ installed
- Git for version control

## Quick Start

### Infrastructure Deployment
```bash
# Clone repository
git clone https://github.com/YOUR_USERNAME/airline-baggage-system.git
cd airline-baggage-system

# Deploy infrastructure
cd infrastructure/terraform
terraform init
terraform validate
terraform plan
terraform apply

# Verify deployment
terraform output
```

### Database Connection
```bash
# Get database endpoint
terraform output -raw database_endpoint

# Get database password
aws secretsmanager get-secret-value \
  --secret-id baggage-system-db-password-dev \
  --query SecretString --output text
```

## Project Structure
```
airline-baggage-system/
├── .gitignore                  # Git ignore rules
├── README.md                   # This file
└── infrastructure/
    └── terraform/
        ├── database.tf         # RDS configuration
        ├── main.tf            # VPC and networking
        ├── outputs.tf         # Infrastructure outputs
        └── variables.tf       # Configuration variables
```

## Infrastructure Details

### AWS Resources Created
- **VPC**: `10.0.0.0/16` with public/private subnets
- **RDS**: PostgreSQL database with Multi-AZ backup
- **Security Groups**: Least privilege access controls
- **Secrets Manager**: Encrypted credential storage
- **Internet Gateway**: Public internet access

### Database Configuration
- **Engine**: PostgreSQL 14.10
- **Instance**: db.t3.micro (Free tier eligible)
- **Storage**: 20GB with auto-scaling to 100GB
- **Backup**: 7-day retention with automated backups
- **Encryption**: At rest with AWS KMS

## Cost Estimation

**Monthly AWS Costs (Development)**:
- RDS db.t3.micro: ~$12-15/month
- VPC/Networking: Free tier
- Secrets Manager: ~$0.40/month
- **Total**: ~$15/month

## Security Features

- **Network Isolation**: Private subnets for database
- **Encryption**: TLS 1.3 in transit, AES-256 at rest
- **Access Control**: Security groups with minimal permissions
- **Credential Management**: AWS Secrets Manager integration
- **Infrastructure**: Immutable infrastructure with Terraform

### Phase 2: Backend Services
- Spring Boot microservices
- JPA/Hibernate data layer
- REST API development
- JWT authentication
- Docker containerization

### Phase 3: Frontend Applications
- React passenger portal
- React staff dashboard
- React Native mobile app
- Real-time WebSocket updates

### Phase 4: Production Deployment
- ECS container orchestration
- Load balancer configuration
- CI/CD pipeline automation
- Monitoring and alerting

## Learning Objectives

This project demonstrates enterprise-level skills in:
- **Cloud Infrastructure**: AWS services and architecture
- **Infrastructure as Code**: Terraform best practices
- **Database Design**: PostgreSQL and data modeling
- **Microservices**: Spring Boot and REST APIs
- **Frontend Development**: React and TypeScript
- **DevOps**: CI/CD, containerization, monitoring

## Contributing

1. Fork the repository
2. Create feature branch (`git checkout -b feature/amazing-feature`)
3. Commit changes (`git commit -m 'Add amazing feature'`)
4. Push to branch (`git push origin feature/amazing-feature`)
5. Open Pull Request

## 📄 License

---

**Built with ❤️ for learning enterprise software development and cloud architecture.**
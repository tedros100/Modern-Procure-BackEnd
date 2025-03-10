# ModernProcure Backend

## Setup Instructions

### Prerequisites
- Java 17
- Maven
- Docker (for containerization)
- PostgreSQL

### Local Development
1. Build the project:
```bash
mvn clean package
```

2. Run the application:
```bash
java -jar target/*.jar
```

### Docker Setup
1. Build the image:
```bash
docker build -t modernprocure-backend .
```

2. Run the container:
```bash
docker run -p 8081:8081 modernprocure-backend
```

### Environment Variables
Required environment variables:
```
SPRING_DATASOURCE_URL=${DATABASE_URL}
SPRING_DATASOURCE_USERNAME=${DB_USERNAME}
SPRING_DATASOURCE_PASSWORD=${DB_PASSWORD}
AUTH_TOKEN_URL=${AUTH_TOKEN_URL}
AUTH_CLIENT_ID=${CLIENT_ID}
AUTH_CLIENT_SECRET=${CLIENT_SECRET}
SERVER_PORT=8081
SPRING_PROFILES_ACTIVE=prod
```

### Project Structure
```
modernprocure/
├── src/                # Source code
│   ├── main/          # Main application code
│   └── test/          # Test code
├── Dockerfile         # Docker configuration
└── pom.xml           # Maven configuration
```

### API Endpoints
- Health Check: `/actuator/health`
- API Documentation: `/swagger-ui.html`

### Deployment
1. Push to GitHub:
```bash
git add .
git commit -m "Update backend"
git push origin main
```

2. Railway will automatically deploy from the main branch

### Health Checks
- Endpoint: `/actuator/health`
- Interval: 30s
- Timeout: 10s
- Retries: 3

### Troubleshooting
1. Build Issues
   - Clear Maven cache
   - Verify Java version
   - Check for dependency conflicts

2. Runtime Issues
   - Check environment variables
   - Verify database connection
   - Review application logs

3. Docker Issues
   - Verify Docker installation
   - Check port conflicts
   - Review container logs

### Database Migration
1. Check current status:
```bash
mvn flyway:info
```

2. Run migrations:
```bash
mvn flyway:migrate
```

3. Repair if needed:
```bash
mvn flyway:repair
```
# pour-hub
### Overview
Pour Hub explores microservices through an API-driven beer ordering system. Users can place orders for various beers via API requests. The system handles the entire process, from order placement to preparation and readiness notification.  
It consists of four main components:
1. API Gateway
2. Discovery Server
3. Hub Service
4. Pour Service

### Architecture
* **API Gateway:** Acts as the single entry point for all client requests, routing them to the appropriate microservice.
* **Discovery Server:** Manages service discovery using Spring Cloud Netflix Eureka.
* **Hub Service:** Handles order processing and coordinates with the Pour Service to manage beer orders.
* **Pour Service:** Manages beer information and handles the pouring process.

### Requirements
* Java 17 or higher
* Spring Boot
* Spring Cloud Netflix Eureka
* Spring Cloud Gateway
* Maven for dependency management

### Setup
1. **Clone the Repository:**
   ```
   git clone https://github.com/dnnsvrmln/pour-hub.git
   cd pour-hub
2. **Build the Project:**
   ```
   mvn clean install
3. **Configuration**  
   Each service has its own configuration file (application.properties). You can set the number of bartenders on duty by specifying the bartenders.on.duty environment variable in the hub-service configuration:
   ```
   bartenders.on.duty=2
4. **Running the Services**  
   Start the services in the following order:
   1. Discovery Server
   2. API Gateway
   3. Hub Service
   4. Pour Service  
   
   **Note:** Wait for about 30 seconds to 1 minute after starting all services before making any requests to ensure all services are properly registered and available.

### API Endpoints
**API Gateway**  
* GET /v1/api/beer: Retrieve all available beers.
* GET /v1/api/beer/{id}: Retrieve a single available beer.
* POST /v1/api/order: Place an order.
* POST /v1/api/order/another-round-of-beers/{id}: Place Another Round of Beers order.
* GET /v1/api/order/{id}

### Example Usage
**Placing an Order**  
```
curl -X POST "http://localhost:8080/v1/api/order" -H "Content-Type: application/json" -d '{
    "orderItemsDto": [
        {"beerId": 1, "quantity": 2},
        {"beerId": 2, "quantity": 1}
    ]
}'
```

**Retrieving all Available Beers**
```
curl -X GET "http://localhost:8080/v1/api/beer"
```
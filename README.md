# Komdatis Backend for Connector

Backend for Communication between Connector and Data Dashboard to CRUD Files

## Start App

Run the Database

```bash
sudo docker compose up -d db
```

Build all Images that are defined in Docker Compose File

```bash
sudo docker compose build
```

Run the Backend

```bash
sudo docker compose up --build -d backend
```

## Usage

### Create a building

```bash
curl -X POST http://localhost:8080/api/buildings/certificate \
     -H "Content-Type: application/json" \
     -d '{
         "firstName": "Alfred",
         "lastName": "Schneider",
         "address": "Otto-Hahn-Str, 44227 Dortmund",
         "livingSpace": 150,
         "warmth": [20966.91, 20942.03, 20936.56, 20969.91, 20950.60, 20972.58],
         "warmWater": [1795.41, 1795.36, 1796.25, 1796.66, 1798.11, 1799.56],
         "heatedBasement": true,
         "apartments": 1
     }'
```

### Generate an energy certificate for a building

```bash
curl -X GET http://localhost:8080/api/certificates/building/{id} \
```
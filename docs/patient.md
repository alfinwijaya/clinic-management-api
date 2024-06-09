# Patient API Spec

## Create Patient

Endpoint : POST /api/patients

Request Body :

```json
{
  "username": "billy",
  "name": "Billy",
  "birthDate": "2024-01-01",
  "email": "billy@email.com",
  "phoneNumber": "01111222333"
}
```

Response Body (Success) :

```json
{
  "data": "OK"
}
```

Response Body (Failed) :

```json
{
  "data": null,
  "errors": "Username already exists"
}
```

## Get Patient

GET /api/patients

Response Body (Success) :

```json
{
  "data": [
    {
      "username": "billy",
      "name": "Billy",
      "birthDate": "2023-12-31T17:00:00.000+00:00",
      "email": null,
      "phoneNumber": null
    }
  ],
  "errors": null
}
```

## Get Patient by Username

GET /api/patients/{username}

Response Body (Success) :

```json
{
  "data": {
    "username": "billy",
    "name": "Billy",
    "birthDate": "2023-12-31T17:00:00.000+00:00",
    "email": null,
    "phoneNumber": null
  },
  "errors": null
}
```

Response Body (Failed, 404) :

```json
{
  "data": null,
  "errors": "Patient not found"
}
```

## Delete Patient

DELETE /api/patients/{username}

Response Body (Success) :

```json
{
  "data": "OK",
  "errors": null
}
```

Response Body (Failed, 404) :

```json
{
  "data": null,
  "errors": "Patient not found"
}
```
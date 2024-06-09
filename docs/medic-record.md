# Medic Record API Spec

## Create Medic Record

Endpoint : POST /api/medic_records

Request Body :

```json
{
  "username": "alfin",
  "diagnosis": "flu",
  "medicalPrescription": "panadol",
  "treatmentSuggestion": "rest"
}
```

Response Body (Success) :

```json
{
  "data": {
    "id": "04338d79-df19-46e0-bf7c-88a8168cec09",
    "username": "billy",
    "name": "Billy",
    "birthDate": "2023-12-31T17:00:00.000+00:00",
    "email": null,
    "phoneNumber": null,
    "diagnosis": "flu",
    "medicalPrescription": "panadol",
    "treatmentSuggestion": "rest"
  },
  "errors": null
}
```

Response Body (Failed) :

```json
{
  "data": null,
  "errors": "Patient not found"
}
```

## Get Medic Record

GET /api/medic_records

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

## Get Medic Record by Id

GET /api/medic_records/{id}

Response Body (Success) :

```json
{
  "data": {
    "id": "04338d79-df19-46e0-bf7c-88a8168cec09",
    "username": "billy",
    "name": "Billy",
    "birthDate": "2023-12-31T17:00:00.000+00:00",
    "email": null,
    "phoneNumber": null,
    "diagnosis": "flu",
    "medicalPrescription": "panadol",
    "treatmentSuggestion": "rest"
  },
  "errors": null
}
```

Response Body (Failed, 404) :

```json
{
  "data": null,
  "errors": "Medic record not found"
}
```

## Delete Medic Record

DELETE /api/medic_records/{id}

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
  "errors": "Medic record not found"
}
```
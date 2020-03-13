# WEB SERVICE SECURED WITH TOKEN AND LDAP AUTHENTICATION

This web api provide acces to JSON example data. Secured services require authorised token. App issue token with /authenticate service. User data is stored in LDAP server.

## JSON Objects

"Example" object:
```bash
{
      "id": 1,
      "name": "name",
      "goal": "goal"
}
```
"User" object:

```bash
{
    "username": "user name",
    "password": "pass"
}
```
## Usage

App contains one 2 services. One for authentication and one for accessing data.

Get all object from file:
```bash
GET
http://localhost:8080/Secured-API-Example/examples  #available without authentication
```

Get object by ID:
```bash
GET
http://localhost:8080/Secured-API-Example/examples/{id} #authentication required(berear token)
```

Create object:
```bash
POST
http://localhost:8080/Secured-API-Example/examples #authentication required(berear token)
```

Get token with credentials
```bash
POST
http://localhost:8080/Secured-API-Example/authenticate
BODY:
{
    "username": "user",
    "password": "pass"
}
```


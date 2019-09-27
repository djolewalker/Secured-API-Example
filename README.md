# WEB SERVICE SECURED WITH TOKEN
# AND LDAP AUTHENTICATION

This web api provide JSON example data. 

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
http://localhost:8080/securedAppExample/examples  #available without authentication
```

Get object by ID:
```bash
GET
http://localhost:8080/securedAppExample/examples/{id} #authentication required(berear token)
```

Create object:
```bash
POST
http://localhost:8080/securedAppExample/examples #authentication required(berear token)
```

Get token with credentials
```bash
POST
http://localhost:8080/securedAppExample/authenticate
BODY:
{
    "username": "user",
    "password": "pass"
}
```


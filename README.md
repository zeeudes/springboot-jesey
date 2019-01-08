## Informações gerais

Após iniciar o projeto, a aplicação estará disponível em `http://localhost:8080/`.

Há três usuários cadastrados, veja o arquivo SQL na pasta de recursos.

## Visão geral da API

#####/signin

```bash
curl -X POST \
  'http://localhost:8080/signin' \
  -H 'Accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
  "email": "<email>",
  "password": "<password>"
}'
```

#####/signup

```bash
curl -X POST \
    'http://localhost:8080/me' \
    -H "Content-type: application/json" \
    -H 'Accept: application/json' \
    -d '{
        "firstName": "<firstName>",
        "lastName": "<lastName>",
        "email": "<email>",
        "phones": [
            {
                "number": <number>,
                "area_code": <area_code>,
                "country_code": "<country_code>"
            }
        ]
    }' 
```

#####/me

```bash
curl -X GET \
  'http://localhost:8080/me' \
  -H 'Accept: application/json' \
  -H 'Authorization: Bearer <authentication-token>'
```

## Acesso ao projeto no Heroku

Link: https://agile-oasis-82589.herokuapp.com/
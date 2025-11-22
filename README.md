JavaUsers это сервис для взаимодействия с логикой юзеров в проектах где они есть.

# Ports

- 8092 - users service
- 5433 - users db

<br>

# Users endpoints

## API methods

### Default users 

link -> http://localhost:8092/api/auth/ 

1) POST login -> body
```
body

{
    "email": "email",
    "password": "password"
}
```
- Response: возвращает объект с JWT токеном, который нужно сохранить 
и использовать в заголовке Authorization для последующих запросов

2) POST register -> body
```
body

{
    "nickname": "nickname",
    "email": "email",
    "password": "password"
}
```

3) POST logout 
```
headers

Authorization: Bearer <your_jwt_token>
```

### Admin users

link -> http://localhost:8092/api/admin/

1) GET getAll 

2) GET getById/{id} 

3) GET getByEmail/{email} 

4) GET getByName/{name} 

5) GET search?params=values
```
params

nickname - схожести в никнейме
email - схожести в электронной почте
role - схожести в роли
```

6) POST registerAsAdmin -> body
```
body

{
    "nickname": "nickname",
    "email": "email",
    "password": "password"
}
```

7) PUT update/{id} -> body
```
body

{
    "nickname": "nickname",
    "email": "email",
    "password": "password"
}
```

8) DELETE delete/{id}

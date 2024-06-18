# E-Commerce Project

- This E-Commerce API project developed using by Java & Spring Boot. Project provides endpoints to manage products, stores, roles, reviews and other features of e-commerce websites.


## Used technologies

- Java 
- Spring Boot
- Spring Data Jpa
- Spring Security
- JWT
- MySQL


## API Endpoints

#### Auth Controller

| Type | Request     |
| :-------- | :------- |
| `POST` | `/api/auth/login` |
| `POST`| `/api/auth/register`|

#### Answer Controller

| Type | Request     |
| :-------- | :------- |
| `GET` | `/api/answers/{id}` |
| `GET`| `/api/questions/{id}/answers`|
| `POST`| `/api/questions/{id}/answers`|
| `PUT`| `/api/answers/{id}`|
| `DELETE`| `/api/answers/{id}`|

#### Category Controller

| Type | Request     | 
| :-------- | :------- | 
| `GET` | `/api/categories/{id}` |
| `GET`| `/api/categories`|
| `POST`| `/api/categories`|
| `PUT`| `/api/categories/{id}`|
| `DELETE`| `/api/categories/{id}`|

#### Order Controller

| Type | Request     |
| :-------- | :------- |
| `GET` | `/api/orders/{id}` |
| `GET`| `/api/orders`|
| `POST`| `/api/orders`|
| `DELETE`| `/api/orders/{id}`|

#### Product Controller

| Type | Request     |
| :-------- | :------- |
| `GET` | `/api/products/{id}` |
| `GET`| `/api/products`|
| `POST`| `/api/stores/{id}/products`|
| `PUT`| `/api/products/{id}`|
| `DELETE`| `/api/products/{id}`|

#### Question Controller

| Type | Request     |
| :-------- | :------- |
| `GET` | `/api/questions/{id}` |
| `GET`| `/api/questions`|
| `POST`| `/api/products/{id}/questions`|
| `DELETE`| `/api/questions/{id}`|

#### Review Controller

| Type | Request     |
| :-------- | :------- |
| `GET` | `/api/reviews/{id}` |
| `GET`| `/api/reviews`|
| `POST`| `/api/products/{id}/reviews`|
| `PUT`| `/api/reviews/{id}`|
| `DELETE`| `/api/reviews/{id}`|

#### Review Controller

| Type | Request     |
| :-------- | :------- |
| `GET` | `/api/roles/{id}` |
| `GET`| `/api/roles`|
| `POST`| `/api/roles`| 
| `PUT`| `/api/roles/{id}`|
| `DELETE`| `/api/roles/{id}`|

#### Store Controller

| Type | Request     |
| :-------- | :------- |
| `GET` | `/api/stores/{id}` | 
| `GET`| `/api/stores`|
| `POST`| `/api/stores`|
| `PUT`| `/api/stores/{id}`|
| `DELETE`| `/api/stores/{id}`|

#### Favorite Controller

| Type | Request                        |
| :-------- |:-------------------------------|
| `GET` | `/api/favorites/{id}`          | 
| `GET`| `/api/users/{id}/favorites`    |
| `POST`| `/api/products/{id}/favorites` |
| `DELETE`| `/api/favorites/{id}`          |
| `DELETE`| `/api/products/{id}/favorites` |
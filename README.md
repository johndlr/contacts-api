# Contact Management Application

This is a contact management application that provides CRUD (Create, Read, Update, Delete) operations to manage users' contacts. The application is implemented using two different approaches: the functional approach using **Spring Cloud Function** and the traditional approach using Spring Web. There are **three branches** for this project, each representing one approach to the application. An extra branch is added that represents the application that was deployed to **Azure.**

## Approaches

### Functional Approach

In the functional approach, Spring Cloud Function is used to implement the CRUD operations. This approach removes the controller and service layers and instead defines functions to handle the requests.

#### Technologies Used

- Spring Cloud Function
- Spring Boot
- Spring Data
- H2 Database
- Maven
- Lombok
- Spring Validation
- Devtools
- Postman

#### Spring Cloud Function Configuration

The Spring Cloud Function configuration is located in the application.yml file.

#### Endpoints

- **Create Contact**: `POST /createContact`
- **Delete Contact**: `DELETE /deleteContact`
- **Update Contact**: `POST /updateContact`
- **Fetch Contact**: `GET /fetchContact`
- **Fetch Contacts with Pagination**: `POST /fetchContacts`

#### Example Requests

- **Create Contact**:
  ```json
  POST /createContact
  {
    "name": "Tony",
    "lastName": "Stark",
    "phoneNumber": "5912354784",
    "email": "tonystark@example.com"
  }
  
- **Delete Contact**:
  ```json
  DELETE /deleteContact/5912354784

- **Update Contact**:
  ```json
  POST /updateContact
  {
    "name": "Tony",
    "lastName": "Stark",
    "phoneNumber": "5912354784",
    "email": "tonystark@avengers.com"
  }

- **Fetch Contact**:
  ```json
  GET /fetchContact/5912354784

- **Fetch Contacts**:
  ```json
  POST /fetchContacts
  {
    "pageNumber": 0,
    "pageSize": 10,
    "sortProperty": "name"
  }

### Traditional Approach

In the traditional approach, Spring Web is used to implement the CRUD operations. This approach uses controllers and services to handle the requests. Unit and integration tests were also added.

#### Technologies Used

- Spring Web
- Spring Boot
- Spring Data
- H2 Database
- Maven
- OpenApi
- Lombok
- Spring Validation
- Devtools
- JUnit
- AssertJ
- Mockito
- Postman

#### Endpoints

- **Create Contact**: `POST /api/v1/create`
- **Delete Contact**: `DELETE /api/v1/delete/?phoneNumber=5515784532`
- **Update Contact**: `PUT /api/v1/update`
- **Fetch Contact**: `GET /api/v1/contact/?phoneNumber=5515784532}`
- **Fetch Contacts with Pagination**: `GET /api/v1/contacts?pageNumber=0&pageSize=5&sortProperty=name`
  
Below are some screenshots from the Swagger UI API documentation:


![Swagger UI](https://github.com/user-attachments/assets/1e1be5d0-f252-4e10-a04a-a1053b90a778)

![Swagger UI](https://github.com/user-attachments/assets/d1524238-b731-4aa0-8f0d-577fa2e23a95)

### Deployment to Azure App Services

The application was deployed to **Azure App Services** using **GitHub Actions**, and some changes were made to the application, specifically the implementation of the H2 database was changed to a persistent one such as **Azure MySQL**.

Below are some screenshots of the implementation in Azure:

![Screenshot 2024-10-10 180845](https://github.com/user-attachments/assets/33682bff-3749-45cb-b5ff-b5da02f4ef75)

![Screenshot 2024-10-10 181015](https://github.com/user-attachments/assets/a0f5dc0e-4a68-431c-ad5e-5de728fecff3)

![Screenshot 2024-10-10 181008](https://github.com/user-attachments/assets/f1e8f1a6-ab5e-4780-91e0-1bd86e5504fb)

![Screenshot 2024-10-10 180920](https://github.com/user-attachments/assets/bf419890-67fa-4cbe-917c-1f61f8a3d30b)

![Screenshot 2024-10-10 182813](https://github.com/user-attachments/assets/ab40de4a-a394-4497-81a3-e269d553ec9c)

## License

This application is licensed under the MIT License. See the LICENSE file for more details.

# 💼 Job Application Portal

A Job Application Tracking System built using Java Spring Boot and MySQL to manage and track job applications efficiently.

---

## 🚀 Features

- Add and manage job applications
- Update application status (Applied, Interview, Rejected, Accepted)
- View all applications
- RESTful API architecture

---

## 🛠️ Tech Stack

- Java
- Spring Boot
- MySQL
- Maven

---

## 📂 Project Structure


job-application-portal/
│
├── controller
├── service
├── repository
├── model
└── resources


---

## ⚙️ Setup Instructions

### 1. Clone Repository


git clone https://github.com/your-username/job-application-portal.git

cd job-application-portal


### 2. Configure MySQL

```sql
CREATE DATABASE job_portal;

Update application.properties:

spring.datasource.url=jdbc:mysql://localhost:3306/job_portal
spring.datasource.username=your_username
spring.datasource.password=your_password
3. Run Application
mvn spring-boot:run
📡 API Endpoints
Method	Endpoint	Description
GET	/applications	Get all applications
POST	/applications	Create application
PUT	/applications/{id}	Update application
DELETE	/applications/{id}	Delete application
👨‍💻 Author

Rohit Dogra


---

# 💻 Method 2: Using Git (Terminal)

If you prefer coding style:

```bash
touch README.md

Open it and paste the content, then:

git add README.md
git commit -m "Added README file"
git push origin main

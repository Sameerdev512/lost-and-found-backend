# 🧭 Lost & Found Platform – College Portal

A web-based platform designed for college students to report lost items, list found items, and securely reclaim belongings using a verification process. The system ensures lost items reach their rightful owners in a fast and secure way.

## 🚀 Features

### 👤 User Features
- Submit Lost Item details with description & photo
- List Found Items
- Claim items through a Security Question + Answer
- View status of items (Pending / Found / Claimed)

### 🔒 Security & Verification
- Claiming requires correct verification
- When verified, both students receive each other's contact details
- Ensures item reaches the true owner

### 🛠️ System Features
- Admin panel (optional)
- Real-time updates after claim
- Fully responsive UI

## 🧰 Tech Stack
### Frontend
- HTML
- CSS
- JavaScript
- React.js
- Bootstrap

### Backend
- Java
- Spring Boot
- Spring Web
- Spring Data JPA

### Database
- MySQL

## 📂 Project Structure
<img width="450" height="520" alt="image" src="https://github.com/user-attachments/assets/5eb2d46b-d05b-486f-a4a6-dba61901986e" />


## ⚙️ Installation & Setup Guide

Follow the steps below to run the project locally.

### 🖥️ 1. Clone the Repository
```bash
git clone https://github.com/yourusername/lost-and-found-platform.git
cd lost-and-found-platform
```
### 🗄️ 2. Configure the Database
## Step 1: Create MySQL Database
- CREATE DATABASE lostfound;
## Step 2: Update Database Credentials
- backend/src/main/resources/application.properties
- spring.datasource.url=jdbc:mysql://localhost:3306/lostfound
- spring.datasource.username=YOUR_USERNAME
- spring.datasource.password=YOUR_PASSWORD

- spring.jpa.hibernate.ddl-auto=update
- spring.jpa.show-sql=true

### ▶️ 3. Run the Backend (Spring Boot)
- cd backend
- mvn spring-boot:run

### 🌐 4. Run the Frontend (React)
- cd frontend
- npm install
- npm start

📌 Future Enhancements
- Email/OTP-based verification

- Search & filter system for items

- Admin analytics dashboard

- Push notifications

Screeshots:
<img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/8f8a3936-e144-48f7-8e62-c7ad3ea7742a" />
<img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/869f41f0-263c-4e6b-a8f4-0636d72eebf1" />
<img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/dc4a723f-e70b-4a77-9a4b-7c26a432c290" />
<img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/faa02494-58d5-4c1d-a5a7-0fbe64c5b1a5" />




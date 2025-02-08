# Blog-App-Android-Application

A basic Android application built using **Kotlin**, **PHP**, and **MySQLite**. The backend is hosted locally for testing using **XAMPP**.

## Features

- User authentication (Sign up & Login)
- Create, edit, and delete blog posts
- View a list of blogs
- Local database management using SQLite
- API integration with PHP backend

## Technologies Used

- **Frontend:** Kotlin (Android)
- **Backend:** PHP (REST API)
- **Database:** MySQL (via XAMPP) & SQLite (for local storage)

## Installation & Setup

### Prerequisites

- Android Studio
- XAMPP (for local backend hosting)
- MySQL Database
- Postman (optional, for API testing)

### Steps to Run the Project

1. **Clone the Repository**

   ```sh
   git clone https://github.com/zubair-ui/Blog-App-Android-Application.git
   cd Blog-App-Android-Application
   ```

2. **Setup Backend**

   - Install **XAMPP** and start Apache & MySQL
   - Copy the `php` backend files to `htdocs` in the XAMPP installation directory
   - Import the `blog_app.sql` database file into MySQL (use phpMyAdmin)

3. **Run the Android Application**
   - Open the project in **Android Studio**
   - Sync Gradle and install dependencies
   - Run the app on an emulator or a real device

# 🎓 University Management System

A Java Swing + MySQL desktop application that streamlines student, faculty, fee, and examination management in universities.  
The project features a modern FlatLaf UI, modular dashboards, and robust data management using **JDBC** with MySQL.

---

## 🚀 Features

- 🔑 Login Authentication
  - Secure login with DB credentials
  - Future-ready for hashed passwords (BCrypt)

- 👨‍🎓 Student Management
  - Add, update, search students
  - View student details by Roll No.
  - Manage student leave records

- 👩‍🏫 Faculty Management
  - Add/update faculty details
  - Track teacher leave records

- 📝 Examination Module
  - Enter marks and results
  - View examination details per student

- 💰 Fee Management
  - View complete fee structure
  - Filter by course
  - Auto calculate **total fee**
  - Export fee report as **PDF**

- 📊 Reports & Export
  - Export Student, Teacher, Fee data to **PDF** (iText)
  - Print data directly from UI

- 🎨 Modern UI
  - FlatLaf theme for clean look
  - Splash screen on startup
  - Integrated utilities: Calculator & Notepad

---

## 🛠️ Tech Stack

- Frontend/UI: Java Swing + FlatLaf  
- Backend: JDBC with MySQL  
- Libraries:  
  - [FlatLaf](https://www.formdev.com/flatlaf/) (modern UI theme)  
  - [JCalendar](https://toedter.com/jcalendar/) (date picker)  
  - [iTextPDF](https://itextpdf.com/) (PDF export)  
  - Custom `ResultSetTableModel` (for ResultSet → JTable conversion)  
- Database: MySQL  

---

## 📂 Project Structure

```

src/university/management/system/
│
├── Conn.java                 # JDBC connection helper
├── Splash.java               # Startup splash screen
├── Login.java                # User authentication
├── StudentDetails.java       # Student CRUD + search
├── TeacherDetails.java       # Teacher CRUD + search
├── FeeStructure.java         # Fee management + PDF export
├── ExaminationDetails.java   # Examination & results
├── StudentLeaveDetails.java  # Student leave records
├── TeacherLeaveDetails.java  # Teacher leave records
├── ResultSetTableModel.java  # Custom helper for JTable
└── ...



## 🗄️ Database Schema (Simplified)

- login(username, password, role)  
- student(roll_no, name, course, branch, dob, …)  
- teacher(emp_id, name, department, …)  
- fee(course, semester1 … semester8)  
- studentLeave (roll_no, date, duration, reason)  
- teacherLeave (emp_id, date, duration, reason)  
- exam/marks(roll_no, subject, marks, …)  

---

## ⚡ How to Run

1. Clone repo
   
   git clone https://github.com/your-username/university-management-system.git
   
   cd university-management-system


3. Set up MySQL

   
   CREATE DATABASE university;
   
   USE university;

   Insert default admin user
   
   INSERT INTO login(username,password,role)
   
   VALUES ('admin','admin123','admin');
   

4. Add libraries
   Place these JARs inside `lib/` and add them to IntelliJ/Eclipse classpath

   * `flatlaf-3.4.1.jar`
   * `jcalendar-1.4.jar`
   * `mysql-connector-j-8.x.x.jar`
   * `itextpdf-5.5.13.jar`

5. Run

   * Open project in IntelliJ/Eclipse
   * Run `Splash.java`
   * Login with:

     
     Username: admin
     
     Password: admin123
     


## 🔮 Future Scope

* Role-based dashboards (Admin, Student, Faculty)
* Hashed passwords with BCrypt
* Attendance analytics
* Cloud deployment (Spring Boot + React in future)
* Export to CSV/Excel


## 👤 Author

Bhuvanesh Adithya M C
📍 Bengaluru, Karnataka
📧 [bhuvaneshadithya294@gmail.com](mailto:bhuvaneshadithya294@gmail.com)
🔗 [LinkedIn](https://linkedin.com/in/bhuvanesh-gowda) | [GitHub](https://github.com/BhuvaneshAdithya45)




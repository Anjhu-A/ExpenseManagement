# ExpenseManagement
Expense Management Tool
A complete expense management system built with Java 17 (Spring Boot) backend and React frontend, featuring customizable approval workflows based on employee job titles.

Features
1. Employee Features
  Submit expenses with receipts
  View all submitted expenses and their approval status
  Track approval progress through workflow levels

2. Manager/Approver Features
  Review pending expenses assigned for approval
   Approve or reject expenses with reasons
  See expenses at current approval level

3. Admin Features
  Configure approval workflows for different job titles
  Define multi-level approval chains (e.g., "Engineer → Team Lead → Finance")
  Manage workflow configurations

Technology Stack
  Backend: Java 17, Spring Boot 3.2.0, Spring Security with JWT authentication, Spring Data JPA, H2 Database (in-memory), Maven
  Frontend: React 17

Setup Instructions
  Backend Setup Prerequisites: Java 17 or higher, Maven 3.6+
    Run Backend
      Maven Clean Install
      mvn spring-boot:run
      The backend will start on http://localhost:9100
    Access H2 Console (Optional):
      URL: http://localhost:8080/h2-console
      JDBC URL: jdbc:h2:mem:expensedb
      Username: sa
      Password: (leave empty)

  Frontend Setup Prerequisites: Node.js 16+ and npm
  Run Frontend
    npm install
    npm run dev
  The frontend will start on http://localhost:5173

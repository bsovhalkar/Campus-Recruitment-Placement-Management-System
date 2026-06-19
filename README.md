# Campus-Recruitment-Placement-Management-System

## Overview

Placement Management System (PMS) is a full-stack web application designed to automate and streamline the campus placement process for colleges and universities.

The system provides separate portals for Administrators (Training & Placement Officers) and Students. It manages student profiles, companies, jobs, applications, shortlisting, assessments, score management, analytics, resumes, skills, and placement tracking through a centralized platform.

---

# Tech Stack

## Frontend

* React.js
* React Router DOM
* Material UI (MUI)
* Axios
* React Toastify

## Backend

* Spring Boot
* Spring Security
* JWT Authentication
* Spring Data JPA
* Hibernate

## Database

* MySQL

## Build Tools

* Maven
* npm

---

# Key Features

## Authentication & Authorization

### Student

* Register account
* Login using email and password
* JWT based authentication
* Role-based authorization

### Admin

* Separate Admin Login Portal
* JWT based authentication
* Secure admin-only endpoints

---

# User Roles

## Administrator

Training & Placement Officer

### Can Manage

* Students
* Companies
* Jobs
* Applications
* Shortlists
* Assessments
* Assessment Scores
* Placement Analytics

---

## Student

### Can Manage

* Personal Profile
* Resume
* Skills
* Job Applications
* Assessments

---

# Authentication Module

## Student Registration

```http
POST /api/auth/register
```

### Request

```json
{
  "name": "John Doe",
  "email": "john@gmail.com",
  "password": "Password@123"
}
```

---

## Login

```http
POST /api/auth/login
```

### Request

```json
{
  "email": "john@gmail.com",
  "password": "Password@123"
}
```

### Response

```json
{
  "token": "JWT_TOKEN",
  "role": "STUDENT"
}
```

---

# Admin Module

---

## Dashboard Analytics

### Get Dashboard Summary

```http
GET /api/admin/analytics/dashboard
```

Returns:

* Total Students
* Total Companies
* Total Jobs
* Total Applications
* Total Assessments
* Selected Students
* Rejected Students
* Placement Percentage

---

## Department Analytics

```http
GET /api/admin/analytics/departments
```

Returns:

* Department
* Total Students
* Selected Students
* Placement Percentage

---

## Selected Students Analytics

```http
GET /api/admin/analytics/selected
```

---

## Rejected Students Analytics

```http
GET /api/admin/analytics/rejected
```

---

# Company Management

## Create Company

```http
POST /api/admin/companies
```

## Get All Companies

```http
GET /api/admin/companies
```

## Get Company By ID

```http
GET /api/admin/companies/{id}
```

## Update Company

```http
PUT /api/admin/companies/{id}
```

---

# Job Management

## Create Job

```http
POST /api/admin/jobs
```

## Get All Jobs

```http
GET /api/admin/jobs
```

## Get Job By ID

```http
GET /api/admin/jobs/{id}
```

## Update Job

```http
PUT /api/admin/jobs/{id}
```

## Delete Job

```http
DELETE /api/admin/jobs/{id}
```

## Jobs By Company

```http
GET /api/admin/jobs/company/{companyId}
```

## Job Statistics

```http
GET /api/admin/jobs/{jobId}/stats
```

Returns:

* Applications
* Shortlisted
* Pending Assessments
* Selected
* Rejected

---

# Application Management

## Get All Applications

```http
GET /api/admin/applications
```

## Get Application

```http
GET /api/admin/applications/{id}
```

## Applications By Job

```http
GET /api/admin/applications/jobs/{jobId}
```

## Update Status

```http
PUT /api/admin/applications/{id}/status
```

Statuses:

* APPLIED
* SHORTLISTED_FOR_TEST
* SELECTED
* REJECTED
* WITHDRAWN

---

# Shortlisting Module

## Generate Shortlist

```http
POST /api/admin/shortlists/generate/{jobId}
```

Automatically ranks students based on:

* CGPA
* Skills Count
* Score

---

## View Shortlist

```http
GET /api/admin/shortlist/{jobId}
```

---

# Assessment Management

## Schedule Assessment

```http
POST /api/admin/assessments
```

Assessment Types:

* TEST
* INTERVIEW

---

## Get Assessments

```http
GET /api/admin/assessments
```

---

## Get Assessment By ID

```http
GET /api/admin/assessments/{id}
```

---

## Get Assessments By Job

```http
GET /api/admin/assessments/job/{jobId}
```

---

## Delete Assessment

```http
DELETE /api/admin/assessments/{id}
```

---

# Assessment Score Management

## Get Assessment Students

```http
GET /api/admin/assessments/{assessmentId}/students
```

Returns:

* Student ID
* Student Name
* Current Score

---

## Upload Scores Manually

```http
POST /api/admin/assessments/{assessmentId}/scores
```

### Request

```json
[
  {
    "studentId": 3,
    "score": 85
  }
]
```

---

## Download CSV Template

```http
GET /api/admin/assessments/{assessmentId}/template
```

Downloads:

```csv
studentId,studentName,score
3,Bob Smith,
4,Charlie Brown,
```

---

## Upload CSV Scores

```http
POST /api/admin/assessments/{assessmentId}/scores/csv
```

### Form Data

```text
file = scores.csv
```

Supported Format:

```csv
studentId,studentName,score
3,Bob Smith,85
4,Charlie Brown,90
```

---

# Student Management

## Get All Students

```http
GET /api/admin/students
```

## Get Student

```http
GET /api/admin/students/{id}
```

## Update Student

```http
PUT /api/admin/students/{id}
```

## Bulk Update Students

```http
PUT /api/admin/students/bulk
```

---

# Student Module

---

# Profile Management

## Create Profile

```http
POST /api/students
```

## Get Profile

```http
GET /api/students/me
```

## Update Profile

```http
PUT /api/students/me
```

## Delete Profile

```http
DELETE /api/students/me
```

Soft delete only.

---

## Profile Completion Status

```http
GET /api/students/profile-status
```

Returns:

* Completion Percentage
* Missing Fields

---

# Resume Management

## Upload Resume

```http
POST /api/resumes/upload
```

Multipart File Upload

---

## Get Resume

```http
GET /api/resumes/me
```

---

## Resume Status

```http
GET /api/resumes/status
```

---

# Skill Management

## Add Skill

```http
POST /api/skills
```

## Get My Skills

```http
GET /api/skills/me
```

## Get Skill

```http
GET /api/skills/{id}
```

## Update Skill

```http
PUT /api/skills/{id}
```

## Delete Skill

```http
DELETE /api/skills/{id}
```

## Skill Status

```http
GET /api/skills/status
```

---

# Job Portal

## View Jobs

```http
GET /api/jobs
```

## View Job

```http
GET /api/jobs/{id}
```

## Jobs By Company

```http
GET /api/jobs/company/{companyId}
```

## Check Eligibility

```http
GET /api/jobs/{jobId}/eligibility
```

---

# Application Portal

## Apply Job

```http
POST /api/jobs/applications/{jobId}/apply
```

---

## Withdraw Application

```http
PUT /api/jobs/applications/{applicationId}/withdraw
```

---

## My Applications

```http
GET /api/jobs/applications/me
```

---

## Application Status

```http
GET /api/jobs/applications/{jobId}/status
```

---

## Application Details

```http
GET /api/jobs/applications/details/{applicationId}
```

---

# Student Assessments

## My Assessments

```http
GET /api/assessments/me
```

## Assessment Details

```http
GET /api/assessments/{assessmentId}
```

## Assessment Score

```http
GET /api/assessments/{assessmentId}/score
```

## Upcoming Assessments

```http
GET /api/assessments/upcoming
```

## Assessment Dashboard

```http
GET /api/assessments/dashboard
```

---

# Frontend Features

## Admin Portal

* Dashboard
* Students
* Companies
* Jobs
* Applications
* Shortlists
* Assessments
* Assessment Score Upload
* CSV Score Upload
* Analytics

---

## Student Portal

* Dashboard
* Profile
* Resume
* Skills
* Jobs
* Applications
* Assessments

---

# UI Features

* Material UI Design
* Responsive Sidebar
* Mobile Drawer Support
* Active Sidebar Highlight
* Dark Mode
* Toast Notifications
* Confirmation Dialogs
* Protected Routes
* Role Based Navigation
* Loading Indicators
* JWT Session Persistence

---

# Security

* JWT Authentication
* Role Based Authorization
* Protected Routes
* Secure API Access
* Password Encryption (BCrypt)
* Spring Security

---

# Project Structure

```text
src
│
├── api
├── components
├── pages
│   ├── admin
│   ├── auth
│   └── student
│
├── layouts
├── routes
├── context
├── theme
├── utils
└── App.jsx
```

---

# Setup Instructions

## Backend

```bash
git clone <repository>
```

```bash
cd placement-management-system
```

Update:

```properties
application.properties
```

```properties
spring.datasource.url=
spring.datasource.username=
spring.datasource.password=
```

Run:

```bash
mvn spring-boot:run
```

Backend runs on:

```text
http://localhost:8080
```

---

## Frontend

```bash
cd pms_frontend
```

Install:

```bash
npm install
```

Run:

```bash
npm run dev
```

Frontend runs on:

```text
http://localhost:5173
```

---

# Developed For

Campus Placement Process Automation

Training and Placement Cell Management

Student Placement Tracking

Assessment & Recruitment Workflow Management

---

# Future Enhancements

* Email Notifications
* Excel Export
* Advanced Search Filters
* Placement Reports PDF
* Company HR Portal
* Student Ranking Dashboard
* Interview Scheduling
* Resume Parsing
* AI Based Shortlisting
* Placement Prediction Analytics

---

## Version

Current Version: 1.0.0

Status: Production Ready

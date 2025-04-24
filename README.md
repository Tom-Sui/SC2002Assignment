# Build-To-Order (BTO) Management System 

The program is design for a command prompt BTO managerment system. The user modifications are stored in local memories. 
OOP oncepts are applied in the current project for easy modification and futher developments.
Detailed javadoc is inlucded under ./docs

## Table of Contents
- [Introduction](#introduction)
- [System Architecture](#system-architecture)
    - [Use Case Modeling](#use-case-modeling)
- [Features](#features)
- [Setup and Installation](#setup-and-installation)
- [Usage](#usage)
    - [Instructions](#instructions)
    - [Instructions for Applicants and Officers](#instructions-for-applicants-and-officers)
        - [Applicant Commands](#applicant-commands)
        - [Officer Commands](#officer-commands)
        - [Enhanced Features](#enhanced-features)
    - [Instructions for Managers](#instructions-for-managers)

## Introduction

This project is a command-line BTO Management System designed with OOP principles for great modularity and future enhanced scalability.

## System Architecture

The system follows a layered architecture with four main components:

- **Entity Layer**: Contains data classes like `User`, `Applicant`, `Project`, and `Flat`.
- **Control Layer**: Handles business logic, including operations like registration, project filtering, flat booking, and status transitions.
- **Boundary Layer**: Manages user interactions via commind-line, such as rendering menus and processing input/output.
- **Repository Layer**: Handles data persistence, including loading and saving user and project data to text files for future reusage.

### Use Case Modeling

Key use cases are mapped to specific controller methods. For example:
- `managerOfficerRegistration.approveOrRejectOfficerRegistration()` manages officer registration approvals.
- `managerApplicantRegistration.approveOrRejectApplication()` handles BTO application approvals.

## Features

 Main features include user authentication, project management, application handling, flat booking, and role-based workflows. Optional features like advanced reporting and session-based saving were considered to enhance functionality.

## Setup and Installation

1. Clone the repository:
    ```bash
    git clone <repository-url>
    ```
2. Navigate to the project directory:
    ```bash
    cd SC2002Assignment
    ```
3. Compile the Java files:
    ```bash
    javac ./src/*.java
    ```
4. Run the application:
    ```bash
    java ./src/App.java
    ```

## Usage

Some key interruption functions with the program

### Instructions

When you first enter the application, the following commands are available in the command line:

| **Command**      | **Description**                     |
|-------------------|-------------------------------------|
| `1-help`         | Display help menu                  |
| `2-login`        | Log in to your account             |
| `3-edit profile` | Edit your user profile             |
| `4-logout`       | Log out of your account            |
| `5-enquiries`    | Make an enquiry                    |
| `6-quit`         | Exit the application               |

### Instructions for Applicants and Officers

#### Applicant Commands

| **Command**          | **Description**                             |
|----------------------|---------------------------------------------|
| `1-view projects`    | View available projects                     |
| `2-view applications`| View applied projects                       |
| `3-apply project`    | Apply for a project                         |
| `4-book flat`        | Book a flat                                 |
| `5-withdraw request` | Request withdrawal from a project           |
| `6-logout`           | Log out of your account                     |

#### Officer Commands

If you are a managing officer for a project, additional commands are available:

| **Command**           | **Description**                            |
|-----------------------|--------------------------------------------|
| `1-view status`       | View project registration status           |
| `2-register project`  | Register for a project                     |
| `3-logout`            | Log out of your account                    |

#### Enhanced Features

| **Command**           | **Description**                            |
|-----------------------|--------------------------------------------|
| `1-view requests`     | View all booking requests                  |
| `2-assist booking`    | Help applicants book a flat                |
| `3-generate receipt`  | Generate flat selection receipt            |
| `4-view project`      | View project details                       |
| `5-logout`            | Log out of your account                    |

### Instructions for Managers

When logged in as a manager, the following commands are available in the command line:

| **Command**                   | **Description**                                    |
|-------------------------------|----------------------------------------------------|
| `1-view active project`       | View the current active project                    |
| `2-create project`            | Create a new BTO project                           |
| `3-edit project`              | Edit an existing BTO project                       |
| `4-delete project`            | Delete a BTO project                               |
| `5-toggle visibility`         | Toggle project visibility                          |
| `6-view all projects`         | View all created projects                          |
| `7-view personal projects`    | View projects assigned to you                      |
| `8-view officer registrations`| View pending and approved HDB officer registrations|
| `9-approve officer`           | Approve or reject HDB officer registrations        |
| `10-approve application`      | Approve or reject applicant’s BTO application      |
| `11-approve withdrawal`       | Approve or reject applicant’s withdrawal request   |
| `12-generate reports`         | Generate reports of applicants                     |
| `13-view enquiries`           | View enquiries for all projects                    |
| `14-reply enquiries`          | Reply to enquiries for your projects               |
| `15-quit`                     | Exit the application                               |
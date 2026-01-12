# Amazon Animal Adventure

**Design Document**

---

## 1. Project Overview

**Amazon Animal Adventure** is a web-based educational application designed to teach grade-school students about animals in the Amazon rainforest. The site uses bright visuals, simple language, and a playful mascot to guide users through learning content, assess understanding via a quiz, and reward completion with badges and a digital certificate.

The project emphasizes **engagement** and **user progression**.
Vision document: https://docs.google.com/document/d/1IZm87gUZxigmwvccD9GuGMK7YbxiwnEuIKRNZyrVNiQ/edit?usp=sharing
Trello Board: https://trello.com/b/XLZD7aNl/rainforest-project
---

## 2. Goals and Objectives

### Primary Goals

* Teach users basic facts about Amazon rainforest animals
* Encourage exploration through gated progression
* Reinforce learning with a quiz
* Reward completion with badges and a certificate

### Secondary Goals

* Provide a fun, character-driven user experience
* Demonstrate backend state management
* Follow sound software engineering practices

---

## 3. Target Audience

* **Primary:** Grade-school students
* **Secondary:** Instructors evaluating software design and implementation

Design choices prioritize:

* Simple navigation
* Minimal text
* Visual and interactive feedback

---

## 4. System Architecture

### High-Level Architecture

```
Browser (Client)
   |
   |  HTTP Requests
   v
Spring Boot Web Application
   |
   |  JSON File Persistence
   v
Local Data Storage (JSON Files)
```

---

## 5. Technology Stack

### Backend

* **Java 17**
* **Spring Boot**

### Frontend

* Angular
* HTML
* CSS
* Basic JavaScript (for UI feedback)

### Persistence

* File-based JSON storage (no SQL)

### Build Tool

* Maven

---

## 6. Data Storage Design (Non-SQL)

### Rationale

This project was originally intended to use SQL; however, this was reconsidered because it added unnecessary complexity

SQL was intentionally excluded to:

* Reduce setup complexity
* Avoid database deployment issues
* Focus on application logic and learning flow

Persistence is achieved through structured JSON files.

### Example Data Files

```
/data
 ├── users.json
 ├── progress.json
 ├── badges.json
 └── quizResults.json
```

### Stored Data

* User credentials (hashed passwords)
* Animal pages viewed per user
* Quiz attempts and scores
* Earned badges
* Certificate eligibility

---

## 7. Authentication & Security Design

### Authentication

* Username login

### Authorization

* Protected routes require authentication
* Quiz and certificate access gated by progress

### Security Considerations

* No plaintext passwords
* Input validation on forms
* Restricted access to protected pages

---

## 8. Functional Design

### 8.1 Animal Discovery & Learning

* Main page displays **three Amazon animals**
* Each animal has a dedicated information page
* Viewing an animal page records progress

Animals include:

* Jaguar
* Sloth
* Parrot

---

### 8.2 Progress Tracking

* Each viewed animal is recorded
* Quiz remains locked until all animals are viewed
* Progress persists across sessions

---

### 8.3 Mascot-Based Quiz Gatekeeper

A mascot acts as a **narrative gatekeeper**:

* Blocks completion until requirements are met
* Provides feedback on failure or success
* Reinforces the educational flow

The mascot improves engagement while enforcing application rules.

---

### 8.4 Quiz System

* Multiple-choice quiz
* Automatically graded
* Passing threshold required
* Unlimited retries allowed

---

### 8.5 Rewards System

#### Badges

* **Jaguar Genius**
* **Sloth Scholar**
* **Parrot Prodigy**

Badges are awarded based on learning completion.

#### Certificate

* Awarded after passing the quiz
* Certificate includes:

  * User name
  * Completion title
  * Date

---

## 9. User Flow

1. User registers or logs in
2. User explores animal pages
3. Progress is tracked automatically
4. Mascot certification until requirements are met
5. User takes and passes quiz
6. Badges are awarded
7. Certificate is generated 

---

## 10. Accessibility & Usability

* Large fonts and buttons
* High-contrast colors
* Simple language
* Clear feedback messages
* Minimal navigation depth

---

## 11. Error Handling

* Friendly error messages for invalid input
* Mascot-based feedback for quiz failure
* Graceful handling of missing or corrupted data files

---

## 12. Design Decisions & Justifications

| Decision             | Justification                      |
| -------------------- | ---------------------------------- |
| No SQL database      | Simpler deployment and maintenance |
| JSON persistence     | Transparent, inspectable data      |
| Mascot gatekeeper    | Increases engagement and clarity   |
| Spring Boot          | Rapid development and structure    |
| Badges & certificate | Reinforces motivation              |

---

## 13. Future Enhancements

* Admin interface to add animals
* Additional rainforest regions
* Audio narration
* Leaderboards
* Mobile-first UI improvements
* Passwords and additional security measures

---

## 14. Conclusion

Amazon Animal Adventure combines educational content with interactive design and sound engineering practices. By using file-based persistence, a mascot-driven flow, and structured progression, the project delivers both learning value and technical depth while remaining accessible and fun.






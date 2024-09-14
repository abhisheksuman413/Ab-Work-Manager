# Work Management System for Boss and Employees

This is a comprehensive Android application developed using Kotlin, designed to streamline task management between bosses and employees. The app provides features for task assignment, status tracking, and real-time notifications, ensuring smooth communication and task management within an organization.
## Screenshots

### Light Mode

### Dark Mode

<p align="center">
  <img src="https://github.com/abhisheksuman413/Ab-Work-Manager/blob/master/Screenshot/1.jpg" alt="Light Mode Screenshot 1" width="200" />
  <img src="https://github.com/abhisheksuman413/Ab-Work-Manager/blob/master/Screenshot/2.jpg" alt="Light Mode Screenshot 1" width="200" />
  <img src="https://github.com/abhisheksuman413/Ab-Work-Manager/blob/master/Screenshot/3.jpg" alt="Light Mode Screenshot 1" width="200" />
  <img src="https://github.com/abhisheksuman413/Ab-Work-Manager/blob/master/Screenshot/4.jpg" alt="Light Mode Screenshot 1" width="200" />
  <img src="https://github.com/abhisheksuman413/Ab-Work-Manager/blob/master/Screenshot/5.jpg" alt="Light Mode Screenshot 1" width="200" />
  <img src="https://github.com/abhisheksuman413/Ab-Work-Manager/blob/master/Screenshot/6.jpg" alt="Light Mode Screenshot 1" width="200" />
  <img src="https://github.com/abhisheksuman413/Ab-Work-Manager/blob/master/Screenshot/7.jpg" alt="Light Mode Screenshot 1" width="200" />
  <img src="https://github.com/abhisheksuman413/Ab-Work-Manager/blob/master/Screenshot/8.jpg" alt="Light Mode Screenshot 1" width="200" />
  <img src="https://github.com/abhisheksuman413/Ab-Work-Manager/blob/master/Screenshot/9.jpg" alt="Light Mode Screenshot 1" width="200" />
  <img src="https://github.com/abhisheksuman413/Ab-Work-Manager/blob/master/Screenshot/10.jpg" alt="Light Mode Screenshot 1" width="200" />
  <img src="https://github.com/abhisheksuman413/Ab-Work-Manager/blob/master/Screenshot/11.jpg" alt="Light Mode Screenshot 1" width="200" />
  <img src="https://github.com/abhisheksuman413/Ab-Work-Manager/blob/master/Screenshot/12.jpg" alt="Light Mode Screenshot 1" width="200" />
  <img src="https://github.com/abhisheksuman413/Ab-Work-Manager/blob/master/Screenshot/12a.jpg" alt="Light Mode Screenshot 1" width="200" />
  <img src="https://github.com/abhisheksuman413/Ab-Work-Manager/blob/master/Screenshot/13.jpg" alt="Light Mode Screenshot 1" width="200" />
  <img src="https://github.com/abhisheksuman413/Ab-Work-Manager/blob/master/Screenshot/14.jpg" alt="Light Mode Screenshot 1" width="200" />
  <img src="https://github.com/abhisheksuman413/Ab-Work-Manager/blob/master/Screenshot/15.jpg" alt="Light Mode Screenshot 1" width="200" />
  <img src="https://github.com/abhisheksuman413/Ab-Work-Manager/blob/master/Screenshot/16.jpg" alt="Light Mode Screenshot 1" width="200" />
  <img src="https://github.com/abhisheksuman413/Ab-Work-Manager/blob/master/Screenshot/17.jpg" alt="Light Mode Screenshot 1" width="200" />
  <img src="https://github.com/abhisheksuman413/Ab-Work-Manager/blob/master/Screenshot/18.jpg" alt="Light Mode Screenshot 1" width="200" />
  <img src="https://github.com/abhisheksuman413/Ab-Work-Manager/blob/master/Screenshot/19.jpg" alt="Light Mode Screenshot 1" width="200" />
  <img src="https://github.com/abhisheksuman413/Ab-Work-Manager/blob/master/Screenshot/20.jpg" alt="Light Mode Screenshot 1" width="200" />
  <img src="https://github.com/abhisheksuman413/Ab-Work-Manager/blob/master/Screenshot/21.jpg" alt="Light Mode Screenshot 1" width="200" />
  <img src="https://github.com/abhisheksuman413/Ab-Work-Manager/blob/master/Screenshot/22.jpg" alt="Light Mode Screenshot 1" width="200" />
</p>


## Features

- **User Authentication**: Secure user login and registration using **Firebase Authentication** with role-based access (Boss/Employee), along with email verification and password recovery.
- **Task Assignment and Tracking**: Bosses can assign tasks to employees with details such as title, description, priority, and deadlines. Employees can update task statuses (Pending, In Progress, Completed).
- **Real-time Notifications**: Integrated **Firebase Cloud Messaging (FCM)** to send instant notifications when a task is assigned or its status changes.
- **RecyclerView with Dynamic Data**: Custom RecyclerView implementation using **AsyncListDiffer** for efficient list updates, displaying employee and task details.
- **Task Priority Management**: Categorize tasks by priority (Low, Medium, High) and update statuses dynamically with real-time visual feedback.
- **Firebase Realtime Database**: Utilized for real-time synchronization of data, ensuring up-to-date task tracking across users.
- **Push Notifications**: Integrated push notifications using **Retrofit** and **Gson** for making REST API calls and handling Firebase Cloud Messaging.
- **Asynchronous Operations**: Employed **Kotlin Coroutines** to handle background tasks, including database operations and network requests, ensuring smooth UI performance.
- **Clean and Modular Code**: Followed best practices to maintain code modularity and ensure ease of future development.

## Tech Stack

- **Language**: Kotlin
- **Database**: Firebase Realtime Database
- **Authentication**: Firebase Authentication
- **Notifications**: Firebase Cloud Messaging (FCM)
- **Networking**: Retrofit, Gson
- **Asynchronous Tasks**: Kotlin Coroutines
- **UI/UX**: Material Design, RecyclerView, XML Layouts
- **Tools**: Android Studio, Gradle, Git, GitHub

## Setup and Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/abhisheksuman413/Ab-Work-Manager.git

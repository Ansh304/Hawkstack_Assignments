# Hawkstack_Assignments
# HawkStack Academy - Full-Stack Learning Platform

A complete full-stack learning management system built for **HawkStack Technologies** featuring course catalogs, progress tracking, and training/lab booking. Demonstrates modern Vue 3 + Spring Boot architecture with enterprise-grade UI/UX.

## 🎯 Features Demonstrated

✅ **Courses Catalog** - Server-side filtered, sorted, paginated course listing  
✅ **Course Progress Tracking** - Real-time lesson completion with progress visualization  
✅ **Training/Lab Booking** - Availability management with booking confirmation  
✅ **My Bookings Dashboard** - User-specific booking history  
✅ **Enterprise UI** - Consistent theming, responsive design, loading states  
✅ **Bonus: 404 Not Found** - Branded error page with navigation recovery  

## 🛠 Tech Stack

| Frontend | Backend | Database | Other |
|----------|---------|----------|-------|
| Vue 3 + Vite | Spring Boot 3.x | H2/PostgreSQL | PrimeVue UI |
| Pinia State | JPA/Hibernate | JPA Repositories | Axios HTTP |
| Vue Router | ResponseUtil | Flyway | TailwindCSS |

## 🚀 Quick Start

### Prerequisites
- Java 17+ (Backend)
- Node.js 18+ (Frontend)
- npm/yarn/pnpm

### Backend (Spring Boot)
cd backend
./mvnw spring-boot:run

or
java -jar target/ltplatform-0.0.1-SNAPSHOT.jar
**Backend runs on:** `http://localhost:8080`

### Frontend (Vue 3)
cd frontend
npm install
npm run dev
**Frontend runs on:** `http://localhost:5173`

### API Base URL
Frontend configured to call `http://localhost:8080/api/*`

## 📱 Pages & Features

### 1. Courses Catalog (`/courses`)
- **Filters:** Category, Level, Search (debounced 1.5s)
- **Server-side:** Pagination, Sorting (`lazy` DataTable)
- **Actions:** "View Progress" → navigates to `/progress/:courseId`

### 2. Course Progress (`/progress/:courseId`)
- **Progress Bar:** Visual completion percentage
- **Lesson List:** Real lessons from backend with checkboxes
- **APIs:**

### 3. Training Bookings (`/booking`)
- **Trainings List:** Upcoming sessions with seat availability
- **Book Button:** Disabled when full, shows loading during booking
- **My Bookings Link:** → `/my-bookings`

### 4. My Bookings (`/my-bookings`)
- **User Bookings:** Personal reservation history
- **Details:** Training title, schedule, booking date

### 5. Bonus: 404 Not Found (`*`)
- Full-screen branded error page
- "Back" and "Home" navigation recovery
- Breadcrumb integration


## 🔌 API Endpoints Summary

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/catalog/courses` | Filtered course list |
| GET | `/api/progress/courses/{id}/progress` | Course completion stats |
| POST | `/api/progress/lessons/{id}/complete` | Mark lesson complete |
| GET | `/api/lessons/courses/lesson/{id}` | Course lessons |
| GET | `/api/booking/trainings` | Available trainings |
| POST | `/api/booking/trainings/{id}/book` | Book training |
| GET | `/api/booking/users/1/bookings` | User bookings |

## 🎨 UI Design System

- **Layout:** `.page` + `.table-card` with radial gradient bg
- **Tables:** PrimeVue DataTable (`stripedRows`, `lazy` pagination)
- **Colors:** Blue (#2563eb) primary, green (#22c55e) success
- **Typography:** Consistent font weights, spacing, uppercase headers
- **States:** Loading overlays, disabled buttons, empty states
- **Mobile:** Responsive grid → stack layout

## 📊 Sample Data (Auto-seeded)


## 🧪 Testing the Flow

1. **Courses** → Filter "Backend" → Click "View Progress" on Spring Boot
2. **Progress** → Mark "Spring Boot Introduction" complete → See progress bar update
3. **Trainings** → Book "Docker Workshop" → See seats decrement
4. **My Bookings** → See your new booking listed
5. **404** → Visit `/random` → See branded error page

## 📈 Key Decisions Made

- **Server-side everything** - Pagination, filtering, sorting
- **Optimistic updates** - Booking immediately reflects seat changes
- **Consistent error handling** - Toast notifications everywhere
- **Demo user (ID=1)** - John Doe has sample progress/bookings
- **No unmarking** - Lessons/bookings are one-way (realistic UX)

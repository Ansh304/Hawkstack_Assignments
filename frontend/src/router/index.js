import { createRouter, createWebHistory } from 'vue-router'

// Views (pages)
import CoursesView from '../views/CoursesView.vue'
import ProgressView from '../views/ProgressView.vue'
import BookingView from '../views/BookingView.vue'
import MyBookings from '../views/MyBookings.vue'

const router = createRouter({
    history: createWebHistory(),
    routes:[
  {
    path: '/',
    name: 'Courses',
    component: CoursesView
  },{
    path: '/courses',
    name: 'Courses',
    component: CoursesView
  },
  {
  path: '/my-bookings',
  name: 'my-bookings',
  component: () => MyBookings
},
  {
  path: '/progress/:courseId',
  name: 'progress',
  component: () => import('@/views/ProgressView.vue'),
  props: true
},
  
      // {
      //   path: '/progress',
      //   name: 'Progress',
      //   component: ProgressView,
      //   meta: { title: 'Progress Tracker - LT Platform' }
      // },
,
      {
        path: '/booking',
        name: 'Booking',
        component: BookingView,
        meta: { title: 'Training Booking - LT Platform' }
      },
  {
    path: '/:pathMatch(.*)*',
    name: 'NotFound',
    component: () => import('../views/NotFound.vue')
  }
]})


// Title update on route change
router.beforeEach((to, from, next) => {
  document.title = to.meta.title || 'LT Learning Platform'
  next()
})

export default router

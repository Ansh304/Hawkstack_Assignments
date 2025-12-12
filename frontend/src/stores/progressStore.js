import { defineStore } from 'pinia'
import services from '../services/progressService.js'
import { useToast } from 'primevue/usetoast'

export const useProgressStore = defineStore('useProgressStore', {
  state: () => ({
    // core progress data
    courseId: null,
    courseTitle: '',
    progressPercentage: 0,
    totalLessons: 0,
    completedLessons: 0,
    completedLessonIds: [],
    loading: false,
    completingLesson: false,
    lessons: [],
  }),

  actions: {
    async fetchCourseProgress(courseId) {
      this.loading = true
      let response = {}

      await services
        .getCourseProgress(courseId)
        .then((res) => {
          console.log('Progress store :: data fetched :: ', res.data.data)

          const data = res.data.data || {}

          this.courseId = data.courseId || null
          this.courseTitle = data.courseTitle || ''
          this.progressPercentage = data.progressPercentage || 0
          this.totalLessons = data.totalLessons || 0
          this.completedLessons = data.completedLessons || 0
          this.completedLessonIds = data.completedLessonIds || []

          response = data
        })
        .catch((error) => {
          console.error('Progress store :: error :: ', error)
          const toast = useToast()
          toast.add({
            severity: 'error',
            summary: 'Error',
            detail:
              error.response?.data?.message ||
              'Failed to fetch course progress',
            life: 3000
          })
        })
        .finally(() => {
          this.loading = false
        })

      return response
    },

    async completeLesson(lessonId) {
      if (!lessonId) return null

      this.completingLesson = true
      let response = {}

      await services
        .completeLesson(lessonId)
        .then((res) => {
          console.log('Progress store :: lesson completed :: ', res.data.data)
          const data = res.data.data || {}

          // Optimistic local update
          if (!this.completedLessonIds.includes(data.lessonId)) {
            this.completedLessonIds.push(data.lessonId)
            this.completedLessons = this.completedLessons + 1
          }

          response = data
        })
        .catch((error) => {
          console.error('Progress store :: complete error :: ', error)
          const toast = useToast()
          toast.add({
            severity: 'error',
            summary: 'Error',
            detail:
              error.response?.data?.message ||
              'Failed to mark lesson as complete',
            life: 3000
          })
        })
        .finally(() => {
          this.completingLesson = false
        })

      // Optionally refresh full progress to stay in sync
      if (this.courseId) {
        await this.fetchCourseProgress(this.courseId)
      }

      return response
    },

    async fetchLessons(courseId) {
      let response = {}

      await services
        .getLessonsByCourse(courseId)
        .then((res) => {
          console.log('Progress store :: lessons fetched :: ', res.data.data)
          const data = res.data.data || []
          this.lessons = data
          response = data
        })
        .catch((error) => {
          console.error('Progress store :: lessons error :: ', error)
          const toast = useToast()
          toast.add({
            severity: 'error',
            summary: 'Error',
            detail:
              error.response?.data?.message ||
              'Failed to fetch lessons for this course',
            life: 3000
          })
        })

      return response
    },
  }
})

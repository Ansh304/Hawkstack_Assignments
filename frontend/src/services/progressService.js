import { api } from '../boot/axios.js'

export default {

  async getCourseProgress(courseId) {
    return api.get(`progress/courses/${courseId}/progress`)
  },

  async completeLesson(lessonId) {
    return api.post(`progress/lessons/${lessonId}/complete`)
  },

  async getLessonsByCourse(courseId) {
    return api.get(`progress/courses/lesson/${courseId}`)
  }
}

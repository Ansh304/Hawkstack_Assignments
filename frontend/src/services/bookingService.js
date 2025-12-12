import { api } from '../boot/axios.js'

export default {
    
  async getTrainings() {
    return api.get('booking/trainings')
  },

  async bookTraining(trainingId) {
    return api.post(`booking/trainings/${trainingId}/book`)
  },

  async getUserBookings(userId) {
    return api.get(`booking/users/${userId}/bookings`)
  }
}

import { defineStore } from 'pinia'
import services from '../services/bookingService.js'
import { useToast } from 'primevue/usetoast'

export const useBookingStore = defineStore('useBookingStore', {
  state: () => ({
    trainings: [],
    trainingsLoading: false,
    bookings: [],
    totalBookings: 0,
    bookingsLoading: false,
    bookingInProgressIds: []
  }),

  getters: {
    isBookingInProgress: (state) => (trainingId) =>
      state.bookingInProgressIds.includes(trainingId)
  },

  actions: {
    async fetchTrainings() {
      this.trainingsLoading = true
      let response = []

      await services
        .getTrainings()
        .then((res) => {
          console.log('Booking store :: trainings fetched :: ', res.data.data)
          const data = res.data.data || []
          this.trainings = data
          response = data
        })
        .catch((error) => {
          console.error('Booking store :: trainings error :: ', error)
          const toast = useToast()
          toast.add({
            severity: 'error',
            summary: 'Error',
            detail:
              error.response?.data?.message ||
              'Failed to fetch trainings',
            life: 3000
          })
        })
        .finally(() => {
          this.trainingsLoading = false
        })

      return response
    },

    async fetchUserBookings(userId) {
      this.bookingsLoading = true
      let response = {}

      await services
        .getUserBookings(userId)
        .then((res) => {
          console.log('Booking store :: bookings fetched :: ', res.data.data)
          const data = res.data.data || {}
          this.bookings = data.bookings || []
          this.totalBookings = data.totalBookings || 0
          response = data
        })
        .catch((error) => {
          console.error('Booking store :: bookings error :: ', error)
          const toast = useToast()
          toast.add({
            severity: 'error',
            summary: 'Error',
            detail:
              error.response?.data?.message ||
              'Failed to fetch user bookings',
            life: 3000
          })
        })
        .finally(() => {
          this.bookingsLoading = false
        })

      return response
    },

    async bookTraining(trainingId) {
      if (!trainingId) return null

      // Track per-training loading state
      if (!this.bookingInProgressIds.includes(trainingId)) {
        this.bookingInProgressIds.push(trainingId)
      }

      let response = null

      await services
        .bookTraining(trainingId)
        .then((res) => {
          if(res.data.success){
            response = res.data

            const index = this.trainings.findIndex((t) => t.id === trainingId)
            if (index !== -1) {
                const training = this.trainings[index]
                const updated = {
                ...training,
                bookedCount: (training.bookedCount || 0) + 1,
                availableSeats: Math.max((training.availableSeats || 0) - 1, 0)
                }
                this.trainings.splice(index, 1, updated)
            }
          }else{
            response = res.data
          }
        })
        .catch((error) => {
          console.error('Booking store :: book error :: ', error)
          const toast = useToast()
          toast.add({
            severity: 'error',
            summary: 'Error',
            detail:
              error.response?.data?.message ||
              'Failed to book this training',
            life: 3000
          })
        })
        .finally(() => {
          this.bookingInProgressIds = this.bookingInProgressIds.filter(
            (id) => id !== trainingId
          )
        })

      // Optionally refresh user bookings for DEMO_USER_ID = 1
      await this.fetchUserBookings(1)

      return response
    }
  }
})

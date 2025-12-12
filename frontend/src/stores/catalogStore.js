import { defineStore } from 'pinia'
import services from '../services/catalogService.js'
import { useToast } from 'primevue/usetoast'

export const useCatalogStore = defineStore('useCatalogStore', {
  state: () => ({
    courses: [],
    totalCourses: 0,
    loading: false,
    filters: {
      category: '',
      level: '',
      search: ''
    },
    currentPage: 0,
    elementsPerPage: 10,
    sortBy: ''
  }),

  actions: {
    async fetchCourses(payload) {
      this.loading = true
      let response = {}

      await services.getCourses(payload).then((res) => {
        console.log("Catalog store :: data fetched :: ", res.data.data)
        
        this.courses = res.data.data.courses || []
        this.totalCourses = res.data.data.totalCourses || 0
        
        response = res.data.data
      })
      .catch((error) => {
        console.error("Catalog store :: error :: ", error)
        const toast = useToast()
        toast.add({ 
          severity: 'error', 
          summary: 'Error', 
          detail: error.response?.data?.message || 'Failed to fetch courses', 
          life: 3000 
        })
      })
      .finally(() => {
        this.loading = false
      })

      return response
    },

  }
})

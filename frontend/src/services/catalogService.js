import { api } from '../boot/axios.js'

export default {
    async getCourses(params = {}) {
        return api.get('catalog/courses', { params })
    }
}

import axios from "axios"

const service = axios.create({
    baseURL: 'http://localhost:1001',
    withCredentials: true,
    timeout: 5000
})

service.interceptors.request.use(
    config => {
        return config
    },
    error => {
        return Promise.reject(error)
    }
)

service.interceptors.response.use(
    response => {
        if (response.status && response.status === 200) {
            return response.data
        }else {
            return Promise.reject()
        }
    },
    error => {
        return Promise.reject(error)
    }
)

export default service
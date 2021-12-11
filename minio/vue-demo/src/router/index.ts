import {createRouter, createWebHistory} from "vue-router";

const routes = [
    {
        path: '/',
        name: 'home',
        meta: {
            title: '欢迎页'
        },
        component: () => import('@/view/home/index.vue')
    },
    {
        path: '/upload',
        name: 'upload',
        meta: {
            title: '上传',
        },
        component: () => import('@/view/upload/index.vue')
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes,
})

export default router

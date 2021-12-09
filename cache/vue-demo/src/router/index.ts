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
        path: '/user',
        name: 'user',
        meta: {
            title: '用户管理',
        },
        component: () => import('@/view/user/index.vue')
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes,
})

export default router

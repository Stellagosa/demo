import { createApp } from 'vue'
import App from './App.vue'
import 'element-plus/packages/theme-chalk/src/base.scss'
import {useElementPlus} from "./plugins/elementPlus";
import router from "./router";

const app = createApp(App)
useElementPlus(app)
app.use(router)
app.mount('#app')

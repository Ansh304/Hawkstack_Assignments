import './style.css'
import { createApp } from 'vue'
import PrimeVue from 'primevue/config'

import 'primeicons/primeicons.css';
import Aura from '@primeuix/themes/aura';
import App from './App.vue'
import router from './router/index.js';
import Toast from 'primevue/toast';
import ToastService from 'primevue/toastservice';

import { createPinia } from 'pinia'
const app = createApp(App);
app.use(PrimeVue, {
    theme: {
        preset: Aura
    }
});
app.use(router);
app.component('Toast', Toast);
app.use(ToastService);
app.use(createPinia())
app.mount('#app')

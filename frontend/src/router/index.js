import { createRouter, createWebHistory } from 'vue-router';
import TypingPractice from '../views/TypingPractice.vue';
import TypingResults from '../views/TypingResults.vue';

const routes = [
    { path: '/', component: TypingPractice },
    { path: '/results', component: TypingResults }
];

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
});

export default router;

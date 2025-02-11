import { defineStore  } from 'pinia';
import api from '@/api';

export const useAuthStore = defineStore('auth', {
    state: () => ({
        user: null,
    }),
    actions: {
        async register(username, password){
            await api.post('/api/auth/register', { username, password });
        },
        async login(username, password) {
            const response = await api.post('/api/auth/login', { username, password });
            this.user = response.data;
        },
        async fetchUser(){
            try {
                const response = await api.get('/me');
                this.user = response.data;
            } catch {
                this.user = null;
            }
        },
        async logout(){
            await api.get('/api/auth/logout');
            this.user = null;
        }
    }
});
import axios from 'axios';
import {eventBus} from "@/api/eventBus";


const instance = axios.create({
    baseURL: 'http://localhost:8080/api',
    withCredentials: true,
});

// 모든 요청에서 401 에러 발생 시 자동 로그아웃
instance.interceptors.request.use(
    response => response,
    error => {
        if(error.response && error.response.status === 401) {
            console.log('세션 만료 감지 - 자동 로그아웃');
            eventBus.emit('logoutSuccess');
            localStorage.removeItem('isAuthenticated');
            window.location.href = '/';
        }
        return Promise.reject(error);
    }
);

export default instance;
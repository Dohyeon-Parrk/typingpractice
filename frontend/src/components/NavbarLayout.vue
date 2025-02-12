<template>
  <nav class="navbar">
    <div class="container">
      <router-link to="/" class="logo">Typing-practice</router-link>
      <ul class="nav-links">
        <li><router-link to="/" class="nav-item">Home</router-link></li>
        <li><router-link to="/courses" class="nav-item">Courses</router-link></li>
        <li v-if="!isAuthenticated">
          <button class="nav-item" @click.prevent="openLoginModal">Login</button>
        </li>
        <li v-else>
          <button class="nav-item logout-btn" @click="logout">Logout</button>
        </li>
      </ul>
    </div>
  </nav>
</template>

<script>
import axios from '@/api/axios';
import { eventBus } from "@/api/eventBus";

export default {
  data() {
    return {
      isAuthenticated: localStorage.getItem("isAuthenticated") === 'true'
    };
  },
  mounted() {
    this.checkSession();

    // 로그인 성공 시 isAuthenticated -> true
    eventBus.on('loginSuccess', () => {
      this.isAuthenticated = true;
      localStorage.setItem('isAuthenticated', 'true');
    })

    // 로그아웃 시 isAuthenticated -> remove
    eventBus.on('logoutSuccess', () => {
      this.isAuthenticated = false;
      localStorage.removeItem('isAuthenticated');
    })
  },
  methods: {
    async checkSession() {
      try {
        const response = await axios.get('/auth/session', { withCredentials: true });
        console.log('세션 유지 상태:', response.data);

        this.isAuthenticated = true;

        localStorage.setItem('isAuthenticated', 'true');
      } catch (error) {
        console.log('세션 없음:', error.response);

        this.isAuthenticated = false;

        localStorage.removeItem('isAuthenticated');
      }
    },
    async logout(){
      try{
        await axios.post('/api/auth/logout', {}, { withCredentials: true });
        console.log('로그아웃 성공');

        // 로그아웃 이벤트
        eventBus.emit('logoutSuccess');

        localStorage.removeItem('isAuthenticated');

        this.$router.push('/');
      } catch (error) {
        console.log('로그아웃 실패', error.response);
      }
    },
    openLoginModal(){
      eventBus.emit('openLoginModal');
    }
  }
};
</script>

<style scoped>
.navbar {
  background-color: #1a202c;
  color: white;
  padding: 1rem 2rem;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.nav-links {
  display: flex;
  gap: 20px;
  list-style-type: none;
  padding: 0;
  margin: 0;
}

.logo {
  font-size: 1.5rem;
  font-weight: bold;
  text-decoration: none;
  color: white;
}

.nav-item {
  text-decoration: none;
  color: white;
  cursor: pointer;
  background: none;
  border: none;
  font-size: 1rem;
  padding: 0;
}

.nav-item:hover {
  color: #ffcc00;
}

.logout-btn {
  background: none;
  border: none;
  color: white;
  font-size: 1rem;
  cursor: pointer;
  padding: 0;
}

.logout-btn:hover {
  color: #ffcc00;
}
</style>

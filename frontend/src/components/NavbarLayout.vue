<template>
  <nav class="navbar">
    <div class="container">
      <router-link to="/" class="logo">Typing-practice.github.io</router-link>
      <ul class="nav-links">
        <li><router-link to="/" class="nav-item">Home</router-link></li>
        <li><router-link to="/courses" class="nav-item">Courses</router-link></li>
        <li v-if="!isAuthenticated">
          <router-link to="/login" class="nav-item">Login</router-link>
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

export default {
  data() {
    return {
      isAuthenticated: false
    };
  },
  mounted() {
    this.checkSession();
  },
  methods: {
    async checkSession() {
      try {
        const response = await axios.get('/auth/me', { withCredentials: true });
        console.log('세션 유지 상태:', response.data);
        this.isAuthenticated = true;
      } catch (error) {
        console.log('세션 없음:', error.response);
        this.isAuthenticated = false;
      }
    },
    async logout(){
      try{
        await axios.get('/auth/logout');
        this.isAuthenticated = false;
        this.username = '';
        console.log('로그아웃 성공');
      } catch(error){
        console.log('로그아웃 실패', error.response);
      }
    },
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

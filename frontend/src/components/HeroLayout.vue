<template>
  <section class="hero">
    <div class="container">
      <h1>Improve Your Typing Skills</h1>
      <p>Practice typing with real-world coding exercises.</p>
      <button class="btn" @click="showModal = true">Get Started</button>
    </div>

    <!-- 모달 창 -->
    <div v-if="showModal" class="modal-overlay" @click.self="showModal = false">
      <div class="modal-content">
        <h2>Login</h2>
        <form @submit.prevent="submitForm">
          <div>
            <label for="email">Email</label>
            <input type="email" id="email" v-model="email" required/>
          </div>
          <div>
            <label for="password">Password</label>
            <input type="password" id="password" v-model="password" required/>
          </div>
          <button type="submit" class="google-btn">Login</button>
        </form>
        <div class="separator"></div>
        <button class="google-btn" @click="googleLogin">
          <img src="https://developers.google.com/identity/images/g-logo.png" alt="Google" class="google-logo"/>
          Sign in with Google
        </button>
        <div class="separator"></div>
        <p class="register-link" @click="openRegisterModal">Go to Register</p>
      </div>
    </div>

    <!-- 회원가입 모달 -->
    <div v-if="showRegisterModal" class="modal-overlay" @click.self="showRegisterModal = false">
      <div class="modal-content">
        <h2>Register</h2>
        <form @submit.prevent="submitRegisterForm">
          <div>
            <label for="register-email">Email</label>
            <input type="email" id="register-email" v-model="registerEmail" required/>
          </div>
          <div>
            <label for="register-username">Username</label>
            <input type="text" id="register-username" v-model="registerUsername" required/>
          </div>
          <div>
            <label for="register-password">Password</label>
            <input type="password" id="register-password" v-model="registerPassword" required/>
          </div>
          <button type="submit" class="google-btn">Register</button>
        </form>
        <div class="separator"></div>
        <p class="register-link" @click="openLoginModal">Go to Login</p>
      </div>
    </div>
  </section>
</template>

<script>
import axios from '@/api/axios';
import {eventBus} from "@/api/eventBus";

export default {
  data() {
    return {
      showModal: false,
      showRegisterModal: false,
      email: '',
      password: '',
      registerEmail: '',
      registerUsername: '',
      registerPassword: '',
      isAuthenticated: false,
      username: '',
    };
  },
  mounted() {
    eventBus.on('openLoginModal', () => {
      this.showModal = true;
    });

    this.checkSession();
    // console.log("mounted 실행:")
  },
  methods: {
    async submitForm() {
      try {
        const response = await axios.post(
            '/auth/login',
            {
              email: this.email,
              password: this.password,
            },
            {
              withCredentials: true
            });
        console.log('로그인 성공', response.data);

        this.isAuthenticated = true;
        this.username = response.data.username;
        this.showModal = false;

        eventBus.emit('loginSuccess');
      } catch (error) {
        console.log('로그인 실패', error.response);
        alert('로그인에 실패했습니다.');
      }
    },
    async submitRegisterForm() {
      try {
        await axios.post('/auth/register', {
          email: this.registerEmail,
          username: this.registerUsername,
          password: this.registerPassword,
        });
        alert('회원가입 성공!');
        this.showRegisterModal = false;
        this.showModal = true;
      } catch (error) {
        console.log('회원가입 실패', error.response);
        alert('회원가입에 실패했습니다.');
      }
    },
    async checkSession() {
      try {
        const response = await axios.get('/auth/me');
        console.log('세션 유지 상태:', response.data);

        this.isAuthenticated = true;
        this.username = response.data.username;
      } catch (error) {
        console.log('세션 없음', error.response);

        this.isAuthenticated = false;
      }
    },
    // async logout(){
    //   try{
    //     await axios.get('/auth/logout');
    //     this.isAuthenticated = false;
    //     this.username = '';
    //     console.log('로그아웃 성공');
    //   } catch(error){
    //     console.log('로그아웃 실패', error.response);
    //   }
    // },
    googleLogin() {
      console.log("Google login clicked");
      this.showModal = false; // 모달 닫기
      window.location.href = "http://localhost:8080/oauth2/authorization/google";
    },
    openRegisterModal() {
      this.showModal = false;
      this.showRegisterModal = true;
    },
    openLoginModal() {
      this.showRegisterModal = false;
      this.showModal = true;
    }
  }
};
</script>

<style scoped>
.hero {
  background-color: #2d3748;
  color: white;
  text-align: center;
  padding: 100px 20px;
}

.btn {
  background-color: #3182ce;
  padding: 10px 20px;
  border-radius: 5px;
  margin-top: 10px;
  cursor: pointer;
  width: auto;
  min-width: 150px;
}

.btn:hover {
  background-color: #2b6cb0;
}

.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.7);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.modal-content {
  background-color: white;
  padding: 30px;
  border-radius: 10px;
  width: 400px;
  text-align: center;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

input {
  padding: 10px;
  margin: 10px 0;
  border-radius: 5px;
  width: 100%;
  box-sizing: border-box;
  border: 1px solid #ddd;
  color: #333;
}

label {
  display: block;
  text-align: left;
  font-size: 14px;
  margin-bottom: 5px;
  color: #333;
}

button {
  padding: 10px 20px;
  background-color: #3182ce;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  width: 100%;
}

button:hover {
  background-color: #2b6cb0;
}

.separator {
  margin: 20px 0;
  color: #aaa;
  font-size: 14px;
}

.google-btn {
  background-color: #4285F4;
  color: white;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 10px 20px;
  border-radius: 5px;
  width: 100%;
  cursor: pointer;
}

.google-logo {
  width: 20px;
  margin-right: 10px;
}

.google-btn:hover {
  background-color: #357ae8;
}

.modal-content h2 {
  font-size: 1.5rem;
  margin-bottom: 20px;
  color: #333;
}

.register-link {
  margin-top: 15px;
  font-size: 14px;
  color: #3182ce;
  cursor: pointer;
  text-decoration: underline;
}

</style>

<template>
  <nav class="navbar">
    <div class="container">
      <router-link to="/" class="logo">Typing-practice.github.io</router-link>
      <ul class="nav-links">
        <li><router-link to="/" class="nav-item">Home</router-link></li>
        <li><router-link to="/courses" class="nav-item">Courses</router-link></li>
        <li><router-link to="/login" class="nav-item">Login</router-link></li>
      </ul>
    </div>
  </nav>
</template>

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
}

.nav-item:hover {
  color: #ffcc00;
}
</style>

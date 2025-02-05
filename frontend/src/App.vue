<template>
  <div class="app-container">
    <NavbarLayout />
    <router-view />
    <FooterLayout />
  </div>
</template>

<script>
import NavbarLayout from "@/components/NavbarLayout.vue";
import FooterLayout from "@/components/FooterLayout.vue";

export default {
  components: {
    NavbarLayout,
    FooterLayout,
  },
};
</script>

<style scoped>
.app-container {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
}
</style>

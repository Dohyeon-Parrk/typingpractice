/* eslint-disable */
<template>
  <div>
    <h1>Typing Results</h1>
    <ul>
      <li v-for="result in results" :key="result.id">
        Accuracy: {{ result.accuracy.toFixed(2) }}%, Time: {{ result.timeTaken }} seconds
      </li>
    </ul>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'TypingResults',  // 이름 변경 (또는 기존 단어 그대로 사용)
  data() {
    return {
      results: []
    };
  },
  created() {
    axios.get("/api/typing/results")
        .then(response => {
          this.results = response.data;
        })
        .catch(error => console.error("Error fetching results:", error));
  }
};
</script>

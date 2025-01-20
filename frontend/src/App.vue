<template>
  <div>
    <h1>Typing Practice</h1>
    <p>{{ sentence.content }}</p>
    <textarea v-model="userInput" placeholder="Type the sentence here..."></textarea>
    <button @click="submitPractice">Submit</button>

    <p v-if="result">Accuracy: {{ result.accuracy.toFixed(2) }}%</p>
    <p v-if="result">Time Taken: {{ result.timeTaken }} seconds</p>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      sentence: { content: "Loading..." },
      userInput: "",
      startTime: null,
      result: null
    };
  },
  created() {
    axios.get("/api/typing/sentence")
        .then(response => {
          this.sentence = response.data;
          this.startTime = new Date().getTime();
        })
        .catch(error => console.error("Error fetching the sentence:", error));
  },
  methods: {
    submitPractice() {
      const timeTaken = (new Date().getTime() - this.startTime) / 1000;

      const payload = {
        userInput: this.userInput,
        correctInput: this.sentence.content,
        timeTaken: timeTaken,
        accuracy: this.calculateAccuracy()
      };

      axios.post("/api/typing/submit", payload)
          .then(response => {
            this.result = response.data;
          })
          .catch(error => console.error("Error submitting the practice:", error));
    },
    calculateAccuracy() {
      const totalChars = this.sentence.content.length;
      let correctChars = 0;

      for (let i = 0; i < this.userInput.length; i++) {
        if (this.userInput[i] === this.sentence.content[i]) {
          correctChars++;
        }
      }

      return (correctChars / totalChars) * 100;
    }
  }
};
</script>

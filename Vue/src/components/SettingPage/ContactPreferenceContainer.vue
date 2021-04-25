<template>
  <v-card-text>
    <v-container class="pb-0">
      <v-row>
        <v-col cols="12" sm="12" md="12">
          <v-checkbox
            class="pa-0 ma-0"
            v-model="checkbox"
            label="Yes, I want to get text updates and reminders about my
          reservations."
          ></v-checkbox>
        </v-col>
      </v-row>
    </v-container>
  </v-card-text>
</template>

<script>
import { mapGetters } from "vuex";
export default {
  data() {
    return {
      checkbox: this.getGetReminders,
    };
  },
  computed: {
    ...mapGetters("account", ["getGetReminders"]),
  },
};
</script>

<style>
</style>
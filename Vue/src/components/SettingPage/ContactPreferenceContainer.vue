<template>
  <v-card-text>
    <v-container class="pb-0">
      <v-row>
        <v-col cols="12" sm="12" md="12">
          <v-checkbox
            class="pa-0 ma-0"
            @change="$emit('changeCheck')"
            v-model="checkboxCone"
            label="Yes, I want to get text updates and reminders about your
          reservations."
          ></v-checkbox>
        </v-col>
      </v-row>
    </v-container>
  </v-card-text>
</template>

<script>
export default {
  data() {
    return {
      checkboxCone: this.checkbox,
    };
  },
  props: {
    checkbox: Boolean,
  },
};
</script>

<style>
</style>
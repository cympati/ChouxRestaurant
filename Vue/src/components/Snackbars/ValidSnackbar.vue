<template>
  <!-- timeout="3000" -->
  <v-snackbar
    v-model="valid"
    :multi-line="multiLine"
    color="success"
    timeout="-1"
  >
    {{ textValid }}
    <template v-slot:action="{ attrs }">
      <v-btn color="white" text v-bind="attrs" @click="$emit('closeDialog')">
        Close
      </v-btn>
    </template>
  </v-snackbar>
</template>

<script>
export default {
  data() {
    return {
      multiLine: true,
    };
  },
  props: {
    valid: Boolean,
    textValid: String,
  },
};
</script>

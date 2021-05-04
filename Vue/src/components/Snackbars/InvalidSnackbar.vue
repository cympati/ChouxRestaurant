<template>
  <v-snackbar
    v-model="invalid"
    :multi-line="multiLine"
    color="error"
    timeout="-1"
  >
    {{ textInvalid }}
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
    invalid: Boolean,
    textInvalid: String,
  },
};
</script>




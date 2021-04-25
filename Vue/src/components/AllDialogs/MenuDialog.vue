<template>
  <v-dialog :value="dialog == i" max-width="790" persistent>
    <v-card class="d-flex pa-10">
      <v-img max-height="400" max-width="300" :src="menu.contents.src"></v-img>
      <div class="d-flex flex-column justify-space-between ml-3">
        <div>
          <div class="d-flex align-center pb-0">
            <v-card-title class="headline">
              {{ menu.eng.toUpperCase() }} ( {{ menu.chn }} )
            </v-card-title>
            <v-divider class="mr-3"></v-divider>
          </div>
          <v-card-text class="text-justify pt-1">
            {{ menu.contents.describ }}
          </v-card-text>
        </div>
        <div>
          <v-divider class="mx-3"></v-divider>
          <v-card-actions>
            <v-spacer></v-spacer>

            <v-btn color="red lighten-1" text @click="$emit('close')">
              close
            </v-btn>
          </v-card-actions>
        </div>
      </div>
    </v-card>
  </v-dialog>
</template>

<script>
export default {
  props: {
    menu: Object, // eng, chn
    i: Number,
    dialog: Number,
  },
};
</script>

<style>
</style>
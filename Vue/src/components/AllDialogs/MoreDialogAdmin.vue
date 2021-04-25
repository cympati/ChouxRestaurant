<template>
  <div>
    <template>
      <v-btn color="primary" dark @click="dialogMore = i"> More </v-btn>
    </template>
    <v-dialog :value="dialogMore == i" persistent max-width="600px">
      <v-card>
        <v-card-title>
          <span class="headline">More details</span>
        </v-card-title>
        <v-divider></v-divider>

        <v-card-text>
          <v-container>
            <v-row>
              <v-col cols="12" sm="6" md="6">
                <v-text-field
                  label="Firstname"
                  required
                  v-model="item.detail.firstName"
                  readonly
                ></v-text-field>
              </v-col>

              <v-col cols="12" sm="6" md="6">
                <v-text-field
                  label="Lastname"
                  v-model="item.detail.lastName"
                  required
                  readonly
                ></v-text-field>
              </v-col>
              <!-- ข้อมูลของv-modelต้องตรงกับitem -->
              <v-col cols="12" sm="6" md="6">
                <v-text-field
                  label="Phone number"
                  required
                  v-model="item.detail.phoneNumber"
                  readonly
                ></v-text-field>
              </v-col>
              <v-col cols="12" sm="6" md="6">
                <v-select
                  :items="[1, 2, 3, 4, 5, 6]"
                  label="Party size"
                  required
                  v-model="item.detail.partySize"
                  readonly
                ></v-select>
              </v-col>

              <v-col cols="12" sm="12" md="12">
                <v-text-field
                  label="Your email"
                  required
                  v-model="item.detail.email"
                  readonly
                ></v-text-field>
              </v-col>

              <v-col cols="12" sm="12" md="12">
                <v-text-field
                  label="Special requests"
                  required
                  readonly
                  v-model="item.detail.specialRequests"
                ></v-text-field>
              </v-col>
            </v-row>
          </v-container>
        </v-card-text>
        <v-divider></v-divider>
        <v-card-actions>
          <v-spacer></v-spacer>
          <div>
            <v-btn color="red lighten-1" text @click="dialogMore = null">
              Close
            </v-btn>
          </div>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      dialogMore: null,
    };
  },
  props: {
    i: Number,
    item: Object,
  },
};
</script>

<style>
</style>
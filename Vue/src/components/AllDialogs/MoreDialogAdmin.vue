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

        <v-card-text class="pb-0 pt-5">
          <v-container>
            <v-row>
              <v-col cols="12" sm="6" md="6">
                <v-text-field
                  label="Firstname"
                  required
                  v-model="item.firstName"
                  readonly
                ></v-text-field>
              </v-col>

              <v-col cols="12" sm="6" md="6">
                <v-text-field
                  label="Lastname"
                  v-model="item.lastName"
                  required
                  readonly
                ></v-text-field>
              </v-col>
              <v-col cols="12" sm="6" md="6">
                <v-text-field
                  label="Phone number"
                  required
                  v-model="item.phone"
                  readonly
                ></v-text-field>
              </v-col>
              <v-col cols="12" sm="6" md="6">
                <v-select
                  :items="[1, 2, 3, 4, 5, 6]"
                  label="Party size"
                  required
                  v-model="item.size"
                  readonly
                ></v-select>
              </v-col>

              <v-col cols="12" sm="12" md="12">
                <v-text-field
                  label="Your email"
                  required
                  v-model="item.email"
                  readonly
                ></v-text-field>
              </v-col>

              <v-col cols="12" sm="12" md="12">
                <v-textarea
                  outlined
                  readonly
                  name="Special request"
                  label="Special request"
                  :value="item.specialReq"
                  v-if="item.specialReq"
                ></v-textarea>
                <v-textarea
                  outlined
                  readonly
                  name="Special request"
                  label="Special request"
                  value="No special request"
                  v-else
                ></v-textarea>
              </v-col>
            </v-row>
          </v-container>
        </v-card-text>
        <v-divider class="mx-0"></v-divider>
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

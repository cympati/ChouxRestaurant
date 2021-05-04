<template>
  <div>
    <template>
      <v-btn color="primary" dark @click="dialogMore = i"> More </v-btn>
    </template>
    <v-dialog :value="dialogMore == i" persistent max-width="600px">
      <v-card>
        <VCardTitle :cardTitle="cardTitle" />
        <v-divider></v-divider>
        <v-card-text class="pb-0 pt-5">
          <v-container>
            <v-row>
              <v-col cols="12" sm="6" md="6">
                <v-text-field
                  label="Firstname"
                  required
                  readonly
                  requiredInfo
                  :rules="[rules.requiredInfo]"
                  v-model="item.firstName"
                ></v-text-field>
              </v-col>
              <v-col cols="12" sm="6" md="6">
                <v-text-field
                  label="Lastname"
                  v-model="item.lastName"
                  required
                  readonly
                  requiredInfo
                  :rules="[rules.requiredInfo]"
                ></v-text-field>
              </v-col>
              <v-col cols="12" sm="6" md="6">
                <v-text-field
                  label="Phone number"
                  required
                  readonly
                  requiredInfo
                  :rules="[rules.requiredInfo]"
                  v-model="item.phone"
                ></v-text-field>
              </v-col>
              <v-col cols="12" sm="6" md="6">
                <v-select
                  :items="[1, 2, 3, 4, 5, 6]"
                  label="Party size"
                  required
                  readonly
                  requiredInfo
                  :rules="[rules.requiredInfo]"
                  v-model="item.size"
                ></v-select>
              </v-col>

              <v-col cols="12" sm="12" md="12">
                <v-text-field
                  label="Your email"
                  required
                  readonly
                  requiredInfo
                  :rules="[rules.requiredInfo]"
                  v-model="item.email"
                ></v-text-field>
              </v-col>

              <v-col cols="12" sm="12" md="12">
                <v-textarea
                  ref="specialForm"
                  outlined
                  name="Special request"
                  label="Special request"
                  :value="item.specialReq"
                  v-model="specialReq"
                ></v-textarea>
              </v-col>
            </v-row>
            <small style="color: #212123"
              >*You can edit only
              <span style="color: #9f4c38">special requests</span> in here, but
              you can edit the others in
              <router-link to="/setting" style="text-decoration: none"
                ><span style="color: #9f4c38">setting</span></router-link
              >
            </small>
          </v-container>
        </v-card-text>
        <v-divider></v-divider>
        <v-card-actions>
          <v-spacer></v-spacer>
          <div>
            <!-- Dialog Confirm -->
            <v-btn color="blue darken-1" text @click="dialogConfirm = true">
              Edit
            </v-btn>
            <v-btn color="red lighten-1" text @click="dialogMore = null">
              Close
            </v-btn>
            <!-- :dialogMore="dialogMore" -->
            <ConfirmDialogMore
              :dialogConfirm="dialogConfirm"
              :checkConfirmPassword="confirmPassword"
              :getMatch="getMatch"
              :setMatch="setMatch"
              :rules="rules"
              @close="dialogConfirm = false"
              @changeInfo="changeInfo"
            />
            <!-- @changeDialogMore="dialogMore = null" -->
            <!-- <ValidSnackbar
              :valid="snackbarConfirmPasswordValid"
              :textValid="notificationTextConfirmPasswordValid"
              @changeValid="snackbarConfirmPasswordValid = false"
            /> -->
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
      dialogConfirm: false,
      dialogMore: null,
      cardTitle: "More details",
      specialReq: "",
      rules: {
        requiredInfo: (value) => !!value || "Required",
        min: (v) => (v && v.length >= 8) || "Min 8 characters",
      },
    };
  },
  props: {
    i: Number,
    item: Object,
    confirmPassword: Function,
    getMatch: Boolean,
    setMatch: Function,
    editRsv: Function,
  },
  components: {
    ConfirmDialogMore: () =>
      import("@/components/AllDialogs/ConfirmDialogMore"),
    VCardTitle: () => import("../JubJibComponent/VCardTitle"),
    // ValidSnackbar: () => import("../Snackbars/ValidSnackbar"),
  },
  methods: {
    changeInfo() {
      let info = {
        id: this.item.id,
        special: this.specialReq,
      };
      this.editRsv(info);
      // this.editRsv(info);
      // console.log(info);
      // change special req
    },
  },
  mounted() {
    this.specialReq = this.item.specialReq;
    // this.infoDetail = this.item;
    // console.log(this.infoDetail);
  },
};
</script>

<style>
</style>
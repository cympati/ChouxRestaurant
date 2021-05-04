<template>
  <div id="reservation" class="px-10 pt-1">
    <Layout :page="name">
      <VNavLeft />
      <div v-if="getIsLogin">
        <ReserveTableAdmin
          v-if="getIsAdmin"
          :headers="headers"
          :allReserve="getReserves"
          :approveRsv="approveRsv"
        />
        <ReserveTableUser v-else :headers="headers" :allReserve="getReserves" />
      </div>
      <div v-else>
        <span
          >You need to <a @click="setDialogLogin(true)">LOG IN</a> before</span
        >
      </div>
    </Layout>
    <Login />
    <ValidSnackbar
      :valid="getValidSnb.dialog"
      :textValid="getValidSnb.text"
      @closeDialog="setDialogSnbValid(false)"
    />
    <InvalidSnackbar
      :invalid="getInvalidSnb.dialog"
      :textInvalid="getInvalidSnb.text"
      @closeDialog="setDialogSnbInvalid(false)"
    />
    <ValidSnackbar
      :valid="getValidSnbRsv.dialog"
      :textValid="getValidSnbRsv.text"
      @closeDialog="setDialogSnbValidRsv(false)"
    />
    <InvalidSnackbar
      :invalid="getInvalidSnbRsv.dialog"
      :textInvalid="getInvalidSnbRsv.text"
      @closeDialog="setDialogSnbInvalidRsv(false)"
    />
  </div>
</template>

<script>
import { mapActions, mapGetters } from "vuex";
export default {
  components: {
    Login: () => import("../components/LoginPage/Login"),
    ValidSnackbar: () => import("../components/Snackbars/ValidSnackbar"),
    InvalidSnackbar: () => import("../components/Snackbars/InvalidSnackbar"),
    Layout: () => import("../components/Navbars/Layout"),
    VNavLeft: () => import("../components/Navbars/VNavLeft"),
    ReserveTableUser: () =>
      import("../components/ReservationPage/ReserveTableUser.vue"),
    ReserveTableAdmin: () =>
      import("../components/ReservationPage/ReserveTableAdmin.vue"),
  },
  data() {
    return {
      name: "Reservation",
      headers: [
        { text: "Id", value: "id", sortable: true, width: "50px" },
        { text: "Date", value: "date", sortable: false, width: "80px" },
        { text: "Times", value: "time", sortable: false, width: "50px" },
        {
          text: "Name",
          align: "start",
          value: "firstname",
          sortable: false,
          width: "200px",
        },
        { text: "", value: "more", sortable: false, width: "50px" },
        { text: "Status", value: "status", sortable: false, width: "50px" },
        { text: "", value: "action", sortable: false, width: "50px" },
      ],
    };
  },
  mounted() {
    this.loadReserves();
  },
  computed: {
    ...mapGetters("account", [
      "getIsLogin",
      "getIsAdmin",
      "getInvalidSnb",
      "getValidSnb",
    ]),
    ...mapGetters("reserve", [
      "getReserves",
      "getValidSnbRsv",
      "getInvalidSnbRsv",
    ]),
  },
  methods: {
    ...mapActions("account", [
      "setDialogLogin",
      "setDialogSnbValid",
      "setDialogSnbInvalid",
    ]),
    ...mapActions("reserve", [
      "loadReserves",
      "approveRsv",
      "setDialogSnbValidRsv",
      "setDialogSnbInvalidRsv",
    ]),
  },
};
</script>

<style scoped>
</style>

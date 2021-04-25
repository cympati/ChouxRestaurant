<template>
  <div id="reservation" class="px-10 pt-1">
    <Layout :page="name">
      <VNavLeft />
      <!-- <Dates /> -->
      <div v-if="getIsLogin">
        <ReserveTableUser
          v-if="adminOrUser"
          :headers="headers"
          :infoDetails="infoDetails"
        />
        <ReserveTableAdmin
          v-else
          :headers="headers"
          :infoDetails="infoDetails"
        />
        <div>
          <v-switch
            v-model="adminOrUser"
            inset
            label="Admin(off) or User(on)"
          ></v-switch>
        </div>
      </div>
      <div v-else>
        <span>You need to <a>LOG IN</a> before</span>
      </div>
    </Layout>
    <Login />
    <!-- <ProfileDrawer /> -->
  </div>
</template>

<script>
import { mapGetters } from "vuex";
export default {
  components: {
    Login: () => import("../components/LoginPage/Login"),

    Layout: () => import("../components/Navbars/Layout"),
    VNavLeft: () => import("../components/Navbars/VNavLeft"),
    // Dates: () => import("../components/Dates.vue"),
    ReserveTableUser: () =>
      import("../components/ReservationPage/ReserveTableUser.vue"),
    ReserveTableAdmin: () =>
      import("../components/ReservationPage/ReserveTableAdmin.vue"),
    // ProfileDrawer: () =>
    //   import("../components/ReservationPage/ProfileDrawer.vue"),
  },
  data() {
    return {
      name: "Reservation",
      adminOrUser: true,
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
      userInfo: {
        // User
        firstName: "Patiphon",
        lastName: "Klangpraphan",
        id: "234",
        email: "cympati@gmail.com",
        password: "28052545",
        phoneNumber: "0956600463",
        isAdmin: 0,
      },

      allReserveForAdmin: [
        {
          userInfo: {
            // User
            firstName: "Patiphon",
            lastName: "Klangpraphan",
            id: "234",
            email: "cympati@gmail.com",
            password: "28052545",
            phoneNumber: "0956600463",
            isAdmin: 0,
          },
          reserveDetails: {
            reserveId: "001",
            date: {
              year: "2002",
              month: "5",
              day: "28",
            },
            time: "11:00",
            partySize: "2",
            specialRequests: "Hungry so much!",
            // 0 = pending or upcoming, 1 = come, 2 = not come, 3 = complete, 4 = cancel
            status: 0,
          },
        },
        {
          userInfo: {
            // User
            firstName: "Jirasin",
            lastName: "Kimuji",
            id: "890",
            email: "Jirasin@mail.kmutt.ac.th",
            password: "7897886",
            phoneNumber: "0888888888",
            isAdmin: 0,
          },
          reserveDetails: {
            reserveId: "122",
            date: {
              year: "2002",
              month: "7",
              day: "12",
            },
            time: "10:00",
            partySize: "4",
            specialRequests: "Hungry so much!",
            // 0 = pending or upcoming, 1 = come, 2 = not come, 3 = complete, 4 = cancel
            status: 3,
          },
        },
        {
          userInfo: {
            // User
            firstName: "Apisit",
            lastName: "Maneerat",
            id: "567",
            email: "Apisit@mail.kmutt.ac.th",
            password: "5677767",
            phoneNumber: "0987654321",
            isAdmin: 0,
          },
          reserveDetails: {
            reserveId: "313",
            date: {
              year: "2001",
              month: "2",
              day: "9",
            },
            time: "09:00",
            partySize: "6",
            specialRequests: "dadsdsdsdsadsadsdadss",
            // 0 = pending or upcoming, 1 = come, 2 = not come, 3 = complete, 4 = cancel
            status: 4,
          },
        },
        {
          userInfo: {
            // User
            firstName: "Monthara",
            lastName: "Kiatruangchai",
            id: "768",
            email: "Monthara.k@mail.kmutt.ac.th",
            password: "5677767",
            phoneNumber: "0987654321",
            isAdmin: 0,
          },
          reserveDetails: {
            reserveId: "022",
            date: {
              year: "2001",
              month: "8",
              day: "26",
            },
            time: "18:00",
            partySize: "4",
            specialRequests: "Bla Bla Bla...",
            // 0 = pending or upcoming, 1 = come, 2 = not come, 3 = complete, 4 = cancel
            status: 0,
          },
        },
      ],
      allReserveForUser: {
        userInfo: {
          // User
          firstName: "Patiphon",
          lastName: "Klangpraphan",
          id: "234",
          email: "cympati@gmail.com",
          password: "28052545",
          phoneNumber: "0956600463",
          isAdmin: 0,
        },

        allReserve: [
          {
            reserveId: "001",
            date: {
              year: "2002",
              month: "5",
              day: "28",
            },
            time: "11:00",
            partySize: "2",
            specialRequests: "Hungry so much!",
            // 0 = pending or upcoming, 1 = come, 2 = not come, 3 = complete, 4 = cancel
            status: 0,
          },
          {
            reserveId: "122",
            date: {
              year: "2002",
              month: "7",
              day: "12",
            },
            time: "10:00",
            partySize: "4",
            specialRequests: "Hungry so much!",
            // 0 = pending or upcoming, 1 = come, 2 = not come, 3 = complete, 4 = cancel
            status: 3,
          },
          {
            reserveId: "313",
            date: {
              year: "2001",
              month: "2",
              day: "9",
            },
            time: "09:00",
            partySize: "6",
            specialRequests: "dadsdsdsdsadsadsdadss",
            // 0 = pending or upcoming, 1 = come, 2 = not come, 3 = complete, 4 = cancel
            status: 4,
          },
          {
            reserveId: "022",
            date: {
              year: "2001",
              month: "8",
              day: "26",
            },
            time: "18:00",
            partySize: "4",
            specialRequests: "Bla Bla Bla...",
            // 0 = pending or upcoming, 1 = come, 2 = not come, 3 = complete, 4 = cancel
            status: 0,
          },
        ],
      },

      infoDetails: [
        {
          id: "001",
          detail: {
            firstName: "Patiphon",
            lastName: "Klangpraphan",
            partySize: 2,
            email: "chancreamz@gmail.com",
            phoneNumber: "0956600463",
            specialRequests: "Hungry so much!",
          },
          date: {
            year: 2002,
            month: 5,
            day: 28,
          },
          time: "11:00",
          status: "upcoming",
          statusComplete: false,
          statusCancel: false,
        },
        {
          id: "122",
          detail: {
            firstName: "Jirasin",
            lastName: "Kimuji",
            partySize: 4,
            email: "Jirasin@mail.kmutt.ac.th",
            phoneNumber: "0888888888",
            specialRequests: "asdasdadadwdwadad",
          },
          date: {
            year: 2002,
            month: 7,
            day: 12,
          },
          time: "10:00",
          status: "complete",
          statusComplete: true,
          statusCancel: false,
        },
        {
          id: "313",
          detail: {
            firstName: "Apisit",
            lastName: "Maneerat",
            partySize: 6,
            email: "Apisit@mail.kmutt.ac.th",
            phoneNumber: "0987654321",
            specialRequests: "dadsdsdsdsadsadsdadss",
          },
          date: {
            year: 2001,
            month: 2,
            day: 9,
          },
          time: "09:00",
          status: "cancel",
          statusComplete: false,
          statusCancel: true,
        },
        {
          id: "022",
          detail: {
            firstName: "Monthara",
            lastName: "Kiatruangchai",
            partySize: 4,
            email: "Monthara.k@mail.kmutt.ac.th",
            phoneNumber: "08888888",
            specialRequests: "Bla Bla Bla...",
          },
          date: {
            year: 2001,
            month: 8,
            day: 26,
          },
          time: "18:00",
          status: "upcoming",
          statusComplete: false,
          statusCancel: false,
        },
      ],
    };
  },
  computed: {
    ...mapGetters("account", ["getIsLogin"]),
  },
};
</script>

<style scoped>
</style>

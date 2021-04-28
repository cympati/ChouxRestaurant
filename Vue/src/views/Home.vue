<template>
  <body class="home-page-wrapper">
    <!-- Dot -->

    <DotDotDot />
    <div class="home-page" id="home-page">
      <div class="nav-wrap">
        <div class="nav-left">
          <!-- Logo -->
          <div class="logo">
            <a href="#" v-scroll-to="'#home-page'">
              <LogoSVG />
            </a>
          </div>
          <!-- nav-list -->
          <NavList />
        </div>

        <!-- Login -->
        <div class="nav-right">
          <button
            v-if="!getIsLogin"
            @click="setDialogLogin(true)"
            class="login"
          >
            Login
          </button>
          <DropdownHome v-else-if="getIsLogin" :infoUser="getInfoUser" />
        </div>
      </div>

      <Login />

      <!-- Section 1 -->
      <SectionMain />

      <!-- Section 2 -->
      <SectionStory />

      <!-- Section 3 -->
      <SectionMenu />

      <!-- Section reservation -->
      <SectionReservation />

      <!-- Section contacts -->
      <SectionContact />

      <!-- Footer -->
      <footer>
        <p>&copy; copyright</p>
      </footer>
      <ValidSnackbar
        :valid="getSnackbar.dialog"
        :textValid="getSnackbar.text"
        @changeDialog="setDialogSnackbar(false)"
      />
    </div>
  </body>
</template>

<script>
import { mapActions, mapGetters } from "vuex";
export default {
  data() {
    return {
      // dialogLogin: false,
      loginStatus: false, // false = login, true = avatar
      // snackbarForgotPasswdValid: false,
      // notificationTextValid: "Your password is changed",
    };
  },

  methods: {
    ...mapActions("account", ["setDialogLogin", "setDialogSnackbar"]),
  },
  computed: {
    ...mapGetters("account", ["getIsLogin", "getSnackbar", "getInfoUser"]),
  },
  components: {
    Login: () => import("../components/LoginPage/Login"),
    DropdownHome: () => import("../components/HomePage/DropdownHome"),
    SectionMenu: () => import("../components/HomePage/Section-menu"),
    SectionStory: () => import("../components/HomePage/Section-story"),
    DotDotDot: () => import("../components/HomePage/DotDotDot"),
    NavList: () => import("../components/HomePage/NavList"),
    LogoSVG: () => import("../components/JubJibComponent/LogoSVG"),
    SectionContact: () => import("../components/HomePage/Section-contact"),
    SectionReservation: () =>
      import("../components/HomePage/Section-reservation"),
    SectionMain: () => import("../components/HomePage/Section-main"),
    ValidSnackbar: () => import("../components/Snackbars/ValidSnackbar"),
  },
};
</script>

<style lang="scss" scoped>
@import url("https://fonts.googleapis.com/css2?family=Nunito+Sans:wght@300;400;600;700;800;900&display=swap");

* {
  box-sizing: border-box;
  margin: 0;
  padding: 0;
  border: 0;
  font-size: 100%;
  font: inherit;
  vertical-align: baseline;
  text-decoration: none;
  color: inherit;
  line-height: 1;
  font-family: "Nunito Sans", sans-serif;
  list-style: none;
  quotes: none;
}

blockquote:before,
blockquote:after,
q:before,
q:after {
  content: "";
  content: none;
}

table {
  border-collapse: collapse;
  border-spacing: 0;
}

.nav-wrap {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 90%;
  margin-top: 40px;
  .nav-left {
    width: 70%;
    display: flex;
    justify-content: flex-start;
    align-items: center;
    .logo {
      width: 70px;
      height: 70px;
      transform: translateY(-7px);
      transition-duration: 1.5s;
      &:hover {
        transform: scale(1.3, 1.3);
        // transform: translateY(0px);
      }
      a {
        fill: #9f4c38;
      }
    }
  }
  .nav-right {
    button {
      font-weight: 900;
      font-size: 20px;
      background-color: white;
      border: 2px solid white;
      border-radius: 20px;
      transform: translateX(-6px);
      outline: none;
      cursor: pointer;
      color: #9f4c38;
      width: 100px;
      height: 35px;
      &:hover {
        border: 2px solid #9f4c38;
        border-radius: 20px;
        box-shadow: 2px 2px 5px rgba(33, 33, 35, 0.5);
      }
    }
  }
}

html {
  scroll-behavior: smooth;
}

.home-page-wrapper {
  display: flex;
  justify-content: center;
  .home-page {
    max-width: 1400px;
    width: 100%;
    display: flex;
    flex-direction: column;
    align-items: center;
  }
  footer {
    width: 100%;
    height: 3rem;
    color: white;
    font-size: 1rem;
    font-weight: 800;
    letter-spacing: 2px;
    background-color: black;
    text-align: end;
    padding: 1rem;
    text-transform: uppercase;
  }
}
</style>
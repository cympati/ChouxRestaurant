import Vue from "vue";
import Vuetify from "vuetify/lib/framework";

Vue.use(Vuetify);

export default new Vuetify({
  theme: {
    themes: {
      light: {
        base: "#faf3ee",
        purple: "#6079d6",
        blue: "#6079d6",
        black: "#212123",
        red: "#9f4c38",
        orange: "#ea7053",
      },
    },
  },
  icons: {
    iconfont: "mdi", // default
    values: {
      reservation: "mdi-calendar-today",
      setting: "mdi-cog",
      logOut: "mdi-logout-variant",
      resend: "mdi-restart",
    },
  },
});

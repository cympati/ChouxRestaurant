import Vue from "vue";
import router from "../../../router";
import axios from "../../../axios/axios";
// import qs from "qs";

export const setUserDetail = (app, value) => {
  app.commit("SET_USERDETAIL", value);
};

export const setGetReminders = (app, value) => {
  console.log(value);
  app.commit("SET_GETREMINDERS", value);
};

export const setColorSelect = (app, value) => {
  app.commit("SET_COLORSELECT", value);
};

export const setIsLogin = (app, value) => {
  app.commit("SET_ISLOGIN", value);
};

export const setDialogLogin = (app, value) => {
  app.commit("SET_DIALOGLOGIN", value);
};

export const setSnackbar = (app, value) => {
  app.commit("SET_SNACKBAR", value);
};

// account
export const checkLoginStatus = (app) => {
  if (app.getters.getLoginStatus) {
    router.push("/reservation");
  } else {
    app.commit("SET_DIALOGLOGIN", true);
  }
};

//login
export const loadDataFromLogin = async ({ commit }, loginInfo) => {
  console.log(loginInfo);
  await axios
    .post("/auth/login", {
      email: loginInfo.email,
      password: loginInfo.password,
    })
    .then((response) => {
      console.log(response.data);
      if (response.data.isLogin) {
        console.log(response.data);
        commit("SET_ISLOGIN", response.data.isLogin);
        commit("SET_USERDETAIL", response.data.userDetail);
        commit("SET_COLORSELECT", response.data.bgColor);
        commit("SET_GETREMINDERS", response.data.getRmd);
        commit("SET_DIALOGLOGIN", false);

        // set "token" cookies
        Vue.$cookies.set("token", response.data.token);
      } else {
        console.log("Your password is incorrect :(");
      }
    })
    .catch((error) => console.log(error));
};

//register
export const register = async ({ dispatch }, newUserForm) => {
  await axios
    .post("/auth/register", {
      fn: newUserForm.fn,
      ln: newUserForm.ln,
      em: newUserForm.em,
      phone: newUserForm.phone,
      passwd: newUserForm.passwd,
      getRmd: newUserForm.getRmd,
    })
    .then((response) => {
      if (response.data.loginStatus) {
        console.log(newUserForm.getRmd);
        const loginInfo = {
          email: newUserForm.em,
          password: newUserForm.passwd,
        };
        dispatch("loadDataFromLogin", loginInfo);
      } else {
        alert(response.data.text);
      }
    })
    .catch((error) => console.log(error));
};

//forgotpassword
export const forgot = (_app, email) => {
  axios
    .post("/auth/forgotpassword", { email: email })
    .then((response) => {
      if (response.data.success) {
        alert(response.data.text);
      } else {
        alert(response.data.text);
      }
    })
    .catch((error) => console.log(error));
};

//load data from token
export const loadDataFromToken = ({ commit }) => {
  axios.post(`/auth/load`).then(
    (response) => {
      if (response.data.isLogin) {
        commit("SET_ISLOGIN", response.data.isLogin);
        commit("SET_USERDETAIL", response.data.userDetail);
        commit("SET_COLORSELECT", response.data.bgColor);
        commit("SET_GETREMINDERS", response.data.getRmd);
      } else {
        console.log("What happend(?)");
      }
    },
    (error) => console.log("Something Wrong :(", error)
  );
};

//logout
export const logout = ({ commit }) => {
  Vue.$cookies.remove("token");
  if (!Vue.$cookies.get("token")) {
    commit("SET_ISLOGIN", false);
    commit("SET_USER", {});
  }
  if (router.currentRoute.name !== "Home") {
    router.push({ name: "Home" });
  }
  alert("logout already");
};

import Vue from "vue";
import router from "../../../router";
import axios from "../../../axios/axios";
import qs from "qs";

export const setUserDetail = (app, value) => {
  app.commit("set_userDetail", value);
};

export const setGetReminders = (app, value) => {
  console.log(value);
  app.commit("set_getReminders", value);
};

export const setColorSelect = (app, value) => {
  app.commit("set_colorSelect", value);
};

export const setIsLogin = (app, value) => {
  app.commit("set_isLogin", value);
};

export const setDialogLogin = (app, value) => {
  app.commit("set_dialogLogin", value);
};

export const setSnackbarForgotPasswdValid = (app, value) => {
  app.commit("set_snackbarForgotPasswdValid", value);
};

// account
export const checkLoginStatus = (app) => {
  if (app.getters.getLoginStatus) {
    router.push("/reservation");
  } else {
    app.commit("set_dialogLogin", true);
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
        commit("set_isLogin", response.data.isLogin);
        commit("set_userDetail", response.data.userDetail);
        commit("set_colorSelect", response.data.bgColor);
        commit("set_getReminders", response.data.getRmd);
        commit("set_dialogLogin", false);
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
export const forgot = (email) => {
  axios
    .post("/auth/forgotpassword", qs.stringify(email))
    .then((response) => {
      if (response.data.success) {
        alert(response.data.text);
      } else {
        alert(response.data.text);
      }
    })
    .catch((error) => console.error(error));
};

//load data from token
export const loadDataFromToken = ({ commit }) => {
  axios
    .post(`/auth/load`, qs.stringify({ token: Vue.$cookies.get("token") }))
    .then(
      (response) => {
        if (response.data.loginStatus) {
          console.log(response.data.userDetail);
          commit("set_isLogin", response.data.loginStatus);
          commit("set_userDetail", response.data.userDetail);
          commit("set_colorSelect", response.data.bgColor);
          commit("set_getReminders", response.data.getRmd);
        } else {
          console.error("Your token is incorrect :(");
        }
      },
      (error) => console.error("Something Wrong :(", error)
    );
};

//logout
export const logout = () => {
  console.log("logout already");
  Vue.$cookies.remove("token");
  location.reload();
};

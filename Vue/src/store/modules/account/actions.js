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

// Login
export const setIsLogin = (app, value) => {
  app.commit("SET_ISLOGIN", value);
};
export const setDialogLogin = (app, value) => {
  app.commit("SET_DIALOGLOGIN", value);
};
//

// Snackbar
export const setValidSnb = (app, value) => {
  app.commit("SET_VALIDSNB", value);
};
export const setInvalidSnb = (app, value) => {
  app.commit("SET_INVALIDSNB", value);
};
export const setDialogSnbValid = (app, value) => {
  app.commit("SET_DIALOGSNBVALID", value);
};
export const setDialogSnbInvalid = (app, value) => {
  app.commit("SET_DIALOGSNBINVALID", value);
};
//

// account
export const checkIsLogin = (app) => {
  if (app.getters.getIsLogin) {
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

//Logout
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

// Profile
export const editProfile = async ({ commit }, info) => {
  await axios
    .patch("/edit/profile", {
      color: info.color,
      firstName: info.firstName,
      lastName: info.lastName,
      email: info.email,
      phone: info.phone,
      getReminders: info.getReminders,
    })
    .then((response) => {
      const snackbar = {
        dialog: response.data.success,
        text: response.data.text,
      };
      if (response.data.success) {
        commit("SET_VALIDSNBRSV", snackbar);
      } else {
        commit("SET_INVALIDSNBRSV", snackbar);
      }
    })
    .catch((error) => console.log(error));
};

// New password
export const editPassword = async ({ commit }, newPassword) => {
  await axios
    .patch("/edit/password", {
      newPassword: newPassword,
    })
    .then((response) => {
      const snackbar = {
        dialog: response.data.success,
        text: response.data.text,
      };
      if (response.data.success) {
        commit("SET_VALIDSNBRSV", snackbar);
      } else {
        commit("SET_INVALIDSNBRSV", snackbar);
      }
    })
    .catch((error) => console.log(error));
};

// Confirm password
export const confirmPassword = async ({ commit }, confirmPassword) => {
  await axios
    .patch("/edit/password", {
      confirmPasswd: confirmPassword,
    })
    .then((response) => {
      const snackbar = {
        dialog: response.data.success,
        text: response.data.text,
      };
      if (response.data.success) {
        commit("SET_VALIDSNBRSV", snackbar);
      } else {
        commit("SET_INVALIDSNBRSV", snackbar);
      }
    })
    .catch((error) => console.log(error));
};

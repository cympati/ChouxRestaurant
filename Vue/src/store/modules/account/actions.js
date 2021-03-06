import Vue from "vue";
import router from "../../../router";
import axios from "../../../axios/axios";

export const setUserDetail = (app, value) => {
  app.commit("SET_USERDETAIL", value);
};
export const setNewPasswd = (app, value) => {
  app.commit("SET_NEWPASSWORD", value);
};
export const setConfirmPasswd = (app, value) => {
  app.commit("SET_CONFIRMPASSWORD", value);
};

export const setMatch = (app, value) => {
  app.commit("SET_MATCH", value);
};

export const setGetReminders = (app, value) => {
  app.commit("SET_GETREMINDERS", value);
};

export const setColorSelect = (app, value) => {
  app.commit("SET_COLORSELECT", value);
};

export const setResetId = (app, value) => {
  app.commit("SET_RESETID", value);
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
  await axios
    .post("/auth/login", {
      email: loginInfo.email,
      password: loginInfo.password,
    })
    .then((response) => {
      const snackbar = {
        dialog: true,
        text: response.data.text,
      };
      if (Vue.$cookies.get("token")) {
        Vue.$cookies.remove("token");
        commit("SET_ISLOGIN", false);
        commit("SET_USERDETAIL", {});
      }
      if (response.data.isLogin) {
        commit("SET_ISLOGIN", response.data.isLogin);
        commit("SET_USERDETAIL", response.data.userDetail);
        commit("SET_COLORSELECT", response.data.bgColor);
        commit("SET_GETREMINDERS", response.data.getRmd);
        commit("SET_DIALOGLOGIN", false);

        // set "token" , "isAdmin" cookies
        Vue.$cookies.set("token", response.data.token);
        // Vue.$cookies.set("isAdmin", response.data.userDetail.isAdmin);
        commit("SET_VALIDSNB", snackbar);
      } else {
        commit("SET_INVALIDSNB", snackbar);
      }
    })
    .catch((error) => console.log(error));
};

//register
export const register = async ({ dispatch, commit }, newUserForm) => {
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
      if (response.data.isLogin) {
        const loginInfo = {
          email: newUserForm.em,
          password: newUserForm.passwd,
        };
        dispatch("loadDataFromLogin", loginInfo);
      } else {
        const snackbar = {
          dialog: true,
          text: response.data.text,
        };
        commit("SET_INVALIDSNB", snackbar);
      }
    })
    .catch((error) => console.log(error));
};

//forgotpassword
export const forgot = async ({ commit }, email) => {
  await axios
    .post("/auth/forgot", { email: email })
    .then((response) => {
      if (response.data.success) {
        commit("SET_EMAILVALID", true);
        commit("SET_RESETID", response.data.id_reset);
      } else {
        let snackbar = {
          dialog: true,
          text: response.data.text,
        };
        commit("SET_INVALIDSNB", snackbar);
        commit("SET_EMAILVALID", false);
      }
    })
    .catch((error) => console.log(error));
};

//load data from token
export const loadDataFromToken = async ({ commit }) => {
  await axios.get(`/auth/load`).then(
    (response) => {
      if (response.data.isLogin) {
        commit("SET_ISLOGIN", response.data.isLogin);
        commit("SET_USERDETAIL", response.data.userDetail);
        commit("SET_COLORSELECT", response.data.bgColor);
        commit("SET_GETREMINDERS", response.data.getRmd);
      } else if (!response.data.isLogin) {
        let snackbar = {
          dialog: true,
          text: "Load data fail",
        };
        commit("SET_INVALIDSNB", snackbar);
      } else {
        let snackbar = {
          dialog: true,
          text: response.data.text,
        };
        commit("SET_INVALIDSNB", snackbar);
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
    commit("SET_USERDETAIL", {});
    window.location.href = "/";
  } else {
    let snackbar = {
      dialog: true,
      text: "Logout fail",
    };
    commit("SET_INVALIDSNB", snackbar);
  }
};
//

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
        dialog: true,
        text: response.data.text,
      };
      if (response.data.success) {
        commit("SET_VALIDSNB", snackbar);
      } else {
        commit("SET_INVALIDSNB", snackbar);
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
        dialog: true,
        text: response.data.text,
      };
      if (response.data.success) {
        commit("SET_VALIDSNB", snackbar);
      } else {
        commit("SET_INVALIDSNB", snackbar);
      }
    })
    .catch((error) => console.log(error));
};

// Confirm password
export const confirmPassword = async ({ commit }, confirmPassword) => {
  await axios
    .patch("/confirm/password", {
      confirmPasswd: confirmPassword,
    })
    .then((response) => {
      const snackbar = {
        dialog: true,
        text: response.data.text,
      };
      if (response.data.success) {
        commit("SET_DIALOGSNBINVALID", false);
        commit("SET_MATCH", response.data.success);
        // commit("SET_VALIDSNB", snackbar);
      } else {
        commit("SET_MATCH", response.data.success);
        commit("SET_INVALIDSNB", snackbar);
      }
    })
    .catch((error) => console.log(error));
};

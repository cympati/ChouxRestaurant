// user
export const set_userDetail = (state, value) => {
  console.log(value);
  state.infoUser.userDetail = value;
  console.log(state.infoUser.userDetail);
};

export const set_getReminders = (state, value) => {
  console.log(value);
  state.infoUser.getReminders = value;
};

export const set_colorSelect = (state, value) => {
  state.colorSelect = value;
};

export const set_isLogin = (state, value) => {
  console.log("loginStatus : ", value);
  state.isLogin = value;
};

export const set_dialogLogin = (state, value) => {
  state.dialogLogin = value;
};

export const set_snackbarForgotPasswdValid = (state, value) => {
  state.snackbarForgotPasswdValid = value;
};

// user
export const getInfoUser = (state) => {
  return state.infoUser;
};

export const getGetReminders = (state) => state.infoUser.getReminders;

export const getColorSelect = (state) => state.colorSelect;

export const getIsLogin = (state) => state.isLogin;

export const getDialogLogin = (state) => state.dialogLogin;

export const getSnackbarForgotPasswdValid = (state) =>
  state.snackbarForgotPasswdValid;

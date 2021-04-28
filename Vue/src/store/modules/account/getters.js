// user
export const getInfoUser = (state) => {
  return state.infoUser;
};

export const getTwoChar = (state) => {
  return state.infoUser.userDetail.firstName.substring(0, 2).toUpperCase();
};

export const getGetReminders = (state) => state.infoUser.getReminders;

export const getColorSelect = (state) => state.colorSelect;

export const getIsLogin = (state) => state.isLogin;

export const getDialogLogin = (state) => state.dialogLogin;

export const getSnackbar = (state) => state.snackbar;

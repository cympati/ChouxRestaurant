// user
export const getInfoUser = (state) => {
  return state.infoUser;
};
export const getIsAdmin = (state) => {
  return state.infoUser.userDetail.isAdmin;
};

export const getNewPasswordForm = (state) => {
  return state.newPasswordForm;
};

export const getMatch = (state) => {
  return state.match;
};

export const getTwoChar = (state) => {
  return state.infoUser.userDetail.firstName?.substring(0, 2).toUpperCase();
};

export const getGetReminders = (state) => {
  return state.infoUser.getReminders;
};

export const getColorSelect = (state) => state.colorSelect;

export const getIsLogin = (state) => state.isLogin;

export const getDialogLogin = (state) => state.dialogLogin;

export const getValidSnb = (state) => state.validSnb;
export const getInvalidSnb = (state) => state.invalidSnb;

export const getResetId = (state) => state.resetId;
export const getEmailValid = (state) => state.emailValid;

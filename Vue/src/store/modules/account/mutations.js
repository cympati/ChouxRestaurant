// user
export const SET_USERDETAIL = (state, value) => {
  state.infoUser.userDetail = value;
};

export const SET_GETREMINDERS = (state, value) => {
  state.infoUser.getReminders = value;
};

export const SET_COLORSELECT = (state, value) => {
  state.colorSelect = value;
};

export const SET_ISLOGIN = (state, value) => {
  state.isLogin = value;
};

export const SET_DIALOGLOGIN = (state, value) => {
  state.dialogLogin = value;
};

export const SET_SNACKBAR = (state, value) => {
  state.snackbar = value;
};

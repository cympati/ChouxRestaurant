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

// Snackbar
export const SET_VALIDSNB = (state, value) => {
  state.validSnb = value;
};
export const SET_INVALIDSNB = (state, value) => {
  state.invalidSnb = value;
};

export const SET_DIALOGSNBVALID = (state, value) => {
  state.validSnb.dialog = value;
};

export const SET_DIALOGSNBINVALID = (state, value) => {
  state.invalidSnb.dialog = value;
};
//

// user
export const SET_USERDETAIL = (state, value) => {
  state.infoUser.userDetail = value;
};
export const SET_NEWPASSWORD = (state, value) => {
  state.newPasswordForm.new = value;
};
export const SET_CONFIRMPASSWORD = (state, value) => {
  state.newPasswordForm.confirm = value;
};

export const SET_MATCH = (state, value) => {
  console.log(value);
  state.match = value;
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
  console.log("valid : " + value.text);
  state.invalidSnb.dialog = false;
  state.validSnb = value;
};
export const SET_INVALIDSNB = (state, value) => {
  console.log("Invalid : " + value.text);
  state.validSnb.dialog = false;
  state.invalidSnb = value;
};

export const SET_DIALOGSNBVALID = (state, value) => {
  state.validSnb.dialog = value;
};

export const SET_DIALOGSNBINVALID = (state, value) => {
  state.invalidSnb.dialog = value;
};
//

export const SET_RESETID = (state, value) => {
  console.log(value);
  state.resetId = value;
};

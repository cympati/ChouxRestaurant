export const SET_RESERVES = (state, value) => {
  state.reserves = value;
};

// Snackbar
export const SET_VALIDSNBRSV = (state, value) => {
  state.invalidSnb.dialog = false;
  state.validSnb = value;
};

export const SET_INVALIDSNBRSV = (state, value) => {
  state.validSnb.dialog = false;
  state.invalidSnb = value;
};

export const SET_DIALOGSNBVALIDRSV = (state, value) => {
  state.validSnb.dialog = value;
};

export const SET_DIALOGSNBINVALIDRSV = (state, value) => {
  state.invalidSnb.dialog = value;
};
//

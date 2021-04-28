export const SET_RESERVES = (state, value) => {
  state.reserves = value;
};

// Snackbar
export const SET_VALIDSNBRSV = (state, value) => {
  state.validSnb = value;
};

export const SET_INVALIDSNBRSV = (state, value) => {
  state.invalidSnb = value;
};

export const SET_DIALOGSNBVALIDRSV = (state, value) => {
  state.validSnb.dialog = value;
};

export const SET_DIALOGSNBINVALIDRSV = (state, value) => {
  state.invalidSnb.dialog = value;
};
//

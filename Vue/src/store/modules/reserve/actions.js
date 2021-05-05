import axios from "../../../axios/axios";

export const setReserves = (app, value) => {
  app.commit("SET_RESERVES", value);
};

// Snackbar
export const setValidSnbRsv = (app, value) => {
  app.commit("SET_VALIDSNBRSV", value);
};
export const setInvalidSnbRsv = (app, value) => {
  app.commit("SET_INVALIDSNBRSV", value);
};
export const setDialogSnbValidRsv = (app, value) => {
  app.commit("SET_DIALOGSNBVALIDRSV", value);
};
export const setDialogSnbInvalidRsv = (app, value) => {
  app.commit("SET_DIALOGSNBINVALIDRSV", value);
};
//

// Add
export const addRsv = async ({ commit }, newReserve) => {
  await axios
    .post("/reserve/add", {
      date_time: newReserve.dt,
      size: newReserve.size,
      req: newReserve.req,
    })
    .then((response) => {
      const snackbar = {
        dialog: true,
        text: response.data.text,
      };
      if (response.data.success) {
        commit("SET_VALIDSNBRSV", snackbar);
      } else {
        commit("SET_INVALIDSNBRSV", snackbar);
      }
    })
    .catch((error) => console.log(error));
};

// Approve
export const approveRsv = async ({ commit }, info) => {
  await axios
    .patch("/reserve/approve", {
      id_rsv: info.id,
      status: info.status,
    })
    .then((response) => {
      const snackbar = {
        dialog: true,
        text: response.data.text,
      };
      if (response.data.success) {
        location.reload();
      } else {
        commit("SET_INVALIDSNBRSV", snackbar);
      }
    })
    .catch((error) => console.log(error));
};

// Edit
export const editRsv = async ({ commit }, info) => {
  await axios
    .patch("/reserve/edit", {
      id_rsv: info.id,
      special_req: info.special,
    })
    .then((response) => {
      const snackbar = {
        dialog: true,
        text: response.data.text,
      };
      if (response.data.success) {
        location.reload();
      } else {
        commit("SET_INVALIDSNBRSV", snackbar);
      }
    })
    .catch((error) => console.log(error));
};

// Reserves
export const loadReserves = async ({ commit }) => {
  await axios
    .get("/reserves/all")
    .then((response) => {
      if (!response.data.success) {
        const snackbar = {
          dialog: true,
          text: response.data.text,
        };
        commit("SET_INVALIDSNBRSV", snackbar);
      } else {
        commit("SET_RESERVES", response.data.reserves);
      }
    })
    .catch((error) => console.log(error));
};

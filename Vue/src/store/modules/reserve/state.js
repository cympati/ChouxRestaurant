export default {
  reserves: [
    {
      reserveId: "122",
      dateTime: "2021-04-07 17:30:00",
      size: "4",
      specialReq: "Hungry so much!",
      // 0 = pending or upcoming, 1 = come, 2 = not come, 3 = complete, 4 = cancel
      status: "pending",
    },
    {
      reserveId: "313",
      dateTime: "2021-04-07 17:30:00",
      size: "6",
      specialReq: "dadsdsdsdsadsadsdadss",
      // 0 = pending or upcoming, 1 = come, 2 = not come, 3 = complete, 4 = cancel
      status: "complete",
    },
    {
      reserveId: "022",
      dateTime: "2021-04-07 17:30:00",
      size: "4",
      specialReq: "Bla Bla Bla...",
      // 0 = pending or upcoming, 1 = come, 2 = not come, 3 = complete, 4 = cancel
      status: "cancel",
    },
  ],
  validSnb: {
    dialog: false,
    text: "null",
  },
  invalidSnb: {
    dialog: false,
    text: "null",
  },
};

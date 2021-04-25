<template>
  <div>
    <div class="my-6"></div>
    <UpcomingTableUser
      :headers="headers"
      :infoDetails="infoDetails.filter((info) => info.status === 'upcoming')"
    />
    <v-divider class="my-10"></v-divider>
    <PastTableUser
      :headers="headers"
      :infoDetails="
        infoDetails.filter(
          (info) => info.status === 'complete' || info.status === 'cancel'
        )
      "
    />
  </div>
</template>

<script>
export default {
  components: {
    UpcomingTableUser: () => import("../ReservationTables/UpcomingTableUser"),
    PastTableUser: () => import("../ReservationTables/PastTableUser"),
  },
  data() {
    return {};
  },
  props: {
    headers: Array,
    infoDetails: Array,
  },
};
</script>

<style lang="scss" scoped>
</style>
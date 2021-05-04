<template>
  <div>
    <div class="my-6"></div>
    <UpcomingTableUser
      :headers="headers"
      :allReserve="
        allReserve && allReserve.filter((val) => val.status === 'pending')
      "
    />
    <v-divider class="my-10"></v-divider>
    <PastTableUser
      :headers="headers"
      :allReserve="
        allReserve &&
        allReserve.filter(
          (info) => info.status === 'complete' || info.status === 'cancel'
        )
      "
    />
    <v-divider class="my-10"></v-divider>
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
    allReserve: Array,
  },
};
</script>

<style lang="scss" scoped>
</style>

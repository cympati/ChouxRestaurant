<template>
  <div class="nav-list">
    <a href="#" v-scroll-to="'#home-page'">Home</a>
    <a href="#" v-scroll-to="'#section-2'">About us</a>
    <a href="#" v-scroll-to="'#section-reservation'">Reservation</a>
    <a href="#" v-scroll-to="'#section-contacts'">Contact</a>
    <a href="#" v-scroll-to="'#section-3'">Menu</a>
  </div>
</template>


<style lang="scss" scoped>
* {
  box-sizing: border-box;
  margin: 0;
  padding: 0;
  border: 0;
  font-size: 100%;
  font: inherit;
  vertical-align: baseline;
  text-decoration: none;
  color: inherit;
  line-height: 1;
  list-style: none;
}
.nav-list {
  padding-left: 20px;
  font-weight: 800;
  a {
    margin-left: 30px;
    &:hover {
      color: #6079d6;
    }
  }
}
</style>
<template>
  <div class="dot-holder">
    <div class="dot-wrap">
      <a href="#" v-scroll-to="'#home-page'">
        <div class="dot"></div>
      </a>
      <a href="#" v-scroll-to="'#section-2'">
        <div class="dot"></div>
      </a>
      <a href="#" v-scroll-to="'#section-3'">
        <div class="dot"></div>
      </a>
      <a href="#" v-scroll-to="'#section-reservation'">
        <div class="dot"></div>
      </a>
      <a href="#" v-scroll-to="'#section-contacts'">
        <div class="dot"></div>
      </a>
    </div>
  </div>
</template>


<style lang="scss" scoped>
.dot-holder {
  max-width: 1400px;
  width: 100%;
  position: fixed;
  .dot-wrap {
    position: absolute;
    width: 25px;
    height: 180px;
    right: 22px;
    top: 50vh;
    transform: translateY(-50%);
    display: flex;
    flex-direction: column;
    justify-content: space-around;
    .dot {
      width: 13px;
      height: 13px;
      border-radius: 50%;
      background-color: #6079d6;
      &:hover {
        box-shadow: 3px 6px 5px #6079d6;
      }
    }
  }
}
</style>
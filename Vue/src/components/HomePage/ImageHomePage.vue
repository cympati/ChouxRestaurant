<template>
  <div class="img">
    <div class="circle"></div>
    <img class="chopstick" id="cs-1" src="../../assets/chopstick.png" alt="" />
    <img class="chopstick" id="cs-2" src="../../assets/chopstick.png" alt="" />
    <img class="noodle" src="../../assets/noodle.png" alt="" />
  </div>
</template>

<style lang="scss" scoped>
.img {
  flex: 1;
  position: relative;
  height: 800px;
  overflow: hidden;
  #cs-1 {
    position: absolute;
    top: 90px;
    left: -350px;
    transform: rotate(22deg);
  }
  #cs-2 {
    position: absolute;
    top: 80px;
    left: -250px;
    transform: rotate(12deg);
  }
  div {
    position: absolute;
    top: 22rem;
    left: 130px;
    width: 300px;
    height: 300px;
    border-radius: 50%;
    background-color: #6079d6;
  }
  .noodle {
    position: absolute;
    top: 2rem;
    left: 45px;
    width: 45rem;
    height: 45rem;
  }
}
</style>
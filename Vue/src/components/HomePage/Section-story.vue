<template>
  <section class="section-2" id="section-2">
    <v-lazy
      :options="{
        threshold: 0.9,
      }"
      min-height="300"
      transition="slide-x-reverse-transition"
    >
      <div class="line"></div>
    </v-lazy>
    <v-lazy
      :options="{
        threshold: 0.65,
      }"
      min-height="200"
      transition="slide-x-transition"
    >
      <div class="img"></div>
    </v-lazy>
    <div class="rectangle">
      <h1>Our Story</h1>
      <span>
        {{ paragraph }}
      </span>
    </div>
  </section>
</template>

<script>
export default {
  data() {
    return {
      paragraph:
        "ipsum dolor sit amet, consectetur adipisicing elit. Explicabo, sint eanecessitatibus, optio ex veniam dolorem ducimus sunt, nihil perferendismollitia! Magni ratione error dolores quis adipisci vel, fugiat omnispraesentium? Tempora laboriosam tenetur harum ipsam eum soluta atquevoluptas animi inventore, ducimus fuga sequi modi. Deserunt id harumofficiis iste assumenda sequi ipsum nostrum doloribus",
    };
  },
};
</script>

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
  quotes: none;
}

.section-2 {
  background-color: #faf3ee;
  height: 850px;
  width: 90%;
  border-radius: 0 0 10rem 0;
  position: relative;
  .line {
    position: relative;
    height: 0.8rem;
    width: 8rem;
    background-color: #212123;
    left: 54rem;
    top: 5rem;
  }
  .img {
    position: absolute;
    background-image: url("../../assets/children.png");
    background-size: cover;
    background-position: 5% 50%;
    width: 22rem;
    height: 30rem;
    top: 13rem;
    left: 16rem;
    overflow: hidden;
  }
  .rectangle {
    position: absolute;
    background-color: #9f4c38;
    height: 22rem;
    width: 30rem;
    top: 7rem;
    left: 32rem;
    color: white;
    padding: 4rem 1.5rem;
    transition: 0.3s;
    span {
      letter-spacing: 1.45px;
      line-height: 1.3rem;
    }
    &:hover {
      transform: scale(1.05) translateY(-5px);
    }
    h1 {
      font-weight: 800;
      font-size: 2.8rem;
      letter-spacing: 3px;
      margin-bottom: 1.7rem;
    }
  }
}
</style>
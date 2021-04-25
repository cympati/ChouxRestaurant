<template>
  <section class="section-3" id="section-3">
    <v-lazy
      :options="{
        threshold: 0.5,
      }"
      transition="slide-x-transition"
    >
      <div class="board-wrap">
        <div v-for="(menu, i) in menues" :key="i">
          <MenuDisplay :menu="menu" :i="i" />
        </div>
      </div>
    </v-lazy>
  </section>
</template>

<script>
export default {
  data() {
    return {
      menues: [
        {
          eng: "soup",
          chn: "汤类",
          id: "b-soups",
          src: require("../../assets/soup_img1.png"),
          contents: {
            src: require("../../assets/soup_img.jpeg"),
            describ: `Soups are served between the cold dishes and the main dishes. In other parts of China, soups are served between the main dish and staple foods, before desserts or fruit salad. There are many traditional Chinese soups, such as wonton soup, herbal chicken soup, hot and sour soup, winter melon soup and so on.`,
          },
        },
        {
          eng: "dumpling",
          chn: "饺子",
          id: "b-dumplings",
          src: require("../../assets/dumplings3_img.jpg"),
          height: "115%",
          width: "115%",
          contents: {
            src: require("../../assets/dumplings3_img.jpeg"),
            describ: `Dumplings are usually boiled, steamed or fried and continue to be a traditional dish. Some people will place a coin or candy inside the dumpling in the hope of obtaining a fortune or having a sweet life, on Chinese New Year's Eve and special family reunions. Particularly, in Northern China, people generally eat dumplings on the Winter solstice in the hope of a warm winter. Dumplings are commonly eaten with a dipping sauce made of vinegar and chilli oil or paste, and occasionally with some soy sauce added in.`,
          },
        },
        {
          eng: "noodle",
          chn: "面条",
          id: "b-noodles",
          src: require("../../assets/noodle_img.png"),
          contents: {
            src: require("../../assets/noodle_img.jpeg"),
            describ: `Noodles are an essential ingredient and staple in Chinese cuisine. Chinese noodles vary widely according to the region of production, ingredients, shape or width, and manner of preparation. They are an important part of most regional cuisines within China, and other countries with sizable overseas Chinese populations.`,
          },
        },
      ],
    };
  },

  components: {
    MenuDisplay: () => import("./MenuDisplay"),
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
.section-3 {
  height: 950px;
  width: 90%;
  display: flex;
  justify-content: center;
  align-items: center;
  .board-wrap {
    position: relative;
    height: 400px;
    width: 1050px;
    margin: 10rem 0 10rem 0;
  }
}
</style>
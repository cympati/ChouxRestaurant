<template>
  <div>
    <v-hover v-slot="{ hover }">
      <div class="board" :id="menu.id">
        <span>{{ menu.eng }}<br />{{ menu.chn }}</span>
        <v-img
          class="hover-img"
          :src="menu.src"
          :width="menu.width"
          :height="menu.height"
        ></v-img>

        <v-expand-transition>
          <div
            v-if="hover"
            class="d-flex transition-fast-in-fast-out red v-card--reveal white--text"
            style="height: 11%"
          >
            <v-btn
              plain
              text
              color="white"
              class="font-weigth-more"
              @click="dialog = i"
              >More story <v-icon>mdi-plus</v-icon></v-btn
            >
          </div>
        </v-expand-transition>
      </div>
    </v-hover>
    <MenuDialog :menu="menu" :i="i" :dialog="dialog" @close="dialog = null" />
  </div>
</template>

<script>
export default {
  data() {
    return {
      dialog: null,
    };
  },
  components: {
    MenuDialog: () => import("../AllDialogs/MenuDialog"),
  },
  props: {
    menu: Object, // eng, chn
    i: Number,
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
#b-noodles {
  left: 800px;
}
#b-dumplings {
  left: 400px;
}
.board {
  position: absolute;
  height: 100%;
  width: 280px;
  background-color: white;
  border: 5px solid #9f4c38;
  border-radius: 50px;
  box-shadow: 3px 6px 5px rgba(33, 33, 35, 0.5);
  color: white;
  display: flex;
  justify-content: center;
  align-items: center;
  transition: 0.5s;
  overflow: hidden;
  &:hover {
    transform: scale(1.1, 1.1);
    .hover-img {
      opacity: 0.7;
    }
  }
  span {
    color: #9f4c38;
    font-size: 1.5rem;
    font-weight: 800;
    letter-spacing: 5px;
    position: absolute;
    top: 35px;
    left: 35px;
    z-index: 1;
  }
}

.v-card--reveal {
  align-items: center;
  bottom: 0;
  justify-content: center;
  position: absolute;
  width: 100%;
}
.font-weigth-more {
  font-weight: 600;
}
</style>
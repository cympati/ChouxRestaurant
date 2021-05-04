<template>
  <section class="section-reservation" id="section-reservation">
    <div class="words">
      <h1 id="w-1">
        祝<br />福<br />您<br />和<br />您<br />的<br />家<br />人<br />幸<br />福<br />如<br />意
      </h1>
      <h1 id="w-2">祝<br />您<br />身<br />体<br />健<br />康</h1>
    </div>

    <div class="book-table-wrap">
      <div class="above-table">
        <div class="calendar"></div>
        <div class="book-table">
          <ReservationHome
            :getIsAdmin="getIsAdmin"
            :getIsLogin="getIsLogin"
            :setInvalidSnb="setInvalidSnb"
          ></ReservationHome>
        </div>
      </div>

      <div class="buttom-table">
        <a @click="checkIsLogin">
          <span v-if="getIsAdmin" class="check-reserve">
            Check information of reservation&nbsp;
            <div id="line-append"></div>
            <ArrowSVG />
          </span>
          <span v-else class="check-reserve">
            Check your reservation&nbsp;
            <div id="line-append"></div>
            <ArrowSVG />
          </span>
        </a>
      </div>
    </div>
  </section>
</template>

<script>
import { mapActions, mapGetters } from "vuex";
export default {
  components: {
    ReservationHome: () => import("./ReservationHome"),
    ArrowSVG: () => import("../JubJibComponent/ArrowSVG"),
  },
  methods: {
    ...mapActions("account", ["checkIsLogin", "setInvalidSnb"]),
  },
  computed: {
    ...mapGetters("account", ["getIsAdmin", "getIsLogin"]),
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
  font-family: "Nunito Sans", sans-serif;
  list-style: none;
  quotes: none;
}

.section-reservation {
  height: 950px;
  width: 100%;
  display: flex;
  padding: 5rem 0 0 2rem;
  justify-content: space-around;
  align-items: center;
  border-radius: 10rem 0 0 0;
  background-color: #faf3ee;
  .words {
    font-size: 2rem;
    font-weight: 800;
    padding-left: 70px;
    margin-top: 7.6rem;
    display: flex;
    justify-content: space-between;
    width: 12%;
    height: 541px;
    #w-1 {
      line-height: 2.5rem !important;
      align-self: flex-end;
    }
    #w-2 {
      line-height: 2.5rem !important;
    }
  }
  .book-table-wrap {
    width: 60%;
    height: 100%;
    .above-table {
      width: 100%;
      height: 85%;
      position: relative;
      .book-table {
        position: absolute;
        width: 400px;
        height: 450px;
        background-color: #9f4c38;
        left: 350px;
        top: 50px;
      }
      .calendar {
        position: absolute;
        background-image: url("../../assets/img-reserve.jpeg");
        background-size: cover;
        background-position: 0 30%;
        width: 400px;
        height: 500px;
        top: 190px;
        left: 50px;
      }
    }
    .buttom-table {
      display: flex;
      justify-content: flex-end;
      .check-reserve {
        background: none;
        border: none;
        outline: none;
        cursor: pointer;
        font-weight: 800;
        text-transform: uppercase;
        font-size: 1rem;
        padding-right: 4.3rem;
        display: flex;
        align-items: center;
        &:hover {
          color: #6079d6;
          fill: #6079d6;
          #line-append {
            width: 35px;
          }
        }
        svg {
          transform: rotate(-90deg);
        }
      }
    }
  }
}
#line-append {
  width: 0px;
  height: 2px;
  margin: 0 0 0 8px;
  background-color: #6079d6;
  transition: 0.3s cubic-bezier(0.075, 0.82, 0.165, 1);
}
</style>
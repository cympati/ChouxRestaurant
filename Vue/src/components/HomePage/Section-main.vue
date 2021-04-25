<template>
  <section class="section-1" id="section-1">
    <div class="section-1-wrap">
      <div class="contents">
        <h1>Best <span>Chinese</span><br />restaurant</h1>
        <div class="p-wrap">
          <p>
            The whole cooking process of Chinese cuisine is the reflection of
            the harmony and balance of nature.
          </p>
        </div>
        <a class="reserve-bnt" href="#" v-scroll-to="'#section-reservation'">
          Reaservation &nbsp;
          <ArrowSVG />
        </a>
      </div>
      <ImageHomePage />
    </div>
  </section>
</template>

<script>
export default {
  components: {
    ArrowSVG: () => import("../JubJibComponent/ArrowSVG"),
    ImageHomePage: () => import("../HomePage/ImageHomePage"),
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
.section-1 {
  height: 800px;
  width: 90%;
  margin-top: 20px;
  padding-bottom: 100px;
  background-color: #faf3ee;
  border-radius: 80px 0 0 0;
  .section-1-wrap {
    display: flex;
    align-items: center;
    height: 100%;
    .contents {
      color: #212123;
      display: flex;
      flex-direction: column;
      margin-left: 70px;
      h1 {
        width: 600px;
        font-size: 6rem;
        font-weight: 800;
        span {
          color: #6079d6;
        }
      }
      .p-wrap {
        margin: 1.6rem 0;
        max-width: 600px;
        p {
          line-height: 1.6rem;
          font-size: 1.5rem;
          font-weight: 600;
        }
      }
      a {
        color: white;
        fill: white;
        background-color: #9f4c38;
        border: 1px solid #9f4c38;
        border-radius: 20px;
        width: fit-content;
        height: 2.5rem;
        padding: 8.5px 20px;
        text-transform: uppercase;
        outline: none;
        text-align: center;
        font-size: 1rem;
        font-weight: 900;
        cursor: pointer;
        &:hover {
          box-shadow: 2px 3px 5px rgba(33, 33, 35, 0.5);
        }
        svg {
          transform: translateY(2px);
        }
      }
    }
  }
}
</style>
<template>
  <section class="section-contacts" id="section-contacts">
    <div class="contents">
      <div class="box">
        <div class="line"></div>
        <div>
          <h1>Where?</h1>
          <span>Somewhere</span>
        </div>
      </div>

      <div class="box">
        <div class="line"></div>
        <div>
          <h1>When?</h1>
          <span>Everyday : 10 am - 8 pm</span>
        </div>
      </div>

      <div class="box">
        <div class="line"></div>
        <div>
          <h1>Contacts!</h1>
          <span>info@mysite.com</span><br />
          <span>099-999-9999</span>
        </div>
      </div>

      <div class="box">
        <div class="line"></div>
        <div>
          <h1>Social life</h1>
          <span>Discover our life : )</span><br />
          <a>
            <v-icon class="hover-icon" color="black">mdi-facebook</v-icon>
          </a>
          <a class="ml-3">
            <v-icon class="hover-icon" color="black">mdi-twitter</v-icon>
          </a>
          <a class="ml-3">
            <v-icon class="hover-icon" color="black">mdi-instagram</v-icon>
          </a>
        </div>
      </div>
    </div>
    <div id="stroke">
      <div class="box" id="map">MAP</div>
    </div>
  </section>
</template>

<script>
import gmapsInit from "../../utils/gMap";
export default {
  async mounted() {
    try {
      const google = await gmapsInit();
      const myLatLng = {
        lat: 13.651472337055107,
        lng: 100.49665462660161,
      };

      const map = new google.maps.Map(document.getElementById("map"), {
        zoom: 15,
        center: myLatLng,
      });

      new google.maps.Marker({
        position: myLatLng,
        map,
      });
    } catch (error) {
      console.error(error);
    }
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
.section-contacts {
  background-color: #faf3ee;
  height: 600px;
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 5rem 0 7rem;
  .contents {
    display: grid;
    grid-template-columns: auto auto;
    width: 55%;
    height: 50%;
    .box {
      display: flex;
      align-items: center;
      h1 {
        font-size: 1.5rem;
        font-weight: 900;
        margin-bottom: 0.6rem;
      }
      span {
        line-height: 1.7rem;
        letter-spacing: 1px;
      }
    }
  }
  .line {
    border-left: 4px solid #9f4c38;
    height: 6.5rem;
    width: 0.3rem;
    margin-right: 1.5rem;
  }
  #map {
    width: 100%;
    height: 100%;
    background-color: #9f4c38;
  }
}
.hover-icon:hover {
  color: #6079d6 !important;
}
#stroke {
  width: 60%;
  height: 60%;
  border: rgb(33, 33, 35) 5px solid;
  box-shadow: 10px 10px rgb(33, 33, 35);
  border-radius: 5px;
}
</style>
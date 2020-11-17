<template>
  <div>
    <v-row align="center">
      <v-col class="text-center" cols="12" sm="4">
        <div class="my-2">
          <v-btn text large color="primary" @click="onclick">위치정보받기</v-btn>
          <v-btn text large color="primary" @click="addScript">지도세팅하기</v-btn>
          <v-btn text large color="primary" @click="initMap">지도세팅하기2</v-btn>
        </div>
        <v-card-text> 경도:{{lat}}<br>위도:{{lon}} </v-card-text>
        <div id="map" style="width:500px;height:400px;"></div>
      </v-col>
    </v-row>
  </div>
</template>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=b85a9134094fd6bd9f92288d6f8d425e"></script>
<script>
import AppApi from '@/api/AppApi.js';

export default {
  data() {
    return {
      comment: "",
      url: "",
      lat: 37.512,
      lon: 127.031,
    }
  },
  methods: {
    onclick() {
      // this.getGeolocation()
      AppApi.getGeolocation(
        position => {
            // console.log("position.coords", position.coords)
            this.lat = position.coords.latitude
            this.lon = position.coords.longitude
          },
        err => {
          // console.log("err", err)
          if (err.code === 1) {
            // console.log("사용자가 위치 정보 제공에 거부하였습니다.")
          } else {            
            // console.log('기타 에러입니다')
            }
        }
      )
    },
    getGeolocation(){
      // console.log("navigator.geolocation: ",navigator.geolocation)
      if (!navigator.geolocation) {
        // console.log("Geolocation 사용 불가 합니다.")
      } else {
        // console.log("Geolocation 사용 가능 합니다.")
        navigator.geolocation.getCurrentPosition(
          position => {
            // console.log("position", position)
            // console.log("position.coords.latitude", position.coords.latitude)
            // console.log("position.coords.longitude", position.coords.longitude)
          },
          err => {
            // console.log("err", err)
        	  if (err.code === 1) {
              // console.log("사용자가 위치 정보 제공에 거부하였습니다.")
            } else {            
              // console.log('기타 에러입니다')
              }
          }
        )
      }
    },
    initMap() {
      var container = document.getElementById('map'); 
      var options = {
            center: new kakao.maps.LatLng(this.lat, this.lon), level: 3 
      }; 
      var map = new kakao.maps.Map(container, options);
    },
    addScript() { 
      const script = document.createElement('script');
      /* global kakao */ 
      script.onload = () => kakao.maps.load(this.initMap); 
      script.src = 'http://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=09e1abd5c5f6238940e4138d128c1b3f&libraries=services';   
      document.head.appendChild(script);
    },
  },
  mounted() {
    this.onclick()
    this.addScript()
  }
}
</script>

<style>

</style>
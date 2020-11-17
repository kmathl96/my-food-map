<template>
<div class="container">
    <!-- 기존 주소 설정 삭제 / 검색 창 추가 -->
      <v-text-field
        v-model="message"        
        @enter="search"
        solo-inverted
        flat
        style="margin-top: 5px;"
        hide-details
        label="맛집 혹은 유저 정보를 검색해보세요."
        type="text"
        >      
      </v-text-field>
      <!-- 서치 버튼 삭제 & 클릭에서 엔터로 이벤트 변환-->
      <!--
      <template v-slot:append-outer>
          <v-btn
           @click="search"
          >
          <v-icon left>mdi-search</v-icon>
             search
          </v-btn>
        </template>
        -->

      <v-spacer></v-spacer>
      <hr style="margin-top: 10px; margin-bottom: 10px;">
    <div>
        시도 : <select id="sido" v-model="sidovalue" @change="initgugun(sidovalue)">
	    <option v-for="item in sido" v-bind:key="item.sido_code" >{{item.sido_name}}</option>
        </select>
        구군 : <select id="gugun" v-model="gugunvalue"  @change="initdong(gugunvalue)">
	<option v-for="item in gugun" v-bind:key="item.gugun_code" >{{item.gugun_name}}</option>
    </select>
    읍면동 :
<select id="dong" v-model="dongvalue" @change="initlist(dongvalue)" @click="clearMap">
    <option v-for="item in dong" v-bind:key="item.dong">{{item.dong}}</option>
</select>
  
    </div>
      <br>
<div>
    <v-row align="center">
      <v-col class="text-center" >
          <v-btn text large color="primary" @click="onclick">내 위치</v-btn>
        <v-card-text> 경도:{{lat}}<br>위도:{{lon}} </v-card-text>        
        <!-- 모바일 페이지 기준 맵 사이즈 조정, 라운드 처리 -->
        <div id="map" class="rounded-lg" style="width:100%;height:360px;"></div>

      </v-col>
    </v-row>
  </div>



  <div id="dmap">
    <h1>{{msg}}</h1>
    <div id="map" ref="map" style="width:90%; margin-left : 5%; height:150px;"></div>
  </div>

 

<!-- 음식점 뿌려 -->
  <RestaurantCard
      v-for="restaurant in restaurants"
      :key="restaurant.idrestaurants"
      :restaurantInfo="restaurant"
    />

</div>
</template>


<script>
import RestaurantCard from '@/components/restaurant/RestaurantCard';
import RestaurantApi from '@/api/RestaurantApi.js'
import http from '../../util/http-common';
import AppApi from '@/api/AppApi.js';

export default {
    components: {
        RestaurantCard
    },
    props: ['isScrollEnd'],
    data() {
        return {
            msg: "",
            temp: '',
            sido: {},
            sidovalue: '',
            gugun: {},
            gugunvalue: '',
            dong: {},
            dongvalue: '',
            comment: "",
            url: "",
            lat: 37.512,
            lon: 127.031,
            message: "",
            restaurants: [],
            allRestaurants: {},
            loading: true,
            offset: 0
        }
    },

    mounted() {
        // console.log(`window: ${window}`)
        // console.log(`window.kakao: ${window.kakao}`)
        // console.log(`window.kakao.maps: ${window.kakao.maps}`)
        window.kakao && window.kakao.maps
            ? this.initMap()
            : this.addScript(); // 처음에실행
        this.initsido();
    },

    methods: {

        fetchRestaurants() {
            const start = this.offset * 5
            const end = start + 5
            // console.log("레스토랑 데이터 갱신 요청", this.allRestaurants.slice(start, end))
            const newArray = this
                .allRestaurants
                .slice(start, end)
            // console.log(`fetchRestaurants 대상은 ${start}~${end}, 5개 슬라이싱`, newArray)
            this.restaurants = [
                ...this.restaurants,
                ...newArray
            ]
            this.offset += 1
            this.loading = false
        },
        onclick() {
            // this.getGeolocation()
            AppApi.getGeolocation(position => {
                // console.log("position.coords", position.coords)
                this.lat = position.coords.latitude
                this.lon = position.coords.longitude
            }, err => {
                // console.log("err", err)
                if (err.code === 1) {
                    // console.log("사용자가 위치 정보 제공에 거부하였습니다.")
                } else {
                    // console.log('기타 에러입니다')
                }
            })
            http
                .post('/map/search', {
                    lat: this.lat,
                    lon: this.lon
                })
                .then(({data}) => {
                    let msg = '위치 불러오기에 실패하였습니다.';
                    if (data != null) {
                        this.allRestaurants = data;
                        // console.log(this.allRestaurants);
                        // console.log(`window: ${window}`)
                        // console.log(`window.kakao: ${window.kakao}`)
                        // console.log(`window.kakao.maps: ${window.kakao.maps}`)
                        // clearmap 실행
                        window.kakao && window.kakao.maps
                            ? this.clearMap()
                            : this.addScript();
                        this.clearMap();
                        this.loading = true
                        this.offset = 0
                        this.restaurants =[]
                        this.fetchRestaurants();
                    } else {
                        alert(msg);
                    }
                });
        },

        initMap() {
            const container = document.getElementById('map');
            const options = {
                center: new kakao
                    .maps
                    .LatLng(this.lat, this.lon),
                level: 3
            };
            const map = new kakao
                .maps
                .Map(container, options);
            //마커추가하려면 객체를 아래와 같이 하나 만든다
            const marker = new kakao
                .maps
                .Marker({position: map.getCenter()});
            marker.setMap(map);
        },
        view(no) {
            this
                .$router
                .push("../store/store/" + no);
        },
        clearMap() {

            const tempstr = this
                .allRestaurants[0]
                .name;
            // console.log(`tempstr: ${tempstr}`)
            const container = document.getElementById('map'),
                mapOption = {
                    center: new kakao
                        .maps
                        .LatLng(this.lat, this.lon), // 지도의 중심좌표
                        // .LatLng(33.450701, 126.570667), // 지도의 중심좌표
                    level: 5 // 지도의 확대 레벨
                };
            const map = new kakao
                .maps
                .Map(container, mapOption);

            const geocoder = new kakao
                .maps
                .services
                .Geocoder();

            //마커추가하려면 객체를 아래와 같이 하나 만든다
            for (let i = 0; i < 10; i++) {
                
                // 주소 정보에 해당하는 좌표값을 요청한다.
                geocoder.addressSearch(
                    this.allRestaurants[i].jibun,
                    (result, status) => {
                        // result : 결과 목록
                        // status : 응답 코드
                        const restaurant = this.allRestaurants[i]
                        // console.log(result)
                        if (status === kakao.maps.services.Status.OK) {
                            const coords = new kakao
                                .maps
                                .LatLng(result[0].y, result[0].x);

                            // 결과값으로 받은 위치를 마커로 표시합니다
                            const marker = new kakao
                                .maps
                                .Marker({map: map, position: coords});
                            marker.setMap(map);

                            // 인포윈도우 컨텐츠
                            const iwContent = `<div>${restaurant.name}</div>`

                            // 인포윈도우 생성 => 위치, 컨텐츠
                            const infowindow = new kakao
                                .maps
                                .InfoWindow({
                                    content: iwContent,
                                });
                                // map, position, content,
                            // position: new kakao.maps.LatLng(33.450701, 126.570667),

                            infowindow.open(map, marker); //지도에 인포윈도우를 올린다. 마커에서 열린 효과를 낸다.

                            // 인포윈도우가 올라가있는 지도를 반환한다.

                        }

                    }
                )
            }


        },
        addScript() {
            const script = document.createElement('script');
            /* global kakao */
            script.onload = () => kakao
                .maps
                .load(this.initMap);
            script.src = 'http://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=09e1abd5c5f6238940e' +
                    '4138d128c1b3f&libraries=services';
            document
                .head
                .appendChild(script);
        },
        initsido() {
            http
                .post('/map/sido', {})
                .then(({data}) => {
                    let msg = '시도 불러오기에 실패하였습니다.';
                    if (data != null) {
                        this.sido = data;
                    } else {
                        alert(msg);
                    }
                });
        },
        initgugun(sidovalue) {
            for (let i = 0; i < this.sido.length; i++) {
                if (sidovalue == this.sido[i].sido_name) {
                    this.temp = this
                        .sido[i]
                        .sido_code;
                }
            }
            http
                .post('/map/gugun', {

                    sido_name: sidovalue,
                    sido_code: this.temp
                })
                .then(({data}) => {
                    let msg = '구군 불러오기에 실패하였습니다.';
                    if (data != null) {
                        this.gugun = data;

                    } else {
                        alert(msg);
                    }
                });
        },

        initdong(gugunvalue) {
            for (let i = 0; i < this.gugun.length; i++) {
                if (gugunvalue == this.gugun[i].gugun_name) {
                    this.temp = this
                        .gugun[i]
                        .gugun_code;
                }
            }
            http
                .post('/map/dong', {
                    gugun_name: gugunvalue,
                    gugun_code: this.temp
                })
                .then(({data}) => {
                    let msg = '동 불러오기에 실패하였습니다.';
                    if (data != null) {
                        this.dong = data;

                    } else {
                        alert(msg);
                    }
                });
        },

        initlist(dongvalue) {

            http
                .post('/map/list', {dong: dongvalue})
                .then(({data}) => {
                    let msg = '동 불러오기에 실패하였습니다.';
                    if (data != null) {
                        this.allRestaurants = data;
                        // console.log(this.allRestaurants);
                        window.kakao && window.kakao.maps
                            ? this.clearMap()
                            : this.addScript();
                        this.clearMap();
                        this.fetchRestaurants();
                    } else {
                        alert(msg);
                    }
                });
        },
        search() {
            http
                .post('/map/list', {dong: this.message})
                .then(({data}) => {
                    let msg = '동 불러오기에 실패하였습니다.';
                    if (data != null) {
                        this.allRestaurants = data;
                        // console.log(this.allRestaurants);
                        window.kakao && window.kakao.maps
                            ? this.clearMap()
                            : this.addScript();
                        this.clearMap();
                        this.fetchRestaurants();
                    } else {
                        alert(msg);
                    }
                });
        }
    },
    watch: {
        isScrollEnd: function (val) {
            // console.log("스크롤엔드 감지 :", val, this.loading)
            if (val && !this.loading) {
                this.loading = true
                // console.log("데이터 로딩 중", this.loading)
                this.fetchRestaurants()
            } else {
                // console.log("지나간다")
            }
        }
    }
}

</script>

<style scoped="scoped"></style>
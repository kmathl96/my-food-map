<template>
<div class="container">
    <!-- 기존 주소 설정 삭제 / 검색 창 추가 -->
      <v-text-field
        v-model="message"        
        v-on:keyup.enter="search"
        solo-inverted
        flat
        style="margin-top: 5px;"
        hide-details
        label="키워드로 검색해보세요."
        type="text"
        >      
      </v-text-field>

      <v-spacer></v-spacer>
      <hr style="margin-top: 10px; margin-bottom: 10px;">

<div>
    <v-row align="center">
      <v-col class="text-center" >
      
        <!-- 모바일 페이지 기준 맵 사이즈 조정, 라운드 처리 -->
        <div id="map" class="rounded-lg" style="width:100%;height:360px;"></div>
        
        <br>
        <hr>
      </v-col>
    </v-row>
  </div>



  <div class="map_wrap">
    <div id="map" ref="map" style="width:100%; height:100%;position:relative;overflow:hidden"></div>
  
     <div id="menu_wrap" class="bg_white">
        <v-btn text large color="primary" @click="Myplace">내 위치
        </v-btn> 
        <hr>
        <ul id="placesList"></ul>
          <RestaurantMiniCard
      v-for="(restaurant ,index) in restaurants"
      :key="restaurant.idrestaurants"
      :restaurantInfo="restaurant"
      :index="index"
    />
    </div>
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
import RestaurantMiniCard from '@/components/restaurant/RestaurantMiniCard';
import RestaurantApi from '@/api/RestaurantApi.js'
import http from '../../util/http-common';
import AppApi from '@/api/AppApi.js';

export default {
    components: {
        RestaurantCard,
        RestaurantMiniCard
    },
    props: ['isScrollEnd'],
    data() {
        return {
            msg: "",
            temp: '',
            dong: {},
            dongvalue: '',
            comment: "",
            url: "",
            lat: 37.512,
            lon: 127.031,
            message: "",
            restaurants: [],
            allRestaurants: {},
            listData:[],
            listDatao:[],
            listimage:[],
            loading: true,
            offset: 0,
            index : 0
        }
    },

    mounted() {
        window.kakao && window.kakao.maps
            ? this.initMap()
            : this.addScript();
    },

    methods: {

        fetchRestaurants() {
            const start = this.offset * 5
            const end = start + 4
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
        
        Myplace() {
            // this.getGeolocation()
            AppApi.getGeolocation(position => {
               // console.log("position.coords", position.coords)
                this.lat = position.coords.latitude
                this.lon = position.coords.longitude
            }, err => {
               // console.log("err", err)
                if (err.code === 1) {
                    //console.log("사용자가 위치 정보 제공에 거부하였습니다.")
                } else {
                    //console.log('기타 에러입니다')
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
                        this.restaurants=[]
                        this.allRestaurants=[]
                        this.allRestaurants = data;
                         for(var i=0; i<this.allRestaurants.length; i++ ){
                            this.listData[i] = this.allRestaurants[i].jibun;
                            this.listDatao[i] = this.allRestaurants[i].name;
                            this.listimage[i] = this.allRestaurants[i].image;
                        }
                        //console.log(this.listData);
                        window.kakao && window.kakao.maps
                            ? this.clearMap()
                            : this.addScript();
                        this.clearMap();
                        this.fetchRestaurants();
                    } else {
                        alert(msg);
                    }
                });
                const target = document.querySelector('#scrolling-techniques-3');
                target.scrollTop = 0;
        },

        initMap() {
              AppApi.getGeolocation(position => {
               // console.log("position.coords", position.coords)
                this.lat = position.coords.latitude
                this.lon = position.coords.longitude
            }, err => {
                //console.log("err", err)
                if (err.code === 1) {
                    //console.log("사용자가 위치 정보 제공에 거부하였습니다.")
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
                         for(var i=0; i<this.allRestaurants.length; i++ ){
                            this.listData[i] = this.allRestaurants[i].jibun;
                            this.listDatao[i] = this.allRestaurants[i].name;
                            this.listimage[i] = this.allRestaurants[i].image;
                        }
                        //console.log(this.listData);
                        window.kakao && window.kakao.maps
                            ? this.clearMap()
                            : this.addScript();
                    } else {
                        alert(msg);
                    }
                });
        },
        view(no) {
            this
                .$router
                .push("../store/store/" + no);
        },

    clearMap() {

    var mapContainer = document.getElementById('map');
    var mapOption = {
        center: new daum
            .maps
            .LatLng(37.450701, 126.570667),
        level: 5
    };

    var map = new daum
        .maps
        .Map(mapContainer, mapOption);
    var listDataa = this.listData;
    var listDataaa = this.listDatao;
    var geocoder = new daum
        .maps
        .services
        .Geocoder();
    listDataa.forEach(function (addr, index) {
        geocoder.addressSearch(addr, function (result, status) {
            if (status === daum.maps.services.Status.OK) {
                var coords = new daum
                    .maps
                    .LatLng(result[0].y, result[0].x);

                var marker = new daum
                    .maps
                    .Marker({map: map, position: coords});
                var infowindow = new daum
                    .maps
                    .InfoWindow({
                        content : '<div class="wrap">' + 
                                '    <div class="info">' + 
                                '        <div class="title">' + 
                                    listDataaa[index] + 
                                '            <div class="close" @click="closeOverlay()" title="닫기"></div>' + 
                                '        </div>' + 
                                '        <div class="body">' + 
                                '            <div class="img">' +
                                '                <img src="https://firebasestorage.googleapis.com/v0/b/my-food-652b5.appspot.com/o/%ED%95%9C%EC%8B%9D_1.jpg?alt=media&token=e17e6179-006f-4dbe-8cff-7b954a2a8209" width="73" height="70">' +
                                '           </div>' + 
                                '            <div class="desc">' + 
                                '                <div class="ellipsis">' + listDataa[index] +'</div>' + 
                                '                <div class="jibun ellipsis">(우) 63309 (지번) 영평동 2181</div>' + 
                                '                <div><a href="https://map.kakao' +
                                '.com/link/to/' + listDataa[index] + ',' + result[0].y + ',' + result[0].x + '"' +
                                ' style="color:blue" target="_blank">길찾기</a></div>' + 
                                '            </div>' + 
                                '        </div>' + 
                                '    </div>' +    
                                '</div>'
                    });
              
                (function (marker, infowindow) {
                    // 마커에 mouseover 이벤트를 등록하고 마우스 오버 시 인포윈도우를 표시합니다
                    kakao
                        .maps
                        .event
                        .addListener(marker, 'mouseover', function () {
                            infowindow.open(map, marker);
                        });

                    // 마커에 mouseout 이벤트를 등록하고 마우스 아웃 시 인포윈도우를 닫습니다
                    kakao
                        .maps
                        .event
                        .addListener(marker, 'click', function () {
                            infowindow.close();
                        });
                })(marker, infowindow);
                if (index === 0) {
                    map.setCenter(coords);
                   // console.log(coords);
                }
            }
        });


    });


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
        initdong(gugunvalue) {
            for (var i = 0; i < this.gugun.length; i++) {
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
                        this.restaurants=[],
                        this.allRestaurants=[],
                        this.allRestaurants = data;
                       // console.log(this.message)
                    
                        for(var i=0; i<this.allRestaurants.length; i++ ){
                           this.listData[i] = this.allRestaurants[i].jibun;
                            this.listDatao[i] = this.allRestaurants[i].name;
                        }
                        //console.log(this.listData);
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

<style>

#menu_wrap {position:absolute;top:130px;left:0;bottom:550px;width:140px;margin:10px 0 30px 10px;padding:5px;overflow-y:auto;background:rgba(255, 255, 255, 0.7);z-index: 1;font-size:12px;border-radius: 10px;}
.bg_white {background:#fff;}
#menu_wrap hr {display: block; height: 1px;border: 0; border-top: 2px solid #5F5F5F;margin:3px 0;}
#menu_wrap .option{text-align: center;}
#menu_wrap .option p {margin:10px 0;}  
#menu_wrap .option button {margin-left:5px;}
#placesList li {list-style: none;}
#placesList .item {position:relative;border-bottom:1px solid #888;overflow: hidden;cursor: pointer;min-height: 65px;}
#placesList .item span {display: block;margin-top:4px;}
#placesList .item h5, #placesList .item .info {text-overflow: ellipsis;overflow: hidden;white-space: nowrap;}
#placesList .item .info{padding:10px 0 10px 55px;}
#placesList .info .gray {color:#8a8a8a;}
#placesList .info .jibun {padding-left:26px;background:url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/places_jibun.png) no-repeat;}
#placesList .info .tel {color:#009900;}
#placesList .item .markerbg {float:left;position:absolute;width:36px; height:37px;margin:10px 0 0 10px;background:url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png) no-repeat;}
#placesList .item .marker_1 {background-position: 0 -10px;}
#placesList .item .marker_2 {background-position: 0 -56px;}
#placesList .item .marker_3 {background-position: 0 -102px}
#placesList .item .marker_4 {background-position: 0 -148px;}
#placesList .item .marker_5 {background-position: 0 -194px;}
#placesList .item .marker_6 {background-position: 0 -240px;}
#placesList .item .marker_7 {background-position: 0 -286px;}
#placesList .item .marker_8 {background-position: 0 -332px;}
#placesList .item .marker_9 {background-position: 0 -378px;}
#placesList .item .marker_10 {background-position: 0 -423px;}
.wrap {position: absolute;left: 0;bottom: -50px;width: 288px;height: 132px;margin-left: -72px;text-align: left;overflow: hidden;font-size: 12px;font-family: 'Malgun Gothic', dotum, '돋움', sans-serif;line-height: 1.5;}
    .wrap * {padding: 0;margin: 0;}
    .wrap .info {width: 286px;height: 120px;border-radius: 5px;border-bottom: 2px solid #ccc;border-right: 1px solid #ccc;overflow: hidden;background: #fff;}
    .wrap .info:nth-child(1) {border: 0;box-shadow: 0px 1px 2px #888;}
    .info .title {padding: 5px 0 0 10px;height: 30px;background: #eee;border-bottom: 1px solid #ddd;font-size: 18px;font-weight: bold;}
    .info .close {position: absolute;top: 10px;right: 10px;color: #888;width: 17px;height: 17px;background: url('https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/overlay_close.png');}
    .info .close:hover {cursor: pointer;}
    .info .body {position: relative;overflow: hidden;}
    .info .desc {position: relative;margin: 13px 0 0 90px;height: 75px;}
    .desc .ellipsis {overflow: hidden;text-overflow: ellipsis;white-space: nowrap;}
    .desc .jibun {font-size: 11px;color: #888;margin-top: -2px;}
    .info .img {position: absolute;top: 6px;left: 5px;width: 73px;height: 71px;border: 1px solid #ddd;color: #888;overflow: hidden;}
    .info:after {content: '';position: absolute;margin-left: -12px;left: 50%;bottom: 0;width: 22px;height: 12px;background: url('https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white.png')}
    .info .link {color: #5085BB;}
</style>
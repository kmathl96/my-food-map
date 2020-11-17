<template>
  <div>
    <!-- 기존 주소 설정 삭제 / 검색 창 추가 -->
      <v-text-field
        v-model="message"        
        v-on:keyup.enter="search"
        solo-inverted
        flat
        hide-details
        style="margin-top: 5px;"
        label="원하는 키워드로 검색해보세요."
      ></v-text-field>
      <v-select
          :items="items"
          label="키워드 선택"
          v-model="select"
          dense
          solo
        ></v-select>
      <v-spacer></v-spacer>
   
    <!-- 기존 주소 설정 삭제 / 검색 창 추가 -->
    <RestaurantCard
      v-for="restaurant in restaurants"
      :key="restaurant.idrestaurants"
      :restaurantInfo="restaurant"
      :userInfo="userInfo"
    />
  </div>

</template>

<script>
import RestaurantCard from '@/components/restaurant/RestaurantCard';
import RestaurantApi from '@/api/RestaurantApi.js'
import http from '../../util/http-common';

export default {
  name: 'Restaurant',
  components: {
    RestaurantCard,
  },
  props: ['isScrollEnd'],
  data() {
    return {
      restaurants: null,
      allRestaurants: null,
      loading: true,
      offset: 0,
      complete: true,
      message: '',
      select: '',
      items: ['음식점명', '지역명','거리순'],
    }
  },
  methods: {
    // 아래는 임시 데이터 세팅을 위한 메소드
    setRestaurants() {
     // console.log("setRestaurants 요청")
      RestaurantApi.requestList(
        res => {
          this.allRestaurants = res.data.message
          this.restaurants = []
          this.complete = false
          this.fetchRestaurants()
        },
        err => {
          console.error(err)
          //console.log("에러반응")
        }
      )
    },
    fetchRestaurants() {
      const start = this.offset * 5 // 0,1,2,3,4 => 5개 , s = 0 , e = 5
      const end = this.allRestaurants.length <= start + 5 ? this.allRestaurants.length : start + 5
      this.complete = this.allRestaurants.length <= start + 5 ? true : this.complete
    //  console.log("리뷰 데이터 갱신 요청", this.allRestaurants.slice(start, end), this.complete)
      this.restaurants = [ ...this.restaurants, ...this.allRestaurants.slice(start, end) ]
      this.offset += 1
      this.loading = false
    },
    search() {
      if(this.select==='지역명'){
            http
                .post('/map/list', {dong: this.message})
                .then(({data}) => {
                    let msg = '동 불러오기에 실패하였습니다.';
                    if (data != null) {
                        this.restaurants=[],
                        this.allRestaurants=[],
                        this.allRestaurants = data;
                        
                        this.fetchRestaurants();
                    } else {
                      //  alert(msg);
                    }
                }
              );
      }
      else{
        console.log(this.message)
         http
                .post('/restaurants/search', {
                  doro: this.message,
                  })
                .then(({data}) => {
                    let msg = '동 불러오기에 실패하였습니다.';
                    if (data != null) {
                        this.restaurants=[],
                        this.allRestaurants=[],
                        this.allRestaurants = data.message;
                      //  console.log(data)
                        this.fetchRestaurants();
                    } else {
                    //    alert(msg);
                    }
                }
              );

      }
   },
  },
  watch: {
    isScrollEnd: function(val) {
      console.log("스크롤엔드 감지 :", val, !this.complete, this.loading)
      if (val && !this.complete && !this.loading) {
        this.loading = true
        this.fetchRestaurants()
      }
    }
  },
  mounted() {
    this.setRestaurants()
  }
}
</script>
<style>
</style>
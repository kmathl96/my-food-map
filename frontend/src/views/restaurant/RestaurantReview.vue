<template>
  <div>
    <RestaurantCard 
      class="mt-1 mb-1"
      :restaurantInfo="restaurant"
    />
    <!--
    상단에 음식점 정보를 넣어주고, 하단에는 리뷰
    앞 페이지와 중복되며 앞의 음식점 페이지 삭제
    -->
    <v-btn 
    block
    dark
    depressed
    tile
    class="justify-end white--text font-weight-bold"
    color="grey lighten-1">
    방문자 리뷰
    </v-btn>
    <ReviewCard
      v-for="review in reviews"
      class="mt-1"
      :key="review.id"
      :reviewInfo="review"
    />
    <div
      v-if="!allReviews"
      class="text-center caption"
    >
      아직 작성된 리뷰가 없습니다. :( <br>
      직접 리뷰를 작성해 멋진 의견을 나눠주세요!
    </div>
  </div>
</template>

<script>
import ReviewCard from '@/components/review/ReviewCard';
import RestaurantApi from '@/api/RestaurantApi.js'
// 레스토랑 정보 받기 위한 추가
import RestaurantCard from '@/components/restaurant/RestaurantCard'; 

export default {
  name: "RestaurantReview",
  components: {
    ReviewCard,
    //레스토랑 카드 추가
    RestaurantCard,
  },
  props: ['isScrollEnd'],
  data(){
    return {
      reviews: null,
      allReviews: null,
      loading: true,
      offset: 0,
      complete: true,
      //레스토랑 프레임
      restaurant: {},
    }
  },
  methods:{
    //레스토랑 정보 설정
    setRestaurant() {
      const restaruantId = this.$route.params.restaurantId
      //console.log("realSetData 요청 Id 값 : ", restaruantId, typeof(restaruantId))
      RestaurantApi.requestInfo(
        restaruantId,
        res => {
          //console.log("realSetData 콜백 성공, res:", res.data.message)
          this.restaurant = res.data.message
          // 리뷰도 받아오기
          this.setReviews()
        },
        err => {
         // console.error(err)
         // console.log("에러반응")
        }
      )
    },
    setReviews() {
       const data = {
          userid: this.$store.state.user.userInfo ? this.$store.state.user.userInfo.userId : 0,
          restaurantId : this.$route.params.restaurantId,
        }
      RestaurantApi.requestReviews(
        data,
        res => {
          this.allReviews = res.data.message // 전체 데이터
          this.reviews = []
         // console.log("리뷰 리스트 데이터 바인딩 성공", this.allReviews)
          this.complete = false
          this.fetchReviews()
        },
        err => {
          console.error(err)
         // console.log("에러반응")
        }
      )
    },
    fetchReviews() {
      const start = this.offset * 10
      const end = this.allReviews.length <= start + 10 ? this.allReviews.length : start + 10
      this.complete = this.allReviews.length <= start + 10 ? true : this.complete
     // console.log("리뷰 데이터 갱신 요청", this.allReviews.slice(start, end), this.complete)
      this.reviews = [ ...this.reviews, ...this.allReviews.slice(start, end) ]
      this.offset += 1
      this.loading = false
    },
  },
  watch: {
    isScrollEnd: function(val) {
     // console.log("스크롤엔드 감지 :", val, !this.complete, this.loading)
      if (val && !this.complete && !this.loading) {
        this.loading = true
        this.fetchReviews()
      }
    }
  },
  mounted() {
    //레스토랑 정보 받아오기 => 성공 => 리뷰도 받아오기 (내부에서 실행)
    this.setRestaurant() 
  },
}
</script>
<style>

</style>
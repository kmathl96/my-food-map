<template>
  <div>
    <h1>Feed</h1>
    <ReviewCard
      v-for="review in reviews"
      class="mt-1"
      :key="review.id"
      :reviewInfo="review"/>
    <div
      v-if="!allReviews"
      class="text-center caption">
      보여줄 Feed가 아직 없습니다. :( <br>
      다른 유저를 Follow 해보세요!
    </div>
  </div>
</template>

<script>
import ReviewCard from '@/components/review/ReviewCard'
import ReviewApi from '@/api/ReviewApi.js'

export default {
  props: ["userInfo", "isScrollEnd"],
  components: {
    ReviewCard
  },
  data() {
    return {
      reviews: null,
      allReviews: null,
      loading: true,
      offset: 0,
      complete: true,
    }
  },
methods:{
    setReviews() {
      ReviewApi.requestFeedReview(
        this.userInfo.userId,
        res => {
          if (res.data.message.length) {
            this.allReviews = res.data.message // 전체 데이터
            this.reviews = []
            // console.log("FeedReview 리스트 데이터 바인딩 성공", this.allReviews)
            this.complete = false
            this.fetchReviews()
          }
        },
        err => {
          // console.error(err)
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
      // console.log("확인", this.reviews)
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
    this.setReviews() 
  },
}
</script>


<template>
  <v-card
    :loading="loading"
    class="mx-auto my-3"
    max-width="374"
    elevation=24
  > 
    <!-- 1 이미지 영역-->
    <v-img
      class="white--text align-end"
      @click="toRestaurantReview"
      v-if="reviewInfo.image !== 'null'"
      max-height="374"
      :src="reviewInfo.image">
      <v-card-title mouseover v-if="reviewInfo.image !== 'null'"> {{ reviewInfo.resname }} </v-card-title>
    </v-img>
    <!-- 1-1 레스토랑 이름 영역(이미지 없을때) -->
    <v-card-title @click="toRestaurantReview" v-if="reviewInfo.image === 'null'">{{reviewInfo.resname}}</v-card-title>

    <v-card-text class="pl-3">
      
      <!-- 2 좋아요, 코멘트숫자 영역 : float-right 통해 우측에 정렬되어 있음 -->

      <div class="float-right">
        <v-btn @click="onLike" icon class="mr-3">
            <v-icon :color="reviewInfo.like ? 'red' : ''">mdi-heart</v-icon> {{ reviewInfo.like_cnt }}
        </v-btn>
        <v-btn @click="toReviewDetail" icon class="float-right">
          <v-icon color="blue darken-2">mdi-message-text</v-icon>
          {{ reviewInfo.comment_cnt }}
        </v-btn>
      </div>
      
      <!-- 3 닉네임 영역 (레스토랑 이름은 위로 옮기며 삭제) -->
      <v-row v-if="showNickname" class="d-flex">
        <v-avatar @click="toProfile" size="35" class="mr-2">
            <v-img :src="reviewInfo.user_image"></v-img>
        </v-avatar>
        <p class="title">{{ reviewInfo.nickname }}</p>
      </v-row>
      <!-- 4 별점과 작성일 영역 -->
      <v-row align="end">
        <v-rating class="ml-2 mb-1 mt-1 mr-1" :value="reviewInfo.reviewrank"
          color="amber" dense half-increments readonly size="20" empty-icon="mdi-star-outline"
        />
        {{ reviewInfo.create_date }}
      </v-row>

      <!-- 5 리뷰 컨텐츠 영역 -->
      <div class="ml-1 mt-1">
        {{ reviewInfo.content }} <br>
      </div>
    </v-card-text>
  </v-card>
</template>

<script>
import ReviewApi from '@/api/ReviewApi.js'
import UserApi from '@/api/UserApi.js'
  export default {
    name: "ReviewCard",
    props: ['reviewInfo'],
    data: () => ({
      loading: false,
      showNickname: false,
    }),
    methods: {
			toReviewDetail(){
        if(this.$route.name !== 'ReviewDetail') {
          this.$router.push({ name: 'ReviewDetail', params: { reviewId: this.reviewInfo.no}});
        }
      },
      toProfile() {
        // 프로필페이지에선 보이지않아 분기 불필요
        this.$router.push({name : 'Profile', params : {nickname : this.reviewInfo.nickname}})
      },
      toRestaurantReview() {  
        if(this.$route.name !== 'RestaurantReview') {
          this.$router.push({ name: 'RestaurantReview', params: { restaurantId: this.reviewInfo.resid}});
        }      
      },
      // 미리 작성해 놓은 Like
      onLike(){
        const data = {
          userid: this.$store.state.user.userInfo.userId,
          reviewid: this.reviewInfo.no,
        }
        ReviewApi.requestLike(
          data,
          res => {
            if (res.data.message === "Like -1") {
              this.reviewInfo.like_cnt -= 1
              this.reviewInfo.like = false;
              // console.log("좋아요-1")
            } else {
              if (res.data.message === "Like +1") {
                this.reviewInfo.like_cnt += 1
                this.reviewInfo.like = true;
                // console.log("좋아요+1")
              } else {
                // console.log("좋아요 요청 실패, res : ", res)
              }
            }
          },
          err => {
            // console.error(err)
          }
        )
      },

    },
    mounted() {
      this.showNickname = (this.$route.name === 'Profile' ||  this.$route.name === 'MyProfile') ? false : true
    }
  }
</script>
<style>

</style>
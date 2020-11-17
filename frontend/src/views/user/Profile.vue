<template>
  <div>
    <UserProfileCard
      :profileUser="profileUser"
      @onFollow="onFollow"
      @toProfile="toProfile"
      :userInfo="userInfo"
      @completeFetch="completeFetch"

    />
    <ReviewCard
      v-for="review in reviews"
      :key="review.id"
      :reviewInfo="review"
    />
      
    <div
      class="text-caption text-center mt-8"
      v-if="!allReviews"
    >
      아직 작성한 리뷰가 없습니다. <br>
      당신의 이야기를 나눠주세요 <br>
      <v-btn :to="{ name: 'Restaurant' }" class="align-center" color="amber" icon>
        <v-icon size="26px">mdi-lead-pencil</v-icon>
      </v-btn>
    </div>
  </div>
</template>

<script>
import UserApi from '@/api/UserApi.js';
import ReviewCard from '@/components/review/ReviewCard';
import UserProfileCard from '@/components/user/UserProfileCard';
import ReviewApi from '@/api/ReviewApi.js';

export default {
  name: 'Profile',
  components: {
    ReviewCard,
    UserProfileCard
  },
  props: ['userInfo', 'isScrollEnd'],
  data() {
    return {
      profileUser: {
        id: "",
        email: "",
        nickname: "",
        follower: 0,
        following: 0,
        followed: false,
        image:"",
      },
      profileUserNickname: "",
      reviews: null,
      allReviews: null,
      loading: true,
      offset: 0,
      complete: true,
      // routeFetch: false,
    }
  },
  methods: {
    fetchProfile() {
      // 초기화 코드, params동일 라우팅 발생시 필요
      this.reviews= null
      this.allReviews = null
      this.loading = true
      this.offset = 0
      this.complete = true

      // console.log("fetchProfile 동작, route와 store 값", this.$route, this.$store)
      const userId = this.$store.state.user.userInfo ? this.$store.state.user.userInfo.userId : 0
      const data = {
        userId : userId,
        nickname : this.$route.params.nickname,
      }
      UserApi.requestUserInfoByNickname(
        data,
        res => {
          // console.log("유저 정보 요청 응답 res는")
          // console.log(res)
          if (res.status === 200) {
            // UserPage 접근 성공
            this.profileUser.id = res.data.userid
            this.profileUser.email = res.data.email
            this.profileUser.nickname = res.data.nickname
            this.profileUser.follower = res.data.follower_cnt
            this.profileUser.following = res.data.following_cnt
            this.profileUser.followed = res.data.followed
            this.profileUser.image = res.data.image
            
            this.profileUserNickname = res.data.nickname // for userProfileCard refresh
            
            this.setReviews()
            // 리뷰 fetch까지 진행
          } else {
            // UserPage 접근 실패 === 올바른 주소 접근이 아님
            // replace : history 남기지 않음 => 404페이지에서 뒤로가기 버튼 올바르게 동작
            this.$router.replace({ name: 'NotFound'})
          }
        },
        err => {
          // console.log("유저 정보 요청 응답 err는")
          // console.error(err)
          this.$router.replace({ name: 'ErrorPage'}) // 히스토리 남기지 않음
        }
      )
    },
    setReviews() {
      const profileUserId = this.profileUser.id
      // console.log("요청 id", profileUserId)
      ReviewApi.requestUserReview(
        profileUserId,
        res => {
          // console.log("리뷰 요청 응답 res => ", res)
          if (res.data.state === 'ok') {
            // console.log("리뷰 받아오기 성공",  res.data.message) 
            if(res.data.message.length !== 0) {
              this.allReviews = res.data.message
              this.reviews = []
              this.complete = false
              this.fetchReviews()
            } else {
              // console.log("빈 배열") 
            }
          } else {
            // console.log("리뷰 받아오기 실패")
          }
        },
        err => {
          // console.error(err)
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
    onFollow() {
      if(!this.userInfo) {
        this.$router.push({name:'Login', query:{ redirect: 'Profile', params: {nickname: this.profileUser.nickname}}})
      }
      // console.log('팔로우', this.userInfo.userId, this.profileUser.id)
      UserApi.requestFollow(
        {
          followerId: this.userInfo.userId,
          followingId: this.profileUser.id,
          no: 0
        },
        res => {
          // console.log("res 정보")
          // console.log(res)  
          if (res.data.message === "Following -1") {
            this.profileUser.follower -= 1
            this.profileUser.followed = false
            // console.log("팔로워 숫자 -1")
          } else {
            if (res.data.message === "Following +1") {
              this.profileUser.follower += 1
              this.profileUser.followed = true
              // console.log("팔로워 숫자 +1")
            } else {
              // 성공외 다른 응답이 왔을때 동작
              // console.log("팔로우 실패", res)
            }
          }
        },
        err => {
          // console.error(err)
          // 라우팅 하지 않음
        }
      )
    },
    toProfile(targetNickname){
        this.$router.push({name: 'Profile', params: {nickname: targetNickname}})
        // this.fetchFollowList()
    },
    // completeFetch() {
    //   this.routeFetch = false
    // }
  },
  watch: {
    isScrollEnd: function(val) {
      // console.log("스크롤엔드 감지 :", val, !this.complete, this.loading)
      if (val && !this.complete && !this.loading) {
        this.loading = true
        this.fetchReviews()
      }
    },
    $route: function(to, from) {
      // console.log("params만 다른 라우팅 발생", to, from)
      if ( to.params.nickname !== from.params.nickname) {
        this.fetchProfile()
        // this.routeFetch = true
      }
    }
  },
  created() {
    this.fetchProfile()
  }
}
</script>

<style>

</style>
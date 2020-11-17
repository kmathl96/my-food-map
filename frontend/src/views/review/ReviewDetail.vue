<template>
  <div>
    <ReviewCard
      class=""
      :reviewInfo="this.review"
    />
    <!-- 댓글 작성 -->

    <!-- 댓글 목록 -->
    <v-card
      max-width="374"
      class="mx-auto"
    >
      <v-list three-line>
        <v-subheader>댓글</v-subheader>
          <v-list-item v-for="item in comment_list" :key="item.no">
            <!-- 프로필 사진 -->
            <v-list-item-avatar>
              <v-img :src="item.user_image"></v-img>
            </v-list-item-avatar>
            <v-list-item-content>
              <v-list-item-title v-html="item.nickname"></v-list-item-title>
              <v-list-item-subtitle v-html="item.content"></v-list-item-subtitle>
              <v-list-item-subtitle v-html="item.create_date"></v-list-item-subtitle>
            </v-list-item-content>
          </v-list-item>
      </v-list>
    </v-card>
    <v-card max-width="374" class="pr-0 pl-0 mb-2 mx-auto" elevation="24">
      <v-row>
        <v-col
          cols="12"
        >
          <v-card-text class="pt-4 pb-0">
            <v-form>
              <v-textarea 
                label="댓글을 작성해주세요"
                :outlined="true"
                v-model="comment.content"
              />
            </v-form>
          </v-card-text>

          <v-card-actions class="ml-5 mr-5 mt-3">
            <v-btn @click="onCreate()">Create</v-btn>
          </v-card-actions>
        </v-col>
      </v-row>
    </v-card>
  </div>
</template>

<script>
import ReviewCard from '@/components/review/ReviewCard'
import ReviewApi from '@/api/ReviewApi.js'
import CommentApi from '@/api/CommentApi.js'

export default {
  name: "ReviewDetail",
  components: {
    ReviewCard,
  },
  data() {
    return {
      review: '',
      comment_list: [],
      comment: {
        userid: '',
        reviewid: this.$route.params.reviewId,
        content: '',
      },
    }
  },
  props: ['userInfo'],
  methods: {
    fetchReview() {
      const reviewId = this.$route.params.reviewId
      // console.log(reviewId)
      ReviewApi.requestReviewInfo(
        reviewId,
        res => {
          // console.log("fetchReview 콜백 성공, res:", res.data.message)
          this.review = res.data.message
        },
        err => {
          // console.error(err)
          // console.log("에러반응")
        }
      )
    },
    fetchCommentList() {
      const reviewId = this.$route.params.reviewId
      CommentApi.requestList(
        reviewId,
        res => {
          // console.log("fetchComentList 콜백 성공, res:", res.data.message)
          this.comment_list = res.data.message
        },
        err => {
          // console.error(err)
          // console.log("에러반응")
        }
      )
    },
    onCreate() {
      // console.log("onCreate 메소드 실행", this.comment)
      const data = {
        userid: Number(this.userInfo.userId),
        reviewid: this.$route.params.reviewId,
        content: this.comment.content,
      }
      // console.log(typeof(data.userId))
      CommentApi.requestCreate(
        data,
        res => {
          // console.log("resquestCreate 성공, res : ", res)
          if (res.data.state === 'ok') {
            this.comment.content = ''
            alert("댓글이 작성 되었습니다.")

            this.fetchCommentList()
            // this.$emit('scrollToBottom')
            
          } else {
            // console.log("댓글 작성 실패, res.data: ", res.data)
            alert(res.data.message || "댓글 작성에 실패했습니다.")
          }
        },
        err => {
          // console.log(err)
          // 에러작성 페이지로
          this.$router.push( { name: 'ErrorPage' })
        }
      )
    },
  },
  mounted() {
    this.fetchReview()
    this.fetchCommentList()
  },
}
</script>

<style>

</style>
<template>
  <div>
    <h1>Review List</h1>
    <v-card
        class="mx-auto my-1"
        max-width="344"
        v-for="review in review_list" :key="review.no"
    >
        <v-card-text>
            <div>작성자 : {{ review.userid }}</div>
            <p class="display-1 text--primary">
                be•nev•o•lent
            </p>
            <p>평점 : {{ review.rank }}</p>
            <div class="text--primary">
                {{ review.content }}
            </div>
        </v-card-text>
        <v-card-actions>
            <v-btn icon @click="likeReview(review)">
                <v-icon color="#FF1744">mdi-heart</v-icon>
            </v-btn>
            <p>{{ review.like_cnt }}</p>
            <v-btn icon>
                <v-icon>mdi-bookmark</v-icon>
            </v-btn>
            <v-btn icon>
                <v-icon>mdi-share-variant</v-icon>
            </v-btn>
        </v-card-actions>
			class="mx-auto my-1"
			max-width="344"
			v-for="review in reviews" :key="review.no"
		>
			<v-card-text>
				<div>Word of the Day</div>
				<p class="display-1 text--primary">
						be•nev•o•lent
				</p>
				<p>{{ review.rank }}</p>
				<div class="text--primary">
						{{ review.content }}
				</div>
			</v-card-text>
			<v-card-actions>
				<v-btn icon @click="onLike(review)">
						<v-icon color="#FF1744">mdi-heart</v-icon>
				</v-btn>
				<v-btn icon>
						<v-icon>mdi-bookmark</v-icon>
				</v-btn>
				<v-btn icon>
						<v-icon>mdi-share-variant</v-icon>
				</v-btn>
			</v-card-actions>
    </v-card>
  </div>
</template>

<script>
import { mapState, mapActions, mapMutations } from 'vuex'
import ReviewApi from '@/api/ReviewApi'

export default {
	name: 'ReviewList',
	props: ['userInfo'],
	computed: {
		...mapState({
			reviews: state => state.review.reviewList
		})
	},
	methods: {
		...mapActions(
			'review',
			['getReviewList']
        ),
		onLike(review) {
			// store 반영시, review.js에 작성해도 됨
			const data = {
				reviewid: review.no,
				userid: this.userInfo.userId
			}
			ReviewApi.requestLike(
				data,
				res => {
					// console.log("likeReview 응답리턴 res: ", res)
					if(res.status === 'ok') {
						// console.log("like 성공")
						//로컬에 반영 or store 반영
					} else {
						// console.log("like 실패")
					}
				},
				err => {
					// console.error(err)
					// 따로 에러에 대한 라우팅 진행하지 않는것이 UX적으로 올바라보임
				}
			)
		},
	},
	created() {
		this.getReviewList()
	},
}
</script>

<style>

</style>
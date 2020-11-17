import ReviewApi from '@/api/ReviewApi'
// import router from '../main'

export default {
  name: "review",
  namespaced: true,

  state: {
    reviewList: [],
  },
  mutations: {
    setReviewList (state, data) {
      state.reviewList = data
      // console.log("setReviewList 작동")
    }
  },
  actions: {
    getReviewList ({ commit }) {
      ReviewApi.requestReviewList(
        res => {
          if(res.data.state === 'ok') {
            // 리뷰리스트 받아오기 성공
            commit('setReviewList', res.data)
            // console.log("리뷰리스트 받아오기 성공", res.data)
          } else {
            // console.log("리뷰리스트 받아오기 실패", res)
          }
        },
        err => {
          // console.error(err)
          // 에러에 대한 라우팅 안함
        }
      )
    }
  }
}
  
<template>
  <div>
    <v-card
      :loading="loading"
      class="mx-auto my-2"
      max-width="374"
      elevation=24>

      <v-img
        class="white--text align-end"
        @click="onClick" 
        v-if="restaurantInfo.image && !showPicture"
        max-height="374"
        :src= "require('@/assets/' + restaurantInfo.image)">
      </v-img>

      <div class="d-flex justify-space-between">
        <v-card-title class="cardTitle ml-3" @click="onClick">
          {{ restaurantInfo.name }}
        </v-card-title>
        <div class="title mt-4 mr-1">
          <div class="d-flex">
            <v-rating
              :value="restaurantInfo.grade"
              color="amber"
              dense
              half-increments
              readonly
              size="20"
              empty-icon="mdi-star-outline"/>
              <p class="grey--text">({{ restaurantInfo.countgrade }})</p>
          </div>
        </div>
      </div>
    
      <v-card-text class="d-flex justify-space-between pb-1 pt-0">
          <div
            class="align-self-top mr-2 mb-0">
            <p class="my-0" :class="textColor">{{ percent }}%일치</p>
            <p class="my-0">AI 선호도입니다.</p>
          </div>
          <div class="subtitle-2 text-right mb-1">
            <div>
              <p v-if="restaurantInfo.meter">
                  거리 : {{restaurantInfo.meter}} M
              </p>
              <template v-for="type in restaurantInfo.res_type">
                  {{ type }}
              </template>
            </div>
            <div>
              {{ doroString }}
            </div> 
          </div>
      </v-card-text>
      <hr class="mx-2">

        <!-- 영업시간, 메뉴 위치 수정 -->
      <div class="d-flex justify-space-between">
        <v-card-text class="text-left pb-2 pt-0 pl-4 pr-4">
          {{ restaurantInfo.time }} <br>
          {{ restaurantInfo.menu }}
        </v-card-text>
        <v-btn @click="toReview" class="align-self-center mr-3" rounded dark small color="amber">
          <v-icon dark>mdi-pencil</v-icon>
        </v-btn>
      </div>
    </v-card>
  </div>
</template>

<script>
import RestaurantApi from '@/api/RestaurantApi.js'
  export default {
    name: "RestaurantCard",
    // props: ['id', 'title', 'content', 'imgUrl', 'types', 'rank', 'likeCount', 'deal_date'],
    props: ['restaurantInfo','userInfo'],
    data: () => ({
      loading: false,
      percent: Math.floor(Math.random() * 100 + 1),
      showPicture: true,
    }),
    computed: {
      doroString() {
        return this.restaurantInfo.doro ? this.restaurantInfo.doro.slice(0, 18) : null
      },
      textColor() {
        return this.percent > 80 ? 'green--text' : this.percent ? 'amber--text' : 'red--text'
      }
    },
    methods: {
			onClick(){
        // restaurant.vue에서만 이동
        if (this.$route.name === 'Restaurant' || this.$route.name === 'Map') {
         // console.log(`restaurantInfo :${this.restaurantInfo}`)
          this.$router.push({ name: 'RestaurantReview', params: { restaurantId: this.restaurantInfo.idrestaurants}});
        }
      },
      toReview() {
          this.$router.push({name : 'ReviewCreate', params : { restaurantId : this.restaurantInfo.idrestaurants, restaurantName : this.restaurantInfo.name, resimage : this.restaurantInfo.image}});
        } 
      },
      mounted() {
        this.showPicture = this.$route.name === 'RestaurantReview' ? true : false
      }
      // 미리 작성해 놓은 Like
      // onLike(){
      //   const data = {
      //     userId: this.$store.state.user.userId,
      //     restaurantId: this.restaurantId,
      //   }
      //   RestaurantApi.requestLike(
      //     data,
      //     res => {
      //       if (res === "좋아요") {
      //         this.restaurantInfo.likeCount += 1
      //         console.log("좋아요+1")
      //       } else {
      //         if (res === "좋아요취소") {
      //           this.restaurantInfo.likeCount -= 1
      //           console.log("좋아요-1")
      //         } else {
      //           console.log("좋아요 요청 실패, res : ", res)
      //         }
      //       }
      //     },
      //     err => {
      //       console.error(err)
      //     }
      //   )
      // }
  }
</script>
<style >
.underbar_gradient {
    background: linear-gradient(270deg, #fa709a, #f6d365 100%);
}
.cardTitle {
    font-family: 'NEXON Lv2 Gothic Bold';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-04@2.1/NEXON Lv2 Gothic Bold.woff') format('woff');
    font-weight: normal;
    font-style: normal;
    padding: 0;
}

</style>
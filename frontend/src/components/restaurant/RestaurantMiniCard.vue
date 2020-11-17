<template>
  <div>
    <v-card
      :loading="loading"
      class="mx-auto my-0"
      max-width="375"
    >
    <v-btn  
      text
      small
      @click="onClick(no)">
      {{ restaurantInfo.name }} >
    </v-btn>
    
    <v-card-text class="pb-1 pt-0">
      <v-row
        align="center"
        class="mx-0"
      >
          <v-rating
            :value="restaurantInfo.grade"
            color="amber"
            dense
            half-increments
            readonly
            size="20"
            empty-icon
          />

        </v-row>
        
        <!-- 레스토랑 정보 : {{ restaurantInfo }} -->
        <!-- 업태명 정보 배치 수정-->
        <!-- 주소 추가 및 디자인 수정 -->
        <div class="subtitle-2 text-right mb-1">
                <img @click="onClick" 
      position="relative"
      height="74" 
      width="104"
      :src= "require('@/assets/' + restaurantInfo.image)">
<br>
      <v-btn @click="toRes" class="mb-1 mr-4" rounded dark small max-height="16px" color="black">
          상세보기
      </v-btn>
      <v-btn class="mb-1 mr-4" rounded dark small max-height="16px" color="blue">
        <a v-bind:href="this.destiny" target="/blank">길찾기</a>
      </v-btn>
          <!-- {{ restaurantInfo.doro.slice(0, 18) }}  -->
          <!-- {{ restaurantInfo.doro ? restaurantInfo.doro.slice(0, 18) : null }}  -->
        </div>
        <hr>
        <!-- <div>{{ restaurantInfo.content }}</div> -->
      </v-card-text>
      <!-- 영업시간, 메뉴 위치 수정 -->
      <v-card-text class="text-right pb-2 pt-0 pl-4 pr-4">
        {{ restaurantInfo.time }} <br>
      </v-card-text>
    <!-- ReviewCreate -->
    <v-row justify='end'>
      <v-btn @click="toReview" class="mb-1 mr-4" rounded dark small max-height="16px" color="amber">
        <v-icon dark size="15">mdi-pencil</v-icon>
      </v-btn>
    </v-row>
    </v-card>
  </div>
</template>

<script>
import RestaurantApi from '@/api/RestaurantApi.js'
  export default {
    name: "RestaurantMiniCard",
    // props: ['id', 'title', 'content', 'imgUrl', 'types', 'rank', 'likeCount', 'deal_date'],
    props: ['restaurantInfo','index'],
    data: () => ({
      loading: false,
      percent: Math.floor(Math.random() * 100 + 1),
      showPicture: true,
      destiny: '',
    }),
    computed: {
      doroString() {
        return this.restaurantInfo.doro ? this.restaurantInfo.doro.slice(0, 18) : null
      }
    },
    methods: {

			onClick(no){
        const target = document.querySelector('#scrolling-techniques-3');
        target.scrollTop = this.index*530+400;
        //console.log(target.scrollTop)
        if (this.$route.name === 'Restaurant') {
          //console.log(`restaurantInfo :${this.restaurantInfo}`)
          this.$router.push({ name: 'RestaurantReview', params: { restaurantId: this.restaurantInfo.idrestaurants}});
        }
      },
      toRes(){
         this.$router.push({ name: 'RestaurantReview', params: { restaurantId: this.restaurantInfo.idrestaurants}});
      },
      toReview() {
          this.$router.push({name : 'ReviewCreate', params : { restaurantId : this.restaurantInfo.idrestaurants, restaurantName : this.restaurantInfo.name}});
        } 
      },
      mounted() {
        this.showPicture = this.$route.name === 'RestaurantReview' ? true : false
        this.destiny = 'https://map.kakao.com/?sName=%27+%EB%A9%80%ED%8B%B0%EC%BA%A0%ED%8D%BC%EC%8A%A4%EC%97%AD%EC%82%BC+%27&eName='+this.restaurantInfo.name;
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
</style>
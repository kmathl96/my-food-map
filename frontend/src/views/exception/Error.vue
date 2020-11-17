<template>
  <div class="mt-10">
    <h2 class="ml-3 mt-6"> 에러페이지 </h2>
    <div class="ml-3 mt-3 mb-3"> 잘못된 접근입니다. <br> 메인화면 혹은 이전 페이지로 돌아가주세요. </div>
    <v-row class="mt-6 mb-16 pb-16" justify='end'>
    <v-btn
      to="/restaurant"
      class="mr-2"
    >
      메인페이지
    </v-btn>
    <v-btn
      class="mr-6 mb-16"
      @click="goBack"
    >
      뒤로가기
    </v-btn>
    </v-row>
  </div>
</template>

<script>

export default {
  methods: {
    goBack: function(){
      var numberOfEntries = window.history.length;
      if(numberOfEntries>2){
        this.$router.go(-1)
      }else{
      var fpath=  this.PageData.backCrumb.url;
      this.$router.push({
          path: fpath
        })
      }
    },
  }
}
</script>

<style>

</style>
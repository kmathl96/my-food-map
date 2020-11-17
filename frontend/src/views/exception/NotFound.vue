<template>
  <div>
    <h2> 페이지가 존재하지 않습니다. </h2>
    <h3>
      클릭하신 링크가 잘못되었거나 <br>
      페이지가 삭제되었습니다.
    </h3>
    <v-btn
      to="/restaurant"
    >
      메인으로
    </v-btn>
    <v-btn
      @click="goBack"
    >
      뒤로가기
    </v-btn>
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
<template>
  <v-card height="56px">
  <v-footer
    fixed
    flat
    tile
    dark
    width="100%"
    height="56%"
    class="backgroud_gradient">
    
    <v-row justify='center'>
      <v-btn :to="{ name: 'Restaurant' }" icon>
        <v-icon size="24px">mdi-search-web</v-icon>
      </v-btn>

      <v-btn :to="{ name: 'Map' }" class="ml-3" icon >
        <v-icon size="24px">mdi-map</v-icon>
      </v-btn>
      <!-- 피드로 가는 버튼 추가 -->
      <v-btn @click="onClick('IndexFeed')" class="ml-3" icon>
        <v-icon>mdi-format-align-justify</v-icon>
      </v-btn>
      
      <v-btn @click="onClick('MyProfile')" class="ml-3" icon>
        <v-icon>mdi-account</v-icon>
      </v-btn>
        <!-- 맨위로 가기(재구현) -->
        <v-btn @click="$emit('scrollToTop')" class="ml-3" icon>
          <v-icon>mdi-arrow-up</v-icon>
        </v-btn>
    </v-row>
  </v-footer>
  </v-card>
</template>

<script>

export default {
  props: ['userInfo'],
  methods: {
    onClick(toName) {
      // console.log("toName", toName)
      if (this.userInfo) {
        this.$router.push({name : toName, params : {nickname : this.userInfo.nickname}}) // nickname 버전으로 수정
      } else {
        this.$router.push({name : 'Login', query : { redirect : toName}})
      }
    }
  }
}
</script>
<style >
  .backgroud_gradient {
      background: linear-gradient(90deg, #fa709a, #f6d365 100%);
  }
</style>
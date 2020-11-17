<template>
  <div class="user" id="logout">
  </div>
</template>

<script>

export default {
  // 아래와 동일한 기능 & 다른 타이밍
  // beforeRouteEnter(to, from, next) {
  //   console.log("로그아웃.vue 진입1 =>")
  //   console.log("this : ", this)
  //   const token = localStorage.token
  //   next(vm => {
  //       console.log("vm 진입", vm)
  //       vm.$store.dispatch('user/logout', {token : token})
  //       console.log("vm.$router.push")
  //       vm.$router.push({ name: 'Login'})
  //   })
  // },
  beforeMount() {
    // console.log("로그아웃.vue 진입, 삭제할 token값은 : ", localStorage.token)
    const logoutData = {
      token: localStorage.token,
      nextRoute: this.$route.query ? this.$route.query.redirect : null,
      nextParams: this.$route.query ? this.$route.query.params : null
    }
    this.$store.dispatch('user/logout', logoutData)
  }

};
</script>



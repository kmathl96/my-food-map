<template>
  <v-app>
    <NavBar 
      :userInfo="userInfo"
      :items="items"
      :messages="messages"
    />
    <!-- nav bar 수정하며 화면 보이는 부분 늘림-->
    <!-- 끝이라고 인식을 못해서 불러오질 않음-->
    <v-sheet
      id="scrolling-techniques-3"
      class="overflow-y-auto"
      v-scroll.self="onScroll"
      height=700>
      <router-view
        :userInfo="userInfo"
        :isScrollEnd="isScrollEnd"
        @scrollToBottom="onScrollToBottom"/>
    </v-sheet>
    <Footerbar
      :token="token"
      :userInfo="userInfo"
      @scrollToTop="onScrollToTop"
    />
  </v-app>
</template>

<script>
import Footerbar from './components/Footerbar';
import NavBar from './components/NavBar';

import { mapState, mapMutations } from 'vuex';

import UserApi from '@/api/UserApi.js'

export default {
  name: 'App',
  data() {
    return {
    isScrollEnd: false,
    }
  },
  components: {
    Footerbar,
    NavBar,
  },
  computed: {
    ...mapState({
      token: state => state.user.token,
      userInfo: state => state.user.userInfo,
      items: state => state.nav.items,
    }),
  },
  methods: {
    ...mapMutations({
      setToken: 'user/setToken',
      setUserInfo: 'user/setUserInfo',
    }),
    checkToken() {
      // Vue에 token이 없지만, 로컬스토리지에 userInfo가 있다면 업데이트
      if(!this.token && !!localStorage.token) {
        this.setToken(localStorage.token)
      }
    },
    checkUserInfo() {
      // Vue에 userInfo가 없지만, 로컬스토리지에 userInfo가 있다면 업데이트
      if(!this.userInfo && !!localStorage.userInfo) {
        const storageObj = JSON.parse(localStorage.getItem('userInfo'))
        this.setUserInfo(storageObj)
      }
    },
    tempSetListData() { 
      // 임시데이터 생성용
      const listData = [
        { title: 'Search', icon: 'mdi-search-web', destination: 'Restaurant'},
        { title: 'Map', icon: 'mdi-map', destination: 'Map' },
        { title: 'Feed', icon: 'mdi-format-align-justify', destination: 'Feed'},
        { title: 'Profile', icon: 'mdi-account', destination: 'MyProfile' },
        { title: 'Profile', icon: 'mdi-logout', destination: 'MyProfile' },
        // 로그인 로그아웃 if 분기를 위해 나눔
      ]
      this.$store.commit('nav/setItems', listData)
    },
    onScroll(e) {
      const s = e.target
      const maxLevel = s.scrollHeight - 700 // 최대 깊이
      const margin = 100 // 여유 마진
      // console.log(`현재위치:${s.scrollTop}, 스크롤 높이:${s.scrollHeight}, maxlevel:${maxLevel}`)
      if (maxLevel - margin - s.scrollTop < 0) {
        this.isScrollEnd = true
      } else {
        // console.log("스크롤엔드 값은 현재 : ", this.isScrollEnd)
        this.isScrollEnd = false
      }
    },
    onScrollToTop() {
      const target = document.querySelector('#scrolling-techniques-3')
      target.scrollTop = 0
    },
    onScrollToBottom() {
      const target = document.querySelector('#scrolling-techniques-3')
      target.scrollTop = 10000000
    }
  },
  created() {
    // console.log("생성시 토큰 체크")
    this.checkToken()
    this.checkUserInfo()
  },
  mounted() {
    // 임시데이터 생성용
    this.tempSetListData()
  },
  beforeUpdate() { 
    // 필요성에 대해서 아직 모르겠음
    // console.log("업데이트전 토큰 체크")
    this.checkToken()
    this.checkUserInfo()
  }
};
</script>

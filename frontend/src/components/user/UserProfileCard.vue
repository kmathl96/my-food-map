<template>
    <v-card
        max-width="374"
        min-height="120"
        class="mx-auto"
        outlined="outlined"
        style="margin-top:15px;"
    >
        <v-list-item three-line>
            <v-list-item-content>
                <div class="overLine mb-4"></div>
                <v-list-item-title class="headline mb-1">
                  {{profileUser.nickname}} 님
                    <v-btn
                      v-if="!userInfo || profileUser.id !== userInfo.userId"
                      @click="onClick"
                      dark
                      small
                      :color="profileUser.followed ? 'blue' : 'grey'">
                      {{ profileUser.followed ? 'Following' : 'Follow' }}
                    </v-btn>
                </v-list-item-title>
                <v-list-item-subtitle> 일반 - {{profileUser.email}}</v-list-item-subtitle>
            </v-list-item-content>

            <v-list-item-avatar size="80" style="margin-right:10px">
                <img
                   :src="profileUser.image"
                   alt="프로필 사진"
                   >
            </v-list-item-avatar>
        </v-list-item>

        <v-card-actions>
            <v-dialog v-model="dialog" scrollable max-width="300px">
                <template v-slot:activator="{ on, attrs }">
                  <div
                    v-bind="attrs"
                    v-on="on"
                    @click="fetchFollowList()">
                    <v-btn style="margin-bottom:20px;" depressed color="warning">Follower {{follower_list.length || profileUser.follower}}</v-btn> 
                    <v-btn style="margin-left:70px;margin-bottom:20px;" depressed color="warning">Following {{following_list.length || profileUser.following}}</v-btn> 
                  </div>
                </template>
                <v-card>
                  <v-card-title>Follow List</v-card-title>
                  <v-divider></v-divider>
                  <v-tabs
                    centered
                    >
                    <v-tab @click="fetchFollowList()">Follower</v-tab>
                    <v-tab-item>
                        <v-list>
                            <v-list-item
                                v-for="follower in follower_list"
                                :key="follower.userid"
                            >
                                <!-- 프로필 사진(아바타) & 닉네임 -->
                                <v-list-item-avatar @click="toProfile(follower.nickname)">
                                    <v-img :src="follower.image"></v-img>
                                </v-list-item-avatar >
                                <v-list-item-content @click="toProfile(follower.nickname)">
                                    <v-list-item-title v-text="follower.nickname"></v-list-item-title>
                                </v-list-item-content>
                                <v-list-item-action>
                                    <!-- Follow / UnFollow 버튼 -->
                                    <v-btn
                                        v-if="!userInfo || follower.userid !== userInfo.userId"
                                        @click="onFollow(follower)"
                                        dark
                                        small
                                        color=#F7B675>
                                        {{ follower.followed ? 'unFollow' : 'Follow' }}
                                    </v-btn>
                                </v-list-item-action>
                            </v-list-item>
                        </v-list>
                    </v-tab-item>
                    <v-tab @click="fetchFollowList()">Following</v-tab>
                    <v-tab-item>
                        <v-list>
                            <v-list-item
                                v-for="following in following_list"
                                :key="following.userid"
                            >
                                <!-- 프로필 사진(아바타) & 닉네임 -->
                                <v-list-item-avatar @click="toProfile(following.nickname)">
                                    <v-img :src="following.image"></v-img>
                                </v-list-item-avatar >
                                <v-list-item-content @click="toProfile(following.nickname)">
                                    <v-list-item-title v-text="following.nickname"></v-list-item-title>
                                </v-list-item-content>
                                <v-list-item-action>
                                    <!-- Follow / UnFollow 버튼 -->
                                    <v-btn
                                        v-if="!userInfo || following.userid !== userInfo.userId"
                                        @click="onFollow(following)"
                                        dark
                                        small
                                        color=#F7B675>
                                        {{ following.followed ? 'unFollow' : 'Follow' }}
                                    </v-btn>
                                </v-list-item-action>
                            </v-list-item>
                        </v-list>
                    </v-tab-item>
                  </v-tabs>
                  <v-card-text style="height: 200px;">
                  </v-card-text>
                  <v-divider></v-divider>
                  <v-card-actions>
                    <v-btn color="blue darken-1" text @click="dialog = false">Close</v-btn>
                  </v-card-actions>
                </v-card>
            </v-dialog>
        </v-card-actions>
 
    </v-card>

</template>

<script>
// import { mapState } from 'vuex'
import UserApi from '@/api/UserApi.js'

export default {
    data() {
        return {
          follower_list: [],
          following_list: [],
          dialog: false,
    }
    },
    props: [ 'profileUser', 'userInfo', 'routeFetch'],
    methods: {
        onClick() {
            // console.log("onClick 반응")
            if (!this.userInfo) {
                this.$router.push({name:'Login', query:{ redirect: 'Profile', params: {nickname: this.profileUser.nickname}}})
            } else if (this.profileUser.id !== this.userInfo.userId) {
                // console.log("onFollow emit")
                this.$emit('onFollow')
            }
        },
        onFollow(target) {
          if(!this.userInfo) {
            this.$router.push({name:'Login', query:{ redirect: 'Profile', params: {nickname: target.nickname}}})
          }
          //console.log(target)
         // console.log('팔로우', this.userInfo.userId, target.id)
          UserApi.requestFollow(
            {
              followerId: this.userInfo.userId,
              followingId: target.userid,
              no: 0
            },
            res => {
             // console.log("res 정보")
             // console.log(res)  
              if (res.data.message === "Following -1") {
                target.follower -= 1
                target.followed = false
                //console.log("팔로워 숫자 -1")
              } else {
                if (res.data.message === "Following +1") {
                  target.follower += 1
                  target.followed = true
                 // console.log("팔로워 숫자 +1")
                } else {
                  // 성공외 다른 응답이 왔을때 동작
                  //console.log("팔로우 실패", res)
                }
              }
            },
            err => {
              console.error(err)
              // 라우팅 하지 않음
            }
          )
        },
        fetchFollowList() {
            const profileUserId = this.profileUser.id
            const userId = this.userInfo.userId
            
            UserApi.requestFollowerList(
                {
                    profileUserId,
                    userId,
                },
                res => {
                    // console.log("realSetData 콜백 성공, res:", res.data.message)
                    this.follower_list = res.data.message
                },
                err => {
                    // console.error(err)
                    // console.log("에러반응")
                }
            )
            UserApi.requestFollowingList(
                {
                    profileUserId,
                    userId,
                },
                res => {
                    // console.log("realSetData 콜백 성공, res:", res.data.message)
                    this.following_list = res.data.message        
                },
                err => {
                    // console.error(err)
                    // console.log("에러반응")
                }
            )
            // this.$emit('completeFetch')
        },
        toProfile(targetNickname){
            this.$emit('toProfile', targetNickname)
            // this.dialog = false
            // this.$router.push({name: 'Profile', params: {nickname: targetNickname}})
            // this.fetchFollowList()
        },
    },
    watch: {
        dialog(val) {
            if (!val) {
                console.log("dialog", this)
                this.fetchFollowList()
            }
        },
        // routeFetch() {
        //     console.log("반응")
        //     this.fetchFollowList()
        // }

    },
    mounted() {
        this.fetchFollowList()
    }
}
</script>

<style>

</style>

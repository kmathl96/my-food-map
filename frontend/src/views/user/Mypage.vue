<template>
    <div>
        <v-card
            max-width="600"

            min-height="120"
            class="mx-auto mb_custom"
            outlined="outlined"
            style="margin-top:15px;">
            <v-card-actions>
                <v-list-item>
                        <v-icon
                            size="70"
                            color="blue"
                            style="margin:8px 16px 8px 0px;padding:0px;">mdi-account</v-icon>
                        <v-list-item-content>
                            <v-list-item-title class="body-1">{{userInfo.nickname}}님</v-list-item-title>
                            <v-list-item-subtitle>이메일 :
                                {{userInfo.email}}</v-list-item-subtitle>
                        </v-list-item-content>
                        <v-list-item-action style="width:30%;">
                            <v-btn
                                depressed="depressed"
                                @click="onInfoChange()"
                                class="ma-2 widfull"
                                outlined="outlined"
                                color="primary">
                                <v-icon left="left">mdi-pencil</v-icon>
                                정보 수정
                            </v-btn>
                            <v-btn
                                depressed="depressed"
                                @click="onPasswordChange()"
                                class="ma-2 widfull"
                                outlined="outlined"
                                color="primary">
                                <v-icon left="left">mdi-pencil</v-icon>
                                비밀번호 수정
                            </v-btn>
                        </v-list-item-action>
                </v-list-item>
              
            </v-card-actions>
                <v-layout align-space-around justify-space-around row fill-height>
                    
                    <v-subheader><v-btn text large color="error">Follower</v-btn>: 0 <hr class="sero"> <v-btn text large color="error">Following</v-btn> : 0</v-subheader>
                    <!-- <팔로잉 수> -->
                </v-layout>
        </v-card>
    </div>
</template>
      
<script>
import { mapActions,mapState } from 'vuex';

export default {
  computed:{
    ...mapState({
      userInfo: state => state.user.userInfo,
      })
  },
  methods:{
    ...mapActions({
      getUserInfo: 'user/getUserInfo',
    }),
    onInfoChange(){
      // console.log("유저 정보 변경 버튼 눌림")
    },
    onPasswordChange(){
      // console.log("비밀번호 변경 버튼 눌림")
    },
  },
  created(){
    this.getUserInfo()
  },
};

</script>
<style scoped>
  hr.sero{height:100%; width:1px; border-right:1px solid black;}
</style>
<template>
  <v-card
    class="mx-auto"
    max-width="400"
  >
    <v-img
      class="white--text align-end"
      height="200px"
      src="https://cdn.vuetifyjs.com/images/cards/docks.jpg"
    >
      <v-card-title>비밀번호 찾기</v-card-title>
    </v-img>

    <v-card-subtitle class="pb-0">이메일 인증</v-card-subtitle>

    <v-card-text class="text--primary">
      <div>회원님의 이메일로 인증코드를 발송 드렸습니다.</div>
    </v-card-text>

    <v-text-field
      v-model="checkemail"
      :counter="6"
      label="인증코드"
      required
    >
    </v-text-field>

    <v-card-actions>
      <v-btn
        color="orange"
        text
        @click="tempCheck()"
      >
        확인
      </v-btn>

      <v-btn
        color="orange"
        text
      >
        취소
      </v-btn>
    </v-card-actions>
  </v-card>
</template>


<script>

export default {
    data: () => {
    return {
      info:{},
      checkemail:'',
      isSubmit: false,
    };
  },

   methods:{
    tempCheck(){
    //  console.log("확인 버튼 눌림")
   },
  },
};
</script>



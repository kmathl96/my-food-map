<template>
  <div class="user" id="login">
    <v-app id="inspire">
      <v-main>
        <!-- 로그인창 한페이지 보이기 위한 수정-->
        <v-container fluid>
          <v-row align="center" justify="center">
            <v-col cols="12" sm="8" md="4">
              <v-card class="elevation-6 mt-10">
                <!-- 로그인창 색상 변경 -->
                <v-toolbar color="primary lighten-1" dark flat>
                  <v-toolbar-title> 로그인 </v-toolbar-title>
                  <v-spacer></v-spacer>
                </v-toolbar>
                <v-card-text>
                  <v-form>
                    <v-text-field
                      label="Email"
                      name="email"
                      v-model="email"
                      :rules="emailRules"
                      placeholder="이메일"
                      prepend-icon="mdi-account"
                      @keyup.enter="onLogin"
                      type="text"
                      outlined
                    ></v-text-field>

                    <v-text-field
                      id="password"
                      label="Password"
                      name="password"
                      v-model="password"
                      prepend-icon="mdi-lock"
                      :rules="passwordRules"
                      :counter="20"
                      placeholder="비밀번호"
                      outlined
                      @keyup.enter="onLogin"
                      type="password"
                    ></v-text-field>

                    <!-- <v-checkbox
                     light label='로그인 상태 유지'
                     hide-details
                     >
                    </v-checkbox> -->
                  </v-form>
                </v-card-text>

                <v-card-actions class="d-flex justify-space-between ml-1 mr-1">
                  <v-btn :to="{ name: 'Join' }" tile class="mt-1">
                    회원가입
                  </v-btn>
                  <a
                    href="https://kauth.kakao.com/oauth/authorize?client_id=26c62e1d4e23a67774060006069c8b84&redirect_uri=http://i3a409.p.ssafy.io:8399/login&response_type=code"
                  >
                    <v-img width="150" src="@/assets/kakaologin.png" />
                  </a>
                  <v-btn dark color="#F7B675" tile @click="onLogin">
                    로그인
                  </v-btn>
                </v-card-actions>

              </v-card>
            </v-col>
          </v-row>
        </v-container>
      </v-main>
    </v-app>
  </div>
</template>

<script>
export default {
  data: () => {
    return {
      email: "",
      emailRules: [
        (v) => !!v || "이메일을 입력해주세요.",
        (v) =>
          /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/.test(v) ||
          "이메일 형식을 지켜주세요",
      ],
      password: "",
      passwordRules: [(v) => !!v || "비밀번호를 입력해주세요"],
      error: {
        email: false,
        passowrd: false,
      },
      isSubmit: false,
    };
  },

  methods: {
    onLogin() {
      // console.log("파라미터 체크1", this.$route)
      const loginData = {
        email: this.email,
        password: this.password,
        nextRoute: this.$route.query ? this.$route.query.redirect : null, // 원래 다른 페이지에 접근하려다가, 로그인 페이지로 리다이렉트 된 경우
        nextParams: this.$route.query ? this.$route.query.params : null,
      };
      this.$store.dispatch("user/login", loginData);
    },
  },
};
</script>

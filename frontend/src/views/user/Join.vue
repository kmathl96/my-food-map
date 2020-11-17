<template>
  <v-form ref="joinForm" v-model="valid">
    <v-container>
      <v-row>
        <v-col
          cols="12"
          md="12"
        >
          <v-text-field
            v-model="email"
            :rules="emailRules"
            :counter="20"
            label="email"
            required
          ></v-text-field>
        </v-col>

        <v-col
          cols="12"
          md="12"
        >
          <v-text-field
            v-model="nickName"
            :rules="nameRules"
            :counter="10"
            label="NickName"
            required
            focusOut
            ref="inputNickname"
            
          >
          </v-text-field>
            <v-btn
              small
              absolute
              dark
              tile  
              right
              color="primary"
              :disabled="valid"
              @click="checkNickname">
              중복체크
            </v-btn>
          <v-spacer/>

        </v-col>

        <v-col
          cols="12"
          md="12"
        >
          <v-text-field
            v-model="password"
            :rules="passwordRules"
            label="Password"
            type="password"
            required
          ></v-text-field>
        </v-col>

         <v-col
          cols="12"
          md="12"
        >
          <v-text-field
            v-model="passwordConfirm"
            :rules="passwordConfirmRules"
            label="PasswordConfirm"
            type="password"
            required
          ></v-text-field>

                        <v-text-field
                            class="pt-0 mt-8"
                            label="이미지 첨부"
                            @click='pickFile'
                            v-model='profile'
                            prepend-inner-icon='mdi-camera'>
                        </v-text-field>

                        <input
                            type="file"
                            style="display: none"
                            ref="image"
                            accept="image/*"
                            @change="previewImage"
                        >
                       <div v-if="imageData!=null">
                        <!-- <v-btn
                            @click="onUpload"
                        >
                            업로드 하기
                        </v-btn>                        -->
                        <img width=344 height=344 :src="picture">
                        <p v-if="uploadValue !== 0 && uploadValue !== 100 ">업로드 중입니다... {{uploadValue.toFixed()+"%"}}
                            <progress id="progress" :value="uploadValue" max="100" />
                        </p>
                            
                     </div>
        </v-col>
        <v-btn class="ml-4" @click="onReset">초기화</v-btn>
        <v-spacer/>
        <v-btn class="mr-4" :disabled="!valid" @click.prevent="onJoin()"> 회원 가입 </v-btn>
      </v-row>

    </v-container>

  </v-form>
</template>

<script>
import firebase from 'firebase';
import UserApi from '@/api/UserApi.js'

export default {
  data: () => {
    return {
      valid: true,
      nicknameDisable: false,
      msg: "",
      email: "",
      nickName: "",
      password: "",
      passwordConfirm: "",
      image:'',
      isLoading: false,
      error: {
        email: false,
        nickName: false,
        password: false,
        passwordConfirm: false,
        term: false
      },
      isSubmit: false,
      passwordType: "password",
      passwordConfirmType: "password",
      nameRules: [
        v => !!v || '닉네임을 입력해주세요.',
        v => (v && v.length <= 10) || '닉네임은 10글자 이하입니다.',
        v => !(v && v.length <= 10) || '중복검사가 필요합니다!'
      ],
      emailRules: [
        v => !!v || '이메일을 입력해주세요.',
        v => /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/.test(v) || '유효하지 않은 이메일입니다.'
      ],
      passwordRules: [
        v => !!v || '패스워드를 입력해주세요.',
        // v => (v && v.length >= 8) || 'Password must be more than 8 characters'
        v => /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/.test(v) || '비밀번호는 글자, 숫자 포함 8자 이상입니다.',
        // /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#\$%\^&\*])(?=.{8,})/ : 소문자1+대문자1+특문1+8자 이상
      ],
      imageData: null,
      picture: null,
      uploadValue : 0,
      profile : '',

    };
  },

  methods: {
    pickFile() {
			// console.log("픽파일")
			// console.log(this)
			// console.log(this.$refs)
			// console.log(this.$refs.image) // ref에 등록된 이름 기준으로 찾아냄 => input
            this.$refs.image.click()
        },
        previewImage(event) {
           this.uploadValue=0;
           this.picture=null;
           this.imageData = event.target.files[0];
           this.onUpload()
       },
        onUpload(){
            this.picture=null;
            this.profile = `${this.imageData.name}`;
            const storageRef=firebase.storage().ref(`${this.imageData.name}`).put(this.imageData);
            storageRef.on(
                `state_changed`,
                snapshot => {
                    this.uploadValue = (snapshot.bytesTransferred/snapshot.totalBytes)*100;
                },
                error => { 
                    // console.log(error.message)
                },
                () => {
                    this.uploadValue=100;
                    storageRef.snapshot.ref.getDownloadURL().then((url) => {
                        this.picture =url;
                        this.image =url;
                        // console.log('url 저장')
                        //this.postingForm
                    });
            });
        },      
    onJoin(){
      // 클릭단계에서 유효성 검증 이미 진행하였음 => :disable="valid"
      const singUpData = {
        nickname : this.nickName,
        email : this.email,
        password : this.password,
        image : this.image,
      }
      this.$store.dispatch('user/join', singUpData)
      // 라우터 push도 user/join 진행
    },
    onReset() {
        this.$refs.joinForm.reset()
    },
    checkNickname() {
      UserApi.requestCheckNickname(
        this.nickName,
        res => {
          // console.log(`응답 ${res}, ${res.data}, ${res.data.state}`)
          if(res.data.state === 'ok') {
            // 중복 검사 통과
            alert("사용가능한 아이디 입니다")
            this.nameRules = [
              v => !!v || '닉네임을 입력해주세요.',
              v => (v && v.length <= 10) || '닉네임은 10글자 이하입니다.'
            ]
          } else {
            alert("중복된 아이디 입니다")  
          }
        },
        err => {
          // console.log(err)
          alert("중복검사 에러입니다")  
        }
      )
    }
  },
  computed: {
    passwordConfirmRules() {
      if (this.password === this.passwordConfirm) {
        return [ true ]
      } else {
        return [ "비밀번호가 일치하지 않습니다." ]
      }
    },
  },
  watch: {
    nickName() {
      this.nameRules = [
        v => !!v || '닉네임을 입력해주세요.',
        v => (v && v.length <= 10) || '닉네임은 10글자 이하입니다.',
        v => !(v && v.length <= 10) || '중복검사가 필요합니다!'
      ]
    }
  }
}
</script>
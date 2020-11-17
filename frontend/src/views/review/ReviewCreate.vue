<template>

    <v-card width="100%" class="pr-0 pl-0"> <!-- 작성창 고정 -->
     <Rating :grade="3" :maxStars="5" :hasCounter="true" />
        <v-row>
            <v-col
              cols="12"
              sm="8"
              md="4"
            >
                <v-card-title class="pb-0">
                    <h2>{{resname}}</h2>
                </v-card-title>
                <v-card-text class="pt-4 pb-0">
                    <v-form>
                        <v-img
                            v-if="resimage"
                            :src= "require('@/assets/' + resimage)"
                            height="200px"
                            width="300px"
                            style="margin-left:20px;margin-bottom:20px;"
                        ></v-img>
                        <v-textarea 
                            label="리뷰를 작성해주세요"
                            :outlined="true"
                            v-model="review.content"
                        />
                        <v-text-field
                            class="pt-0"
                            label="이미지 첨부"
                            @click='pickFile'
                            v-model='review.name'
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
                    </v-form>
                </v-card-text>
                    
                    <v-card-actions class="ml-5 mr-5 mt-3">
                   
                    <div>
                      <star-rating v-model="review.rank" :round-start-rating="false" />
                    </div>
                    </v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn 
                        @click="onCreate()"
                        class="ma-2 widfull"
                         outlined="outlined"
                         color="warning">
                        <v-icon left="left">mdi-pencil</v-icon>
                         리뷰 작성
                    </v-btn>
                
            </v-col>
        </v-row>
    </v-card>
</template>

<script>
import ReviewApi from "@/api/ReviewApi.js";
import firebase from 'firebase';
import StarRating from 'vue-star-rating'

export default {
    name: 'ReviewCreate',
    props: ['restaurantInfo'],
    components: {
        StarRating
    },
    created() {
        setTimeout(() => {
            this.rating = 3.5
        }, 1000)
    },
    data() {
        return {
            rating:0,
            resname: this.$route.params.restaurantName,
            resimage: this.$route.params.resimage,
            review: {
                resid : this.$route.params.restaurantId,
                content: "",
                rank: 1,
                image: '',
                name,
            },
            title: "Image Upload",
            dialog: false,
            rules: [
                value => !value || value.size < 2000000 || 'Img size should be less than 2 MB!',
            ],
            imageData: null,
            picture: null,
            uploadValue: 0

        }
    },
    props: ['userInfo'],
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
            this.review.name = `${this.imageData.name}`;
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
                        this.review.image =url;
                        // console.log('url 저장')
                        //this.postingForm
                    });
            });
        },         
        onCreate() {
            // console.log("onCraete 메소드 실행",this.review)
            const data = {
                userid:  Number(this.userInfo.userId),
                nickname: this.userInfo.nickname,
                content: this.review.content,
                reviewrank : Number(this.review.rank),
                image : this.review.image,
                resid : this.$route.params.restaurantId,
            }
            // console.log(typeof(data.userId))
            ReviewApi.requestCreate(
                data,
                res => {
                    // console.log("resquestCreate 성공, res : ", res)
                    if(res.data.state === 'ok') {
                        // 리뷰 작성 성공 => 작성한 리뷰 페이지로 라우팅
                        alert("리뷰가 작성 되었습니다.")
                        this.$router.push( { name: 'RestaurantReview', params: {restaurantId: this.review.resid }} );
                    } else {
                        // 리뷰 작성 실패 => 에러 원인 알려주기?
                        // console.log("리뷰작성 실패, res.data: ", res.data)
                        alert(res.data.message || "리뷰가 작성에 실패했습니다.")
                    }
                },
                err => {
                    // console.log(err)
                    // 에러작성 페이지로
                    this.$router.push( { name: 'ErrorPage' })
                }
            )
        },
        // onFilePicked(e) {
        //     console.log("e입니다",e) // change 이벤트
        //     console.log("e.target입니다",e.target) // input
        //     console.log("e.target.files입니다",e.target.files) // Filelist
        //     console.log("e.target.files[0]입니다",e.target.files[0]) // Filelist
        //     console.log("URL.createObjectURL(e.target.files[0])입니다",URL.createObjectURL(e.target.files[0])) // Filelist
        
        //     const files = e.target.files
        //     if (files[0] !== undefined) {
        //         this.review.image.name = files[0].name
        //         if (this.review.image.name.lastIndexOf('.') <= 0) {
        //             return
        //         }
        //         const fr = new FileReader()
        //         fr.readAsDataURL(files[0])
        //         fr.addEventListener('load', () => {
        //             this.review.image.url = fr.result
        //             this.review.image.file = files[0] // this is an image file that can be sent to server...
        //         })
        //     } else {
        //         this.review.image.name = ''
        //         this.review.image.url = ''
        //         this.review.image.file = ''
        //     }
        // },
    }
}
</script>

<style>
img.preview{
    width:200px;
}
</style>

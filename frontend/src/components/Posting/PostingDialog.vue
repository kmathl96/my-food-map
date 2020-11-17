<template>
  <v-row justify="center">
<v-dialog v-model="dialog"  persistent width="unset" height="600px">
    
      <v-card    >
        <v-card-title>게시글 작성</v-card-title>
        <v-divider></v-divider>

        <v-container fluid style="padding-bottom:0px;">
          <v-textarea
           v-model="content"
            filled
            rows="9"
            value="리뷰 작성 ㄱ ㅡ ㄱ"
          ></v-textarea>
        </v-container>

        <v-divider></v-divider>
        <v-card-actions>
          <v-btn color="blue darken-1" text @click="dialog = false">close</v-btn>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" text @click="dialog = false">Photo</v-btn>
          <v-btn color="blue darken-1" text @click="dialog = false">Done</v-btn>
        </v-card-actions>
      </v-card>
</v-dialog>
  </v-row>
</template>
 
<script>
  export default {
    data () {
      return {
        dialog: false,
        content: '',
        tag:[],
      }
      
    },


  }
</script>
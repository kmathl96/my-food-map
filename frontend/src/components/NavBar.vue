<template>
	<v-card height="56px">
		<v-app-bar
			color="backgroud_gradient"
			dark
			fixed
			scroll-target="#scrolling-techniques-3">
			<v-btn to="/restaurant" class="ml-10 title" icon>
				<v-toolbar-title class="ml-4"> My Food Map </v-toolbar-title>
			</v-btn>
			
			<v-spacer></v-spacer>
			
			<v-btn 
				@click.native.stop="drawerToggle = true"
				icon style="margin-top:1px">
				<v-icon>mdi-dots-horizontal</v-icon>
			</v-btn>
			
			<!-- drawer dots로 옮김 -->
			<v-badge
				@click.native.stop="drawerToggle2 = true "
				:content="messages.length"
				:value="messages.length"
				style="margin-top:1px"
				overlap>
				<v-icon>mdi-bell</v-icon>
			</v-badge>
		</v-app-bar>

		<v-navigation-drawer
			v-model="drawerToggle"
			fixed
			temporary
			right
			width="250">

			<v-list dense>
				<v-list-item
					@click="toProfile">
					<v-list-item-avatar>
						<v-icon size="40">mdi-account-circle</v-icon>
					</v-list-item-avatar>
					<v-list-item-content >
						<v-list-item-title
							v-text="userInfo ? userInfo.nickname : '로그인이필요합니다'"
							:class="{ 'text-caption': !userInfo}"/>
					</v-list-item-content>
				</v-list-item>
				
				<v-divider/>			
				
				<v-list-item
					v-for="item in items"
					:key="item.title"
					link
					:to="{
						name: item.destination,
						query: item.params
					}">
					<v-list-item-icon>
						<v-icon>{{ item.icon }}</v-icon>
					</v-list-item-icon>
					<v-list-item-content>
						<v-list-item-title
							v-text="item.title"/>
					</v-list-item-content>
				</v-list-item>
			</v-list>
		<!-- 드로워 서비스 소개 추가 -->
		<v-col
			class="backgroud_gradient py-2 text-center white--text"
			cols="12">
			ⓒ {{ new Date().getFullYear() }} — MFD by MiBaSi
		</v-col>
		</v-navigation-drawer>

		<v-navigation-drawer
			v-model="drawerToggle2"
			fixed
			temporary
			right
			width="250">
			<v-list>
				<v-subheader>
					메세지
				</v-subheader>
				<v-list-item-group
					v-if="Boolean(messages)"
					v-model="message">
					<v-list-item
						v-for="(message, i) in messages"
						:key="i">
						<v-list-item-content @click="onClick(message.reviewid)">
							<v-list-item-title
								v-text="message.content"/>
						</v-list-item-content>

					</v-list-item>
				</v-list-item-group>
				<v-list-item-group
					v-else>
					<v-list-item>
						<v-list-item-content>
							<v-list-item-title>
								메세지가 없습니다!
							</v-list-item-title>
						</v-list-item-content>

					</v-list-item>
				</v-list-item-group>
			</v-list>

			<v-divider></v-divider>


		</v-navigation-drawer>

	</v-card>
</template>

<script>
import UserApi from '@/api/UserApi.js'
import { mapState } from 'vuex'
export default {
  name: 'NavBar',
  props: ['userInfo'],
  data() {
      return {
		drawerToggle: false,
		drawerToggle2: false,
      }
  },
  computed: {
	items() {
		return [
			{ title: 'Search', icon: 'mdi-search-web', destination: 'Restaurant' },
			{ title: 'Map', icon: 'mdi-map', destination: 'Map' },
			{ title: 'Feed', icon: 'mdi-format-align-justify', destination: 'IndexFeed' },
			// 로그인 여부에 따라, Login or Logout 리스트 출력
			{ title: this.userInfo ? 'Logout' : 'Login/Signup',
				icon: this.userInfo ? 'mdi-logout' : 'mdi-login',
				destination: this.userInfo ? 'Logout' : 'Login',
				query: { redirect: this.$route.name, params: this.$route.params}
			},
		]
	  },
	  ...mapState({
		  messages: state => state.user.messages,    
	  })
  },
  methods: {
    toProfile() {
      if (this.userInfo) {
        this.$router.push({name : 'MyProfile', params : {nickname : this.userInfo.nickname}})
      } else {
        this.$router.push({name : 'Login', query: { redirect: 'MyProfile' }})
      }
	},
	onClick(val) {
		UserApi.requestNoticeCheck(
			{
				userId: this.userInfo.userId,
				reviewId: val
			},
			res => {
				// console.log(res)
				this.$router.push({name: 'ReviewDetail', params: {reviewId: val}})
			},
			err => {
				// console.log(err)
			}
		)
		}
	},
}
</script>
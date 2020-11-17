
import Login from './views/user/Login.vue'
import Join from './views/user/Join.vue'
// import Mypage from './views/user/Mypage.vue'
// import UserPage from './views/user/UserPage.vue'
import Profile from './views/user/Profile.vue' // 경민버전
import Findpw from './views/user/Findpw.vue'
import Logout from './views/user/Logout.vue'

import Map from './views/map/map.vue'

import Restaurant from './views/restaurant/Restaurant.vue'
// import RestaurantDetail from './views/restaurant/RestaurantDetail.vue'
import RestaurantReview from './views/restaurant/RestaurantReview.vue'

import ReviewList from './views/review/ReviewList.vue'
import ReviewCreate from './views/review/ReviewCreate.vue'
import ReviewDetail from './views/review/ReviewDetail.vue'

import ErrorPage from './views/exception/Error.vue'
import NotFound from './views/exception/NotFound.vue'

import IndexFeed from './views/feed/IndexFeed.vue'
import Components from './views/Components.vue'

import TestVue from './views/testvue/TestVue.vue'
import KakaoLogin from './views/user/kakaoLogin.vue'

/*
const checkToken = function(token){
    return token===null || token==='';
}
const  ifNotAuthenticated = (to, from, next) => {
   var token = localStorage.JWT
   console.log(token)
   if (checkToken(token)) {
    return next({name:'Login'})
   }
   return next()
 }

 const  ifAuthenticated = (to, from, next) =>{
     var token = localStorage.JWT
     console.log(token)
     if(!checkToken(token)){
        return next({name:'FeedMain'})
    }
    return next()
 } 

 */
const requireAuth = (to, form, next) => {
	// console.log("requireAuth 체크", Boolean(localStorage.getItem('userInfo')))
	if (Boolean(localStorage.getItem('userInfo'))) {
		return next()
	} else {
		// console.log("파라미터 체크0", to, to.params)
		next({
			path : '/user/login',
			query: { redirect: to.name, params: to.params },
		})

	}
}

export default [

	{
		path : '/',
		redirect : { name : 'Map'}
	},
	{
		path : '/user/login',
		name : 'Login',
		component : Login,
	},
	{
		path : '/user/join',
		name : 'Join',
		component : Join
	},
	{
		path: '/user/logout',
		name: 'Logout',
		component : Logout,
	},
	// {
	// 	path : '/user/mypage',
	// 	name : 'Mypage',
	// 	component : Mypage,
	// 	beforeEnter : requireAuth,
	// },
	{
		path : '/user/findpw',
		name : 'Findpw',
		component : Findpw,
		beforeEnter : requireAuth,
	},
	{
		path : '/user/:nickname', // userId 요청하던 버전에서 nickname으로 요청하던 버전으로 수정
		name : 'Profile',
		component : Profile,
	},
	{
		path : '/user/:nickname', // userId 요청하던 버전에서 nickname으로 요청하던 버전으로 수정
		name : 'MyProfile',
		component : Profile,
		beforeEnter : requireAuth,
	},
	{
		path : '/map/map',
		name : 'Map',
		component : Map
	},
	{
		path : '/restaurant',
		name : 'Restaurant',
		component : Restaurant
	},
	// {
	// 	path : '/restaurant/:restaurantId',
	// 	name : 'RestaurantDetail',
	// 	component : RestaurantDetail
	// },
	{
		path : '/restaurant/:restaurantId/review',
		name : 'RestaurantReview',
		component : RestaurantReview
	},

	{
		path : '/review/create',
		name : 'ReviewCreate',
		component : ReviewCreate,
		beforeEnter : requireAuth,
	},
	{
		path : '/review/:reviewId',
		name : 'ReviewDetail',
		component : ReviewDetail,
		// beforeEnter : requireAuth,
	},
    {
        path : '/review',
        name : 'ReviewList',
        component : ReviewList,
    },
	{
		path : '/error',
		name : 'ErrorPage',
		component : ErrorPage
	},
	{
		path : '/components',
		name : 'Components',
		component : Components
	},
	{
		path : '/IndexFeed',
		name : 'IndexFeed',
		component : IndexFeed
	},
	{
		path : '/test',
		name : 'TestVue',
		component : TestVue
	},
	{
		path : '/user/login/:email/:userid/:nickname',
		name : 'KakaoLogin',
		component : KakaoLogin,
		props: true
	},
	{
		path : '*',
		name : 'NotFound',
		component : NotFound
	},
]
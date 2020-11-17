const axios = require('axios')
// const token = `Bearer ${localStorage.getItem('JWT')}`
// axios.defaults.headers.common['Authorization'] = token

//const hosturl = 'http://localhost:8399'
const hosturl = 'http://i3a409.p.ssafy.io:8399'
const appname = '/user'

const refreshAuthToken = () => {
    const AuthToken = `Bearer ${localStorage.getItem('JWT')}`
    axios.defaults.headers.common['Authorization'] = AuthToken
}

const requestSignup = (data, callback, errorCallback) => {
    // console.log("Signup 요청 : ", hosturl+appname+'/join', data)
    axios.post(hosturl+appname+'/join', data)
    .then(callback)
    .catch(errorCallback)
}

const requestLogin = (data,callback,errorCallback) => {
    // console.log("Login 요청 : ", hosturl+appname+'/login', data)
    axios.post(hosturl+appname+'/login', data)
    .then(callback)
    .catch(errorCallback)
}

const requestLogout = (data,callback,errorCallback) => {
    // console.log("Logout 요청 : ", hosturl+appname+'/logout', data)
    refreshAuthToken()
    axios.post(hosturl+appname+'/logout', data)
    .then(callback)
    .catch(errorCallback)
}

// userId 11 로 요청하는버전
const requestUserInfo = (data, callback, errorCallback) => {
    // console.log(`UserInfo 요청 : ${hosturl}${appname}/${data.id}/${data.userId}`)
    axios.get(`${hosturl}${appname}/${data.id}/${data.userId}`)
    .then(callback)
    .catch(errorCallback)
}

// nickname c1 으로 요청하는 버전
const requestUserInfoByNickname = (data, callback, errorCallback) => {
    // console.log(`UserInfo 요청 : ${appname}/nickname/${data.nickname}/${data.userId}`)
    axios.get(`${hosturl}${appname}/nickname/${data.nickname}/${data.userId}`)
    .then(callback)
    .catch(errorCallback)
}

const requestFollow = (data, callback, errorCallback) => {
    refreshAuthToken()
    // console.log("Follow 요청 :", hosturl+appname+'/follow', data)
    axios.post(hosturl+appname+'/follow', data)
    .then(callback)
    .catch(errorCallback)
}
const requestCheckNickname = (data, callback, errorCallback) => {
    // console.log(`CheckNickname 요청 : ${hosturl}${appname}/checkNickname/${data}`)
    axios.get(`${hosturl}${appname}/checkNickname/${data}`)
    .then(callback)
    .catch(errorCallback)
}
const requestNotice = (data, callback, errorCallback) => {
    // console.log(`Notice 요청 :  ${hosturl}/notice/comment/${data}`)
    axios.get(`${hosturl}/notice/comment/${data}`)
    .then(callback)
    .catch(errorCallback)
}
const requestFollowerList = (data, callback, errorCallback) => {
    refreshAuthToken()
    // console.log("Follower list 요청 :", hosturl+appname+`/follower/${data.profileUserId}/${data.userId}`, data)
    axios.get(hosturl+appname+`/follower/${data.profileUserId}/${data.userId}`)
    .then(callback)
    .catch(errorCallback)
}
const requestFollowingList = (data, callback, errorCallback) => {
    refreshAuthToken()
    // console.log("Following list 요청 :", hosturl+appname+`/following/${data.profileUserId}/${data.userId}`, data)
    axios.get(hosturl+appname+`/following/${data.profileUserId}/${data.userId}`)
    .then(callback)
    .catch(errorCallback)
}

const requestNoticeCheck = (data, callback, errorCallback) => {
    // console.log(`Notice 요청 :  ${hosturl}/notice/comment/${data.userId}/${data.reviewId}`)
    axios.get(`${hosturl}/notice/comment/${data.userId}/${data.reviewId}`)
    .then(callback)
    .catch(errorCallback)
}

const UserApi = {
    requestSignup:(data,callback,errorCallback)=>requestSignup(data,callback,errorCallback),
    requestLogin:(data,callback,errorCallback)=>requestLogin(data,callback,errorCallback),
    requestLogout:(data,callback,errorCallback)=>requestLogout(data,callback,errorCallback),
    requestUserInfo:(data,callback,errorCallback)=>requestUserInfo(data,callback,errorCallback), 
    requestFollow:(data,callback,errorCallback)=>requestFollow(data,callback,errorCallback),
    requestUserInfoByNickname:(data,callback,errorCallback)=>requestUserInfoByNickname(data,callback,errorCallback),
    requestCheckNickname:(data,callback,errorCallback)=>requestCheckNickname(data,callback,errorCallback),
    requestNotice:(data,callback,errorCallback)=>requestNotice(data,callback,errorCallback),
    requestFollowerList:(data,callback,errorCallback)=>requestFollowerList(data,callback,errorCallback),
    requestFollowingList:(data,callback,errorCallback)=>requestFollowingList(data,callback,errorCallback),
    requestNoticeCheck:(data,callback,errorCallback)=>requestNoticeCheck(data,callback,errorCallback),
}

export default UserApi

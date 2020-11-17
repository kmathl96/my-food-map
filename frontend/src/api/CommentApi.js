const axios = require('axios')
const hosturl = 'http://i3a409.p.ssafy.io:8399'
const appname = '/comment'

const refreshAuthToken = () => {
    const AuthToken = `Bearer ${localStorage.getItem('JWT')}`
    axios.defaults.headers.common['Authorization'] = AuthToken
}
const requestList = (reviewId, callback, errorCallback) => {
    // console.log("CommentList 요청 : ", hosturl+appname+'/'+reviewId)
    axios.get(hosturl+appname+'/'+reviewId)
    .then(callback)
    .catch(errorCallback)
}
// const requestInfo = (reviewId, callback, errorCallback) => {
//     console.log("CommentInfo 요청 : ", hosturl+appname+'/'+reviewId)
//     axios.get(hosturl+appname+'/'+reviewId)
//     .then(callback)
//     .catch(errorCallback)
// }
const requestCreate = (data, callback, errorCallback) => {
    refreshAuthToken()
    // console.log("Create 요청 : ", hosturl+appname+'/create_comment', data)
    axios.post(hosturl+appname+'/create_comment', data)
    .then(callback)
    .catch(errorCallback)
}
// const requestUserReview = (profileUserId, callback, errorCallback) => {
//     console.log("UserReview 요청 : ", hosturl+appname+profileUserId+profileUserId)
//     axios.get(hosturl+appname+'/'+profileUserId+'/'+profileUserId)
//     .then(callback)
//     .catch(errorCallback)
// }

const CommentApi = {
    requestList:(callback,errorCallback)=>requestList(callback,errorCallback),
    // requestInfo:(reviewId,callback,errorCallback)=>requestReviewInfo(reviewId,callback,errorCallback),
    requestCreate:(data,callback,errorCallback)=>requestCreate(data,callback,errorCallback),
    // requestUserReview:(profileUserId,callback,errorCallback)=>requestUserReview(profileUserId,callback,errorCallback),
}

export default CommentApi
const axios = require('axios')
//const hosturl = 'http://localhost:8399'
const hosturl = 'http://i3a409.p.ssafy.io:8399'
const appname = '/review'

const refreshAuthToken = () => {
    const AuthToken = `Bearer ${localStorage.getItem('JWT')}`
    axios.defaults.headers.common['Authorization'] = AuthToken
}
const requestLike = (data, callback, errorCallback) => {
    refreshAuthToken()
    // console.log("Like 요청 : ", hosturl+appname+'/like', data)
    axios.post(hosturl+appname+'/like', data)
    .then(callback)
    .catch(errorCallback)
}
const requestReviewList = (callback, errorCallback) => {
    // console.log("ReviewList 요청 : ", hosturl+appname+'/list')
    axios.get(hosturl+appname+'/list')
    .then(callback)
    .catch(errorCallback)
}

const requestReviewInfo = (reviewId, callback, errorCallback) => {
    // console.log("ReviewInfo 요청 : ", hosturl+appname+'/'+reviewId)
    axios.get(hosturl+appname+'/'+reviewId)
    .then(callback)
    .catch(errorCallback)
}

const requestCreate = (data, callback, errorCallback) => {
    refreshAuthToken()
    // console.log("Create 요청 : ", hosturl+appname+'/create', data)
    axios.post(hosturl+appname+'/create', data)
    // data : userId, review (place, content, rank, image(name, url, file))
    .then(callback)
    .catch(errorCallback)
}
const requestUserReview = (profileUserId, callback, errorCallback) => {
    // console.log("UserReview 요청 : ", hosturl+appname+profileUserId+profileUserId)
    axios.get(hosturl+appname+'/'+profileUserId+'/'+profileUserId)
    .then(callback)
    .catch(errorCallback)
}

const requestFeedReview = (userId, callback, errorCallback) => {
    // console.log(`FeedReview 요청 : ${hosturl}${appname}/following/${userId}`)
    axios.get(`${hosturl}${appname}/following/${userId}`)
    .then(callback)
    .catch(errorCallback)
}

const ReviewApi = {
    requestLike:(data,callback,errorCallback)=>requestLike(data,callback,errorCallback),
    requestReviewList:(callback,errorCallback)=>requestReviewList(callback,errorCallback),
    requestReviewInfo:(reviewId,callback,errorCallback)=>requestReviewInfo(reviewId,callback,errorCallback),
    requestCreate:(data,callback,errorCallback)=>requestCreate(data,callback,errorCallback),
    requestUserReview:(profileUserId,callback,errorCallback)=>requestUserReview(profileUserId,callback,errorCallback),
    requestFeedReview:(userId,callback,errorCallback)=>requestFeedReview(userId,callback,errorCallback),
}

export default ReviewApi

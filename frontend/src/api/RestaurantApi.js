const axios = require('axios')
//const hosturl = 'http://localhost:8399'
const hosturl = 'http://i3a409.p.ssafy.io:8399'
const appname = '/restaurants'

const refreshAuthToken = () => {
    const AuthToken = `Bearer ${localStorage.getItem('JWT')}`
    axios.defaults.headers.common['Authorization'] = AuthToken
}
const requestLike = (data, callback, errorCallback) => {
    refreshAuthToken()
    // console.log("Like 요청 : ", hosturl+appname+'/like', data)
    axios.post(hosturl+appname+'/like', data) // userId, restaurantId
    .then(callback)
    .catch(errorCallback)
}
const requestList = (callback, errorCallback) => {
    // console.log("List 요청 : ", hosturl+appname)
    axios.post(hosturl+appname)
    .then(callback)
    .catch(errorCallback)
}

const requestInfo = (restaurantId, callback, errorCallback) => {
    // console.log("Info 요청 : ", hosturl+appname+'/'+restaurantId)
    axios.get(hosturl+appname+'/'+restaurantId)
    .then(callback)
    .catch(errorCallback)
}

const requestCreate = (data, callback, errorCallback) => {
    refreshAuthToken()
    // console.log("Create 요청 : ", hosturl+appname+'/create', data)
    axios.post(hosturl+appname+'/create', data)
    .then(callback)
    .catch(errorCallback)
}
const requestReviews = (data, callback, errorCallback) => {
    // console.log("Reviews 요청 : ", hosturl+appname+'/'+data.restaurantId+'/reviews/'+data.userid)
    axios.get(hosturl+appname+'/'+data.restaurantId+'/reviews/'+data.userid)
    .then(callback)
    .catch(errorCallback)
}

const RestaurantApi = {
    requestLike:(data,callback,errorCallback)=>requestLike(data,callback,errorCallback),
    requestList:(callback,errorCallback)=>requestList(callback,errorCallback),
    requestInfo:(restaurantId,callback,errorCallback)=>requestInfo(restaurantId,callback,errorCallback),
    requestCreate:(data,callback,errorCallback)=>requestCreate(data,callback,errorCallback),
    requestReviews:(restaurantId,callback,errorCallback)=>requestReviews(restaurantId,callback,errorCallback),
}

export default RestaurantApi

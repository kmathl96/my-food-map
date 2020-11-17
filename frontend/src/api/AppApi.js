// const axios = require('axios')

const getGeolocation = (callback, errorCallback) => {
    const options = {
        enableHighAccuracy: true,
        maximumAge: 60*1000, // 1분 캐싱
        timeout: 60*1000, // 최대 1분 찾기
    }
    // console.log("AppApi.getGeolocation")
    if (!navigator.geolocation) {
        // console.log("Geolocation 사용 불가능한 기기 입니다.")
    } else {
        // console.log("Geolocation 사용 가능한 기기입니다.")
        navigator.geolocation.getCurrentPosition(
            callback,
            errorCallback,
            options
        )
    }
}

const AppApi = {
    getGeolocation:(callback,errorCallback)=>getGeolocation(callback,errorCallback),
}

export default AppApi

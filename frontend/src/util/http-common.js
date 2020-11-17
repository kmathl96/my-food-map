import axios from 'axios';

// axios 객체 생성
export default axios.create({
  //baseURL : 'http://localhost:8399',
  baseURL: 'http://i3a409.p.ssafy.io:8399',
  headers: {
    'Content-type': 'application/json',
  },
});

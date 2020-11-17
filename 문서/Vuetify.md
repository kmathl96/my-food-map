# Vuetify

Vue.js를 위한 컴포넌트 라이브러리

## 설치

```bash
$ vue create ex-app2 # default 선택
$ vue add vuetify # default 선택
$ yarn serve
```

## 컴포넌트

### v-card

> https://vuetifyjs.com/ko/components/cards/#cards
>
> 패널에서 정적 이미지까지 사용가능한 다목적 구성요소

1. v-card-title
   - 기본 글꼴 크기 및 패딩 제공
   - typography class로 글꼴 크기 덮어쓰기 가능

2. v-card-subtitle
   - 기본 글꼴 크기 및 패딩 제공
   - typography class로 글꼴 크기 덮어쓰기 가능

3. v-card-text
   - 카드 텍스트 컨텐츠에 사용
   - 텍스트에 패딩을 적용, 글꼴 크기를 .875rem으로 줄임
4. v-card-actions
   - `v-btn` or `v-menu` 카드를 위한 actions 를 사용하는 컨테이너 공간

### v-form

> https://vuetifyjs.com/ko/components/forms/#forms
>
> 유효성 검사 양식 지원
>
> 타사 유효성 검사 플러그인 => Vee-validate, vuelidate 가능

### v-text-field

> https://vuetifyjs.com/ko/components/text-fields/
>
> 사용자 제공 정보 수집에 사용

- label
- type
  - 디폴트 값 : text
  - password

### v-divider

> hr 효과

### v-spacer

> 예제에서 버튼간 간격 주어, 좌우 정렬 되었음



# 패스워드 보기 on / off

1. data에 `showPassword` 선언
2. type에 바인딩 : `:type="showPassword ? 'text' : 'password'"`
3. 클릭으로 showPassword 변수 T/F 변환 `@click-append="showPassword = !showPassword"`
   - `append-icon` 에는 `@click:append`가 통하지 않음

# 빠른시작

> https://vuetifyjs.com/ko/getting-started/quick-start/

### Vue CLI로 설치

- 뷰티파이 Vue cli package 설치

```bash
$ vue create my-app
$ cd my-app
$ vue add vuetify	
```

### Webpack 설치

- 의존성 추가

```bash
$ yarn add vuetify
# $ npm install vuerify

$ yarn add sass sass-loader fibers deepmerge -D
# $ npm install sass sass-loader fibers deepmerge -D
```

- `webpack.config.js`

#### Webpack의 기본 개념

> 출처 : [김정환 블로그](http://jeonghwan-kim.github.io/js/2017/05/15/webpack.html)

엔트리

모듈간 의존성 그래프의 시작점인 엔트리를 통해서 웹팩은 필요한 모듈을 로딩하고 하나의 파일로 묶는다.

설정파일 (webpack.config.js)

- entry : 엔트리 시작점 설정

- output :  의존성 있는 모든 모듈을 번들로 묶고, output에 기록한다.

```js
module.exports = { 
	entry: {
        main: './src/main.js' // 엔트리 지정
    },
  output: {
    filename: 'bundle.js',
    path: './dist'
  }
}
```

index.html

- 번들된 파일을 로딩

```html
<body>
    <script src="./dist/bundle.js"></script>
</body>
```

src/main.js

- 엔트리에 설정한 JS는 Utils.js 모듈을 사용한다

```js
import Utils from './Utils'
Utils/log('Hello webpack')
```

src/Utils.js

```js
export default class Utils {
    static log(msg) {
        console.log('[LOG] ' + msg)
    }
}
```

터미널

- 빌드

```bash
webpack
```

### 로더

JS밖에 모르는 웹팩에게 이해시키는 역할

- test : 로딩할 파일 지정
- use : 적용할 로더 설정

### babel-loader

- 버전간 변환

webpack.config.js

- node_modules 폴더는 패키지 폴더라서 제외
- 로더는 바벨-로더
- npm으로 로더 추가

```js
module.exports = {
  module: {
    rules: [{
      test: /\.js$/,
      exclude: 'node_modules',
      use: {
        loader: 'babel-loader',
        options: {
          presets: ['env']
        }
      }
    }]
  }
}
```

```bash
npm i --save-dev babel-loader babel-core babel-preset-env
```

### css-loader, style-loader

css-loader

​	CSS 파일을 JS로 변환하여 로딩

### 플러그인

번들된 결과물을 처리 (로더는 파일단위 처리)

### UglifyJsPlugin

- 로더로 처리된 JS 결과물을 난독화 처리하는 플러그인
- 웹팩 설정 객체의 plugins 배열에 추가

```js
const webpack = require('webpack')

module.exports = {
  plugins: [
    new webpack.optimize.UglifyJsPlugin(),
  ]
}
```

ExtractTextPlugin

sass

​	css의 전처리기
# :pencil2: Today I Learned



## 2020-07-24 :sun_behind_rain_cloud:

### 흐름

1. url로 요청 들어오면

2. controller에서 service 호출
3. service에서 repository 호출
4. repository에서 db 조작할 일이 있을 때 mapper 호출
5. mapper에서 쿼리로 db 조작
6. 다시 repository - service - controller
7. 프론트로 넘겨줌 !



### Dto

1. 어떤 값을 넣어야 할지

2. 어떤 타입인지

3. getter, setter 생성

   - 오른쪽 눌러서 source - generate getter, setter

4. 생성자 만들기

   1. **기본 생성자** : 오른쪽 눌러서 source - generate constructor from superclass

   2. 인자 값을 갖는 생성자 : 오른쪽 눌러서 source - generate constructor using fields

5. toString 함수 만들기

   - 어떤 형태로 출력할지 정의

   - generate toString



## 2020-07-26 :sunny:

### 스프링 코드 짜는 흐름

1. mapper에서 쿼리 작성
2. repository
   1. **Dao**에서 인터페이스 작성
   2. Impl 파일에서 함수 작성 - mapper 쿼리 리턴
3. service
   1. 인터페이스 작성
   2. Impl 파일에서 함수 작성 - Dao의 함수 리턴
4. contoller에서 함수 작성
   1. @ApiOperation(value="`무슨 함수인지?`")
   2. @RequestMapping(value="`url`", method=RequestMethod.`요청방식`)
   3. ResponseEntity 타입으로 return



#### url에 인자가 들어갈 때

- controller에서 호출하는 함수의 인자로 넣어주기
  - `@PathVariable` String email
- 나머지 함수들에선 인자로 넣을 때 @Path~ 쓰지 않음!



### Spring Layer

#### 1. Controller layer

1. 클라이언트가 이용할 End point
2. 요청을 어떻게 처리할지, 어떻게 응답할지 결정

#### 2. Service layer

1. Controller와 Dao의 중간 영역에서 사용됨
2. 비즈니스 로직
   - 핵심 업무 로직 구현, 그에 관련된 데이터의 적합성 검증, 트랜잭션 처리, 다른 계층과의 통신을 위한 인터페이스 제공, 해당 계층의 객체 간 관계 관리 등

#### 3. Repository layer

1. Dao(Data Access Object)
2. Strorage에 접근해 데이터를 조작



### 좋아요 기능 구현

> 참고
> [https://kwakkwakkwak.github.io/spring/2017/12/18/Sprng-%EC%A2%8B%EC%95%84%EC%9A%94%EA%B8%B0%EB%8A%A5/](https://kwakkwakkwak.github.io/spring/2017/12/18/Sprng-좋아요기능/)



#### 1. DB 모델링

- table : review_like - 좋아요 기능을 위한 테이블
  - 좋아요를 누른 사용자와 해당 게시글 각각의 id를 저장
  - reviewLikeId - PK
  - userId(email) - FK
  - reviewId - FK

#### 2. Mapper

```java
// 좋아요 데이터 추가
<insert id="createReviewLike">
     INSERT INTO review_like (review_id, email)
     VALUES (#{reviewId}, #{email})
</insert>

// 요청 보낸 사용자의 해당 리뷰 좋아요 데이터 삭제
<delete id="deleteReviewLike">
     DELETE FROM review_like
     WHERE review_id = #{reviewId} AND email = #{email}
</delete>

// 해당 리뷰의 좋아요 개수 갱신
<select id="updateReviewLike">
    UPDATE review
    SET
    review_like_cnt = (SELECT COUNT(*)
                     FROM review_like
                     WHERE review_id = #{reviewId})
    WHERE review_id = #{reviewId}
</select>

// 해당 리뷰의 좋아요 개수 반환
<select id="getReviewLike" resultType="int">
    SELECT COUNT(review_like_id) FROM review_like WHERE review_id = #{reviewId} AND email = #{email}
</select>
```

#### 3. Dao

- session을 이용해 데이터를 조작하는 함수를 정의

#### 4. Service

- 함수를 정의하고, 그 함수에서 실행할 dao 함수 실행
- 좋아요, 좋아요 취소 함수는 그에 해당하는 함수도 실행하고, review의 like 개수를 갱신하는 함수도 실행해야 함

#### 5. Controller

- 함수를 실행할 url과 요청 받을 method를 mapping
- 좋아요 돼 있으면 delete, 안 돼 있으면 insert
- 좋아요 여부를 나타내는 변수(heart)의 값도 변경



## 2020-07-27 :sun_behind_rain_cloud:

### swagger-ui

localhost:`server port`/swagger-ui.html



### Follow 기능 구현

> 참고
> https://yeahajeong.tistory.com/102



#### Controller

```java
@Autowired
private IFollowService followService;

//personal-list 요청 : 개인 게시물 보이는 페이지
@RequestMapping(value="/{id}", method=RequestMethod.GET)
public String personalList(@PathVariable String id, Model model, HttpSession session) throws Exception {
	
	//개인 페이지 주인 정보 담기
	UsersVO user = usersService.inquiryOfUserById(id);
	//로그인한 회원 (=나) 정보 담기
	Object object = session.getAttribute("login");
	UsersVO loginUser = (UsersVO)object;
	
	//개인페이지의 유저 번호 가져오기
	int userNo = user.getUserNo();
	//로그인 회원 유저 번호 가져오기
	int loginUserNo = loginUser.getUserNo();
	logger.info("현재 페이지 주인의 번호 : " + userNo + ", 로그인 유저의 번호 : " + loginUserNo);
	
	//팔로우 객체 생성
	FollowVO follow = new FollowVO();
	follow.setActiveUser(loginUserNo); 	//하는놈
	follow.setPassiveUser(userNo);		//당하는놈
	
	//팔로우 유무 체크
	int followCheck = followService.isFollow(follow);
	logger.info("팔로우 유무 체크 : " + followCheck);

	//팔로워 리스트 -> (개인페이지에서)나를 팔로우하는 놈들 목록
	List<FollowVO> followerList = followService.selectPassiveUserList(userNo);
	//팔로잉리스트 -> (개인페이지에서) 내가 팔로우 하는 놈들 목록
	List<FollowVO> followingList = followService.selectActiveUserList(userNo);
	
	//사용자 아이디로 사용자 번호(pk)를 조회해서 그 번호로 게시물 가져오기
	model.addAttribute("post", postsService.selectPostListById(id));
	//사용자 아이디로 회원의 모든 정보 조회하기
	model.addAttribute("user", user);
	//팔로우 체크 유무
	model.addAttribute("followCheck", followCheck);
	//팔로워 리스트
	model.addAttribute("followerList", followerList);
	//팔로잉 리스트
	model.addAttribute("followingList", followingList);
	
	return "post/personal-list";
}
//게시글 작성 페이지 personal-write.jsp 요청
@RequestMapping(value="/{id}/personal-write", method=RequestMethod.GET)
public String personalWrite(@PathVariable String id, Model model, HttpSession session) throws Exception {

	//id로 회원의 모든 정보 조회
	UsersVO user = usersService.inquiryOfUserById(id);
	
	//로그인한 회원 (=나) 정보 담기
	Object object = session.getAttribute("login");
	UsersVO loginUser = (UsersVO)object;
	
	//개인페이지의 유저 번호 가져오기
	int userNo = user.getUserNo();
	//로그인 회원 유저 번호 가져오기
	int loginUserNo = loginUser.getUserNo();
	logger.info("현재 페이지 주인의 번호 : " + userNo + ", 로그인 유저의 번호 : " + loginUserNo);
	
	//팔로우 객체 생성
	FollowVO follow = new FollowVO();
	follow.setActiveUser(loginUserNo); 	//하는놈
	follow.setPassiveUser(userNo);		//당하는놈
	
	//팔로우 유무 체크
	int followCheck = followService.isFollow(follow);
	logger.info("팔로우 유무 체크 : " + followCheck);
	
	//팔로워 리스트 -> (개인페이지에서)나를 팔로우하는 놈들 목록
	List<FollowVO> followerList = followService.selectPassiveUserList(userNo);
	//팔로잉리스트 -> (개인페이지에서) 내가 팔로우 하는 놈들 목록
	List<FollowVO> followingList = followService.selectActiveUserList(userNo);
	
	//사용자 아이디로 사용자 번호(pk)를 조회해서 그 번호로 게시물 가져오기
	model.addAttribute("post", postsService.selectPostListById(id));
	//회원의 정보
	model.addAttribute("user", user);
	//팔로우 체크 유무
	model.addAttribute("followCheck", followCheck);
	//팔로워 리스트
	model.addAttribute("followerList", followerList);
	//팔로잉 리스트
	model.addAttribute("followingList", followingList);	
	
	return "post/personal-write";
}
```



## 2020-07-29 :cloud_with_rain:

### Follow

- Review List 페이지에서 리뷰 작성자를 조회할 수 있어야 함
  - review 테이블에는 작성자 nickname이 없어서 user 테이블도 참조해야 함
    - back에서 같이 넘겨줘야 하는 건가?
  - 상세 페이지 url에 들어갈 userid를 가져오기 위해 작성자 데이터 필요
  - profile page url로 넘어갈 때 프론트에서 작성자와 관련된 특정 데이터(nickname?)를 백으로 넘겨주고, 백에서 그걸 이용해서 작성자와 관련된 데이터를 가져와 다시 프론트로 넘겨주면서 profile page 나오게? 하는 건가?? 후...
  - 팔로우 상태에 따라서 버튼 다르게 보여줘야 하니까 팔로우 상태를 갱신하기 위한 그런 함수.. 필요하겠지...

#### Modeling

- `follow` 테이블
- no(pk), followerId(fk: user-userId), followingId(fk: user-userId)

#### 함수

- follow 테이블에 데이터를 insert, delete
- follower, following 몇 명인지 세는 함수?
- follower list, following list 조회하는 함수?
  - 이게 있으면 몇 명인지 세는 함수가 따로 필요 없을까?



### Like

- 아이콘 색깔을 바로바로 바꿔줄 수 있도록, 현재 로그인 된 사용자가 해당 리뷰에 좋아요를 했는지 판단하는 함수? 가 필요할까?



### Error

- Optional int parameter 'reviewid' is present but cannot be translated into a null value due to being declared as a primitive type. Consider declaring it as object wrapper for the corresponding primitive type."
  - ~~int 타입은 null 값이 들어갈 수 없어서 나는 거 같은데 해결 방법을 못 찾겠다 :cry:~~
  - userId가 String 타입으로 들어가 있어서, int 대신 String으로 받도록 해주었고, `like` table이 SQL의 like와 중복되어 에러나, table 이름을 `heart`로 바꾸니 해결됨!



### Git 폴더와 sts 연동

1. sts의 Git repositories에 깃 폴더 추가
   - Git 폴더가 이미 존재하면 Add ~
   - 아닐 땐 Clone ~
2. 해당 repository를 Import



## 2020-07-30 :cloud:

### Error

- SQL 문법에서 사용하는 단어는 Table 이름으로 사용하면 안 됨



### Like

- 하트 색을 바로바로 바꾸기 위해서 어떻게 해야 할까?
  - 프론트에서 리뷰에 관한 정보들만 리턴해줬음
  - Review Dto에 해당 사용자의 좋아요 여부를 판단하는 변수 isLike 추가
  - 흐름
    1. 리뷰 리스트를 가져옴
    2. 모든 리뷰들을 포문을 돌려서 userid reviewid를 heart 테이블을 통해 비교
    3. 비교한 결과값을 변수에 저장
-  해당 리뷰의 좋아요 개수를 보여주자!
  1. `review` table에 like_cnt 필드 추가
  2. 좋아요 기능에서 like_cnt +-1
     - Update review set like_cnt=like_cnt +-1 where reviewId=#{reviewId}
- 해결해야 할 부분
  - 좋아요를 눌러도 like_cnt가 바로 바뀌지 않고 새로고침해야 함
  - 좋아요 여부에 따라 하트 색을 바꾸는 걸 아직 구현하지 않음



## 2020-08-04 :cloud:

-  Follow 구현할 때 자꾸 Null 값을 참조한다는 에러 발생

  - 코드 곳곳에 print문을 넣어 확인하고, dao의 함수를 실행할 때 문제가 발생한다는 것을 알아냄

  - 하지만 문제는 다른 곳에 있었음...

    ```java
    @Autowired
    FollowDao followDao;
    ```

    - Dao를 선언할 때 `@Autowired`를 안 붙여놓고 있었음
    - `@Autowired` 붙여주니까 바로 해결!

  - 코치님 정말 감사합니다... :pray:



## 2020-08-05 :cloud_with_rain:

### To do

- user의 id들 다 int 타입으로 변경
  - 처음에 email을 id로 받은 부분이 있었어서 String으로 인자를 받는 함수들이 존재..
- Follow 기능 에러 디버깅
- Pagination, 무한 스크롤 공부
- 쿼리에서 user의 password 제외하고 조회하도록! 프로젝트 완성되면 에러 콘솔에 띄우지 않도록!
  - 잘못하면 털릴 수도 있다고 함 :sweat:



### User profile page

- 백에서는 url로 id를 넘겨주고, 프론트에서 보여줄 땐 url에 닉네임 넣어서?



### List 보여주는 방법

#### Back에서 데이터 넘겨주는 방식

1. 데이터를 한번에 받아와서 보여주기
   - 데이터를 한번에 프론트로 보내고, 프론트에서 나눠서 보여주기
2. 데이터를 페이지 열 때마다 받아와서 보여주기
   - 데이터의 총 개수 (총 몇 페이지)
   - 해당 페이지에 들어가는 데이터들

#### 1. Pagination

> 참고
> https://freehoon.tistory.com/112
> (데이터를 잘라서 가져오는 방식)

##### 1. Pagination 클래스 만들기

##### 2. SQL 작성

```sql
LIMIT 첫번째 row 위치, 읽어줄 개수
```



#### 2. Infinity Scrolling

> 참고
> [https://kutar37.tistory.com/entry/%EB%AC%B4%ED%95%9C-%EC%8A%A4%ED%81%AC%EB%A1%A4Infinity-Scrolling-%EC%98%88%EC%A0%9C-6%EA%B0%80%EC%A7%80](https://kutar37.tistory.com/entry/무한-스크롤Infinity-Scrolling-예제-6가지)
> (무한 스크롤 기능 구현할 때 참고)



- 마지막 예제
  - 참고 글에서 유일하게 JQuery를 사용하지 않음,,
  - 페이지네이션도 함께 구현하여, 이전에 로드됐던 걸 보고 싶을 때 페이지 번호를 클릭해 쉽게 갈 수 있음

```html
<div class="article-list" id="article-list"></div>
<ul class="article-list__pagination article-list__pagination--inactive" id="article-list-pagination"></ul>
```

```javascript
function getPageId(n) {
    return 'article-page-' + n;
}
 
function getDocumentHeight() {
    const body = document.body;
    const html = document.documentElement;
    
    return Math.max(
        body.scrollHeight, body.offsetHeight,
        html.clientHeight, html.scrollHeight, html.offsetHeight
    );
};

// 스크롤 위치 반환?
function getScrollTop() {
    return (window.pageYOffset !== undefined) ? window.pageYOffset : (document.documentElement || document.body.parentNode || document.body).scrollTop;
}

// article에 image 넣기
function getArticleImage() {
    const hash = Math.floor(Math.random() * Number.MAX_SAFE_INTEGER);
    const image = new Image;
    image.className = 'article-list__item__image article-list__item__image--loading';
    image.src = 'http://api.adorable.io/avatars/250/' + hash;
    
    image.onload = function() {
        image.classList.remove('article-list__item__image--loading');
    };
    
    return image;
}

function getArticle() {
    const articleImage = getArticleImage();
    const article = document.createElement('article');
    article.className = 'article-list__item';
    article.appendChild(articleImage);
    
    return article;
}
 
function getArticlePage(page, articlesPerPage = 16) {
    const pageElement = document.createElement('div');
    pageElement.id = getPageId(page);
    pageElement.className = 'article-list__page';
    
    while (articlesPerPage--) {
        pageElement.appendChild(getArticle());
    }
    
    return pageElement;
}
 
function addPaginationPage(page) {
    const pageLink = document.createElement('a');
    pageLink.href = '#' + getPageId(page);
    pageLink.innerHTML = page;
    
    const listItem = document.createElement('li');
    listItem.className = 'article-list__pagination__item';
    listItem.appendChild(pageLink);
    
    articleListPagination.appendChild(listItem);
    
    if (page === 2) {
        articleListPagination.classList.remove('article-list__pagination--inactive');
    }
}
 
function fetchPage(page) {
    articleList.appendChild(getArticlePage(page));
}
 
function addPage(page) {
    fetchPage(page);
    addPaginationPage(page);
}
 
const articleList = document.getElementById('article-list');
const articleListPagination = document.getElementById('article-list-pagination');
let page = 0;
 
addPage(++page);
 
window.onscroll = function() {
    if (getScrollTop() < getDocumentHeight() - window.innerHeight) return;
    addPage(++page);
};
```

![image-20200805180337281](C:\Users\kyungmin\AppData\Roaming\Typora\typora-user-images\image-20200805180337281.png)

```
└ article-list
	└ article-page-n
		└ article
			└ img
└ article-list__pagination
```

- 스크롤했을 때 감지해서 함수 실행

  - getScrollTop : `화면의 y축의 상단값`, 정의되지 않았다면 `<html>의 스크롤 바 높이`를, html이 없다면 `<body>의 부모의 스크롤 바 높이`를, 그것도 없다면 `<body>의 스크롤 바 높이`를 반환

  - getScrollTop 반환값이 문서 높이와 innerHeight(브라우저에서 틀을 제거한 진짜 내용 부분의 높이)의 차보다 작을 때

    = 스크롤했을 때 위치가 직전 화면을 벗어나지 않았을 때 return

    = 화면 밖에 있을 경우는 **addPage(fetchPage, addPaginationPage)** 함수 실행

- articleList의 자식으로 **getArticlePage**의 반환값을 추가

  - article을 자식으로 갖고 있는 div 태그
  - article은 image를 갖고 있음

- getArticleImage 함수에서 image 데이터 가져와서 넣고 반환

  - 우리 프로젝트에서도 여기 함수에서 데이터를 가져와서 넣는 함수를 만들면 될 듯!

![image-20200805173007428](C:\Users\kyungmin\AppData\Roaming\Typora\typora-user-images\image-20200805173007428.png)



## 2020-08-06 :partly_sunny: 

### Error

- follow가 있는지 확인하기 위해 FollowDto를 이용해 변수를 선언했는데, null로 선언하여 오류가 발생했고, 객체를 생성하는 방식으로 바꾸니 제대로 실행됨

  ```java
  // NullPointerException
  FollowDto follow = null;
  // 해결
  FollowDto follow = new FollowDto();
  ```

- review들을 조회할 때도 같은 에러 발생, 위와 같은 방식으로 고쳐서 해결

- 로그인 할 때 id 값이 없으면 에러 발생

  - 원래는 이메일과 비밀번호만 제대로 입력하면 실행되어야 함



## 2020-08-07 :cloud:

### Comment

- Table
  - no : int, PK
  - user_id : int, FK
  - review_id : int, FK
  - content : String, not null
  - create_date : Date
  - like_cnt : int, default 0
- Review detail 페이지에서 보여질 것



### Review detail page

> url : /review/{review_id}

- 댓글 생성 기능
- 댓글 리스트
- 댓글 내용 : content, user-nickname, create_date, like_cnt



## 2020-08-09 :cloud_with_rain:

### Comment

- url : /review/{reviewId}/create_comment/{userId}



## 2020-08-10 :cloud:

### DB 접근 제어

1. 허가된 ip에서만 로그인이 가능하게 함
2. 권한이 있지 않은 사람은 update, delete를 못하도록



### 팀 미팅

- 추천할 때 몇 % 일치하는지
  - 계산식은 나중에 정하고, 수치 먼저 입력해놓기
  - 알고리즘 어떻게 할지 계획이 정확하게 없다면 나중에 구현하는 게 맞음 (알고리즘을 어떻게 짰는지 평가하는 게 아니기 때문에..)
- 이번 주 내로 와이어프레임대로 구현



## 2020-08-14 :cloud:

### Comment - Count

- 각 review에 달린 comment의 개수를 프론트에 넘겨주고 싶음!
- like 개수 반환은 review 테이블에 like_cnt를 만들어서 like 기능이 실행될 때마다 증감시켜주었음
  - 원래는 쿼리를 이용해 like 테이블에서 해당하는 데이터의 개수를 세서 반환하고 싶었으나, 그 당시는 스프링에 대한 이해가 부족해서 구현을 못했음
  - 이후로 프로젝트를 진행하면서, 데이터를 보낼 때 테이블에 값을 넣는 게 아니라 dto에 넣어주면 된다는 것을 알게 됨
- ReviewDto에 comment_cnt 변수를 만들고, 전체 review 목록을 조회할 때 각 review에 comment 개수 구하는 쿼리를 이용해 값을 넣어줌!



## 2020-08-15 :sunny:

### Search 기능

- User, Restaurant, Review를 검색하여 찾아내는 기능

- `LIKE` 활용하면 될 듯



### 알림 기능

- 확인 안 한 정보들이 있을 때 표시하는 기능
  - 문자/카카오톡 메시지 개수 표시 같은 거
  - 내가 작성한 review에 달린 댓글을 아직 확인하지 않았을 때
  - 내가 작성한 review에 누군가 좋아요를 눌렀을 때?



## 2020-08-16 :sunny:

### Search

- `LIKE`를 사용할 때 `CONCAT`으로 와일드카드와 변수를 묶어서 작성해야 함
  - **LIKE** `CONCAT('%',#{필드명},'%')`
- user는 처음에 email과 nickname 중 하나라도 포함되면 검색되도록 하려고 했음 그러나 sns에서 email은 보통 가입할 때 활용되고 타인에게 공개되지는 않으므로 검색 기준으로 적합하지 않다고 생각해 nickname만을 기준으로 검색
- review는 content를 기준으로 검색
- restaurant는 res_type, menu, name을 기준으로 검색
  - 검색을 해도 아무 것도 반환되지 않는 문제가 발생했는데, mapper를 호출할 때 input값을 인자로 넘겨주지 않았기 때문이었음



## 2020-08-17 :sunny:

### Error

- 이미 포트를 쓰고 있어서 서버가 켜지지 않음
  - `메이븐 클린 - 업데이트 프로젝트 - 프로젝트 실행` 하니까 실행됨



### Search

- restaurant를 검색할 때 지역도 같이 검색했을 때 그 지역의 식당이 검색되도록 하고 싶으나 어려울 것 같음
  -  `역삼 타코`라고 검색하면 주소에 역삼이 들어가는 타코 관련 음식점이 나오도록
  - 띄어쓰기로 단어들 구분
  - 각 단어들이 주소에 포함돼 있는지 + 각 단어들이 업종/가게명/메뉴에 포함돼 있는지 여부 판단



### 알림 기능

> 1. 알림 내용이 있는 경우 앱 좌측 상단의 종 아이콘 옆에 표시
> 2. 종 아이콘을 클릭했을 때 어떤 알림이 있는지 확인
> 3. 알림을 클릭했을 때 관련된 페이지로 이동



#### 알림 내용

1. 본인이 쓴 review에 comment가 달렸을 경우
   - comment마다 해당 review의 작성자(userid)가 읽었는지 확인하는 변수 `check`를 만듦
   - 종 모양 버튼 눌렀을 때 확인할 알림 목록 떠야 함
2. 팔로잉이 새로운 review를 작성했을 경우



#### 구현 방식

1. comment 테이블에 is_checked 필드를 만들고(default: false) review의 userid가 해당 comment를 확인했을 때 true로 변경

   - 처음에 댓글 알림만 고려했을 때 생각했던 방법인데, 나중에 다른 내용도 알림 기능을 적용해야 할 때 확장시키기 어려울 것 같음..

2. `notice` 테이블을 생성해 알림 객체를 따로 만듦

   - `no(pk)`, `to_userid`(알림 대상), `content`(알림 내용)을 기본적인 필드로 구성하고,

   - 관련 있는 테이블의 pk를 참조하는 필드를 만들고 default로 false을 넣어버리기...?

     | no   | to_userid | content                                                 | comment | follow | ...   |
     | ---- | --------- | ------------------------------------------------------- | ------- | ------ | ----- |
     | 1    | 2         | {댓글작성자}님이 {가게명} 리뷰에 댓글을 작성하셨습니다. | true    | false  | false |
     | 2    | 3         | {팔로워}님이 팔로우하기 시작했습니다.                   | false   | true   | false |

   - content를 백에서 넣지 말고, 프론트에서 보여주는 식이 맞는 건가? 이 방식도 효율적이진 않은 것 같다.:cry:



#### 결론

- 팀원분들이 댓글 알림을 우선 구현해보는 것이 좋겠다고 조언해주었다.. 1번 방식으로 구현 시작!



#### 새 댓글 알림 목록

- url : `/notice/comment/{userId}`

- userId를 가진 user가 쓴 review에 달린 comment 목록 불러오기

  1. 결과로 반환할 List<CommentDto> comment_list 생성

     - 생성할 때 ArrayList로 생성

       ```java
       List<CommentDto> comment_list = new ArrayList<CommentDto>();
       ```

  2. userid가 userId인 review 목록 가져오기

  3. 각 review에 달린 comment 목록을 comment_list에 저장

     - `add` 메소드 이용
     - is_checked 확인해서 false인 comment만 저장

  4. comment_list 반환



#### 새 댓글 확인

- url
  1. 리뷰를 통해 댓글을 확인한 경우
     /notice/comment/{userId}/{reviewId}`
  2. 댓글을 직접 확인한 경우
     `/notice/comment/{userId}/{commentId}`

- 새 댓글을 확인했을 때(알림 메시지를 클릭했을 때), **is_checked**를 false에서 true로 변경

  ```sql
  /* 1. 리뷰 통해 확인 */
  UPDATE comment SET is_checked='1' WHERE userid=#{userId} and reviewid=#{reviewId};
  
  /* 2. 직접 확인 */
  UPDATE comment SET is_checked='1' WHERE userid=#{userId} no=#{commentId};
  ```



## 2020-08-18 :sunny:

### 0. To do

1. 새 comment 확인 기능 : is_checked를 true로 변경
2. Review detail page를 위해 review 정보 반환
3. like, follow 개수 세는 방식 변경 : comment 개수 세는 방식처럼



### 1. 새 댓글 확인

#### error

- CommentDto에 userId, reviewId가 아니라 userid, reviewid로 들어가있어서 오류가 났고, 소문자로 바꾸니 해결됨
- url의 reviewId에 해당하는 review에 달린 모든 comment의 is_checked를 1로 변경해야 하는데, 오류는 나지 않지만 제대로 실행되지 않음



#### 방식

1. reviewId로 review를 불러오기
2. 그 리뷰에 달린 comment 목록 불러오기
3. 반복문으로 각 comment들이 조건을 만족하는지 여부 판단
   - review.no=comment.reviewid
4. 조건을 만족하는 comment들의 is_checked 값 변경



### 2. Review Detail

- url : `/revivew/{reviewId}`
- 리뷰 작성자의 nickname도 같이 넘겨줌



### 3. Like, Follow - Count

- 나중에 구현



## 2020-08-19 :sunny:

### To do

- Vue로 Review 상세 페이지 만들기



### Review detail page

![Review_detail_page](C:\Users\kyungmin\git\s03p13a409\문서\Review_detail_page.png)

1. ReviewDetail.vue를 만들고, routes.js에 url과 함께 저장
2. ReviewCard.vue를 참고하여 review 상세 정보를 담을 컴포넌트를 만듦
3. comment 목록을 보여줌



### Follow list

> 내가 팔로우한 회원 목록, 나를 팔로우한 회원 목록

1. follow 테이블에서 로그인한 회원 userId에 해당하는 following, follower 목록(userid)을 가져옴
2. 각 userid로 user 테이블에서 회원 목록을 가져옴
   - password는 빈 값으로 바꿔서 넘겨줌



# :honey_pot: Tip

- `Ctrl + Shift + O` : 자동으로 import
- `Ctrl + Space bar` : 자동완성
- `Ctrl + y` : 실행 앞으로 (**Crtl + z**와 반대)
- `Ctrl + d` : 해당하는 줄 삭제
- 포트 죽이기 : 포트 이미 켜져있어서 서버가 안 켜짐
  1. cmd 창 열어서 `netstat -a -o`로 죽일 포트 번호의 `PID` 확인
  2.  `taskkill /f /pid (해당 PID)`로 포트 죽이기
- `Shift + Enter` : 다음 줄로 넘어감
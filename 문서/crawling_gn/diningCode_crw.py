
from bs4 import BeautifulSoup
import requests
from urllib.request import urlopen
import json
import time
from selenium import webdriver
from selenium.common.exceptions import NoSuchElementException
from selenium.common.exceptions import ElementNotVisibleException
from selenium.webdriver.common.keys import Keys

driver = webdriver.Chrome('chromedriver.exe')
driver.implicitly_wait(3)
search_url = "https://www.diningcode.com/"
driver.get(search_url)
#시작 팝업 제거
driver.find_element_by_xpath('//*[@id="div_popup"]/div/div[1]').click()


#데이터에서 검색할 키 불러오기
with open('서울시 강남구 일반 및 휴게음식점 식품위생업소 현황_정리(편의점제외).json','r', encoding='utf-8-sig') as f:
    data = json.load(f)
    input_text = []
    for i in range(0,2000):
        input_text.append(data[i]['업소명']) # + ' ' + data[i]['소재지지번'][0:13]
        #print(input_text)
        #print(data[i])

idx = 0
#검색 반복 과정
for i in range(1850, 2000):
    #검색 후 디테일 페이지로 넘어가기
    query = input_text[i]
    print(query)
    driver.find_element_by_xpath('//*[@id="txt_keyword"]').clear()
    driver.find_element_by_xpath('//*[@id="txt_keyword"]').send_keys(query)
    driver.implicitly_wait(1)
    driver.find_element_by_xpath('//*[@id="txt_keyword"]').send_keys(Keys.ENTER)
    time_table = "영업시간 정보가 없습니다."
    menus = "메뉴 정보가 없습니다."


    try: #검색 결과
        #첫 글 클릭
        driver.find_element_by_xpath('//*[@id="div_rn"]/ul/li[1]/a/span[1]').click()

        driver.switch_to.window(driver.window_handles[-1])

        #디테일 페이지에서 정보 저장 -> 각 정보가 있냐 없냐에 따라서 주소 달라짐. 분기 필요
        #영업 시간 찾기(있는/없는 경우)
        try: #시간 있는 경우 1
            time_table = driver.find_element_by_xpath('//*[@id="div_detail"]/div[1]/ul').text
        except NoSuchElementException: 
            pass

        #정해진 시간 출력
        data[i]['영업일'] = time_table
        #print(data[i]['영업일'])

        #메뉴 찾기(있는/없는 경우)
        try: #메뉴 1
            menus = driver.find_element_by_xpath('//*[@id="div_detail"]/div[2]/ul').text
        except NoSuchElementException:
            pass
                
        #정해진 메뉴 출력
        data[i]['메뉴'] = menus
        #print(data[i]['메뉴'])

        #활성창 변경
        driver.close()
        driver.switch_to.window(driver.window_handles[-1])

    except NoSuchElementException:
        print(data[i]['영업일'])
        print(data[i]['메뉴'])
    
    idx += 1
    if idx % 5 == 1:
        time.sleep(30)
        
    print("#{}-----------------체크 완료".format(idx))
    driver.implicitly_wait(2)

    if idx % 50 == 0:
        with open('서울시 강남구 일반 및 휴게음식점 식품위생업소 현황_정리(편의점제외).json','w', encoding='utf-8-sig') as make_file:
            json.dump(data, make_file, indent='\t', ensure_ascii = False)
            print("-----------------저장 완료")
from bs4 import BeautifulSoup
import requests
from urllib.request import urlopen
import json
import time
from selenium import webdriver
from selenium.common.exceptions import NoSuchElementException
from selenium.common.exceptions import ElementNotVisibleException

driver = webdriver.Chrome('chromedriver.exe')
driver.implicitly_wait(3)
naver_url = "https://m.map.naver.com/#/search"
driver.get(naver_url)

#데이터에서 검색할 키 불러오기
with open('서울시 강남구 일반 및 휴게음식점 식품위생업소 현황_정리(편의점제외).json','r', encoding='utf-8-sig') as f:
    data = json.load(f)
    input_text = []
    for i in range(-10,-1):
        input_text.append(data[i]['업소명'] + ' ' + data[i]['소재지지번'][0:13])
    #print(input_text)

#검색 반복 과정
for i in range(3, 4):
    #검색 후 디테일 페이지로 넘어가기
    query = input_text[i]
    print(query)
    try: #페이지에 결과 있는 경우
        driver.find_element_by_xpath('//*[@id="ct"]/div[1]/div[1]/form/div/div[2]/div/span[1]/input').clear()
        driver.find_element_by_xpath('//*[@id="ct"]/div[1]/div[1]/form/div/div[2]/div/span[1]/input').send_keys(query)
        driver.find_element_by_xpath('//*[@id="ct"]/div[1]/div[1]/form/div/div[2]/div/span[2]/button[2]').click()
        driver.find_element_by_xpath('//*[@id="ct"]/div[2]/ul/li/div[1]/a/div/strong').click()

        #디테일 페이지에서 정보 저장 -> 각 정보가 있냐 없냐에 따라서 주소 달라짐. 분기 필요
        #영업 시간 찾기(있는/없는 경우)
        try: #시간 있는 경우
            time = driver.find_element_by_xpath('//*[@id="app-root"]/div/div[2]/div[4]/div/div[1]/div/ul/li[2]/div/div/div').text
        except NoSuchElementException: #시간 없는 경우
            time = driver.find_element_by_xpath('//*[@id="app-root"]/div/div[2]/div[4]/div/div[2]/div/ul/li[2]/div/span').text
        except NoSuchElementException: #시간 없는 경우
            time = driver.find_element_by_xpath('//*[@id="app-root"]/div/div[2]/div[4]/div/div[2]/div/ul/li[2]').text            
        print(time)

        #메뉴 찾기(있는/없는 경우)
        try: #가격표 사진을 올려주세요
            menus = driver.find_element_by_xpath('//*[@id="app-root"]/div/div[2]/div[4]/div/div[1]/div/ul/li[3]/div/span').text
        except NoSuchElementException: #가격표 있는 경우
            menus = driver.find_element_by_xpath('//*[@id="app-root"]/div/div[2]/div[4]/div/div[3]/div[1]/ul').text
        else:
            menus = driver.find_element_by_xpath('//*[@id="app-root"]/div/div[2]/div[4]/div/div[3]/div/ul').text
        print(menus)
        driver.get(naver_url)

    except NoSuchElementException: #페이지에 결과 없는 경우
        print("위치 정보가 없습니다.")
        driver.get(naver_url)

    

    #새로운 검색창 열기
    driver.get(naver_url)
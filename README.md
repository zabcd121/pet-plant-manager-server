# 반려식물 관리 서비스

## 1. 주요 기능
1. 반려식물 추천
  - 실내전원용 식물 API에 있는 관리 난이도, 가격, 물주는 주기에 대한 사용자 선호를 입력받아서 코사인 유사도를 통해 적합한 반려식물을 추천해준다.

2. 식물 관리 도움
    - 식물 관리 방법 안내
    - 물줘야 할 때 알림
    - 현재 온도와 습도에 따라 반려식물 주의 알림
    
3. 반려식물 다이어리 작성

## 2, ER Diagram

<img width="719" alt="image" src="https://user-images.githubusercontent.com/68465716/196342960-4b45e16f-6934-4869-854c-3ce2af0173f1.png">

## 3. 데이터 출처
1. 농사로 - 실내정원용 식물 API
https://www.nongsaro.go.kr/portal/ps/psz/psza/contentNsMain.ps?menuId=PS03954

2. OpenWeather - Daily Forecast 16 Days API
https://openweathermap.org/forecast16

3. Kakao developers - 로컬 API
https://developers.kakao.com/docs/latest/ko/local/common

## 4. 사용된 Open API 및 데이터
1. 농사로 - 실내정원용 식물
<img width="947" alt="image" src="https://user-images.githubusercontent.com/68465716/196345008-c7444dbe-1287-4b80-b4c4-926aa1e74b68.png">

2. OpenWeather - Daily Forecast 16 Days
위도, 경도, 국가 정보와 함께 데이터 요청 시 일별로 최대 16일까지의 날씨 정보를 알려줌
<img width="938" alt="image" src="https://user-images.githubusercontent.com/68465716/196345038-487a1d7c-d937-42fd-a57f-28bc1b392d74.png">

3. Kakao Developers - 로컬 API
주소를 입력받으면 지번 주소와 도로명 주소와 함께 경도, 위도 좌표로 변환해줌
<img width="920" alt="image" src="https://user-images.githubusercontent.com/68465716/196345079-5509b56c-7e01-4a63-8dfd-3ee173ecfce8.png">

  

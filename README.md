## 🚀 EC2 배포

### 📌 Overview

- EC2의 배포하기

---
<br>

## 🔧 AWS 설정

### 1. EC2 인스턴스 시작하기
<img width="1121" height="481" alt="스크린샷 2025-11-05 오후 4 29 17" src="https://github.com/user-attachments/assets/c2439d4f-8f6f-425c-8740-9a4c00b935ed" />

🔗 [EC2 사용하기 velog](https://velog.io/@alzkdpf000/AWS-%EC%82%AC%EC%9A%A9%ED%95%B4%EB%B3%B4%EA%B8%B0-EC2)

### 2. S3, IAM 시작하기
<img width="732" height="153" alt="스크린샷 2025-11-05 오후 4 30 04" src="https://github.com/user-attachments/assets/8838f16c-12cb-4810-9474-9206d98d3461" />
<img width="1150" height="184" alt="스크린샷 2025-11-05 오후 4 29 48" src="https://github.com/user-attachments/assets/c8170339-b421-4196-89d8-df154c276c9c" />

🔗 [S3, IAM 사용하기 velog](https://velog.io/@alzkdpf000/AWS-%EC%82%AC%EC%9A%A9%ED%95%B4%EB%B3%B4%EA%B8%B0-S3-IAM)


### 3. 인바운드 설정 및 접속 

![](https://velog.velcdn.com/images/alzkdpf000/post/de3fbec3-46f3-40cf-8da2-b6f7ba7c7290/image.png)

윈도우의 경우 PuTTY, 맥의 경우 iTerm profile 설정으로 원격 접속하면 아래와 같이 나옵니다.

![](https://velog.velcdn.com/images/alzkdpf000/post/ee5b1ffd-1670-4b8f-89d9-9e1208dbd36d/image.png) 



<br>

##  🖥️ 원격 접속 후

### 1. EC2 기본 설정 및 자바 설치 
```bash
# 사용할 명령어
 sudo passwd # root 비밀번호 설정 
 1234 # 비밀번호 입력
 sudo apt update && sudo apt upgrade -y # apt 최신화
 sudo apt install -y tzdata # 한국 시간으로 바꾸기 위한 라이브러리
 sudo dpkg-reconfigure tzdata # 라이브러리 실행 도시와 나라 선택
 sudo date # 서울 선택하면 UCT -> KST로 변경
 export TZ=Asia/Seoul # 다시 한 번 설정
 sudo apt-get install openjdk-17-jdk (창 나오면 엔터 혹은 →, 엔터) # 자바 설치 17버전
vim ~/.bashrc # 환경변수 설정하기
최하단에 추가
export JAVA_HOME=$(dirname $(dirname $(readlink -f $(which java))))
export PATH=$PATH:$JAVA_HOME/bin
 source ~/.bashrc #설정한 환경 변수 적용하기
 echo $JAVA_HOME # 제대로 나오면 종료

> sudo fallocate -l 2G /swapfile
sudo chmod 600 /swapfile
> sudo mkswap /swapfile
> sudo swapon /swapfile
> echo '/swapfile none swap sw 0 0' | sudo tee -a /etc/fstab
> free -h

# 우리가 사용할 스프링부트 깃 저장소
 git clone 
 chmod +x ./gradlew # 실행 권한 쥐
 ./gradlew build # 빌드하기 
 java -jar build/libs/app.jar # 실행

 sudo apt install iptables-persistent # 이제 우리가 10000포트 적기 번거로우므로 80으로 접근하면 10000번으로 리다이렉트
 # 80은 생략하면 자동으로 80으로 가므로 생략 가능하다
 sudo iptables -t nat -A PREROUTING -p tcp --dport 80 -j REDIRECT --to-ports 10000
 # 매번 위에 명령어를 적기 번거로우므로 저장해놓기
 sudo chmod 777 /etc/iptables/rules.v4
 sudo iptables-save > /etc/iptables/rules.v4
 cat /etc/iptables/rules.v4

```




### 2. PostgreSQL 설치하기

```bash
# [Ubuntu 기본 설정]
~$ sudo passwd
	1234
~$ su root
	1234
~$ su ubuntu
~$ sudo apt update && sudo apt upgrade -y
~$ sudo apt install -y tzdata
~$ sudo dpkg-reconfigure tzdata
~$ date
~$ sudo fallocate -l 2G /swapfile
~$ sudo chmod 600 /swapfile
~$ sudo mkswap /swapfile
~$ sudo swapon /swapfile
~$ echo '/swapfile none swap sw 0 0' | sudo tee -a /etc/fstab
~$ free -h


# [PostgreSQL 설치]
~$ sudo apt install postgresql -y
~$ sudo vim /etc/postgresql/14/main/pg_hba.conf
	 :set number
	# /IPv4 local
	# 96 + Shift + g
	# IPv4 local connections의 ADDRESS를 0.0.0.0/0으로 수정
	# 인증 방식 변경
	# # "local" is for Unix domain socket connections only
	 local   all             all                                     peer
	# peer: 운영체제(우분투)의 사용자 이름과 PostgreSQL의 사용자 이름이 같아야 인증이 되는 방식
	#       암호 없이 로그인할 수 있으나 시스템 계정을 매번 만들어야 한다는 점에서 번거러움
	 local   all             all                                     scram-sha-256
	# peer -> scram-sha-256으로 변경


~$ sudo vim /etc/postgresql/14/main/postgresql.conf
	# :set number
	# /listen : listen 단어 검색
	# 60 + Shift + g : 60번 줄로 이동
	# 주석 제거 후 listen_addresses를 '*'로 수정
    
    
# PostgreSQL 재시작
~$ sudo systemctl restart postgresql

# PostgreSQL 부팅 시 자동 시작 활성화
~$ sudo systemctl enable postgresql

# PostgreSQL 상태 확인
~$ sudo systemctl status postgresql
    
 ~$ sudo -u postgres psql
```


### 3. Redis 설치하기

```bash

# [Redis 설치] 
sudo apt install redis-server -y
sudo vim /etc/redis/redis.conf
:set number # 줄번호
# :set nonumber # 줄번호 취소
# 68 # bind 127.0.0.1 ::1 -- 주석 처리
# 87 protected-mode no : 외부 접속 차단 활성화
# 236 supervised systemd : 서비스 관리자 선택, systemctl start redis-server 명령으로 관리
sudo systemctl restart redis
sudo systemctl enable redis-server
sudo systemctl status redis
sudo ufw allow 6379/tcp
```
이제 아래와 같이 인바운드 규칙 편집하면 완료
![](https://velog.velcdn.com/images/alzkdpf000/post/13cf9a40-7ad5-4b94-85b5-f4af0d0049a3/image.png)

<br>

---

## 🧩 Troubleshooting


| Issue                  | Cause               |
| ---------------------- | ------------------- |
| 빌드 실패 | 프리티어 기본 용량 부족으로 실패           |


```bash
~$ sudo fallocate -l 2G /swapfile
~$ sudo chmod 600 /swapfile
~$ sudo mkswap /swapfile
~$ sudo swapon /swapfile
~$ echo '/swapfile none swap sw 0 0' | sudo tee -a /etc/fstab
~$ free -h
```
스왑 메모리를 이용해서 RAM(주기억장치)의 부족을 보완하기 위해 디스크 공간을 임시 메모리처럼 사용하도록 만들어서 해결했습니다.



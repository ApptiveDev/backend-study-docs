# C++로 간단 웹 서버 구축하기

```c++
#include <unistd.h>
```
- 리눅스/유닉스 시스템 호출 함수

    - 파일 디스크립터 : 파일을 가리키는 정수값
    - read(), write() : 파일 디스크립터 읽기/쓰기
    - close() : 파일 디스크립터 닫기
    - fork() : 멀티프로세싱 용도
    - sleep()

```c++
#include <sys/socket.h>
```
- 소켓 관련 함수
    - socket() : 소켓 생성 후 소켓 디스크립터 반환
    - bind() : IP/포트 할당
    - listen() : 클라이언트 접속 대기
    - accept() : 접속 허용
    - send(), recv() : 데이터 송수신

```c++
#include <netinet/in.h>
```
- IP 주소 체계, 포트 등을 다루기 위한 구조체
    - sockadd_in : IPv4 주소 구조체
    - INADDR_ANY, htons(), htol()

```c++
#include <arpa/inet.h>
```
- IP 주소 변환용 함수 제공
    - inet_pton() : 문자열 IP -> 이진 IP
    - inet_ntop() : 이진 IP -> 문자열 IP
# 컴파일러 및 플래그 설정
CXX = g++
CXXFLAGS = -std=c++11 -Wall -Wextra

# 빌드 타겟
all: echo_server echo_client

# echo_server 실행 파일 빌드 규칙
echo_server: echo_server.cpp
	$(CXX) $(CXXFLAGS) -o echo_server echo_server.cpp

# echo_client 실행 파일 빌드 규칙
echo_client: echo_client.cpp
	$(CXX) $(CXXXFLAGS) -o echo_client echo_client.cpp

# 빌드 결과물 삭제 규칙
clean:
	rm -f echo_server echo_client

.PHONY: all clean
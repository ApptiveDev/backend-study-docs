#include <iostream>
#include <string>
#include <cstring>
#include <unistd.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <vector>

void error(const char *msg) {
    // 가장 최근의 오류 메시지 출력
    perror(msg);
    exit(1);
}

int main() {
    // 1. 리스닝 소켓 생성
    // AF_INET(IPv4), SOCK_STREAM(TCP), 0(기본 프로토콜)
    int sockfd = socket(AF_INET, SOCK_STREAM, 0);
    if(sockfd < 0) {
        error("ERROR opening socket");
    }
    std::cout << "1. Server socket created." << std::endl;

    // 2. 주소 바인딩 & 리슨
    struct sockaddr_in serv_addr;
    // 포트 번호 지정
    int portno = 12345;

    // 구조체 초기화
    memset(&serv_addr, 0, sizeof(serv_addr));

    serv_addr.sin_family = AF_INET; // 주소 체계 설정
    // 서버 IP 주소 지정 (127.0.0.1)
    serv_addr.sin_addr.s_addr = INADDR_ANY;
    // 서버 포트 지정
    // htons : host to network short
    serv_addr.sin_port = htons(portno);

    if (bind(sockfd, (struct sockaddr *) &serv_addr, sizeof(serv_addr)) < 0) {
        error("ERROR on binding");
    }

    std::cout << "2. 127.0.0.1:" << portno << std::endl;

    // 소켓을 수동 모드로 전환해서 연결 대기
    // 5는 대기 큐(backlog)의 크기
    listen(sockfd, 5);
    std::cout << "3. 서버 대기중.." << std::endl;

    // 클라이언트 주소 정보 저장 구조체 선언
    struct sockaddr_in cli_addr;
    socklen_t clilen = sizeof(cli_addr);

    while (true) {
        // 3. 새 연결 요청 수락
        // accept()를 통해 클라이언트 연결 요청을 수락하고
        // 통신을 위한 소겟 디스크립터를 생성한다.
        int newsockfd = accept(sockfd, (struct sockaddr *) &cli_addr, &clilen);
        if (newsockfd < 0) {
            error("ERROR on accept");
        }

        std::cout << "New connection from " << inet_ntoa(cli_addr.sin_addr)
        << ":" << ntohs(cli_addr.sin_port) << std::endl;

        // 4. 읽기/쓰기
        char buffer[256];
        
        while(true) {
            memset(buffer, 0, 256);
            
            // 클라이언트로부터 데이터 읽어오기
            // 데이터가 올 때까지 실행 멈춤(blocking)
            int n = read(newsockfd, buffer, 255);

            if (n < 0) {
                error("ERROR reading from socket");
            }
            else if (n == 0) {
                // read() == 0 -> EOF : 클라이언트가 연결 종료
                std::cout << "Client disconnected (EOF)." << std::endl;
                break;
            }

            std::cout << "Received data: " << buffer;

            // write()를 통해 클라이언트에게 데이터를 보냄
            n = write(newsockfd, buffer, strlen(buffer));

            if (n < 0) {
                error("ERROR writing to socket");
            }

            // 5. 연결 닫기
            // e.g. 'q'가 포함되면 서버가 연결을 닫는다
            if (strchr(buffer, 'q') != nullptr) {
                std::cout << "Closing connection as 'q' was received." << std::endl;
                break;
            }
        }

        // 클라이언트와 통신이 끝나면 연결 소켓 닫기
        close(newsockfd);
        std::cout << "Connection closed." << std::endl;
    }

    // 서버 실행 종료 시 리스닝 소켓 닫기 (이 예제에서는 무한 루프 때문에 도달 x)
    close(sockfd);
    return 0;
}
#include <iostream>
#include <string>
#include <cstring>
#include <unistd.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>

void error(const char *msg) {
    perror(msg);
    exit(1);
}

int main() {
    int sockfd;
    struct sockaddr_in serv_addr;
    const char* HOST = "127.0.0.1";
    int PORT = 12345;

    // 1. 소켓 생성
    sockfd = socket(AF_INET, SOCK_STREAM, 0);

    if (sockfd < 0) {
        error("ERROR opening socket");
    }

    // 2. 서버 주소 설정
    memset(&serv_addr, 0, sizeof(serv_addr));
    serv_addr.sin_family = AF_INET;
    serv_addr.sin_port = htons(PORT);

    if (inet_pton(AF_INET, HOST, &serv_addr.sin_addr) <= 0) {
        error("Invalid address/Address not supported");
    }

    // 3. 서버에 연결
    if (connect(sockfd, (struct sockaddr*)&serv_addr, sizeof(serv_addr)) < 0) {
        error("ERROR connecting");
    }

    std::cout << "Connected to server " << HOST << ":" << PORT << std::endl;
    std::cout << "Enter message to send (type 'q' to quit):" << std::endl;

    // 4. 메시지 송수신 루프
    std::string line;
    while (std::getline(std::cin, line)) {
        if (line.empty()) continue;

        line += "\n";

        int n = write(sockfd, line.c_str(), line.length());

        if (n < 0) {
            error("ERROR writing to socket");
        }

        char buffer[256];
        memset(buffer, 0, 256);
        n = read(sockfd, buffer, 255);

        if (n < 0) {
            error("ERROR reading from socket");
        }
        else if (n == 0) {
            std::cout << "Server closed the connection." << std::endl;
            break;
        }

        std::cout << "Echo from server: " << buffer;

        if (line.find('q') != std::string::npos) {
            break;
        }
    }

    // 5. 소켓 닫기
    close(sockfd);
    std::cout << "Connection closed" << std::endl;
    return 0;
}
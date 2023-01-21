# Security Management System(보안 관리 시스템)

---

### 1. Apache Kafka 설치 및 환경설정
### 1.1. 설치정보
#### 1.1.1. 설치주소
> https://kafka.apache.org/downloads
#### 1.1.2. Binary 버전으로 다운로드
> 3.3.1 Version
#### 1.1.3. C:/kafka에 압축풀기
### 1.2. Apache Kafka Properties 설정
<!--
#### 2.1. zookeeper.properties
- C:\kafk\kafka_2.12-3.3.1\config\ookeeper.properties
```
dataDir=C:\kafka\kafka_2.12-3.3.1\tmp
```
-->
- Kafka server.properties 설정
- Kafka 실행시 필요한 기본적인 Option 설정

※ 기동 중에서 변경하였다면 후 kafka server 재기동 필수
``` 
 C:\kafka\kafka_2.12-3.3.1\config\server.properties
 
 log.retention.hours=72                              # retention 시간 변경
 advertised.listeners=PLAINTEXT://localhost:9092     # 주석해제 및 host변경
 
 ############################# Custom My Option Settings #############################
 delete.topic.enable=true        #삭제 표시만 남아서 문제시 삭제가 안됨 true로 설정
 allow.auto.create.topics=false  #Topic 이 없을 시 자동생성되는 옵션 false로 꺼줌
```
- Kafka 기본 용어

| NO  | 필드             | 설명                                                                    |
|-----|----------------|-----------------------------------------------------------------------|
| 1   | TOPIC          | 소비되는 토픽의 이름                                                           |
| 2   | PARTITION      | 소비되는 파티션의 ID번호                                                        |
| 3   | CURRENT-OFFSET | 컨슈머 그룹에 의해 커밋된 이 토픽 파티션의 마지막 오프셋 해당 필드가 파티션 내부의 컨슈머의 위치               |
| 4   | LOG-END-OFFSET | 해당 토픽 파티션에 저장된 데이터의 끝을 나타내며(브로커가 관리함), 파티션에 쓰고 클러스터에 커밋된 마지막 메시지의 오프셋 |
| 5   | LAG            | 컨슈머 Current-Offset과 브로커의 Log-End-Offset 간의 차이(메시지 수)를 나타낸다.           |

### 1.3. Apache Kafka 실행(Windows 버전)
#### 1.3.1. zookeeper 실행
- Broker는 kafka의 서버를 뜻하며 동일 노드 내에서 여러개의 Broker를 띄울 수 있다.
분산되어서 여러개의 Broker가 띄워져 있으면 이 분산 Message Queue를 관리해주는 역할을
하는것이 Zookeeper이다. kafka 서버를 띄우기 앞서 Zookeeper를 반드시 띄워야 한다.
```
 cd C:\kafka\kafka_2.12-3.3.1\bin\windows
 .\zookeeper-server-start.bat C:\kafka\kafka_2.12-3.3.1\config\zookeeper.properties
 
 <종료>
 .\zookeeper-server-stop.bat C:\kafka\kafka_2.12-3.3.1\config\zookeeper.properties
```
#### 1.3.2. Kafka 실행
- kafka 서버를 실행 시킨다. 
``` 
 cd C:\kafka\kafka_2.12-3.3.1\bin\windows
 .\kafka-server-start.bat C:\kafka\kafka_2.12-3.3.1\config\server.properties
 
 <종료>
 .\kafka-server-stop.bat C:\kafka\kafka_2.12-3.3.1\config\server.properties
```
#### 1.3.3. kafka & zookeeper Port 확인
- netstat -a
- 9092: kafka, 2181 : zookeeper
```
    활성 연결
    
    프로토콜   로컬 주소           외부 주소              상태
    TCP       0.0.0.0:2181        LAPTOP-U07QL8PT:0     LISTENING
    TCP       0.0.0.0:9092        LAPTOP-U07QL8PT:0     LISTENING
      
```
### 1.4. Apache Kafka 테스트(CLI)
#### 1.4.1. Kafka Topic
- quickstart-events 토픽 생성
```
 cd C:\kafka\kafka_2.12-3.3.1\bin\windows
 ./kafka-topics.bat --create --topic quickstart-events --bootstrap-server localhost:9092
```
- Topic 리스트 보기
```
./kafka-topics.bat --bootstrap-server localhost:9092 --list
```
- Topic 제거
```
./kafka-topics.bat --bootstrap-server localhost:9092 --topic quickstart-events --delete
```
- Topic 정보보기(quickstart-events 토픽 생성 확인)
```
./kafka-topics.bat --bootstrap-server localhost:9092 --topic quickstart-events --describe
./kafka-topics.bat --bootstrap-server localhost:9092 --topic sms-events --describe
```
- Topic Offset Reset Dry Run(예상 결과값)
```
./kafka-consumer-groups.bat --bootstrap-server localhost:9092 \
--group secmngsys --topic quickstart-events \
--reset-offsets --to-earliest --dry-run

GROUP                          TOPIC                          PARTITION  NEW-OFFSET
secmngsys                      quickstart-events              0          5
```
- Topic Offset Reset 초기화
```
./kafka-consumer-groups.bat --bootstrap-server localhost:9092 \
--group secmngsys --topic quickstart-events \
--reset-offsets --to-earliest --execute

GROUP                          TOPIC                          PARTITION  NEW-OFFSET
secmngsys                      quickstart-events              0          5
```
#### 1.4.2. Kafka Consumber Group
- Group 생성
```
 ./kafka-console-consumer.bat --topic quickstart-events --group secmngsys --bootstrap-server localhost:9092
```
- Group 정보 확인
```
 ./kafka-consumer-groups.bat --bootstrap-server localhost:9092 --describe --group secmngsys
 
 GROUP           TOPIC             PARTITION  CURRENT-OFFSET  LOG-END-OFFSET  LAG             CONSUMER-ID                                           HOST            CLIENT-ID
 secmngsys       quickstart-events 0          5               5               0               console-consumer-bad418e4-457c-4078-9416-c1a6ba2671ee /192.168.245.1  console-consumer
```
- Consumer Group 목록보기
```
 ./kafka-consumer-groups.bat --bootstrap-server localhost:9092 --list
```
- Consumer Group 상태 및 Offset 정보보기
```
 ./kafka-consumer-groups.bat --bootstrap-server localhost:9092 \
 --group [그룹명] --describe
```
- Consumer Group 제거하기
```
 ./kafka-consumer-groups.bat --bootstrap-server localhost:9092 \
 --group [그룹명] --delete
```
#### 1.4.3. Kafka Producer
- Producer 실행
```
 ./kafka-console-producer.bat --topic quickstart-events --bootstrap-server localhost:9092
```
#### 1.4.4. Kafka Consumer
- Consumer 실행
```
 ./kafka-console-consumer.bat --topic quickstart-events \
 --from-beginning --bootstrap-server localhost:9092
```
#### 1.4.5. Kafka Topic Consume
- Topic Consume 해보기
```
 ./kafka-console-consumer.bat --bootstrap-server localhost:9092 \
 --from-beginning \
 --topic [토픽명]
```
- 특정 파티션만 Consume 하기
```
 ./kafka-console-consumer.bat --bootstrap-server localhost:9092 \
 --from-beginning --partition 1 \
 --topic [토픽명]
```
- 특정 consumer group 으로 consume 하기
```
 ./kafka-console-consumer.bat --bootstrap-server localhost:9092 \
 --from-beginning --partition 1 \
 --group [그룹명] \
 --topic [토픽명]
```

---
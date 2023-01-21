# Security Management System(보안 관리 시스템)

---

### 1. Apache Kafka 설치 및 환경설정
### 1.1. 설치정보
#### 1.1.1. docker-compose-kafka.yml 생성
``` 
version: '3'

    services:
        zookeeper:
        image: wurstmeister/zookeeper
        container_name: zookeeper
    ports:
     - "2181:2181"
    kafka:
        image: wurstmeister/kafka
        container_name: kafka
    ports:
     - "9092:9092"
    environment:
        KAFKA_ADVERTISED_HOST_NAME: localhost
        KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
        KAFKA_LOG_RETENTION_HOURS: 72
        KAFKA_AUTO_CREATE_TOPICS_ENABLE: false
        KAFKA_DELETE_TOPIC_ENABLE: true
        KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://localhost:9092
```  
※ environment 설정시 다음과 같은 규칙을 사용
```
log.retention.hours=72  =>  KAFKA_LOG_RETENTION_HOURS: 72
```
#### 1.1.2. docker-compose 명령어 수행
```
docker-compose -f docker-compose-kafka.yml up -d
※ 미설치시 자동으로 pull 이후 실행
```
### 1.2. Apache Kafka Properties 설정 확인
- docker-compose-kafka.yml에서 설정한 옵션이 제대로 적용 되었는지 확인
``` 
 docker exec -it kafka bash
 cd /opt/kafka_2.13-2.8.1/config
 vi server.properties
```
- Kafka 기본 용어

| NO  | 필드             | 설명                                                                    |
|-----|----------------|-----------------------------------------------------------------------|
| 1   | TOPIC          | 소비되는 토픽의 이름                                                           |
| 2   | PARTITION      | 소비되는 파티션의 ID번호                                                        |
| 3   | CURRENT-OFFSET | 컨슈머 그룹에 의해 커밋된 이 토픽 파티션의 마지막 오프셋 해당 필드가 파티션 내부의 컨슈머의 위치               |
| 4   | LOG-END-OFFSET | 해당 토픽 파티션에 저장된 데이터의 끝을 나타내며(브로커가 관리함), 파티션에 쓰고 클러스터에 커밋된 마지막 메시지의 오프셋 |
| 5   | LAG            | 컨슈머 Current-Offset과 브로커의 Log-End-Offset 간의 차이(메시지 수)를 나타낸다.           |

### 1.3. Apache Kafka & Zookeeper 실행
- Broker는 kafka의 서버를 뜻하며 동일 노드 내에서 여러개의 Broker를 띄울 수 있다.
분산되어서 여러개의 Broker가 띄워져 있으면 이 분산 Message Queue를 관리해주는 역할을
하는것이 Zookeeper이다. kafka 서버를 띄우기 앞서 Zookeeper를 반드시 띄워야 한다.
```
 docker-compose -f docker-compose-kafka.yml restart
 docker-compose -f docker-compose-kafka.yml start
 docker-compose -f docker-compose-kafka.yml stop
 
 <yml 설정 변경시 아래 명령어로 갱신 해줘야 됨>
 docker-compose -f docker-compose-kafka.yml up -d
```
#### 1.3.3. kafka & zookeeper Port 및 상태 확인
- 9092: kafka, 2181 : zookeeper
```
❯ docker ps

CONTAINER ID   IMAGE                    COMMAND                  CREATED         STATUS         PORTS                                                NAMES
9487f68c6506   wurstmeister/kafka       "start-kafka.sh"         8 minutes ago   Up 8 minutes   0.0.0.0:9092->9092/tcp                               kafka
a90fef597476   mariadb                  "docker-entrypoint.s…"   33 hours ago    Up 4 hours     0.0.0.0:3306->3306/tcp                               mariadb
381738deeb55   wurstmeister/zookeeper   "/bin/sh -c '/usr/sb…"   34 hours ago    Up 8 minutes   22/tcp, 2888/tcp, 3888/tcp, 0.0.0.0:2181->2181/tcp   zookeeper
      
```
### 1.4. Apache Kafka 테스트(CLI)
#### 1.4.1. Kafka Topic
- quickstart-events 토픽 생성
```
 cd /opt/kafka_2.13-2.8.1/bin
 ./kafka-topics.sh --create --topic quickstart-events --bootstrap-server localhost:9092
```
- Topic 리스트 보기
```
./kafka-topics.sh --bootstrap-server localhost:9092 --list
```
- Topic 제거
```
./kafka-topics.sh --bootstrap-server localhost:9092 --topic quickstart-events --delete
```
- Topic 정보보기(quickstart-events 토픽 생성 확인)
```
./kafka-topics.sh --bootstrap-server localhost:9092 --topic quickstart-events --describe
./kafka-topics.sh --bootstrap-server localhost:9092 --topic sms-events --describe
```
- Topic Offset Reset Dry Run(예상 결과값)
```
./kafka-consumer-groups.sh --bootstrap-server localhost:9092 \
--group secmngsys --topic quickstart-events \
--reset-offsets --to-earliest --dry-run

GROUP                          TOPIC                          PARTITION  NEW-OFFSET
secmngsys                      quickstart-events              0          5
```
- Topic Offset Reset 초기화
```
./kafka-consumer-groups.sh --bootstrap-server localhost:9092 \
--group secmngsys --topic quickstart-events \
--reset-offsets --to-earliest --execute

GROUP                          TOPIC                          PARTITION  NEW-OFFSET
secmngsys                      quickstart-events              0          5
```
#### 1.4.2. Kafka Consumber Group
- Group 생성
```
 ./kafka-console-consumer.sh --topic quickstart-events --group secmngsys --bootstrap-server localhost:9092
```
- Group 정보 확인
```
 ./kafka-consumer-groups.sh --bootstrap-server localhost:9092 --describe --group secmngsys
 
 GROUP           TOPIC             PARTITION  CURRENT-OFFSET  LOG-END-OFFSET  LAG             CONSUMER-ID                                           HOST            CLIENT-ID
 secmngsys       quickstart-events 0          5               5               0               console-consumer-bad418e4-457c-4078-9416-c1a6ba2671ee /192.168.245.1  console-consumer
```
- Consumer Group 목록보기
```
 ./kafka-consumer-groups.sh --bootstrap-server localhost:9092 --list
```
- Consumer Group 상태 및 Offset 정보보기
```
 ./kafka-consumer-groups.sh --bootstrap-server localhost:9092 \
 --group [그룹명] --describe
```
- Consumer Group 제거하기
```
 ./kafka-consumer-groups.sh --bootstrap-server localhost:9092 \
 --group [그룹명] --delete
```
#### 1.4.3. Kafka Producer
- Producer 실행
```
 ./kafka-console-producer.sh --topic quickstart-events --bootstrap-server localhost:9092
```
#### 1.4.4. Kafka Consumer
- Consumer 실행
```
 ./kafka-console-consumer.sh --topic quickstart-events \
 --from-beginning --bootstrap-server localhost:9092
```
#### 1.4.5. Kafka Topic Consume
- Topic Consume 해보기
```
 ./kafka-console-consumer.sh --bootstrap-server localhost:9092 \
 --from-beginning \
 --topic [토픽명]
```
- 특정 파티션만 Consume 하기
```
 ./kafka-console-consumer.sh --bootstrap-server localhost:9092 \
 --from-beginning --partition 1 \
 --topic [토픽명]
```
- 특정 consumer group 으로 consume 하기
```
 ./kafka-console-consumer.sh --bootstrap-server localhost:9092 \
 --from-beginning --partition 1 \
 --group [그룹명] \
 --topic [토픽명]
```

---
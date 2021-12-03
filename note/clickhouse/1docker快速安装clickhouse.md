- 创建docker-compose.yml 

  ```yaml
  version: '3'
  
  services:
    ch_server:
      image: yandex/clickhouse-server
      ports:
        - "8123:8123"
      volumes:
        - ./db:/var/lib/clickhouse
      networks:
        - ch_ntw
  
    ch_client:
      image: yandex/clickhouse-client
      entrypoint:
        - /bin/sleep
      command:
        - infinity
      networks:
        - ch_ntw
  
  networks:
    ch_ntw:
      driver: bridge
      ipam:
        config:
          - subnet: 10.222.1.0/24
  ```

- 启动clickhouse服务

  ```
  docker-compose up -d
  ```

- 进入clickhouse客户端

  ```
  docker-compose exec ch_server clickhouse-client
  ```
```zsh
$ curl -X POST http://localhost:8084/api/kafka-connect-1/connectors \
      -H 'content-type: application/json' \
      -d @'source.template.json'
```
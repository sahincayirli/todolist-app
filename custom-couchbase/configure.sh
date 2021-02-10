set -m

/entrypoint.sh couchbase-server &

sleep 15

curl -v -X POST http://127.0.0.1:8091/pools/default -d memoryQuota=512 -d indexMemoryQuota=512

curl -v http://127.0.0.1:8091/node/controller/setupServices -d services=kv%2cn1ql%2Cindex

curl -v http://127.0.0.1:8091/settings/web -d port=8091 -d username=$USERNAME -d password=$PASSWORD

curl -i -u $USERNAME:$PASSWORD -X POST http://127.0.0.1:8091/settings/indexes -d 'storageMode=memory_optimized'

curl -v -u $USERNAME:$PASSWORD -X POST http://127.0.0.1:8091/pools/default/buckets -d name=todo -d bucketType=couchbase -d ramQuotaMB=128 -d authType=sasl -d saslPassword=todo_PASSWORD

curl -v -u $USERNAME:$PASSWORD -X POST http://127.0.0.1:8091/pools/default/buckets -d name=user -d bucketType=couchbase -d ramQuotaMB=128 -d authType=sasl -d saslPassword=todo_PASSWORD

fg 1
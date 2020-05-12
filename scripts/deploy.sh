#!/usr/bin/env bash

mvn clean package

echo 'Copy file ...'

scp target/GhostSite-1.0-SNAPSHOT.jar \
    ghost@192.168.0.2:/home/ghost/

echo 'Restart server...'
ssh ghost@192.168.0.2 <<EOF

  cp GhostSite-1.0-SNAPSHOT.jar www/
  cd www/
  pgrep java | xargs kill -9
  nohup java -jar GhostSite-1.0-SNAPSHOT.jar > log.txt &

EOF

echo 'Bye!'
jmxclient
=========

# Build

```
$ sbt
sbt> docker:stage
sbt> docker:publishLocal
sbt> exit
$ docker tag jmxclient:0.0.1-SNAPSHOT testreporter:0.0.1-SNAPSHOT
```

# Kubernetes

## minikube

```
$ minikube start
$ kubectl apply -f jmxmonitor.yaml
$ kubectl logs jmxmonitor reporter
```

## describe

```
$ kubectl describe pods jmxmonitor
```

## delete

```
$ kubectl delete pod jmxmonitor
```

# Docker

## Tomcat

```
$ docker run \
  -e "CATALINA_OPTS=-Dcom.sun.management.jmxremote -Djava.rmi.server.hostname=0.0.0.0 -Dcom.sun.management.jmxremote.port=9010 -Dcom.sun.management.jmxremote.rmi.port=9010 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.local.only=false" \
  -p 8080:8080 -p 9010:9010 \
  -d \
  --rm \
  tomcat:9-jre11
```

## Reporter

```
$ export AWS_ACCESS_KEY_ID=<YOUR ACCESS KEY ID>
$ export AWS_SECRET_ACCESS_KEY=<YOUR SECRET ACCESS KEY>
$ docker run --rm -e AWS_ACCESS_KEY_ID -e AWS_SECRET_ACCESS_KEY jmxclient:0.0.1-SNAPSHOT
```
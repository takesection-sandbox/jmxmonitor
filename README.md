jmxclient
=========

# Build

```
$ sbt
sbt> docker:stage
sbt> docker:publishLocal
```

# Run

```
$ export AWS_ACCESS_KEY_ID=<YOUR ACCESS KEY ID>
$ export AWS_SECRET_ACCESS_KEY=<YOUR SECRET ACCESS KEY>
$ docker run -e AWS_ACCESS_KEY_ID -e AWS_SECRET_ACCESS_KEY jmxclient:0.0.1-SNAPSHOT
```
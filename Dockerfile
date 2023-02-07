FROM openjdk:8-jre

WORKDIR /data/br

COPY collector-core/target/OpenRUM-collector.jar OpenRUM-collector.jar

COPY collector-core/target/classes/bootstrap.yml /data/br/conf/bootstrap.yml

RUN mkdir -p /data/br/logs

ENV JAVA_OPTS="\
-server \
-Xms2048m \
-Xmx2048m \
-XX:+UseG1GC \
-XX:ParallelGCThreads=6 \
-XX:InitiatingHeapOccupancyPercent=50 \
-XX:+ClassUnloadingWithConcurrentMark \
-XX:+ParallelRefProcEnabled \
-XX:+UseStringDeduplication \
-XX:+PrintGC \
-XX:+HeapDumpOnOutOfMemoryError \
-XX:HeapDumpPath=/data/br/logs/heapdump \
-Xloggc:/data/br/logs/gc_collector.log"

ENTRYPOINT java ${JAVA_OPTS} -jar OpenRUM-collector.jar

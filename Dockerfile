FROM eclipse-temurin:21-jdk

RUN apt-get update -qq && \
    apt-get install -y -qq git openssh-server && \
    echo 'root:Hello@123' | chpasswd && \
    sed -i 's/#PermitRootLogin prohibit-password/PermitRootLogin yes/' /etc/ssh/sshd_config && \
    mkdir -p /var/run/sshd && \
    rm -rf /var/lib/apt/lists/*

WORKDIR /root/project
CMD ["tail", "-f", "/dev/null"]
COPY . /app

EXPOSE 8082 2222

ENTRYPOINT ["/entrypoint.sh"]
FROM jenkins/jenkins:lts

USER root

# Instalacja docker-compose, curl, certów
RUN apt-get update && \
    apt-get install -y curl ca-certificates openssl && \
    curl -L "https://github.com/docker/compose/releases/latest/download/docker-compose-$(uname -s)-$(uname -m)" \
         -o /usr/local/bin/docker-compose && \
    chmod +x /usr/local/bin/docker-compose

# Kopiowanie certyfikatu Gmaila
COPY gmail.crt /usr/local/share/ca-certificates/gmail.crt

# Dodanie certyfikatu do systemu i do Java
RUN update-ca-certificates && \
    keytool -import -alias gmailSMTP \
           -keystore "$JAVA_HOME/lib/security/cacerts" \
           -file /usr/local/share/ca-certificates/gmail.crt \
           -storepass changeit -noprompt

USER jenkins

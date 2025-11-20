#!/bin/bash

JAVA_REPO="https://github.com/cyberbeef-projeto/looca-api-monitoramento.git"
JAVA_DIR="$HOME/captura-java"
JAVA_PROJ_DIR="$JAVA_DIR/looca-api-prod"
JAR_PATH="$JAVA_PROJ_DIR/target/looca-1.0-SNAPSHOT.jar"
LOG_FILE="$HOME/java_app.log"

# === FUNÇÃO: FINALIZAR JAVA ===
encerrar_java() {
    echo
    echo "Encerrando aplicação Java..."
    [ ! -z "$JAVA_PID" ] && kill "$JAVA_PID" 2>/dev/null
    exit 0
}

# Captura Ctrl + C
trap encerrar_java SIGINT

# === FUNÇÃO: VERIFICAR DEPENDÊNCIAS ===
verificar_dependencias() {
    sudo apt update -y
    sudo apt install openjdk-21-jdk maven -y
}

# === FUNÇÃO: CLONAR OU ATUALIZAR PROJETO JAVA ===
baixar_projeto_java() {
    if [ -d "$JAVA_DIR" ]; then
        echo "Atualizando repositório Java..."
        cd "$JAVA_DIR" && git pull
    else
        echo "Clonando repositório Java..."
        git clone "$JAVA_REPO" "$JAVA_DIR"
    fi
}

# === FUNÇÃO: CONSTRUIR E RODAR JAVA ===
rodar_java() {
    cd "$JAVA_PROJ_DIR" || exit
    mvn clean install -DskipTests
    if [ -f "$JAR_PATH" ]; then
        echo "Rodando aplicação Java..."
        java -jar "$JAR_PATH" > "$LOG_FILE" 2>&1 &
        JAVA_PID=$!
    else
        echo "Erro: JAR não encontrado!"
        exit 1
    fi
}

# === FUNÇÃO: MONITORAR LOG ===
monitorar_log() {
    echo "Monitorando log Java (Ctrl + C para parar):"
    tail -f "$LOG_FILE"
}

verificar_dependencias
baixar_projeto_java
rodar_java
monitorar_log

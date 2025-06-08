pipeline {
    agent any

    environment {
        VERSION = "v${env.BUILD_NUMBER}"
    }

    stages {

        stage('CLEANUP') {
            steps {
                echo "\n====================================="
                echo "ETAP: CLEANUP"
                echo "Usuwanie poprzednich kontenerów, jeśli istnieją"
                echo "====================================="
                script {
                    sh 'docker-compose down || true'
                }
            }
        }

        stage('INITIALIZE') {
            steps {
                echo "\n====================================="
                echo "ETAP: INITIALIZE"
                echo "Konfiguracja środowiska i wersji buildu"
                echo "====================================="
                script {
                    def dockerHome = tool 'papaja-docker'
                    env.PATH = "${dockerHome}/bin:${env.PATH}"
                    echo "Build version: ${VERSION}"
                }
            }
        }

        stage('CHECKOUT') {
            steps {
                echo "\n====================================="
                echo "ETAP: CHECKOUT"
                echo "Pobieranie kodu źródłowego z repozytorium"
                echo "====================================="
                git branch: 'main', url: 'https://github.com/7UL0/papaja.git'
            }
        }

        stage('BUILD JAR') {
            steps {
                echo "\n====================================="
                echo "ETAP: BUILD JAR"
                echo "Budowanie aplikacji Spring Boot przy użyciu Mavena"
                echo "====================================="
                sh 'chmod +x mvnw || true'
                sh './mvnw clean package -DskipTests'
            }
        }

        stage('FREE PORTS') {
            steps {
                echo "\n====================================="
                echo "ETAP: FREE PORTS"
                echo "Sprawdzanie i uwalnianie portów: 8088, 5432"
                echo "====================================="
                script {
                    def ports = ['8088', '5432']
                    ports.each { port ->
                        def containerId = sh(
                            script: "docker ps --filter 'publish=${port}' --format '{{.ID}}' || true",
                            returnStdout: true
                        ).trim()

                        if (containerId) {
                            echo "Port ${port} używany przez kontener ${containerId}, zatrzymuję..."
                            sh "docker kill ${containerId} || true"
                            sh "docker rm ${containerId} || true"
                        } else {
                            echo "Port ${port} jest wolny"
                        }
                    }
                }
            }
        }

        stage('BUILD IMAGE') {
            steps {
                echo "\n====================================="
                echo "ETAP: BUILD IMAGE"
                echo "Budowanie obrazu Docker z aplikacją"
                echo "====================================="
                sh "docker build -t papaja-app:${VERSION} ."
            }
        }

        stage('TESTS') {
            steps {
                echo "\n====================================="
                echo "ETAP: TESTS"
                echo "Testy jednostkowe (placeholder)"
                echo "====================================="
                echo 'Tests coming soon...'
            }
        }

        stage('DEPLOY') {
            steps {
                echo "\n====================================="
                echo "ETAP: DEPLOY"
                echo "Uruchamianie aplikacji z Docker Compose"
                echo "====================================="
                script {
                    sh 'docker-compose up --build -d'
                }
            }
        }
    }

    post {
        success {
            echo "\n====================================="
            echo "BUILD SUCCESS"
            echo "Zakończono pomyślnie z wersją ${VERSION}"
            echo "====================================="

            mail to: 'papaja822@gmail.com',
                 subject: "Build SUCCESS: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                 body: """\
    Gratulacje! Build zakończony sukcesem.

    Podgląd aplikacji:
    http://localhost:8088

    Zobacz szczegóły w Jenkinsie:
    ${env.BUILD_URL}
    """
        }

        failure {
            echo "\n====================================="
            echo "BUILD FAILURE"
            echo "Czyszczenie kontenerów i rollback"
            echo "====================================="
            sh 'docker-compose down || true'
            mail to: 'juliuszparchem@gmail.com',
                 subject: "Build FAILURE: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                 body: "Build nie powiódł się\n\nSprawdź logi: ${env.BUILD_URL}"
        }
    }
}

pipeline {
    agent any

    environment {
        VERSION = "v${env.BUILD_NUMBER}"
    }

    stages {
        stage('Clean up before deploy') {
            steps {
                script {
                    sh 'docker-compose down || true'
                }
            }
        }

        stage('Initialize') {
            steps {
                script {
                    def dockerHome = tool 'papaja-docker'
                    env.PATH = "${dockerHome}/bin:${env.PATH}"
                    echo "Build version: ${VERSION}"
                }
            }
        }

        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/7UL0/papaja.git'
            }
        }

        stage('Build JAR with Maven') {
            steps {
                sh 'chmod +x mvnw || true'
                sh './mvnw clean package -DskipTests'
            }
        }

        stage('Free up used ports') {
            steps {
                script {
                    echo "\n====================================="
                    echo "🔍 CHECKING & FREEING USED PORTS"
                    echo "====================================="

                    def portsToCheck = ['8088', '55432']
                    portsToCheck.each { port ->
                        def output = sh(script: "lsof -i :${port} -t || true", returnStdout: true).trim()
                        if (output) {
                            echo "⚠️ Port ${port} is in use by PID(s): ${output}"
                            def containers = sh(script: "docker ps -q --filter 'publish=${port}'", returnStdout: true).trim()
                            if (containers) {
                                echo "🔨 Killing container(s) using port ${port}: ${containers}"
                                sh "docker kill ${containers} || true"
                                sh "docker rm ${containers} || true"
                            } else {
                                echo "⚠️ No Docker container found, but process still using port ${port}"
                            }
                        } else {
                            echo "✅ Port ${port} is free"
                        }
                    }
                }
            }
        }

        stage('Build Docker image') {
            steps {
                script {
                    sh "docker build -t papaja-app:${VERSION} ."
                }
            }
        }

        stage('Run tests') {
            steps {
                echo 'Tests coming soon...'
            }
        }

        stage('Deploy with Docker Compose') {
            steps {
                script {
                    sh 'docker-compose up --build -d'
                    sh 'docker-compose down'
                }
            }
        }
    }

    post {
        success {
            echo "Build and deploy succeeded with version ${VERSION}"
            mail to: 'papaja822@gmail.com',
                 subject: "Build SUCCESS: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                 body: "Gratulacje! Build zakończony sukcesem.\n\nSprawdź: ${env.BUILD_URL}"
        }
        failure {
            echo 'Build failed. Rolling back...'
            sh 'docker-compose down || true'
            mail to: 'juliuszparchem@gmail.com',
                 subject: "Build FAILURE: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                 body: "Build nie powiódł się \n\nSprawdź logi: ${env.BUILD_URL}"
        }
    }
}

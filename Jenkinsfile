pipeline {
    agent any

    environment {
        VERSION = "v${env.BUILD_NUMBER}"
    }

    stages {
        stage('Initialize') {
            steps {
                echo "Build version: ${VERSION}"
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

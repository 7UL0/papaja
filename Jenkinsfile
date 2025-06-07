pipeline {
    agent any

    environment {
        VERSION = "v${env.BUILD_NUMBER}"
    }

    stages {
        stage('Initialize') {
            steps {
                script {
                    def dockerHome = tool 'papaja-docker'
                    env.PATH = "${dockerHome}/bin:${env.PATH}"
                }
            }
        }

        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/7UL0/papaja.git'
            }
        }

        stage('Build Docker image') {
            steps {
                script {
                    sh 'docker build -t papaja-app:${VERSION} .'
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
        failure {
            echo 'Build failed. Rolling back...'
            sh 'docker-compose down || true'
        }
        success {
            echo "Build and deploy succeeded with version ${VERSION}"
        }
    }
}

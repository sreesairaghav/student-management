pipeline {

    agent any

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Maven Build & Test') {
        steps {
            bat '"C:\\Program Files\\Apache\\Maven\\apache-maven-3.9.16\\bin\\mvn.cmd" clean package'
            }
        }

        stage('Docker Build') {
            steps {
                bat 'docker build -t student-management:1.0 .'
            }
        }

        stage('Docker Deploy') {
            steps {
                bat 'docker rm -f student-app || exit 0'
                bat 'docker run -d --name student-app -p 8083:8082 student-management:1.0'
            }
        }
    }
}
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
                withMaven(maven: 'Maven-3.9.16') {
                    bat 'mvn clean package'
                }
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
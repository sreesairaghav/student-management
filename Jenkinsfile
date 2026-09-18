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
                bat '"C:/Users/SSR/AppData/Local/Programs/Docker/DockerDesktop/resources/bin/docker.exe" build -t student-management:1.0 .'
            }
        }

        stage('Docker Deploy') {
            steps {
                bat '"C:/Users/SSR/AppData/Local/Programs/Docker/DockerDesktop/resources/bin/docker.exe" rm -f student-app || exit 0'
                bat '"C:/Users/SSR/AppData/Local/Programs/Docker/DockerDesktop/resources/bin/docker.exe" run -d --name student-app -p 8083:8082 student-management:1.0'
            }
        }
    }
}
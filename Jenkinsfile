
pipeline {
    agent any

    options {
        buildDiscarder(logRotator(numToKeepStr: '10'))
        timeout(time: 30, unit: 'MINUTES')
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build & Test') {
            steps {
                bat '''
                    set "JAVA_HOME=C:\\Program Files\\Java\\jdk-25"
                    set "PATH=%JAVA_HOME%\\bin;%PATH%"
                    "C:\\Program Files\\Apache\\Maven\\apache-maven-3.9.16\\bin\\mvn.cmd" clean package
                '''
            }

            post {
                always {
                    junit allowEmptyResults: true,
                          testResults: 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Archive Artifacts') {
            steps {
                archiveArtifacts artifacts: 'target/*.jar',
                                 fingerprint: true
            }
        }
    }

    post {
        always {
            cleanWs()
        }

        success {
            echo "Build successful for branch: ${env.BRANCH_NAME}"
        }

        failure {
            echo "Build failed for branch: ${env.BRANCH_NAME}"
        }
    }
}

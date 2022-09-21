def maindir="."
def deployHost="192.168.158.142"
def dockerUser="jihundockerdev"
def dockerRepo="fintech-arena-be"
def imageTag="1.0"

def dockerPasswd="jihundockerdev"  // 절대 노출 금지

pipeline {
    agent any

    tools {
        jdk 'jdk17-arena'
    }

    stages {
        stage('Pull Codes') {
            steps {
                checkout scm
            }
        }
        stage('Build Codes') {
            steps {
                sh """
                cd ${maindir}
                ./gradlew clean build
                """
            }
        }
        stage('Build Docker Image & Push to Docker Hub') {
            steps {
                sh """
                ./gradlew jib -Djib.to.auth.username=${dockerUser} -Djib.to.auth.password=${dockerPasswd} -Djib.to.image=${dockerUser}/${dockerRepo}:${imageTag} -Djib.console='plain'
                """
            }
        }
        stage('Deploy to CD server') {
            steps {
                sshagent(credentials : ["jenkins-cd-ssh"]) {
                    sh "ssh deployer@${deployHost} \
                    'sleep 3; \
                    docker stop `docker container ps -q -f ancestor=${dockerUser}/${dockerRepo}:${imageTag}`; \
                    docker pull ${dockerUser}/${dockerRepo}:${imageTag}; \
                    docker run -d -p 60000:8080 -t ${dockerUser}/${dockerRepo}:${imageTag};'"
                }
            }
        }
    }
}
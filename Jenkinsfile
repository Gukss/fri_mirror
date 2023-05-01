pipeline {
    agent any
    environment {
       FRONT_CONTAINER_NAME="fri_front_container"
       FRONT_NAME = "fri_front"
    }
    stages {
        stage('Clean'){
            steps{
                script {
                    try{
                        sh "docker stop ${FRONT_CONTAINER_NAME}"
                        sleep 1
                        sh "docker rm ${FRONT_CONTAINER_NAME}"
                    }catch(e){
                        sh 'exit 0'
                    }
                }
            }
        }
        stage('Build') {
            steps {
                script{
                    sh "docker build -t ${FRONT_NAME} ./fri/."
                }
            }
        }
        stage('Docker run') {
            steps {
                sh "docker run -d --name=${FRONT_CONTAINER_NAME} -p 3000:80 ${FRONT_NAME}"
                sh "docker image prune --force" 
            }
        }
    }
}

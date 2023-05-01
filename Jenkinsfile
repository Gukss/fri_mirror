pipeline{
    agent any
    environment {
       BACK_SPRING_CONTAINER_NAME="fri_back_spring_container"
       BACK_SPRING_NAME = "fri_back_spring"
    }
    stages {
        stage('Clean'){
            steps{
                script {
                    try{
                        sh "docker stop ${BACK_SPRING_CONTAINER_NAME}"
                        sleep 1
                        sh "docker rm ${BACK_SPRING_CONTAINER_NAME}"
                    }catch(e){
                        sh 'exit 0'
                    }
                }
            }
        }
        stage('Build') {
            steps {
                script{
                     sh "docker build -t ${BACK_SPRING_NAME} ./fri/."
                }
            }
        }
        stage('Deploy'){
            steps {  
                sh "docker run -d --name=${BACK_SPRING_CONTAINER_NAME} -p 8080:8080 -e JAVA_OPTS=-Djasypt.encryptor.password=hellofri -v ~/share:/usr/bin/ ${BACK_SPRING_NAME}"
                //sh "docker run -d --name=${BACK_SPRING_CONTAINER_NAME} -p 8080:8080 ${BACK_SPRING_NAME}"
                sh "docker image prune --force"
            }
        }
    }
}

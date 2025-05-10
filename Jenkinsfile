pipeline{
    agent any

    tools {
     jdk 'jdk 17'
     maven 'maven3'
    }

    environment{
        SCANNER_HOHE= tool 'sonar-scanner'
    }

    stages{
        stage("Checkout from SCM"){
            steps {
                git branch: 'master', url: 'https://github.com/Srinu-rj/ncpl-dem0.git'
            }
        }

        stage('Compile') {
            steps {
                sh "mvn compile"
            }
        }

        stage('Test') {
            steps {
                sh "mvn test"
            }
        }

        stage('SonarQube-Analysis') {
            steps {
                script {
                 echo "sonarqube code analysis"
                 withSonarQubeEnv(credentialsId: 'sonar-token') {
                     sh ''' $SCANNER_HOHE/bin/sonar-scanner -Dsonar.projectName=spring-application-with-mysql-one  -Dsonar.projectKey=spring-application-with-mysql-one \
                     -Dsonar.java.binaries=. '''
                     echo "End of sonarqube code analysis"

                   }
                }
            }
        }

        stage('Build') {
            steps {
               sh "mvn package"
            }
        }

        stage('Build & Tag Docker Image') {
            steps {
               script {
                   withDockerRegistry(credentialsId: 'docker-cred', toolName: 'docker') {

                            sh '''
                            docker build -t ci-cd-demo .
                            docker tag ci-cd-demo srinu641/ci-cd-demo:V6.0
                            docker push srinu641/ci-cd-demo:V6.0
                            docker images
                            '''
//                             sh "docker build -t ci-cd-demo ."
//                             sh "docker tag ci-cd-demo srinu641/ci-cd-demo:V6.0"
//                             sh "docker push srinu641/ci-cd-demo:V6.0"
                    }
               }
            }
        }

    }


}
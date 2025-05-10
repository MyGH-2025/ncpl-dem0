pipeline{
    agent any

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

        stage('SonarQube Analsyis') {
            steps {
                withSonarQubeEnv('sonar-token') {
                    sh ''' $SCANNER_HOME/bin/sonar-scanner Dsonar.projectName=BoardGame -Dsonar.projectKey=BoardGame \
                            -Dsonar.java.binaries=. '''
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
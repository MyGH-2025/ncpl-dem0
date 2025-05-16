pipeline {
    agent any

    environment{
        SCANNER_HOHE= tool 'sonar-scanner'
    }

    tools {
     jdk 'jdk 17'
     maven 'maven3'
    }

    stages {
        stage('Git CkecOut') {
            steps {
                echo 'Git Check Out Done'
                git branch: 'master', url: 'https://github.com/Srinu-rj/ncpl-dem0.git'
            }
        }

        stage('Maven Integration Test') {
            steps {
                echo 'Git Check Out Done'
                 sh "mvn clean install -DskipTests=true"
            }
        }

        stage('Maven Compile') {
            steps {
                sh "mvn compile"
            }
        }

        stage('SonarQube-Analysis') {
            steps {
                script {
                 echo "sonarqube code analysis"
                 withSonarQubeEnv(credentialsId: 'sonar-token') {
                     sh ''' $SCANNER_HOHE/bin/sonar-scanner -Dsonar.projectName=spring-application-with  -Dsonar.projectKey=spring-application-with \
                     -Dsonar.java.binaries=. '''
                     echo "End of sonarqube code analysis"

                   }
                }
            }
        }

        // stage('Quality Gate') {
        //     steps {
        //         script {
        //           echo "sonarqube Quality Gate"
        //           waitForQualityGate abortPipeline: false, credentialsId: 'sonar-token'
        //           echo "End Sonarqube Quality Gate"

        //         }
        //     }
        // }
        stage('Upload jar file to Nexus'){
            steps{
                script{
                     echo "--> Nexus started <--"
                    nexusArtifactUploader artifacts:
                     [
                       [
                         artifactId: 'ncpl-dem0',
                         classifier: '',
                         file: 'target/ci-cd-demo.jar',
                         type: 'jar'
                         ]
                      ],
                       credentialsId: 'nexus-cred',
                       groupId: 'com.example',
                       nexusUrl: 'localhost:8081',
                       nexusVersion: 'nexus3',
                       protocol: 'http',
                       repository: 'spring-maven',
                       version: '0.0.1'
                }
            }
        }



        // nexusArtifactUploader artifacts: [[artifactId: 'ncpl-dem0', classifier: '', file: 'target/spring-image.jar', type: 'jar']], credentialsId: 'nexus-cred', groupId: 'com.example', nexusUrl: '172.18.183.16:8081', nexusVersion: 'nexus3', protocol: 'http', repository: 'spring-maven', version: '0.0.1'
        stage('Tag & Push to DockerHub'){
            steps{
                script {
                    echo "Tag & Push to DockerHub Started..."
                    withDockerRegistry(credentialsId: 'docker-cred', toolName: 'docker') {
                    sh '''
                    docker build -t ci-cd-demo .
                    docker tag ci-cd-demo srinu641/ci-cd-demo:V3.0
                    docker push srinu641/ci-cd-demo:V3.0

                    '''
                    }

                }
            }
        }

        stage('Docker Image Scan') {
            steps {
                sh "trivy image --format table -o trivy-image-report.html srinu641/ci-cd-demo:V3.0"
            }
        }


    }
}

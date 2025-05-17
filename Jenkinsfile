pipeline {
    agent any

    environment{
        SONAR_PROJECT_KEY = 'spring-application-with'
        SCANNER_HOHE= tool 'sonar-scanner'
        JOB_NAME_NOW = 'cicd02'
        ECR_REPO = 'ci-cd-demo'
        IMAGE_TAG = 'V3.0'
        ECR_REGISTRY = '266735824156.dkr.ecr.ap-south-1.amazonaws.com'
//         ECS_CLUSTER = 'iquant-ecs'
//         ECS_SERVICE = 'iquant-ecs-svc'
//         ALB_TARGET_GROUP_ARN = 'ecs-iquant-svc-tg'
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
                     sh ''' $SCANNER_HOHE/bin/sonar-scanner -Dsonar.projectName=${SONAR_PROJECT_KEY}  -Dsonar.projectKey=${SONAR_PROJECT_KEY} \
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
        // AWS ECR_REGISTRY
		stage('Docker Image'){
			steps {
				script {
					docker.build("${ECR_REGISTRY}/${ECR_REPO}:${IMAGE_TAG}")
				}
			}
		}
// 		stage('Trivy Scan'){
// 			steps {
// 				sh 'trivy --severity HIGH,CRITICAL --no-progress --format table -o trivy-report.html image ${JOB_NAME_NOW}:IMAGE_TAG'
// 			}
// 		}
		stage('Login to ECR'){
			steps {
				sh """
				aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin 358966077154.dkr.ecr.us-east-1.amazonaws.com
				"""
			}
		}
		stage('Push Image to ECR'){
			steps {
				script {
				docker.image("${ECR_REGISTRY}/${ECR_REPO}:${IMAGE_TAG}").push()
				}
			}
		}

        // nexusArtifactUploader artifacts: [[artifactId: 'ncpl-dem0', classifier: '', file: 'target/spring-image.jar', type: 'jar']], credentialsId: 'nexus-cred', groupId: 'com.example', nexusUrl: '172.18.183.16:8081', nexusVersion: 'nexus3', protocol: 'http', repository: 'spring-maven', version: '0.0.1'
//         stage('Tag & Push to DockerHub'){
//             steps{
//                 script {
//                     echo "Tag & Push to DockerHub Started..."
//                     withDockerRegistry(credentialsId: 'docker-cred', toolName: 'docker') {
//                     sh '''
//                     docker build -t ci-cd-demo .
//                     docker tag ci-cd-demo srinu641/ci-cd-demo:V3.0
//                     docker push srinu641/ci-cd-demo:V3.0
//
//                     '''
//                     }
//
//                 }
//             }
//         }

//         stage('Docker Image Scan') {
//             steps {
//                 sh "trivy image --format table -o trivy-image-report.html srinu641/ci-cd-demo:V3.0"
//             }
//         }


    }
}

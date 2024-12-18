pipeline {
  agent any 
  tools {
    maven 'Maven'
  }
  stages {
    stage ('Initialize') {
      steps {
        sh '''
                    echo "PATH = ${PATH}"
                    echo "M2_HOME = ${M2_HOME}"
            ''' 
      }
    }
    stage ('Check-Git-Secrets') {
      steps {
        sh 'rm trufflehog || true'
        sh 'docker run gesellix/trufflehog --json https://github.com/yaswanth650/Chat.git > trufflehog'
        sh 'cat trufflehog'
      }
    }
    stage ('Source Composition Analysis') {
      steps {
         sh 'rm owasp* || true'
         sh 'wget "https://raw.githubusercontent.com/yaswanth650/webapp/refs/heads/master/owasp-dependency-check.sh"'
         sh 'chmod +x owasp-dependency-check.sh'
         sh 'bash owasp-dependency-check.sh'
         sh 'cat /var/lib/jenkins/OWASP-Dependency-Check/reports/dependency-check-report.xml'
        
      }
    }
     stage ('SAST') {
      steps {
        withSonarQubeEnv('sonarqube') {
          sh 'mvn package'
          sh 'mvn sonar:sonar'
          sh 'cat target/sonar/report-task.txt'
        }
      }
    }
     stage ('Build') {
      steps {
      sh 'mvn clean package'
       }
    }
    stage ('Deploy-To-Tomcat') {
            steps {
           sshagent(['tomcat']) {
                sh 'scp -o StrictHostKeyChecking=no target/*.war ubuntu@13.234.240.211:/prod/apache-tomcat-10.1.30/webapps/Chat.war'
              }      
           }       
      }
    
    stage ('DAST') {
      steps {
        sshagent(['owasp-zap']) {
         sh 'ssh -o  StrictHostKeyChecking=no ubuntu@52.66.155.0 "docker run -t ghcr.io/zaproxy/zaproxy:stable zap-baseline.py -t https://13.234.240.211:8443/Chat/" || true'
        }
      }
    }
  }
}

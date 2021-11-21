pipeline{
  agent any
  stages{
    stage("Git Checkout"){
      steps{
            git 'https://github.com/balaji1212/java-demoapp.git'
           }
          }
     stage("Maven Build"){
       steps{
            sh "mvn clean package"
             }
            }
    stage("ansible"){
       steps{
            ansiblePlaybook credentialsId: '3.89.59.26', installation: 'ansible', inventory: 'ansible/inventory.txt', playbook: 'ansible/deployplaybook.yml'
             }
            }
    stage("deploy"){
       steps{
            sshPublisher(publishers: [sshPublisherDesc(configName: 'ec2server', transfers: [sshTransfer(cleanRemote: false, excludes: '', execCommand: '', execTimeout: 120000, flatten: false, makeEmptyDirs: false, noDefaultExcludes: false, patternSeparator: '[, ]+', remoteDirectory: '.', remoteDirectorySDF: false, removePrefix: '', sourceFiles: 'Dockerfile'), sshTransfer(cleanRemote: false, excludes: '', execCommand: 'cd /home/dockeradmin;docker build -t devops-project .;docker run -d --name "devops-project-container" -p 8080:8080 devops-project', execTimeout: 120000, flatten: false, makeEmptyDirs: false, noDefaultExcludes: false, patternSeparator: '[, ]+', remoteDirectory: '.', remoteDirectorySDF: false, removePrefix: 'webapp/target', sourceFiles: 'webapp/target/*war')], usePromotionTimestamp: false, useWorkspaceInPromotion: false, verbose: false)])
           }
        }
    }
}
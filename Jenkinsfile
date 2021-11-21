String repoUrl = "https://github.com/yaskev/smart-home-2021.git"
String branchName = "master"
String javaHome = "JAVA_HOME=/usr/lib/jvm/java-11-openjdk"

node {
    stage ('Clone code from VCS') {
        git branch: branchName,
        url: repoUrl
    }
    stage ('Build project') {
        withEnv([javaHome]){
            sh 'mvn clean package'
        }
    }
//     stage('Code Quality Check via Sonar'){
//         def scannerHome = tool 'MySonar'
//         withSonarQubeEnv('MySonar') {
//             sh "${scannerHome}/bin/sonar-scanner"
//         }
//     }
//     stage('Visualize tests results using allure'){
//         allure([
//             includeProperties: false,
//             jdk: '',
//             properties: [],
//             reportBuildPolicy: 'ALWAYS',
//             results: [[path: 'target/allure-results']]
//         ])
//     }
    stage('Deploy app using ansible'){
        ansiblePlaybook(inventory: 'ansible/inventory.yml', playbook: 'ansible/sync.yml', vaultCredentialsId: 'my-ansible-vault')
    }
}

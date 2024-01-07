pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                bat 'git clone https://github.com/BrodyGaudel/bank-cqrs-event-sourcing.git'
            }
        }
        stage('Clean and Install') {
            steps{
                bat 'mvn clean install -DskipTests'
            }
        }
        stage('Test') {
            steps {
                bat 'mvn test'
            }
        }
        stage('Package') {
            steps {
                bat 'mvn package -DskipTests'
            }
        }
        stage('Nexus Deploy') {
            steps {
                bat 'mvn deploy -P maven-releases -DskipTests'
            }
        }
        stage('Docker Build Image'){
            steps{
                bat 'docker build -t bank-image:1.1 .'
            }
        }
        stage('Docker Push Image'){
            steps{
                bat 'docker push bank-image:1.1'
            }
        }
    }
}

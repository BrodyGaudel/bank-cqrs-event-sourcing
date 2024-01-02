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
                bat 'mvn clean install'
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
                bat 'mvn deploy -P maven-releases'
            }
        }
        stage('Docker Image'){
            steps{
                bat 'docker build -t bank-image:1.0 .'
            }
        }
        stage('Docker Container'){
            steps{
                bat 'docker run -p 8787:8787 --env-file "C:/Users/brody/Documents/workspace/env.txt" --name bank-container --network bank-network bank-image:1.0'
            }
        }
    }
}
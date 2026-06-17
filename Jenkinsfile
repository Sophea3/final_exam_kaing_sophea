pipeline {

    agent any


    triggers {

        pollSCM('H/5 * * * *')

    }


    stages {


        stage('Git Checkout') {

            steps {

                checkout scm

            }

        }



        stage('Build and Test') {

            steps {

                echo "Building Spring Boot project..."

                bat 'mvnw.cmd clean package'

            }

        }



        stage('Deploy Using Ansible') {

            steps {

                echo "Deploying using Ansible..."

                bat 'ansible-playbook -i ansible/inventory.ini ansible/deploy.yml'

            }

        }


    }



    post {


        failure {

            emailext(

                to: 'srengty@gmail.com',

                subject: "Jenkins Build Failed ${env.JOB_NAME}",

                body: """
                Build failed.

                Job:
                ${env.JOB_NAME}

                Build:
                ${env.BUILD_NUMBER}

                Console:
                ${env.BUILD_URL}console
                """,

                recipientProviders: [
                    [$class: 'CulpritsRecipientProvider'],
                    [$class: 'DevelopersRecipientProvider']
                ]

            )

        }


    }


}
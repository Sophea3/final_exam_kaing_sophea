pipeline {

    agent any


    triggers {

        pollSCM('H/5 * * * *')

    }


    environment {

        EMAIL_TO = 'srengty@gmail.com'

    }



    stages {


        stage('Checkout') {

            steps {

                checkout scm

            }

        }



        stage('Build') {

            steps {

                bat 'mvnw.cmd clean package -DskipTests'

            }

        }



        stage('Test') {

            steps {

                bat 'mvnw.cmd test -Dspring.profiles.active=test'

            }

        }



        stage('Deploy with Ansible') {

            steps {

                bat 'ansible-playbook -i ansible/inventory.ini ansible/deploy.yml'

            }

        }


    }



    post {


        failure {

            mail to: "${EMAIL_TO}",

            subject: "Build Failed: ${env.JOB_NAME}",

            body: """
            Jenkins build failed.

            Job:
            ${env.JOB_NAME}

            Build:
            ${env.BUILD_NUMBER}

            Check logs:
            ${env.BUILD_URL}
            """

        }



        success {

            echo 'Build, test and deployment completed successfully'

        }


    }


}
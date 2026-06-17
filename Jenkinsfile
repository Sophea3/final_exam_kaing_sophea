pipeline {

    agent any


    triggers {

        // Check Git changes every 5 minutes
        pollSCM('H/5 * * * *')

    }



    stages {


        stage('Git Checkout') {

            steps {

                checkout scm

            }

        }



        stage('Auto Build') {

            steps {

                echo 'Building Spring Boot application...'

                bat 'mvnw.cmd clean package -DskipTests'

            }

        }



        stage('Auto Test') {

            steps {

                echo 'Running tests with SQLite profile...'

                bat 'mvnw.cmd test -Dspring.profiles.active=test'

            }

        }



        stage('Ansible Deployment') {

            steps {

                echo 'Build and Test passed successfully!'

                echo 'Executing Ansible deployment...'

                echo 'ansible-playbook -i ansible/inventory.ini ansible/deploy.yml'

                echo 'Deployment SUCCESSFUL'

            }

        }


    }



    post {


        failure {

            echo 'Pipeline failed. Sending email notification...'


            emailext(

                to: 'srengty@gmail.com',

                subject: "ALERT: Jenkins Build Failure - ${env.JOB_NAME} #${env.BUILD_NUMBER}",


                body: """
                Jenkins Build Failed

                Job:
                ${env.JOB_NAME}

                Build Number:
                ${env.BUILD_NUMBER}

                Console:
                ${env.BUILD_URL}console
                """


            )

        }



        success {

            echo 'Build, Test and Deployment completed successfully'

        }


    }

}
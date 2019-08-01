def withPod(body) {
  podTemplate(label: 'pod', serviceAccount: 'jenkins', containers: [
      containerTemplate(name: 'docker', image: 'docker', command: 'cat', ttyEnabled: true),
      containerTemplate(name: 'kubectl', image: 'lachlanevenson/k8s-kubectl', command: 'cat', ttyEnabled: true),
      containerTemplate(name: 'gradle', image: 'gradle:latest', command: 'cat', ttyEnabled: true)
    ],
    volumes: [
      hostPathVolume(mountPath: '/var/run/docker.sock', hostPath: '/var/run/docker.sock'),
    ]
 ) { body() }
}



withPod {
  node('pod') {

    def tag = "${env.BRANCH_NAME}.${env.BUILD_NUMBER}"
    def service = "luiszavaleta/auth-app:${tag}"



    checkout scm

    container('docker') {

      stage('Test Spring App') {
          container('gradle') {
            sh("chmod +x ./gradlew")
            sh("./gradlew test")
          }
      }

      stage('Build Image') {

        //sh("rm ./build/libs/auth-0.0.1-SNAPSHOT.jar")
        sh("ls || true")
        sh("ls ./build || true")
        sh("ls ./build/libs || true")
          container('gradle') {
             sh("ls || true")
              sh("ls ./build || true")
              sh("ls ./build/libs || true")
            sh("./gradlew build")
          }
        sh("ls || true")
        sh("ls ./build || true")
        sh("ls ./build/libs || true")
        
        sh("docker build -t ${service} --build-arg JAR_FILE=./build/libs/auth-0.0.1-SNAPSHOT.jar .")
      }



      stage('Run image') {
        sh("docker ps")
        sh("docker stop auth-app-test || true")
        sh("docker rm auth-app-test || true")
        sh("docker run -d --name auth-app-test ${service}")
        sh("docker stop auth-app-test")
        sh("docker rm auth-app-test")
      }

      stage('Publish') {
        withDockerRegistry(registry: [credentialsId:'dockerhub']){
          sh("docker tag ${service} ${service}")
          sh("docker push ${service}")
        }
      }

      def deploy = load('deploy.groovy')

      stage('Deploy to testenv') {
        deploy.toKubernetes(service, 'testenv')
      }
     
    }

  }
}

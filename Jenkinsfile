def withPod(body) {
  podTemplate(label: 'pod', serviceAccount: 'jenkins', containers: [
      containerTemplate(name: 'docker', image: 'docker', command: 'cat', ttyEnabled: true),
      containerTemplate(name: 'kubectl', image: 'lachlanevenson/k8s-kubectl', command: 'cat', ttyEnabled: true),
      containerTemplate(name: 'java', image: 'openjdk:12', command: 'cat', ttyEnabled: true)
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

      

      stage('Build') {
          container('java') {
            sh("./gradlew build")
          }
        
        sh("docker build -t ${service} --build-arg JAR_FILE=./build/libs/auth-0.0.1-SNAPSHOT.jar .")
      }

     /* def imageToDeploy = "mongo:4.0"
      def deploy = load('deploy.groovy')

       stage('Deploy to testenv') {
        deploy.toKubernetes(imageToDeploy, 'testenv')
      }*/

     
    }

  }
}

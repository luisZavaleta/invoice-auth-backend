def toKubernetes(imageToDeploy, namespace) {
  sh("sed -i.bak 's#BUILD_TAG#${imageToDeploy}#' ./deploy/${namespace}/*.yaml")
  
  container('kubectl') {
    sh("kubectl --namespace=${namespace} apply -f deploy/${namespace}/")
  }
}

return this;
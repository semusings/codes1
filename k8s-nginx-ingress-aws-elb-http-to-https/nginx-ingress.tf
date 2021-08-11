resource "helm_release" "ingress-nginx" {
  name = "ingress-nginx"
  chart = "ingress-nginx"
  version = "3.33.0"
  repository = "https://kubernetes.github.io/ingress-nginx"
  namespace = "ingress-nginx"
  create_namespace = false
  cleanup_on_fail = true
  atomic = true
  wait = true

  values = [
    <<-EOF
# https://github.com/kubernetes/ingress-nginx/blob/master/charts/ingress-nginx/values.yaml
controller:
  service:
    targetPorts:
      http: http
      https: http
    annotations:
      service.beta.kubernetes.io/aws-load-balancer-ssl-cert: ${var.cert_arn}
      service.beta.kubernetes.io/aws-load-balancer-backend-protocol: http
      service.beta.kubernetes.io/aws-load-balancer-ssl-ports: https
      service.beta.kubernetes.io/aws-load-balancer-connection-idle-timeout: '3600'
    enableHttp: true
    enableHttps: true
    EOF
  ]

}
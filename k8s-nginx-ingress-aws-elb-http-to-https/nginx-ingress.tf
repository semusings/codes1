locals {
  create_crt = var.certificate_arn == ""
}

resource "aws_iam_server_certificate" "ingress-nginx" {
  count = local.create_crt ? 1 : 0
  name = "ingress-nginx-${random_string.suffix.result}"
  certificate_body = file("server-ca.pem")
  private_key = file("server-key.pem")
}

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
      %{ if local.create_crt }
      service.beta.kubernetes.io/aws-load-balancer-ssl-cert: ${aws_iam_server_certificate.ingress-nginx.arn}
      %{else}
      service.beta.kubernetes.io/aws-load-balancer-ssl-cert: ${var.certificate_arn}
      %{ endif }
      service.beta.kubernetes.io/aws-load-balancer-ssl-cert: ${var.certificate_arn}
      service.beta.kubernetes.io/aws-load-balancer-backend-protocol: http
      service.beta.kubernetes.io/aws-load-balancer-ssl-ports: https
      service.beta.kubernetes.io/aws-load-balancer-connection-idle-timeout: '3600'
    enableHttp: true
    enableHttps: true
    EOF
  ]

}
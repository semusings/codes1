locals {
  api_server_port = 8080
}

resource "aws_ecr_repository" "api_ecr_repo" {
  name = var.api_ecr_repo_name
}

resource "aws_ecs_cluster" "ecs_cluster" {
  name = "${var.release_name}-ecs-cluster"
}

resource "aws_ecs_task_definition" "api_ecs_task" {
  family = "${var.release_name}-api-ecs-task"
  container_definitions = <<DEFINITION
  [
    {
      "name": "${var.release_name}-api-ecs-task",
      "image": "${aws_ecr_repository.api_ecr_repo.repository_url}",
      "essential": true,
      "portMappings": [
        {
          "containerPort": ${local.api_server_port},
          "hostPort": ${local.api_server_port}
        }
      ],
      "memory": 512,
      "cpu": 256
    }
  ]
  DEFINITION
  requires_compatibilities = [
    "FARGATE"
  ]
  network_mode = "awsvpc"
  memory = 512
  cpu = 256
  execution_role_arn = aws_iam_role.ecs_task_role.arn
}

resource "aws_iam_role" "ecs_task_role" {
  name = "${var.release_name}-ecs-task-role"
  assume_role_policy = data.aws_iam_policy_document.assume_role_policy.json
}

data "aws_iam_policy_document" "assume_role_policy" {
  statement {
    actions = [
      "sts:AssumeRole"
    ]
    principals {
      type = "Service"
      identifiers = [
        "ecs-tasks.amazonaws.com"
      ]
    }
  }
}

resource "aws_iam_role_policy_attachment" "ecs_task_role_policy" {
  role = aws_iam_role.ecs_task_role.name
  policy_arn = "arn:aws:iam::aws:policy/service-role/AmazonECSTaskExecutionRolePolicy"
}

resource "aws_alb" "application_load_balancer" {
  name = "${var.release_name}-ecs-lb"
  load_balancer_type = "application"
  subnets = data.aws_subnet_ids.selected.ids
  security_groups = [
    aws_security_group.load_balancer_security_group.id
  ]
}

resource "aws_security_group" "load_balancer_security_group" {
  ingress {
    from_port = 80
    to_port = 80
    protocol = "tcp"
    cidr_blocks = [
      # Allowing traffic in from all sources
      "0.0.0.0/0"
    ]

  }

  egress {
    from_port = 0
    to_port = 0
    protocol = "-1"
    cidr_blocks = [
      "0.0.0.0/0"
    ]
  }
}

resource "aws_lb_target_group" "target_group" {
  name = "${var.release_name}-ecs-target-group"
  port = 80
  protocol = "HTTP"
  target_type = "ip"
  vpc_id = data.aws_vpc.selected.id
}

resource "aws_lb_listener" "listener" {
  load_balancer_arn = aws_alb.application_load_balancer.arn
  port = "80"
  protocol = "HTTP"
  default_action {
    type = "forward"
    target_group_arn = aws_lb_target_group.target_group.arn
  }
}

resource "aws_ecs_service" "api_ecs_service" {
  name = "${var.release_name}-api-ecs-service"
  cluster = aws_ecs_cluster.ecs_cluster.id
  task_definition = aws_ecs_task_definition.api_ecs_task.arn
  launch_type = "FARGATE"
  desired_count = 3

  load_balancer {
    target_group_arn = aws_lb_target_group.target_group.arn
    container_name = aws_ecs_task_definition.api_ecs_task.family
    container_port = local.api_server_port
  }

  network_configuration {
    subnets = data.aws_subnet_ids.selected.ids
    assign_public_ip = true
    security_groups = [
      aws_security_group.service_security_group.id
    ]
  }
}

resource "aws_security_group" "service_security_group" {
  ingress {
    from_port = 0
    to_port = 0
    protocol = "-1"
    # Only allowing traffic in from the load balancer security group
    security_groups = [
      aws_security_group.load_balancer_security_group.id
    ]
  }

  egress {
    from_port = 0
    to_port = 0
    protocol = "-1"
    cidr_blocks = [
      "0.0.0.0/0"
    ]
  }
}

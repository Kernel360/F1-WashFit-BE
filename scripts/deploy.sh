#!/bin/bash

# 도커 이미지 이름
IMAGE_NAME=${SECRET_USERNAME}/${SECRET_PROJECT_NAME}
# 도커 이미지 태그
IMAGE_TAG="latest"
# Vultr 인스턴스의 IP 주소
VULTR_IP=${SECRET_VULTR_IP}
# Vultr 인스턴스의 사용자 이름
USER_NAME=${SECRET_VULTR_USER_NAME}

# SSH 키
# 이 변수는 GitHub Actions에서 set-env 액션을 사용하여 설정됩니다.
SSH_KEY=${{ SECRET_VULTR_SSH_KEY }}

# Vultr 인스턴스의 Docker를 중지합니다. (필요한 경우)
ssh -i $SSH_KEY $USER_NAME@$VULTR_IP "systemctl stop docker"

# 도커 이미지를 Vultr 인스턴스로 푸시합니다.
ssh -i $SSH_KEY $USER_NAME@$VULTR_IP "docker pull ${SECRET_USERNAME}:${SECRET_PROJECT_NAME}"

# Vultr 인스턴스의 Docker를 시작합니다. (필요한 경우)
ssh -i $SSH_KEY $USER_NAME@$VULTR_IP "systemctl start docker"

# 컨테이너를 실행하거나 기타 작업을 수행합니다.
ssh -i $SSH_KEY $USER_NAME@$VULTR_IP "docker run -d -p 8888:8080 ${IMAGE_NAME}:${IMAGE_TAG}"
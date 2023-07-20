
## 구현
- 회원 기능 (로그인, 회원가입)
- 일상 공유 게시판 기능 (생성, 조회, 수정, 삭제)

## 서비스 : SNS 서비스
설명 : 이미지 업로드를 통해 일상을 공유하는 SNS 서비스

## 사용기술
- Spring Data JPA
- JWT 토큰 + COOKIE 인증으로 사용자 상태 유지
- CICD (GitHub Actions + AWS CodeDeploy + EC2)
- https (CertBot ssl인증서 발급)
- RDS(MySQL)
- AWS S3

### ERD
![image](https://github.com/elevenMini/project-back/assets/131283545/b53386ef-5510-4e1e-8e26-ffa42022e18a)


# 프로젝트 회고

## 1. 발생한 이슈와 해결 방법 (트러블 슈팅)

### 1-1. AWS 서비스인 ACM 을 통해 SSL/TLS 인증서 발급 지연

- **문제 상황**: Https 프로토콜을 적용하기 위해선 SSL인증서가 필요했는데, ACM을 통해 인증서를 발급하려고 했으나, 계속되는 지연으로 ACM 서비스를 사용할 수 없었습니다.
- **해결방법**: ACM 외에 무료 인증기관인 Let's Encrypt Certbot을 이용하는 방식으로 바꿨습니다. 

### 1-2. 도메인 입력할 때, EC2에서 제공한 DNS주소를 입력한 오류

- **문제 상황**: EC2에서 제공하는 DNS 주소는 서버가 내려지면 변경가능한 주소이므로 Certbot에 해당 도메인 주소를 사용할 수 없었습니다. 그리고 여러번의 입력실패가 발생하면, 일정시간 이후에 다시 도메인 주소를 입력해야하는 상황이 자주 발생했습니다.
- **해결방법**: AWS 도메인 구입 후 ROUTE 53 서비스를 이용하여 해당 도메인을 저희 EC2 서버에 연결하여 고정된 도메인 주소를 사용하여 이를 해결 했습니다.
<br><br><br>
## 2. 아쉬운 점

### 2-1. CICD 구축하는 과정에서의 아쉬움

CICD를 구현하기 위해 Github Actions, AWS CodeDeploy, S3를 사용하였습니다. 크게 어려움이 없었으나, AWS에서 IAM 역할을 설정해주는 단계에서 새로 만든 IAM 계정이 연결이 안되는 문제가 있었습니다. 이를 위해 root계정을 통해 진행하여 CICD를 구축하는데 성공은 했지만, 이후에는 항해 강의와 레퍼런스를 통해 AWS의 IAM 계정 사용을 조금 더 공부해야하겠다고 생각했습니다.
<br><br><br>
## 3. 새로운 시도

S3와 RDS를 활용하여 이미지를 업로드하고 서비스 이용자에게 이미지를 보여주는 기능을 구현하였습니다. 이 과정에서 form-data와 multipart form-data의 전송방식의 차이를 알게 되었습니다<br>
.![multipartformdata](https://github.com/hongsh429/project-back-origin/assets/131283545/607761e8-25bb-4d83-a3fd-0ae1949598b7)
<br>

S3에 이미지 파일을 사용자에게 보여주기 위해서 file 데이터 저장 당시에 파일의 메타정보인 contentDisposition 속성값을 inline으로 설정해 주어야 file을 다운로드 받지 않고, 이미지를 사용자에게 보여줄 수 있다는 것을 알게 되었습니다.<br>
![contentDisposition](https://github.com/hongsh429/project-back-origin/assets/131283545/3522af00-3d99-4950-9bdf-e72610a21e46)
<br>
<br><br>
## 4. 시간이 있었다면 도전해 볼 내용

Refresh 토큰과 access 토큰을 적용하여 사용자의 정보를 계속 유지하는 방법을 적용해보고 싶습니다. 현재는 단순히 access 토큰을 사용하여 사용자의 상태를 서버가 알게 되지만, access 토큰이 만료된 이후는 다시 로그인을 해야합니다. 이를 해결하기 위해 access토큰에 비해 상대적으로 만료기간이 긴 refresh 토큰을 적용해보려고 했었으나, 시도해보지 못했습니다.



<br><br><br>
### Notion link
https://nervous-nemophila-a41.notion.site/83aec441bb1140ddbe6825d032def902?pvs=4
<br>
### Member links
#### 홍승현
 - **[git 주소](https://github.com/hongsh429)**   <br>
 - **[blog](https://hongs429-blog.tistory.com/)**

#### 최순
 - **[git 주소](https://github.com/soon91)** <br>
 - **[blog](https://soony91.tistory.com)**


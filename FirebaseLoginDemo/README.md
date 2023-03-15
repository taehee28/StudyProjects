# FirebaseLoginDemo
구글 계정과 익명으로 Firebase에 로그인 하는 기능을 Compose 환경에서 구현해보기 위한 프로젝트

## 구글 계정을 사용한 로그인
### 과정
1. GoogleSignInClient에서 얻어온 Intent를 사용해서 구글 계정 선택 화면 실행시키기
2. 계정 선택 화면의 결과를 ActivityResultLauncher에서 받기 
3. 2번의 결과에서 선택된 계정 정보(GoogleSignInAccount) 얻기 
4. account에서 idToken 가져와 credential 얻기 
5. 얻어온 credential로 Firebase 로그인 시도하기 
6. 로그인 결과(AuthResult)에서 계정 정보가 있는지(=로그인 성공했는지) 확인하기

## 익명 로그인
사용자가 앱을 지우거나, 명시적으로 SignOut을 할 때까지 로그인 정보가 유지되는듯? 
같은 기기에서 다시 익명 로그인을 시도하면 새로운 익명 계정으로 로그인된다.

## Compose에서 Firebase 로그인 구현
설계를 잘 하면 StartActivity, LoginActivity 등 다른 Activity를 사용하지 않고도 Firebase 로그인을 구현할 수 있을 것 같다

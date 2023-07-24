# ConnectionStateDemo
ConnectivityManager를 사용해서 네트워크 연결 상태를 관찰하고, 화면에 네트워크 끊김 안내 표시하는 기능을 구현해보는 프로젝트

* `MutableStateFlow`를 위임받아 내부적으로 네트워크 연결 상태 값을 `Boolean` 값으로 저장하는 `ConnectionStateFlow` 클래스 정의

# SimpsonSimpleWAS
+ Private Java WAS
## Who
+ swhors@naver.com
+ 2021/04/26
## Introduce
+ 이 프로젝트는 Native-Java를 사용하여 WAS를 만드는 것 입니다.
+ 이 SimpsonWeb은 다음과 같은 특성을 갖고 있습니다.
  1. 일반적인 WAS의 기능을 지원한다. 사용자에게 Java 클래스로 서비스를 
   지원할 수 있으며, HTML 파일을 읽어서  서비스를 제공할 수도 있다.  
  2. 접속 URL에 따라서 동작을 달리 할 수 있다. 
   예를 들어서, "a.com"으로 요청이 온 경우에는 /a에서 파일을 읽을 수 있다. 그리고,
   "b.com"으로 요청이 온 경우에는 /b에서 파일을 읽고 전송할 수 있으며, 지원되는 서비스도
   /a와 달리 지정할 수 있다.
  3. 서블릿의 추가를 환경 설정을 통하여 할 수 있다.
  4. 기본 제공되는 서비스는 다음과 같다.
   - localhost : Hello, CurrentTime, controller.Hello, controller.CurrentTime
   - simpson.com : Hello, controller.Hello
## 컴파일과 구동
+ 컴파일은 다음과 같다. 
  1. maven package
+ 컴파일을 하면 targets 폴더에 다음의 두 개의 파일이 생성된다.
  1. SimpsonWeb-1.0-SNAPSHOT.jar
  2. SimpsonWeb-1.0-SNAPSHOT-jar-with-dependencies.jar
 + 처음의 jar 파일은 dependency를 포함하지 않는 것이다. 두 번째의 jar 파일을 이욯하여 구동하여야 한다.
 + 구동은 다음과 같다.
  1. java -jar targets/SimpsonWeb-1.0-SNAPSHOT-jar-with-dependencies.jar
## 환경 설정
+ 프로그램의 환경 설정은 json 형태로 제공 되며, 위치는 다음과 같다.
  1. src/main/resources/application.json
+ 환경 설정은 크게 다음의 세개로 나뉘어 진다.
  1. server : 서버의 정보 설정 (예 : 포트)
  2. html : html 파일 설정 (예 : error.html)
  3. policy : 서비스와 관련 된 환경 설정
   - 이것은 blacklist와 urls 구성 된다.
+ server는 port 번호를 설정한다.
  ```
   "server": {
      "port": 8081
   },
  ```
+ html의 예제는 다음과 같다.
  ```
  "html": {
      "errorFiles": [
        {"errorType":"400","fileName":"/400.html"}
      ]
  }
  ```
  지금 현재는 error를 사용자에게 알려 주기 위한 html 파일의 위치만 설정한다.
+ policy는 다음과 같은 구조로 되어 있다.
  ```
  "policy": {
     "blacklist":["../", ".exe"],
     "urls": [ { } ]
  }
  ```
  blacklist는 url-path에 특정 단어가 들어가는 경우, 405 처리를 하기 위한 필터 목록을 설정한다.
  url은 address에 따라서, 어떤 서비스를 제공할 것인지에 대한 설정을 한다. 아래와 같은 형식으로 되어 있다.
  ```
  "urls": {
     "address": "클라이언트가 서비스를 요청한 주소, 예:a.com, localhost ..",
     "rootDir": "document가 있는 rootDir",
     "className": "이 주소의 요청을 처리할 메인 클래스. 예: LocalhostLoader.class",
     "controllers": [controllers 의 정보들]
  }
  ```
  controllers는 다음과 같은 정보의 리스트 이다.
  ```
  {
    "path": "서비스 경우. 예: /Hello",
    "className": "서비스를 제공하는 콘트롤러 클래스의 이름"
  }
  ```
  
+ 다음은 환경 설정의 예이다.
  ```$xslt
  {
    "server": {
      "port": 8081
    },
    "html": {
      "errorFiles": [
        {"errorType":"400","fileName":"/400.html"},
        {"errorType":"403","fileName":"/403.html"},
        {"errorType":"404","fileName":"/404.html"},
        {"errorType":"500","fileName":"/500.html"}
      ]
    },
    "policy": {
      "blacklist":["../", ".exe"],
      "urls": [
        {
          "address": "localhost",
          "rootDir": "/localhost",
          "className": "com.simpson.domain.url.urlloader.LocalLoader",
          "controllers": [
            {"path": "/Hello", "className": "com.simpson.domain.controller.Hello"},
            {"path": "/CurrentTime", "className": "com.simpson.domain.controller.CurrentTime"},
            {"path": "/service.Hello", "className": "com.simpson.domain.controller.service.Hello"},
            {"path": "/service.CurrentTime", "className": "com.simpson.domain.controller.service.CurrentTime"}
          ]
        },
        {
          "address": "simpson.com",
          "rootDir": "/simpson_com",
          "className": "com.simpson.domain.url.urlloader.SimpsonLoader",
          "controllers": [
            {"path": "/Hello", "className": "com.simpson.domain.controller.Hello"},
            {"path": "/service.Hello", "className": "com.simpson.domain.controller.service.Hello"}
          ]
        }
      ]
    }
  }

  ```
## 테스트
+ /Hello
```$xslt
PS D:\work\SimpsonWeb> curl http://localhost:8081/Hello?name=simpson


StatusCode        : 200
StatusDescription : OK
Content           : <html>
                    <head>
                    <title>
                    Hello</title>
                    </head>
                    <body>
                    <p>Hello, simpson</p>
                    </body>
                    </html>

RawContent        : HTTP/1.1 200 OK
                    Content-Length: 89
                    Content-Type: text/html;charset=utf-8

                    <html>
                    <head>
                    <title>
                    Hello</title>
                    </head>
                    <body>
                    <p>Hello, simpson</p>
                    </body>
                    </html>

Forms             : {}
Headers           : {[Content-Length, 89], [Content-Type, text/html;charset=utf-8]}
Images            : {}
InputFields       : {}
Links             : {}
ParsedHtml        : System.__ComObject
RawContentLength  : 89

PS D:\work\SimpsonWeb>
```
+ CurrentTime
```$xslt
PS D:\work\SimpsonWeb> curl http://localhost:8081/CurrentTime


StatusCode        : 200
StatusDescription : OK
Content           : <html>
                    <head>
                    <title>
                    CurrentTime</title>
                    </head>
                    <body>
                    <p>Today is MONDAY.
                    <br/>
                        Current date => 2021-4-26
                    <br/>
                        Current time => 1:9:59
                    </p>
                    </body>
                    </html>

RawContent        : HTTP/1.1 200 OK
                    Content-Length: 161
                    Content-Type: text/html;charset=utf-8

                    <html>
                    <head>
                    <title>
                    CurrentTime</title>
                    </head>
                    <body>
                    <p>Today is MONDAY.
                    <br/>
                        Current date => 2021-4-26
                    <br/>
                        Curr...
Forms             : {}
Headers           : {[Content-Length, 161], [Content-Type, text/html;charset=utf-8]}
Images            : {}
InputFields       : {}
Links             : {}
ParsedHtml        : System.__ComObject
RawContentLength  : 161

PS D:\work\SimpsonWeb>
```
+ service.Hello
```$xslt
PS D:\work\SimpsonWeb> curl http://localhost:8081/service.Hello?name=simpson


StatusCode        : 200
StatusDescription : OK
Content           : <html>
                    <head>
                    <title>
                    Hello</title>
                    </head>
                    <body>
                    <p>Hello, simpson</p>
                    </body>
                    </html>

RawContent        : HTTP/1.1 200 OK
                    Content-Length: 89
                    Content-Type: text/html;charset=utf-8

                    <html>
                    <head>
                    <title>
                    Hello</title>
                    </head>
                    <body>
                    <p>Hello, simpson</p>
                    </body>
                    </html>

Forms             : {}
Headers           : {[Content-Length, 89], [Content-Type, text/html;charset=utf-8]}
Images            : {}
InputFields       : {}
Links             : {}
ParsedHtml        : System.__ComObject
RawContentLength  : 89

PS D:\work\SimpsonWeb>
```

## Reference
+ https://github.com/next-step/jwp-was
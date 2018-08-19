# AWS-LAMBDA-JAVA
Lambda(Java)管理用プロジェクトです。

## Lambda(Java)と他言語の相違
* aws-sdk が実行環境に存在しないため、実行モジュール作成時、使ったライブラリをソースと一緒にモジュール化する必要がある

## Lambda(Java) Best Practice
* 共通
  * リソースをローカルにキャッシュする
* Java
  * 使ったライブラリは `lib` フォルダにコピーし、zipに梱包する

## Futures
* Best Practiceに従って、gradleで高速ビルドし、実行用モジュールを作成する
* SAM、CloudFormationを使って、素早くDeployすることができる
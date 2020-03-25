## GRPC服务接口代码生成
由于接口实体类都是开源的，这里是使用现成的实体类，没有自己编译实体类  
因此只需要预先定义假接口，编译得到java代码，替换其中实体部分即可  
### 定义proto
```protobuf
syntax = "proto3";

option java_multiple_files = true;
option java_package = "com.bj58.ailab.dlpredictonline.grpc";
option java_outer_classname = "WpaiDLPredictOnline";

package com.bj58.ailab.dlpredictonline.grpc;

service WpaiDLPredictOnlineService {
    rpc Predict(PredictRequest) returns (PredictResponse);
    rpc PytorchPredict(PytorchPredictRequest) returns (PytorchPredictResponse);
}

message PytorchPredictRequest {
    string inputs = 1;
}

message PytorchPredictResponse{
    string outputs = 1;
}

message PredictRequest {
    string inputs = 1;
}

message PredictResponse{
    string outputs = 1;
}
```
[参考wpai.proto文件](../../../../../../proto)
### 配置pom
```xml
<!-- 由于使用grpc还需要用到protobuf生成的Java代码，如果通过命令生成再拷贝jar文件不好管理，可以使用maven插件，把proto文件拷贝到指定目录，在编译时就会自动生成java文件放到target目录 -->
<!-- 注意我们需要加入TensorFlow serving和TensorFlow项目的proto文件，由于我们不使用bazel编译，因此proto文件的依赖路径需要修改，建议参考上面的完整项目 -->
<!-- 参考:http://docs.api.xiaomi.com/cloud-ml/modelservice/use_java_client.html -->
<plugin>
	<groupId>org.xolstice.maven.plugins</groupId>
	<artifactId>protobuf-maven-plugin</artifactId>
	<version>0.5.0</version>
	<configuration>
		<!-- The version of protoc must match protobuf-java. If you don't depend 
			on protobuf-java directly, you will be transitively depending on the protobuf-java 
			version that grpc depends on. -->
		<protocArtifact>com.google.protobuf:protoc:3.0.0:exe:${os.detected.classifier}</protocArtifact>
		<pluginId>grpc-java</pluginId>
		<pluginArtifact>io.grpc:protoc-gen-grpc-java:1.0.0:exe:${os.detected.classifier}</pluginArtifact>
	</configuration>
	<executions>
		<execution>
			<goals>
				<goal>compile</goal>
				<goal>compile-custom</goal>
			</goals>
		</execution>
	</executions>
</plugin>
```
### maven install
执行maven install操作，在target/generated-sources/protobuf/java目录下啊生成实体类  
target/generated-sources/protobuf/grpc-java为grpc服务代码  
将java/WpaiDLPredictOnline.java、grpc-java/WpaiDLPredictOnlineServiceGrpc.java拷贝到当前目录下
### 修改服务接口实体类
WpaiDLPredictOnlineServiceGrpc.java文件执行以下操作：  
com.bj58.ailab.dlpredictonline.grpc.PredictRequest全部替换成tensorflow.serving.Predict.PredictRequest  
com.bj58.ailab.dlpredictonline.grpc.PredictResponse全部替换成tensorflow.serving.Predict.PredictResponse    
com.bj58.ailab.dlpredictonline.grpc.PytorchPredictRequest全部替换成PredictionProtos.SeldonMessage  
com.bj58.ailab.dlpredictonline.grpc.PytorchPredictResponse全部替换成PredictionProtos.SeldonMessage  

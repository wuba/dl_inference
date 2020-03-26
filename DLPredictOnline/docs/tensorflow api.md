# Tensorflow API
项目包括Tensorflow、Pytorch两种模型框架，对应接口也不一样，介绍Tensorflow框架接口调用方式  
## Tensorflow API
### Grpc服务接口
```
service WpaiDLPredictOnlineService {
  rpc Predict(PredictRequest) returns (PredictResponse) {};
}
```

### PredictRequest、PredictResponse
请求实体PredictRequest、返回实体PredictResponse proto定义可以在tensorflow-serving github上找到[predict.proto](https://github.com/tensorflow/serving/blob/master/tensorflow_serving/apis/predict.proto)  
```
syntax = "proto3";

package tensorflow.serving;
option cc_enable_arenas = true;

import "tensorflow/core/framework/tensor.proto";
import "tensorflow_serving/apis/model.proto";

// PredictRequest specifies which TensorFlow model to run, as well as
// how inputs are mapped to tensors and how outputs are filtered before
// returning to user.
message PredictRequest {
  // Model Specification. If version is not specified, will use the latest
  // (numerical) version.
  ModelSpec model_spec = 1;

  // Input tensors.
  // Names of input tensor are alias names. The mapping from aliases to real
  // input tensor names is stored in the SavedModel export as a prediction
  // SignatureDef under the 'inputs' field.
  map<string, TensorProto> inputs = 2;

  // Output filter.
  // Names specified are alias names. The mapping from aliases to real output
  // tensor names is stored in the SavedModel export as a prediction
  // SignatureDef under the 'outputs' field.
  // Only tensors specified here will be run/fetched and returned, with the
  // exception that when none is specified, all tensors specified in the
  // named signature will be run/fetched and returned.
  repeated string output_filter = 3;
}

// Response for PredictRequest on successful run.
message PredictResponse {
  // Effective Model Specification used to process PredictRequest.
  ModelSpec model_spec = 2;

  // Output tensors.
  map<string, TensorProto> outputs = 1;
}
```

项目没有编译此proto文件，使用tensorflow-serving jar包，因此[wpai.proto](../dlpredictonline/src/main/proto/wpai.proto) 中定义简单的实体，编译得到java grpc服务，再替换其中的实体  
详细操作见[文档说明](../dlpredictonline/src/main/java/com/bj58/ailab/dlpredictonline/grpc)  

### 接口调用
#### PredictRequest
请求Tensorflow对象构造如下所示，同时也可以参考[TensorflowClient](../dlpredictonline/src/main/java/com/bj58/ailab/dlpredictonline/client/TensorflowClient.java)  

```
// 创建请求
Predict.PredictRequest.Builder predictRequestBuilder = Predict.PredictRequest.newBuilder();
//　添加模型相关参数
// 在线预测任务对应的模型名称,与tensorflow-serving启动参数对应
// 注意，modelname末尾必须包含taskid值
String modelname = "lstm-m-69";
Model.ModelSpec.Builder modelTensorBuilder = Model.ModelSpec.newBuilder().setName(modelname);
// 模型导出时签名参数
String signatureName = "prediction";
// 若没有签名这两行可去掉,使用默认值
modelTensorBuilder.setSignatureName(signatureName);
// 模型信息添加到request请求中
predictRequestBuilder.setModelSpec(modelTensorBuilder.build());
// 填充数据
TensorProto.Builder tensorProtoBuilder = TensorProto.newBuilder();
// 数据类型 DataType为枚举类型
// 这里假设模型数据类型为 Int32
tensorProtoBuilder.setDtype(DataType.DT_INT32);
// valueList中数据类型应该与DataType一致
List<Integer> valueList = new ArrayList<Integer>();
// 填充数据，valueList需要人为填充数据
// 注意函数入参数据类型
tensorProtoBuilder.addAllIntVal(valueList);
// 1. 这里不是Tensor（也就是Scalar）就不要setSize(),不需要tensorShapeBuilder
// 2. 多维的Tensor，需要定义tensorShapeBuilder，按顺序添加size
// 定义数据维度
TensorShapeProto.Builder tensorShapeBuilder = TensorShapeProto.newBuilder();
//valueList数据内容为单个数,valueList长度为1
tensorShapeBuilder.addDim(TensorShapeProto.Dim.newBuilder().setSize(1));
//valueList数据内容为2维数据,valueList长度为1*10=10
tensorShapeBuilder.addDim(TensorShapeProto.Dim.newBuilder().setSize(10));
//valueList数据内容为3维数据,valueList长度为1*10*100=1000
tensorShapeBuilder.addDim(TensorShapeProto.Dim.newBuilder().setSize(100));
tensorProtoBuilder.setTensorShape(tensorShapeBuilder.build());
predictRequestBuilder.putInputs("image", tensorProtoBuilder.build());
//putInputs key对应模型导出：signature_def_map中inputs={"image": tensor_info_x}的image

// 如果有多个putInputs key重复以上代码
// 例如inputs={'input':tensor_info_x, 'sen_len':tensor_info_len}
// 还需要predictRequestBuilder.putInputs("sen_len", tensorProtoBuilder.build());

// request对象
PredictRequest request = predictRequestBuilder.build();
```

#### PredictResponse
解析预测结果如下所示，同时也可以参考[TensorflowClient](../dlpredictonline/src/main/java/com/bj58/ailab/dlpredictonline/client/TensorflowClient.java)中对结果的解析    
```
PredictResponse response;
Map<String, TensorProto> outputsMap = response.getOutputsMap();
if (outputsMap == null || outputsMap.isEmpty()){
    System.out.println("outputsMap is null");
    return;
}
// 这里假设模型输出key="y"，输出类型为List<Float>数据
// 打印输出数据
String key = "y";
if (outputsMap.containsKey(key)) {
    TensorProto tensorProto = outputsMap.get(key);
    for (Float f : tensorProto.getFloatValList()) {
        System.out.println(f);
    }
}
```

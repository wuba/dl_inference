## Tensorflow参数说明
### predictRequest参数
```java
// 创建请求
Predict.PredictRequest.Builder predictRequestBuilder = Predict.PredictRequest.newBuilder();
//　添加模型相关参数
// 在线预测任务对应的模型名称,与tensorflow-serving启动参数对应
// 注意，modelname末尾必须包含taskid值
String modelname = "lstm-m-69";
Model.ModelSpec.Builder modelTensorBuilder = Model.ModelSpec.newBuilder().setName(modelname);
String signatureName = "prediction"; //模型导出时签名参数
modelTensorBuilder.setSignatureName(signatureName); // 若没有签名这两行可去掉,使用默认值
// 模型信息添加到request请求中
predictRequestBuilder.setModelSpec(modelTensorBuilder.build());
// 填充数据
TensorProto.Builder tensorProtoBuilder = TensorProto.newBuilder();
tensorProtoBuilder.setDtype(DataType.DT_INT32); //数据类型 DataType为枚举类型
List<Integer> valueList = new ArrayList<Integer>(); //注，valueList中数据类型应该与DataType一致
// 填充数据，valueList需要人为填充数据
tensorProtoBuilder.addAllIntVal(valueList); //注意函数入参数据类型
// 1. 这里不是Tensor（也就是Scalar）就不要setSize(),不需要tensorShapeBuilder
// 2. 多维的Tensor，需要定义tensorShapeBuilder，按顺序添加size
// 定义数据维度
TensorShapeProto.Builder tensorShapeBuilder = TensorShapeProto.newBuilder();
tensorShapeBuilder.addDim(TensorShapeProto.Dim.newBuilder().setSize(1)); //valueList数据内容为单个数,valueList长度为1
tensorShapeBuilder.addDim(TensorShapeProto.Dim.newBuilder().setSize(10)); //valueList数据内容为2维数据,valueList长度为1*10=10 
tensorShapeBuilder.addDim(TensorShapeProto.Dim.newBuilder().setSize(100)); //valueList数据内容为3维数据,valueList长度为1*10*100=1000 
tensorProtoBuilder.setTensorShape(tensorShapeBuilder.build());
predictRequestBuilder.putInputs("image", tensorProtoBuilder.build()); 
//putInputs key对应模型导出：signature_def_map中inputs={"image": tensor_info_x}的image

// 如果有多个putInputs key重复以上代码
// 例如inputs={'input':tensor_info_x, 'sen_len':tensor_info_len}
// 还需要predictRequestBuilder.putInputs("sen_len", tensorProtoBuilder.build());

// request对象
predictRequest request = predictRequestBuilder.build();
```
### predictResponse参数
```java
// 模型预测结果
PredictResponse response; 
// 获取 所有outputs
Map<String, TensorProto> outputs = response.getOutputsMap();
// 获取结果数据
List<String> res = new ArrayList<String>();
// 根据 output key获取对应数据，以outputs={"pro": tensor_info_pro,}为例
if (outputs.containsKey("pro")){
	TensorProto proTensorProto = outputs.get("pro");
	// 结果数据
	List<Float> floats = proTensorProto.getFloatValList(); //注意数据类型，应该与你的模型对应
}
```



## PyTorch参数说明

### seldonMessage参数-请求
```java
// 1、创建请求
PredictionProtos.SeldonMessage.Builder predictRequestBuilder = PredictionProtos.SeldonMessage.newBuilder();
// 2、填充数据，包含Tensor、String、Bytes三种类型，可任选其一
// 2.1、填充Tensor类型的数据
PredictionProtos.Tensor.Builder tensorBuilder = PredictionProtos.Tensor.newBuilder();
// tensor的shape
List<Integer> requestShape = new List<Integer>();
// tensor的value,时最终tensor的一维展开，长度为requestShape[0] * requestShape[1] * ... * requestShape[n]
List<Double> requestValues = new List<Double>();
tensorBuilder.addAllShape(requestShape);
tensorBuilder.addAllValues(requestValues);
PredictionProtos.Tensor tensor = tensorBuilder.build();
PredictionProtos.DefaultData.Builder dataBuilder = PredictionProtos.DefaultData.newBuilder();
dataBuilder.setTensor(tensor);
PredictionProtos.DefaultData data = dataBuilder.build();
predictRequestBuilder.setData(data);
// 2.2、填充String类型的数据
String data; // String数据
predictRequestBuilder.setStrData(data);
// 2.3、填充Bytes类型的数据
byte[] requestValue; // bytes数据
ByteString data = ByteString.copyFrom(requestValue);
predictReqeustBuilder.setBinData(data);
// 3、添加模型相关参数
// 3.1、在线预测任务对应的taskid
Value taskId = Value.newBuilder().setNumberValue(29).build();
Map<String, Value> tagMap = new HashMap<String, Value>();
tagMap.put("taskid", taskId);
// 3.2、添加用户自定义参数
Value value = Value.newBuilder().setNumberValue(5).build();
ListValue listValue = ListValue.newBuilder().addValues(value).builder();
Value tag = Value.newBuilder().setListValue(listValue).build();
tagMap.put("input_lengths", tag);
// 容器内接收到的参数为{"meta":{"tags": tag }}, tag的类型可以为double、str、list、dict
// 例如此处为{"meta":{"tags":{"input_lengths": [5]}}}
PredictionProtos.Meta meta = PredictionProtos.Meta.newBuilder().putAllTags(tagMap).build();
predictRequestBuilder.setMeta(meta);

// request对象
PredictionProtos.SeldonMessage request = predictRequestBuilder.build();
```
### seldonMessage参数-返回
```java
// 1、模型预测结果
SeldonMessage response; 
// 2、获取结果数据，包含三种类型的结果数据：数组(list、numpyarray)、String、Bytes
// 2.1、获取数组类型结果数据
PredictionProtos.DefaultData reponseListData = seldonMessage.getData();
List<Double> listValue = reponseListData.getTensor().getValuesList();
// 2.2、获取String类型结果数据        
String reponseStringData = seldonMessage.getStrData();
// 2.3、获取Bytes类型结果数据
com.google.protobuf.ByteString reponseBytesData = seldonMessage.getBinData();
byte[] bytesValue = reponseBytesData.toByteArray();
```
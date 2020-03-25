# Tensorflow-Serving添加自定义operate
模型中存在自定义operate，需要使用Tensorflow-Serving中，下面介绍如何在Tensorflow-Serving添加自定义op，下面所有操作以Tensorflow-Serving 1.12.0版本为例。
## 修改Tensorflow-Serving源码
https://github.com/tensorflow/serving/blob/r1.12
### 修改BUILD
BUILD文件路径：tensorflow-serving/tensorflow_serving/model_servers/BUILD  
修改内容如下所示：
```
## 28行添加如下内容
## custom op add  start 
## --define framework_shared_object=true需要添加使用以下两个
cc_import(
    name = "frameso",
    shared_library = "@org_tensorflow//tensorflow:libtensorflow_framework.so",
)

cc_import(
    name = "forestso",
    shared_library = "@org_tensorflow//tensorflow/contrib/tensor_forest:libforestprotos.so",
)
## custom op add  end


## 添加linkopts = ["-rdynamic"],
cc_library(
    name = "tensorflow_model_server_main_lib",
    srcs = [ 
        "main.cc",
    ],  
    hdrs = [ 
        "version.h",
    ],  
    linkstamp = "version.cc",
    visibility = [ 
        ":tensorflow_model_server_custom_op_clients",
        "//tensorflow_serving:internal",
    ],  
    linkopts = ["-rdynamic"],
    deps = [ 
        ":server_lib",
        "@org_tensorflow//tensorflow/c:c_api",
        "@org_tensorflow//tensorflow/core:lib",
        "@org_tensorflow//tensorflow/core/platform/cloud:gcs_file_system",
        "@org_tensorflow//tensorflow/core/platform/hadoop:hadoop_file_system",
        "@org_tensorflow//tensorflow/core/platform/s3:s3_file_system",
    ],  
)

## custom op add frameso forestso
## frameso/forestso 跟 第28行内容对应，表示tensorflow_model_server需要使用这两个库
cc_binary(
    name = "tensorflow_model_server",
    stamp = 1,
    visibility = ["//tensorflow_serving:internal"],
    deps = [ 
        ":tensorflow_model_server_main_lib",
        ":frameso",
        ":forestso",
    ],  
)
```
### 修改main.cc文件
main.cc文件路径：tensorflow-serving/tensorflow_serving/model_servers/main.cc
修改内容如下所示：  
将需要添加的内容前后代码都展示
```C++
// 添加头文件
#include "tensorflow/core/platform/load_library.h"

// librarypath定义
int main(int argc, char** argv) {
  tensorflow::serving::main::Server::Options options;
  bool display_version = false;
  std::string librarypath;
  std::vector<tensorflow::Flag> flag_list = {
      tensorflow::Flag("port", &options.grpc_port,
                       "Port to listen on for gRPC API"),

      tensorflow::Flag("enable_batching", &options.enable_batching,
                       "enable batching"),
      // 添加loadlibrary参数
      tensorflow::Flag("loadlibrary", &librarypath,
                        "load additional operators from given user_op library"),
      tensorflow::Flag("batching_parameters_file",
                       &options.batching_parameters_file,
                       "If non-empty, read an ascii BatchingParameters "
                       "protobuf from the supplied file name and use the "
                       "contained values instead of the defaults.")
                       

  if (display_version) {
    std::cout << "TensorFlow ModelServer: " << TF_Serving_Version() << "\n"
              << "TensorFlow Library: " << TF_Version() << "\n";
    return 0;
  }
  // 添加librarypath处理逻辑
  if (librarypath.size() > 0) {
    TF_Status* status = TF_NewStatus();
    // Load the library.
      TF_LoadLibrary(librarypath.c_str(), status);
      if (!TF_GetCode(status) == TF_OK) {
        std::string status_msg(TF_Message(status));
        std::cout << "Problem loading user_op library " <<  librarypath << ": " <<
            TF_Message(status);
        return -1;
    }
    TF_DeleteStatus(status);
  }
  tensorflow::port::InitMain(argv[0], &argc, &argv);
  if (argc != 1) {
    std::cout << "unknown argument: " << argv[1] << "\n" << usage;
  }
```
参考链接：https://github.com/bknl/serving/commit/72ccb799d3fd6e35b2acfcde72e33737fdb723a9
## 编译Tensorflow-Serving源码，
### Dockerfile
将修改好的BUILD，main.cc放在deps目录下  
```
FROM tensorflow/serving:1.12.0-devel-gpu

COPY ./deps /opt/

RUN cp -rf /opt/BUILD /tensorflow-serving/tensorflow_serving/model_servers && \
    cp -rf /opt/main.cc /tensorflow-serving/tensorflow_serving/model_servers && \
    ln -s /usr/local/cuda/lib64/stubs/libcuda.so /usr/local/cuda/lib64/stubs/libcuda.so.1 && \
    LD_LIBRARY_PATH=/usr/local/cuda/lib64/stubs:${LD_LIBRARY_PATH} \
    bazel build --color=yes --curses=yes --define framework_shared_object=true --config=cuda --copt="-fPIC"\
    --verbose_failures \
    --output_filter=DONT_MATCH_ANYTHING \
    --config=nativeopt \
    tensorflow_serving/model_servers:tensorflow_model_server && \
    cp bazel-bin/tensorflow_serving/model_servers/tensorflow_model_server \
    /usr/local/bin/ && \
    rm /usr/local/cuda/lib64/stubs/libcuda.so.1

RUN libtensorflow_framework_path=`ldd /tensorflow-serving/bazel-bin/tensorflow_serving/model_servers/tensorflow_model_server | grep libtensorflow_framework.so | awk '{print $3}'` && \
    cp $libtensorflow_framework_path /usr/lib/ && \
    libforestprotos_path=`ldd /tensorflow-serving/bazel-bin/tensorflow_serving/model_servers/tensorflow_model_server | grep libforestprotos.so | awk '{print $3}'` && \
    cp $libforestprotos_path /usr/lib/

## 动态链接库放在/usr/lib下,设置LD_LIBRARY_PATH
ENV LD_LIBRARY_PATH /usr/lib/:${LD_LIBRARY_PATH}
#RUN bazel clean --expunge --color=yes && \
#    rm -rf /root/.cache

RUN rm /etc/localtime && cp /opt/localtime /etc && rm /opt/localtime

# Tensorflow-serving
EXPOSE 8866

# 第一次改进版, 将启动脚本添加镜像
CMD ["/opt/startCMD.sh"]
```
### 构建镜像
```
docker build -t tensorflow/serving:1.12.0-devel-gpu-custom .
```
### 启动程序
注意此程序需要打包到镜像/opt目录下  
```bash
#!/bin/bash
#
#  启动任务脚本

## 这里需要配置自定义operate动态链接库文件位置
loadlibrary=""
tensorflow_model_server --port=8866 --model_name=$modelName --model_base_path=$exportModel --loadlibrary=$loadlibrary
```
## 编译自定义op的so
以https://github.com/mgharbi/hdrnet_legacy为例，目前只有1.12版本tensorflow-serving GPU版本支持自定义operate，因此只能使用自定义operate编译只能在tensorflow 1.12 GPU版本环境下编译
### 以Tensorflow 1.12 GPU环境编译
#### pull镜像环境
使用docker镜像环境：docker pull tensorflow/tensorflow:1.12.0-gpu
#### 启动镜像实例
在此镜像里面编译已经写好的C++ operate源码  
NV_GPU=0 nvidia-docker run -itd -v /opt:/opt tensorflow/tensorflow:1.12.0-gpu bash  
注意挂载operate源码到镜像内部，这里只是拿/opt 来举例  
nvidia-docker是docker插件需要个人安装，挂载GPU需要安装  
NV_GPU=0 指定挂载Nvidia GPU 0卡到镜像实例中
#### 编译源码
编译按照官网给的例子来编译，
https://www.tensorflow.org/guide/extend/op#compile_the_op_using_your_system_compiler_tensorflow_binary_installation  
下面是hdrnet Makefile，稍微改了一下，不过还是跟官网介绍的方式来编译的
##### hdrnet Makefile
```
TF_INC ?= `python -c 'import tensorflow as tf; print(tf.sysconfig.get_include())'`
# TF_INC ?= /usr/local/lib/python2.7/dist-packages/tensorflow/include
CUDA_HOME ?= /usr/local/cuda

SRC_DIR = ops

BUILD_DIR = build
LIB_DIR = lib

CC = c++ -std=c++11
## 对于 >=5 的 gcc 版本请注意：gcc 自版本 5 起使用新的 C++ ABI。TensorFlow 网站上提供的二进制 pip 软件包是使用 gcc4 编译的，该编译器使用的是旧版 ABI。
## 如果您使用 gcc>=5 编译操作库，请在命令行中添加 -D_GLIBCXX_USE_CXX11_ABI=0，以使库与旧版 ABI 兼容。
## -DNDEBUG 解决异常:[1 error detected in the compilation of "/tmp/tmpxft_000008c9_00000000-6_bilateral_slice.cu.cpp1.ii"]
NVCC = nvcc -std c++11 -DNDEBUG -D_GLIBCXX_USE_CXX11_ABI=0
CFLAGS = -fPIC -I$(TF_INC) -O2 -D_GLIBCXX_USE_CXX11_ABI=0
LDFLAGS = -L$(CUDA_HOME)/lib64 -lcudart
## -I/usr/local/ 解决[cuda/include/cuda.h: No such file or directory]异常
NVFLAGS = -DGOOGLE_CUDA=1 -x cu -Xcompiler -fPIC -I$(TF_INC) -I/usr/local/ \
					-expt-relaxed-constexpr -Wno-deprecated-gpu-targets -ftz=true


SRC = bilateral_slice.cc
CUDA_SRC = bilateral_slice.cu.cc
CUDA_OBJ = $(addprefix $(BUILD_DIR)/,$(CUDA_SRC:.cc=.o))
SRCS = $(addprefix $(SRC_DIR)/, $(SRC))

all: $(LIB_DIR)/hdrnet_ops.so

# Main library
$(LIB_DIR)/hdrnet_ops.so: $(CUDA_OBJ) $(LIB_DIR) $(SRCS)
	$(CC) -shared -o $@ $(SRCS) $(CUDA_OBJ) $(CFLAGS) $(LDFLAGS) 

# Cuda kernels
$(BUILD_DIR)/%.o: $(SRC_DIR)/%.cc $(BUILD_DIR)
	$(NVCC) -c  $< -o $@ $(NVFLAGS)

$(BUILD_DIR):
	mkdir -p $@


$(LIB_DIR):
	mkdir -p $@

clean:
	rm -rf $(BUILD_DIR) $(LIB_DIR)
```

## 根据Tensorflow-serving环境来编译，即上一步制作好的Tensorflow-Serving镜像
不推荐使用这种方式，不方便推广使用，配置比较复杂  
libforestprotos.so   libtensorflow_framework.so 编译serving生成的。
编译custom op的so需要用和serving相同的libtensorflow_framework.so，以及相同的头文件  
### libforestprotos.so/libtensorflow_framework.so位置
```
ldd /tensorflow-serving/bazel-bin/tensorflow_serving/model_servers/tensorflow_model_server | grep libtensorflow_framework.so | awk '{print $3}'
ldd /tensorflow-serving/bazel-bin/tensorflow_serving/model_servers/tensorflow_model_server | grep libforestprotos.so | awk '{print $3}'
```
### 编译Makefile
```
#TF_INC ?= `python3 -c 'import tensorflow as tf; print(tf.sysconfig.get_include())'`
TF_INC=/root/.cache/bazel/_bazel_root/e53bbb0b0da4e26d24b415310219b953/external/org_tensorflow/
EIGEN_INC=-I/root/.cache/bazel/_bazel_root/e53bbb0b0da4e26d24b415310219b953/external/eigen_archive/ -I/root/.cache/bazel/_bazel_root/e53bbb0b0da4e26d24b415310219b953/external/com_google_absl/ -I/root/.cache/bazel/_bazel_root/e53bbb0b0da4e26d24b415310219b953/external/protobuf_archive/src/ -I/root/.cache/bazel/_bazel_root/e53bbb0b0da4e26d24b415310219b953/execroot/tf_serving/bazel-out/host/genfiles/external/org_tensorflow/
#TF_LIB=`python3 -c 'import tensorflow as tf; print(tf.sysconfig.get_lib())'`
TF_LIB=/usr/lib
# TF_INC ?= /usr/local/lib/python2.7/dist-packages/tensorflow/include
CUDA_HOME ?= /usr/local/cuda

## 源代码目录
SRC_DIR = ops

BUILD_DIR = build
LIB_DIR = lib

CC = c++ -std=c++11
## 对于 >=5 的 gcc 版本请注意：gcc 自版本 5 起使用新的 C++ ABI。TensorFlow 网站上提供的二进制 pip 软件包是使用 gcc4 编译的，该编译器使用的是旧版 ABI。如果您使用 gcc>=5 编译操作库，请在命令行中添加 -D_GLIBCXX_USE_CXX11_ABI=0，以使库与旧版 ABI 兼容。
## https://www.tensorflow.org/guide/extend/op#compile_the_op_using_your_system_compiler_tensorflow_binary_installation
NVCC = nvcc -std c++11 -DNDEBUG -D_GLIBCXX_USE_CXX11_ABI=0
CFLAGS = -fPIC -I$(TF_INC) $(EIGEN_INC) -O2 -D_GLIBCXX_USE_CXX11_ABI=0
LDFLAGS = -L$(CUDA_HOME)/lib64 -lcudart  -L $(TF_LIB) -ltensorflow_framework
NVFLAGS = -DGOOGLE_CUDA=1 -x cu -Xcompiler -fPIC -I$(TF_INC) $(EIGEN_INC) -I/usr/local/ \
					-expt-relaxed-constexpr -Wno-deprecated-gpu-targets -ftz=true

## 需要编译的文件
SRC = bilateral_slice.cc
CUDA_SRC = bilateral_slice.cu.cc
CUDA_OBJ = $(addprefix $(BUILD_DIR)/,$(CUDA_SRC:.cc=.o))
SRCS = $(addprefix $(SRC_DIR)/, $(SRC))

## 目标文件
all: $(LIB_DIR)/hdrnet_ops.so

# Main library
$(LIB_DIR)/hdrnet_ops.so: $(CUDA_OBJ) $(LIB_DIR) $(SRCS)
	$(CC) -shared -o $@ $(SRCS) $(CUDA_OBJ) $(CFLAGS) $(LDFLAGS) 

# Cuda kernels
$(BUILD_DIR)/%.o: $(SRC_DIR)/%.cc $(BUILD_DIR)
	$(NVCC) -c  $< -o $@ $(NVFLAGS)

$(BUILD_DIR):
	mkdir -p $@


$(LIB_DIR):
	mkdir -p $@

clean:
	rm -rf $(BUILD_DIR) $(LIB_DIR)
```

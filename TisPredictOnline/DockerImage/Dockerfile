FROM nvcr.io/nvidia/tensorrt:20.08-py3

COPY ./deps /opt/

RUN cp /opt/localtime /etc && rm /opt/localtime && cd /opt

RUN pip install pycuda tensorflow==1.15.3 torch==1.5.0 torchvision==0.6.0 onnx==1.7.0 onnxruntime==1.4.0 tf2onnx==1.7.1 -i http://mirrors.aliyun.com/pypi/simple/ --trusted-host mirrors.aliyun.com

RUN pip install keras2onnx==1.7.0  h5py==2.10.0 -i https://pypi.tuna.tsinghua.edu.cn/simple

RUN rm /etc/apt/sources.list && cp /opt/sources.list /etc/apt && \
	apt-get update && apt-get install -y locales libglib2.0-dev libsm6 libxrender1 libxext6 vim  libgl1-mesa-glx && \
	apt-get clean && rm -rf /var/lib/apt/lists/*

## 解决python中文乱码问题 https://webkul.com/blog/setup-locale-python3/
RUN locale-gen en_US.UTF-8
ENV LANG en_US.UTF-8
ENV LANGUAGE en_US:en
ENV LC_ALL en_US.UTF-8

RUN chmod  777 /opt/tis_boot.sh
CMD ["/opt/tis_boot.sh"]

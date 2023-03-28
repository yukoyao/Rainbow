### 使用 ffmpeg 给视频添加水印

#### 常用参数

* -i input_file：指定输入文件路径，可以是本地文件路径或者 URL 地址，例如 -i /path/to/input.mp4 或者 -i http://example.com/input.mp4。

* -ss start_time：指定起始时间，格式为 hh:mm:ss.xxx 或者秒数，例如 -ss 00:01:30.000 表示从 1 分 30 秒开始处理，或者 -ss 90 表示从 90 秒开始处理。

* -t duration：指定持续时间，格式为 hh:mm:ss.xxx 或者秒数，例如 -t 00:00:30.000 表示持续 30 秒，或者 -t 30 表示持续 30 秒。

* -codec[:stream_specifier] codec_name：指定编解码器，stream_specifier 是指音频或视频流的索引或者 ID，例如 -codec:a:0 libmp3lame 表示使用 libmp3lame 编码器对第一个音频流进行编码。

* -filter[:stream_specifier] filtergraph：指定过滤器，stream_specifier 是指音频或视频流的索引或者 ID，例如 -filter:v:0 "scale=640:480" 表示对第一个视频流进行缩放操作。

* -vf filtergraph：指定视频过滤器，可以使用多个过滤器，中间用逗号隔开，例如 -vf "scale=640:480,transpose=1" 表示先进行缩放操作，再进行旋转操作。

* -af filtergraph：指定音频过滤器，可以使用多个过滤器，中间用逗号隔开，例如 -af "volume=2,atempo=1.5" 表示先进行音量增大操作，再进行音频加速操作。

* -preset preset_name：指定预设参数，比如 ultrafast、fast、medium、slow、veryslow 等，不同的预设参数会影响编码速度和输出文件大小，例如 -preset fast 表示使用快速编码速度和适当的输出文件大小。

* -crf quality：指定视频质量，取值范围是 0-51，值越小质量越高，码率越大，例如 -crf 20 表示使用质量系数为 20 的压缩，质量越高输出文件越大。

* -b:v video_bitrate：指定视频码率，单位是比特率，例如 -b:v 1M 表示视频比特率为 1 Mbps。

* -b:a audio_bitrate：指定音频码率，单位是比特率，例如 -b:a 128k 表示音频比特率为 128 kbps。

* -s resolution：指定视频分辨率，格式为宽度 x 高度，例如 -s 640x480 表示输出视频的分辨率为 640x480。

* -aspect aspect_ratio：指定输出视频的纵横比，例如 -aspect 16:9 表示输出视频的宽高比为 16:9。

* -r frame_rate：指定输出视频的帧率，例如 -r 30 表示输出视频的帧率为 30 fps。

* -y：覆盖输出文件，如果指定了该参数，则会强制覆盖同名的输出文件。

* -n：不覆盖输出文件，如果指定了该参数，则如果输出文件已经存在，则不会进行覆盖。

* -loglevel log_level：指定日志级别，取值为 quiet、panic、fatal、error、warning、info、verbose、debug、trace，例如 -loglevel warning 表示只输出警告及以上级别的日志。

* -stats：显示统计信息，包括处理时间、输入输出流信息、编解码器信息等。

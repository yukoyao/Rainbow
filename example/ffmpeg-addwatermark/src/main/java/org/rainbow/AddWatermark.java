package org.rainbow;

import cn.hutool.core.io.FileUtil;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import cn.hutool.core.util.StrUtil;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.job.FFmpegJob;
import net.bramp.ffmpeg.job.FFmpegJob.State;
import net.bramp.ffmpeg.probe.FFmpegFormat;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import net.bramp.ffmpeg.probe.FFmpegStream;
import org.apache.commons.io.IOUtils;

/**
 * @author K
 */
public class AddWatermark{

  private static final String watermarkPath = "D:\\watermark.jpg"; // 水印图片路径

  public static void main(String[] args) throws IOException {
    try {
      InputStream inputStream = FileUtil.getInputStream("D:\\粉雪.mp4");

      // 将上传的文件保存到本地临时文件
      String fileName = UUID.randomUUID().toString() + ".mp4";
      String filePath = "D:\\" + fileName;
      File tempFile = new File(filePath);
      OutputStream outputStream = new FileOutputStream(tempFile);
      IOUtils.copy(inputStream, outputStream);
      IOUtils.closeQuietly(inputStream);
      IOUtils.closeQuietly(outputStream);

      // 创建FFmpeg实例并获取视频信息
      FFmpeg ffmpeg = new FFmpeg("D:\\dev_tool\\ffmpeg\\bin\\ffmpeg.exe");
      FFprobe ffprobe = new FFprobe("D:\\dev_tool\\ffmpeg\\bin\\ffprobe.exe");
      FFmpegProbeResult probeResult = ffprobe.probe(filePath);
      FFmpegFormat format = probeResult.getFormat();
      FFmpegStream videoStream = null;
      for (FFmpegStream stream : probeResult.getStreams()) {
        if (StrUtil.equalsAnyIgnoreCase(stream.codec_type.name(), "video")) {
          videoStream = stream;
        }
      }

      // 配置FFmpeg命令
      FFmpegBuilder builder = new FFmpegBuilder()
          .setInput(filePath)
          .overrideOutputFiles(true)
          .addInput(watermarkPath)
          .addOutput("D:\\output.mp4")
          .setVideoCodec(videoStream.codec_name)
          .setVideoFrameRate((int) videoStream.avg_frame_rate.doubleValue())
          .setVideoResolution(videoStream.width, videoStream.height)
          .setStrict(FFmpegBuilder.Strict.EXPERIMENTAL)
          .addExtraArgs("-filter_complex", "[1:v] scale=176:144 [logo];[0:v][logo]overlay=x=0:y=0")
          //.addExtraArgs("-map", "[v]")
          //.addExtraArgs("-map", "0:a")
          .done();

      // 执行FFmpeg命令
      FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
      executor.createJob(builder).run();

      // 删除临时文件
      FileUtil.del(filePath);
      // 将处理后的视频文件读入字节数组并返回
   /*   byte[] bytes = IOUtils.toByteArray(Files.newInputStream(new File("D:\\output.mp4").toPath()));
      System.out.println(bytes.length);*/

      // 后期如果需要上传到存储云 可以直接将字节数组进行上传
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}

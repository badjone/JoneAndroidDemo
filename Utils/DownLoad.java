package newstudiowork.newtest.UI.Utils;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 多线程下载
 *
 * @author badjone
 * @Date 2016/4/6 17:14
 */
public class DownLoad {

    // 创建线程池，指定大小
    private Executor threadPool = Executors.newFixedThreadPool(3);


    public void downLoadFile(String url, Handler handler) {
        try {
            // 根据URL实例化URL
            URL httpUrl = new URL(url);
            // 打开连接
            HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
            // 设置属性
            conn.setRequestMethod("GET");
            conn.setReadTimeout(1000 * 5);
            // 因为要通过线程池-多线程下载文件,所以应该先获取总长度，然后分配每个线程的下载大小
            int count = conn.getContentLength();
            // 假设通过三个线程下载，计算每个线程的下载大小
            int block = count / 3;
            // 遍历 计算出每个线程获取内容的起始位置

            // 获取文件名称
            String fileName = getFileName(url);
            L.d("fileName:" + fileName);
            // 指定文件的下载地址，加载到sd卡中
            File parent = Environment.getExternalStorageDirectory();
            File downFile = new File(parent, fileName);

            for (int i = 0; i < 3; i++) {
                // 算法解释 假设文件的大小为11个字节，我们通过线程池开启3个线程，这样还剩余2个字节，在最后的线程里处理
                // 第一个线程 从0开始获取到2
                // 第二个线程 从3开始获取到5
                // 第三个线程 从6到10
                long start = i * block;
                long end = (i + 1) * block - 1;
                if (i == 2) {
                    end = count;
                }
                L.d("start:" + start + ",end" + end);

                // 实例化Runnable对象
                L.d("getAbsolutePath:" + downFile.getAbsolutePath());
                DownLoadRunnnable downLoadRunnnable = new DownLoadRunnnable(url, downFile.getAbsolutePath(), start, end, handler);
                // 提交到线程池
                threadPool.execute(downLoadRunnnable);
                L.d("execute成功");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据URL的后缀 获取要获取的图片文件名称
     * http://localhost:8080/HttpService/girl.jpg
     */
    public String getFileName(String url) {
        // 获取最后一个/ 截取文件名称
        return url.substring(url.lastIndexOf("/") + 1);
    }

    /**
     * DownLoadRunnnable对象
     */
    static class DownLoadRunnnable implements Runnable {

        private String url;
        private String fileName;
        private long start;
        private long end;
        private Handler handler;

        // 构造函数
        public DownLoadRunnnable(String url, String fileName, long start, long end, Handler handler) {
            this.url = url;
            this.fileName = fileName;
            this.start = start;
            this.end = end;
            this.handler = handler;
        }

        @Override
        public void run() {
            try {
                URL downLoadURL = new URL(url);
                HttpURLConnection conn = (HttpURLConnection) downLoadURL.openConnection();
                conn.setReadTimeout(1000 * 5);
                conn.setRequestMethod("GET");
                //使用Http的Range头字段指定每条线程从文件的什么位置开始下载，下载到什么位置为止,这里指定从开始到结束
                conn.setRequestProperty("Range", "bytes=" + start + "-" + end);

                // 保存文件
                /**public RandomAccessFile(File file, String mode) throws FileNotFoundException
                 创建从中读取和向其中写入（可选）的随机访问文件流，该文件由 File 参数指定。
                 将创建一个新的 FileDescriptor 对象来表示此文件的连接。
                 mode 参数指定用以打开文件的访问模式。允许的值及其含意为：

                 值         含意
                 "r"    以只读方式打开。调用结果对象的任何 write 方法都将导致抛出 IOException。
                 "rw"   打开以便读取和写入。如果该文件尚不存在，则尝试创建该文件。
                 "rws"  打开以便读取和写入，对于 "rw"，还要求对文件的内容或元数据的每个更新都同步写入到底层存储设备。
                 "rwd"  打开以便读取和写入，对于 "rw"，还要求对文件内容的每个更新都同步写入到底层存储设备。
                 **/
                RandomAccessFile accessFile = new RandomAccessFile(new File(fileName), "rwd");
                //从文件的什么位置开始写入数据
                accessFile.seek(start);

                // 获取输入流
                InputStream ins = conn.getInputStream();
                // 设置缓冲区域
                byte[] b = new byte[1024 * 4];
                int len;
                // 循环读取写入accessFile
                while ((len = ins.read(b)) != -1) {
                    accessFile.write(b, 0, len);
                }
                L.d("write 成功");
                // 关闭流
                if (accessFile != null) {
                    accessFile.close();
                }
                if (ins != null) {
                    ins.close();
                }

                // 发送消息通知主线程完成
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
                L.d("message发送成功");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
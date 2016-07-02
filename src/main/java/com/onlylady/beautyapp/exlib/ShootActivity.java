package com.onlylady.beautyapp.exlib;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.onlylady.beautyapp.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ShootActivity extends Activity implements SurfaceHolder.Callback {

    private Button button_start;
    private Button button_stop;
    private Button button_back;
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private File storageDir;
    private File tempFile;
    private List<String> lists = new ArrayList<String>();
    private MediaRecorder mediaRecorder;
    private Camera camera;
    // private Spinner spinner;
    private int width;
    private int height;
    private Handler handler;
    private TextView textView_time;
    private Handler dialog_handler;
    private Runnable uploadRunnable;
    private ProgressDialog dialogProgress;
    private Thread uploadThread;
    private int time = 5 * 1000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // the window without title
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // the display orientation
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.layout_shoot);
//		getMP4Tegether();


        button_start = (Button) this.findViewById(R.id.button_start);
        button_stop = (Button) this.findViewById(R.id.button_stop);
        button_back = (Button) this.findViewById(R.id.button_back);
        surfaceView = (SurfaceView) this.findViewById(R.id.surfaceView);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(ShootActivity.this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        // spinner = (Spinner)this.findViewById(R.id.spinner_size);
        width = 1280;
        height = 720;
        handler = new Handler();
        textView_time = (TextView) this.findViewById(R.id.textView_time);
        dialogProgress = new ProgressDialog(ShootActivity.this);


        startcamera();

        button_start.setOnClickListener(btnStartListener);
        button_stop.setOnClickListener(btnStopListener);
        // button_back.setOnClickListener(btnBackListener);
        // spinner.setOnItemSelectedListener(spinnerListener);

        boolean sdCardExist = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
        if (sdCardExist) {
            storageDir = new File(Environment.getExternalStorageDirectory()
                    .toString() + File.separator + "IVC" + File.separator);
            if (!storageDir.exists()) {
                storageDir.mkdir();
            }
            button_stop.setEnabled(false);
        } else {
            button_start.setEnabled(false);

        }
    }

    private void startcamera() {
        try {
            if (camera == null) {
                camera = Camera.open();
                camera.lock();
                // camera.stopPreview();
                Camera.Parameters para = camera.getParameters();
                para.setPreviewSize(width, height);
                camera.setParameters(para);
                camera.setPreviewDisplay(surfaceHolder);
                camera.startPreview();
                camera.unlock();
            } else {
                camera.unlock();
            }

            //
        } catch (IOException e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
        }
    }

    private View.OnClickListener btnStartListener = new View.OnClickListener() {

        public void onClick(View v) {
            start();
        }

    };

    private View.OnClickListener btnStopListener = new View.OnClickListener() {

        public void onClick(View v) {
            stop();

        }

    };

    private Runnable refreshTime = new Runnable() {

        public void run() {
            // TODO Auto-generated method stub

            stop();
        }
    };


    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        // TODO Auto-generated method stub

    }

    public void surfaceCreated(SurfaceHolder holder) {

    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        try {

            mediaRecorder.stop();
            mediaRecorder.release();
            camera.release();

            Log.e("info", tempFile.getAbsolutePath());
        } catch (RuntimeException e) {
            // Log.e("DEBUG", e.getMessage());
        }
        //camera.release();

    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub

        super.onDestroy();
        handler.removeCallbacks(refreshTime);
    }

    private void start() {

        handler.postDelayed(refreshTime, time);

        try {

//			//
//			if (tempFile.exists()) {
//				if (count % 2 != 0) {
//					tempFile = new File(
//							Environment.getExternalStorageDirectory(), "2.mp4");
//				} else {
//					tempFile = new File(
//							Environment.getExternalStorageDirectory(), "3.mp4");
//				}
//			}
//			Log.i("info", "-------->" + tempFile.getAbsolutePath());

            if (TextUtils.isEmpty(desName)) {
                tempFile = new File("/storage/sdcard0/xyz.mp4");
                desName = tempFile.getAbsolutePath();
            } else {
                tempFile = File.createTempFile("IVC", ".3gp", storageDir);
            }
            lists.add(tempFile.getAbsolutePath());
            Log.i("info", "-------->" + tempFile.getAbsolutePath() + lists.size());
            mediaRecorder = new MediaRecorder();
            mediaRecorder.setCamera(camera);

            mediaRecorder.setPreviewDisplay(surfaceHolder.getSurface());
            mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);// 视频源
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);// 音频源
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);// 视频输出格式
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);// 音频格式
            mediaRecorder.setVideoSize(width, height);// 设置分辨率：
            // mMediaRecorder.setVideoFrameRate(16);// 这个我把它去掉了，感觉没什么用
            mediaRecorder.setVideoEncodingBitRate(2 * 1024 * 1024);// 设置帧频率，然后就清晰了
//	        mediaRecorder.setOrientationHint(90);// 输出旋转90度，保持竖屏录制  
            mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);// 视频录制格式

			
			
			/*mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
			mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
			mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
			mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
			mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
			mediaRecorder.setVideoSize(width, height);
			mediaRecorder.setVideoFrameRate(16);*/


            mediaRecorder.setPreviewDisplay(surfaceHolder.getSurface());
            mediaRecorder.setOutputFile(tempFile.getAbsolutePath());
            mediaRecorder.prepare();
            mediaRecorder.start();

        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    private void stop() {
        if (mediaRecorder != null) {
            try {

                mediaRecorder.stop();
                mediaRecorder.release();
                // camera.release();

                Log.e("info", "stop---" + tempFile.getAbsolutePath());


//                appendVideo();
            } catch (RuntimeException e) {
                // Log.e("DEBUG", e.getMessage());
            }


            startcamera();
            start();
        }
    }


    private String desName = "";
    private  boolean startpush = false;
    /*private void appendVideo() {
        //String[] videos = {"/storage/sdcard0/1.mp4","/storage/sdcard0/2.mp4"};
//		String desName="/storage/sdcard0/xyz.mp4";
        try {
            File desFile = new File(desName);
            Movie[] inMovies = new Movie[lists.size()];

            int index = 0;
            if (lists.size() < 2) {
                return;
            }
            startpush = true;
            if (!startpush){
                startpush = true;
                LogUtils.Log("开始push",getApplicationContext());
                RtmpPush.push(desName,"rtmp://w.gslb.lecloud.com/live/20160118300023399?sign=925e26f479de7306d9c9192677fb34a1&tm=20160118100131");
            }
            for (String video : lists) {
                inMovies[index] = MovieCreator.build(video);
                index++;
            }
            List<Track> videoTracks = new LinkedList<Track>();
            List<Track> audioTracks = new LinkedList<Track>();
            for (Movie m : inMovies) {
                for (Track t : m.getTracks()) {
                    if (t.getHandler().equals("soun")) {
                        audioTracks.add(t);
                    }
                    if (t.getHandler().equals("vide")) {
                        videoTracks.add(t);
                    }
                }
            }
            Movie result = new Movie();
            if (audioTracks.size() > 0) {
                result.addTrack(new AppendTrack(audioTracks.toArray(new Track[audioTracks.size()])));
            }
            if (videoTracks.size() > 0) {
                result.addTrack(new AppendTrack(videoTracks.toArray(new Track[videoTracks.size()])));
            }
            Container out = new DefaultMp4Builder().build(result);
            FileChannel fc = new RandomAccessFile(String.format(desName), "rw").getChannel();
            out.writeContainer(fc);
            fc.close();
            for (int i = 0; i < lists.size(); i++) {
                String video = lists.get(i);
                if (!video.equals(desName)) {
                    File file = new File(video);
//						file.delete();
                    lists.remove(i);
                    Log.i("info", file.getAbsolutePath() + "s删除");
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        }


    }*/

}

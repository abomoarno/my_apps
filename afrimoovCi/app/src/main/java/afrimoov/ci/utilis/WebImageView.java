package afrimoov.ci.utilis;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.AttributeSet;

import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;

import afrimoov.ci.utilis.Utils;

public class WebImageView extends android.support.v7.widget.AppCompatImageView implements Serializable {
    public Context context;
    public WebImageView(Context context) {
        this(context, null);
    }
    public WebImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public WebImageView(Context context, AttributeSet attrs,
                        int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
    }
    public void setImageUrl(String url) {
        DownloadTask task = new DownloadTask();
        task.execute(url);
    }
    public void setImageUrl2(String url) {
        DownloadTask2 task = new DownloadTask2();
        task.execute(url);
    }
    private class DownloadTask extends
            AsyncTask<String, Void, Bitmap> {
        private String url;
        @Override
        protected Bitmap doInBackground(String... params) {
            url = params[0];
            try {
                URLConnection connection =
                        (new URL(url)).openConnection();
                InputStream is = connection.getInputStream();
                return BitmapFactory.decodeStream(is);
            } catch (Exception exc) {
                return null;
            }
        }
        @Override
        protected void onPostExecute(final Bitmap result) {
            if(result!=null)
                setImageBitmap(result);
            }
    }
    private class DownloadTask2 extends
            AsyncTask<String, Void, Bitmap> {
        private String url;
        @Override
        protected Bitmap doInBackground(String... params) {
            url = params[0];
            try {
                URLConnection connection =
                        (new URL(url)).openConnection();
                InputStream is = connection.getInputStream();
                return BitmapFactory.decodeStream(is);
            } catch (Exception exc) {
                return null;
            }
        }
        @Override
        protected void onPostExecute(final Bitmap result) {
            setImageBitmap(result);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String url1 = url.substring(url.lastIndexOf("/")+1);
                    Utils.saveImage(context,result,url1);
                }
            }).start();

        }
    }
}

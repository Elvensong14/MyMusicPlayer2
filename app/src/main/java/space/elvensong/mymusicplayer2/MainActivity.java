package space.elvensong.mymusicplayer2;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer player;
    private EditText urlEdit;
    private ProgressBar progressBar;
    int duration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        urlEdit = findViewById(R.id.urlEdit);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
    }

    /** Called when the play button is clicked. */
    public void playClicked(View view) {
        if (player == null || !player.isPlaying()) {
            player = MediaPlayer.create(this, Uri.parse(urlEdit.getText().toString()));

            duration = player.getDuration();
            progressBar.setMax(duration);
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(0);

            if (player != null) {
                player.start();
                toast("Playing.");
                //--
                //-- TODO: WRITE YOUR CODE HERE
                //--

                new MyAsyncTask().execute(duration);

                //showProgressAsyncTask(player);
                //showProgressThread(player);
                //showProgressHandler(player);
                //--
            } else {
                toast("Failed!");
            }
        }
    }

    /** Called when the stop button is clicked. */
    public void stopClicked(View view) {
        if (player != null && player.isPlaying()) {
            player.stop();
            progressBar.setVisibility(View.INVISIBLE);
//            player.release(); // or reset?  or none at all (due to crash)
            player.reset();  // works
            toast("Stopped.");
        }
    }

    /** Shows a toast message. */
    private void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    //--
    //-- WRITE YOUR CODE HERE
    //--

    private class MyAsyncTask extends AsyncTask<Integer, Integer, String> {

        int count = 0;
        boolean isFinished = false;

        @Override
        protected void onPreExecute() {

//      …
        }

        @Override
        protected String doInBackground(Integer... params) {

            for (; count <= duration;)  {
                try {
                    Thread.sleep(500);
                    count = player.getCurrentPosition();
                    publishProgress(count);
                } catch (InterruptedException e)  {
                    e.printStackTrace();
                }
            }
            return "Task Completed";
        }

        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            progressBar.setVisibility(View.GONE);
        }

        @Override
        protected void onCancelled()  {

        }
    }

}




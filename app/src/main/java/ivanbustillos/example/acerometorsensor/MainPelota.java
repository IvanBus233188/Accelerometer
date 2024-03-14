package ivanbustillos.example.acerometorsensor;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MainPelota extends AppCompatActivity {
    MiPelota dibujo;

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        dibujo= new MiPelota(this);
        setContentView(dibujo);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
}

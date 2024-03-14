package ivanbustillos.example.acerometorsensor;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

public class MiPelota extends View implements SensorEventListener {

    Paint pincel = new Paint();
    int alto, ancho;
    int tamanio = 40;
    int borde = 12;
    float ejeX, ejeY, ejeZ;
    SharedPreferences sharedPreferences;

    public MiPelota(Context interfaz) {
        super(interfaz);
        SensorManager smAdministrador = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
        Sensor snsRotacion = smAdministrador.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        smAdministrador.registerListener(this, snsRotacion, SensorManager.SENSOR_DELAY_FASTEST);
        Display pantalla = ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        ancho = pantalla.getWidth();
        alto = pantalla.getHeight();

        // Inicializar SharedPreferences
        sharedPreferences = getContext().getSharedPreferences("coordenadas_pelota", Context.MODE_PRIVATE);

        // Obtener las coordenadas guardadas
        ejeX = sharedPreferences.getFloat("posicion_x", ancho / 2);
        ejeY = sharedPreferences.getFloat("posicion_y", alto / 2);
    }

    @Override
    public void onSensorChanged(SensorEvent cambio) {
        ejeX -= cambio.values[0];
        if (ejeX < (tamanio + borde)) {
            ejeX = (tamanio + borde);
        } else if (ejeX > (ancho - (tamanio + borde))) {
            ejeX = ancho - (tamanio + borde);
        }

        ejeY += cambio.values[1];
        if (ejeY < (tamanio + borde)) {
            ejeY = (tamanio + borde);
        } else if (ejeY > (alto - tamanio - 170)) {
            ejeY = alto - tamanio - 170;
        }

        ejeZ = cambio.values[2];

        // Guardar las coordenadas actualizadas
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat("posicion_x", ejeX);
        editor.putFloat("posicion_y", ejeY);
        editor.apply();

        invalidate();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // No necesitas implementar nada aquí para este ejemplo
    }

    @Override
    protected void onDraw(Canvas lienzo) {
        pincel.setColor(Color.RED);
        lienzo.drawCircle(ejeX, ejeY, ejeZ + tamanio, pincel);
        pincel.setColor(Color.WHITE);
        pincel.setTextSize(25);
        lienzo.drawText("Ivan", ejeX - 20, ejeY + 10, pincel); // Ajustar la posición del texto
    }
}


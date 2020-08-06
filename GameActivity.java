package galaxy.vendur.guessthenumber;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class GameActivity extends AppCompatActivity {

    Dialog dialog;

    private long backPressedTime;
    private Toast backToast;
    private MediaPlayer plusSound, minusSound, checkSound, restartSound;

    Button button_plus, button_minus, button_check, button_restart;
    TextView number, title;

    int current_number, number_to_guess, tries;

    Random r;

    public void soundPlay(MediaPlayer sound){
        sound.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        plusSound = MediaPlayer.create(this, R.raw.plus);
        minusSound = MediaPlayer.create(this, R.raw.minus);
        checkSound = MediaPlayer.create(this,R.raw.check);
        restartSound = MediaPlayer.create(this, R.raw.restart);

        r = new Random();
        number_to_guess = r.nextInt(51);

        button_check = (Button) findViewById(R.id.button_check);
        button_plus = (Button) findViewById(R.id.button_plus);
        button_minus = (Button) findViewById(R.id.button_minus);
        button_restart = (Button) findViewById(R.id.button_restart);
        number = (TextView) findViewById(R.id.number);
        title = (TextView) findViewById(R.id.title);

        button_restart.setVisibility(View.INVISIBLE);

        button_restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button_check.setEnabled(true);
                button_check.setVisibility(View.VISIBLE);
                button_restart.setVisibility(View.INVISIBLE);
                button_restart.setEnabled(false);
                button_plus.setEnabled(true);
                button_minus.setEnabled(true);
                current_number = 0;
                number.setText("0");
                tries = 1;
                number_to_guess = r.nextInt(51);
                title.setText("УГАДАЙ ЧИСЛО!");

                soundPlay(restartSound);
            }
        });

        tries = 1;
        current_number = 0;
        number.setText("" + current_number);


        button_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(current_number == number_to_guess){
                    title.setText(getString(R.string.win) + " " + tries + " " + getString(R.string.text_tries));
                    button_check.setEnabled(false);
                    button_check.setVisibility(View.INVISIBLE);
                    button_plus.setEnabled(false);
                    button_minus.setEnabled(false);
                    button_restart.setVisibility(View.VISIBLE);
                    button_restart.setEnabled(true);
                } else if(current_number > number_to_guess){
                    title.setText("МОЁ ЧИСЛО ПОМЕНЬШЕ ТВОЕГО, ПОПРОБУЙ ЕЩЁ.");
                    tries++;
                } else if(current_number < number_to_guess){
                title.setText("МОЁ ЧИСЛО БОЛЬШЕ ТВОЕГО.");
                tries++;
            }
                soundPlay(checkSound);
            }
        });

        button_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(current_number < 50){
                    current_number++;
                }
                number.setText("" + current_number);
                soundPlay(plusSound);
            }
        });

        button_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(current_number > 0){
                    current_number--;
                }
                number.setText("" + current_number);
                soundPlay(minusSound);
            }
        });


        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        //Вызов ДИАЛОГ.ОКНА - начало
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.previewdialog); //путь к макету диалог.окна
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); //прозрачный фон диалог.окна
        dialog.setCancelable(false); //окно нельзя закрыть сис.кнопкой НАЗАД

        //кнопка которая закрывает ДИАЛОГ.ОКНО - начало
        TextView btn_close = (TextView)dialog.findViewById(R.id.btn_close);
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{

                }catch (Exception e){

                }
                dialog.dismiss(); //закрываем диалог окно
                soundPlay(plusSound);
            }
        });
        //кнопка которая закрывает ДИАЛОГ.ОКНО - конец

        //кнопка "Продолжить" - начало
        Button btn_continue = (Button)dialog.findViewById(R.id.btn_continue);
        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss(); //закрываем диалог.окно
                soundPlay(plusSound);
            }
        });
        //кнопка "Продолжить" - конец

        dialog.show(); //показать диалог.окно
        //Вызов ДИАЛОГ.ОКНА - конец
    }


    //сис.кнопка НАЗАД - начало
    @Override
    public void onBackPressed() {


        if(backPressedTime + 2000 > System.currentTimeMillis()){
            backToast.cancel();
            super.onBackPressed();
            return;
        } else  {
            backToast = Toast.makeText(getBaseContext(), "Нажмите ещё раз, чтобы выйти", Toast.LENGTH_SHORT);
            backToast.show();
        }

        backPressedTime = System.currentTimeMillis();
    }
    //сис.кнопка НАЗАД - конец
}

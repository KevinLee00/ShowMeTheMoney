package edu.ucsb.cs.cs185.nivek325.showmethemoney;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.rengwuxian.materialedittext.MaterialEditText;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        final Intent intent = new Intent(this, MainActivity.class);

        final MaterialEditText editText = (MaterialEditText) findViewById(R.id.name_edit_text);
        editText.setTextColor(ContextCompat.getColor(this, R.color.white));

        Button button = (Button) findViewById(R.id.welcome_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("name", editText.getText().toString());
                startActivity(intent);
                finish();
            }
        });
    }
}

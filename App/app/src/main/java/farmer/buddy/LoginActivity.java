package farmer.buddy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class LoginActivity extends AppCompatActivity {
  @Override
  public void onCreate(Bundle savedInstanceState) {
    System.out.println("LoginActivity");
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    EditText username = (EditText)findViewById(R.id.UsernameField);
    EditText password = (EditText)findViewById(R.id.PasswordField);
    Button login = (Button)findViewById(R.id.LoginBTN);
    login.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        hideSoftKeyboards(LoginActivity.this, v);
        setContentView(R.layout.activity_loading);
        user.login(getString(R.string.serverDomain), username.getText().toString(), password.getText().toString());
        if (!user.check()) {
          Toast.makeText(LoginActivity.this, "Login failed. Try again", Toast.LENGTH_SHORT).show();
        } else {
          LoginActivity.this.finish();
          startActivity(new Intent(LoginActivity.this, MenuActivity.class));
        }
        setContentView(R.layout.activity_login);
      }
    });
    ((ConstraintLayout)findViewById(R.id.activity_login)).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        hideSoftKeyboards(LoginActivity.this, v);
      }
    });
  }
  public void hideSoftKeyboards(android.app.Activity activity, View view) {
    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
  }
}

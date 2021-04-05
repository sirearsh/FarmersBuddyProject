  package farmer.buddy;
  
  import android.content.Intent;
  import android.os.Bundle;
  
  import androidx.appcompat.app.AppCompatActivity;
  
  public class MainActivity extends AppCompatActivity {
    private Intent loginIntent;
    private Intent menuIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
      System.out.println("MainActivity");
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
      loginIntent = new Intent(MainActivity.this, LoginActivity.class);
      menuIntent = new Intent(MainActivity.this, MenuActivity.class);
      if (!user.check()) {
        startActivity(loginIntent);
      } else {
        startActivity(menuIntent);
      }
      MainActivity.this.finish();
    }
  }
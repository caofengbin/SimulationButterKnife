package cfb.com.simulationbutterknife.sample1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import cfb.com.simulationbutterknife.R;

/**
 * 通过反射实现的ButterKnife效果测试Activity
 */

@ContentView(R.layout.activity_reflect_impl)
public class ReflectImplActivity extends AppCompatActivity {

    @BindView(R.id.testButton)
    private Button mButton;

    @OnClick(R.id.testButton)
    private void onClick(View view) {
        mButton.setText("我是click后的文字内容");
        Toast.makeText(this, "按钮被点击了", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewInjectUtils.bind(this);
        mButton.setText("我是click前的Button内容");
    }
}

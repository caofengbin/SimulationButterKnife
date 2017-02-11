package cfb.com.simulationbutterknife.sample2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

import com.annotation.BindView;
import com.annotation.OnClick;
import com.mybutterkinfe.api.MyButterKnife;

import cfb.com.simulationbutterknife.R;
import cfb.com.simulationbutterknife.sample1.ContentView;

/**
 * 通过apt技术实现的ButterKnife效果测试Activity
 */
@ContentView(R.layout.activity_apt_impl)
public class AptImplActivity extends AppCompatActivity {

    @BindView(R.id.button1)
    Button mButton1;

    @BindView(R.id.button2)
    Button mButton2;

    @OnClick({R.id.button1})
    public void click() {
        Toast.makeText(this, "点击了第一个按钮", Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.button2})
    public void click2() {
        Toast.makeText(this, "点击了第二个按钮", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_apt_impl);
        MyButterKnife.bind(this);
    }
}

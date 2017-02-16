# SimulationButterKnife
**分别通过运行时处理自定义注解和编译时处理自定义注解的方式**，来模拟实现ButterKnife的效果。
简单的实现了:

> * @ContentView：注入布局文件
> * @BindView：注入控件
> * @OnClick：注入简单的事件监听

通过自己实现的注解处理器，可以实现如下的使用效果：

``` java
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
```

主要涉及技术包括：
> * 注解处理器的使用
> * JavaPoet技术
> * 运行时处理注解技术
> * 编译时处理注解技术

比较有价值的参考链接：

[1.Android中使用注解](https://www.zhangningning.com.cn/blog/Android/android_rentention.html)
[2.生成Java源文件 (javawriter, javapoet, codemodel)](http://www.jianshu.com/p/ae0fa4761dd8)
[3.javapoet的Github地址](https://github.com/square/javapoet)
[4.annotationprocessing101](http://hannesdorfmann.com/annotation-processing/annotationprocessing101)
[5.javapoet——让你从重复无聊的代码中解放出来](http://www.jianshu.com/p/95f12f72f69a)
[6.Compilation Overview](http://openjdk.java.net/groups/compiler/doc/compilation-overview/index.html)
[7.深入理解 ButterKnife，让你的程序学会写代码](https://zhuanlan.zhihu.com/p/22248846)
# RxUtil

自己生成Api.class文件

Api中这么写
```Java
public interface Api {
    //获取主页banner
    @GET("api/Ad/index")
    Observable<HomeBannerResponse> getHomeBanner();
}
```

Application中这么写
```Java
public class APP extends Application {
    public static Api mApi;

    @Override
    public void onCreate() {
        super.onCreate();
        if (mApi == null) {
            mApi=RxJavaUtil.retrofitBuilder.baseUrl("http://www.yunzhiju.cn/").build().create(Api.class);
        }
    }
}
```

调用的时候这么写
```Java
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //RxJavaUtil.retrofit.create(Api.class);
        new Request<HomeBannerResponse>().request(APP.mApi.getHomeBanner(), "tag", this, true, new Result<HomeBannerResponse>() {
            @Override
            public void get(HomeBannerResponse response) {
                HomeBannerResponse response1=response;
            }
        });
    }
```

# RxUtil 
### RxJava+Retrofit的二次封装和使用
###### compile 'com.github.FizzClown:RxUtil:e6c2fbba63'

自己生成Api.class文件

Api中这么写
```Java
public interface Api {
    //获取主页banner
    @GET("api/Ad/index")
    Observable<HomeBannerResponse> getHomeBanner();
    
    //单文件上传
    @Multipart
    @POST("api/Friends/addSays")
    Observable<BaseResponse> publish(@Part MultipartBody.Part file);
    
    //多文件上传
    @Multipart
    @POST("api/Friends/addSays")
    Observable<BaseResponse> publish(@PartMap Map<String, RequestBody> file);
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
            //替换成你自己的url，以斜杠结尾
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
       
        new Request<HomeBannerResponse>().request(APP.mApi.getHomeBanner(), "tag", this, true, new Result<HomeBannerResponse>() {
            @Override
            public void get(HomeBannerResponse response) {
                HomeBannerResponse response1=response;
            }
        });
    }
```
###### "tag"是打印日志的标签  this是Context  true：是否显示ProgressDialog  最后是接收到数据的回调
###### 其中HomeBannerResponse是泛型可以换成自己的实体类

##### 单文件上传
```Java
RequestBody requestFile = RequestBody.create(MediaType.parse("image/png"), file);
MultipartBody.Part part = MultipartBody.Part.createFormData("fpic", file.getName(), requestFile);
new Request<BaseResponse>().request(APP.mApi.publis(part), "tag", this, true, new Result<BaseResponse>() {
    @Override
    public void get(BaseResponse response) {
        
    }
});
```
##### 多文件上传
```Java
Map<String, RequestBody> partMap = new HashMap<>();

for (int i = 0; i < pictures.size(); i++) {
    File file1 = new File(pictures.get(i));
    RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), file1);
    //这儿的参数就得这么拼 源码中单文件有这个字段 多文件也要加上(其中“fpic[]”是文件名称)
    partMap.put("fpic[]"+ "\"; filename=\"" + file1.getName() + "\"", fileBody);
}

new Request<BaseResponse>().request(APP.mApi.publis(partMap), "tag", this, true, new Result<BaseResponse>() {
    @Override
    public void get(BaseResponse response) {
        
    }
});
```

# RxUtil 
###### compile 'com.github.FizzClown:RxUtil:e6c2fbba63'

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
        //RxJavaUtil.retrofit.create(Api.class);
        new Request<HomeBannerResponse>().request(APP.mApi.getHomeBanner(), "tag", this, true, new Result<HomeBannerResponse>() {
            @Override
            public void get(HomeBannerResponse response) {
                HomeBannerResponse response1=response;
            }
        });
    }
```

##### 单文件上传
```Java
RequestBody requestFile = RequestBody.create(MediaType.parse("image/png"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("fpic", file.getName(), requestFile);
        sp = RxJavaUtil
                .xApi()
                .publish(User.uid(), sayName, part)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<BaseResponse>() {
                    @Override
                    public void onCompleted() {
                        sp.unsubscribe();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: " + e.toString());
                    }

                    @Override
                    public void onNext(BaseResponse response) {
                        if (response.status == 1) {
                            EventBus.getDefault().post(new PublishSay());
                            view.finish();
                        } else if (response.status == -2) {
                            ActivityManager.overdue();
                        }
                        ToastUtil.show(response.msg);
                    }
                });
                
 @Multipart
    @POST("api/Friends/addSays")
    Observable<BaseResponse> publish(@Query("user_id") int uid,
                                     @Query("say_name") String say_name,
                                     @Part MultipartBody.Part file);
```
##### 多文件上传
```Java
Map<String, RequestBody> partMap = new HashMap<>();

        for (int i = 0; i < pictures.size(); i++) {
            File file1 = new File(pictures.get(i));
            RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), file1);
            //这儿的参数就得这么拼 源码中单文件有这个字段 多文件也要加上
            partMap.put("fpic[]"+ "\"; filename=\"" + file1.getName() + "\"", fileBody);
        }

        for (String key : partMap.keySet()) {
            Log.e(TAG, "key= " + key + " and value= " + partMap.get(key));
        }
        sp1 = RxJavaUtil
                .xApi()
                .publish(User.uid(), sayName, partMap)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<BaseResponse>() {
                    @Override
                    public void onCompleted() {
                        sp1.unsubscribe();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "getDeviceData: " + e.toString());
                    }

                    @Override
                    public void onNext(BaseResponse response) {
                        if (response.status == 1) {
                            view.finish();
                        }
                        ToastUtil.show(response.msg);
                    }
                });
```

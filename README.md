# FlikrPagination

An Android app that searches images based on text using flickr api and displays them in a recyclerview. The project is designed using android architecture components.
The project has been developed using Android Studio 3.0.1.
The project is split into two gradle projects, one for UI and the other for data, to clearly separate the concerns.

It uses RecyclerView.
It uses Glide for image loading and caching.
It uses Volley for the web service calls.

It uses Android architecture components.
Room, LiveData, ViewModel ( which can withstand configuration changes and activity lifecycles).
It uses the Clean architecture by introducing an abstraction in each three mainlayers of the application namely Presentation, ViewModel(or Business logic) and the repository for the data layer. The Repository layer seemlessly supplies data either from the webservice layer or local persistence layer. 
## Architecture
![Architecture](https://github.com/sunragav/FlikrPagination/blob/master/Architecture.JPG)

The search query is made using Volley with the search text and the page number as query params.
```java
//RemoteDataSource.java
public void fetch(String searchText, int page) {
        String searchURL = String.format(URL_SEARCH, API_KEY, PAGE_SIZE, page, searchText);
        final JsonObjectRequest jsonObjReq =
                new JsonObjectRequest(Request.Method.GET, searchURL, null,
                        response -> {
                            Log.d(TAG, "Thread->" +
                                    Thread.currentThread().getName() + "\tGot some network response");
                            Log.d(TAG,"Thread->"+Thread.currentThread().getName()+"\n Response:"+response.toString());
                            final ArrayList<FlikrEntity> data = mObjMapper.mapJSONToEntity(response.toString());
                            if (data != null && data.size() > 0)
                                mDataApi.setValue(data);
                        },
                        error -> {
                            Log.d(TAG, "Thread->" +
                                    Thread.currentThread().getName() + "\tGot network error");
                            mError.setValue(error.toString());
                        });

        mQueue.add(jsonObjReq);
 }
```
The search query response data fetched from webservice (Flickr API) in the RemoteDataSource code snippet shown above, is persisted in the local storage(SQL Lite via Room) and posted to the observers via mDataMerger.post(list) call as shown in the code snippet below.
```java
//FlikrRepositoryImpl.java
public class FlikrRepositoryImpl implements FlikrRepository {
    private final RemoteDataSource mRemoteDataSource;
    private final LocalDataSource mLocalDataSource;
    private MediatorLiveData<List<FlikrModel>> mDataMerger = new MediatorLiveData<>();
    private MediatorLiveData<String> mErrorMerger = new MediatorLiveData<>();

    private FlikrRepositoryImpl(RemoteDataSource mRemoteDataSource, LocalDataSource mLocalDataSource, FlickrMapper mapper) {
        this.mRemoteDataSource = mRemoteDataSource;
        this.mLocalDataSource = mLocalDataSource;
        mMapper = mapper;
        mDataMerger.addSource(this.mRemoteDataSource.getDataStream(), entities ->
                mExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "mDataMerger\tmRemoteDataSource onChange invoked");
                        mLocalDataSource.writeData(entities);
                    }
                })
        );
        mDataMerger.addSource(this.mLocalDataSource.getDataStream(), entities ->
                mExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "mDataMerger\tmLocalDataSource onChange invoked");
                        List<FlikrModel> models = mMapper.mapEntityToModel(entities);
                        mDataMerger.postValue(models);
                    }
                })

        );
```
The data as and when written to the Room DB is updated to a LiveData observable , which is being observed by the view(MainActivity)

```java
//MainActivity.java
 private final Observer<List<FlikrModel>> dataObserver = flikrModels -> updateData(flikrModels);

 private final Observer<String> errorObserver = errorMsg -> setError(errorMsg);
 ```
 
 ```java
 //MainActivity.java
 @Override
    protected void onCreate(Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this).get(FlikrViewModel.class);
        mViewModel.getFlikerModels().observe(this, dataObserver);
        mViewModel.getErrorUpdates().observe(this, errorObserver);
 ```
 and served to recycler adapter of the app as needed.
 ```java
 //MainActivity.java
  @Override
    public void updateData(List<FlikrModel> data) {
        isLoading = false;
        mLastFetchedDataTimeStamp = System.currentTimeMillis();
        mAdapter.setItems(data);

        Log.d(TAG, "Thread->" + Thread.currentThread().getName() + "\nData Size:" + data.size() + "\nAdapter Data Size:" + mAdapter.getItemCount());
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void setError(String msg) {
        showErrorToast(msg);
    }
 ```
The data is fetched page by page as long as there is a page that exists for the search. 
It fetches 15 images per fetch and loads the pages on demand as the user reaches the end of the scroll in the recycler view. 
This is handled in the recycler view's onScrolled listener. 

```java
//MainAcitivity.java
recView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) //check for scroll down
                {
                    GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();

                    if (!isLoading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            isLoading = true;
                            Log.d(TAG, "fetchData called for page:" + page);
                            //Do pagination.. i.e. fetch new data
                            mViewModel.fetchData(searchText, page++);
                        }
                    }
                }
            }
```

Room is used as an ORM tool to interact between the java and the SQL Lite world.

## Room
![Room](https://github.com/sunragav/FlikrPagination/blob/master/Room.JPG)


## Screenshots
![FirstScreen](https://github.com/sunragav/FlikrPagination/blob/master/FirstScreen.png)
![Search](https://github.com/sunragav/FlikrPagination/blob/master/search.png)
![Results](https://github.com/sunragav/FlikrPagination/blob/master/results.png)

## Contact
sunragav@gmail.com
+91 8655444565

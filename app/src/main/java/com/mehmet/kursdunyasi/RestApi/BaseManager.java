package com.mehmet.kursdunyasi.RestApi;

/**
 * Created by MEHMET on 16.02.2019.
 */

public class BaseManager {

    protected RestApi getRestApi()
    {
        RestApiClient restApiClient = new RestApiClient(BaseUrl.URL);
        return restApiClient.getRestApi();
    }

}

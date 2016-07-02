package com.onlylady.beautyapp.engines;

import com.onlylady.beautyapp.configs.Configs;
import com.onlylady.beautyapp.urls.UrlsHolder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by caining on 16/3/30.
 */
public class GetLiveBeanFromLid {
    private static GetLiveBeanFromLid g;

    public static GetLiveBeanFromLid getInstance() {
        if (g == null) g = new GetLiveBeanFromLid();
        return g;
    }

    public interface CallbackJson {
        void finish(JSONObject json);
    }

    public void getLiveBeanLaid(final String lid, final CallbackJson call) {
        BaseEngine.getInstance().getStringGet(Configs.getDOMAINV100(), UrlsHolder.getInstance().getParams1019(lid), new BaseEngine.CallbackForT<String>() {
            @Override
            public void finish(String bean) {

                try {
                    JSONObject json = new JSONObject(bean);
                    call.finish(json.optJSONObject("data"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void finish(List<String> listT) {

            }
        });
    }
}

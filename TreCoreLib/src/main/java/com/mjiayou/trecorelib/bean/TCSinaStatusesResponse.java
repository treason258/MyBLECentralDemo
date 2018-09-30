package com.mjiayou.trecorelib.bean;


import com.mjiayou.trecorelib.bean.entity.TCSinaStatuses;
import com.mjiayou.trecorelib.json.JsonHelper;

import java.util.List;

/**
 * Created by treason on 15/3/11.
 */
public class TCSinaStatusesResponse extends TCResponse {

    private List<TCSinaStatuses> statuses;

    public static String parseString(TCSinaStatusesResponse result) {
        return JsonHelper.get().toJson(result);
    }

    public static TCSinaStatusesResponse parseObject(String data) {
        return JsonHelper.get().fromJson(data, TCSinaStatusesResponse.class);
    }

    public List<TCSinaStatuses> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<TCSinaStatuses> statuses) {
        this.statuses = statuses;
    }
}

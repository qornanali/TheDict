package id.aliqornan.thedict.util;

/**
 * Created by qornanali on 27/03/18.
 */

public interface OnLoadResult<M> {

    void onFinish();
    void onFailed(Exception e);
    void onSuccess(M data);
}

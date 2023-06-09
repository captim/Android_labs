package it_school.sumdu.edu.asynctaskandasynctaskloader;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.loader.content.AsyncTaskLoader;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class SourceCodeLoader extends AsyncTaskLoader<String> {

    private final String mQueryString;
    private final String mTransferProtocol;
    @SuppressLint("StaticFieldLeak")
    Context mContext;

    public SourceCodeLoader(@NonNull Context context, String queryString, String transferProtocol) {
        super(context);
        mContext = context;
        mQueryString = queryString;
        mTransferProtocol = transferProtocol;
    }

    @Nullable
    @Override
    public String loadInBackground() {
        return NetworkUtils.getSourceCode(mContext, mQueryString, mTransferProtocol);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }
}
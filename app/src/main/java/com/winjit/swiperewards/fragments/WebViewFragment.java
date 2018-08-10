package com.winjit.swiperewards.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.winjit.swiperewards.R;
import com.winjit.swiperewards.activities.HomeActivity;
import com.winjit.swiperewards.constants.ISwipe;
import com.winjit.swiperewards.helpers.NetworkUtil;


public class WebViewFragment extends BaseFragment {

    public WebView mWebView;

    private String url;


    public static WebViewFragment newInstance() {
        Bundle args = new Bundle();
        WebViewFragment fragment = new WebViewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            url = getArguments().getString(ISwipe.WEB_URL);
        }
        ((HomeActivity) getActivity()).setTopLayoutVisibility(ISwipe.HIDE_TOP_VIEW);

        if(!NetworkUtil.getInstance().isConnectedToInternet(getActivity())){
            showMessage(getActivity().getResources().getString(R.string.err_network));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_web_view, container, false);
        mWebView = (WebView) v.findViewById(R.id.wv_web_view);
        mWebView.loadUrl(url);

        // Enable Javascript
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Force links and redirects to open in the WebView instead of in a browser
        mWebView.setWebViewClient(new WebViewClient());

        showProgress("Loading 0%");
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int progress) {
                if (getLoader() != null) {
                    getLoader().getLoadingTextView().setText("Loading " + String.valueOf(progress) + "%");
                }
                if (progress == 100) {
                    hideProgress();
                }
            }

        });
        return v;
    }


}
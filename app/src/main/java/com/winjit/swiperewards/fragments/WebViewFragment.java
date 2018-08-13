package com.winjit.swiperewards.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatSeekBar;
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

    private WebView mWebView;
    private AppCompatSeekBar skWebPage;
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

        if (!NetworkUtil.getInstance().isConnectedToInternet(getActivity())) {
            showMessage(getActivity().getResources().getString(R.string.err_network));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_web_view, container, false);
        skWebPage = v.findViewById(R.id.sk_web_page);
        mWebView = v.findViewById(R.id.wv_web_view);
        mWebView.loadUrl(url);

        skWebPage.setProgress(50);
        // Enable Javascript
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Force links and redirects to open in the WebView instead of in a browser
        mWebView.setWebViewClient(new WebViewClient());
        skWebPage.setVisibility(View.VISIBLE);
        setWebViewClient();
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int progress) {
                skWebPage.setProgress(progress);
                if (progress == 100) {
                    skWebPage.setVisibility(View.GONE);
                }
            }

        });
        return v;
    }

    private void setWebViewClient() {
        mWebView.setWebViewClient(new WebViewClient() {

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                skWebPage.setProgress(0);
                skWebPage.setVisibility(View.VISIBLE);
            }

            public void onLoadResource(WebView view, String url) {


            }

            public void onPageFinished(WebView view, String url) {
                skWebPage.setVisibility(View.GONE);

            }

        });
    }


}
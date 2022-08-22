package com.example.newsapp.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newsapp.Article;
import com.example.newsapp.ArticlesLoader;
import com.example.newsapp.Constants;
import com.example.newsapp.R;
import com.example.newsapp.RecyclerViewAdapter;

import java.util.ArrayList;

public class SportsFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Article>> {
    RecyclerView rvSport;
    RecyclerViewAdapter adapter;
    ProgressBar loadingIndicator;
    TextView emptyText;
    ImageView noInternetImage;

    private String SPORT_URL = "https://content.guardianapis.com/search";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sports, container, false);

        rvSport = rootView.findViewById(R.id.rv_sport);
        loadingIndicator = rootView.findViewById(R.id.progress_indicator_sport);
        emptyText = rootView.findViewById(R.id.empty_text_sport);
        noInternetImage = rootView.findViewById(R.id.no_internet_sport);
        getActivity().setTitle("Sport News");
        setHasOptionsMenu(true);
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(1, null, this);

        return rootView;
    }

    @Override
    public Loader<ArrayList<Article>> onCreateLoader(int id, Bundle args) {
        Uri baseUri = Uri.parse(SPORT_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendQueryParameter("api-key", Constants.API_KEY);
        uriBuilder.appendQueryParameter("page-size", String.valueOf(Constants.PAGE_SIZE));
        uriBuilder.appendQueryParameter("page", String.valueOf(Constants.PAGE_NUMBER_SPORT));
        uriBuilder.appendQueryParameter("section", "sport");
        uriBuilder.appendQueryParameter("show-fields", "thumbnail");
        uriBuilder.appendQueryParameter("show-tags", "contributor");

        return new ArticlesLoader(getContext(), uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Article>> loader, ArrayList<Article> articles) {
        loadingIndicator.setVisibility(View.GONE);

        if (!isOnline()) {
            emptyText.setVisibility(View.VISIBLE);
            noInternetImage.setVisibility(View.VISIBLE);

            rvSport.setVisibility(View.GONE);
            emptyText.setText(R.string.no_internet);
            loadingIndicator.setVisibility(View.GONE);
        } else {
            if (articles.isEmpty()) {
                emptyText.setVisibility(View.VISIBLE);
                noInternetImage.setVisibility(View.GONE);
                rvSport.setVisibility(View.GONE);
            } else {
                emptyText.setVisibility(View.GONE);
                noInternetImage.setVisibility(View.GONE);
                rvSport.setVisibility(View.VISIBLE);
            }
            adapter = new RecyclerViewAdapter(getContext(), articles);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

            rvSport.setAdapter(adapter);
            rvSport.setLayoutManager(linearLayoutManager);

            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Article>> loader) {
        adapter = new RecyclerViewAdapter(getContext(), new ArrayList<>());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        rvSport.setAdapter(adapter);
        rvSport.setLayoutManager(linearLayoutManager);

        adapter.notifyDataSetChanged();
    }

    private boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager) getActivity()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        return networkInfo != null;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_previous && Constants.PAGE_NUMBER_SPORT > 1) {
            --Constants.PAGE_NUMBER_SPORT;
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SportsFragment()).commit();
            Toast.makeText(getContext(), R.string.page_previous, Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.nav_next && Constants.PAGE_NUMBER_SPORT <= Constants.TOTAL_PAGES_Sport) {
            ++Constants.PAGE_NUMBER_SPORT;
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SportsFragment()).commit();
            Toast.makeText(getContext(), R.string.page_next, Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
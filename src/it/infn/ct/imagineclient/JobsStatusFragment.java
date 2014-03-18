package it.infn.ct.imagineclient;

import it.infn.ct.imagineclient.net.RESTLoader;
import it.infn.ct.imagineclient.net.RESTLoader.RESTResponse;
import it.infn.ct.imagineclient.pojos.ActiveInteraction;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class JobsStatusFragment extends ListFragment implements
        LoaderCallbacks<RESTLoader.RESTResponse> {

    private static final String TAG = JobsStatusFragment.class.getSimpleName();
    private static final String SERVER_URL = "http://10.70.1.180:8080/IMAGINE/jobs/status/test";
    private ActiveInteractionsAdapter adapter;
    private ArrayList<ActiveInteraction> activeInteractions;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // If activity recreated (such as from screen rotate), restore
        // the previous article selection set by onSaveInstanceState().
        // This is primarily necessary when in the two-pane layout.
        // if (savedInstanceState != null) {
        // mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
        // }

        Uri uri = Uri.parse(SERVER_URL);
        Log.i(TAG, uri.toString());

        Bundle args = new Bundle();
        args.putParcelable("ARGS_URI", uri);

        // if (cd.isConnectingToInternet())
        getLoaderManager().initLoader(1, args, this);
        // else {
        // Dialog d = DialogFactory.getDialog(getActivity(),
        // "No internet connection",
        // "You don't have internet connection.", false, "Connect",
        // "Exit", this, this);
        // d.show();
        // }

        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.article_view, container, false);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();

        // During startup, check if there are arguments passed to the fragment.
        // onStart is a good place to do this because the layout has already
        // been
        // applied to the fragment at this point so we can safely call the
        // method
        // below that sets the article text.
        // Bundle args = getArguments();
        // if (args != null) {
        // Set article based on argument passed in
        // updateArticleView(1);
        // } //else if (mCurrentPosition != -1) {
        // // Set article based on saved instance state defined during
        // onCreateView
        // updateArticleView(mCurrentPosition);
        // }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        adapter = new ActiveInteractionsAdapter(getActivity().getBaseContext(),
                R.layout.interaction_lv_item);
        // View footerView = ((LayoutInflater) getActivity().getSystemService(
        // Context.LAYOUT_INFLATER_SERVICE)).inflate(
        // R.layout.not_sure_footer, null, false);

        // getListView().addFooterView(footerView);
        getListView().setAdapter(adapter);

    }

    public void updateArticleView(int position) {
        TextView article = (TextView) getActivity().findViewById(R.id.article);
        article.setText(Ipsum.Articles[position]);
        // mCurrentPosition = position;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the current article selection in case we need to recreate the
        // fragment
        // outState.putInt(ARG_POSITION, mCurrentPosition);
    }

    @Override
    public Loader<RESTResponse> onCreateLoader(int id, Bundle args) {
        if (args != null && args.containsKey("ARGS_URI")) {
            Uri action = args.getParcelable("ARGS_URI");
            if (args.containsKey("ARGS_PARAMS")) {

                Bundle params = args.getParcelable("ARGS_PARAMS");

                return new RESTLoader(this.getActivity().getBaseContext(),
                        RESTLoader.HTTPVerb.GET, action, params);
            } else {
                return new RESTLoader(this.getActivity().getBaseContext(),
                        RESTLoader.HTTPVerb.GET, action);
            }
        }

        return null;
    }

    @Override
    public void onLoadFinished(Loader<RESTResponse> loader, RESTResponse data) {
        int code = data.getCode();
        String json = data.getData();

        Log.d(TAG, "onLoadFinished with code: " + code + " and json: " + json);

        if (code == 200 && !json.equals("")) {

            // For really complicated JSON decoding I usually do my heavy
            // lifting
            // Gson and proper model classes, but for now let's keep it simple
            // and use a utility method that relies on some of the built in
            // JSON utilities on Android.
            activeInteractions = getInteractionsFromJson(json);

            // Load our list adapter with our Federations.
            adapter.setData(activeInteractions);

            if (isResumed()) {
                setListShown(true);
            } else {
                setListShownNoAnimation(true);
            }
            // mAdapter.clear();

            // for (Federation federation : federations) {
            // mAdapter.add(federation.getName());
            // }

        } else {
            Toast.makeText(
                    this.getActivity().getBaseContext(),
                    "Failed to load Federation data. Check your internet settings.",
                    Toast.LENGTH_SHORT).show();
        }

    }

    private ArrayList<ActiveInteraction> getInteractionsFromJson(String json) {
        ArrayList<ActiveInteraction> activeInteractions = new ArrayList<ActiveInteraction>();
        try {

            JSONArray interactionsJSON = new JSONArray(new JSONTokener(json));

            for (int i = 0; i < interactionsJSON.length(); i++) {
                ActiveInteraction a = new ActiveInteraction();
                JSONObject interactionJSON = interactionsJSON.getJSONObject(i);
                a.setApplication(interactionJSON.getString("application"));
                a.setId(interactionJSON.getInt("id"));
                a.setPortal(interactionJSON.getString("portal"));
                a.setStatus(interactionJSON.getString("status"));
                a.setSubmissionTimestamp(interactionJSON
                        .getString("submissionTimestamp"));
                a.setUserDescription(interactionJSON
                        .getString("userDescription"));
                List<ActiveInteraction> subjobsInfos = new ArrayList<ActiveInteraction>();
                if (interactionJSON.has("subjobsInfos")) {
                    //if (!interactionJSON.get("subjobsInfos").toString()
                    //		.equals("null")) {
                    JSONArray subjobsInfosJSON = (JSONArray) new JSONTokener(
                            json).nextValue();
                    for (int j = 0; j < subjobsInfosJSON.length(); j++) {
                        JSONObject subjobJSON = interactionsJSON
                                .getJSONObject(i);
                        ActiveInteraction subjob = new ActiveInteraction();
                        subjob.setApplication(subjobJSON
                                .getString("application"));
                        subjob.setId(subjobJSON.getInt("id"));
                        subjob.setPortal(subjobJSON.getString("portal"));
                        subjob.setStatus(subjobJSON.getString("status"));
                        subjob.setSubmissionTimestamp(subjobJSON
                                .getString("submissionTimestamp"));
                        subjob.setUserDescription(subjobJSON
                                .getString("userDescription"));
                        subjobsInfos.add(subjob);
                    }

                }
                a.setSubjobsInfos(subjobsInfos);
                activeInteractions.add(a);
            }

        } catch (JSONException e) {
            Log.e(TAG, "Failed to parse JSON.", e);
        }
        return activeInteractions;
    }

    @Override
    public void onLoaderReset(Loader<RESTResponse> arg0) {
        Log.d(TAG, "Loader Reset");

    }

}

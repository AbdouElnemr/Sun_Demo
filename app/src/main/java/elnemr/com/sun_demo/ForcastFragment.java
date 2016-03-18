package elnemr.com.sun_demo;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import elnemr.com.sun_demo.adapters.CustomListViewAdapter;
import elnemr.com.sun_demo.beans.RowItem;

/**
 * Created by root on 3/17/16.
 */
public class ForcastFragment extends Fragment {
    ListView listView;  // list view to show the daily weather detail
    TextView status;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.forcastfragment, container, false);


        String[] forcastArray = {"Tomorrow", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};//Array of Days
        String[] descriptions = {"Cloudy", "Sunny", "Clear", "Rainy", "Cloudy", "Rainy"}; // Status Of The Day
        int[] images = {
                R.drawable.cloudy,
                R.drawable.clear_or_sunny,
                R.drawable.clear_or_sunny,
                R.drawable.rainy,
                R.drawable.cloudy,
                R.drawable.rainy};

        final List<RowItem> rowItems = new ArrayList<RowItem>();

        /*********************************---------List View  Operation-----------******************************/


        for (int i = 0; i < forcastArray.length; i++) {
            RowItem item = new RowItem(images[i], forcastArray[i], descriptions[i]);
            rowItems.add(item);
        }

        listView = (ListView)  rootView.findViewById(R.id.listview_forecast);
        CustomListViewAdapter adapter = new CustomListViewAdapter(getActivity(), R.layout.list_item_forcast, rowItems);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // list view Item click listener
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                view.setSelected(true);
                String name = parent.getItemAtPosition(position).toString(); // get the data from a list view

              Intent intent = new Intent(getActivity(),  Detail_Activity.class);
                RowItem x = rowItems.get(position);
                int theid = x.getImageId();

                String test = "";

                switch (theid) {
                    case R.drawable.clear_or_sunny:
                        test = "Sunny";
                        break;
                    case R.drawable.rainy:
                        test = "Rainy";
                        break;
                    case R.drawable.cloudy:
                        test = "Cloudy";
                        break;
                }
                intent.putExtra("pic", test);
                intent.putExtra("Day_name", name);
                startActivity(intent);
            }


        });



        return rootView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id  = item.getItemId();
        if(id == R.id.action_refresh)
        {
            FeachWeatherTask feachWeatherTask = new FeachWeatherTask();
            feachWeatherTask.execute("94043");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class  FeachWeatherTask extends AsyncTask <String, Void, Void>{

        private final String LOG_TAG = FeachWeatherTask.class.getSimpleName();
        @Override
        protected Void doInBackground(String ... params) {

            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String forecastJsonStr = null;

            try {
                // Construct the URL for the OpenWeatherMap query
                // Possible parameters are avaiable at OWM's forecast API page, at
                // http://openweathermap.org/API#forecast
                URL url = new URL("http://api.openweathermap.org/data/2.5/forecast/daily?q=94043&mode=json&units=metric&cnt=7");

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                forecastJsonStr = buffer.toString();
            } catch (IOException e) {
                Log.e("PlaceholderFragment", "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
            } finally{
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("PlaceholderFragment", "Error closing stream", e);
                    }
                }
            }

            return null;
        }
    }
}

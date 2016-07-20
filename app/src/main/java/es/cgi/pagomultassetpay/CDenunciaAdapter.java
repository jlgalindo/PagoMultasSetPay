package es.cgi.pagomultassetpay;
/**
 * Created by Lichi on 12/07/2016.
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;


public class CDenunciaAdapter extends BaseAdapter {

        private Activity activity;
        private ArrayList< HashMap<String, String> > data;
        private static LayoutInflater inflater=null;


        public CDenunciaAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
            activity = a;
            data=d;
            inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public int getCount() {
            return data.size();
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean isEnabled(int position)
        {
            return true;
        }
        public View getView(int position, View convertView, ViewGroup parent) {
            View vi=convertView;

            if (convertView == null) {
                // Create a new view into the list.
                //LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                vi = inflater.inflate(R.layout.list_row, parent, false);
            }


            TextView idboletin = (TextView)vi.findViewById(R.id.idboletin); // title
            TextView fechainfrac = (TextView)vi.findViewById(R.id.fechainfrac); // artist name
            TextView matricula = (TextView)vi.findViewById(R.id.matricula); // duration
            TextView dboidboletin = (TextView)vi.findViewById(R.id.dboidboletin); // title
            TextView dboidc60 = (TextView)vi.findViewById(R.id.dboidc60); // title
            //ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image); // thumb image

            HashMap<String, String> denuncia = new HashMap<String, String>();
            denuncia = data.get(position);

            // Setting all values in listview
            idboletin.setText(denuncia.get(MainActivity.KEY_MULIDEBOL));
            fechainfrac.setText(denuncia.get(MainActivity.KEY_MULFECINF));
            matricula.setText(denuncia.get(MainActivity.KEY_MULMATRIC));
            dboidboletin.setText(denuncia.get(MainActivity.KEY_DBOID));
            dboidc60.setText(denuncia.get(MainActivity.KEY_DBOIDC60));

            //imageLoader.DisplayImage(song.get(CustomizedListView.KEY_THUMB_URL), thumb_image);
            vi.setFocusable(false);


            return vi;


        }


    }


package es.cgi.pagomultassetpay;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.setpay.mpos.model.TransactionSetPay;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import es.cgi.webservices.Asynchtask;
import es.cgi.webservices.WebService;


    ////////////////////////////////////////////////////////////////////////
    public class MainActivity extends AppCompatActivity implements Asynchtask{
    //Variables Comunes al proyecto
    private static  String hostCGIConecta = "213.27.254.217";
    private static  int    puertoCGIConecta = 80;
    // Metodo que queremos ejecutar en el servicio web
    private static  String Metodo = "runMethod";
    // Namespace definido en el servicio web
    private static  String namespace = "http://conecta.ws.cgi.es";
    // namespace + metodo
    private static  String accionSoap = "http://conecta.ws.cgi.es/runMethod";
    // Fichero de definicion del servcio web
    private static  String url = "http://"+hostCGIConecta+"/WSCGIConecta/services/ConectaCGI";

    ////////////////////////////////////////////////////////////////////////
    // All static variables para ListView

    // XML node keys
    static final String KEY_DENUNCIA = "DENUNCIA"; // parent node
    static final String KEY_MULIDEBOL = "MULIDEBOL";
    static final String KEY_MULFECINF = "MULFECINF";
    static final String KEY_MULMATRIC = "MULMATRIC";
    static final String KEY_DBOID = "DBOID";
    static final String KEY_DBOIDC60 = "C60DBOIDE";


    ////////////////////////////////////////////////////////////////////////
    // Variables propias de SePay
    private static final int SIMULATED_SALE_WITH_SIGNATURE  = 200;
    private static final int SIMULATED_SALE_WITH_CARD_SELECTION = 201;
    private static final int SIMULATED_SALE_FAILING = 202;
    private static final int REAL_READER_SALE = 203;
    private static final int REFUND = 204;



    ListView listRelacionDenuncias;
    CDenunciaAdapter adapter;
    //private ProgressDialog progressDialog;
    public Button btnConsumeWs;
    public Button btnPagar;

    public void init(){
        //List<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
        listRelacionDenuncias=(ListView)findViewById(R.id.listRelacionDenuncias);
        ejecutaConsumeWS();
    }

    //
    //Aquí se crea la activity main
    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }


    @Override
    public void processFinish(String result) {
        // TODO Auto-generated method stub
        //Log.i("processFinish", result);
        //Toast.makeText(getApplicationContext(),result, Toast.LENGTH_LONG).show();
        String[] auxresult = result.split("@#@");
        if (auxresult[0].equals("dameDenunciasAgenteyFecha")) {
            if ( auxresult[1].subSequence(0,5).equals("ERROR")){
                Toast.makeText(getApplicationContext(),auxresult[1], Toast.LENGTH_LONG).show();
            }else {
                String denuncias = "<root>" + auxresult[1] + "</root>";
                cargaListaDenuncias(denuncias);
            }
        }else if  (auxresult[0].equals("dameReciboAsociadoaDenuncia")){
            if ( auxresult[1].subSequence(0,5).equals("ERROR")){
                Toast.makeText(getApplicationContext(),auxresult[1], Toast.LENGTH_LONG).show();
            }else {

                String Recibo = auxresult[1];
                XMLParser parser = new XMLParser();
                String xml = Recibo; // getting XML from URL
                Document doc = parser.getDomElement(xml); // getting DOM element
                String Recnumrec = "";
                String Recnumfra = "";
                String Recdboide = "";
                String c60dboid = "";
                String c60import = "";
                String c60impdes = "";
                String c60imprec = "";
                String c60impint = "";
                String c60impcos = "";
                NodeList nl = doc.getElementsByTagName("RECIBO");
                // looping through all song nodes &lt;song&gt;
                for (int i = 0; i < nl.getLength(); i++) {
                    Element e = (Element) nl.item(i);
                    Recnumrec = parser.getValue(e, "RECNUMREC");
                    Recnumfra = parser.getValue(e, "RECNUMFRA");
                    Recdboide = parser.getValue(e, "RECDBOIDE");
                    c60dboid = parser.getValue(e, "C60DBOIDE");
                    c60import = parser.getValue(e, "C60IMPORT");
                    ;
                    c60impdes = parser.getValue(e, "C60IMPDES");
                    ;
                    c60imprec = parser.getValue(e, "C60IMPREC");
                    ;
                    c60impint = parser.getValue(e, "C60IMPINT");
                    ;
                    c60impcos = parser.getValue(e, "C60IMPCOS");
                    ;
                }
                Toast.makeText(getApplicationContext(), Recnumrec + "-" + Recnumfra, Toast.LENGTH_LONG).show();
                //Ya sabemos que RefC60 hay que pagar, así que ahora llamamos a Pagar con SetPay
                comunes.numrecibo = Recnumrec;
                comunes.numfracc = Recnumfra;
                comunes.dboidc60 = c60dboid;
                double importetotal = 0;
                importetotal = (double) (Float.parseFloat(c60impdes) + Float.parseFloat(c60imprec) +
                        Float.parseFloat(c60impint) + Float.parseFloat(c60impcos));
                pagaconSetPay(Recnumrec, Recnumfra, importetotal);
            }
        }else if (auxresult[0].equals("cobraReciboC60")){
            String resultadoCobroExtcall =   auxresult[1];
            if (resultadoCobroExtcall.equals("OK")){
                Toast.makeText(getApplicationContext(),"Cobro aplicado correctamente en BBDD", Toast.LENGTH_LONG).show();
            }
        }
    }


    public String ejecutaConsumeWS() {
        //Toast.makeText(getApplicationContext(), "Llamando al WS.......", Toast.LENGTH_LONG).show();
        String denuncias="Denuncias antes del WS";
        if (isHostReachable(hostCGIConecta,puertoCGIConecta,4000)) {
            Map<String, String> datosparaWS = new HashMap<String, String>();
            datosparaWS.put("proyecto",comunes.proyecto);
            datosparaWS.put("webservice","Multas");
            datosparaWS.put("nombremetodo","dameDenunciasAgenteyFecha");
            datosparaWS.put("parametros",comunes.agente+"@#@"+comunes.fecharef);
            datosparaWS.put("namespace",namespace);
            datosparaWS.put("metodo",Metodo);

            WebService t=new WebService(url, datosparaWS,MainActivity.this, MainActivity.this);
            t.execute("");
            /*
            denuncias = traeDenuncias("Alaior", "PDA25", "01-07-2016");
            //denuncias="<?xml version='1.0' encoding='UTF-8'?><root>"+denuncias+"</root>";
            denuncias="<root>"+denuncias+"</root>";
            cargaListaDenuncias(denuncias);
            */
        }else{
            denuncias="No hay Conexión a Internet....Por favor, inténtelo más tarde";
        }
        //Toast.makeText(getApplicationContext(),denuncias, Toast.LENGTH_LONG).show();
        return denuncias;
    }



    public static boolean isHostReachable(String serverAddress, int serverTCPport, int timeoutMS){
        boolean connected = false;
        Socket socket;
        try {
            socket = new Socket();
            SocketAddress socketAddress = new InetSocketAddress(serverAddress, serverTCPport);
            socket.connect(socketAddress, timeoutMS);
            if (socket.isConnected()) {
                connected = true;
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            socket = null;
        }
        return connected;
    }

        public void cargaListaDenuncias(String xmllistaDenuncias){

            ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();

            XMLParser parser = new XMLParser();
            String xml =xmllistaDenuncias; // getting XML from URL
            Document doc = parser.getDomElement(xml); // getting DOM element

            NodeList nl = doc.getElementsByTagName(KEY_DENUNCIA);
            // looping through all song nodes &lt;song&gt;
            for (int i = 0; i < nl.getLength(); i++) {
                // creating new HashMap
                HashMap<String, String> map = new HashMap<String, String>();
                Element e = (Element) nl.item(i);
                // adding each child node to HashMap key =&gt; value
                map.put(KEY_MULIDEBOL, parser.getValue(e, KEY_MULIDEBOL));
                map.put(KEY_MULFECINF, parser.getValue(e, KEY_MULFECINF));
                map.put(KEY_MULMATRIC, parser.getValue(e, KEY_MULMATRIC));
                map.put(KEY_DBOID, parser.getValue(e, KEY_DBOID));
                //map.put(KEY_THUMB_URL, parser.getValue(e, KEY_THUMB_URL));

                // adding HashList to ArrayList
                songsList.add(map);
            }

            // Getting adapter by passing xml data ArrayList
            adapter=new CDenunciaAdapter(this, songsList);
            listRelacionDenuncias.setAdapter(adapter);
            // Click event for single list row
            AdapterView.OnItemClickListener myListViewClicked = new AdapterView.OnItemClickListener () {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //Toast.makeText(MainActivity.this, "Clicked at positon = " + position, Toast.LENGTH_SHORT).show();
                    // Sets the visibility of the indeterminate progress bar in the
                    // title
                    //setProgressBarIndeterminateVisibility(true);
                    // Show progress dialog
                    //progressDialog = ProgressDialog.show(MainActivity.this,"ProgressDialog", "Cargando....!");

                    //ClipData.Item item = (ClipData.Item) listRelacionDenuncias.getAdapter().getItem(position);

                    TextView idBoletin = (TextView) view
                            .findViewById(R.id.idboletin);

                    TextView dboidBoletin = (TextView) view
                            .findViewById(R.id.dboidboletin);
                    TextView dboidC60= (TextView) view
                            .findViewById(R.id.dboidc60);

                    comunes.boletinSeleccionado=idBoletin.getText().toString();
                    //HashMap<String, String> fila = (HashMap) listRelacionDenuncias.getAdapter().getItem(position);
                    //Toast.makeText(MainActivity.this, "Boletin = " +dboidBoletin.getText().toString(), Toast.LENGTH_SHORT).show();

                    traereciboyrefc60(dboidBoletin.getText().toString());
                }
            };
            listRelacionDenuncias.setOnItemClickListener(  myListViewClicked );
        }

        public void traereciboyrefc60( String dboidDenuncia) {

            Map<String, String> datosparaWS = new HashMap<String, String>();
            datosparaWS.put("proyecto",comunes.proyecto);
            datosparaWS.put("webservice","Multas");
            datosparaWS.put("nombremetodo","dameReciboAsociadoaDenuncia");
            datosparaWS.put("parametros",dboidDenuncia+"@#@"+comunes.fecharef);
            datosparaWS.put("namespace",namespace);
            datosparaWS.put("metodo",Metodo);
            WebService t=new WebService(url, datosparaWS,MainActivity.this, MainActivity.this);
            t.execute("");

        }

        //
    //
    ///Funciones y métodos propios de SetPay
    //
    //
    private void  pagaconSetPay(String Numrec, String Numfra, double importe){
        Intent intent = new Intent("com.setpay.mpos.ExternalSale");
        String concepto=comunes.NombreEntidad+" Boletin :" + comunes.boletinSeleccionado +" (Recibo:" + Numrec+"-"+Numfra+")";
        concepto=comunes.NombreEntidad+" - Boletin :" + comunes.boletinSeleccionado ;
        intent.putExtra("TYPE", "sale");
        intent.putExtra("AMOUNT",importe);
        intent.putExtra("CONCEPT", concepto);
        intent.putExtra("REQUEST_CODE", REAL_READER_SALE);

        startActivityForResult(intent, REAL_READER_SALE);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK){

            TransactionSetPay transaction = data.getExtras().getParcelable("TRANSACTION");

            if(transaction !=null) {

                Log.d("TRANSACTION", transaction.toString());

                switch (requestCode) {

                    case SIMULATED_SALE_WITH_SIGNATURE:
                        Toast.makeText(getApplicationContext(),
                                "TransactionID: " + transaction.getTransactionId()
                                , Toast.LENGTH_LONG).show();
                        break;

                    case SIMULATED_SALE_WITH_CARD_SELECTION:
                        Toast.makeText(getApplicationContext(),
                                "TransactionID: " + transaction.getTransactionId()
                                , Toast.LENGTH_LONG).show();
                        break;

                    case SIMULATED_SALE_FAILING:
                        Toast.makeText(getApplicationContext(), "Transaction failed", Toast.LENGTH_LONG).show();
                        break;

                    case REAL_READER_SALE:
                        Toast.makeText(getApplicationContext(),
                                "TransactionID: " + transaction.getTransactionId()
                                , Toast.LENGTH_LONG).show();
                        //Aqui llamaremos al WebService para aplicar el Cobro_b sobre el recibo
                        Map<String, String> datosparaWS = new HashMap<String, String>();
                        datosparaWS.put("proyecto",comunes.proyecto);
                        datosparaWS.put("webservice","Recibos");
                        datosparaWS.put("nombremetodo","cobraReciboC60");
                        //160000001@#@0@#@109700000001544807900@#@PRUEBA_BOR@#@14@#@10008
                        String param=comunes.numrecibo+"@#@" +
                                     comunes.numfracc+"@#@" +
                                     comunes.dboidc60+"@#@" +
                                     comunes.cuentabanco+"@#@" +
                                     comunes.formpag+"@#@" +
                                     comunes.agente;
                        datosparaWS.put("parametros",param);
                        datosparaWS.put("namespace",namespace);
                        datosparaWS.put("metodo",Metodo);

                        WebService t=new WebService(url, datosparaWS,MainActivity.this, MainActivity.this);
                        t.execute("");

                        break;

                    case REFUND:
                        Toast.makeText(getApplicationContext(),
                                "TransactionID: " + transaction.getTransactionId()
                                , Toast.LENGTH_LONG).show();
                        break;
                }
            }
        }

        else{
            Toast.makeText(getApplicationContext(), data.getExtras().getString("MESSAGE"), Toast.LENGTH_LONG).show();
        }



    }

}


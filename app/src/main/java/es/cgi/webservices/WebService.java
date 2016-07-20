package es.cgi.webservices;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.Iterator;
import java.util.Map;

import es.cgi.webservices.HttpRequest.HttpRequestException;

/**
 * Esta clase se encarga de hacer el llamado al servicio web y retornar la informacion
 * @author JorgeAndres
 *
 */
public class WebService extends AsyncTask<String, Long, String> {

	//Variable con los datos para pasar al web service
	private Map<String, String> datos;
	//Url del servicio web
	private String url = "http://192.168.1.46/codigobarras/";

	//Actividad para mostrar el cuadro de progreso
	private Context actividad;

	//Resultado
	private String xml = null;

	//Clase a la cual se le retorna los datos dle ws
	private Asynchtask callback = null;

	public Asynchtask getCallback() {
		return callback;
	}

	public void setCallback(Asynchtask callback) {
		this.callback = callback;
	}

	//LIchi. Atributos nuevos
	private String namespace = "";
	private String metodo = "";

	ProgressDialog progDailog;

	/**
	 * Crea una estancia de la clase webService para hacer consultas a ws
	 *
	 * @param urlWebService Url del servicio web
	 * @param data          Datos a enviar del servicios web
	 * @param activity      Actividad de donde se llama el servicio web, para mostrar el cuadro de "Cargando"
	 * @param callback      CLase a la que se le retornara los datos del servicio web
	 */
	public WebService(String urlWebService, Map<String, String> data, Context activity, Asynchtask callback) {
		this.url = urlWebService;
		this.datos = data;
		this.actividad = activity;
		this.callback = callback;
		//En el Map data, me llegan el namespace y el metodo
		//Aunque Rubén diga que no, ésto rula
		this.metodo = data.get("metodo");
		this.namespace = data.get("namespace");
	}

	public WebService() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		progDailog = new ProgressDialog(actividad);
		progDailog.setMessage("Cargando datos...");
		progDailog.setIndeterminate(false);
		progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progDailog.setCancelable(true);
		progDailog.show();
	}

	@Override
	protected String doInBackground(String... params) {
		String r = "";  //Respuesta del WS
		try {
			/*
			String r=HttpRequest.post(this.url).form(this.datos).body();
			return r;
			*/
			SoapObject result;


			String accionSoap = namespace + "/" + metodo;
			SoapObject request = new SoapObject(namespace, metodo);

			Iterator it = datos.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry e = (Map.Entry) it.next();
				if (!(e.getKey().equals("metodo") || e.getKey().equals("namespace"))) {
					request.addProperty(e.getKey().toString(), e.getValue().toString());
				}
			}
			//Use this to add parameters
			/*
			request.addProperty("proyecto",proyecto);
			request.addProperty("webservice","Multas");
			request.addProperty("parametros",agente + "@#@" + fechaRef);
			request.addProperty("nombremetodo","dameDenunciasAgenteyFecha");
			*/
			//Declare the version of the SOAP request
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.setOutputSoapObject(request);
			envelope.dotNet = true;
			HttpTransportSE androidHttpTransport = new HttpTransportSE(url);
			//this is the actual part that will call the webservice
			androidHttpTransport.call(accionSoap, envelope);
			// Get the SoapResult from the envelope body.
			result = (SoapObject) envelope.bodyIn;
			if (result != null) { //Llamada al WS OK
				r = (result.getProperty(0).toString());
			} else {
				//Toast.makeText(getApplicationContext(), "No Response", Toast.LENGTH_LONG).show();
				r = "No response";
			}
		} catch (HttpRequestException exception) {
			Log.e("doInBackground", exception.getMessage());
			r= exception.getMessage();
			//return "Error HttpRequestException";
		} catch (Exception e) {
			Log.e("doInBackground", e.getMessage());
			r= e.getMessage();
			//return "Error Exception";
		}
		return r;
	}


	@Override
	protected void onPostExecute(String response) {
		super.onPostExecute(response);
        this.xml=response;
        progDailog.dismiss();
        //Retorno los datos
        callback.processFinish(this.xml);
    }
	public Map<String, String> getDatos() {
		return datos;
	}

	public void setDatos(Map<String, String> datos) {
		this.datos = datos;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Context getActividad() {
		return actividad;
	}

	public void setActividad(Context actividad) {
		this.actividad = actividad;
	}

	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

	public ProgressDialog getProgDailog() {
		return progDailog;
	}

	public void setProgDailog(ProgressDialog progDailog) {
		this.progDailog = progDailog;
	}
}

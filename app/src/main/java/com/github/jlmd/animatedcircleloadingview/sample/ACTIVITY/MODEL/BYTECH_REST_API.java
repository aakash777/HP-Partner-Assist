//package com.github.jlmd.animatedcircleloadingview.sample.ACTIVITY.MODEL;
//
//import com.android.volley.DefaultRetryPolicy;
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.VolleyLog;
//import com.android.volley.toolbox.JsonObjectRequest;
//import com.github.jlmd.animatedcircleloadingview.sample.ACTIVITY.MAIN_APPLICATION.BYTECH_SINGLETON;
//import com.pixplicity.easyprefs.library.Prefs;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.Map;
//
///**
// * Created by Mainak Karmakar on 16/09/2015.
// */
//public class BYTECH_REST_API
//{
//
//    CASHIE_M_CUSTM_REQ custmr_reqrmnt;
//    private String TAG = "VOLLEY";
//    // Tag used to cancel the request
//    private String tag_json_obj = "json_obj_req";
//
//    public void post(String url, final Map<String, String> params, final OnRestCallback callback, final Request.Priority priority) {
//
//        JSONObject paramsJsonObject = null;
//        if (params != null) {
//            paramsJsonObject = new JSONObject(params);
//        }
//
//        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, url, paramsJsonObject, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                VolleyLog.d(TAG, response.toString());
//                callback.onRestResponse(true, response);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                VolleyLog.d(TAG, "Error: " + error.getMessage());
//                callback.onRestResponse(false, null);
//            }
//        }) {
//            /**
//             * Returns the {@link com.android.volley.Request.Priority} of this request; {@link com.android.volley.Request.Priority#NORMAL} by default.
//             */
//            @Override
//            public Priority getPriority() {
//                return priority;
//            }
//        };
//        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(5000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        // Get a RequestQueue
//
//        // Get a RequestQueue
//        RequestQueue queue = BYTECH_SINGLETON.getInstance().getRequestQueue();
//        // Adding request to request queue
//        BYTECH_SINGLETON.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
//
//    }
//
//    public void post(String url, JSONObject params, final OnRestCallback callback, final Request.Priority priority) {
//
//        JSONObject paramsJsonObject = params;
//
//        custmr_reqrmnt = new CASHIE_M_CUSTM_REQ();
//        custmr_reqrmnt.setCustomerid(Prefs.getInt(AIE_CONSTANTS.shared_customer_id, 0));
//        custmr_reqrmnt.setLatitude(Prefs.getString(AIE_CONSTANTS.shared_user_latitude, ""));
//        custmr_reqrmnt.setLongitude(Prefs.getString(AIE_CONSTANTS.shared_user_longitude, ""));
//        custmr_reqrmnt.setCity(Prefs.getString(AIE_CONSTANTS.shared_user_city, ""));
//        custmr_reqrmnt.setCountry(Prefs.getString(AIE_CONSTANTS.shared_user_country, ""));
//        try {
//            paramsJsonObject.put("customerreq",custmr_reqrmnt);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, url, paramsJsonObject, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                VolleyLog.d(TAG, response.toString());
//                callback.onRestResponse(true, response);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                VolleyLog.d(TAG, "Error: " + error.getMessage());
//                callback.onRestResponse(false, null);
//            }
//        }) {
//            /**
//             * Returns the {@link com.android.volley.Request.Priority} of this request; {@link com.android.volley.Request.Priority#NORMAL} by default.
//             */
//            @Override
//            public Priority getPriority() {
//                return priority;
//            }
//        };
//        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(5000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        // Get a RequestQueue
//        // Get a RequestQueue
//        RequestQueue queue = BYTECH_SINGLETON.getInstance().getRequestQueue();
//        // Adding request to request queue
//        BYTECH_SINGLETON.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
//
//    }
//
////    public void get(String url, final Map<String, String> params, final OnRestCallback callback, final Request.Priority priority) {
////        JSONObject object = new JSONObject();
////        if (params == null) {
////            object = null;
////        } else {
////            object = new JSONObject(params);
////            custmr_reqrmnt = new CASHIE_M_CUSTM_REQ();
////            custmr_reqrmnt.setCustomerid(Prefs.getInt(AIE_CONSTANTS.shared_customer_id, 0));
////            custmr_reqrmnt.setLatitude(Prefs.getString(AIE_CONSTANTS.shared_user_latitude, ""));
////            custmr_reqrmnt.setLongitude(Prefs.getString(AIE_CONSTANTS.shared_user_longitude, ""));
////            custmr_reqrmnt.setCity(Prefs.getString(AIE_CONSTANTS.shared_user_city, ""));
////            custmr_reqrmnt.setCountry(Prefs.getString(AIE_CONSTANTS.shared_user_country, ""));
////            try {
////                params.get("customerreq",custmr_reqrmnt);
////            } catch (JSONException e) {
////                e.printStackTrace();
////            }
////        }
////
////        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, object, new Response.Listener<JSONObject>() {
////            @Override
////            public void onResponse(JSONObject response) {
////                VolleyLog.d(TAG, response.toString());
////                callback.onRestResponse(true, response);
////            }
////        }, new Response.ErrorListener() {
////            @Override
////            public void onErrorResponse(VolleyError error) {
////                VolleyLog.d(TAG, "Error: " + error.getMessage());
////                callback.onRestResponse(false, null);
////            }
////        }) {
////            /**
////             * Returns the {@link com.android.volley.Request.Priority} of this request; {@link com.android.volley.Request.Priority#NORMAL} by default.
////             */
////            @Override
////            public Priority getPriority() {
////                return priority;
////            }
////
////            /**
////             * Returns a Map of parameters to be used for a POST or PUT request.  Can throw
////             * {@link com.android.volley.AuthFailureError} as authentication may be required to provide these values.
////             * <p/>
////             * <p>Note that you can directly override {@link #getBody()} for custom data.</p>
////             *
////             * @throws com.android.volley.AuthFailureError in the event of auth failure
////             */
////        };
////        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(5000,
////                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
////                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
////        // Get a RequestQueue
////        RequestQueue queue = AIE_U_Singleton.getInstance().getRequestQueue();
////        // Adding request to request queue
////        AIE_U_Singleton.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
////    }
//
//    public void get(String url, JSONObject parm, final OnRestCallback callback, final Request.Priority priority) {
//
//
//        custmr_reqrmnt = new CASHIE_M_CUSTM_REQ();
//        custmr_reqrmnt.setCustomerid(Prefs.getInt(AIE_CONSTANTS.shared_customer_id, 0));
//        custmr_reqrmnt.setLatitude(Prefs.getString(AIE_CONSTANTS.shared_user_latitude, ""));
//        custmr_reqrmnt.setLongitude(Prefs.getString(AIE_CONSTANTS.shared_user_longitude, ""));
//        custmr_reqrmnt.setCity(Prefs.getString(AIE_CONSTANTS.shared_user_city, ""));
//        custmr_reqrmnt.setCountry(Prefs.getString(AIE_CONSTANTS.shared_user_country, ""));
//        try {
//            parm.put("customerreq", custmr_reqrmnt);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, parm, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                VolleyLog.d(TAG, response.toString());
//                callback.onRestResponse(true, response);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                VolleyLog.d(TAG, "Error: " + error.getMessage());
//                callback.onRestResponse(false, null);
//            }
//        });
//        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(5000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        // Get a RequestQueue
//        // Get a RequestQueue
//        RequestQueue queue = BYTECH_SINGLETON.getInstance().getRequestQueue();
//        // Adding request to request queue
//        BYTECH_SINGLETON.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
//    }
//
//    public void get(String url, final OnRestCallback callback, final Request.Priority priority) {
//
//        JSONObject paramsJsonObject;
//        paramsJsonObject = new JSONObject();
//        custmr_reqrmnt = new CASHIE_M_CUSTM_REQ();
//        custmr_reqrmnt.setCustomerid(Prefs.getInt(AIE_CONSTANTS.shared_customer_id, 0));
//        custmr_reqrmnt.setLatitude(Prefs.getString(AIE_CONSTANTS.shared_user_latitude, ""));
//        custmr_reqrmnt.setLongitude(Prefs.getString(AIE_CONSTANTS.shared_user_longitude, ""));
//        custmr_reqrmnt.setCity(Prefs.getString(AIE_CONSTANTS.shared_user_city, ""));
//        custmr_reqrmnt.setCountry(Prefs.getString(AIE_CONSTANTS.shared_user_country, ""));
//        try {
//            paramsJsonObject.put("customerreq",custmr_reqrmnt);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, paramsJsonObject, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                VolleyLog.d(TAG, response.toString());
//                callback.onRestResponse(true, response);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                VolleyLog.d(TAG, "Error: " + error.getMessage());
//                callback.onRestResponse(false, null);
//            }
//        });
//        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(5000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        // Get a RequestQueue
//        // Get a RequestQueue
//        RequestQueue queue = BYTECH_SINGLETON.getInstance().getRequestQueue();
//        // Adding request to request queue
//        BYTECH_SINGLETON.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
//    }
//
//    public void post(String url, final JSONArray params, final OnRestCallback callback, final Request.Priority priority) {
//        JSONObject paramobj = null;
//        JSONArray paramsJsonObject = null;
//        if (params != null) {
//            paramsJsonObject = params;
//
//            System.out.println("item detail output3:" + paramsJsonObject);
//            paramobj = new JSONObject();
//            custmr_reqrmnt = new CASHIE_M_CUSTM_REQ();
//            custmr_reqrmnt.setCustomerid(Prefs.getInt(AIE_CONSTANTS.shared_customer_id, 0));
//            custmr_reqrmnt.setLatitude(Prefs.getString(AIE_CONSTANTS.shared_user_latitude, ""));
//            custmr_reqrmnt.setLongitude(Prefs.getString(AIE_CONSTANTS.shared_user_longitude, ""));
//            custmr_reqrmnt.setCity(Prefs.getString(AIE_CONSTANTS.shared_user_city, ""));
//            custmr_reqrmnt.setCountry(Prefs.getString(AIE_CONSTANTS.shared_user_country, ""));
//            try {
//                paramobj.put("customerreq", custmr_reqrmnt);
//                params.put(paramobj);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//
//        JsonPostArrayRequest jsonObjReq = new JsonPostArrayRequest(url, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                VolleyLog.d(TAG, response.toString());
//                System.out.println("item detail output4:" + response.toString());
//
//                callback.onRestResponse(true, response);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                VolleyLog.d(TAG, "Error: " + error.getMessage());
//                callback.onRestResponse(false, null);
//            }
//        }, paramsJsonObject);
//        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(5000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        // Get a RequestQueue
//        // Get a RequestQueue
//        RequestQueue queue = BYTECH_SINGLETON.getInstance().getRequestQueue();
//        // Adding request to request queue
//        BYTECH_SINGLETON.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
//    }
//
//    public void put(String url, final Map<String, String> params, final OnRestCallback callback, final Request.Priority priority) {
//
//        custmr_reqrmnt = new CASHIE_M_CUSTM_REQ();
//        params.put("customerid", String.valueOf(Prefs.getInt(AIE_CONSTANTS.shared_customer_id, 0)));
//        params.put("city", Prefs.getString(AIE_CONSTANTS.shared_user_city, ""));
//        params.put("country", Prefs.getString(AIE_CONSTANTS.shared_user_country, ""));
//        params.put("latitude", Prefs.getString(AIE_CONSTANTS.shared_user_latitude, ""));
//        params.put("longitude",Prefs.getString(AIE_CONSTANTS.shared_user_longitude, ""));
//
//        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.PUT,
//                url, new JSONObject(params),
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        VolleyLog.d(TAG, response.toString());
//                        callback.onRestResponse(true, response);
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        VolleyLog.d(TAG, "Error: " + error.getMessage());
//                        callback.onRestResponse(false, null);
//                    }
//                }) {
//
//            @Override
//            public Priority getPriority() {
//                return priority;
//            }
//
//        };
//        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(5000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        // Get a RequestQueue
//        // Get a RequestQueue
//        RequestQueue queue = BYTECH_SINGLETON.getInstance().getRequestQueue();
//        // Adding request to request queue
//        BYTECH_SINGLETON.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
//
//    }
//
////    public void put(String url, JSONObject params, final OnRestCallback callback, final Request.Priority priority) {
////        System.out.println("url" + url);
////        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.PUT,
////                url, params,
////                new Response.Listener<JSONObject>() {
////                    @Override
////                    public void onResponse(JSONObject response) {
////                        VolleyLog.d(TAG, response.toString());
////                        callback.onRestResponse(true, response);
////                    }
////                },
////                new Response.ErrorListener() {
////                    @Override
////                    public void onErrorResponse(VolleyError error) {
////                        VolleyLog.d(TAG, "Error: " + error.getMessage());
////                        callback.onRestResponse(false, null);
////                    }
////                }) {
////
////            @Override
////            public Priority getPriority() {
////                return priority;
////            }
////
////        };
////        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(5000,
////                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
////                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
////        // Get a RequestQueue
////        // Get a RequestQueue
////        RequestQueue queue = AIE_U_Singleton.getInstance().getRequestQueue();
////        // Adding request to request queue
////        AIE_U_Singleton.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
////
////    }
//
//    public void delete(String url, final Map<String, String> params, final OnRestCallback callback, final Request.Priority priority) {
//
//        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.DELETE,
//                url, new JSONObject(params),
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        VolleyLog.d(TAG, response.toString());
//                        callback.onRestResponse(true, response);
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        VolleyLog.d(TAG, "Error: " + error.getMessage());
//                        callback.onRestResponse(false, null);
//                    }
//                }) {
//
//            @Override
//            public Priority getPriority() {
//                return priority;
//            }
//
//        };
//        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(5000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        // Get a RequestQueue
//        // Get a RequestQueue
//        RequestQueue queue = BYTECH_SINGLETON.getInstance().getRequestQueue();
//        // Adding request to request queue
//        BYTECH_SINGLETON.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
//    }
//
//    public interface OnRestCallback {
//        public void onRestResponse(boolean success, JSONObject jsonObject);
//    }
//
//
//}

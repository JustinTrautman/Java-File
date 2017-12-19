private Observable<Void> onWifiHotspot() {
  Return Observable.create(subscriber -> {
    
    WifiManager wifiManager = (WifiMananger) getSystemService(Context.WIFI_SERVICE);

private Observable<Void> onWifiHotspot() {
  Return Observable.create(subscriber -> {
    
    WifiManager wifiManager = (WifiMananger) getSystemService(Context.WIFI_SERVICE);
        if (wifiManager.isWifiEnabled()) {
                wifiManager.setWifiEnabled(false);
            }
            WifiConfiguration netConfig = new WifiConfiguration();
            netConfig.SSID = "WIFI";
            netConfig.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
            netConfig.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
            netConfig.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
            netConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            try {
                Method setWifiApMethod = wifiManager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class, boolean.class);
                boolean apstatus = (Boolean) setWifiApMethod.invoke(wifiManager, netConfig, true);

                Method isWifiApEnabledmethod = wifiManager.getClass().getMethod("isWifiApEnabled");
                while (!(Boolean) isWifiApEnabledmethod.invoke(wifiManager)) {
                }
                ;
                Method getWifiApStateMethod = wifiManager.getClass().getMethod("getWifiApState");
                int apstate = (Integer) getWifiApStateMethod.invoke(wifiManager);
                Method getWifiApConfigurationMethod = wifiManager.getClass().getMethod("getWifiApConfiguration");
                netConfig = (WifiConfiguration) getWifiApConfigurationMethod.invoke(wifiManager);
                Log.d("stas", "\nSSID:" + netConfig.SSID + "\nPassword:" + netConfig.preSharedKey + "\n");

            } catch (Exception e) {
                Log.e(this.getClass().toString(), "", e);
            }

            WifiApControl apControl = WifiApControl.getInstance(this);

            apControl.enable();
        });


    }
  private void getActiveNetworkInfo() {
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) { // connected to the internet
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                // connected to wifi
                Toast.makeText(this, activeNetwork.getTypeName(), Toast.LENGTH_SHORT).show();
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                // connected to the mobile provider's data plan
                Toast.makeText(this, activeNetwork.getTypeName(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "NONE", Toast.LENGTH_SHORT).show();
        }


    }

    public boolean isMobileConnected(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        return ((netInfo != null) && netInfo.isConnected());
    }

    public void checkPermission() {
        boolean hasPermission = (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
        if (!hasPermission) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_SETTINGS);

        }

    }

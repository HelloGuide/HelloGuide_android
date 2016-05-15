/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2015 Perples, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.example.androidchoi.helloguide.Service;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.androidchoi.helloguide.MainActivity;
import com.example.androidchoi.helloguide.PlaceInfoActivity;
import com.example.androidchoi.helloguide.R;
import com.example.androidchoi.helloguide.model.Beacon;
import com.example.androidchoi.helloguide.model.PlaceServerData;
import com.perples.recosdk.RECOBeacon;
import com.perples.recosdk.RECOBeaconManager;
import com.perples.recosdk.RECOBeaconRegion;
import com.perples.recosdk.RECOBeaconRegionState;
import com.perples.recosdk.RECOErrorCode;
import com.perples.recosdk.RECOMonitoringListener;
import com.perples.recosdk.RECOServiceConnectListener;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * RECOBackgroundMonitoringService is to monitor regions in the background.
 *
 * RECOBackgroundMonitoringService는 백그라운드에서 monitoring을 수행합니다.
 */
public class RecoBackgroundMonitoringService extends Service implements RECOMonitoringListener, RECOServiceConnectListener {
    /**
     * We recommend 1 second for scanning, 10 seconds interval between scanning, and 60 seconds for region expiration time.
     * 1초 스캔, 10초 간격으로 스캔, 60초의 region expiration time은 당사 권장사항입니다.
     */
    public static String NOTIFICATION_EXTRA = "notification_extra";
    private long mScanDuration = 2*1000L;
    private long mSleepDuration = 5*1000L;
    private long mRegionExpirationTime = 5*1000L;
    private int mNotificationID = 9998;

    private RECOBeaconManager mRecoManager;
    private ArrayList<RECOBeaconRegion> mRegions;

    @Override
    public void onCreate() {
        Log.i("BackMonitoringService", "onCreate()");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("BackMonitoringService", "onStartCommand()");
        /**
         * Create an instance of RECOBeaconManager (to set scanning target and ranging timeout in the background.)
         * If you want to scan only RECO, and do not set ranging timeout in the backgournd, create an instance:
         * 		mRecoManager = RECOBeaconManager.getInstance(getApplicationContext(), true, false);
         * WARNING: False enableRangingTimeout will affect the battery consumption.
         *
         * RECOBeaconManager 인스턴스틀 생성합니다. (스캔 대상 및 백그라운드 ranging timeout 설정)
         * RECO만을 스캔하고, 백그라운드 ranging timeout을 설정하고 싶지 않으시다면, 다음과 같이 생성하시기 바랍니다.
         * 		mRecoManager = RECOBeaconManager.getInstance(getApplicationContext(), true, false);
         * 주의: enableRangingTimeout을 false로 설정 시, 배터리 소모량이 증가합니다.
         */
        mRecoManager = RECOBeaconManager.getInstance(getApplicationContext(), MainActivity.SCAN_RECO_ONLY, MainActivity.ENABLE_BACKGROUND_RANGING_TIMEOUT);
        mRecoManager.setDiscontinuousScan(MainActivity.DISCONTINUOUS_SCAN);

        this.bindRECOService();
        //this should be set to run in the background.
        //background에서 동작하기 위해서는 반드시 실행되어야 합니다.
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.i("BackMonitoringService", "onDestroy()");
        this.tearDown();
        super.onDestroy();
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Log.i("BackMonitoringService", "onTaskRemoved()");
        super.onTaskRemoved(rootIntent);
    }

    private void bindRECOService() {
        Log.i("BackMonitoringService", "bindRECOService()");

        mRegions = new ArrayList<RECOBeaconRegion>();
        this.generateBeaconRegion();

        mRecoManager.setMonitoringListener(this);
        mRecoManager.bind(this);
    }

    private void generateBeaconRegion() {
        Log.i("BackMonitoringService", "generateBeaconRegion()");

        RECOBeaconRegion recoRegion;

        recoRegion = new RECOBeaconRegion(MainActivity.RECO_UUID, "RECO Sample Region");
        recoRegion.setRegionExpirationTimeMillis(mRegionExpirationTime);
        mRegions.add(recoRegion);
    }

    private void startMonitoring() {
        Log.i("BackMonitoringService", "startMonitoring()");

        mRecoManager.setScanPeriod(mScanDuration);
        mRecoManager.setSleepPeriod(mSleepDuration);

        for(RECOBeaconRegion region : mRegions) {
            try {
                mRecoManager.startMonitoringForRegion(region);
            } catch (RemoteException e) {
                Log.e("BackMonitoringService", "RemoteException has occured while executing RECOManager.startMonitoringForRegion()");
                e.printStackTrace();
            } catch (NullPointerException e) {
                Log.e("BackMonitoringService", "NullPointerException has occured while executing RECOManager.startMonitoringForRegion()");
                e.printStackTrace();
            }
        }
    }

    private void stopMonitoring() {
        Log.i("BackMonitoringService", "stopMonitoring()");

        for(RECOBeaconRegion region : mRegions) {
            try {
                mRecoManager.stopMonitoringForRegion(region);
            } catch (RemoteException e) {
                Log.e("BackMonitoringService", "RemoteException has occured while executing RECOManager.stopMonitoringForRegion()");
                e.printStackTrace();
            } catch (NullPointerException e) {
                Log.e("BackMonitoringService", "NullPointerException has occured while executing RECOManager.stopMonitoringForRegion()");
                e.printStackTrace();
            }
        }
    }

    private void tearDown() {
        Log.i("BackMonitoringService", "tearDown()");
        this.stopMonitoring();

        try {
            mRecoManager.unbind();
        } catch (RemoteException e) {
            Log.e("BackMonitoringService", "RemoteException has occured while executing unbind()");
            e.printStackTrace();
        }
    }

    @Override
    public void onServiceConnect() {
        Log.i("BackMonitoringService", "onServiceConnect()");
        this.startMonitoring();
        //Write the code when RECOBeaconManager is bound to RECOBeaconService
    }

    @Override
    public void didDetermineStateForRegion(RECOBeaconRegionState state, RECOBeaconRegion region) {
        Log.i("BackMonitoringService", "didDetermineStateForRegion()");
        //Write the code when the state of the monitored region is changed
    }

    @Override
    public void didEnterRegion(RECOBeaconRegion region, Collection<RECOBeacon> beacons) {
        /**
         * For the first run, this callback method will not be called.
         * Please check the state of the region using didDetermineStateForRegion() callback method.
         *
         * 최초 실행시, 이 콜백 메소드는 호출되지 않습니다.
         * didDetermineStateForRegion() 콜백 메소드를 통해 region 상태를 확인할 수 있습니다.
         */
        List<RECOBeacon> beaconList = new ArrayList<RECOBeacon>(beacons);
        Collections.sort(beaconList, new NameAscCompare());

        int majorCode;
        int minorCode;
        PlaceServerData placeServerData;
        boolean check = false;
        int notificationID;

        for(RECOBeacon beacon : beaconList){
            majorCode = beacon.getMajor();
            minorCode = beacon.getMinor();
            Log.i("BackMonitoringService", majorCode + "/" + minorCode);
            switch (majorCode + "" + minorCode){
                case Beacon.BEACON_1:
                    placeServerData = new PlaceServerData("근정전","Geunjeongjeon","경복궁 근정전은 조선시대 법궁인 경복궁의 중심 건물로, 신하들이 임금에게 새해 인사를 드리거나 국가의식을 거행하고 외국 사신을 맞이하던 곳이다.", "http://www.cha.go.kr/unisearch/images/national_treasure/1611701.jpg", "11", "02230000", "11", 37.578575, 126.977013, 3);
                    if(beacon.getAccuracy() < 2) {
                        check = true;
                    }
                    break;
                case Beacon.BEACON_2:
                    placeServerData = new PlaceServerData("경회루","Gyeonghoeru","경복궁 근정전 서북쪽 연못 안에 세운 경회루는, 나라에 경사가 있거나 사신이 왔을 때 연회를 베풀던 곳이다.", "http://www.cha.go.kr/unisearch/images/national_treasure/1611724.jpg", "11", "02240000", "11", 37.579773, 126.976051, 1);
                    if(beacon.getAccuracy() < 2) {
                        check = true;
                    }
                    break;
                case Beacon.BEACON_3:
                    placeServerData = new PlaceServerData("자경전","Jagyeongjeon", "자경전은 1867년 경복궁을 다시 지으면서 자미당 터에 고종의 양어머니인 조대비(신정왕후)를 위해 지은 대비전이다.", "http://www.cha.go.kr/unisearch/images/treasure/1613927.jpg", "12",  "08090000", "11", 37.580299, 126.978096, 9);
                    if(beacon.getAccuracy() < 2) {
                        check = true;
                    }
                    break;
                default:
                    placeServerData = new PlaceServerData();
                    check = false;
            }
            if(check) {
                this.popupNotification(placeServerData);
                check = false;
            }
            //Write the code when the device is enter the region
        }
    }

    static class NameAscCompare implements Comparator<RECOBeacon> {
        /**
         * 오름차순(ASC)
         */
        @Override
        public int compare(RECOBeacon arg0, RECOBeacon arg1) {
            // TODO Auto-generated method stub
            return Double.compare(arg1.getAccuracy(), arg0.getAccuracy());
        }

    }

    @Override
    public void didExitRegion(RECOBeaconRegion region) {
        /**
         * For the first run, this callback method will not be called.
         * Please check the state of the region using didDetermineStateForRegion() callback method.
         *
         * 최초 실행시, 이 콜백 메소드는 호출되지 않습니다.
         * didDetermineStateForRegion() 콜백 메소드를 통해 region 상태를 확인할 수 있습니다.
         */

        Log.i("BackMonitoringService", "didExitRegion() - " + region.getUniqueIdentifier());
        //Write the code when the device is exit the region
    }

    @Override
    public void didStartMonitoringForRegion(RECOBeaconRegion region) {
        Log.i("BackMonitoringService", "didStartMonitoringForRegion() - " + region.getUniqueIdentifier());
        //Write the code when starting monitoring the region is started successfully
//        PlaceServerData placeServerData;
//        boolean isNear = false;
//        int notificationID;
//        int majorCode = region.getMajor();
//        int minorCode = region.getMinor();
//        Log.i("dafssd", region.describeContents()+"");
//        switch (majorCode + "" + minorCode) {
//            case "50119853":
//                placeServerData = new PlaceServerData("근정전", "경복궁 근정전은 조선시대 법궁인 경복궁의 중심 건물로, 신하들이 임금에게 새해 인사를 드리거나 국가의식을 거행하고 외국 사신을 맞이하던 곳이다.", "http://www.cha.go.kr/unisearch/images/national_treasure/1611701.jpg", "11", "02230000", "11", 37.578575, 126.977013);
//                notificationID = 9999;
//                Log.i("proximity", region.get + "");
//                if (region.() < 2) isNear = true;
//                break;
//            case "50119854":
//                placeServerData = new PlaceServerData("경회루", "경복궁 근정전 서북쪽 연못 안에 세운 경회루는, 나라에 경사가 있거나 사신이 왔을 때 연회를 베풀던 곳이다.", "http://www.cha.go.kr/unisearch/images/national_treasure/1611724.jpg", "11", "02240000", "11", 37.579773, 126.976051);
//                notificationID = 9998;
//                Log.i("proximity", beacon.getAccuracy() + "");
//                if (beacon.getAccuracy() < 2) isNear = true;
//                break;
//            case "50119855":
//            default:
//                placeServerData = new PlaceServerData("자경전", "자경전은 1867년 경복궁을 다시 지으면서 자미당 터에 고종의 양어머니인 조대비(신정왕후)를 위해 지은 대비전이다.", "http://www.cha.go.kr/unisearch/images/treasure/1613927.jpg", "12", "08090000", "11", 37.580299, 126.978096);
//                notificationID = 9997;
//                Log.i("proximity", beacon.getAccuracy() + "");
//                if (beacon.getAccuracy() < 2) isNear = true;
//                break;
//        }
//        if (isNear) {
//            this.popupNotification(placeServerData, notificationID);
//            isNear = false;
//        }
    }

    private void popupNotification(PlaceServerData placeServerData) {
        Log.i("BackMonitoringService", "popupNotification()");
        PendingIntent pendingIntent = getPendingIntent(placeServerData);
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setStyle(bigTextStyle)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(getBitmap(placeServerData.getImageUrl()))
                .setColor(getResources().getColor(R.color.colorPrimary))
                .setContentTitle(placeServerData.getName())
                .setContentText(placeServerData.getSimpleContent())
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        nm.notify(mNotificationID, builder.build());
//        mNotificationID = (mNotificationID - 1) % 1000 + 9000;
    }

    private Bitmap getBitmap(String url){
        try{
            URL imgUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) imgUrl.openConnection();
            connection.setDoInput(true); //url로 input받는 flag 허용
            connection.connect(); //연결
            InputStream is = connection.getInputStream(); // get inputstream
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            return bitmap;
        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    private PendingIntent getPendingIntent(PlaceServerData placeServerData){
        Intent intent = new Intent(getBaseContext(), PlaceInfoActivity.class);
        intent.putExtra(PlaceInfoActivity.PLACEDATA, placeServerData);
        PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return pendingIntent;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // This method is not used
        return null;
    }

    @Override
    public void onServiceFail(RECOErrorCode errorCode) {
        //Write the code when the RECOBeaconService is failed.
        //See the RECOErrorCode in the documents.
        return;
    }

    @Override
    public void monitoringDidFailForRegion(RECOBeaconRegion region, RECOErrorCode errorCode) {
        //Write the code when the RECOBeaconService is failed to monitor the region.
        //See the RECOErrorCode in the documents.
        Log.i("BackMonitoringService", errorCode.toString());
        return;
    }
}

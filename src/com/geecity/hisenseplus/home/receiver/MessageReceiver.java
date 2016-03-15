package com.geecity.hisenseplus.home.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import cn.jpush.android.api.JPushInterface;

import com.geecity.hisenseplus.home.activity.notice.NoticeActivity;
import com.lidroid.xutils.util.LogUtils;


public class MessageReceiver extends BroadcastReceiver {
    

    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        LogUtils.d(intent.getAction() + ", extras: " + printBundle(bundle));
        if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
          String messge = intent.getStringExtra(KEY_MESSAGE);
          String extras = intent.getStringExtra(KEY_EXTRAS);
          StringBuilder showMsg = new StringBuilder();
          showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
          if (!TextUtils.isEmpty(extras)) {
              showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
          }
        }
        

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            // LogUtils.d("[PushReceiver] 接收Registration Id : " + regId);
            // send the Registration Id to your server...

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            // LogUtils.d("[PushReceiver] 接收到推送下来的自定义消息: " +
            // bundle.getString(JPushInterface.EXTRA_MESSAGE));
            processCustomMessage(context, bundle);

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            // LogUtils.d("[PushReceiver] 接收到推送下来的通知");
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            // LogUtils.d("[PushReceiver] 接收到推送下来的通知的ID: " + notifactionId);

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            startDetailActivity(context, bundle);
        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            LogUtils.d("ACTION_RICHPUSH_CALLBACK");
            // LogUtils.d("[PushReceiver] 收到到RICH PUSH CALLBACK: " +
            // bundle.getString(JPushInterface.EXTRA_EXTRA));
            // 在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected =
                            intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            LogUtils.d("[PushReceiver]" + intent.getAction() + " connected state change to "
                            + connected);
        } else {
            LogUtils.d("[PushReceiver] Unhandled intent - " + intent.getAction());
        }
    }
    

    private void startDetailActivity(Context context, Bundle bundle) {
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                String pushStr = bundle.getString(key);
                if(TextUtils.isEmpty(pushStr)){
                	LogUtils.d("Push Message is empty");
                	return;
                }
                LogUtils.d(pushStr);
                // TODO 启动通知消息界面
                Intent intent = new Intent(context, NoticeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
                context.startActivity(intent);
//                PushInfo info = new Gson().fromJson(pushStr, PushInfo.class);
//                if(pushStr == null){
//                  return;
//                }
//                if (info != null && info.isWholeInfo()) {
//                    Class<?> acClass;
//                    String action;
//                    // 0-客源，1-车源
//                    if (info.getSourceType().equals("0")) {
//                        acClass = CustomerDetailActivity.class;
//                        action = ACTION_DETAIL_CUSTOMER;
//                    } else if (info.getSourceType().equals("1")) {
//                        acClass = CarSourceDetailActivity.class;
//                        action = ACTION_DETAIL_CAR;
//                    } else {
//                        LogUtils.e("Unsupport source type : " + info.getSourceType());
//                        return;
//                    }
//                    bundle.clear();
//                    bundle.putInt(CarSourceDetailActivity.INTENT_KEY_SOURCE_TYPE,
//                                    Integer.parseInt(info.getDetailType()));
//                    bundle.putInt(CarSourceDetailActivity.INTENT_KEY_SOURCE_ID,
//                                    Integer.parseInt(info.getDetailID()));
//                    Intent intent = new Intent(context, acClass);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
//                    intent.putExtras(bundle);
//                    context.startActivity(intent);
//                } else {
//                    LogUtils.e("PushInfo Error");
//                }
                break;
            }
        }
    }
    
    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                // sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                // sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            } else {
                // sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }    // send msg to MainActivity
    private void processCustomMessage(Context context, Bundle bundle) {
        // if (MainActivity.isForeground) {
        // String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        // String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        // Intent msgIntent = new Intent(MainActivity.MESSAGE_RECEIVED_ACTION);
        // msgIntent.putExtra(MainActivity.KEY_MESSAGE, message);
        // if (!ExampleUtil.isEmpty(extras)) {
        // try {
        // JSONObject extraJson = new JSONObject(extras);
        // if (null != extraJson && extraJson.length() > 0) {
        // msgIntent.putExtra(MainActivity.KEY_EXTRAS, extras);
        // }
        // } catch (JSONException e) {
        //
        // }
        //
        // }
        // context.sendBroadcast(msgIntent);
        // }
    }
}
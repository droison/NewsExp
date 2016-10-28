package xyz.chaisong.lib.util;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import xyz.chaisong.lib.R;
import xyz.chaisong.lib.network.response.RespError;

/**
 * Created with IntelliJ IDEA.
 * User: Felix
 * Date: 4/9/15
 * Time: 17:28
 * QQ:74104
 * Email:lovejiuwei@gmail.com
 */
public class ToastUtil {

    private static Toast toast;
    private static View toastView;
    private static TextView toastMsgView;
    public static Context defaultContext;
    private static ImageView toastImage;

    public static void cancelToast() {
        if (toast == null) {
            return;
        }
        toast.cancel();
    }

    public static void showToast(Context context, String msg) {
        // 避免没有信息时，弹出无内容的黑框
        if (TextUtils.isEmpty( msg )) {
            return;
        }
        initToast();
        toastMsgView.setText(msg);
        toastMsgView.setVisibility(View.VISIBLE);
        toast.setDuration( Toast.LENGTH_SHORT );
        toast.setGravity( Gravity.CENTER, 0, 0 );
        toast.show();
    }

    public static void showToastWithImage(String msg, int resourceId){
        initToast();
        if (!TextUtils.isEmpty(msg)){
            toastMsgView.setText(msg);
            toastMsgView.setVisibility(View.VISIBLE);
        } else {
            toastView.setBackgroundColor(0x00000000);
        }
        toastImage.setImageResource(resourceId);
        toastImage.setVisibility(View.VISIBLE);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    private static void initToast(){
        if (toast == null || toastMsgView == null) {
            toastView = LayoutInflater.from( defaultContext ).inflate( R.layout.view_toast, null );
            toast = new Toast( defaultContext );
            toast.setView(toastView);
            toastMsgView = (TextView) toastView.findViewById( R.id.message );
            toastImage = (ImageView)toastView.findViewById(R.id.image);
            toastImage.setVisibility(View.VISIBLE);
        }
        toastView.setBackgroundColor(defaultContext.getResources().getColor(R.color.toast_bg));
        toastMsgView.setText("");
        toastImage.setImageBitmap(null);
        toastImage.setVisibility(View.GONE);
        toastMsgView.setVisibility(View.GONE);
    }

    public static void showToast(Context context, int msgId) {
        showToast(context, context.getString(msgId));
    }

    public static void showToast(int msgId) {
        showToast( defaultContext, defaultContext.getString( msgId ) );
    }

    public static void showToast(String msg) {
        showToast( defaultContext, msg);
    }

    /**
     * 此处弹出的错误为统一服务器返回的错误信息.
     * @param failData 传入的http请求错误
     * @return true表示当前已经做提示了,false表示没提示,调用方自己去处理.
     */
    public static boolean showRequestErrorToast(RespError failData){
        if (failData != null && failData.getResponseErrorType() == RespError.ErrorType.responseNot200Error) {
            showToast(failData.getMessage());
            return true;
        }
        return false;
    }
}

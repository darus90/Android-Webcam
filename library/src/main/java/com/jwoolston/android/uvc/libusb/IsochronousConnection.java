package com.jwoolston.android.uvc.libusb;

import android.content.Context;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.support.annotation.NonNull;
import android.util.Log;
import java.nio.ByteBuffer;

/**
 * @author Jared Woolston (Jared.Woolston@gmail.com)
 */
public class IsochronousConnection {

    private static final String TAG = "UsbDeviceIsoConnection";

    static {
        System.loadLibrary("usb-runtime");
    }

    /**
     * The application context.
     */
    private static Context sContext;

    /**
     * The {@link UsbManager} for this application.
     */
    private static UsbManager sUsbManager;

    /**
     * Initializes the libusb JNI wrapper. This should be provided the Application
     * {@link Context}, though it will call {@link Context#getApplicationContext()}
     * to be sure.
     *
     * @param context {@link Context} The application context.
     */
    public IsochronousConnection(Context context, int fd) {
        sContext = context.getApplicationContext();
        sUsbManager = (UsbManager) sContext.getSystemService(Context.USB_SERVICE);
        final int retval = initialize(fd);
        Log.v(TAG, "Initialization result: " + retval);
    }

    private native int initialize(int fd);

    public native int deinitialize();

    public native int isochronousTransfer(UsbDeviceConnection connection, ByteBuffer buffer);

    public native int controlTransfer(int requestType,
                                      int request,
                                      int value,
                                      int index,
                                      byte[] buffer,
                                      int length,
                                      int timeout);

    public native boolean selectAlternateSetting(int interfaceId, int alternateSetting);

}

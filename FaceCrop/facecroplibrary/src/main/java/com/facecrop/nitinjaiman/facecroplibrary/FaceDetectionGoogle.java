/*
 * Copyright (C) The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.facecrop.nitinjaiman.facecroplibrary;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.Log;
import android.util.SparseArray;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;
import com.google.android.gms.vision.face.Landmark;

/**
 * Demonstrates basic usage of the GMS vision face detector by running face landmark detection on a
 * photo and displaying the photo with associated landmarks in the UI.
 */
public class FaceDetectionGoogle {
    private static final String TAG = "PhotoViewerActivity";
    private static final String LOG_TAG = "FaceDetection";
    ImageView imageView;
    Context context;
    Bitmap mainBitmap;

    public FaceDetectionGoogle(Context context) {

        this.context = context;

    }

    public Bitmap getMainBitmap() {
        return mainBitmap;
    }

    public void setMainBitmap(Bitmap mainBitmap) {
        this.mainBitmap = mainBitmap;
    }

    public Bitmap getFaceDetectedBitmap() {


        // A new face detector is created for detecting the face and its landmarks.
        //
        // Setting "tracking enabled" to false is recommended for detection with unrelated
        // individual images (as opposed to video or a series of consecutively captured still
        // images).  For detection on unrelated individual images, this will give a more accurate
        // result.  For detection on consecutive images (e.g., live video), tracking gives a more
        // accurate (and faster) result.
        //
        // By default, landmark detection is not enabled since it increases detection time.  We
        // enable it here in order to visualize detected landmarks.
        FaceDetector detector = new FaceDetector.Builder(context)
                .setTrackingEnabled(false)
                .setLandmarkType(FaceDetector.ALL_LANDMARKS).setClassificationType(FaceDetector.NO_CLASSIFICATIONS).setMode(FaceDetector.FAST_MODE)
                //.setProminentFaceOnly(true)
                .build();

        Bitmap faceDetectedBitmap = null;
        SparseArray<Face> faces;
        Detector<Face> safeDetector = null;
        try {

            // This is a temporary workaround for a bug in the face detector with respect to operating
            // on very small images.  This will be fixed in a future release.  But in the near term, use
            // of the SafeFaceDetector class will patch the issue.
            safeDetector = new SafeFaceDetector(detector);

            // Create a frame from the bitmap and run face detection on the frame.
            Frame frame = new Frame.Builder().setBitmap(mainBitmap).build();
            faces = safeDetector.detect(frame);

            if (!safeDetector.isOperational()) {
                // Note: The first time that an app using face API is installed on a device, GMS will
                // download a native library to the device in order to do detection.  Usually this
                // completes before the app is run for the first time.  But if that download has not yet
                // completed, then the above call will not detect any faces.
                //
                // isOperational() can be used to check if the required native library is currently
                // available.  The detector will automatically become operational once the library
                // download completes on device.
                Log.w(TAG, "Face detector dependencies are not yet available.");

                // Check for low storage.  If there is low storage, the native library will not be
                // downloaded, so detection will not become operational.
                IntentFilter lowstorageFilter = new IntentFilter(Intent.ACTION_DEVICE_STORAGE_LOW);
                boolean hasLowStorage = context.registerReceiver(null, lowstorageFilter) != null;

                if (hasLowStorage) {
                    Toast.makeText(context, "low storage", Toast.LENGTH_LONG).show();
                    Log.w(TAG, "low storage");
                }
            }

            //   FaceView overlay = (FaceView) findViewById(R.id.faceView);
            // overlay.setContent(bitmap, faces);


            for (int i = 0; i < faces.size(); ++i) {
                Log.d("dsf", faces.size() + "");
                Face face = faces.valueAt(i);


                //  canvas.drawCircle((float) (face.getPosition().x),(float) (face.getPosition().y),50,paint2);


                int nx = 0;
                int ny = 0;

                int rex = 0;
                int lex = 0;
                int rey = 0;
                int ley = 0;

                int ex = 0;
                int ey = 0;

                int jx = 0;
                int jy = 0;


                for (Landmark landmark : face.getLandmarks()) {


                    if (landmark.getType() == Landmark.LEFT_EYE) {
                        lex = (int) (landmark.getPosition().x);
                        ley = (int) (landmark.getPosition().y);
                    }

                    if (landmark.getType() == Landmark.RIGHT_EYE) {
                        rex = (int) (landmark.getPosition().x);
                        rey = (int) (landmark.getPosition().y);
                    }

                    if (lex != 0 && rex != 0) {

                        ex = (lex + rex) / 2;

                    }
                    if (ley != 0 && rey != 0) {

                        ey = ley;

                    }

                    if (landmark.getType() == Landmark.BOTTOM_MOUTH) {
                        jx = (int) (landmark.getPosition().x);
                        jy = (int) (landmark.getPosition().y);
                    }


                }

                if (ex != 0 && ley != 0 && jx != 0 && jy != 0) {
                    int radius = (int) Math.sqrt(Math.pow(ex - jx, 2) + Math.pow(ley - jy, 2));

                    // faceDetectedBitmap = getCroppedBitmap(mainBitmap, ex, rey, (float) (radius * 1.8));
                    Log.e("x", ex + "");
                    Log.e("y", ey + "");
                    Log.e("width", mainBitmap.getWidth() + "");
                    Log.e("height", mainBitmap.getHeight() + "");


                    int mainWidth = mainBitmap.getWidth();
                    int mainHeight = mainBitmap.getHeight();

                    if (mainHeight >= mainWidth) {

                        if ((ey - (mainWidth / 2)) > 0 && ((ey - (mainWidth / 2)) + mainWidth) < mainHeight) {

                            Log.e(LOG_TAG, "1,1");

                            faceDetectedBitmap = Bitmap.createBitmap(mainBitmap, 0, (ey - (mainWidth / 2)), mainWidth, mainWidth);

                        } else {

                            if ((ey - (mainWidth / 2)) < 0) {

                                Log.e(LOG_TAG, "1,2");

                                faceDetectedBitmap = Bitmap.createBitmap(mainBitmap, 0, 0, mainWidth, mainWidth);


                            } else if (((ey - (mainWidth / 2)) + mainWidth) > mainHeight) {

                                Log.e(LOG_TAG, "1,3");
                                faceDetectedBitmap = Bitmap.createBitmap(mainBitmap, 0, (ey - (mainWidth - (mainHeight - ey))), mainWidth, mainWidth);


                            } else {

                                Log.e(LOG_TAG, "1,4");
                                faceDetectedBitmap = Bitmap.createBitmap(mainBitmap, 0, 0, mainHeight, mainHeight);


                            }


                        }

                    } else {

                        if ((ex - (mainHeight / 2)) > 0 && ((ex - (mainHeight / 2)) + mainHeight) < mainWidth) {

                            Log.e(LOG_TAG, "2,1");
                            faceDetectedBitmap = Bitmap.createBitmap(mainBitmap, (ex - (mainHeight / 2)), 0, mainHeight, mainHeight);

                        } else {

                            if ((ex - (mainHeight / 2)) < 0) {

                                Log.e(LOG_TAG, "2,2");

                                faceDetectedBitmap = Bitmap.createBitmap(mainBitmap, 0, 0, mainHeight, mainHeight);


                            } else if (((ex - (mainHeight / 2)) + mainHeight) > mainWidth) {


                                Log.e(LOG_TAG, "2,3");
                                faceDetectedBitmap = Bitmap.createBitmap(mainBitmap, (ex - (mainHeight - (mainWidth - ex))), 0, mainHeight, mainHeight);


                            } else {

                                Log.e(LOG_TAG, "2,4");
                                faceDetectedBitmap = Bitmap.createBitmap(mainBitmap, 0, 0, mainHeight, mainHeight);


                            }


                        }


                    }


//imageView.setImageDrawable();


                }

            }


            // Although detector may be used multiple times for different images, it should be released
            // when it is no longer needed in order to free native resources.
            safeDetector.release();


        } catch (Exception e) {
            e.printStackTrace();
            if (safeDetector != null) {
                safeDetector.release();
            }
        }
        return faceDetectedBitmap;

    }


    /**
     * get cropped bitmap using this method
     *
     * @param bitmap
     * @param x
     * @param y
     * @param radius
     * @return
     */
    public Bitmap getCroppedBitmap(Bitmap bitmap, float x, float y, float radius) {
        // Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
        // bitmap.getHeight(), Config.ARGB_8888);
        // Canvas canvas = new Canvas(output);
        //
        // final int color = 0xff424242;
        // final Paint paint = new Paint();
        // final Rect rect = new Rect(0, 0, bitmap.getWidth(),
        // bitmap.getHeight());
        //
        // paint.setAntiAlias(true);
        // canvas.drawARGB(0, 0, 0, 0);
        // paint.setColor(color);
        // // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        // canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
        // bitmap.getWidth() / 2, paint);
        // paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        // canvas.drawBitmap(bitmap, rect, rect, paint);
        // // Bitmap _bmp = Bitmap.createScaledBitmap(output, 60, 60, false);
        // // return _bmp;
        // return output;

        int targetWidth = bitmap.getWidth();
        int targetHeight = bitmap.getHeight();
        Bitmap targetBitmap = Bitmap.createBitmap(targetWidth, targetHeight,
                Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(targetBitmap);
        Path path = new Path();

        path.addCircle(x,
                y,
                radius,
                Path.Direction.CCW);

        canvas.clipPath(path);

        Bitmap sourceBitmap = bitmap;

        canvas.drawBitmap(sourceBitmap, new Rect(0, 0, sourceBitmap.getWidth(),
                sourceBitmap.getHeight()), new Rect(0, 0, targetWidth,
                targetHeight), null);
        return targetBitmap;


    }


}
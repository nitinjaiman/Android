package com.facecrop.nitinjaiman.facecroplibrary;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

/**
 * Created by nitinjaiman on 05/08/16.
 */
public class FaceCrop {

    Context context;

    FaceDetectionGoogle faceDetectionGoogle;

    public FaceCrop(Context context) {

        this.context = context;
        faceDetectionGoogle = new FaceDetectionGoogle(context);


    }

    public void setFaceCrop(final ImageView imageView, Uri uri) {


        Glide.with(context).load(uri).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {

                        faceDetectionGoogle.setMainBitmap(resource);
                        Bitmap faceDetectedBitmap = faceDetectionGoogle.getFaceDetectedBitmap();
                        if (resource != null) {

                            imageView.setImageBitmap(faceDetectedBitmap);
                        } else {

                            imageView.setImageBitmap(resource);
                        }


                    }
                });


    }

    public void setFaceCropAsync(final ImageView imageView, Uri uri) {


        Glide.with(context).load(uri).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {


                        imageView.setImageBitmap(resource);


                        new FaceDetectionBackground(faceDetectionGoogle, resource, imageView).execute();


                    }
                });

    }

    public void setFaceCrop(ImageView imageView, Bitmap bitmap) {

        faceDetectionGoogle.setMainBitmap(bitmap);
        Bitmap faceDetectedBitmap = faceDetectionGoogle.getFaceDetectedBitmap();
        if (bitmap != null) {

            imageView.setImageBitmap(faceDetectedBitmap);
        } else {

            imageView.setImageBitmap(bitmap);
        }


    }

    public void setFaceCropAsync(ImageView imageView, Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);


        new FaceDetectionBackground(faceDetectionGoogle, bitmap, imageView).execute();

    }

    class FaceDetectionBackground extends AsyncTask<Void, Void, Bitmap> {

        FaceDetectionGoogle faceDetectionGoogle;
        Bitmap bitmap;
        ImageView imageView;

        FaceDetectionBackground(FaceDetectionGoogle faceDetectionGoogle, Bitmap bitmap, ImageView imageView) {


            this.faceDetectionGoogle = faceDetectionGoogle;
            this.bitmap = bitmap;
            this.imageView = imageView;

        }


        @Override
        protected Bitmap doInBackground(Void... params) {

            faceDetectionGoogle.setMainBitmap(bitmap);
            Bitmap faceDetectedBitmap = faceDetectionGoogle.getFaceDetectedBitmap();


            faceDetectionGoogle.setMainBitmap(null);

            return faceDetectedBitmap;
        }


        @Override
        protected void onPostExecute(Bitmap aVoid) {


            super.onPostExecute(aVoid);

            if (aVoid != null) {


                Log.e("bitmap", "facedetected");
                this.imageView.setImageBitmap(aVoid);

            } else {
                Log.e("bitmap", "null");
                this.imageView.setImageBitmap(bitmap);

            }


        }
    }


}

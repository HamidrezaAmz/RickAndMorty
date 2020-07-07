package ir.vasl.navigationcomponentimpl.view.customViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.vasl.recyclerlibrary.utils.LogHelper;

import java.io.File;

import ir.vasl.navigationcomponentimpl.R;
import ir.vasl.navigationcomponentimpl.utils.GlideApp;

public class MagicalImageView extends AppCompatImageView {

    boolean imageSquare = false;

    boolean imageRound = false;

    boolean imageAspectRatio = false;

    private LogHelper logHelper = new LogHelper(MagicalImageView.class);

    private Bitmap selectedImageBitmap;

    private Transformation<Bitmap> bitmapTransformation;

    public MagicalImageView(Context context) {
        super(context);
    }

    public MagicalImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MagicalImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        try {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MagicalImageView, 0, 0);
            imageSquare = a.getBoolean(R.styleable.MagicalImageView_square_image, false);
            imageRound = a.getBoolean(R.styleable.MagicalImageView_round_image, false);
            imageAspectRatio = a.getBoolean(R.styleable.MagicalImageView_aspect_ratio, false);
        } catch (Exception ex) {
            logHelper.e("ImageViewCustom constructor ", ex);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        try {
            if (imageSquare) {
                setMeasuredDimension(widthMeasureSpec, widthMeasureSpec);
            } else if (imageAspectRatio) {
                // 16 * 9
                int width = getMeasuredWidth();

                //force a 16:9 aspect ratio
                int height = Math.round(width * .5625f);
                setMeasuredDimension(width, height);
            }
        } catch (Exception e) {
            logHelper.e("onMeasure", e);
        }
    }

    public void setImageUrl(File file) {
        try {
            GlideApp
                    .with(getContext())
                    .load(file)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .centerCrop()
                    .into(this);
        } catch (Exception e) {
            logHelper.e(e);
        }
    }

    public void setImageUrl(Uri url) {
        setImageUrl(url.toString());
    }

    public void setImageUrl(String url) {
        try {
            GlideApp
                    .with(getContext())
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    //.skipMemoryCache(true)
                    .fitCenter()
                    .apply(new RequestOptions().placeholder(R.drawable.ic_place_holder).error(R.drawable.ic_place_holder))
                    .into(this);
        } catch (Exception e) {
            logHelper.e(e);
        }
    }

    public void setImageUrl(Bitmap bitmap) {
        try {
            GlideApp
                    .with(getContext())
                    .load(bitmap)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .fitCenter()
                    .into(this);
        } catch (Exception e) {
            logHelper.e(e);
        }
    }

    public void setImageUrlRound(String url) {
        try {
            GlideApp
                    .with(getContext())
                    .load(url)
                    .apply(RequestOptions.circleCropTransform())
                    .into(this);
        } catch (Exception e) {
            logHelper.e(e);
        }
    }

    public void setImageUrlRound(Uri uri) {
        try {
            GlideApp
                    .with(getContext())
                    .load(uri)
                    .apply(RequestOptions.circleCropTransform())
                    .into(this);
        } catch (Exception e) {
            logHelper.e(e);
        }
    }

    public void setImageUrlRound(String url, int placeHolder) {
        try {
            GlideApp
                    .with(getContext())
                    .load(url)
                    .placeholder(placeHolder)
                    .error(placeHolder)
                    .apply(RequestOptions.circleCropTransform())
                    .into(this);
        } catch (Exception e) {
            logHelper.e(e);
        }
    }

    public void setImageUrl(String url, int placeHolder) {
        try {
            GlideApp
                    .with(getContext())
                    .load(url)
                    .placeholder(placeHolder)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .centerCrop()
                    .into(this);
        } catch (Exception e) {
            logHelper.e(e);
        }
    }

    public void setImageUrl(String url, boolean circle) {
        try {
            if (circle) {
                GlideApp
                        .with(getContext())
                        .load(url)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(false)
                        .centerCrop()
                        .optionalCircleCrop()
                        .into(this);
            } else {
                GlideApp
                        .with(getContext())
                        .load(url)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(false)
                        .centerCrop()
                        .into(this);
            }
        } catch (Exception e) {
            logHelper.e(e);
        }
    }

    public void setImageUrl(int resource) {
        try {
            GlideApp
                    .with(getContext())
                    .load(resource)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(false)
                    .into(this);
        } catch (Exception e) {
            logHelper.e("ImageViewSetUrl set Image resource : " + e);
        }
    }

    public void setImageResRound(int resource) {
        try {
            GlideApp
                    .with(getContext())
                    .load(resource)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(false)
                    .apply(RequestOptions.circleCropTransform())
                    .into(this);
        } catch (Exception e) {
            logHelper.e("ImageViewSetUrl set Image resource : " + e);
        }
    }

    /*public void setImageUrlCurve(String url, int radius) {
        try {
            GlideApp
                    .with(getContext())
                    .load(url)
                    .apply(new RequestOptions().transform(new CenterCrop(), new RoundedCorners(((int) AndroidUtils.convertDpToPixel(radius, getContext())))))
                    .placeholder(R.drawable.background_global_place_holder)
                    .into(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    public void setImageUrlCurve(int res) {
        try {
            GlideApp
                    .with(getContext())
                    .load(res)
                    .centerCrop()
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                    .into(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setImageBitmapRound(Bitmap bitmap, int placeHolder) {
        try {
            GlideApp
                    .with(getContext())
                    .load(bitmap)
                    .placeholder(placeHolder)
                    .apply(RequestOptions.circleCropTransform())
                    .into(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setImageUriRound(Uri uri, int placeHolder) {
        try {
            GlideApp
                    .with(getContext())
                    .load(uri)
                    .placeholder(placeHolder)
                    .apply(RequestOptions.circleCropTransform().error(R.drawable.ic_place_holder))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(this);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setImageUriCurved(Uri uri, int placeHolder) {
        try {
            GlideApp
                    .with(getContext())
                    .load(uri)
                    .placeholder(placeHolder)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                    .into(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setImageUriWithPlaceHolder(Uri uri, int placeHolder) {
        try {
            GlideApp
                    .with(getContext())
                    .load(uri)
                    .placeholder(placeHolder)
                    .error(placeHolder)
                    .into(this);
        } catch (Exception e) {
            logHelper.e(e);
        }
    }


    public void setImageUrlWithPlaceHolder(String url, int placeHolder) {
        try {
            GlideApp
                    .with(getContext())
                    .load(url)
                    .placeholder(placeHolder)
                    .error(placeHolder)
                    .into(this);
        } catch (Exception e) {
            logHelper.e(e);
        }
    }

    public void setImageUrlRoundWithPlaceHolder(String url, int placeHolder) {
        try {
            Bitmap placeholder = BitmapFactory.decodeResource(getResources(), placeHolder);
            RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), placeholder);
            circularBitmapDrawable.setCircular(true);
            GlideApp
                    .with(getContext())
                    .load(url)
                    .placeholder(circularBitmapDrawable)
                    .error(circularBitmapDrawable)
                    .apply(RequestOptions.circleCropTransform())
                    .into(this);
        } catch (Exception e) {
            logHelper.e(e);
        }
    }

    public void setImageUrlCurveWithRadius(int res, int radius, int placeHolder) {
        try {
            GlideApp
                    .with(getContext())
                    .load(res)
                    .centerCrop()
                    .placeholder(placeHolder)
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(radius)))
                    .into(this);
        } catch (Exception e) {
            logHelper.e(e);
        }
    }

    public void setImageUrlCurveWithRadius(String url, int radius, int placeHolder) {
        try {
            GlideApp
                    .with(getContext())
                    .load(url)
                    .centerCrop()
                    .placeholder(placeHolder)
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(radius)))
                    .into(this);
        } catch (Exception e) {
            logHelper.e(e);
        }
    }


}

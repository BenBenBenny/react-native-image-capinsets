package dk.madslee;

import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.NinePatchDrawable;
import android.widget.ImageView;
import dk.madslee.utils.NinePatchBitmapFactory;
import dk.madslee.utils.RCTImageLoaderListener;
import dk.madslee.utils.RCTImageLoaderTask;


public class RCTImageCapInsetView extends ImageView {
    private Rect mCapInsets;

    public RCTImageCapInsetView(Context context) {
        super(context);

        mCapInsets = new Rect();
    }

    public void setCapInsets(Rect insets) {
        mCapInsets = insets;
    }

    public void setSource(String uri) {
        final String key = uri + "-" + mCapInsets.toShortString();
        final RCTImageCache cache = RCTImageCache.getInstance();

        if (cache.has(key)) {
            setBackground(cache.get(key));
            return;
        }

        RCTImageLoaderTask task = new RCTImageLoaderTask(uri, getContext(), new RCTImageLoaderListener() {
            @Override
            public void onImageLoaded(Bitmap bitmap) {
                int ratio = Math.round(bitmap.getDensity() / 160);
                int top = mCapInsets.top * ratio;
                int right = bitmap.getWidth() - (mCapInsets.right * ratio);
                int bottom = bitmap.getHeight() - (mCapInsets.bottom * ratio);
                int left = mCapInsets.left * ratio;

                NinePatchDrawable ninePatchDrawable = NinePatchBitmapFactory.createNinePathWithCapInsets(getResources(), bitmap, top, left, bottom, right, null);
                setBackground(ninePatchDrawable);

                cache.put(key, ninePatchDrawable);
            }
        });

        task.execute();
    }
}
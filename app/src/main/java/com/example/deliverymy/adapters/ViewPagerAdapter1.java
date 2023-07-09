package com.example.deliverymy.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.deliverymy.R;


public class ViewPagerAdapter1 extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private Integer[] images = {R.string.review1,R.string.review2,R.string.review3,
            R.string.review4,R.string.review5,R.string.review6,R.string.review7};

    private Integer[] reviewUsers = {R.string.reviewuser1,R.string.reviewuser2,R.string.reviewuser3,
            R.string.reviewuser4,R.string.reviewuser5,R.string.reviewuser6,R.string.reviewuser7};

    public ViewPagerAdapter1(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.review_custom_layout, null);
        TextView review_text = (TextView) view.findViewById(R.id.review_text);
        TextView review_user = (TextView) view.findViewById(R.id.review_user);

        review_text.setText(images[position]);
        review_user.setText(reviewUsers[position]);


        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);

    }
}
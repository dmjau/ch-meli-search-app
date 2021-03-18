package com.cursoandroid.melisearchapp.ui.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.cursoandroid.melisearchapp.domain.models.Picture
import com.squareup.picasso.Picasso
/*
 * Adapter para poder utilizar el recycler
 */
class PictureAdapter(val imageUrls: ArrayList<Picture>) : PagerAdapter() {

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return imageUrls.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val imageView = ImageView(container.context)

        Picasso.get()
            .load(imageUrls[position].secure_url)
            .into(imageView);

        container.addView(imageView);
        return imageView;
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

}
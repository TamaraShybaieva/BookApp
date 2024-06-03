package com.books.app.presentation.ui.adapter

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.books.app.R
import com.books.app.model.BannerSlide
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope.coroutineContext
import org.jetbrains.annotations.Nullable
import java.io.IOException
import java.io.InputStream
import java.net.URL
import java.security.KeyStore
import java.security.cert.Certificate
import java.security.cert.X509Certificate
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLPeerUnverifiedException
import javax.net.ssl.TrustManagerFactory
import kotlin.coroutines.coroutineContext

class ViewPagerAdapter(private val clickListener: (BannerSlide) -> Unit): PagerAdapter() {

    private val items = mutableListOf<BannerSlide>()
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    fun submitList(list: List<BannerSlide>) {
        items.clear()
        items.addAll(list)
    }

    override fun getCount(): Int {
        return 3
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(container.context).inflate(R.layout.item_view_pager, container, false)
        val imageView = container.findViewById<ImageView>(R.id.imageView3)

        if (items.size > 0) {
            val item = items[position]
            val url = "https://unsplash.it/600/300"

            val glideUrl = GlideUrl(url, LazyHeaders.Builder()
                   // .addHeader("User-Agent", )
                    .build())
            Glide.with(container.context)
                    .load(glideUrl)
                    .error(R.drawable.error_placeholder)
                    .into((object : CustomTarget<Drawable>() {
                        override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                            imageView.setImageDrawable(resource)
                        }

                        override fun onLoadCleared(@Nullable placeholder: Drawable?) {}

                        override fun onLoadFailed(errorDrawable: Drawable?) {
                            super.onLoadFailed(errorDrawable)

                        }
                    }))
            container.setOnClickListener {
                clickListener(item)
            }
            container.addView(view)
        }
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}

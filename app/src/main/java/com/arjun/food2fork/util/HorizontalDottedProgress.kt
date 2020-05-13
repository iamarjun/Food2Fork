package com.arjun.food2fork.util

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.Transformation
import androidx.core.content.ContextCompat
import com.arjun.food2fork.R


class HorizontalDottedProgress : View {
    // distance between neighbour dot centres
    private var mDotStep = 5

    // actual dot radius
    private var mDotRadius = 1

    // Bounced Dot Radius
    private var mBigDotRadius = 2

    private val paint = Paint()

    // to get identified in which position dot has to bounce
    private var mDotPosition = 0
    private var mDotCount = DEF_COUNT
    private var mTimeout = DEF_TIMEOUT
    private var mDotColor: Int = ContextCompat.getColor(context, R.color.colorAccent)

    constructor(context: Context?) : super(context) {
        initDotSize()
    }

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
        initDotSize()
        applyAttrs(context, attrs)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        initDotSize()
        applyAttrs(context, attrs)
    }

    private fun initDotSize() {
        val scale = resources.displayMetrics.density
        mDotStep = (mDotStep * scale).toInt()
        mDotRadius = (mDotRadius * scale).toInt()
        mBigDotRadius = (mBigDotRadius * scale).toInt()
    }

    private fun applyAttrs(
        context: Context,
        attrs: AttributeSet?
    ) {
        val a = context.theme.obtainStyledAttributes(
            attrs, R.styleable.HorizontalDottedProgress, 0, 0
        )
        try {
            mDotColor = a.getColor(R.styleable.HorizontalDottedProgress_color, mDotColor)
            mDotCount = a.getInteger(R.styleable.HorizontalDottedProgress_count, mDotCount)
            mDotCount = mDotCount.coerceAtLeast(MIN_COUNT).coerceAtMost(MAX_COUNT)
            mTimeout = a.getInteger(R.styleable.HorizontalDottedProgress_timeout, mTimeout)
            mTimeout = mTimeout.coerceAtLeast(MIN_TIMEOUT).coerceAtMost(MAX_TIMEOUT)
        } finally {
            a.recycle()
        }
    }

    //Method to draw your customized dot on the canvas
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (isShown) {
            paint.color = mDotColor
            createDots(canvas, paint)
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        startAnimation()
    }

    private fun createDots(
        canvas: Canvas,
        paint: Paint
    ) {
        for (i in 0 until mDotCount) {
            val radius = if (i == mDotPosition) mBigDotRadius else mDotRadius
            canvas.drawCircle(
                mDotStep / 2 + (i * mDotStep).toFloat(),
                mBigDotRadius.toFloat(),
                radius.toFloat(),
                paint
            )
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        // MUST CALL THIS
        setMeasuredDimension(mDotStep * mDotCount, mBigDotRadius * 2)
    }

    private fun startAnimation() {
        val bounceAnimation = BounceAnimation()
        bounceAnimation.duration = mTimeout.toLong()
        bounceAnimation.repeatCount = Animation.INFINITE
        bounceAnimation.interpolator = LinearInterpolator()
        bounceAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {}
            override fun onAnimationRepeat(animation: Animation) {
                if (++mDotPosition >= mDotCount) {
                    mDotPosition = 0
                }
            }
        })
        startAnimation(bounceAnimation)
    }

    private inner class BounceAnimation : Animation() {
        override fun applyTransformation(
            interpolatedTime: Float,
            t: Transformation
        ) {
            super.applyTransformation(interpolatedTime, t)
            // call invalidate to redraw your view again
            invalidate()
        }
    }

    companion object {
        // specify how many dots you need in a progressbar
        private const val MIN_COUNT = 1
        private const val DEF_COUNT = 10
        private const val MAX_COUNT = 100
        private const val MIN_TIMEOUT = 100
        private const val DEF_TIMEOUT = 500
        private const val MAX_TIMEOUT = 3000
    }
}
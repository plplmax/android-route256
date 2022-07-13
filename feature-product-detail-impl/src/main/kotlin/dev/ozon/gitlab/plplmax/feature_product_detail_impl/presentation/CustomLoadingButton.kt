package dev.ozon.gitlab.plplmax.feature_product_detail_impl.presentation

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import androidx.core.content.ContextCompat
import dev.ozon.gitlab.plplmax.feature_product_detail_impl.R

class CustomLoadingButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var buttonTextBefore = ""
    private var buttonTextAfter = ""

    private val textPaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
        color = Color.WHITE
        textSize = dpToPx(16f)
    }

    private val backgroundPaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
        color = ContextCompat.getColor(
            context,
            dev.ozon.gitlab.plplmax.core_resources.R.color.purple_200
        )
    }

    private val buttonRect = RectF()
    private val progressRect = RectF()

    private var buttonRadius = dpToPx(16f)

    private var offset: Float = 0f

    private val progressPaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
        color = Color.WHITE
        strokeWidth = dpToPx(2f)
    }

    private var widthAnimator: ValueAnimator? = null
    private var rotationAnimator: ValueAnimator? = null

    private var loading = false
    private var startAngle = 0f

    private var drawCheck = false

    var inBucket = false
        private set

    init {
        initializeAttrs(context, attrs)
    }

    private fun initializeAttrs(context: Context, attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomLoadingButton)

        buttonTextBefore = typedArray.getString(
            R.styleable.CustomLoadingButton_customLoadingButton_textBefore
        ) ?: ""

        buttonTextAfter = typedArray.getString(
            R.styleable.CustomLoadingButton_customLoadingButton_textAfter
        ) ?: ""

        typedArray.recycle()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        buttonRadius = measuredHeight / 2f

        buttonRect.apply {
            top = 0f
            left = 0f + offset
            right = measuredWidth.toFloat() - offset
            bottom = measuredHeight.toFloat()
        }

        backgroundPaint.color = if (inBucket) Color.GRAY else ContextCompat.getColor(
            context,
            dev.ozon.gitlab.plplmax.core_resources.R.color.purple_200
        )

        val textToDraw = if (inBucket) buttonTextAfter else buttonTextBefore

        canvas.drawRoundRect(buttonRect, buttonRadius, buttonRadius, backgroundPaint)

        if (offset < (measuredWidth - measuredHeight) / 2f) {
            val textX = measuredWidth / 2.0f - textPaint.measureText(textToDraw) / 2.0f
            val textY = measuredHeight / 2f - (textPaint.descent() + textPaint.ascent()) / 2f

            canvas.drawText(textToDraw, textX, textY, textPaint)
        }

        if (loading && offset == (measuredWidth - measuredHeight) / 2f) {
            with(progressRect) {
                left = measuredWidth / 2.0f - buttonRect.width() / 4
                top = measuredHeight / 2.0f - buttonRect.width() / 4
                right = measuredWidth / 2.0f + buttonRect.width() / 4
                bottom = measuredHeight / 2.0f + buttonRect.width() / 4

                canvas.drawArc(progressRect, startAngle, 140f, false, progressPaint)
            }
        }

        if (drawCheck) {
            canvas.save()
            canvas.rotate(45f, measuredWidth / 2f, measuredHeight / 2f)

            val x1 = measuredWidth / 2f - buttonRect.width() / 8
            val y1 = measuredHeight / 2f + buttonRect.width() / 4
            val x2 = measuredWidth / 2f + buttonRect.width() / 8
            val y2 = measuredHeight / 2f + buttonRect.width() / 4
            val x3 = measuredWidth / 2f + buttonRect.width() / 8
            val y3 = measuredHeight / 2f - buttonRect.width() / 4

            canvas.drawLine(x1, y1, x2, y2, progressPaint)
            canvas.drawLine(x2, y2, x3, y3, progressPaint)
            canvas.restore()

            drawCheck = false
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()

        widthAnimator?.cancel()
        rotationAnimator?.cancel()
    }

    fun startLoading() {
        widthAnimator = ValueAnimator.ofFloat(0f, 1f).apply {
            addUpdateListener {
                offset = (measuredWidth - measuredHeight) / 2f * it.animatedValue as Float
                invalidate()
            }

            addListener(
                object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        super.onAnimationEnd(animation)

                        startProgressAnimation()
                    }
                }
            )

            duration = 200
        }

        loading = true
        isClickable = false
        widthAnimator?.start()
    }

    private fun startProgressAnimation() {
        rotationAnimator = ValueAnimator.ofFloat(0f, 360f).apply {
            addUpdateListener {
                startAngle = it.animatedValue as Float
                invalidate()
            }

            duration = 600
            repeatCount = Animation.INFINITE
            interpolator = LinearInterpolator()

            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)

                    loading = false
                    invalidate()
                }
            })
        }

        rotationAnimator?.start()
    }

    fun done(immediately: Boolean = false) {
        loading = !immediately
        drawCheck = !immediately
        isClickable = true
        inBucket = !inBucket
        rotationAnimator?.cancel()
        if (!immediately) doneAfterDraw()
        invalidate()
    }

    private fun doneAfterDraw() {
        widthAnimator = ValueAnimator.ofFloat(1f, 0f).apply {
            addUpdateListener {
                offset = (measuredWidth - measuredHeight) / 2f * it.animatedValue as Float
                invalidate()
            }

            duration = 200
            startDelay = 600
        }

        widthAnimator?.start()
    }

    private fun dpToPx(dp: Float): Float {
        return dp * Resources.getSystem().displayMetrics.density
    }
}
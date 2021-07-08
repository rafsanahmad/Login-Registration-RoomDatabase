package com.dev.custombutton

import android.animation.LayoutTransition
import android.annotation.TargetApi
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.dev.custombutton.model.Shape
import com.github.nikartm.button.util.RippleEffect
import com.github.nikartm.button.util.dpToPx


/**
 * A custom button that displays an image, a title, and a subtitle.
 */
class CustomButton : LinearLayout {
    private lateinit var container: LinearLayout
    private lateinit var imageView: ImageView
    private lateinit var titleView: TextView
    private lateinit var subtitleView: TextView

    private var cornerRadius: Float = 0f
    private var rippleColor: Int = 0
    private var borderColor: Int = 0
    private var borderWidth: Float = 0f
    private var btnShape: Shape = Shape.RECTANGLE
    private var btnBackgroundColor: Int = Color.WHITE

    private var buttonListener: (() -> Unit)? = null

    constructor(context: Context) : super(context) {
        init(context, null, null, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs, null, null)
    }

    constructor(ctx: Context, attr: AttributeSet?, defStyle: Int) : super(ctx, attr, defStyle) {
        init(ctx, attr, defStyle, null)
    }

    @TargetApi(21)
    constructor(ctx: Context, attr: AttributeSet?, defStyle: Int, defStyleRes: Int)
            : super(ctx, attr, defStyle, defStyleRes) {
        init(ctx, attr, defStyle, defStyleRes)
    }


    private fun init(context: Context, attrs: AttributeSet?, defStyle: Int?, defStyleRes: Int?) {
        val a = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CustomButton,
            defStyle ?: 0,
            defStyleRes ?: 0
        )

        val inflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.button_layout, this, true)
        orientation = LinearLayout.VERTICAL
        gravity = Gravity.CENTER

        /* Set android:animateLayoutChanges="true" programmatically */
        layoutTransition = LayoutTransition()

        container = findViewById(R.id.btn_container)
        imageView = findViewById(R.id.btn_image)
        titleView = findViewById(R.id.btn_title)
        subtitleView = findViewById(R.id.btn_subtitle)

        val imageRes: Int
        val imageTint: Int
        val imageVisible: Boolean
        val imageSize: Int

        val title: String?
        val titleColor: Int

        val subtitle: String?
        val subtitleColor: Int

        val titleVisible: Boolean
        val subtitleVisible: Boolean

        try {
            imageRes = a.getResourceId(R.styleable.CustomButton_btn_image, R.drawable.info_icon)
            imageTint = a.getColor(R.styleable.CustomButton_btn_imageTint, 0)
            imageVisible = a.getBoolean(R.styleable.CustomButton_btn_imageVisible, false)
            imageSize = a.getDimensionPixelSize(R.styleable.CustomButton_btn_imageSize, 0)
            title = a.getString(R.styleable.CustomButton_btn_title)
            titleColor = a.getColor(
                R.styleable.CustomButton_btn_titleColor,
                resources.getColor(R.color.view_title)
            )
            titleVisible = a.getBoolean(R.styleable.CustomButton_btn_titleVisible, true)
            subtitle = a.getString(R.styleable.CustomButton_btn_subtitle)
            subtitleColor = a.getColor(
                R.styleable.CustomButton_btn_subtitleColor,
                resources.getColor(R.color.view_subtitle)
            )
            subtitleVisible = a.getBoolean(R.styleable.CustomButton_btn_subtitleVisible, true)

            cornerRadius = a.getDimension(R.styleable.CustomButton_btn_cornerRadius, 0f)
            rippleColor = a.getColor(
                R.styleable.CustomButton_btn_rippleColor,
                Color.TRANSPARENT
            )
            btnBackgroundColor = a.getColor(
                R.styleable.CustomButton_btn_backgroundColor,
                resources.getColor(R.color.background_color)
            )
            borderColor = a.getColor(
                R.styleable.CustomButton_btn_borderColor,
                Color.TRANSPARENT
            )
            borderWidth = a.getDimension(R.styleable.CustomButton_btn_borderWidth, 0f)
            btnShape =
                Shape.fromInt(a.getInt(R.styleable.CustomButton_btn_shape, Shape.RECTANGLE.shape))

            if (imageRes != 0) {
                setImage(imageRes)
            }
            if (imageTint != 0) {
                setImageTint(imageTint)
            }
            setImageVisible(imageVisible)
            if (imageSize != 0) {
                setImageSize(imageSize)
            }

            if (title != null) {
                setTitle(title)
            }

            if (subtitle != null) {
                setSubtitle(subtitle)
                subtitleView.visibility = View.VISIBLE
            }

            if (!titleVisible) {
                titleView.visibility = View.GONE
            }

            if (!subtitleVisible) {
                subtitleView.visibility = View.GONE
            }
            titleView.setTextColor(titleColor)
            subtitleView.setTextColor(subtitleColor)

            if (cornerRadius != 0f) {
                setCornerRadius(cornerRadius)
            }

            setBtnBackgroundColor(btnBackgroundColor)

            if (borderWidth != 0f) {
                setButtonBorder(borderWidth, borderColor, btnBackgroundColor)
            }
            setRippleColor(rippleColor)
        } finally {
            a.recycle()
        }
    }

    /**
     * Indicates whether the title is currently visible.
     */
    var isImageVisible: Boolean
        get() = imageView.visibility == View.VISIBLE
        set(value) = setImageVisible(value).unitify()

    /**
     * Returns the current title string.
     */
    var title: CharSequence
        get() = titleView.text
        set(value) = setTitle(value.toString()).unitify()

    /**
     * Indicates whether the title is currently visible.
     */
    var isTitleVisible: Boolean
        get() = titleView.visibility == View.VISIBLE
        set(value) = setTitleVisible(value).unitify()

    /**
     * Returns the current subtitle.
     */
    var subtitle: CharSequence
        get() = subtitleView.text
        set(value) = setSubtitle(value.toString()).unitify()

    /**
     * Indicates whether the subtitle is currently visible.
     */
    var isSubtitleVisible: Boolean
        get() = subtitleView.visibility == View.VISIBLE
        set(value) = setSubtitleVisible(value).unitify()

    /**
     * Sets button image to a given drawable resource.
     */
    fun setImage(res: Int): CustomButton {
        imageView.setImageResource(res)
        return this
    }

    /**
     * Sets the button image to a given [android.graphics.drawable.Drawable].
     */
    fun setImage(drawable: Drawable): CustomButton {
        imageView.setImageDrawable(drawable)
        return this
    }

    /**
     * Sets the image button to a given [android.graphics.Bitmap].
     */
    fun setImage(bitmap: Bitmap): CustomButton {
        imageView.setImageBitmap(bitmap)
        return this
    }

    /**
     * Tints the Button image with given color.
     */
    fun setImageTint(color: Int): CustomButton {
        imageView.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        return this
    }

    /**
     * Shows or hides the Button image.
     */
    fun setImageVisible(visible: Boolean): CustomButton {
        imageView.visibility = if (visible) View.VISIBLE else View.GONE
        return this
    }

    /**
     * Sets Button image width to given pixel value. The ImageView adjusts its bounds to preserve the
     * aspect ratio of its drawable.
     */
    fun setImageSize(width: Int): CustomButton {
        imageView.layoutParams.width = width
        return this
    }

    /**
     * Sets the Button title to a given [java.lang.String].
     */
    fun setTitle(text: String?): CustomButton {
        setTitleVisible(text != null)
        titleView.text = text
        return this
    }

    /**
     * Sets the Button title to a given string resource.
     */
    fun setTitle(res: Int): CustomButton {
        // An exception will be thrown if the res isn't found anyways so it's safe to just go ahead
        // and make the title visible.
        setTitleVisible(true)
        titleView.setText(res)
        return this
    }

    /**
     * Sets the Button title text to a given color.
     */
    fun setTitleColor(color: Int): CustomButton {
        titleView.setTextColor(color)
        return this
    }

    /**
     * Sets the Button background to a given color.
     */
    fun setBtnBackgroundColor(color: Int): CustomButton {
        container.setBackgroundColor(color)
        return this
    }

    /**
     * Shows or hides the Button title
     */
    fun setTitleVisible(visible: Boolean): CustomButton {
        titleView.visibility = if (visible) View.VISIBLE else View.GONE
        return this
    }

    /**
     * Sets the Button subtitle to a given [java.lang.String].
     */
    fun setSubtitle(subtitle: String?): CustomButton {
        setSubtitleVisible(subtitle != null)
        subtitleView.text = subtitle
        return this
    }

    /**
     * Sets the Button subtitle to a given string resource.
     */
    fun setSubtitle(res: Int): CustomButton {
        // An exception will be thrown if the res isn't found anyways so it's safe to just go ahead
        // and make the title visible.
        setSubtitleVisible(true)
        subtitleView.setText(res)
        return this
    }

    /**
     * Sets the Button subtitle text to a given color
     */
    fun setSubtitleColor(color: Int): CustomButton {
        subtitleView.setTextColor(color)
        return this
    }

    /**
     * Shows or hides Button subtitle.
     */
    fun setSubtitleVisible(visible: Boolean): CustomButton {
        subtitleView.visibility = if (visible) View.VISIBLE else View.GONE
        return this
    }


    /**
     * Get the button corner radius
     * @return button corner radius [Float]
     */
    fun getCornerRadius(): Float = getButton().cornerRadius

    /**
     * Set the button corner radius
     * @param cornerRadius [Float]
     */
    fun setCornerRadius(cornerRadius: Float): CustomButton {
        getButton().cornerRadius = cornerRadius
        setButtonCornerRadius(cornerRadius)
        return this
    }

    /**
     * Get the button ripple effect color
     * @return ripple effect color [Int]
     */
    fun getRippleColor(): Int = getButton().rippleColor

    /**
     * Set the button ripple effect color
     * @param rippleColor [Int]
     */
    fun setRippleColor(rippleColor: Int): CustomButton {
        getButton().rippleColor = rippleColor
        addRipple()
        return this
    }

    /**
     * Get the button shape. By default uses [Shape.RECTANGLE]
     * @return button shape [Shape]
     */
    fun getButtonShape(): Shape = getButton().btnShape

    /**
     * Set the button shape
     * @param btnShape [Shape]
     */
    fun setButtonShape(btnShape: Shape): CustomButton {
        getButton().btnShape = btnShape
        return this
    }

    /**
     * Get the button border color
     * @return border color [Int]
     */
    fun getBorderColor(): Int = getButton().borderColor

    /**
     * Set the button border color
     * @param borderColor [Int]
     */
    fun setBorderColor(borderColor: Int): CustomButton {
        getButton().borderColor = borderColor
        setButtonBorder(
            getButton().borderWidth,
            getButton().borderColor,
            getButton().btnBackgroundColor
        )
        return this
    }

    /**
     * Get the button border width
     * @return border width [Float]
     */
    fun getBorderWidth(): Float = getButton().borderWidth

    /**
     * Set the button border width
     * @param borderWidth [Float]
     */
    fun setBorderWidth(borderWidth: Float): CustomButton {
        getButton().borderWidth = dpToPx(borderWidth)
        setButtonBorder(
            getButton().borderWidth,
            getButton().borderColor,
            getButton().btnBackgroundColor
        )
        return this
    }

    fun getButton(): CustomButton {
        return this
    }

    fun setButtonBorder(borderWidth: Float, borderColor: Int, backgroundColor: Int) {
        val border = GradientDrawable()
        border.setColor(backgroundColor)
        border.setStroke(borderWidth.toInt(), borderColor)
        if (cornerRadius != 0f) {
            border.cornerRadius = getButton().cornerRadius
        }
        container.setBackground(border)
    }

    fun setButtonCornerRadius(radius: Float) {
        val shape = GradientDrawable()
        shape.cornerRadius = radius
        container.background = shape
    }

    fun addRipple() {
        /*val shape = GradientDrawable()
        //container.background = shape
        RippleEffect.createRipple(
            container,
            getButton().btnBackgroundColor,
            getButton().rippleColor,
            getButton().cornerRadius,
            getButton().btnShape,
            shape
        )*/
    }

    private fun Any.unitify(): Unit {}
}


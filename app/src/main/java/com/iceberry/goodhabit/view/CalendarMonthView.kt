package com.iceberry.goodhabit.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.text.TextUtils
import com.haibin.calendarview.Calendar
import com.haibin.calendarview.MonthView
import com.iceberry.goodhabit.R

/**
 * author: Iceberry
 * email:qddwork@outlook.com
 * date: 2021/4/4
 * desc:
 *
 */
class CalendarMonthView(context: Context) : MonthView(context) {
    private var mRadius: Int = 0

    /**
     * 自定义魅族标记的文本画笔
     */
    private val mTextPaint = Paint()

    /**
     * 24节气画笔
     */
    private val mSolarTermTextPaint = Paint()

    /**
     * 背景圆点
     */
    private val mPointPaint = Paint()

    /**
     * 今天的背景色
     */
    private val mCurrentDayPaint = Paint()

    /**
     * 圆点半径
     */
    private var mPointRadius = 0f

    private var mPadding = 0

    private var mCircleRadius = 0f

    /**
     * 自定义魅族标记的圆形背景
     */
    private val mSchemeBasicPaint = Paint()

    private var mSchemeBaseLine = 0f

    init {
        mTextPaint.apply {
            textSize = dipToPx(context, 8f).toFloat()
            isAntiAlias = true
            isFakeBoldText = true
        }
        //二十四节气画笔
        mSolarTermTextPaint.apply {
            color = Color.GRAY
            isAntiAlias = true
            textAlign = Paint.Align.CENTER
        }
        //标记画笔
        mSchemeBasicPaint.apply {
            isAntiAlias = true
            style = Paint.Style.FILL
            textAlign = Paint.Align.CENTER
            isFakeBoldText = true
            color = resources.getColor(R.color.view_primary_color)
        }
        //当前日画笔
        mCurrentDayPaint.apply {
            isAntiAlias = true
            style = Paint.Style.STROKE
            strokeWidth = 2f
            color = resources.getColor(R.color.view_primary_color)
        }
        mPointPaint.apply {
            isAntiAlias = true
            style = Paint.Style.FILL
            textAlign = Paint.Align.CENTER
        }
        mCircleRadius = dipToPx(context, 7f).toFloat()
        mPadding = dipToPx(context, 3f)
        mPointRadius = dipToPx(context, 2f).toFloat()
        val metrics = mSchemeBasicPaint.fontMetrics
        mSchemeBaseLine =
            mCircleRadius - metrics.descent + (metrics.bottom - metrics.top) / 2 + dipToPx(
                context,
                1f
            )
    }

    override fun onPreviewHook() {
        mSolarTermTextPaint.textSize = mCurMonthLunarTextPaint.textSize
        mRadius = mItemWidth.coerceAtMost(mItemHeight) / 11 * 5
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }

    override fun onDrawSelected(
        canvas: Canvas,
        calendar: Calendar?,
        x: Int,
        y: Int,
        hasScheme: Boolean
    ): Boolean {
        val cx = x + mItemWidth / 2
        val cy = y + mItemHeight / 2
        canvas.drawCircle(cx.toFloat(), cy.toFloat(), mRadius.toFloat(), mSelectedPaint)
        return true
    }

    override fun onDrawScheme(canvas: Canvas, calendar: Calendar?, x: Int, y: Int) {
        val isSelected = isSelected(calendar)
        mPointPaint.color = /*if (isSelected) Color.WHITE else*/ Color.GRAY
        canvas.drawCircle(
            (x + mItemWidth / 2).toFloat(),
            (y + mItemHeight - 3 * mPadding).toFloat(),
            mPointRadius,
            mPointPaint
        )
    }

    override fun onDrawText(
        canvas: Canvas,
        calendar: Calendar,
        x: Int,
        y: Int,
        hasScheme: Boolean,
        isSelected: Boolean
    ) {
        val cx = x + mItemWidth / 2
        val cy = y + mItemHeight / 2
        val top = y - mItemHeight / 6
        if (calendar.isCurrentDay && !isSelected) {
            canvas.drawCircle(cx.toFloat(), cy.toFloat(), mRadius.toFloat(), mCurrentDayPaint)
        }
        if (hasScheme) {
            canvas.drawCircle(
                x + mItemWidth - mPadding - mCircleRadius / 2,
                y + mPadding + mCircleRadius,
                mCircleRadius,
                mSchemeBasicPaint
            )
            mTextPaint.color = calendar.schemeColor
            canvas.drawText(
                calendar.scheme,
                x + mItemWidth - mPadding - mCircleRadius - 1f,
                y + mPadding + mSchemeBaseLine - 1f,
                mTextPaint
            )
        }

        //当然可以换成其它对应的画笔就不麻烦，
        if (/*calendar.isWeekend && */calendar.isCurrentMonth) {
            mCurMonthTextPaint.color =
                resources.getColor(R.color.view_text)//-0xb76201
            mCurMonthLunarTextPaint.color = Color.GRAY//-0xb76201
            mSchemeTextPaint.color = resources.getColor(R.color.view_text)//-0xb76201
            mSchemeLunarTextPaint.color = Color.GRAY //-0xb76201
            //mOtherMonthLunarTextPaint.color =//-0xb76201
            //mOtherMonthTextPaint.color = //-0xb76201
        } else {
            mCurMonthTextPaint.color = resources.getColor(R.color.view_text)//-0xcccccd
            mCurMonthLunarTextPaint.color = Color.GRAY//-0x303031
            mSchemeTextPaint.color = resources.getColor(R.color.view_text)//-0xcccccd
            mSchemeLunarTextPaint.color = Color.GRAY//-0x303031
            //mOtherMonthTextPaint.color =//-0x1e1e1f
            //mOtherMonthLunarTextPaint.color = //-0x1e1e1f
        }
        when {
            isSelected -> {
                canvas.drawText(
                    calendar.day.toString(), cx.toFloat(),
                    mTextBaseLine + top,
                    mSelectTextPaint
                )
                canvas.drawText(
                    calendar.lunar,
                    cx.toFloat(),
                    mTextBaseLine + y + mItemHeight / 10,
                    mSelectedLunarTextPaint
                )
            }
            hasScheme -> {
                canvas.drawText(
                    calendar.day.toString(), cx.toFloat(),
                    mTextBaseLine + top,
                    if (calendar.isCurrentMonth) mSchemeTextPaint else mOtherMonthTextPaint
                )
                canvas.drawText(
                    calendar.lunar,
                    cx.toFloat(),
                    mTextBaseLine + y + mItemHeight / 10,
                    if (!TextUtils.isEmpty(calendar.solarTerm))
                        mSolarTermTextPaint
                    else mSchemeLunarTextPaint
                )
            }
            else -> {
                canvas.drawText(
                    calendar.day.toString(),
                    cx.toFloat(),
                    mTextBaseLine + top,
                    when {
                        calendar.isCurrentDay -> mCurDayTextPaint
                        calendar.isCurrentMonth -> mCurMonthTextPaint
                        else -> mOtherMonthTextPaint
                    }
                )
                canvas.drawText(
                    calendar.lunar,
                    cx.toFloat(),
                    mTextBaseLine + y + mItemHeight / 10,
                    when {
                        calendar.isCurrentDay -> mCurDayLunarTextPaint
                        calendar.isCurrentMonth ->
                            if (!TextUtils.isEmpty(calendar.solarTerm)) mSolarTermTextPaint else mCurMonthLunarTextPaint
                        else -> mOtherMonthLunarTextPaint
                    }
                )
            }
        }
    }

    /**
     * dp转px
     *
     * @param context context
     * @param dpValue dp
     * @return px
     */
    private fun dipToPx(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }
}
package com.iceberry.goodhabit

import android.R.attr
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import com.haibin.calendarview.Calendar
import com.haibin.calendarview.MonthView


/**
 * author: Iceberry
 * email:qddwork@outlook.com
 * date: 2021/4/4
 * desc:
 *
 */
class CalendarMonthView(context: Context) : MonthView(context) {
    private val mTextPaint = Paint()
    private val mSchemeBasicPaint = Paint()
    var mRadio = Float.NaN
    var mPadding = Float.NaN
    var mSchemeBaseLine = Float.NaN

    init {
        mTextPaint.apply {
            textSize = 30f//dipToPx(context, 8f).toFloat()
            color = 0x000000
            isAntiAlias = true
            isFakeBoldText = true
            strokeWidth=2f
            style=Paint.Style.FILL
            isDither=true
        }
        mSchemeBasicPaint.apply {
            isAntiAlias = true
            style = Paint.Style.FILL
            textAlign = Paint.Align.CENTER
            isFakeBoldText = true
        }
        mRadio = dipToPx(context, 7f).toFloat()
        mPadding = dipToPx(context, 4f).toFloat()
        val metrics = mSchemeBasicPaint.fontMetrics
        mSchemeBaseLine =
            mRadio - metrics.descent + (metrics.bottom - metrics.top) / 2 + dipToPx(context, 1f)
    }

    /**
     * 绘制选中的日期
     *
     * @param canvas    canvas
     * @param calendar  日历日历calendar
     * @param x         日历Card x起点坐标
     * @param y         日历Card y起点坐标
     * @param hasScheme hasScheme 非标记的日期
     * @return 是否绘制onDrawScheme，true or false
     */
    override fun onDrawSelected(
        canvas: Canvas,
        calendar: Calendar,
        x: Int,
        y: Int,
        hasScheme: Boolean
    ): Boolean {
        mSelectedPaint.style = Paint.Style.FILL
        canvas.drawRect(
            x + mPadding,
            y + mPadding,
            x + mItemWidth - mPadding,
            y + mItemHeight - mPadding,
            mSelectedPaint
        )
        return true
    }

    /**
     * 绘制标记的日期,这里可以是背景色，标记色什么的
     *
     * @param canvas   canvas
     * @param calendar 日历calendar
     * @param x        日历Card x起点坐标
     * @param y        日历Card y起点坐标
     */
    override fun onDrawScheme(canvas: Canvas, calendar: Calendar, x: Int, y: Int) {
        mSchemeBasicPaint.color = calendar.schemeColor

        canvas.apply {
            drawCircle(
                x + mItemWidth - mPadding - mRadio / 2,
                y + mPadding + mRadio,
                mRadio,
                mSchemeBasicPaint
            )
            drawText(
                calendar.scheme,
                x + mItemWidth - mPadding - mRadio / 2 - getTextWidth(calendar.scheme) / 2,
                y + mPadding + mSchemeBaseLine,
                mTextPaint
            )
        }
    }

    /**
     * 绘制日历文本
     *
     * @param canvas     canvas
     * @param calendar   日历calendar
     * @param x          日历Card x起点坐标
     * @param y          日历Card y起点坐标
     * @param hasScheme  是否是标记的日期
     * @param isSelected 是否选中
     */
    override fun onDrawText(
        canvas: Canvas,
        calendar: Calendar,
        x: Int,
        y: Int,
        hasScheme: Boolean,
        isSelected: Boolean
    ) {
        val cx = attr.x + mItemWidth / 2
        val top = attr.y - mItemHeight / 6

        val isInRange = isInRange(calendar)

        when {
            isSelected -> {
                canvas.drawText(
                    java.lang.String.valueOf(calendar.day), cx.toFloat(),
                    mTextBaseLine + top,
                    mSelectTextPaint
                )
                canvas.drawText(
                    calendar.lunar,
                    cx.toFloat(),
                    mTextBaseLine + attr.y + mItemHeight / 10, mSelectedLunarTextPaint
                )
            }
            hasScheme -> {
                canvas.drawText(
                    java.lang.String.valueOf(calendar.day), cx.toFloat(),
                    mTextBaseLine + top,
                    if (calendar.isCurrentMonth && isInRange) mSchemeTextPaint else mOtherMonthTextPaint
                )
                canvas.drawText(
                    calendar.lunar,
                    cx.toFloat(),
                    mTextBaseLine + attr.y + mItemHeight / 10, mCurMonthLunarTextPaint
                )
            }
            else -> {
                canvas.drawText(
                    java.lang.String.valueOf(calendar.day), cx.toFloat(),
                    mTextBaseLine + top,
                    if (calendar.isCurrentDay) mCurDayTextPaint else if (calendar.isCurrentMonth && isInRange) mCurMonthTextPaint else mOtherMonthTextPaint
                )
                canvas.drawText(
                    calendar.lunar, cx.toFloat(),
                    mTextBaseLine + attr.y + mItemHeight / 10,
                    if (calendar.isCurrentDay && isInRange) mCurDayLunarTextPaint else if (calendar.isCurrentMonth) mCurMonthLunarTextPaint else mOtherMonthLunarTextPaint
                )
            }
        }
    }

    private fun getTextWidth(text: String): Float {
        return mTextPaint.measureText(text)
    }

    companion object {
        private fun dipToPx(context: Context, dpValue: Float): Int {
            val scale = context.resources.displayMetrics.density
            return (dpValue * scale + 0.5f).toInt()
        }
    }

}
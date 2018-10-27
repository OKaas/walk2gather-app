package com.walk2gather.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.walk2gather.R
import com.walk2gather.R.id.*
import com.walk2gather.R.layout.group_item_view
import com.walk2gather.model.ui.GroupItem


class CustomAdapter(private val dataSet: ArrayList<GroupItem>, private var mContext: Context) :
    ArrayAdapter<GroupItem>(mContext, R.layout.group_item_view, dataSet), View.OnClickListener {

    private var lastPosition = -1

    private val TAG = this::class.java.simpleName

    // View lookup cache
    private class ViewHolder {
        internal var txtName: TextView? = null
        internal var txtOccupancy: TextView? = null
        internal var info : ImageView? = null
        internal var uidPoint: String = ""
    }

    override fun onClick(v: View) {

    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        // Get the data item for this position
        val dataModel = getItem(position)
        // Check if an existing view is being reused, otherwise inflate the view
        val viewHolder: ViewHolder // view lookup cache stored in tag

        val result: View

        if (convertView == null) {

            viewHolder = ViewHolder()
            val inflater = LayoutInflater.from(context)

            convertView = inflater.inflate(group_item_view, parent, false)
            viewHolder.txtName = convertView!!.findViewById(groupItemView_textView_name)
            viewHolder.txtOccupancy = convertView!!.findViewById(groupItemView_textView_occupancy)
            viewHolder.info = convertView!!.findViewById(groupItemView_imageView_info)

            result = convertView

            convertView!!.tag = viewHolder
        } else {
            viewHolder = convertView!!.tag as ViewHolder
            result = convertView
        }

        val animation = AnimationUtils.loadAnimation(
            mContext,
            if (position > lastPosition) R.anim.up_from_bottom else R.anim.down_from_top
        )
        result.startAnimation(animation)
        lastPosition = position

        viewHolder.txtName!!.text = dataModel!!.name
        viewHolder.txtOccupancy!!.text = dataModel!!.occupancy.toString()
        viewHolder.uidPoint = dataModel.uidPoint
//        viewHolder.txtType!!.setText(dataModel!!.getType())
//        viewHolder.txtVersion!!.setText(dataModel!!.getVersion_number())
        viewHolder.info!!.setOnClickListener(this)
//        viewHolder.info!!.tag = position
        // Return the completed view to render on screen
        return convertView
    }
}
package com.walk2gather.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.walk2gather.R
import com.walk2gather.model.GroupItem


class CustomAdapter(private val dataSet: ArrayList<GroupItem>, private var mContext: Context) :
    ArrayAdapter<GroupItem>(mContext, R.layout.group_item_view, dataSet), View.OnClickListener {

    private var lastPosition = -1

    private val TAG = this::class.java.simpleName

    // View lookup cache
    private class ViewHolder {
        internal var txtName: TextView? = null
        internal var txtOccupancy: TextView? = null
        internal var info : ImageView? = null
    }

    override fun onClick(v: View) {

//        val position = v.tag as Int
//        val `object` = getItem(position)
//        val dataModel = `object` as GroupItem

//        when (v.getId()) {

//            R.id.groupItemView_imageView_info -> Log.i(TAG, "Name: "+(dataModel!!.name) + " | Occupancy: "+dataModel!!.occupancy)

//            R.id.item_info -> Snackbar.make(v, "Release date " + dataModel!!.getFeature(), Snackbar.LENGTH_LONG)
//                .setAction("No action", null).show()
//        }
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
            convertView = inflater.inflate(R.layout.group_item_view, parent, false)
            viewHolder.txtName = convertView!!.findViewById(R.id.groupItemView_textView_name)
            viewHolder.txtOccupancy = convertView!!.findViewById(R.id.groupItemView_textView_occupancy)
            viewHolder.info = convertView!!.findViewById(R.id.groupItemView_imageView_info)

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
//        viewHolder.txtType!!.setText(dataModel!!.getType())
//        viewHolder.txtVersion!!.setText(dataModel!!.getVersion_number())
        viewHolder.info!!.setOnClickListener(this)
//        viewHolder.info!!.tag = position
        // Return the completed view to render on screen
        return convertView
    }
}
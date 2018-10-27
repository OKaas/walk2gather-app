package com.walk2gather.model.ui

class GroupItem( val name : String, val occupancy: Int, val uidPoint: String) {
    override fun toString(): String{
        return "GroupItem(name='$name', occupancy=$occupancy, uidPoint= %uidPoint)"
    }
}